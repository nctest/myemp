package nc.itf.myemp.method;

import nc.vo.bd.accassitem.AccAssItemVO;
import nc.vo.pub.BusinessException;

public interface IBasisService {
	AccAssItemVO getAccAssItemVO(String pk_allocDimen) throws BusinessException;
}
