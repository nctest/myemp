package nc.ui.myemp.mng.view;

import nc.ui.uif2.editor.BillForm;
import nc.vo.myemp.mng.Dept;

public class MngBillForm extends BillForm {
	private static final long serialVersionUID = -54765436372627634L;

	@Override
	protected void setDefaultValue() {
		Dept selectedData = (Dept) getModel().getSelectedData();
		if (selectedData != null) {
			getBillCardPanel().getHeadItem(Dept.P_ID).setValue(
					selectedData.getPrimaryKey());
		}
	}
}
