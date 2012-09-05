package nc.ui.myemp.method.model;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.myemp.method.IMethodQueryService;
import nc.ui.uif2.model.BillManageModel;
import nc.ui.uif2.model.IAppModelDataManagerEx;
import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;

public class MethodModelDataManager implements IAppModelDataManagerEx {
	private BillManageModel model;

	@Override
	public void initModel() {
		try {
			List<MethodVO> list = getQueryService().getAll();
			model.initModel(list.toArray(new MethodVO[list.size()]));
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessRuntimeException(e.getMessage(), e);
		}
	}

	private IMethodQueryService getQueryService() {
		return NCLocator.getInstance().lookup(IMethodQueryService.class);
	}

	@Override
	public void refresh() {
		int selectedRow = model.getSelectedRow();
		initModel();
		if (selectedRow > 0) {
			model.setSelectedRow(selectedRow);
		}

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
