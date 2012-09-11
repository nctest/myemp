package nc.ui.myemp.method.model;

import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.pubitf.resa.factor.IFactorAssPubService;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.AppEventListener;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.allocbasis.AllocBasisVO;
import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.resa.factor.FactorAssVO;

public class MethodBasisMediator implements AppEventListener {
	private BillManageModel methodModel;
	private BillManageModel basisModel;

	@Override
	public void handleEvent(AppEvent event) {
		if (event.getType() == AppEventConst.SELECTION_CHANGED) {
			BillManageModel methodModel = (BillManageModel) event.getSource();
			MethodVO selectedData=null;
			try {
				selectedData = (MethodVO) methodModel.getSelectedData();
			} catch (Exception e1) {
				Logger.info("在新增状态时，选中的数据的行数大于model中的总行数，这里异常不作处理");
			}
			if (selectedData != null) {
				String pk_factor = selectedData.getFactor();
				try {
					Map<String, List<FactorAssVO>> map = getFactorAssPubService()
							.queryAllByAccPKs(new String[] { pk_factor }, "");
					// FIXME
					List<FactorAssVO> list = map.get(pk_factor);
					if (list != null) {
						basisModel.initModel(list.toArray(new AllocBasisVO[0]));
					}
				} catch (BusinessException e) {
					Logger.debug(e.getMessage());
					throw new BusinessRuntimeException(e.getMessage(), e);
				}
			}
		}
	}

	private IFactorAssPubService getFactorAssPubService() {
		return NCLocator.getInstance().lookup(IFactorAssPubService.class);
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
