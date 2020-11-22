package entertainment;

import java.util.ArrayList;

import main.Database;
import user.User;
import utils.Utils;

public abstract class Video {
	private String title;
	private Integer year;
	private ArrayList<String> cast;
	private ArrayList<Genre> genres;
	/**
     * Pozitia video-ului in baza de date
     */
	private Integer VideoId;
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
     * Durata in minute a show-ului (la seriale se ia doar durata
     * unui episod pe sezon)
     */
	protected Integer duration;
	
	public Video(String title, int year, ArrayList<String> cast,
				                    ArrayList<String> genres, Integer VideoId) {
		this.title = title;
		this.year = year;
		this.cast = cast;
		this.genres = this.stringstoGenres(genres);
		this.VideoId = VideoId;
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

	public ArrayList<Genre> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<Genre> genres) {
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

	public Integer getVideoId() {
		return VideoId;
	}

	public void setVideoId(Integer videoId) {
		VideoId = videoId;
	}

	public void setYear(Integer year) {
		this.year = year;
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
	
	/**
	 * Transforma o lista genuri tip string intr-o lista de genuri tip Genre
	 */
	public ArrayList<Genre> stringstoGenres(ArrayList<String> genreStrings) {
		ArrayList<Genre> genres = new ArrayList<Genre>();
		for (String genreString : genreStrings) {
			genres.add(Utils.stringToGenre(genreString));
		}
		
		return genres;
	}
	
	/**
	 * La afisare intoarce titlul video-ului
	 */
	@Override
	public String toString() {
		return this.getTitle() + " " + this.getVideoId() + "\n";
	}
}
