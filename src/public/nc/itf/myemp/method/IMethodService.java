package nc.itf.myemp.method;

import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public interface IMethodService {
	/**
	 * ����
	 * 
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	MethodVO insert(MethodVO vo) throws BusinessException;

	/**
	 * ����
	 * 
	 * @param vos
	 * @return
	 * @throws BusinessException
	 */
	MethodVO update(MethodVO vo) throws BusinessException;

	/**
	 * ɾ��
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	void delete(MethodVO vo) throws BusinessException;

	/**
	 * ͨ����̯��������ɾ����̯����
	 * 
	 * @param pk_method
	 */
	void deleteBasisVOByMethodPk(String pk_method) throws BusinessException;
}
