package nc.ui.myemp.emp.model;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.emp.IEmpQueryService;
import nc.ui.uif2.IExceptionHandler;
import nc.ui.uif2.model.BatchBillTableModel;
import nc.ui.uif2.model.IAppModelDataManager;
import nc.vo.myemp.emp.EmpVO;
import nc.vo.pub.BusinessException;

public class EmpAppModelDataManager implements IAppModelDataManager {
	private BatchBillTableModel model;
	private IExceptionHandler exceptionHandler;

	@Override
	public void initModel() {
		try {
			List<EmpVO> list = getQueryService().getAll();
			
			model.initModel(list.toArray(new EmpVO[list.size()]));
		} catch (BusinessException e) {
			exceptionHandler.handlerExeption(e);
		}
	}

	private IEmpQueryService getQueryService() {
		return NCLocator.getInstance().lookup(IEmpQueryService.class);
	}

	public BatchBillTableModel getModel() {
		return model;
	}

	public void setModel(BatchBillTableModel model) {
		this.model = model;
	}

	public IExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void setExceptionHandler(IExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

}
