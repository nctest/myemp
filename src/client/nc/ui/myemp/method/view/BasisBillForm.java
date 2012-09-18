package nc.ui.myemp.method.view;

import java.util.List;

import nc.bs.logging.Logger;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.uif2.editor.BillForm;
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
		billCardPanel.getBillTable().getSelectionModel()
				.setSelectionInterval(0, 0);
		Logger.debug("leaving synchronizeDataFromModel");
	}

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		//分摊维度不可以编辑
		if (AllocBasisVO.ALLOCDIMEN.equals(e.getKey())) {
			billCardPanel.getBillModel().setCellEditable(e.getRow(),
					e.getKey(), false);
		}
		return true;
	}

}
