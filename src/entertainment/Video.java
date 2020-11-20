package entertainment;

import java.util.ArrayList;

import main.Database;
import user.User;

public abstract class Video {
	private String title;
	private Integer year;
	private ArrayList<String> cast;
	private ArrayList<String> genres;
	/**
     * Media notelor primite de catre video
     */
	protected Double rating;
	/**
     * Numarul de vizualizari ale video-ului de catre utilizatori
     */
	protected Integer numberOfViews;
	/**
     * Numarul de utilizatori care au selectat video-ul drept favorit
     */
	protected Integer numberOfFavorites;
	/**
     * Durata in minute a show-ului
     */
	protected Integer duration;
	
	public Video(String title, int year, ArrayList<String> cast,
				                                     ArrayList<String> genres) {
		this.title = title;
		this.year = year;
		this.cast = cast;
		this.genres = genres;
		this.rating = 0.0;
		this.numberOfFavorites = 0;
		this.numberOfViews = 0;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
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
	
    public Integer getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(Integer numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public Integer getNumberOfFavorites() {
		return numberOfFavorites;
	}

	public void setNumberOfFavorites(Integer numberOfFavorites) {
		this.numberOfFavorites = numberOfFavorites;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
     * Calculeaza media notelor primite de catre video de la utilizatori
     */
	public abstract Double calculateRating();
	
	/**
     * Calculeaza numarul total de vizionari primite de la utilizatori
     */
	public void calculateViews(Database db) {
		int numberOfViews = 0;
		for (User user : db.getUsers().values()) {
			numberOfViews += user.getHistory().getOrDefault(this.title, 0);
		}
		
		this.numberOfViews = numberOfViews;
	}
	
	/**
	 * Calculeaza numarul de selectii drept favorit ale unui show in functie
	 * de lista de favorite a utilizatorilor la momentul curent
	 */
	public void calculateFavorites(Database db) {
		int numberOfFavorites = 0;
		for (User user : db.getUsers().values()) {
			if (user.getFavoriteShows().contains(title))
				numberOfFavorites++;
		}
		
		this.numberOfFavorites = numberOfFavorites;
	}
}
