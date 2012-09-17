package nc.ui.myemp.method.model;

import nc.bs.logging.Logger;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.method.MethodVO;

public class MethodBillManageModel extends BillManageModel {
	
	public static String SELECT_METHODVO="select_methodvo";
	public void setMethodVO(MethodVO methodvo){
		fireEvent(new AppEvent(SELECT_METHODVO,methodvo,null));
	}
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
