package nc.impl.myemp.method;

import nc.bs.bd.baseservice.md.SingleBaseService;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.itf.myemp.method.IMethodService;
import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;

public class MethodServiceImpl extends SingleBaseService<MethodVO> implements
		IMethodService {
	public MethodServiceImpl() {
		super("52d96209-b406-4077-8ffb-358b621aa577");
	}

	/**
	 * ����
	 */
	@Override
	public MethodVO update(MethodVO vo) throws BusinessException {
		deleteOldBasisVOs(vo);
		return updateVO(vo);
	}

	private void deleteOldBasisVOs(MethodVO vo) throws BusinessException,
			DAOException {
		MethodVO oldVo = retrieveVO(vo.getPrimaryKey());
		if (!oldVo.getFactor().equals(vo.getFactor())) {
			new BaseDAO().deleteByClause(nc.vo.myemp.method.BasisVO.class,
					"pk_method='" + vo.getPrimaryKey()+"'");
		}
	}

	public MethodServiceImpl(String MDId) {
		super(MDId);
	}

	/**
	 * ����
	 */
	@Override
	public MethodVO insert(MethodVO vo) throws BusinessException {
		return insertVO(vo);
	}

	/**
	 * ɾ��
	 */
	@Override
	public void delete(MethodVO vo) throws BusinessException {
		deleteVO(vo);
	}

}
