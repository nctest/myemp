package nc.impl.myemp.mng;

import nc.itf.myemp.mng.IDeptService;
import nc.md.persist.framework.IMDPersistenceService;
import nc.md.persist.framework.MDPersistenceService;
import nc.vo.myemp.mng.Dept;
import nc.vo.pub.BusinessException;

public class DeptServiceImpl implements IDeptService {
	private IMDPersistenceService persistenceService = getPersistenceService();
	@Override
	public String save(Dept dept) throws BusinessException {
		return persistenceService.saveBill(dept);
	}

	@Override
	public void update(Dept dept) throws BusinessException {
		persistenceService.saveBill(dept);
		
	}

	@Override
	public void delete(Dept dept) throws BusinessException {
		persistenceService.deleteBillFromDB(dept);

	}

	private IMDPersistenceService getPersistenceService() {
		return MDPersistenceService.lookupPersistenceService();
	}

}
