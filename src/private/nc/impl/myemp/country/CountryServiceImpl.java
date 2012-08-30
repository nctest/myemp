package nc.impl.myemp.country;

import nc.bs.bd.baseservice.ManageTypeBaseService;
import nc.itf.myemp.country.ICountryService;
import nc.vo.myemp.country.CountryVO;
import nc.vo.pub.BusinessException;

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
		// FIXME just for test,need to delete
		String name = vo.getName();
		for (int i = 0; i < 300; i++) {
			vo.setName(name+i);
			if (vo.getPk_country() != null) {
				vo.setPk_country(null);
			}
			insertVO(vo);
			vo.setName(name);
		}
		vo.setPk_country(null);
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
