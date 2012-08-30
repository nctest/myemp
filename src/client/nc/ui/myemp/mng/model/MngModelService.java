package nc.ui.myemp.mng.model;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.mng.IDeptService;
import nc.ui.uif2.model.IAppModelService;
import nc.vo.myemp.mng.Dept;
import nc.vo.uif2.LoginContext;

public class MngModelService implements IAppModelService {

	@Override
	public Object insert(Object object) throws Exception {
		Dept dept=(Dept)object;
		dept.setD_id(getService().save(dept));
		return dept;
	}

	@Override
	public Object update(Object object) throws Exception {
		getService().update((Dept) object);
		return object;
	}

	@Override
	public void delete(Object object) throws Exception {
		getService().delete((Dept) object);
	}

	@Override
	public Object[] queryByDataVisibilitySetting(LoginContext context)
			throws Exception {
		return null;
	}
	private IDeptService getService() {
		return NCLocator.getInstance().lookup(IDeptService.class);
	}

}
