package entertainment;

import java.util.ArrayList;

public class Movie extends Video {
	/**
     * Duration in minutes of a movie
     */
	private int duration;
	private int rating;
	
	public Movie(String title, int year, int duration, ArrayList<String> cast,
							                         ArrayList<String> genres) {
		super(title, year, cast, genres);
		this.duration = duration;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
}
