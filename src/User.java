import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;

import javax.swing.tree.TreeNode;

// Observer model
public class User extends AbstractUserNode{
	private String name;
	private AbstractUserNode parent;
	private List<User> subscriptions;
	private List<User> subscribers;
	private List<Message> feed;
	private Window host;

	public User(String name) {
		this.name = name;

		subscriptions = new ArrayList<User>();

		subscribers = new ArrayList<User>();
		subscribers.add(this);
		feed = new ArrayList<Message>();
	}

	public void postUpdates(Message m) {
		for (User u : subscribers) {
			u.updateFeed(m);
		}
		if (host != null) {
			host.update();
		}
	}

	public void followUser(User u) {
		u.acceptUser(this);
		subscriptions.add(u);
		if (host != null) {
			host.update();
		}

	}

	public void acceptUser(User u) {
		subscribers.add(u);
		if (host != null) {
			host.update();
		}
	}

	public void updateFeed(Message m) {
		feed.add(m);
		if (host != null) {
			host.update();
		}
	}

	public String toString() {
		return name;
	}

	public void setParent(AbstractUserNode p) {
		parent = p;
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Enumeration<User> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public int getIndex(TreeNode node) {
		return 0;
	}


	@Override
	public void add(AbstractUserNode t) {
		parent.add(t);

	}

	public String followsToString() {
		String ret = "Following:\n";
		for (User u : subscriptions) {
			ret += u.toString() + "\n";
		}
		return ret;
	}

	public String messagesToString() {
		String ret = "Twitter Feed:\n";
		for (Message m : feed) {
			ret += m.toString() + "\n";
		}
		return ret;
	}

	public void setHost(Window h) {
		this.host = h;
	}
}
