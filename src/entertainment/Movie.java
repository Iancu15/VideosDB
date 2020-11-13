package entertainment;

public class Movie extends Video {
	/**
     * Duration in minutes of a movie
     */
	private int duration;
	private int rating;
	
	public Movie(String title, int release_year, int duration, int rating) {
		super(title, release_year);
		this.duration = duration;
		this.rating = rating;
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
