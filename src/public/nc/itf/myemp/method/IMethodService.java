package nc.itf.myemp.method;

import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public interface IMethodService {
	/**
	 * 保存
	 * 
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	MethodVO insert(MethodVO vo) throws BusinessException;

	/**
	 * 更新
	 * 
	 * @param vos
	 * @return
	 * @throws BusinessException
	 */
	MethodVO update(MethodVO vo) throws BusinessException;

	/**
	 * 删除
	 * 
	 * @param vo
	 * @throws BusinessException
	 */
	void delete(MethodVO vo) throws BusinessException;

	/**
	 * 通过分摊方法主键删除分摊依据
	 * 
	 * @param pk_method
	 */
	void deleteBasisVOByMethodPk(String pk_method) throws BusinessException;
}
