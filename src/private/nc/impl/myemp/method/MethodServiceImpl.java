package nc.impl.myemp.method;

import nc.bs.bd.baseservice.busilog.IBusiOperateConst;
import nc.bs.bd.baseservice.md.SingleBaseService;
import nc.bs.bd.baseservice.md.VOArrayUtil;
import nc.bs.businessevent.bd.BDCommonEventUtil;
import nc.bs.uif2.validation.IValidationService;
import nc.bs.uif2.validation.ValidationFrameworkUtil;
import nc.bs.uif2.validation.Validator;
import nc.itf.myemp.method.IMethodService;
import nc.vo.bd.pub.BatchDistributedUpdateValidator;
import nc.vo.myemp.method.MethodVO;
import nc.vo.pub.BusinessException;
import nc.vo.util.AuditInfoUtil;
import nc.vo.util.BDPKLockUtil;
import nc.vo.util.BDUniqueRuleValidate;
import nc.vo.util.BDVersionValidationUtil;
import nc.vo.util.bizlock.BizlockDataUtil;

public class MethodServiceImpl extends SingleBaseService<MethodVO> implements
		IMethodService {
	public MethodServiceImpl() {
		super("52d96209-b406-4077-8ffb-358b621aa577");
	}

	@Override
	public MethodVO[] update(MethodVO... vos) throws BusinessException {
		if (vos == null || vos.length == 0)
			return vos;

		// ����ʱ�ļ�������
		updatelockOperate(vos);

		// У��汾
		BDVersionValidationUtil.validateSuperVO(vos);

		// ��ȡ����ǰ��OldVOs
		MethodVO[] oldVOs = retrieveVO(VOArrayUtil.getPrimaryKeyArray(vos));

		// ҵ��У���߼�
		updateValidateVO(oldVOs, vos);

		// ���������Ϣ
		setUpdateAuditInfo(vos);

		// ����ǰ�¼�����
		fireBeforeUpdateEvent(oldVOs, vos);

		// �����
		dbUpdateVO(vos);

		// ���»���
		notifyVersionChangeWhenDataUpdated(vos);

		// ���¼�����������
		vos = retrieveVO(VOArrayUtil.getPrimaryKeyArray(vos));

		// ���º��¼�֪ͨ
		fireAfterUpdateEvent(oldVOs, vos);

		// ҵ����־
		writeUpdatedBusiLog(vos);

		return vos;
	}

	protected void updatelockOperate(MethodVO... vos) throws BusinessException {
		BDPKLockUtil.lockSuperVO(vos);
		BizlockDataUtil.lockDataByBizlock(vos);
	}

	protected void updateValidateVO(MethodVO[] oldVOs, MethodVO... vos)
			throws BusinessException {
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(getUpdateValidator(oldVOs));
		validateService.validate(vos);
	}

	protected Validator[] getUpdateValidator(MethodVO[] oldVO) {
		Validator[] validators = new Validator[] { new BDUniqueRuleValidate(),
				new BatchDistributedUpdateValidator() };
		return validators;
	}

	protected void setUpdateAuditInfo(MethodVO... vos) {
		AuditInfoUtil.updateData(vos);
	}

	protected void fireBeforeUpdateEvent(MethodVO[] oldVOs, MethodVO... vos)
			throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.setOldObjs(oldVOs);
		eventUtil.dispatchUpdateBeforeEvent((Object[]) vos);
	}

	protected void dbUpdateVO(MethodVO... vos) throws BusinessException {
		getPersistenceUtil().updateVO(vos);
	}

	protected void notifyVersionChangeWhenDataUpdated(MethodVO... vos)
			throws BusinessException {
		getPersistenceUtil().notifyVersionChangeWhenDataUpdated(vos);
	}

	protected void fireAfterUpdateEvent(MethodVO[] oldVOs, MethodVO... vos)
			throws BusinessException {
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(getMDId());
		eventUtil.setOldObjs(oldVOs);
		eventUtil.dispatchUpdateAfterEvent((Object[]) vos);
	}

	protected void writeUpdatedBusiLog(MethodVO... vos)
			throws BusinessException {
		getBusiLogUtil().writeBusiLog(IBusiOperateConst.EDIT, null, vos);
	}

	public MethodServiceImpl(String MDId) {
		super(MDId);
	}

	@Override
	public MethodVO insert(MethodVO vo) throws BusinessException {
		return insertVO(vo);
	}

	@Override
	public void delete(MethodVO vo) throws BusinessException {
		deleteVO(vo);
	}

}
