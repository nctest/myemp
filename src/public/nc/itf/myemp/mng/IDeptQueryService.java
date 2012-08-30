package nc.itf.myemp.mng;

import java.util.List;

import nc.vo.myemp.mng.Dept;
import nc.vo.pub.BusinessException;

public interface IDeptQueryService {
	List<Dept> getAll() throws BusinessException;
}
