package com.blackops.view;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.blackops.model.Assignment;
import com.blackops.model.Family;
import com.blackops.model.Minister;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JScrollPane;

public class AssignmentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DefaultListModel<Minister> companionship;
	DefaultListModel<Family> assignment;
	/**
	 * Create the panel.
	 */
	public AssignmentPanel(Assignment a) {
		setBackground(SystemColor.controlHighlight);
		companionship = a.getMinisters();
		assignment = a.getFamilies();
		
		setMaximumSize(new Dimension(300, 130));
		setPreferredSize(new Dimension(300, 130));
		setMinimumSize(new Dimension(300, 130));
		setBorder(null);
		setLayout(new MigLayout("", "[100px][grow]", "[][][100px,grow]"));
		
		JLabel lblAssignment = new JLabel("Assignment");
		lblAssignment.setFont(new Font("Tahoma", Font.BOLD, 10));
		add(lblAssignment, "cell 0 0");
		
		AssignmentPanel ap = this;
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!companionship.isEmpty() || !assignment.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please reassign all Ministers and Families before deleting an assignment", "No Can Do", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				a.getParent().removeAssignment(a);
				JPanel c = (JPanel)ap.getParent();
				Dimension d = c.getSize();
				d.setSize(d.getWidth(), d.getHeight() - 200);
				c.setPreferredSize(d);
				c.remove(ap);					
				c.revalidate();
				c.repaint();
			}
		});
		btnNewButton.setMinimumSize(new Dimension(20, 20));
		btnNewButton.setPreferredSize(new Dimension(20, 20));
		btnNewButton.setIcon(new ImageIcon(AssignmentPanel.class.getResource("/cross.png")));
		add(btnNewButton, "cell 1 0,alignx right");
		
		JLabel lblCompantionship = new JLabel("Compantionship");
		add(lblCompantionship, "cell 0 1");
		
		JLabel lblNewLabel = new JLabel("Families");
		add(lblNewLabel, "cell 1 1");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		add(scrollPane, "cell 0 2,grow");
		
		JList<Minister> companionshipList = new JList<Minister>();
		scrollPane.setViewportView(companionshipList);
		companionshipList.setCellRenderer(new AssignmentCellRenderer());
		companionshipList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		companionshipList.setFont(new Font("Tahoma", Font.PLAIN, 13));
		companionshipList.setBackground(SystemColor.info);
		companionshipList.setDropMode(DropMode.INSERT);
		companionshipList.setDragEnabled(true);
		companionshipList.setSize(new Dimension(100, 100));
		companionshipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		companionshipList.setTransferHandler(new MinisterTransferHandler(a));
		companionshipList.setModel(companionship);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, "cell 1 2,grow");
		
		JList<Family> assignmentList = new JList<Family>();
		scrollPane_1.setViewportView(assignmentList);
		assignmentList.setCellRenderer(new AssignmentCellRenderer());
		assignmentList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		assignmentList.setBackground(SystemColor.info);
		assignmentList.setFont(new Font("Tahoma", Font.PLAIN, 13));
		assignmentList.setDropMode(DropMode.INSERT);
		assignmentList.setDragEnabled(true);
		assignmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		assignmentList.setTransferHandler(new AssignmentTransferHandler(a));
		assignmentList.setModel(assignment);

	}

}
