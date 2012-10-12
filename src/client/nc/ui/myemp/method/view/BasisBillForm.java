package nc.ui.myemp.method.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.md.MDBaseQueryFacade;
import nc.md.model.IBean;
import nc.md.model.MetaDataException;
import nc.ui.myemp.method.model.BasisBillManageModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.IBillItem;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.bd.accassitem.AccAssItemVO;
import nc.vo.myemp.method.BasisVO;
import nc.vo.pub.BusinessException;

public class BasisBillForm extends BillForm implements BillEditListener2 {

	private static final long serialVersionUID = 2540172321607743066L;
	private Map<String, String> map = new HashMap<String, String>();
	private UIRefPane uiRefPane = new UIRefPane();

	@Override
	public void initUI() {
		super.initUI();
		billCardPanel.addBodyEditListener2(this);
		billCardPanel.getBodyItem(BasisVO.TYPE).setComponent(uiRefPane);
	}

	@Override
	protected void synchronizeDataFromModel() {
		Logger.debug("entering synchronizeDataFromModel");
		@SuppressWarnings("unchecked")
		List<BasisVO> data = ((BillManageModel) getModel()).getData();
		// 如果无数据，还应把以前界面上的数据清空
		billCardPanel.getBillModel().clearBodyData();
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				billCardPanel.addLine();
			}
			billCardPanel.getBillModel().setBodyRowObjectByMetaData(
					data.toArray(new BasisVO[0]), 0);
		}
		billCardPanel.getBillTable().getSelectionModel()
				.setSelectionInterval(0, 0);
		Logger.debug("leaving synchronizeDataFromModel");
	}

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		if (BasisVO.TYPE.equals(e.getKey())) {
			boolean selected = (Boolean) billCardPanel.getBillModel().getValueAt(
					e.getRow(), BasisVO.SELECTED);
			if(!selected){
				return false;
			}
		}
		try {
			uiRefPane.setRefNodeName(getRefNodeName(e));
		} catch (MetaDataException e1) {
			Logger.info(e1.getMessage());
		} catch (BusinessException e1) {
			Logger.info(e1.getMessage());
		}
		return true;
	}

	private String getRefNodeName(BillEditEvent e) throws BusinessException,
			MetaDataException {
		BasisBillManageModel model = (BasisBillManageModel) getModel();
		String pkAllocDimen = (String) billCardPanel.getBillModel().getValueAt(
				e.getRow(), BasisVO.ALLOCDIMEN + IBillItem.ID_SUFFIX);
		if (map.containsKey(pkAllocDimen)) {
			return map.get(pkAllocDimen);
		}
		AccAssItemVO vo = model.getAllocDimen(pkAllocDimen);
		String beanid = vo.getClassid();
		IBean bean = MDBaseQueryFacade.getInstance().getBeanByID(beanid);
		String refNodeName = bean.getDefaultRefModelName();
		map.put(pkAllocDimen, refNodeName);
		return refNodeName;
	}

}
