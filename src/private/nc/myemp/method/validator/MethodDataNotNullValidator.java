package nc.myemp.method.validator;

import org.apache.commons.lang.StringUtils;

import nc.bs.uif2.validation.ValidationFailure;
import nc.bs.uif2.validation.Validator;
import nc.vo.myemp.method.MethodVO;

public class MethodDataNotNullValidator implements Validator {

	@Override
	public ValidationFailure validate(Object obj) {
		if(!(obj instanceof MethodVO)){
			return null;
		}
		MethodVO vo=(MethodVO)obj;
		StringBuffer sb=new StringBuffer();
		if(StringUtils.isBlank(vo.getControlarea())){
			sb.append("[�ܿط�Χ]");
		}
		if(StringUtils.isBlank(vo.getMethodcode())){
			sb.append("[��������]");
		}
		if(StringUtils.isBlank(vo.getMethodname())){
			sb.append("[��������]");
		}
		if(StringUtils.isBlank(vo.getMethodtype())){
			sb.append("[��̯��������]");
		}
		if(StringUtils.isBlank(vo.getFactor())){
			sb.append("[Ҫ��]");
		}
		if(sb.length()>0){
			return new ValidationFailure(sb.append("����Ϊ�գ�").toString());
		}
		return null;
	}
	
}
