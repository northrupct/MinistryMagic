package com.blackops.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.blackops.model.Assignment;
import com.blackops.model.District;
import com.blackops.model.Family;
import com.blackops.model.Minister;
import com.blackops.model.MinisteringModel;

import net.miginfocom.swing.MigLayout;

public class SecretProjectGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private MinisteringModel ministeringModel;
	private JTextField txtAddFamily;
	private JTextField txtAddMinister;

	JPanel districtsPanel = new JPanel();
	JList<Family> familyList;
	JList<Minister> ministerList;

	String currentFilename = "Untitled.dat";

	final String TITLE = "Ministering Magic ";
	private final Action saveAsAction = new SaveAsAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecretProjectGUI frame = new SecretProjectGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SecretProjectGUI() {
		setTitle(TITLE + " - " + currentFilename);
		setMinimumSize(new Dimension(1000, 750));

		ministeringModel = new MinisteringModel();

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we)
		    { 
		        String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","MinisteryMagic",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            System.exit(0);
		        }
		    }
		});
		setBounds(100, 100, 740, 463);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		mnNewMenu.setMnemonic('F');
		menuBar.add(mnNewMenu);

		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData();
			}
		});

		JMenuItem mnNewMenu_1 = new JMenuItem("New");
		mnNewMenu_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setTitle(TITLE + "- " + currentFilename);
				ministeringModel = new MinisteringModel();
				populate();
			}
		});
		mnNewMenu.add(mnNewMenu_1);

		mnNewMenu.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				saveData();
			}
		});
		mnNewMenu.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.setAction(saveAsAction);
		mnNewMenu.add(mntmSaveAs);

		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);

		JMenuItem mntmImportMenu = new JMenu("Import");
		mnNewMenu.add(mntmImportMenu);

		JMenuItem mntmFamilies = new JMenuItem("Families");
		mntmFamilies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<Family> importData = importFamilyData();
				ministeringModel.setUnassignedFamilies(importData);
				populate();
			}
		});
		mntmImportMenu.add(mntmFamilies);

		JMenuItem mntmMinisters = new JMenuItem("Ministers");
		mntmMinisters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<Minister> importData = importMinisterData();
				ministeringModel.setUnassignedMinisters(importData);
				populate();
			}
		});
		mntmImportMenu.add(mntmMinisters);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu mnExport = new JMenu("Export");
		mnNewMenu.add(mnExport);

		JMenuItem mntmProposedChanges = new JMenuItem("Proposed Changes");
		mntmProposedChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportProposedChanges();
			}
		});
		mnExport.add(mntmProposedChanges);

		JSeparator separator_1 = new JSeparator();
		mnNewMenu.add(separator_1);
		mnNewMenu.add(mntmExit);

		JMenu mnActions = new JMenu("Actions");
		menuBar.add(mnActions);

		JMenuItem mntmCreateDistrict = new JMenuItem("Create District");
		mntmCreateDistrict.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int distNum = ministeringModel.getDistricts().size() + 1;

				if (distNum > 4) {
					JOptionPane.showMessageDialog(null, "More than 4 discticts! Are you crazy?", "Whoa there!",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				District district = new District();
				district.setDistrictName("District " + distNum);
				ministeringModel.addDistrict(district);
				DistrictPanel dp = new DistrictPanel(district);

				// districtsPanel.add(dp, "growy");
				districtsPanel.add(dp, "alignx left,aligny top, growy");

				Dimension d = districtsPanel.getPreferredSize();
				System.out.println(districtsPanel.getPreferredSize().width);
				if (d.getWidth() + 320 > districtsPanel.getPreferredSize().width)
					d.setSize(d.getWidth() + 200, d.getHeight());
				districtsPanel.setPreferredSize(d);

				districtsPanel.revalidate();
			}
		});
		mnActions.add(mntmCreateDistrict);

		JMenuItem mntmCommitChanges = new JMenuItem("Commit Changes");
		mntmCommitChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "This will reset the change log. Are you sure?",
						"Change Reset", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION)
					resetChanges();
			}
		});
		mnActions.add(mntmCommitChanges);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDlg ad = new AboutDlg();
				ad.setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, "cell 0 0,grow");

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_1);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		topPanel.setBorder(
				new TitledBorder(null, "Unassigned Families", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JScrollPane sourcePanel1 = new JScrollPane();
		sourcePanel1.setPreferredSize(new Dimension(150, 200));
		sourcePanel1.setMinimumSize(new Dimension(100, 23));
		splitPane_1.setTopComponent(topPanel);
		// sourcePanel1.setBorder(new TitledBorder(null, "Source", TitledBorder.LEADING,
		// TitledBorder.TOP, null, null));

		topPanel.add(sourcePanel1, "cell 0 0, grow");

		familyList = new JList<Family>();
		familyList.setDropMode(DropMode.INSERT);
		familyList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE && familyList.getSelectedIndex() != -1) {
					ministeringModel.getUnassignedFamilies().removeElementAt(familyList.getSelectedIndex());

				}
			}
		});
		familyList.setMinimumSize(new Dimension(100, 50));
		familyList.setBackground(UIManager.getColor("Button.background"));
		familyList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		familyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		familyList.setDragEnabled(true);
		sourcePanel1.setViewportView(familyList);
		familyList.setTransferHandler(new AssignmentTransferHandler());

		txtAddFamily = new JTextField();
		txtAddFamily.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = txtAddFamily.getText();
				if (!txt.equals("")) {
					ministeringModel.getUnassignedFamilies().addElement(new Family(txt));
					txtAddFamily.setText("");
				}
			}
		});
		topPanel.add(txtAddFamily, "cell 0 1,growx");
		txtAddFamily.setColumns(10);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		bottomPanel.setBorder(
				new TitledBorder(null, "Unassigned Ministers", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JScrollPane sourcePanel2 = new JScrollPane();
		splitPane_1.setBottomComponent(bottomPanel);

		bottomPanel.add(sourcePanel2, "cell 0 0, grow");

		ministerList = new JList<Minister>();
		ministerList.setMinimumSize(new Dimension(50, 50));
		ministerList.setBackground(UIManager.getColor("Button.background"));
		ministerList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sourcePanel2.setViewportView(ministerList);
		ministerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ministerList.setDragEnabled(true);
		ministerList.setDropMode(DropMode.INSERT);
		ministerList.setTransferHandler(new MinisterTransferHandler());
		ministerList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE && ministerList.getSelectedIndex() != -1) {
					ministeringModel.getUnassignedMinisters().removeElementAt(ministerList.getSelectedIndex());

				}
			}
		});
		splitPane_1.setDividerLocation(0.5);

		JScrollPane districtScrollPane = new JScrollPane();
		splitPane.setRightComponent(districtScrollPane);

		districtsPanel
				.setBorder(new TitledBorder(null, "Districts", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		districtScrollPane.setViewportView(districtsPanel);
		districtsPanel.setLayout(new MigLayout("", "", "[grow]"));

		txtAddMinister = new JTextField();
		txtAddMinister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = txtAddMinister.getText();
				if (!txt.equals("")) {
					ministeringModel.getUnassignedMinisters().addElement(new Minister(txt));
					txtAddMinister.setText("");
				}
			}
		});
		bottomPanel.add(txtAddMinister, "cell 0 1,growx");
		txtAddMinister.setColumns(10);

		populate();

		splitPane.setDividerLocation(0.3);

	}

	void populate() {

		familyList.setModel(ministeringModel.getUnassignedFamilies());

		ministerList.setModel(ministeringModel.getUnassignedMinisters());

		districtsPanel.removeAll();
		for (District d : ministeringModel.getDistricts()) {
			DistrictPanel dp = new DistrictPanel(d);
			districtsPanel.add(dp, "alignx left,aligny top, growy");
		}

		districtsPanel.revalidate();
		districtsPanel.repaint();
	}

	void loadData() {

		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Data File", "dat"));

		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File importFile = fc.getSelectedFile();
			// load ministering data
			try (FileInputStream fis = new FileInputStream(importFile);
					ObjectInputStream ois = new ObjectInputStream(fis)) {

				currentFilename = importFile.getAbsolutePath();
				ministeringModel = (MinisteringModel) ois.readObject();
				populate();
				setTitle(TITLE + " - " + currentFilename);
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	void saveData() {

		// resetChanges();
		try (FileOutputStream fout = new FileOutputStream(currentFilename);
				ObjectOutputStream oos = new ObjectOutputStream(fout)) {

			oos.writeObject(ministeringModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void exportProposedChanges() {

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save As...");
		fc.setFileFilter(new FileNameExtensionFilter("Text Documents", "txt"));

		int returnVal = fc.showSaveDialog(rootPane);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String saveFile = fc.getSelectedFile() + "";
			System.out.println(saveFile);
			if (!saveFile.endsWith(".txt"))
				saveFile += ".txt";

			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("MMM dd YYYY");
			StringBuilder output = new StringBuilder();

			output.append("Proposed Ministering Changes\n");
			output.append(format.format(now) + "\n");

			output.append("\n");

			for (District d : ministeringModel.getDistricts()) {
				output.append(d.getDistrictName() + "\n");

				for (Assignment a : d.getAssignmentList()) {
					if (a.isChanged()) {
						DefaultListModel<Minister> ministers = a.getMinisters();
						for (int i = 0; i < ministers.size(); i++) {
							output.append(ministers.getElementAt(i));
							if(ministers.getElementAt(i).isChanged())
								output.append("*");
							output.append("\n");
						}
						DefaultListModel<Family> families = a.getFamilies();
						for (int i = 0; i < families.size(); i++) {
							output.append("\t\t" + families.getElementAt(i) );
							if(families.getElementAt(i).isChanged())
								output.append("*");
							output.append("\n");
						}
						output.append("\n");
					}
				}
			}

			// write to file
			try (FileWriter fw = new FileWriter(saveFile)) {
				fw.write(output.toString());
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	void resetChanges() {
		for (District d : ministeringModel.getDistricts()) {
			for (Assignment a : d.getAssignmentList()) {
				a.setChanged(false);

				for (int i = 0; i < a.getFamilies().getSize(); i++) {
					a.getFamilies().getElementAt(i).setChanged(false);
				}
				for (int i = 0; i < a.getMinisters().getSize(); i++) {
					a.getMinisters().getElementAt(i).setChanged(false);
				}
			}
		}
		districtsPanel.revalidate();
		districtsPanel.repaint();

	}

	DefaultListModel<Family> importFamilyData() {
		JFileChooser fc = new JFileChooser();
		DefaultListModel<Family> importData = new DefaultListModel<Family>();

		fc.setFileFilter(new FileNameExtensionFilter("Text Documents", "txt"));

		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File importFile = fc.getSelectedFile();

			try (BufferedReader br = new BufferedReader(new FileReader(importFile))) {
				for (String line; (line = br.readLine()) != null;) {
					if (!line.isEmpty())
						importData.addElement(new Family(line));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return importData;
	}

	DefaultListModel<Minister> importMinisterData() {
		JFileChooser fc = new JFileChooser();
		DefaultListModel<Minister> importData = new DefaultListModel<Minister>();

		fc.setFileFilter(new FileNameExtensionFilter("Text Documents", "txt"));

		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File importFile = fc.getSelectedFile();

			try (BufferedReader br = new BufferedReader(new FileReader(importFile))) {
				for (String line; (line = br.readLine()) != null;) {
					if (!line.isEmpty())
						importData.addElement(new Minister(line));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return importData;
	}

	private class SaveAsAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SaveAsAction() {
			putValue(NAME, "Save As...");
			putValue(SHORT_DESCRIPTION, "Save As Dialog");
		}

		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save As...");
			fc.setFileFilter(new FileNameExtensionFilter("Data File", "dat"));

			int returnVal = fc.showSaveDialog(rootPane);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String saveFile = fc.getSelectedFile() + "";
				if (!saveFile.endsWith(".dat"))
					saveFile += ".dat";
				try (FileOutputStream fout = new FileOutputStream(saveFile);
						ObjectOutputStream oos = new ObjectOutputStream(fout)) {
					oos.writeObject(ministeringModel);
					oos.flush();
					currentFilename = saveFile;
					setTitle(TITLE + " - " + currentFilename);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
	}
}
