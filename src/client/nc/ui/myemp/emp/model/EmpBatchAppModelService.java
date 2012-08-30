package nc.ui.myemp.emp.model;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.emp.IEmpService;
import nc.ui.uif2.model.IBatchAppModelService;
import nc.vo.bd.meta.BatchOperateVO;
import nc.vo.uif2.LoginContext;

public class EmpBatchAppModelService implements IBatchAppModelService {

	@Override
	public Object[] queryByDataVisibilitySetting(LoginContext context)
			throws Exception {
		return null;
	}

	@Override
	public BatchOperateVO batchSave(BatchOperateVO batchVO) throws Exception {
		return getService().batchSave(batchVO);
	}

	private IEmpService getService() {
		return NCLocator.getInstance().lookup(IEmpService.class);
	}

}
