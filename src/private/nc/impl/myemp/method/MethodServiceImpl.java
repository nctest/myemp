package nc.impl.myemp.method;

import nc.bs.bd.baseservice.md.SingleBaseService;
import nc.itf.myemp.method.IMethodService;
import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public class MethodServiceImpl extends SingleBaseService<MethodVO> implements
		IMethodService {
	public MethodServiceImpl() {
		super("52d96209-b406-4077-8ffb-358b621aa577");
	}

	public MethodServiceImpl(String MDId) {
		super(MDId);
	}

	@Override
	public MethodVO insert(MethodVO vo) throws BusinessException {
		return insertVO(vo);
	}

	@Override
	public MethodVO update(MethodVO vo) throws BusinessException {
		return updateVO(vo);
	}

	@Override
	public void delete(MethodVO vo) throws BusinessException {
		deleteVO(vo);
	}

}
