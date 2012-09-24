package nc.ui.myemp.method.view;

import java.util.List;

import nc.bs.logging.Logger;
import nc.ui.myemp.method.model.MethodBillManageModel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.method.BasisVO;

public class BasisBillForm extends BillForm implements BillEditListener2,
		BillEditListener {

	private static final long serialVersionUID = 2540172321607743066L;
	private MethodBillForm methodForm;

	@Override
	public void initUI() {
		super.initUI();
		billCardPanel.addBodyEditListener2(this);
		billCardPanel.addEditListener(this);
	}

	@Override
	protected void synchronizeDataFromModel() {
		Logger.debug("entering synchronizeDataFromModel");
		@SuppressWarnings("unchecked")
		List<BasisVO> data = ((BillManageModel) getModel()).getData();
		// 如果无数据，还应把以前界面上的数据清空
		billCardPanel.getBillModel().clearBodyData();
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				billCardPanel.addLine();
			}
			billCardPanel.getBillModel().setBodyRowObjectByMetaData(
					data.toArray(new BasisVO[0]), 0);
		}
		billCardPanel.getBillTable().getSelectionModel()
				.setSelectionInterval(0, 0);
		Logger.debug("leaving synchronizeDataFromModel");
	}

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		// 分摊维度不可以编辑
		if (BasisVO.ALLOCDIMEN.equals(e.getKey())) {
			billCardPanel.getBillModel().setCellEditable(e.getRow(),
					e.getKey(), false);
		}
		return true;
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		MethodBillManageModel methodModel = (MethodBillManageModel) methodForm
				.getModel();
		methodForm.addToEditRows(methodModel.getSelectedRow());
	}

	@Override
	public void bodyRowChange(BillEditEvent e) {
	}

	public void setMethodForm(MethodBillForm methodForm) {
		this.methodForm = methodForm;
	}

}
