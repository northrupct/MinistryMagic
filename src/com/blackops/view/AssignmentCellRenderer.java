package com.blackops.view;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.blackops.model.Family;
import com.blackops.model.Minister;

public class AssignmentCellRenderer extends javax.swing.JLabel implements ListCellRenderer<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		// TODO Auto-generated method stub
		Font f = this.getFont();
		
		setText(value.toString());
		
		boolean changeFlag = false;
		
		if(value instanceof Minister) {
			Minister m = (Minister)value;
			changeFlag = m.isChanged();
		}
		else if(value instanceof Family) {
			Family fam = (Family)value;
			changeFlag = fam.isChanged();
		}
		
		if(changeFlag) {
			setFont(f.deriveFont(f.getStyle() | Font.BOLD));
		}
		else {
			setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
		}
			
		
		return this;
	}

}
