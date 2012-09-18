package nc.ui.myemp.method.view;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListSelectionModel;

import nc.bs.logging.Logger;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.ui.myemp.method.event.MethodAppEventConst;
import nc.ui.myemp.method.model.MethodBillManageModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.resa.refmodel.FactorRefModel;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.UIState;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.method.MethodVO;

import org.apache.commons.lang.StringUtils;

public class MethodBillForm extends BillForm implements BillEditListener,
		BillEditListener2, ValueChangedListener {
	private static final long serialVersionUID = 1L;
	private Set<Integer> editRows = new HashSet<Integer>();
	private BasisBillForm basisForm;

	@Override
	public void initUI() {
		super.initUI();
		billCardPanel.setBodyAutoAddLine(false);
		billCardPanel.addBodyEditListener2(this);
		billCardPanel.addEditListener(this);
		billCardPanel.getBillTable().getSelectionModel()
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		UIRefPane factorRefPane = (UIRefPane) billCardPanel.getBodyItem(
				"factor").getComponent();
		factorRefPane.addValueChangedListener(this);
	}

	@Override
	protected void synchronizeDataFromModel() {
		Logger.debug("entering synchronizeDataFromModel");
		@SuppressWarnings("unchecked")
		List<MethodVO> data = ((BillManageModel) getModel()).getData();
		if (data != null && data.size() > 0) {
			billCardPanel.getBillModel().clearBodyData();
			for (int i = 0; i < data.size(); i++) {
				billCardPanel.addLine();
			}
			billCardPanel.getBillModel().setBodyRowObjectByMetaData(
					data.toArray(new MethodVO[0]), 0);
		}
		// ���õ�һ��ѡ��
		billCardPanel.getBillTable().getSelectionModel()
				.setSelectionInterval(0, 0);
		Logger.debug("leaving synchronizeDataFromModel");
	}

	@Override
	public Object getValue() {
		Object value = super.getValue();
		// ����ʱֻ����һ�����ݣ�����Ϊ�գ�
		if (isUIStateAdd() && value.getClass().isArray()) {
			MethodVO[] objs = (MethodVO[]) value;
			for (MethodVO vo : objs) {
				if (vo.getPk_method() == null) {
					vo.setBasisVOs(Arrays.asList(basisForm.getValue()));
					return vo;
				}
			}
		}
		return value;
	}

	@Override
	protected void beforeGetValue() {
		super.beforeGetValue();
		// ������������Щ�޸���Ϊ�޸�״̬,��Ϊ������SELECTION_CHANGED�¼�ʱ��������е���״̬��ΪNormal
		for (Integer editRow : editRows) {
			billCardPanel.getBillModel().setRowState(editRow,
					BillModel.MODIFICATION);
		}
	}

	private boolean isUIStateAdd() {
		return getModel().getUiState() == UIState.ADD;
	}

	@Override
	protected void onAdd() {
		// ������ʽ��ԭ��װ�߼���ͬ������Ҫ�������
		setEditable(true);
		billCardPanel.addLine();
	}

	@Override
	protected void onEdit() {
		setEditable(true);
	}

	@Override
	protected void onNotEdit() {
		super.onNotEdit();
		// ���editRows,��ֹӰ���´ε��޸Ĳ���
		editRows.clear();
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		// ���޸ĵ�����ӵ�editRows�У��Ա���beforeGetValue�����н����ǻָ�Ϊ�޸�״̬
		addToEditRows(e);
		if (isControlAreaAndNotBlank(e)) {
			// �����ܿط�Χ��Ҫ��
			relateControlAreaAndFactor();
			// ����Ҫ��Ϊ�ɱ༭
			setFactorEditable(e, true);
		}
	}

	private void setFactorEditable(BillEditEvent e, boolean isEditable) {
		billCardPanel.getBillModel().setCellEditable(e.getRow(),
				MethodVO.FACTOR, isEditable);
	}

	private boolean isControlAreaAndNotBlank(BillEditEvent e) {
		return MethodVO.CONTROLAREA.equals(e.getKey())
				&& StringUtils.isNotBlank((String) e.getValue());
	}

	private BillManageModel addToEditRows(BillEditEvent e) {
		BillManageModel model = (BillManageModel) getModel();
		if (e.getOldValue() != e.getValue()) {
			int selectedRow = model.getSelectedRow();
			if (!editRows.contains(selectedRow)) {
				editRows.add(selectedRow);
			}
		}
		return model;
	}

	/**
	 * �����ܿط�Χ��Ҫ��
	 */
	private void relateControlAreaAndFactor() {
		UIRefPane factorRefPane = (UIRefPane) billCardPanel.getBodyItem(
				MethodVO.FACTOR).getComponent();
		FactorRefModel factorRefModel = (FactorRefModel) factorRefPane
				.getRefModel();
		factorRefModel.setDate(WorkbenchEnvironment.getInstance().getBusiDate()
				.toLocalString());
		String controlArea = (String) billCardPanel.getBillModel().getValueAt(
				0, MethodVO.CONTROLAREA + IBillItem.ID_SUFFIX);
		factorRefModel.setPk_controlarea(controlArea);
	}

	@Override
	public void bodyRowChange(BillEditEvent e) {
		// ���basisBillForm�ı�������,��������״̬�£���ִ�иò������������ݲ�����ʧ
		basisForm.getBillCardPanel().getBillModel().clearBodyData();
		MethodBillManageModel model = (MethodBillManageModel) getModel();
		int row = e.getRow();
		@SuppressWarnings("unchecked")
		List<MethodVO> list = (List<MethodVO>) model.getData();
		if (list.size() <= row) {
			return;
		}
		MethodVO vo = list.get(row);
		// ����setMethodVO�������ڸ÷����д���SELECT_METHODVO�¼�
		model.setMethodVO(vo);
	}

	/**
	 * �÷���ֻ�ڱ༭Ҫ��ʱ���У����ݹܿط�Χ�Ƿ�Ϊ��������Ҫ���Ƿ���Ա༭
	 */
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		if (!MethodVO.FACTOR.equals(e.getKey())) {
			return true;
		}
		String controlArea = (String) billCardPanel.getBillModel().getValueAt(
				e.getRow(), MethodVO.CONTROLAREA + IBillItem.ID_SUFFIX);
		// ���ݹܿط�Χ�Ƿ�Ϊ�գ�����factor�Ƿ���Ա༭
		setFactorEditable(e, StringUtils.isNotBlank(controlArea));
		if (StringUtils.isNotBlank(controlArea)) {
			relateControlAreaAndFactor();
		}
		return true;
	}

	/**
	 * ��Ҫ�ص�ֵ�����ı�ʱ�������Զ���Factor_Changed�¼���
	 */
	@Override
	public void valueChanged(ValueChangedEvent event) {
		BillManageModel model = (BillManageModel) getModel();
		model.fireEvent(new AppEvent(MethodAppEventConst.FACTOR_CHANGE
				.toString(), model, event));
	}

	public Set<Integer> getEditRows() {
		return editRows;
	}

	public void setEditRows(Set<Integer> editRows) {
		this.editRows = editRows;
	}

	public BasisBillForm getBasisForm() {
		return basisForm;
	}

	public void setBasisForm(BasisBillForm basisForm) {
		this.basisForm = basisForm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
