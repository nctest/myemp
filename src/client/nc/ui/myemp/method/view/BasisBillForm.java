package nc.ui.myemp.method.view;

import java.util.List;

import nc.bs.logging.Logger;
import nc.ui.myemp.method.event.MethodAppEventConst;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.allocbasis.AllocBasisVO;

public class BasisBillForm extends BillForm implements BillEditListener2 {

	private static final long serialVersionUID = 2540172321607743066L;

	@Override
	public void initUI() {
		super.initUI();
		billCardPanel.addBodyEditListener2(this);
	}

	@Override
	public void handleEvent(AppEvent event) {
		super.handleEvent(event);
		// ������SELECT_NULL�¼�����Ҫ��ձ�������
		if (MethodAppEventConst.SELECT_NULL.toString().equals(event.getType())) {
			billCardPanel.getBillModel().clearBodyData();
		}
	}

	@Override
	protected void synchronizeDataFromModel() {
		Logger.debug("entering synchronizeDataFromModel");
		@SuppressWarnings("unchecked")
		List<AllocBasisVO> data = ((BillManageModel) getModel()).getData();
		// ��������ݣ���Ӧ����ǰ�����ϵ��������
		billCardPanel.getBillModel().clearBodyData();
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				billCardPanel.addLine();
			}
			billCardPanel.getBillModel().setBodyRowObjectByMetaData(
					data.toArray(new AllocBasisVO[0]), 0);
		}
		billCardPanel.getBillTable().getSelectionModel()
				.setSelectionInterval(0, 0);
		Logger.debug("leaving synchronizeDataFromModel");
	}

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		// ��̯ά�Ȳ����Ա༭
		if (AllocBasisVO.ALLOCDIMEN.equals(e.getKey())) {
			billCardPanel.getBillModel().setCellEditable(e.getRow(),
					e.getKey(), false);
		}
		return true;
	}

}
