package nc.itf.myemp.method;

import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public interface IMethodService {
	/**
	 * ±£´æ
	 * 
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	MethodVO insert(MethodVO vo) throws BusinessException;

	/**
	 * ¸üÐÂ
	 * 
	 * @param vos
	 * @return
	 * @throws BusinessException
	 */
	MethodVO[] update(MethodVO... vos) throws BusinessException;

	/**
	 * É¾³ý
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	void delete(MethodVO vo) throws BusinessException;
}
