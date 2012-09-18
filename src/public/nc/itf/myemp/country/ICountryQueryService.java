package nc.itf.myemp.country;

import java.util.List;

import nc.vo.myemp.country.CountryVO;
import nc.vo.pub.BusinessException;

public interface ICountryQueryService {
	/**
	 * ��ѯ�����е�CountryVO
	 * 
	 * @return
	 * @throws BusinessException
	 */
	List<CountryVO> getAll() throws BusinessException;

	/**
	 * ����sqlWhere��ѯ
	 * 
	 * @param sqlWhere
	 * @return
	 * @throws BusinessException
	 */
	List<CountryVO> getByCondition(String sqlWhere) throws BusinessException;

	/**
	 * �������������ѯ
	 * 
	 * @param pks
	 * @return
	 * @throws BusinessException
	 */
	List<CountryVO> getAllByPks(String[] pks) throws BusinessException;

	/**
	 * ����sqlWhere��ѯ������
	 * 
	 * @param sqlWhere
	 * @return
	 * @throws BusinessException
	 */
	String[] getPksByCondition(String sqlWhere) throws BusinessException;

}
