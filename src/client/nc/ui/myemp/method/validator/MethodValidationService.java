package nc.ui.myemp.method.validator;

import java.util.ArrayList;
import java.util.List;

import nc.bs.uif2.validation.IValidationService;
import nc.bs.uif2.validation.ValidationException;
import nc.bs.uif2.validation.ValidationFailure;
import nc.ui.myemp.method.view.MethodBillForm;

public class MethodValidationService implements IValidationService {
	private MethodBillForm billForm;

	@Override
	public void validate(Object obj) throws ValidationException {
		try {
			billForm.getBillCardPanel().dataNotNullValidate();
		} catch (nc.vo.pub.ValidationException e) {
			ValidationFailure failure = new ValidationFailure(e.getMessage());
			List<ValidationFailure>  list = new ArrayList<ValidationFailure>();
			list.add(failure);
			throw new ValidationException(list);
		}
	}

	public MethodBillForm getBillForm() {
		return billForm;
	}

	public void setBillForm(MethodBillForm billForm) {
		this.billForm = billForm;
	}

}
