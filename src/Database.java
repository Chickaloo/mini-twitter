import java.util.Hashtable;
import java.util.Map;

// Database holds "tables" for each of users, groups, and messages.
public class Database {
	private static Database db;
	private static Map<String, User> users;
	private static Map<String, UserGroup> groups;
	private static Map<String, Message> messages;

	private Database() {
		users = new Hashtable<String, User>();
		groups = new Hashtable<String, UserGroup>();
		messages = new Hashtable<String, Message>();
	}

	public static Database getConnection() {
		if (db == null) {
			synchronized(Database.class) {
				if (db == null) {
					db = new Database();
				}
			}
		}

		return db;
	}

	// Simulate CRUD operations for users
	public void userCreate(String id, User u) {
		users.put(id, u);
	}

	public boolean userContains(String id) {
		try {
			return users.containsKey(id);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public User userGetByID(String id) {
		return users.get(id);
	}

	public void userUpdate(String id, User u) {
		userCreate(id, u);
	}

	public void userDelete(String id) {
		users.remove(id);
	}

	public int userCount() {
		return users.size();
	}

	// Simulate CRUD operations for groups
	public void groupCreate(String id, UserGroup u) {
		groups.put(id, u);
	}

	public boolean groupContains(String id) {
		try {
			return groups.containsKey(id);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public UserGroup groupGetByID(String id) {
		return groups.get(id);
	}

	public void groupUpdate(String id, UserGroup u) {
		groupCreate(id, u);
	}

	public void groupDelete(String id) {
		groups.remove(id);
	}

	public int groupCount() {
		return groups.size();
	}

	// Simulate CRUD operations for messages
	public void messageCreate(String id, Message m) {
		messages.put(id, m);
	}

	public boolean messageContains(String id) {
		try {
			return groups.containsKey(id);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public Message messageGetByID(String id) {
		return messages.get(id);
	}

	public void messageUpdate(String id, Message m) {
		messageCreate(id, m);
	}

	public void messageDelete(String id) {
		messages.remove(id);
	}

	public int messageCount() {
		return messages.size();
	}

	public double positiveMessagePercent() {
		double count = 0;
		for (String id : messages.keySet()) {
			if (messages.get(id).getMessage().contains("meme")) {
				count++;
			}

		}
		return count/messageCount();
	}
}
