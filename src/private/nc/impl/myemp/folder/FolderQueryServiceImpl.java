package nc.impl.myemp.folder;

import java.util.List;

import nc.itf.myemp.folder.IFolderQueryService;
import nc.md.persist.framework.IMDPersistenceQueryService;
import nc.md.persist.framework.MDPersistenceService;
import nc.vo.myemp.folder.FolderVO;
import nc.vo.pub.BusinessException;

public class FolderQueryServiceImpl implements IFolderQueryService {

	@SuppressWarnings("unchecked")
	@Override
	public List<FolderVO> getAll() throws BusinessException {
		return (List<FolderVO>) getQueryService().queryBillOfVOByCond(
				FolderVO.class, "1=1", false);
	}

	private IMDPersistenceQueryService getQueryService() {
		return MDPersistenceService.lookupPersistenceQueryService();
	}

}
