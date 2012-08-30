package nc.ui.myemp.folder.model;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.actions.DeleteConfirmInterceptor;
import nc.ui.uif2.model.HierachicalDataAppModel;

public class FolderDeleteConfirmInterceptor extends DeleteConfirmInterceptor {
	private HierachicalDataAppModel model;

	@Override
	public boolean beforeDoAction(Action action, ActionEvent e) {
		if(model.getSelectedNode().getChildCount()>0){
			ShowStatusBarMsgUtil.showErrorMsg("ɾ������", "���ļ����º������ļ��У�����ɾ��", model.getContext());
			return false;
		}
		return super.beforeDoAction(action, e);
	}

	public HierachicalDataAppModel getModel() {
		return model;
	}

	public void setModel(HierachicalDataAppModel model) {
		this.model = model;
	}

}
