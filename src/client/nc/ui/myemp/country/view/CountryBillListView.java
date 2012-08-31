package nc.ui.myemp.country.view;

import nc.ui.uif2.components.AutoShowUpEventSource;
import nc.ui.uif2.components.IAutoShowUpComponent;
import nc.ui.uif2.components.IAutoShowUpEventListener;
import nc.ui.uif2.components.ITabbedPaneAwareComponent;
import nc.ui.uif2.components.ITabbedPaneAwareComponentListener;
import nc.ui.uif2.components.TabbedPaneAwareCompnonetDelegate;
import nc.ui.uif2.editor.BillListView;

public class CountryBillListView extends BillListView implements
		ITabbedPaneAwareComponent, IAutoShowUpComponent {

	private static final long serialVersionUID = -309087606484671890L;
	private IAutoShowUpComponent autoShowUpComponent;
	private ITabbedPaneAwareComponent tabbedPaneAwareComponent;

	public CountryBillListView() {
		super();
		autoShowUpComponent = new AutoShowUpEventSource(this);
		tabbedPaneAwareComponent = new TabbedPaneAwareCompnonetDelegate();
	}

	@Override
	public boolean canBeHidden() {
//		return true;
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

	@Override
	public void setAutoShowUpEventListener(IAutoShowUpEventListener l) {
		autoShowUpComponent.setAutoShowUpEventListener(l);
	}

	@Override
	public void showMeUp() {
		autoShowUpComponent.showMeUp();
	}

}
