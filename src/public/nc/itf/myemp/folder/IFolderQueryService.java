package nc.itf.myemp.folder;

import java.util.List;

import nc.vo.myemp.folder.FolderVO;
import nc.vo.pub.BusinessException;

public interface IFolderQueryService {
	List<FolderVO> getAll() throws BusinessException;
}
