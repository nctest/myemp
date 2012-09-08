package nc.ui.myemp.method.actions;

import nc.ui.uif2.UIState;
import nc.ui.uif2.actions.OutputAction;

public class MethodOutputAction extends OutputAction {
	private static final long serialVersionUID = -9054102807386057792L;

	@Override
	protected boolean isActionEnable() {
		return getModel().getUiState() != UIState.ADD && super.isActionEnable();
	}
}
