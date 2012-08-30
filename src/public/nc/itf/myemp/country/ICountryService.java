package nc.itf.myemp.country;

import nc.vo.myemp.country.CountryVO;
import nc.vo.pub.BusinessException;

public interface ICountryService {
	CountryVO insert(CountryVO vo) throws BusinessException;

	CountryVO update(CountryVO vo) throws BusinessException;

	void delete(CountryVO vo) throws BusinessException;
}
