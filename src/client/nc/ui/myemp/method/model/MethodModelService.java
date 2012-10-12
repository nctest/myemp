package nc.ui.myemp.method.model;

import java.lang.reflect.Array;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.method.IMethodService;
import nc.ui.uif2.model.IAppModelService;
import nc.vo.myemp.method.MethodVO;
import nc.vo.uif2.LoginContext;

public class MethodModelService implements IAppModelService {
	public void deleteBasisVOByMethodPk(String pk_method) throws Exception{
		getService().deleteBasisVOByMethodPk(pk_method);
	}

	@Override
	public Object insert(Object object) throws Exception {
		return getService().insert((MethodVO) object);
	}

	private IMethodService getService() {
		return NCLocator.getInstance().lookup(IMethodService.class);
	}

	@Override
	public Object update(Object object) throws Exception {
		if (object.getClass().isArray()&&Array.getLength(object)>0) {
			return getService().update(((MethodVO[]) object)[0]);
		}
		return null;
	}

	@Override
	public void delete(Object object) throws Exception {
		getService().delete((MethodVO) object);
	}

	@Override
	public Object[] queryByDataVisibilitySetting(LoginContext context)
			throws Exception {
		return null;
	}

}
