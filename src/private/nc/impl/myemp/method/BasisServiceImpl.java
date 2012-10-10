package nc.impl.myemp.method;

import nc.bs.dao.BaseDAO;
import nc.itf.myemp.method.IBasisService;
import nc.vo.bd.accassitem.AccAssItemVO;
import nc.vo.pub.BusinessException;

public class BasisServiceImpl implements IBasisService {

	@Override
	public AccAssItemVO getAccAssItemVO(String pk_allocDimen)
			throws BusinessException {
		return (AccAssItemVO) new BaseDAO().retrieveByPK(AccAssItemVO.class, pk_allocDimen);
	}

}
