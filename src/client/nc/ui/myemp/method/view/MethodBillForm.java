package nc.ui.myemp.method.view;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListSelectionModel;

import nc.bs.logging.Logger;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.ui.myemp.method.model.MethodBillManageModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
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
import nc.ui.uif2.model.AppEventConst;
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
		final UITable billTable = billCardPanel.getBillTable();
		billTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		UIRefPane factorRefPane = (UIRefPane) billCardPanel.getBodyItem(
				"factor").getComponent();
		factorRefPane.addValueChangedListener(this);
	}

	@Override
	public void handleEvent(AppEvent event) {
		super.handleEvent(event);
		if (isModelInitializedEvent(event) || isDataDeletedEvent(event)) {
			billCardPanel.getBillTable().getSelectionModel()
					.setSelectionInterval(0, 0);
			((BillManageModel) getModel()).setSelectedRow(0);
		}
	}

	private boolean isDataDeletedEvent(AppEvent event) {
		return AppEventConst.DATA_DELETED == event.getType();
	}

	private boolean isModelInitializedEvent(AppEvent event) {
		return AppEventConst.MODEL_INITIALIZED == event.getType();
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

	@SuppressWarnings("unused")
	private boolean isUIStateEdit() {
		return getModel().getUiState() == UIState.EDIT;
	}

	private boolean isUIStateAdd() {
		return getModel().getUiState() == UIState.ADD;
	}

	@Override
	protected void onAdd() {
		// ������ʽ��ԭ��װ�߼���ͬ,
		setEditable(true);
		billCardPanel.addLine();
	}

	@Override
	protected void onNotEdit() {
		super.onNotEdit();
		editRows.clear();
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		addToEditRows(e);
		BillManageModel model = (BillManageModel) getModel();
		if (MethodVO.CONTROLAREA.equals(e.getKey())
				&& StringUtils.isNotBlank((String) e.getValue())) {
			relateControlAreaAndFactor();
			// ����Ϊ�ɱ༭
			billCardPanel.getBillModel().setCellEditable(
					model.getSelectedRow(), MethodVO.FACTOR, true);
		}
		// else if (MethodVO.FACTOR.equals(e.getKey())) {
		// model.fireEvent(new AppEvent("Factor_Changed", model, e));
		// }

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
		if (getModel().getUiState() == UIState.NOT_EDIT) {
			int row = e.getRow();
			@SuppressWarnings("unchecked")
			List<MethodVO> data = ((BillManageModel) getModel()).getData();
			MethodVO vo = data.get(row);
			model.setMethodVO(vo);
		} else {

		}
	}

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		if (!MethodVO.FACTOR.equals(e.getKey())) {
			return true;
		}
		String controlArea = (String) billCardPanel.getBillModel().getValueAt(
				e.getRow(), MethodVO.CONTROLAREA + IBillItem.ID_SUFFIX);
		billCardPanel.getBillModel().setCellEditable(e.getRow(),
				MethodVO.FACTOR, StringUtils.isNotBlank(controlArea));
		if (StringUtils.isNotBlank(controlArea)) {
			relateControlAreaAndFactor();
		}
		return true;
	}

	@Override
	public void valueChanged(ValueChangedEvent event) {
		BillManageModel model = (BillManageModel) getModel();
		model.fireEvent(new AppEvent("Factor_Changed", model, event));
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
