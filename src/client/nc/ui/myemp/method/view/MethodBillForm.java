package nc.ui.myemp.method.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nc.bs.logging.Logger;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.resa.refmodel.FactorRefModel;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.UIState;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.method.MethodVO;

public class MethodBillForm extends BillForm implements BillEditListener,
		BillEditListener2 {
	private static final long serialVersionUID = 1L;
	private Set<Integer> editRows = new HashSet<Integer>();

	@Override
	public void initUI() {
		super.initUI();
		billCardPanel.setBodyAutoAddLine(false);
		billCardPanel.addBodyEditListener2(this);
		final UITable billTable = billCardPanel.getBillTable();
		billTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = billTable.rowAtPoint(e.getPoint());
				BillManageModel model = (BillManageModel) getModel();
				if (model.getSelectedRow() != selectedRow) {
					model.setSelectedRow(selectedRow);
				}
			}
		});
		BillItem[] bodyItems = billCardPanel.getBodyItems();
		for (BillItem billItem : bodyItems) {
			billItem.getItemEditor().addBillEditListener(this);
		}
	}

	@Override
	public void handleEvent(AppEvent event) {
		super.handleEvent(event);
		if (isModelInitializedEvent(event) || isDataDeletedEvent(event)) {
			reloadDataFromModel();
		}
	}

	private boolean isDataDeletedEvent(AppEvent event) {
		return AppEventConst.DATA_DELETED == event.getType();
	}

	private boolean isModelInitializedEvent(AppEvent event) {
		return AppEventConst.MODEL_INITIALIZED == event.getType();
	}

	private void reloadDataFromModel() {
		addOrDelLines();
		setRowObject();
	}

	@SuppressWarnings("unchecked")
	private void setRowObject() {
		BillManageModel model = (BillManageModel) getModel();
		List<MethodVO> list = (List<MethodVO>) model.getData();
		if (list.size() > 0) {
			billCardPanel.getBillModel().setBodyRowObjectByMetaData(
					list.toArray(new MethodVO[0]), 0);
		}
	}

	@SuppressWarnings("unchecked")
	private void addOrDelLines() {
		BillManageModel model = (BillManageModel) getModel();
		List<MethodVO> list = (List<MethodVO>) model.getData();
		int rowCount = billCardPanel.getRowCount();
		int size = list.size();
		if (size >= rowCount) {
			// 只需要添加size-rowCount行。
			for (int i = 0; i < size - rowCount; i++) {
				billCardPanel.addLine();
			}
		} else {
			// 只需要删除rowCount-size行
			for (int i = 0; i < rowCount - size; i++) {
				billCardPanel.delLine();
			}
		}
	}

	@Override
	protected void synchronizeDataFromModel() {
		try {
			// super.synchronizeDataFromModel();
			Logger.debug("entering synchronizeDataFromModel");
			Object selectedData = ((BillManageModel) getModel())
					.getSelectedData();
			// 这里必须加以处理，即选中行数据为null时,不再调用setValue方法，否则，新增的行又会消失。
			if (selectedData != null) {
				setValue(selectedData);
			}
			Logger.debug("leaving synchronizeDataFromModel");
		} catch (Exception e) {
			Logger.info("在新增状态时，选中的数据的行数大于model中的总行数，这里异常不作处理");
		}
	}

	@Override
	public Object getValue() {
		Object value = super.getValue();
		// 增加时只返回一条数据（主键为空）
		if (isUIStateAdd() && value.getClass().isArray()) {
			MethodVO[] objs = (MethodVO[]) value;
			for (MethodVO vo : objs) {
				if (vo.getPk_method() == null) {
					return vo;
				}
			}
		}
		return value;
	}

	@Override
	protected void beforeGetValue() {
		super.beforeGetValue();
		// 在这里设置那些修改行为修改状态,因为当发生SELECTION_CHANGED事件时，会把所有的行状态设为Normal
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
		super.onAdd();
		reloadDataFromModel();
		billCardPanel.addLine();
	}

	@Override
	protected void onNotEdit() {
		super.onNotEdit();
		reloadDataFromModel();
		// getModel().directlyUpdate(getModel().getSelectedData());
		editRows.clear();
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		if (e.getOldValue() != e.getValue()) {
			int selectedRow = ((BillManageModel) getModel()).getSelectedRow();
			if (!editRows.contains(selectedRow)) {
				editRows.add(selectedRow);
			}
		}
		if (MethodVO.CONTROLAREA.equals(e.getKey())) {
			UIRefPane factorRefPane = (UIRefPane) billCardPanel.getBodyItem(
					MethodVO.FACTOR).getComponent();
			FactorRefModel factorRefModel = (FactorRefModel) factorRefPane
					.getRefModel();
			factorRefModel.setDate(WorkbenchEnvironment.getInstance()
					.getBusiDate().toLocalString());
			String controlArea = (String) billCardPanel.getBillModel()
					.getValueAt(0, MethodVO.CONTROLAREA + IBillItem.ID_SUFFIX);
			factorRefModel.setPk_controlarea(controlArea);
			// 设置为可编辑
			billCardPanel.getBillModel().setCellEditable(
					((BillManageModel) getModel()).getSelectedRow(),
					MethodVO.FACTOR, true);
		}
	}

	@Override
	public void bodyRowChange(BillEditEvent e) {
	}

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		String controlArea = (String) billCardPanel.getBillModel().getValueAt(
				e.getRow(), MethodVO.CONTROLAREA + IBillItem.ID_SUFFIX);
		boolean flag = controlArea != null && !"".equals(controlArea.trim()) ? true
				: false;
		billCardPanel.getBillModel().setCellEditable(e.getRow(),
				MethodVO.FACTOR, flag);
		return true;
	}
}
