package nc.ui.myemp.method.model;

import nc.ui.uif2.AppEvent;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.BillManageModel;
import nc.ui.uif2.model.RowOperationInfo;
import nc.vo.myemp.method.MethodVO;

public class MethodBillManageModel extends BillManageModel {
	final String SELECT_METHODVO = "SELECT_METHODVO";

	private int bodySelectedRow = -1;

	@Override
	public Object getSelectedData() {
		if (bodySelectedRow == -1 || getData() == null || getData().size() == 0) {
			return null;
		} else {
			return getData().get(bodySelectedRow);
		}
	}

	@Override
	public void directlyDelete(Object obj) throws Exception {
		if (obj == null)
			return;

		int index = findBusinessData(obj);
		getData().remove(obj);
		datapks.remove(index);

		boolean isDeleteSelectedData = false;
		// 改用bodySelectedRow
		if (index == this.bodySelectedRow) {// 避免删除选中行，在发送删除事件时，有监听器获取当前选中行出错。
			bodySelectedRow = -1;
			isDeleteSelectedData = true;
		}
		fireEvent(new AppEvent(AppEventConst.DATA_DELETED, this,
				new RowOperationInfo(index, obj)));
		if (isDeleteSelectedData)
			setSelectedRow(Math.min(index, getData().size() - 1));
	}

	public void setMethodVO(MethodVO vo) {
		fireEvent(new AppEvent(SELECT_METHODVO, vo, null));
		bodySelectedRow = getData().indexOf(vo);
	}

	@Override
	public int getSelectedRow() {
		return bodySelectedRow;
	}

	public void setBodySelectedRow(int bodySelectedRow) {
		this.bodySelectedRow = bodySelectedRow;
	}

}
