package nc.impl.myemp.emp;

import java.util.List;

import nc.itf.myemp.emp.IEmpQueryService;
import nc.md.persist.framework.IMDPersistenceQueryService;
import nc.md.persist.framework.MDPersistenceService;
import nc.vo.myemp.emp.EmpVO;
import nc.vo.pub.BusinessException;

public class EmpQueryServiceImpl implements IEmpQueryService {

	@SuppressWarnings("unchecked")
	@Override
	public List<EmpVO> getAll() throws BusinessException {
		return (List<EmpVO>) getQueryService().queryBillOfVOByCond(EmpVO.class,
				"1=1", true);
	}

	private IMDPersistenceQueryService getQueryService() {
		return MDPersistenceService.lookupPersistenceQueryService();
	}

}
