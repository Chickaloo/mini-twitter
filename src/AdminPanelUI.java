import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

// Singleton implementation of an administration panel.
public class AdminPanelUI {

	private static AdminPanelUI adminPanel;
	private static Database db;
	private JFrame mainFrame;
	private AbstractUserNode focus;
	private JTree tree;
	private JLabel status;
	private JTextField userIDField;
	private JTextField groupIDField;

	private AdminPanelUI() {
		db = Database.getConnection();
		this.initUI();
	}

	public static void launch() {
		if (adminPanel == null) {
			synchronized(AdminPanelUI.class) {
				if (adminPanel == null) {
					adminPanel = new AdminPanelUI();
				}
			}
		}
		adminPanel.showUI();
	}

	private void showUI() {
		mainFrame.setVisible(true);
	}

	// Adds all the parts to the jFrame
	private void initUI() {
		UserGroup root = new UserGroup("Root");
		focus = root;
		tree = new JTree(root);
		db.groupCreate("Root", root);

		status = new JLabel();
		tree.addTreeSelectionListener(new UserTreeListener());
		JScrollPane treeView = new JScrollPane(tree);

		mainFrame = new JFrame("Mini Twitter Admin Panel");
		mainFrame.setSize(640, 480);

		GridLayout mainFrameLayout = new GridLayout(1,2,10,10);

		mainFrame.setLayout(mainFrameLayout);
		mainFrame.add(treeView);

		// Control Panel, Top, Middle, Bottom
		JPanel cPanel = new JPanel();
		JPanel tCPanel = new JPanel();
		JPanel mCPanel = new JPanel();
		JPanel bCPanel = new JPanel();

		GridLayout tCPanelLayout = new GridLayout(2,2,10,10);
		GridLayout mCPanelLayout = new GridLayout(1,1,10,10);
		GridLayout bCPanelLayout = new GridLayout(3,2,10,10);

		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.PAGE_AXIS));
		tCPanel.setLayout(tCPanelLayout);
		mCPanel.setLayout(mCPanelLayout);
		bCPanel.setLayout(bCPanelLayout);

		userIDField = new JTextField();
		JButton addUserButton = new JButton("Add User");
		groupIDField = new JTextField();
		JButton addGroupButton = new JButton("Add Group");

		addUserButton.addActionListener(new UserActionListener());
		addGroupButton.addActionListener(new GroupActionListener());

		tCPanel.add(userIDField);
		tCPanel.add(addUserButton);
		tCPanel.add(groupIDField);
		tCPanel.add(addGroupButton);

		JButton openUserView = new JButton("Open User View");
		openUserView.addActionListener(new UserViewActionListener());
		mCPanel.add(openUserView);

		JButton userTotal = new JButton("Show User Total");
		JButton groupTotal = new JButton("Show Group Total");
		JButton messageTotal = new JButton("Show Messages Total");
		JButton positiveMessagePercentage = new JButton("Show Positive Percentage");
		JButton validateEntries = new JButton("Validate Entries");
		JButton showLastUpdated = new JButton("Show Last Updated");

		userTotal.addActionListener(new UserCountActionListener());
		groupTotal.addActionListener(new GroupCountActionListener());
		messageTotal.addActionListener(new MessageCountActionListener());
		positiveMessagePercentage.addActionListener(new PositiveMessageActionListener());
		validateEntries.addActionListener(new ValidateIDActionListener());
		showLastUpdated.addActionListener(new LastUpdateActionListener());

		bCPanel.add(userTotal);
		bCPanel.add(groupTotal);
		bCPanel.add(messageTotal);
		bCPanel.add(positiveMessagePercentage);
		bCPanel.add(validateEntries);
		bCPanel.add(showLastUpdated);

		//Add all components to the control panel
		cPanel.add(status);
		cPanel.add(tCPanel);
		cPanel.add(mCPanel);
		cPanel.add(bCPanel);

		mainFrame.add(cPanel);
	}

	// Listeners
	class UserTreeListener implements TreeSelectionListener {
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			focus = (AbstractUserNode) e.getPath().getLastPathComponent();
			status.setText("Selected Node: " + focus.toString());
		}

	}

	class UserActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			String id = userIDField.getText();

			if (!db.userContains(id)) {
				User u = new User(id);
				db.userCreate(id, u);
				focus.add(u);
			}

			DefaultTreeModel tm = (DefaultTreeModel) tree.getModel();
			tm.reload();

		}

	}

	class GroupActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			String id = groupIDField.getText();

			if (!db.groupContains(id)) {
				UserGroup u = new UserGroup(id);
				db.groupCreate(id, u);
				focus.add(u);
			}
			DefaultTreeModel tm = (DefaultTreeModel) tree.getModel();
			tm.reload();
		}
	}

	class UserViewActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(focus.isLeaf()) {
				UserWindow u = new UserWindow(focus.toString());
				u.launch();
			}
		}
	}

	class UserCountActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			status.setText("User Count: " + Integer.toString(db.userCount()));
		}
	}

	class GroupCountActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			status.setText("Group Count: " + Integer.toString(db.groupCount()));
		}
	}

	class MessageCountActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			status.setText("Message Count: " + Integer.toString(db.messageCount()));
		}
	}

	class PositiveMessageActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			status.setText("Positive Message Percent: " + Double.toString(db.positiveMessagePercent()*100));
		}
	}

	class ValidateIDActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			int invalid = 0;
			// Duplicates are already rejected by the DB
			ArrayList<AbstractUserNode> nameList = db.getTreeList();
			for (AbstractUserNode u : nameList) {
				if (u.toString().contains(" ")) {
					invalid++;
				}
			}

			status.setText(String.format("%d Invalid Entries Found", invalid));
		}
	}

	class LastUpdateActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			long latest = 0;
			AbstractUserNode lastUpdate = null;

			ArrayList<AbstractUserNode> nameList = db.getTreeList();
			for (AbstractUserNode u : nameList) {
				if (u.getLastUpdateTime() > latest) {
					latest = u.getLastUpdateTime();
					lastUpdate = u;
				}
			}

			if (lastUpdate == null) {
				status.setText("No Last Update Found");
			} else {
				status.setText(String.format("Last updated: %s at %d", lastUpdate.toString(), latest));

			}
		}
	}
}


