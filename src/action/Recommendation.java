package action;

public class Recommendation extends Action {
	private String user;

	public Recommendation(int actionId, String actionType, String type, 
																String user) {
		super(actionId, actionType, type);
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
