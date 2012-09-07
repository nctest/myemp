package nc.ui.myemp.method.view;

import java.util.List;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillItem;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.UIState;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.method.MethodVO;

public class MethodBillForm extends BillForm implements BillEditListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void initUI() {
		super.initUI();
		billCardPanel.setBodyAutoAddLine(false);
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
		getModel().directlyUpdate(getModel().getSelectedData());
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		if (MethodVO.CONTROLAREA.equals(e.getKey())) {
			// UIRefPane refPane = (UIRefPane)
			// billCardPanel.getBodyItem(MethodVO.CONTROLAREA).getComponent();
			// refPane.getRefName();
			((UIRefPane) e.getSource()).getRefName();

		}
	}

	@Override
	public void bodyRowChange(BillEditEvent e) {
		System.out.println("here");
	}
}
