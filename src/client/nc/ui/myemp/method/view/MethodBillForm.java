package nc.ui.myemp.method.view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListSelectionModel;

import nc.desktop.ui.WorkbenchEnvironment;
import nc.ui.myemp.method.event.MethodAppEventConst;
import nc.ui.myemp.method.model.MethodBillManageModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
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
import nc.vo.myemp.method.MethodVO;

import org.apache.commons.lang.StringUtils;

public class MethodBillForm extends BillForm implements BillEditListener,
		BillEditListener2, ValueChangedListener {
	private static final long serialVersionUID = 1L;
	private Set<Integer> editRows = new HashSet<Integer>();

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
		Object value = super.getValue();
		// 增加时只返回一条数据（主键为空）
		if (isUIStateAdd() && value.getClass().isArray()) {
			MethodVO[] objs = (MethodVO[]) value;
			for (MethodVO vo : objs) {
				if (vo.getPk_method() == null) {
					return vo;
				}
			}
		}
		return value;
	}

	@Override
	protected void beforeGetValue() {
		super.beforeGetValue();
		// 在这里设置那些修改行为修改状态,因为当发生SELECTION_CHANGED事件时，会把所有的行状态设为Normal
		for (Integer editRow : editRows) {
			billCardPanel.getBillModel().setRowState(editRow,
					BillModel.MODIFICATION);
		}
	}

	private boolean isUIStateAdd() {
		return getModel().getUiState() == UIState.ADD;
	}

	@Override
	protected void onAdd() {
		// 新增方式与原封装逻辑不同，不需要清除数据
		setEditable(true);
		billCardPanel.addLine();
	}

	@Override
	protected void onEdit() {
		setEditable(true);
	}

	@Override
	protected void onNotEdit() {
		super.onNotEdit();
		// 清空editRows,防止影响下次的修改操作
		editRows.clear();
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		// 将修改的行添加到editRows中，以便在beforeGetValue方法中将它们恢复为修改状态
		addToEditRows(e);
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
	 * 将修改的行添加到editRows集合中
	 * 
	 * @param e
	 * @return
	 */
	private void addToEditRows(BillEditEvent e) {
		int selectedRow = e.getRow();
		if (!editRows.contains(selectedRow)) {
			editRows.add(selectedRow);
		}
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
		// 调用setMethodVO方法，在该方法中触发SELECT_METHODVO事件
		model.setMethodVO(vo);
	}

	/**
	 * 该方法只在编辑要素时进行，根据管控范围是否为空来设置要素是否可以编辑
	 */
	@Override
	public boolean beforeEdit(BillEditEvent e) {
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
		String controlArea = (String) billCardPanel.getBillModel().getValueAt(
				e.getRow(), MethodVO.CONTROLAREA + IBillItem.ID_SUFFIX);
		return controlArea;
	}

	/**
	 * 当要素的值发生改变时，触发自定义Factor_Changed事件。
	 */
	@Override
	public void valueChanged(ValueChangedEvent event) {
		BillManageModel model = (BillManageModel) getModel();
		model.fireEvent(new AppEvent(MethodAppEventConst.FACTOR_CHANGE
				.toString(), model, event));
	}

	public Set<Integer> getEditRows() {
		return editRows;
	}

	public void setEditRows(Set<Integer> editRows) {
		this.editRows = editRows;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
