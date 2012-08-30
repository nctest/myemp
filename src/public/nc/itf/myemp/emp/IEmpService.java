package nc.itf.myemp.emp;

import nc.vo.bd.meta.BatchOperateVO;
import nc.vo.pub.BusinessException;

public interface IEmpService {
	BatchOperateVO batchSave(BatchOperateVO batchVO) throws BusinessException;
}
