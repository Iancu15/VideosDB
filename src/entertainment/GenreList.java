package entertainment;

import java.util.ArrayList;

/**
 * In clasa se stocheaza toate video-urile ce au genre-ul aferent si numarul
 * total de vizualizari ale acestor video-uri
 */
public class GenreList {
	private Genre name;
	private ArrayList<Video> movies;
	private ArrayList<Video> serials;
	private Integer views;
	
	public GenreList(Genre name) {
		this.name = name;
		this.movies = new ArrayList<Video>();
		this.serials = new ArrayList<Video>();
		this.views = 0;
	}
	
	public GenreList() {
		
	}

	public Genre getName() {
		return name;
	}

	public void setName(Genre name) {
		this.name = name;
	}

	public ArrayList<Video> getMovies() {
		return movies;
	}

	public void setMovies(ArrayList<Video> movies) {
		this.movies = movies;
	}

	public ArrayList<Video> getSerials() {
		return serials;
	}

	public void setSerials(ArrayList<Video> serials) {
		this.serials = serials;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}
	
	/**
	 * Calculeaza numarul total de vizualizari ale genului respectiv
	 * raportandu-se la starea curenta a bazei de date, presupune ca baza de
	 * date e actualizata
	 */
	public void calculateViews() {
		int numberOfViews = 0;
		for (Video show : this.movies) {
			numberOfViews += show.getNumberOfViews();
		}
		
		for (Video show : this.serials) {
			numberOfViews += show.getNumberOfViews();
		}
		
		this.views = numberOfViews;
	}
	
	/**
	 * Intoarce toate informatiile legate de clasa, se foloseste pentru debug
	 */
	@Override
	public String toString() {
		return this.getName() + " " + this.getViews() + "\n" + this.getMovies()
															+ this.getSerials();
	}
}
