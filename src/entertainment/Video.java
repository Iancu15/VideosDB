package entertainment;

import java.util.ArrayList;

public abstract class Video {
	private String title;
	private int year;
	private ArrayList<String> cast;
	private ArrayList<String> genres;
	/**
     * Media notelor primite de catre video
     */
	protected Double rating;
	
	public Video(String title, int year, ArrayList<String> cast,
				                                     ArrayList<String> genres) {
		this.title = title;
		this.year = year;
		this.cast = cast;
		this.genres = genres;
		this.rating = 0.0;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public ArrayList<String> getCast() {
		return cast;
	}

	public void setCast(ArrayList<String> cast) {
		this.cast = cast;
	}

	public ArrayList<String> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}
	
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
    /**
     * Calculeaza media notelor primite de catre video de la utilizatori
     */
	public abstract Double calculateRating();
}
