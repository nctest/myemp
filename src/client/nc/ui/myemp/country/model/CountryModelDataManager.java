package nc.ui.myemp.country.model;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.itf.myemp.country.ICountryQueryService;
import nc.ui.uif2.components.pagination.BillManagePaginationDelegator;
import nc.ui.uif2.components.pagination.IPaginationModelListener;
import nc.ui.uif2.components.pagination.PaginationModel;
import nc.ui.uif2.model.BillManageModel;
import nc.ui.uif2.model.IAppModelDataManagerEx;
import nc.vo.myemp.country.CountryVO;
import nc.vo.pub.BusinessException;

public class CountryModelDataManager implements IAppModelDataManagerEx,
		IPaginationModelListener {
	private BillManageModel model;
	private PaginationModel paginationModel;
	private BillManagePaginationDelegator paginationDelegator;

	@Override
	public void initModel() {
		initModelBySqlWhere(null);
//		try {
//			initModelByList(getQueryService().getAll());
//		} catch (BusinessException e) {
//			throw new RuntimeException(e);
//		}
	}

	@SuppressWarnings("unused")
	private void initModelByList(List<CountryVO> list) {
		model.initModel(list.toArray(new CountryVO[list.size()]));
	}

	private ICountryQueryService getQueryService() {
		return NCLocator.getInstance().lookup(ICountryQueryService.class);
	}

	@Override
	public void refresh() {
		int selectedRow = model.getSelectedRow();
		initModel();
		model.setSelectedRow(selectedRow);
	}

	@Override
	public void initModelBySqlWhere(String sqlWhere) {
		try {
			paginationModel.setObjectPks(getQueryService().getPksByCondition(
					sqlWhere));
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setShowSealDataFlag(boolean showSealDataFlag) {
	}

	@Override
	public void onStructChanged() {
	}

	@Override
	public void onDataReady() {
		paginationDelegator.onDataReady();
	}

	public BillManageModel getModel() {
		return model;
	}

	public void setModel(BillManageModel model) {
		this.model = model;
	}

	public PaginationModel getPaginationModel() {
		return paginationModel;
	}

	public void setPaginationModel(PaginationModel paginationModel) {
		this.paginationModel = paginationModel;
		paginationModel.addPaginationModelListener(this);
	}

	public BillManagePaginationDelegator getPaginationDelegator() {
		return paginationDelegator;
	}

	public void setPaginationDelegator(
			BillManagePaginationDelegator paginationDelegator) {
		this.paginationDelegator = paginationDelegator;
	}

}
