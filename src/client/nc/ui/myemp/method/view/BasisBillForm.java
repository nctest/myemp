package nc.ui.myemp.method.view;

import java.util.List;

import nc.bs.logging.Logger;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.allocbasis.AllocBasisVO;

public class BasisBillForm extends BillForm implements BillEditListener2 {

	private static final long serialVersionUID = 2540172321607743066L;

	@Override
	public void initUI() {
		super.initUI();
		billCardPanel.addBodyEditListener2(this);
	}
	@Override
	public Object getValue() {
		return billCardPanel.getBillModel().getBodyValueByMetaData();
	}

	@Override
	public void handleEvent(AppEvent event) {
		super.handleEvent(event);
		if (isModelInitializedEvent(event) || isDataDeletedEvent(event)) {
			reloadDataFromModel();
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

	private void reloadDataFromModel() {
		addOrDelLines();
		setRowObject();
	}

	@SuppressWarnings("unchecked")
	private void setRowObject() {
		BillManageModel model = (BillManageModel) getModel();
		List<AllocBasisVO> list = (List<AllocBasisVO>) model.getData();
		if (list.size() > 0) {
			billCardPanel.getBillModel().setBodyRowObjectByMetaData(
					list.toArray(new AllocBasisVO[0]), 0);
		}
	}

	@SuppressWarnings("unchecked")
	private void addOrDelLines() {
		BillManageModel model = (BillManageModel) getModel();
		List<AllocBasisVO> list = (List<AllocBasisVO>) model.getData();
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
			super.synchronizeDataFromModel();
		} catch (Exception e) {
			Logger.info("在新增状态时，选中的数据的行数大于model中的总行数，这里异常不作处理");
		}
	}

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		if (AllocBasisVO.ALLOCDIMEN.equals(e.getKey())) {
			for (int i = 0; i < ((BillManageModel) getModel()).getRowCount(); i++) {
				billCardPanel.getBillModel().setCellEditable(i, e.getKey(),
						false);
			}
		}
		return true;
	}

}
