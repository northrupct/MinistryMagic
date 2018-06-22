package com.blackops.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutDlg extends JDialog {

	private final JPanel contentPanel = new JPanel();


	/**
	 * Create the dialog.
	 */
	public AboutDlg() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][][][][][]", "[][][][][]"));
		{
			JLabel lblMinisteringMagic = new JLabel("Ministering Magic");
			lblMinisteringMagic.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblMinisteringMagic, "cell 4 1 2 1,alignx center,aligny center");
		}
		{
			JLabel lblAuthor = new JLabel("Author:");
			contentPanel.add(lblAuthor, "cell 4 2,alignx right");
		}
		{
			JLabel lblAuthorName = new JLabel("Carey Northrup");
			contentPanel.add(lblAuthorName, "cell 5 2");
		}
		{
			JLabel lblEmailCareynorthrupnetnet = new JLabel("Email: ");
			contentPanel.add(lblEmailCareynorthrupnetnet, "cell 4 3,alignx right");
		}
		{
			JLabel lblEmailAddress = new JLabel("carey@northrupnet.net");
			contentPanel.add(lblEmailAddress, "cell 5 3");
		}
		{
			JLabel lblGithubProject = new JLabel("GitHub Project: ");
			contentPanel.add(lblGithubProject, "cell 4 4,alignx right");
		}
		{
			JLabel lblGitHubURL = new JLabel("https://github.com/northrupct/MinistryMagic");
			contentPanel.add(lblGitHubURL, "cell 5 4");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						clearAndHide();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
    /** This method clears the dialog and hides it. */
    public void clearAndHide() {
        setVisible(false);
    }

}
