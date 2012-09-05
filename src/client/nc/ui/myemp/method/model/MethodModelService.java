package nc.ui.myemp.method.model;

import java.lang.reflect.Array;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.method.IMethodService;
import nc.ui.uif2.model.IAppModelService;
import nc.vo.myemp.method.MethodVO;
import nc.vo.uif2.LoginContext;

public class MethodModelService implements IAppModelService {

	@Override
	public Object insert(Object object) throws Exception {
		if(object.getClass().isArray()){
			return getService().insert((MethodVO) Array.get(object, 0));
		}
		return null;
	}

	private IMethodService getService() {
		return NCLocator.getInstance().lookup(IMethodService.class);
	}

	@Override
	public Object update(Object object) throws Exception {
		return getService().update((MethodVO) object);
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
