package nc.ui.myemp.method.model;

import nc.bs.logging.Logger;
import nc.ui.uif2.model.BillManageModel;

public class BasisBillManageModel extends BillManageModel {
	@Override
	public Object getSelectedData() {
		try {
			return super.getSelectedData();
		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
		return null;
	}
}
