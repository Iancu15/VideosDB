package action;

public class Command extends Action {
	private String user;
	private String title;
	
	public Command(int actionId, String type, String actionType, String user, 
																 String title) {
		super(actionId, actionType, type);
		this.user = user;
		this.title = title;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
