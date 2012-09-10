package nc.ui.myemp.method.validator;

import nc.bs.logging.Logger;
import nc.bs.uif2.validation.IValidationService;
import nc.bs.uif2.validation.ValidationException;
import nc.ui.myemp.method.view.MethodBillForm;
import nc.vo.pub.BusinessRuntimeException;

public class MethodValidationService implements IValidationService {
	private MethodBillForm billForm;

	@Override
	public void validate(Object obj) throws ValidationException {
		try {
			billForm.getBillCardPanel().dataNotNullValidate();
		} catch (nc.vo.pub.ValidationException e) {
			Logger.debug(e.getMessage());
			throw new BusinessRuntimeException(e.getMessage(), e);
		}
	}

	public MethodBillForm getBillForm() {
		return billForm;
	}

	public void setBillForm(MethodBillForm billForm) {
		this.billForm = billForm;
	}

}
