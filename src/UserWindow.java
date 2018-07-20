import java.awt.GridLayout;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

import javax.swing.*;

// Window that manages a user's account.
class UserWindow implements Window {
	private String userID;
	private User u;
	private static Database db;
	private JFrame mainFrame;
	private JTextField followLabel;
	private JTextField tweetLabel;
	private JLabel creationLabel;
	private JLabel updateLabel;
	private JTextArea following;
	private JTextArea newsFeed;

	public UserWindow(String userID) {
		this.userID = userID;
		this.db = Database.getConnection();
		this.u = db.userGetByID(userID);
	}

	@Override
	public void launch() {
		u.setHost(this);

		creationLabel = new JLabel();
		creationLabel.setText(String.format("Created at: %d", this.u.getCreationTime()));
		updateLabel = new JLabel();

		followLabel = new JTextField();
		JButton followButton = new JButton("Follow User");
		followButton.addActionListener(new FollowActionListener());
		following = new JTextArea();
		following.setEditable(false);

		tweetLabel = new JTextField();
		JButton tweetButton = new JButton("Send Tweet");
		tweetButton.addActionListener(new TweetActionListener());
		newsFeed = new JTextArea();
		newsFeed.setEditable(false);

		JScrollPane followView = new JScrollPane(following);
		JScrollPane feedView = new JScrollPane(newsFeed);

		mainFrame = new JFrame(userID+"'s Home");
		mainFrame.setSize(300, 480);

		GridLayout mainFrameLayout = new GridLayout(6,1,10,10);
		GridLayout followPanelLayout = new GridLayout(1,2,10,10);
		GridLayout tweetPanelLayout = new GridLayout(1,2,10,10);

		JPanel followPanel = new JPanel();
		JPanel tweetPanel = new JPanel();

		mainFrame.setLayout(mainFrameLayout);
		followPanel.setLayout(followPanelLayout);
		tweetPanel.setLayout(tweetPanelLayout);

		followPanel.add(followLabel);
		followPanel.add(followButton);

		tweetPanel.add(tweetLabel);
		tweetPanel.add(tweetButton);

		mainFrame.add(followPanel);
		mainFrame.add(followView);
		mainFrame.add(tweetPanel);
		mainFrame.add(feedView);
		mainFrame.add(creationLabel);
		mainFrame.add(updateLabel);

		update();

		mainFrame.setVisible(true);
	}

	public void update() {
		following.setText(u.followsToString());
		newsFeed.setText(u.messagesToString());

		updateLabel.setText(String.format("Updated at: %d", this.u.getLastUpdateTime()));
	}

	class FollowActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			User target = db.userGetByID(followLabel.getText());
			if (target != null) {
				u.followUser(target);
				followLabel.setText("");
			} else {
				followLabel.setText("Invalid User ID");
			}
		}
	}

	class TweetActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    Date date = new Date();
		    Message tx = new TextMessage(formatter.format(date),tweetLabel.getText(),userID);
			u.postUpdates(tx);
			db.messageCreate(tx.getKey(), tx);
			tweetLabel.setText("");
		}
	}
}
