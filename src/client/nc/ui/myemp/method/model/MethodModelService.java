package nc.ui.myemp.method.model;

import java.util.Arrays;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.method.IMethodService;
import nc.ui.uif2.model.IAppModelService;
import nc.vo.myemp.method.MethodVO;
import nc.vo.uif2.LoginContext;

public class MethodModelService implements IAppModelService {

	@Override
	public Object insert(Object object) throws Exception {
		return getService().insert((MethodVO) object);
	}

	@SuppressWarnings("unused")
	private List<MethodVO> toList(Object object) {
		MethodVO[] vos = new MethodVO[] { (MethodVO) object };
		List<MethodVO> list = (List<MethodVO>) Arrays.asList(vos);
		return list;
	}

	private IMethodService getService() {
		return NCLocator.getInstance().lookup(IMethodService.class);
	}

	@Override
	public Object update(Object object) throws Exception {
		return getService().update(((MethodVO[]) object)[0]);
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
