package nc.ui.myemp.folder.model;

import nc.ui.bd.ref.AbstractRefTreeModel;
import nc.vo.myemp.folder.FolderVO;

public class FolderRefModel extends AbstractRefTreeModel {
	public FolderRefModel() {
		reset();
	}

	@Override
	public void reset() {
		setRefTitle("文件夹");
		setFieldCode(new String[] { FolderVO.CODE, FolderVO.NAME });
		setFieldName(new String[] { "文件夹编码", "文件夹名称" });
		setHiddenFieldCode(new String[] { FolderVO.ID, FolderVO.P_ID });
		setPkFieldCode(FolderVO.ID);
		setFatherField(FolderVO.P_ID);
		setChildField(FolderVO.ID);
		setRefCodeField(FolderVO.CODE);
		setRefNameField(FolderVO.NAME);
		setTableName(FolderVO.getDefaultTableName());
	}

}
