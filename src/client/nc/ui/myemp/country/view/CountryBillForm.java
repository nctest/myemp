package nc.ui.myemp.country.view;

import nc.ui.uif2.AppEvent;
import nc.ui.uif2.UIState;
import nc.ui.uif2.components.AutoShowUpEventSource;
import nc.ui.uif2.components.IAutoShowUpComponent;
import nc.ui.uif2.components.IAutoShowUpEventListener;
import nc.ui.uif2.components.ITabbedPaneAwareComponent;
import nc.ui.uif2.components.ITabbedPaneAwareComponentListener;
import nc.ui.uif2.components.TabbedPaneAwareCompnonetDelegate;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.AppEventConst;

public class CountryBillForm extends BillForm implements
		ITabbedPaneAwareComponent, IAutoShowUpComponent {

	private static final long serialVersionUID = -94791644961909290L;
	private IAutoShowUpComponent autoShowUpComponent;
	private ITabbedPaneAwareComponent tabbedPaneAwareComponent;

	public CountryBillForm() {
		super();
		autoShowUpComponent = new AutoShowUpEventSource(this);
		tabbedPaneAwareComponent = new TabbedPaneAwareCompnonetDelegate();
	}

	@Override
	public void handleEvent(AppEvent event) {
		if (isUiStateChangedAndUiStateAddOrEdit(event)
				|| isEventTypeShowEditor(event)) {
			showMeUp();
		}
		super.handleEvent(event);
	}

	private boolean isEventTypeShowEditor(AppEvent event) {
		return AppEventConst.SHOW_EDITOR == event.getType();
	}

	private boolean isUiStateChangedAndUiStateAddOrEdit(AppEvent event) {
		return isEventTypeUiStateChanged(event)
				&& (isUiStateAdd() || isUiStateEdit());
	}

	private boolean isUiStateEdit() {
		return getModel().getUiState() == UIState.EDIT;
	}

	private boolean isUiStateAdd() {
		return getModel().getUiState() == UIState.ADD;
	}

	private boolean isEventTypeUiStateChanged(AppEvent event) {
		return AppEventConst.UISTATE_CHANGED == event.getType();
	}

	@Override
	public void setAutoShowUpEventListener(IAutoShowUpEventListener l) {
		autoShowUpComponent.setAutoShowUpEventListener(l);
	}

	@Override
	public void showMeUp() {
		autoShowUpComponent.showMeUp();
	}

	@Override
	public boolean canBeHidden() {
		return tabbedPaneAwareComponent.canBeHidden();
	}

	@Override
	public void setComponentVisible(boolean visible) {
		tabbedPaneAwareComponent.setComponentVisible(visible);
	}

	@Override
	public boolean isComponentVisible() {
		return tabbedPaneAwareComponent.isComponentVisible();
	}

	@Override
	public void addTabbedPaneAwareComponentListener(
			ITabbedPaneAwareComponentListener l) {
		tabbedPaneAwareComponent.addTabbedPaneAwareComponentListener(l);
	}

}
