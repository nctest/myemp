package nc.ui.myemp.method.actions;

import nc.ui.uif2.UIState;
import nc.ui.uif2.actions.TemplatePreviewAction;

public class MethodTemplatePreviewAction extends TemplatePreviewAction {
	private static final long serialVersionUID = -9150991744608039863L;

	@Override
	protected boolean isActionEnable() {
		return getModel().getUiState() != UIState.ADD && super.isActionEnable();
	}
}
