package action;

import main.Database;
import user.User;

public class Command extends Action {
	private String username;
	private String title;
	private Double grade;
	
	public Command(int actionId, String type, String actionType, String user, 
												   String title, Double grade) {
		super(actionId, actionType, type);
		this.username = user;
		this.title = title;
	}

	public String getUser() {
		return username;
	}

	public void setUser(String user) {
		this.username = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}
	
	public void execute(Database db) {
		if (this.getType().equals("view")) {
			User user = db.getUser(this.username);
			this.message = user.viewVideo(this.title);
		}
	}
}
