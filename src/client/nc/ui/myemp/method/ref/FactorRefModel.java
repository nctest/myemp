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
		setFieldName(new String[]{"Ҫ�ر���","Ҫ������"});
		setRefTitle("Ҫ��");
		setHiddenFieldCode(new String[] { FactorVO.PK_FACTOR });
		setTableName(FactorVO.getDefaultTableName());
		setPkFieldCode(FactorVO.PK_FACTOR);
		setDefaultFieldCount(2);
		resetFieldName();
	}
}
