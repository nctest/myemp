package nc.itf.myemp.country;

import java.util.List;

import nc.vo.myemp.country.CountryVO;
import nc.vo.pub.BusinessException;

public interface ICountryQueryService {
	List<CountryVO> getAll() throws BusinessException;

	List<CountryVO> getByCondition(String sqlWhere) throws BusinessException;

	List<CountryVO> getAllByPks(String[] pks) throws BusinessException;

	String[] getPksByCondition(String sqlWhere) throws BusinessException;

}
