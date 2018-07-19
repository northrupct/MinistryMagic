package com.blackops.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.blackops.model.Assignment;
import com.blackops.model.District;

import net.miginfocom.swing.MigLayout;
import javax.swing.ScrollPaneConstants;

public class DistrictPanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel viewportPanel;
	/**
	 * Create the panel.
	 */
	public DistrictPanel(District district) {
		//setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setPreferredSize(new Dimension(350, 600));
		//setMinimumSize(new Dimension(350, 100));
		setMaximumSize(new Dimension(350, 32767));
		setBorder(new TitledBorder(null, district.getDistrictName(), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		viewportPanel = new JPanel();
		viewportPanel.setMaximumSize(new Dimension(310, 32767));
		viewportPanel.setPreferredSize(new Dimension(310, 50));
		
		setViewportView(viewportPanel);
		viewportPanel.setLayout(new MigLayout("", "[]", "[]"));
	
		JButton btnNewAssignment = new JButton("New Assignment");
		btnNewAssignment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Assignment a = new Assignment();
				district.addAssignment(a);
				
				AssignmentPanel ap = new AssignmentPanel(a);
				viewportPanel.add(ap, "wrap");
				
				Dimension d = viewportPanel.getPreferredSize();
				//System.out.println(viewportPanel.getPreferredSize().height);
				if(d.getHeight()+135 > viewportPanel.getPreferredSize().height)
					d.setSize(320, d.getHeight() + 135);				
				viewportPanel.setPreferredSize(d);
				viewportPanel.revalidate();
			}
		});
		viewportPanel.add(btnNewAssignment, "wrap");
		
		for(Assignment a : district.getAssignmentList()) {
			AssignmentPanel ap = new AssignmentPanel(a);
			viewportPanel.add(ap, "wrap");
			Dimension d = viewportPanel.getPreferredSize();
			
			if(d.getHeight()+135 > viewportPanel.getPreferredSize().height)
				d.setSize(320, d.getHeight() + 135);				
			viewportPanel.setPreferredSize(d);			
		}
		viewportPanel.revalidate();

	}

}
