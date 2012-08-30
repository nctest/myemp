package nc.ui.myemp.mng.model;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.mng.IDeptQueryService;
import nc.ui.uif2.IExceptionHandler;
import nc.ui.uif2.model.HierachicalDataAppModel;
import nc.ui.uif2.model.IAppModelDataManagerEx;
import nc.vo.myemp.mng.Dept;
import nc.vo.pub.BusinessException;

public class MngModelDataManager implements IAppModelDataManagerEx {
	private HierachicalDataAppModel model;
	private IExceptionHandler exceptionHandler;

	public HierachicalDataAppModel getModel() {
		return model;
	}

	public void setModel(HierachicalDataAppModel model) {
		this.model = model;
	}

	public IExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void setExceptionHandler(IExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public void initModel() {
		try {
			List<Dept> list = getQueryService().getAll();
			model.initModel(list.toArray(new Dept[list.size()]));
		} catch (BusinessException e) {
			exceptionHandler.handlerExeption(e);
		}
	}

	@Override
	public void refresh() {
		Object obj=model.getSelectedData();
		initModel();
		model.setSelectedData(obj);
	}

	@Override
	public void initModelBySqlWhere(String sqlWhere) {
	}

	@Override
	public void setShowSealDataFlag(boolean showSealDataFlag) {
	}

	private IDeptQueryService getQueryService() {
		return NCLocator.getInstance().lookup(IDeptQueryService.class);
	}

}
