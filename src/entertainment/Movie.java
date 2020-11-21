package entertainment;

import java.util.ArrayList;

public class Movie extends Video {
	/**
     * O lista cu notele date de utilizatori
     */
	private ArrayList<Double> ratings;
	
	public Movie(String title, int year, int duration, ArrayList<String> cast,
							            ArrayList<String> genres, int MovieId) {
		super(title, year, cast, genres, MovieId);
		this.duration = duration;
		this.ratings = new ArrayList<Double>();
	}
	
	public ArrayList<Double> getRatings() {
		return ratings;
	}
	
	public void setRatings(ArrayList<Double> rating) {
		this.ratings = rating;
	}
	
	/**
     * Adauga o nota data de un utilizator, nu se specifica utilizatorul
     */
	public void giveRating(Double grade) {
		this.ratings.add(grade);
	}
	
	/**
     * Calculeaza media notelor date de utilizatori filmului
     */
    public Double calculateRating() {
    	if (this.ratings.isEmpty()) {
    		this.rating = 0.0;
    		return this.rating;
    	}
    	
    	Double ratingMovie = 0.0;
    	for (Double rating : this.ratings) {
    		ratingMovie += rating;
    	}
    	
    	this.rating = ratingMovie/this.ratings.size();
    	return this.rating;
    }
}
