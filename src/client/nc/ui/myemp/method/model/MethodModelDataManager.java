package nc.ui.myemp.method.model;

import nc.ui.uif2.model.BillManageModel;
import nc.ui.uif2.model.IAppModelDataManagerEx;

public class MethodModelDataManager implements IAppModelDataManagerEx {
	private BillManageModel model;

	@Override
	public void initModel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initModelBySqlWhere(String sqlWhere) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShowSealDataFlag(boolean showSealDataFlag) {
		// TODO Auto-generated method stub

	}

	public BillManageModel getModel() {
		return model;
	}

	public void setModel(BillManageModel model) {
		this.model = model;
	}
	

}
