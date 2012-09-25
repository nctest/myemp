package nc.ui.myemp.method.view;

import java.lang.reflect.Array;
import java.util.List;

import javax.swing.ListSelectionModel;

import nc.desktop.ui.WorkbenchEnvironment;
import nc.ui.myemp.method.event.MethodAppEventConst;
import nc.ui.myemp.method.model.MethodBillManageModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
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
import nc.vo.myemp.method.BasisVO;
import nc.vo.myemp.method.MethodVO;

import org.apache.commons.lang.StringUtils;

public class MethodBillForm extends BillForm implements BillEditListener,
		BillEditListener2, ValueChangedListener {
	private static final long serialVersionUID = 1L;
	private int editRow;
	private BasisBillForm basisForm;

	@Override
	public void initUI() {
		super.initUI();
		// �ر��Զ�����
		billCardPanel.setBodyAutoAddLine(false);
		billCardPanel.addBodyEditListener2(this);
		billCardPanel.addEditListener(this);
		// ����һ��ֻ��ѡ��һ��
		billCardPanel.getBillTable().getSelectionModel()
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// ΪҪ�����ValueChangedListener
		UIRefPane factorRefPane = (UIRefPane) billCardPanel.getBodyItem(
				"factor").getComponent();
		factorRefPane.addValueChangedListener(this);
	}

	@Override
	protected void synchronizeDataFromModel() {
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
	}

	@Override
	public Object getValue() {
		Object methodVOs = super.getValue();
		// ͨ�����ã�setEditRow,beforeEdit��restoreState�������������޸�״̬�µõ��Ķ��Ǻ���һ��Ԫ�ص����顣
		// ����ʱֻ����һ�����ݣ�����Ϊ�գ�
		if (MethodVO[].class.isInstance(methodVOs)
				&& Array.getLength(methodVOs) > 0) {
			MethodVO[] vos = (MethodVO[]) methodVOs;
			if (isUIStateAdd() && vos[0].getPk_method() == null) {
				vos[0].setBases((BasisVO[]) basisForm.getValue());
				return vos[0];
			} else if (isUIStateEdit()) {
				vos[0].setBases((BasisVO[]) basisForm.getValue());
			}
			return vos;
		}
		return methodVOs;
	}

	private boolean isUIStateEdit() {
		return getModel().getUiState() == UIState.EDIT;
	}

	@Override
	protected void beforeGetValue() {
		super.beforeGetValue();
		restoreState();
	}

	private void restoreState() {
		int rowCount = billCardPanel.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			// �����ǰ�༭���а���i,�͸���UIState��ֵ����i�е�״̬����Ϊ��Ӧ��״̬
			if (i != editRow) {
				billCardPanel.getBillModel().setRowState(i, BillModel.NORMAL);
			} else {
				if (isUIStateAdd()) {
					setRowState(i, BillModel.ADD);
				} else if (isUIStateEdit()) {
					setRowState(i, BillModel.MODIFICATION);
				}
			}
		}
	}

	private void setRowState(int i, int state) {
		billCardPanel.getBillModel().setRowState(i, state);
	}

	private boolean isUIStateAdd() {
		return getModel().getUiState() == UIState.ADD;
	}

	@Override
	protected void onAdd() {
		// ������ʽ��ԭ��װ�߼���ͬ������Ҫ�������
		setEditable(true);
		billCardPanel.addLine();
		// �����һ������Ϊ�༭��
		setEditRow(billCardPanel.getRowCount() - 1);
	}

	@Override
	protected void onEdit() {
		setEditable(true);
		// �˴��÷�����Ҫ��Ϊ�˴������޸�״̬�£�ֻ�޸��ӱ���û���޸�����
		// ��������������beforeGetValue�����лָ�״̬�����Ա�֤�ڻ�ȡֵʱ���Եõ�
		setEditRow(((MethodBillManageModel) getModel()).getSelectedRow());
	}

	@Override
	protected void onNotEdit() {
		super.onNotEdit();
		editRow = -1;
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		// �����޸ĵ��У��Ա���beforeGetValue�����н����ָ�Ϊ�޸�״̬
		setEditRow(e.getRow());
		// ����༭���ǹܿط�Χ������ֵ�ǿ�
		if (isControlAreaAndNotBlank(e)) {
			// �����ܿط�Χ��Ҫ��
			relateControlAreaAndFactor();
			// ����Ҫ��Ϊ�ɱ༭,����ȱ��
			setFactorEditable(e, true);
		}
	}

	/**
	 * ����Ҫ���Ƿ���Ա༭
	 * 
	 * @param e
	 * @param isEditable
	 */
	private void setFactorEditable(BillEditEvent e, boolean isEditable) {
		billCardPanel.getBillModel().setCellEditable(e.getRow(),
				MethodVO.FACTOR, isEditable);
	}

	// ����༭���ǹܿط�Χ������ֵ�ǿ�
	private boolean isControlAreaAndNotBlank(BillEditEvent e) {
		return MethodVO.CONTROLAREA.equals(e.getKey())
				&& StringUtils.isNotBlank((String) e.getValue());
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
		MethodBillManageModel model = (MethodBillManageModel) getModel();
		int row = e.getRow();
		@SuppressWarnings("unchecked")
		List<MethodVO> list = (List<MethodVO>) model.getData();
		MethodVO vo = null;
		if (list.size() > row) {
			vo = list.get(row);
		}
		if (vo == null) {
			vo = new MethodVO();
			UITable billTable = billCardPanel.getBillTable();
			int rowCount = billTable.getRowCount();
			DefaultConstEnum defaultConstEnum = (DefaultConstEnum) billTable
					.getValueAt(rowCount - 1, 4);// ��4��
			if (defaultConstEnum != null) {
				vo.setFactor((String) defaultConstEnum.getValue());
			}
		}
		// ����setMethodVO�������ڸ÷����д���SELECT_METHODVO�¼�
		model.setMethodVO(vo);
	}

	/**
	 * �÷���ֻ�ڱ༭Ҫ��ʱ���У����ݹܿط�Χ�Ƿ�Ϊ��������Ҫ���Ƿ���Ա༭
	 */
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		if (editRow != e.getRow()) {
			return false;
		}
		if (!MethodVO.FACTOR.equals(e.getKey())) {
			return true;
		}
		String controlArea = getControlAreaValue(e);
		// ���ݹܿط�Χ�Ƿ�Ϊ�գ�����factor�Ƿ���Ա༭
		setFactorEditable(e, StringUtils.isNotBlank(controlArea));
		if (StringUtils.isNotBlank(controlArea)) {
			relateControlAreaAndFactor();
		}
		return true;
	}

	private String getControlAreaValue(BillEditEvent e) {
		return (String) billCardPanel.getBillModel().getValueAt(e.getRow(),
				MethodVO.CONTROLAREA + IBillItem.ID_SUFFIX);
	}

	/**
	 * ��Ҫ�ص�ֵ�����ı�ʱ�������Զ���Factor_Changed�¼���
	 */
	@Override
	public void valueChanged(ValueChangedEvent event) {
		MethodBillManageModel model = (MethodBillManageModel) getModel();
		if (event.getOldValue() != null
				&& StringUtils.isNotBlank((String) Array.get(
						event.getOldValue(), 0))) {
			model.setEditFactorChanged(true);
		}
		model.fireEvent(new AppEvent(MethodAppEventConst.FACTOR_CHANGE
				.toString(), model, event));
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BasisBillForm getBasisForm() {
		return basisForm;
	}

	public void setBasisForm(BasisBillForm basisForm) {
		this.basisForm = basisForm;
	}

	public int getEditRow() {
		return editRow;
	}

	public void setEditRow(int editRow) {
		this.editRow = editRow;
	}

}
