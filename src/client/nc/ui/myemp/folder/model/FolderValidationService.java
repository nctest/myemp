package nc.ui.myemp.folder.model;

import java.util.Arrays;

import nc.bs.uif2.validation.IValidationService;
import nc.bs.uif2.validation.ValidationException;
import nc.bs.uif2.validation.ValidationFailure;
import nc.md.persist.framework.IMDPersistenceQueryService;
import nc.md.persist.framework.MDPersistenceService;
import nc.ui.uif2.UIState;
import nc.ui.uif2.model.HierachicalDataAppModel;
import nc.vo.myemp.folder.FolderVO;
import nc.vo.pub.BusinessException;

import org.apache.commons.lang.StringUtils;

public class FolderValidationService implements IValidationService {
	private HierachicalDataAppModel model;

	public HierachicalDataAppModel getModel() {
		return model;
	}

	public void setModel(HierachicalDataAppModel model) {
		this.model = model;
	}

	@Override
	public void validate(Object obj) throws ValidationException {
		if (!(obj instanceof FolderVO)) {
			return;
		}
		FolderVO folderVO = (FolderVO) obj;
		if (StringUtils.isBlank(folderVO.getName())) {
			ValidationFailure failure = new ValidationFailure("文件夹名称不能为空");
			throw new ValidationException(Arrays.asList(failure));
		}
		if(model.getUiState()==UIState.ADD){
			return;
		}
		FolderVO selectedData = (FolderVO) model.getSelectedData();
		try {
			if (!isValid(selectedData.getId(), folderVO)) {
				ValidationFailure failure = new ValidationFailure(
						"上级文件夹不能为它本身及它的下级");
				throw new ValidationException(Arrays.asList(failure));
			}
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isValid(String id, FolderVO folderVO)
			throws BusinessException {
		if (folderVO == null || folderVO.getP_id() == null
				|| "".equals(folderVO.getP_id())) {
			return true;
		}
		if (id.equals(folderVO.getP_id())) {
			return false;
		}
		folderVO = getQueryService().queryBillOfVOByPK(FolderVO.class,
				folderVO.getP_id(), true);
		return isValid(id, folderVO);
	}

	private IMDPersistenceQueryService getQueryService() {
		return MDPersistenceService.lookupPersistenceQueryService();
	}

}
