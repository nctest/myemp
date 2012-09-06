package nc.ui.myemp.method.view;

import java.util.List;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillItem;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.method.MethodVO;

public class MethodBillForm extends BillForm implements BillEditListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void initUI() {
		super.initUI();
		BillItem[] bodyItems = billCardPanel.getBodyItems();
		for (BillItem billItem : bodyItems) {
			billItem.getItemEditor().addBillEditListener(this);
		}
	}
	@Override
	public void handleEvent(AppEvent event) {
		super.handleEvent(event);
		if(AppEventConst.MODEL_INITIALIZED==event.getType()){
			BillManageModel model=(BillManageModel) getModel();
			@SuppressWarnings("unchecked")
			List<MethodVO> list = model.getData();
			for (int i = 0; i < list.size(); i++) {
				billCardPanel.addLine();
			}
			billCardPanel.getBillModel().setBodyRowObjectByMetaData(list.toArray(new MethodVO[0]), 0);
		}
	}

	@Override
	protected void onAdd() {
		super.onAdd();
		billCardPanel.addLine();
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		if(MethodVO.CONTROLAREA.equals(e.getKey())){
//			UIRefPane refPane = (UIRefPane) billCardPanel.getBodyItem(MethodVO.CONTROLAREA).getComponent();
//			refPane.getRefName();
			((UIRefPane)e.getSource()).getRefName();
			
		}
	}

	@Override
	public void bodyRowChange(BillEditEvent e) {
		System.out.println("here");
	}
}
