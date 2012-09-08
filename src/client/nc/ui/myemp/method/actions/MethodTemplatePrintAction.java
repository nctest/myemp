package nc.ui.myemp.method.actions;

import nc.ui.uif2.UIState;
import nc.ui.uif2.actions.TemplatePrintAction;

public class MethodTemplatePrintAction extends TemplatePrintAction {
	private static final long serialVersionUID = 758588415414315333L;

	@Override
	protected boolean isActionEnable() {
		return getModel().getUiState() != UIState.ADD && super.isActionEnable();
	}
}
