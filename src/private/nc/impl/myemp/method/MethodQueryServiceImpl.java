package nc.impl.myemp.method;

import java.util.List;

import nc.itf.myemp.method.IMethodQueryService;
import nc.md.persist.framework.MDPersistenceService;
import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public class MethodQueryServiceImpl implements IMethodQueryService {
	
	/**
	 * 查询出所有的MethodVO
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MethodVO> getAll() throws BusinessException {
		return (List<MethodVO>) MDPersistenceService.lookupPersistenceQueryService()
				.queryBillOfVOByCond(MethodVO.class, "1=1", false);
	}

}
