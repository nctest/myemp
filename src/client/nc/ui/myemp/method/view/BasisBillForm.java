package nc.ui.myemp.method.view;

import nc.bs.logging.Logger;
import nc.ui.uif2.editor.BillForm;

public class BasisBillForm extends BillForm {

	private static final long serialVersionUID = 2540172321607743066L;
	
	@Override
	protected void synchronizeDataFromModel() {
		try {
			super.synchronizeDataFromModel();
		} catch (Exception e) {
			Logger.info("在新增状态时，选中的数据的行数大于model中的总行数，这里异常不作处理");
		}
	}

}
