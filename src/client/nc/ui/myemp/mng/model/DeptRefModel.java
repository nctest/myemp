package nc.ui.myemp.mng.model;

import nc.ui.bd.ref.AbstractRefTreeModel;
import nc.vo.myemp.mng.Dept;

public class DeptRefModel extends AbstractRefTreeModel {
	public DeptRefModel() {
		reset();
	}

	@Override
	public void reset() {
		setRefTitle("����Ա������");
		setRefNodeName("����Ա������");
		setFieldCode(new String[] { Dept.CODE, Dept.NAME });
		setFieldName(new String[] { "���ű���", "��������" });
		setHiddenFieldCode(new String[] { Dept.D_ID, Dept.P_ID });
		setDefaultFieldCount(2);
		setPkFieldCode(Dept.D_ID);
		setFatherField(Dept.P_ID);
		setChildField(Dept.D_ID);
		setRefNameField(Dept.NAME);
		setRefCodeField(Dept.CODE);
		setTableName(Dept.getDefaultTableName());
		resetFieldName();
	}

}
