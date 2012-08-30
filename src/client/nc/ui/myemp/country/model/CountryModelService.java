package nc.ui.myemp.country.model;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.country.ICountryQueryService;
import nc.itf.myemp.country.ICountryService;
import nc.ui.uif2.components.pagination.IPaginationQueryService;
import nc.ui.uif2.model.IAppModelService;
import nc.vo.myemp.country.CountryVO;
import nc.vo.pub.BusinessException;
import nc.vo.uif2.LoginContext;

public class CountryModelService implements IAppModelService,
		IPaginationQueryService {

	@Override
	public Object insert(Object object) throws Exception {
		return getService().insert((CountryVO) object);
	}

	@Override
	public Object update(Object object) throws Exception {
		return getService().update((CountryVO) object);
	}

	@Override
	public void delete(Object object) throws Exception {
		getService().delete((CountryVO) object);
	}

	@Override
	public Object[] queryByDataVisibilitySetting(LoginContext context)
			throws Exception {
		return null;
	}

	@Override
	public Object[] queryObjectByPks(String[] pks) throws BusinessException {
		List<CountryVO> list = getQueryService().getAllByPks(pks);
		return list.toArray(new CountryVO[list.size()]);
	}

	private ICountryService getService() {
		return NCLocator.getInstance().lookup(ICountryService.class);
	}

	private ICountryQueryService getQueryService() {
		return NCLocator.getInstance().lookup(ICountryQueryService.class);
	}

}
