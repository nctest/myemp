package nc.ui.myemp.folder.model;

import nc.ui.bd.ref.AbstractRefTreeModel;
import nc.vo.myemp.folder.FolderVO;

public class FolderRefModel extends AbstractRefTreeModel {
	public FolderRefModel() {
		reset();
	}

	@Override
	public void reset() {
		setRefTitle("�ļ���");
		setFieldCode(new String[] { FolderVO.CODE, FolderVO.NAME });
		setFieldName(new String[] { "�ļ��б���", "�ļ�������" });
		setHiddenFieldCode(new String[] { FolderVO.ID, FolderVO.P_ID });
		setPkFieldCode(FolderVO.ID);
		setFatherField(FolderVO.P_ID);
		setChildField(FolderVO.ID);
		setRefCodeField(FolderVO.CODE);
		setRefNameField(FolderVO.NAME);
		setTableName(FolderVO.getDefaultTableName());
	}

}
