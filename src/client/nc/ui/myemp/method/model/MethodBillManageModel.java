package nc.ui.myemp.method.model;

import nc.ui.myemp.method.event.MethodAppEventConst;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.BillManageModel;
import nc.ui.uif2.model.RowOperationInfo;
import nc.vo.myemp.method.MethodVO;

public class MethodBillManageModel extends BillManageModel {
	private int bodySelectedRow = -1;
	private boolean isEditFactorChanged;

	@Override
	public Object update(Object object) throws Exception {
		//增加了下面这个方法，做一些处理
		beforeUpdate(object);
		Object obj = getService().update(object);
		directlyUpdate(obj);
		return obj;
	}

	private void beforeUpdate(Object object) throws Exception {
		if(isEditFactorChanged){
			String pk_method = (((MethodVO[])object)[0]).getPk_method();
			((MethodModelService)getService()).deleteBasisVOByMethodPk(pk_method);
			isEditFactorChanged=false;
		}
	}

	/**
	 * 覆盖该方法，主要是替换为用bodySelectedRow
	 */
	@Override
	public Object getSelectedData() {
		if (bodySelectedRow == -1 || getData() == null || getData().size() == 0) {
			return null;
		} else {
			return getData().get(bodySelectedRow);
		}
	}

	/**
	 * 覆盖该方法，主要是替换为用bodySelectedRow
	 */
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

	/**
	 * 在这里触发SELECT_METHODVO事件，并且用bodySelectedRow存放当前选中行
	 * 
	 * @param vo
	 */
	public void setMethodVO(MethodVO vo) {
		fireEvent(new AppEvent(MethodAppEventConst.SELECT_METHODVO.toString(),
				vo, null));
		bodySelectedRow = getData().indexOf(vo);
	}

	/**
	 * 返回当前选中行
	 */
	@Override
	public int getSelectedRow() {
		return bodySelectedRow;
	}

	public void setBodySelectedRow(int bodySelectedRow) {
		this.bodySelectedRow = bodySelectedRow;
	}

	public boolean isEditFactorChanged() {
		return isEditFactorChanged;
	}

	public void setEditFactorChanged(boolean isEditFactorChanged) {
		this.isEditFactorChanged = isEditFactorChanged;
	}

	public int getBodySelectedRow() {
		return bodySelectedRow;
	}

}
