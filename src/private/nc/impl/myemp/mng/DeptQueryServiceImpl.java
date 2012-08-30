package nc.impl.myemp.mng;

import java.util.List;

import nc.itf.myemp.mng.IDeptQueryService;
import nc.md.persist.framework.IMDPersistenceQueryService;
import nc.md.persist.framework.MDPersistenceService;
import nc.vo.myemp.mng.Dept;
import nc.vo.pub.BusinessException;

public class DeptQueryServiceImpl implements IDeptQueryService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Dept> getAll() throws BusinessException {
		return (List<Dept>) getQueryService().queryBillOfVOByCond(Dept.class,
				"1=1", false);
	}

	private IMDPersistenceQueryService getQueryService() {
		return MDPersistenceService.lookupPersistenceQueryService();
	}

}
