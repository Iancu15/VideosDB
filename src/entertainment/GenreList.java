package entertainment;

import java.util.ArrayList;

/**
 * In clasa se stocheaza toate video-urile ce au genre-ul aferent si numarul
 * total de vizualizari ale acestor video-uri
 */
public class GenreList {
	private Genre name;
	private ArrayList<Video> shows;
	private Integer views;
	
	public GenreList(Genre name) {
		this.name = name;
		this.shows = new ArrayList<Video>();
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

	public ArrayList<Video> getShows() {
		return shows;
	}

	public void setShows(ArrayList<Video> shows) {
		this.shows = shows;
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
		for (Video show : this.shows) {
			numberOfViews += show.getNumberOfViews();
		}
		
		this.views = numberOfViews;
	}
}
