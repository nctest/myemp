package nc.itf.myemp.country;

import java.util.List;

import nc.vo.myemp.country.CountryVO;
import nc.vo.pub.BusinessException;

public interface ICountryQueryService {
	/**
	 * 查询出所有的CountryVO
	 * 
	 * @return
	 * @throws BusinessException
	 */
	List<CountryVO> getAll() throws BusinessException;

	/**
	 * 根据sqlWhere查询
	 * 
	 * @param sqlWhere
	 * @return
	 * @throws BusinessException
	 */
	List<CountryVO> getByCondition(String sqlWhere) throws BusinessException;

	/**
	 * 根据主键数组查询
	 * 
	 * @param pks
	 * @return
	 * @throws BusinessException
	 */
	List<CountryVO> getAllByPks(String[] pks) throws BusinessException;

	/**
	 * 根据sqlWhere查询出主键
	 * 
	 * @param sqlWhere
	 * @return
	 * @throws BusinessException
	 */
	String[] getPksByCondition(String sqlWhere) throws BusinessException;

}
