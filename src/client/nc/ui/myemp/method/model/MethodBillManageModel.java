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
		//���������������������һЩ����
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
	 * ���Ǹ÷�������Ҫ���滻Ϊ��bodySelectedRow
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
	 * ���Ǹ÷�������Ҫ���滻Ϊ��bodySelectedRow
	 */
	@Override
	public void directlyDelete(Object obj) throws Exception {
		if (obj == null)
			return;

		int index = findBusinessData(obj);
		getData().remove(obj);
		datapks.remove(index);

		boolean isDeleteSelectedData = false;
		// ����bodySelectedRow
		if (index == this.bodySelectedRow) {// ����ɾ��ѡ���У��ڷ���ɾ���¼�ʱ���м�������ȡ��ǰѡ���г���
			bodySelectedRow = -1;
			isDeleteSelectedData = true;
		}
		fireEvent(new AppEvent(AppEventConst.DATA_DELETED, this,
				new RowOperationInfo(index, obj)));
		if (isDeleteSelectedData)
			setSelectedRow(Math.min(index, getData().size() - 1));
	}

	/**
	 * �����ﴥ��SELECT_METHODVO�¼���������bodySelectedRow��ŵ�ǰѡ����
	 * 
	 * @param vo
	 */
	public void setMethodVO(MethodVO vo) {
		fireEvent(new AppEvent(MethodAppEventConst.SELECT_METHODVO.toString(),
				vo, null));
		bodySelectedRow = getData().indexOf(vo);
	}

	/**
	 * ���ص�ǰѡ����
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
