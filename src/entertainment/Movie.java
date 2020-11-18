package entertainment;

import java.util.ArrayList;

public class Movie extends Video {
	/**
     * Duration in minutes of a movie
     */
	private int duration;
	private ArrayList<Double> ratings;
	
	public Movie(String title, int year, int duration, ArrayList<String> cast,
							                         ArrayList<String> genres) {
		super(title, year, cast, genres);
		this.duration = duration;
		this.ratings = new ArrayList<Double>();
	}
	
	public int getDuration() {
		return duration;
	}
	
	public ArrayList<Double> getRating() {
		return ratings;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setRating(ArrayList<Double> rating) {
		this.ratings = rating;
	}
	
	public void giveRating(Double grade) {
		this.ratings.add(grade);
	}
}
