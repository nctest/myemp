package nc.impl.myemp.method;

import nc.bs.bd.baseservice.md.BatchBaseService;
import nc.bs.bd.baseservice.md.SingleBaseService;
import nc.itf.myemp.method.IMethodService;
import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public class MethodServiceImpl extends SingleBaseService<MethodVO> implements
		IMethodService {
	private BatchBaseService<MethodVO> baseService = new BatchBaseService<MethodVO>(
			"52d96209-b406-4077-8ffb-358b621aa577");

	public MethodServiceImpl() {
		super("52d96209-b406-4077-8ffb-358b621aa577");
	}

	/**
	 * ¸üÐÂ
	 */
	@Override
	public MethodVO[] update(MethodVO... vos) throws BusinessException {
		return baseService.updateVO(vos);
	}

	public MethodServiceImpl(String MDId) {
		super(MDId);
	}

	/**
	 * ±£´æ
	 */
	@Override
	public MethodVO insert(MethodVO vo) throws BusinessException {
		return insertVO(vo);
	}

	/**
	 * É¾³ý
	 */
	@Override
	public void delete(MethodVO vo) throws BusinessException {
		deleteVO(vo);
	}

}
