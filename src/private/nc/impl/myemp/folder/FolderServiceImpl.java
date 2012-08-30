package nc.impl.myemp.folder;

import nc.bs.dao.BaseDAO;
import nc.itf.myemp.folder.IFolderService;
import nc.vo.myemp.folder.FolderVO;
import nc.vo.pub.BusinessException;

public class FolderServiceImpl implements IFolderService {
	private BaseDAO dao = new BaseDAO();

	@Override
	public FolderVO insertVO(FolderVO folderVO) throws BusinessException {
		String pk = dao.insertVO(folderVO);
		folderVO.setId(pk);
		return folderVO;
	}

	@Override
	public FolderVO updateVO(FolderVO folderVO) throws BusinessException {
		dao.updateVO(folderVO);
		return folderVO;
	}

	@Override
	public void deleteVO(FolderVO folderVO) throws BusinessException {
		dao.deleteVO(folderVO);
	}
}
