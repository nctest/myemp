package nc.ui.myemp.method.actions;

import java.awt.event.ActionEvent;

import nc.ui.ml.NCLangRes;
import nc.ui.myemp.method.model.MethodBillManageModel;
import nc.ui.myemp.method.view.MethodBillForm;
import nc.ui.uif2.IShowMsgConstant;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.UIState;
import nc.ui.uif2.actions.EditAction;

@Deprecated
public class MethodEditAction extends EditAction {
	private static final long serialVersionUID = 2682925775711503580L;
	private MethodBillForm methodForm;

	@Override
	public void doAction(ActionEvent e) throws Exception {
		if (checkDataPermission()) {
			getModel().setUiState(UIState.EDIT);
			// 主要增加了以下操作
			methodForm.addToEditRows(((MethodBillManageModel) getModel())
					.getSelectedRow());
		} else {
			ShowStatusBarMsgUtil.showErrorMsg(
					NCLangRes.getInstance().getStrByID("uif2",
							"ExceptionHandlerWithDLG-000000")/* 错误 */,
					IShowMsgConstant.getDataPermissionInfo(), getModel()
							.getContext());
		}
	}

	public MethodBillForm getMethodForm() {
		return methodForm;
	}

	public void setMethodForm(MethodBillForm methodForm) {
		this.methodForm = methodForm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
