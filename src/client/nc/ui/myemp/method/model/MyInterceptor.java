package nc.ui.myemp.method.model;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import nc.bs.logging.Logger;
import nc.ui.uif2.actions.ActionInterceptor;
import nc.ui.uif2.actions.DeleteAction;
import nc.ui.uif2.actions.SaveAction;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.pub.BusinessRuntimeException;

public class MyInterceptor implements ActionInterceptor {
	private BillManageModel model;

	@Override
	public boolean beforeDoAction(Action action, ActionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void afterDoActionSuccessed(Action action, ActionEvent e) {
		if(action instanceof DeleteAction){
			try {
				model.directlyDelete(model.getSelectedData());
			} catch (Exception e1) {
				Logger.error(e1.getMessage(), e1);
				throw new BusinessRuntimeException(e1.getMessage(),e1);
			}
		}else if(action instanceof SaveAction){
			
		}

	}

	@Override
	public boolean afterDoActionFailed(Action action, ActionEvent e,
			Throwable ex) {
		// TODO Auto-generated method stub
		return false;
	}

	public BillManageModel getModel() {
		return model;
	}

	public void setModel(BillManageModel model) {
		this.model = model;
	}

}
