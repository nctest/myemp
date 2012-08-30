package nc.ui.myemp.folder.model;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.folder.IFolderService;
import nc.ui.uif2.model.IAppModelService;
import nc.vo.myemp.folder.FolderVO;
import nc.vo.uif2.LoginContext;

public class FolderAppModelService implements IAppModelService {

	@Override
	public Object insert(Object object) throws Exception {
		return getService().insertVO((FolderVO)object);
	}

	private IFolderService getService() {
		return NCLocator.getInstance().lookup(IFolderService.class);
	}

	@Override
	public Object update(Object object) throws Exception {
		return getService().updateVO((FolderVO) object);
	}

	@Override
	public void delete(Object object) throws Exception {
		getService().deleteVO((FolderVO) object);
	}

	@Override
	public Object[] queryByDataVisibilitySetting(LoginContext context)
			throws Exception {
		return null;
	}

}
