package nc.ui.myemp.mng.view;

import nc.ui.uif2.editor.value.BillCardPanelMetaDataValueAdapter;
import nc.ui.uif2.model.HierachicalDataAppModel;

public class MngComponentValueStrategy extends
		BillCardPanelMetaDataValueAdapter {
	private HierachicalDataAppModel model;

//	@Override
//	public Object getValue() {
//		if (model.getUiState() == UIState.ADD) {
//			return super.getValue();
//		}
//		BillCardPanel panel = (BillCardPanel) getComponent();
//		int rowCount = panel.getBillModel().getRowCount();
//		System.out.println(rowCount);
//		// ¿¨Æ¬Õû´æÕûÉ¾
//		
//		return panel.getBillData().getBillObjectByMetaData();
//	}

	public HierachicalDataAppModel getModel() {
		return model;
	}

	public void setModel(HierachicalDataAppModel model) {
		this.model = model;
	}

}
