package nc.itf.myemp.method;

import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public interface IMethodService {
	MethodVO insert(MethodVO vo) throws BusinessException;

	MethodVO update(MethodVO vo) throws BusinessException;

	void delete(MethodVO vo) throws BusinessException;
}
