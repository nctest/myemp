package nc.impl.myemp.country;

import nc.bs.bd.baseservice.ManageTypeBaseService;
import nc.itf.myemp.country.ICountryService;
import nc.vo.myemp.country.CountryVO;
import nc.vo.pub.BusinessException;

@SuppressWarnings("deprecation")
public class CountryServiceImpl extends ManageTypeBaseService<CountryVO>
		implements ICountryService {

	public CountryServiceImpl() {
		super("b53a11c0-da23-43a4-983a-4817485648e3", CountryVO.class, null);
	}

	public CountryServiceImpl(String MDId, Class<?> entityClass,
			String[] subAttributeNames) {
		super(MDId, entityClass, subAttributeNames);
	}

	@Override
	public CountryVO insert(CountryVO vo) throws BusinessException {
		return insertVO(vo);
	}

	@Override
	public CountryVO update(CountryVO vo) throws BusinessException {
		return updateVO(vo);
	}

	@Override
	public void delete(CountryVO vo) throws BusinessException {
		deleteVO(vo);
	}

}
