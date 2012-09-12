package nc.ui.myemp.method.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.AppEventListener;
import nc.ui.uif2.UIState;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.allocbasis.AllocBasisVO;
import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.resa.factor.FactorAssVO;

public class MethodBasisMediator implements AppEventListener {
	private BillManageModel methodModel;
	private BillManageModel basisModel;

	@Override
	public void handleEvent(AppEvent event) {
		if (event.getType() == AppEventConst.SELECTION_CHANGED
				|| event.getType() == AppEventConst.MODEL_INITIALIZED) {
			doSelectedChanged(event);
		} else if (event.getType() == AppEventConst.UISTATE_CHANGED) {
			if (methodModel.getUiState() == UIState.ADD) {
				basisModel.setUiState(UIState.ADD);
			} else if (methodModel.getUiState() == UIState.EDIT) {
				basisModel.setUiState(UIState.EDIT);
			} else {
				basisModel.setUiState(UIState.NOT_EDIT);
			}
		} else if ("Factor_Changed".equals(event.getType())) {
			doFactorChanged(event);
		}
	}

	private void doFactorChanged(AppEvent event) {
		BillEditEvent e = (BillEditEvent) event.getContextObject();
		Object factorPks = e.getValue();
		if (String[].class.isInstance(factorPks)
				&& Array.getLength(factorPks) > 0) {
			initBasisModelByFactorPK((String) Array.get(factorPks, 0));
		}
	}

	private void doSelectedChanged(AppEvent event) {
		BillManageModel methodModel = (BillManageModel) event.getSource();
		MethodVO selectedData = (MethodVO) methodModel.getSelectedData();
		// FIXME MODEL_INITIALIZED ±£¨selectedData»‘»ª∑µªÿnull
		if (selectedData == null) {
			return;
		}
		initBasisModelByFactorPK(selectedData.getFactor());
	}

	private void initBasisModelByFactorPK(String pk_factor) {
		try {
			Map<String, List<FactorAssVO>> map = ((BasisModelService) basisModel
					.getService()).queryAllByAccPKs(new String[] { pk_factor },
					"0000-00-00");
			List<FactorAssVO> list = map.get(pk_factor);
			if (list != null) {
				List<AllocBasisVO> basisVOs = new ArrayList<AllocBasisVO>(
						list.size());
				for (int i = 0; i < list.size(); i++) {
					AllocBasisVO basisVO = new AllocBasisVO();
					basisVO.setSelected(UFBoolean.FALSE);
					basisVO.setAllocdimen(list.get(i).getPk_entity());
					basisVOs.add(basisVO);
				}
				basisModel.initModel(basisVOs.toArray(new AllocBasisVO[0]));
			} else {
				basisModel.initModel(null);
			}
		} catch (BusinessException e) {
			Logger.debug(e.getMessage());
			throw new BusinessRuntimeException(e.getMessage(), e);
		}
	}

	public BillManageModel getMethodModel() {
		return methodModel;
	}

	public void setMethodModel(BillManageModel methodModel) {
		this.methodModel = methodModel;
		methodModel.addAppEventListener(this);
	}

	public BillManageModel getBasisModel() {
		return basisModel;
	}

	public void setBasisModel(BillManageModel basisModel) {
		this.basisModel = basisModel;
	}

}
