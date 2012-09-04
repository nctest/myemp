package nc.ui.myemp.method.view;

import nc.ui.uif2.editor.BillForm;

public class MethodBillForm extends BillForm {
	private static final long serialVersionUID = 1L;

	@Override
	protected void onAdd() {
		super.onAdd();
		billCardPanel.addLine();
	}
}
