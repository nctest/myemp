package nc.itf.myemp.emp;

import java.util.List;

import nc.vo.myemp.emp.EmpVO;
import nc.vo.pub.BusinessException;

public interface IEmpQueryService {

	List<EmpVO> getAll() throws BusinessException;

}
