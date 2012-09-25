package nc.impl.myemp.method;

import nc.bs.bd.baseservice.md.SingleBaseService;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.itf.myemp.method.IMethodService;
import nc.vo.myemp.method.BasisVO;
import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public class MethodServiceImpl extends SingleBaseService<MethodVO> implements
		IMethodService {
	public MethodServiceImpl() {
		super("52d96209-b406-4077-8ffb-358b621aa577");
	}

	/**
	 * ¸üÐÂ
	 */
	@Override
	public MethodVO update(MethodVO vo) throws BusinessException {
		if (isFactorChanged(vo, retrieveVO(vo.getPrimaryKey()))) {
			deleteOldBasisVOs(vo);
		}
		return updateVO(vo);
	}

	private void deleteOldBasisVOs(MethodVO vo) throws DAOException {
		new BaseDAO().deleteByClause(BasisVO.class,
				"pk_method='" + vo.getPrimaryKey() + "'");
	}

	private boolean isFactorChanged(MethodVO vo, MethodVO oldVo) {
		return !oldVo.getFactor().equals(vo.getFactor());
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
