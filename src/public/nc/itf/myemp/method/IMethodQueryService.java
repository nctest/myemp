package nc.itf.myemp.method;

import java.util.List;

import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public interface IMethodQueryService {
	/**
	 * ��ѯ�����е�MethodVO
	 * @return
	 * @throws BusinessException
	 */
	List<MethodVO> getAll() throws BusinessException;
}
