package nc.ui.myemp.method.model;

import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.method.IBasisService;
import nc.pubitf.resa.factor.IFactorAssPubService;
import nc.ui.uif2.model.IAppModelService;
import nc.vo.bd.accassitem.AccAssItemVO;
import nc.vo.pub.BusinessException;
import nc.vo.resa.factor.FactorAssVO;
import nc.vo.uif2.LoginContext;

public class BasisModelService implements IAppModelService {
	/**
	 * 根据要素主键，查询出FactorAssVO
	 * @param pk_accasoas
	 * @param period
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, List<FactorAssVO>> queryAllByAccPKs(
			String[] pk_accasoas, String period) throws BusinessException {
		return getFactorAssPubService().queryAllByAccPKs(pk_accasoas, period);
	}

	private IFactorAssPubService getFactorAssPubService() {
		return NCLocator.getInstance().lookup(IFactorAssPubService.class);
	}

	@Override
	public Object insert(Object object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object update(Object object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Object object) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] queryByDataVisibilitySetting(LoginContext context)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public AccAssItemVO getAllocDimenVO(String pkAllocDimen) throws BusinessException {
		return getBasisService().getFactorAssVO(pkAllocDimen);
	}

	private IBasisService getBasisService() {
		return NCLocator.getInstance().lookup(IBasisService.class);
	}

}
