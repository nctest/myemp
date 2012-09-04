package nc.ui.myemp.method.ref;

import nc.ui.bd.ref.AbstractRefModel;
import nc.vo.resa.factor.FactorVO;

public class FactorRefModel extends AbstractRefModel {
	public FactorRefModel() {
		reset();
	}

	@Override
	public void reset() {
		setFieldCode(new String[] { FactorVO.FACTORCODE, FactorVO.FACTORNAME,
				FactorVO.PK_FACTOR });
		setFieldName(new String[]{"要素编码","要素名称"});
		setRefTitle("要素");
		setHiddenFieldCode(new String[] { FactorVO.PK_FACTOR });
		setTableName(FactorVO.getDefaultTableName());
		setPkFieldCode(FactorVO.PK_FACTOR);
		setDefaultFieldCount(2);
		resetFieldName();
	}
}
