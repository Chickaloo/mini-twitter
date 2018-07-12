// A normal post.
public class TextMessage implements Message {

	private String user;
	private String message;
	private String date;

	public TextMessage(String date, String message, String user) {
		this.date = date;
		this.user = user;
		this.message = message;
	}

	public void editMessage(String s) {
		message = s;
	}

	public String getMessage() {
		return message;
	}

	public String getKey() {
		return String.format("%s-%s", date, user);
	}

	public String toString() {
		return String.format("%s %s : %s", date, user, message);
	}
}
