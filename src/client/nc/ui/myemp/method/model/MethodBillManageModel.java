package nc.ui.myemp.method.model;

import nc.bs.logging.Logger;
import nc.ui.uif2.model.BillManageModel;

public class MethodBillManageModel extends BillManageModel {
	@Override
	public Object getSelectedData() {
		try {
			return super.getSelectedData();
		} catch (Exception e) {
			Logger.debug(e.getMessage());
			Logger.info("在新增状态时，选中的数据的行数大于model中的总行数，这里异常不作处理");
		}
		return null;
	}
}
