package nc.ui.myemp.folder.model;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.folder.IFolderQueryService;
import nc.ui.uif2.model.HierachicalDataAppModel;
import nc.ui.uif2.model.IAppModelDataManagerEx;
import nc.vo.myemp.folder.FolderVO;
import nc.vo.pub.BusinessException;

public class FolderAppModelDataManager implements IAppModelDataManagerEx {
	private HierachicalDataAppModel model;

	public HierachicalDataAppModel getModel() {
		return model;
	}

	public void setModel(HierachicalDataAppModel model) {
		this.model = model;
	}

	@Override
	public void initModel() {
		try {
			List<FolderVO> list = getQueryService().getAll();
			model.initModel(list.toArray(new FolderVO[list.size()]));
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
	}

	private IFolderQueryService getQueryService() {
		return NCLocator.getInstance().lookup(IFolderQueryService.class);
	}

	@Override
	public void refresh() {
		Object selectedData = model.getSelectedData();
		initModel();
		model.setSelectedData(selectedData);

	}

	@Override
	public void initModelBySqlWhere(String sqlWhere) {
	}

	@Override
	public void setShowSealDataFlag(boolean showSealDataFlag) {
	}

}
