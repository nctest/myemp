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
			Logger.info("������״̬ʱ��ѡ�е����ݵ���������model�е��������������쳣��������");
		}
	}

}
