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
			Logger.info("������״̬ʱ��ѡ�е����ݵ���������model�е��������������쳣��������");
		}
		return null;
	}
}
