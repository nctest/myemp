package nc.ui.myemp.method.view;

import java.lang.reflect.Array;
import java.util.List;

import javax.swing.ListSelectionModel;

import nc.desktop.ui.WorkbenchEnvironment;
import nc.ui.myemp.method.event.MethodAppEventConst;
import nc.ui.myemp.method.model.MethodBillManageModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.resa.refmodel.FactorRefModel;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.UIState;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.myemp.method.BasisVO;
import nc.vo.myemp.method.MethodVO;

import org.apache.commons.lang.StringUtils;

public class MethodBillForm extends BillForm implements BillEditListener,
		BillEditListener2, ValueChangedListener {
	private static final long serialVersionUID = 1L;
	private int editRow;
	private BasisBillForm basisForm;

	@Override
	public void initUI() {
		super.initUI();
		// 关闭自动增行
		billCardPanel.setBodyAutoAddLine(false);
		billCardPanel.addBodyEditListener2(this);
		billCardPanel.addEditListener(this);
		// 设置一次只能选择一行
		billCardPanel.getBillTable().getSelectionModel()
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 为要素添加ValueChangedListener
		UIRefPane factorRefPane = (UIRefPane) billCardPanel.getBodyItem(
				"factor").getComponent();
		factorRefPane.addValueChangedListener(this);
	}

	@Override
	protected void synchronizeDataFromModel() {
		@SuppressWarnings("unchecked")
		List<MethodVO> data = ((BillManageModel) getModel()).getData();
		if (data != null && data.size() > 0) {
			billCardPanel.getBillModel().clearBodyData();
			for (int i = 0; i < data.size(); i++) {
				billCardPanel.addLine();
			}
			billCardPanel.getBillModel().setBodyRowObjectByMetaData(
					data.toArray(new MethodVO[0]), 0);
		}
		// 设置第一行选中
		billCardPanel.getBillTable().getSelectionModel()
				.setSelectionInterval(0, 0);
	}

	@Override
	public Object getValue() {
		Object methodVOs = super.getValue();
		// 通过调用，setEditRow,beforeEdit，restoreState方法，新增和修改状态下得到的都是含有一个元素的数组。
		// 增加时只返回一条数据（主键为空）
		if (MethodVO[].class.isInstance(methodVOs)
				&& Array.getLength(methodVOs) > 0) {
			MethodVO[] vos = (MethodVO[]) methodVOs;
			if (isUIStateAdd() && vos[0].getPk_method() == null) {
				vos[0].setBases((BasisVO[]) basisForm.getValue());
				return vos[0];
			} else if (isUIStateEdit()) {
				vos[0].setBases((BasisVO[]) basisForm.getValue());
			}
			return vos;
		}
		return methodVOs;
	}

	private boolean isUIStateEdit() {
		return getModel().getUiState() == UIState.EDIT;
	}

	@Override
	protected void beforeGetValue() {
		super.beforeGetValue();
		restoreState();
	}

	private void restoreState() {
		int rowCount = billCardPanel.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			// 如果当前编辑行中包含i,就根据UIState的值将该i行的状态设置为相应的状态
			if (i != editRow) {
				billCardPanel.getBillModel().setRowState(i, BillModel.NORMAL);
			} else {
				if (isUIStateAdd()) {
					setRowState(i, BillModel.ADD);
				} else if (isUIStateEdit()) {
					setRowState(i, BillModel.MODIFICATION);
				}
			}
		}
	}

	private void setRowState(int i, int state) {
		billCardPanel.getBillModel().setRowState(i, state);
	}

	private boolean isUIStateAdd() {
		return getModel().getUiState() == UIState.ADD;
	}

	@Override
	protected void onAdd() {
		// 新增方式与原封装逻辑不同，不需要清除数据
		setEditable(true);
		billCardPanel.addLine();
		// 将最后一行设置为编辑行
		setEditRow(billCardPanel.getRowCount() - 1);
	}

	@Override
	protected void onEdit() {
		setEditable(true);
		// 此处该方法主要是为了处理在修改状态下，只修改子表，而没有修改主表
		// 经过这样处理，在beforeGetValue方法中恢复状态，可以保证在获取值时可以得到
		setEditRow(((MethodBillManageModel) getModel()).getSelectedRow());
	}

	@Override
	protected void onNotEdit() {
		super.onNotEdit();
		editRow = -1;
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		// 设置修改的行，以便在beforeGetValue方法中将它恢复为修改状态
		setEditRow(e.getRow());
		// 如果编辑的是管控范围，并且值非空
		if (isControlAreaAndNotBlank(e)) {
			// 关联管控范围和要素
			relateControlAreaAndFactor();
			// 设置要素为可编辑,不可缺少
			setFactorEditable(e, true);
		}
	}

	/**
	 * 设置要素是否可以编辑
	 * 
	 * @param e
	 * @param isEditable
	 */
	private void setFactorEditable(BillEditEvent e, boolean isEditable) {
		billCardPanel.getBillModel().setCellEditable(e.getRow(),
				MethodVO.FACTOR, isEditable);
	}

	// 如果编辑的是管控范围，并且值非空
	private boolean isControlAreaAndNotBlank(BillEditEvent e) {
		return MethodVO.CONTROLAREA.equals(e.getKey())
				&& StringUtils.isNotBlank((String) e.getValue());
	}

	/**
	 * 关联管控范围和要素
	 */
	private void relateControlAreaAndFactor() {
		UIRefPane factorRefPane = (UIRefPane) billCardPanel.getBodyItem(
				MethodVO.FACTOR).getComponent();
		FactorRefModel factorRefModel = (FactorRefModel) factorRefPane
				.getRefModel();
		factorRefModel.setDate(WorkbenchEnvironment.getInstance().getBusiDate()
				.toLocalString());
		String controlArea = (String) billCardPanel.getBillModel().getValueAt(
				0, MethodVO.CONTROLAREA + IBillItem.ID_SUFFIX);
		factorRefModel.setPk_controlarea(controlArea);
	}

	@Override
	public void bodyRowChange(BillEditEvent e) {
		MethodBillManageModel model = (MethodBillManageModel) getModel();
		int row = e.getRow();
		@SuppressWarnings("unchecked")
		List<MethodVO> list = (List<MethodVO>) model.getData();
		MethodVO vo = null;
		if (list.size() > row) {
			vo = list.get(row);
		}
		if (vo == null) {
			vo = new MethodVO();
			UITable billTable = billCardPanel.getBillTable();
			int rowCount = billTable.getRowCount();
			DefaultConstEnum defaultConstEnum = (DefaultConstEnum) billTable
					.getValueAt(rowCount - 1, 4);// 第4列
			if (defaultConstEnum != null) {
				vo.setFactor((String) defaultConstEnum.getValue());
			}
		}
		// 调用setMethodVO方法，在该方法中触发SELECT_METHODVO事件
		model.setMethodVO(vo);
	}

	/**
	 * 该方法只在编辑要素时进行，根据管控范围是否为空来设置要素是否可以编辑
	 */
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		if (editRow != e.getRow()) {
			return false;
		}
		if (!MethodVO.FACTOR.equals(e.getKey())) {
			return true;
		}
		String controlArea = getControlAreaValue(e);
		// 根据管控范围是否为空，设置factor是否可以编辑
		setFactorEditable(e, StringUtils.isNotBlank(controlArea));
		if (StringUtils.isNotBlank(controlArea)) {
			relateControlAreaAndFactor();
		}
		return true;
	}

	private String getControlAreaValue(BillEditEvent e) {
		return (String) billCardPanel.getBillModel().getValueAt(e.getRow(),
				MethodVO.CONTROLAREA + IBillItem.ID_SUFFIX);
	}

	/**
	 * 当要素的值发生改变时，触发自定义Factor_Changed事件。
	 */
	@Override
	public void valueChanged(ValueChangedEvent event) {
		MethodBillManageModel model = (MethodBillManageModel) getModel();
		if (event.getOldValue() != null
				&& StringUtils.isNotBlank((String) Array.get(
						event.getOldValue(), 0))) {
			model.setEditFactorChanged(true);
		}
		model.fireEvent(new AppEvent(MethodAppEventConst.FACTOR_CHANGE
				.toString(), model, event));
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BasisBillForm getBasisForm() {
		return basisForm;
	}

	public void setBasisForm(BasisBillForm basisForm) {
		this.basisForm = basisForm;
	}

	public int getEditRow() {
		return editRow;
	}

	public void setEditRow(int editRow) {
		this.editRow = editRow;
	}

}
