package action;

import entertainment.Video;
import main.Database;
import user.User;

public class Command extends Action {
	private String username;
	private String title;
	private Double grade;
	private int season;
	
	public Command(int actionId, String type, String actionType, String user, 
									   String title, Double grade, int season) {
		super(actionId, actionType, type);
		this.username = user;
		this.title = title;
		this.season = season;
		this.grade = grade;
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
	
	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
     * Executa comanda in functie de tipul comenzii si creeaza mesajul
     * instantei curente
     */
	public void execute(Database db) {
		User user = db.getUser(this.username);
		Video show = db.getShow(this.title);
		
		if (this.getType().equals("view"))
			this.message = user.viewVideo(this.title);
		else if (this.getType().equals("favorite"))
			this.message = user.addFavourite(title);
		else
			this.message = user.giveRating(show, this.grade, season);
	}
}
