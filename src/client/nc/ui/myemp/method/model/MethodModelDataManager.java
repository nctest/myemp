package nc.ui.myemp.method.model;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.myemp.method.IMethodQueryService;
import nc.ui.uif2.model.BillManageModel;
import nc.ui.uif2.model.IAppModelDataManagerEx;
import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public class MethodModelDataManager implements IAppModelDataManagerEx {
	private BillManageModel model;
	
	/**
	 * ��ʼ��
	 */
	@Override
	public void initModel() {
		try {
			List<MethodVO> list = getQueryService().getAll();
			model.initModel(list.toArray(new MethodVO[list.size()]));
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
		}
	}

	private IMethodQueryService getQueryService() {
		return NCLocator.getInstance().lookup(IMethodQueryService.class);
	}

	@Override
	public void refresh() {
		initModel();
	}

	@Override
	public void initModelBySqlWhere(String sqlWhere) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShowSealDataFlag(boolean showSealDataFlag) {
		// TODO Auto-generated method stub

	}

	public BillManageModel getModel() {
		return model;
	}

	public void setModel(BillManageModel model) {
		this.model = model;
	}

}
