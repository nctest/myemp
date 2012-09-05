package nc.itf.myemp.method;

import java.util.List;

import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public interface IMethodQueryService {
	List<MethodVO> getAll() throws BusinessException;
}
