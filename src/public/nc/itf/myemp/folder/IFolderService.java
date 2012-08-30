package nc.itf.myemp.folder;

import nc.vo.myemp.folder.FolderVO;
import nc.vo.pub.BusinessException;

public interface IFolderService {

	FolderVO insertVO(FolderVO folderVO) throws BusinessException;

	FolderVO updateVO(FolderVO folderVO) throws BusinessException;

	void deleteVO(FolderVO folderVO) throws BusinessException;

}
