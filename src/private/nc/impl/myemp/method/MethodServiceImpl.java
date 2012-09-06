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

		// 更新时的加锁操作
		updatelockOperate(vos);

		// 校验版本
		BDVersionValidationUtil.validateSuperVO(vos);

		// 获取更新前的OldVOs
		MethodVO[] oldVOs = retrieveVO(VOArrayUtil.getPrimaryKeyArray(vos));

		// 业务校验逻辑
		updateValidateVO(oldVOs, vos);

		// 设置审计信息
		setUpdateAuditInfo(vos);

		// 更新前事件处理
		fireBeforeUpdateEvent(oldVOs, vos);

		// 库操作
		dbUpdateVO(vos);

		// 更新缓存
		notifyVersionChangeWhenDataUpdated(vos);

		// 重新检索出新数据
		vos = retrieveVO(VOArrayUtil.getPrimaryKeyArray(vos));

		// 更新后事件通知
		fireAfterUpdateEvent(oldVOs, vos);

		// 业务日志
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
