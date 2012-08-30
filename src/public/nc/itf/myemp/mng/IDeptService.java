package nc.itf.myemp.mng;

import nc.vo.myemp.mng.Dept;
import nc.vo.pub.BusinessException;

public interface IDeptService {
	String save(Dept dept) throws BusinessException;

	void update(Dept dept) throws BusinessException;

	void delete(Dept dept) throws BusinessException;
}
