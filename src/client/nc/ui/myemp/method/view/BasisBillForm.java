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
		List<AllocBasisVO> data = ((BillManageModel) getModel()).getData();
		// 如果无数据，还应把以前界面上的数据清空
		billCardPanel.getBillModel().clearBodyData();
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				billCardPanel.addLine();
			}
			billCardPanel.getBillModel().setBodyRowObjectByMetaData(
					data.toArray(new AllocBasisVO[0]), 0);
		}
		Logger.debug("leaving synchronizeDataFromModel");
	}

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		if (AllocBasisVO.ALLOCDIMEN.equals(e.getKey())) {
			billCardPanel.getBillModel().setCellEditable(e.getRow(),
					e.getKey(), false);
		}
		return true;
	}

}
