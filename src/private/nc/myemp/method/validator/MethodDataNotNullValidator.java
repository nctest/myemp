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
			sb.append("[管控范围]");
		}
		if(StringUtils.isBlank(vo.getMethodcode())){
			sb.append("[方法编码]");
		}
		if(StringUtils.isBlank(vo.getMethodname())){
			sb.append("[方法名称]");
		}
		if(StringUtils.isBlank(vo.getMethodtype())){
			sb.append("[分摊方法类型]");
		}
		if(StringUtils.isBlank(vo.getFactor())){
			sb.append("[要素]");
		}
		if(sb.length()>0){
			return new ValidationFailure(sb.append("不能为空！").toString());
		}
		return null;
	}
	
}
