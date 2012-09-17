package nc.ui.myemp.method.model;

import nc.bs.logging.Logger;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.method.MethodVO;

public class MethodBillManageModel extends BillManageModel {
	final String SELECT_METHODVO = "SELECT_METHODVO";
	private int selectedRow;

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

	public void setMethodVO(MethodVO vo) {
		fireEvent(new AppEvent(SELECT_METHODVO, vo, null));
		selectedRow = getData().indexOf(vo);
	}

	@Override
	public int getSelectedRow() {
		return selectedRow;
	}
	
}
