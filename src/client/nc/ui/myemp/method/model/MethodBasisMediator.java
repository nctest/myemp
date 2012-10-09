package nc.ui.myemp.method.model;

import java.lang.reflect.Array;

import nc.ui.myemp.method.event.MethodAppEventConst;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.AppEventListener;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.method.MethodVO;

/**
 * 分摊方法与分摊依据模型适配器，主要用于两个模型之间的事件适配与派发
 * 
 */
public class MethodBasisMediator implements AppEventListener {
	private MethodBillManageModel methodModel;
	private BasisBillManageModel basisModel;

	@Override
	public void handleEvent(AppEvent event) {
		if (isSelectionChangedEvent(event) || isModelInitializedEvent(event)) {
			doSelectedChanged(event);
		} else if (isUIStateEvent(event)) {
			doUIStateChanged();
		} else if (isFactorChangeEvent(event)) {
			doFactorChanged(event);
		} else if (isSelectMethodVOEvent(event)) {
			doSelectMethodVO(event);
		}
	}

	private boolean isModelInitializedEvent(AppEvent event) {
		return event.getType() == AppEventConst.MODEL_INITIALIZED;
	}

	private boolean isSelectionChangedEvent(AppEvent event) {
		return event.getType() == AppEventConst.SELECTION_CHANGED;
	}

	private boolean isSelectMethodVOEvent(AppEvent event) {
		return MethodAppEventConst.SELECT_METHODVO.toString().equals(
				event.getType());
	}

	private boolean isFactorChangeEvent(AppEvent event) {
		return MethodAppEventConst.FACTOR_CHANGE.toString().equals(
				event.getType());
	}

	private boolean isUIStateEvent(AppEvent event) {
		return event.getType() == AppEventConst.UISTATE_CHANGED;
	}

	/**
	 * 当发生SELECT_METHODVO事件时，调用该方法
	 * 
	 * @param event
	 */
	private void doSelectMethodVO(AppEvent event) {
		MethodVO vo = (MethodVO) event.getSource();
		if (vo == null) {
			basisModel.initModel(null);
		} else if (vo.getBases() != null) {
			basisModel.initModel(vo.getBases());
		} else {
			basisModel.initByFactorPK(vo.getFactor());
		}
	}

	private void doUIStateChanged() {
		basisModel.setUiState(methodModel.getUiState());
	}

	/**
	 * 当要素的值发生改变时，调用该方法，根据新的要素主键时来初始化分摊依据界面
	 * 
	 * @param event
	 */
	private void doFactorChanged(AppEvent event) {
		ValueChangedEvent e = (ValueChangedEvent) event.getContextObject();
		// 得到新的要素主键值
		Object factorPks = e.getNewValue();
		if (String[].class.isInstance(factorPks)
				&& Array.getLength(factorPks) > 0) {
			basisModel.initByFactorPK((String) Array.get(factorPks, 0));
		}
	}

	/**
	 * 当发生选择行改变时，调用该方法来初始化分摊依据界面
	 * 
	 * @param event
	 */
	private void doSelectedChanged(AppEvent event) {
		BillManageModel methodModel = (BillManageModel) event.getSource();
		MethodVO selectedData = (MethodVO) methodModel.getSelectedData();
		if (selectedData == null) {
			return;
		}
		basisModel.initByFactorPK(selectedData.getFactor());
	}

	public MethodBillManageModel getMethodModel() {
		return methodModel;
	}

	public void setMethodModel(MethodBillManageModel methodModel) {
		this.methodModel = methodModel;
		methodModel.addAppEventListener(this);
	}

	public BasisBillManageModel getBasisModel() {
		return basisModel;
	}

	public void setBasisModel(BasisBillManageModel basisModel) {
		this.basisModel = basisModel;
	}

}
