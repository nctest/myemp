package nc.itf.myemp.method;

import java.util.List;

import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public interface IMethodService {
	String[] insert(List<MethodVO> list) throws BusinessException;

	MethodVO update(MethodVO vo) throws BusinessException;

	void delete(MethodVO vo) throws BusinessException;
}
