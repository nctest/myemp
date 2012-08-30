package nc.ui.myemp.country.validator;

import nc.bs.uif2.validation.ValidationFailure;
import nc.bs.uif2.validation.Validator;
import nc.vo.myemp.country.CountryVO;

import org.apache.commons.lang.StringUtils;

public class DataNotNullValidator implements Validator {

	@Override
	public ValidationFailure validate(Object obj) {
		if (!(obj instanceof CountryVO)) {
			return new ValidationFailure("��������!");
		}
		CountryVO vo = (CountryVO) obj;
		StringBuffer sb = new StringBuffer();
		if (StringUtils.trimToNull(vo.getName()) == null) {
			sb.append("[��������]");
		}
		if (StringUtils.trimToNull(vo.getCode()) == null) {
			sb.append("[]���ұ���]");
		}
		if (sb.length() > 0) {
			return new ValidationFailure(sb.append("����Ϊ��!").toString());
		}
		return null;
	}

}
