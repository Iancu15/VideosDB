package action;

import main.Database;

public class Recommendation extends Action {
	private String user;
	private String genre;

	public Recommendation(int actionId, String actionType, String type, 
													String user, String genre) {
		super(actionId, actionType, type);
		this.user = user;
		this.genre = genre;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public void execute(Database db) {
	}
}
