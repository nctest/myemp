package nc.ui.myemp.method.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.allocbasis.AllocBasisVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.resa.factor.FactorAssVO;

public class BasisBillManageModel extends BillManageModel {
	/**
	 * 根据要素主键来初始化界面
	 * 
	 * @param pk_factor
	 */
	public void initBasisModelByFactorPK(String pk_factor) {
		try {
			Map<String, List<FactorAssVO>> map = ((BasisModelService) getService())
					.queryAllByAccPKs(new String[] { pk_factor }, "0000-00-00");
			List<FactorAssVO> list = map.get(pk_factor);
			if (list != null) {
				List<AllocBasisVO> basisVOs = new ArrayList<AllocBasisVO>(
						list.size());
				for (int i = 0; i < list.size(); i++) {
					AllocBasisVO basisVO = new AllocBasisVO();
					basisVO.setSelected(UFBoolean.FALSE);
					basisVO.setAllocdimen(list.get(i).getPk_entity());
					basisVOs.add(basisVO);
				}
				initModel(basisVOs.toArray(new AllocBasisVO[0]));
			} else {
				initModel(null);
			}
		} catch (BusinessException e) {
			Logger.debug(e.getMessage());
		}
	}
}
