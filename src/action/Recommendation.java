package action;

import java.util.ArrayList;
import java.util.Comparator;

import entertainment.Genre;
import entertainment.GenreList;
import entertainment.Video;
import main.Database;
import utils.Utils;

public class Recommendation extends Action {
	private String user;
	private Genre genre;

	public Recommendation(int actionId, String actionType, String type, 
													String user, String genre) {
		super(actionId, actionType, type);
		this.user = user;
		this.genre = null;
		if (genre != null)
			this.genre = Utils.stringToGenre(genre);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	/**
     * Executa cautarea dorita de utilizator si intoarce prin intermediul
     * mesajului rezultatul cautarii. Un utilizator care nu are subscriptie
     * premium nu poate sa utilizeze cautarile premium
     */
	public void execute(Database db) {
		db.updateShows();
		db.updateGenres();
		
		if (this.getType().equals("standard")) {
			this.getNotViewedShow(db, db.getShows(), "Standard");
			return;
		} else if (this.getType().equals("best_unseen")) {
			this.getRecommendationBestUnseen(db);
			return;
		}
		
		if (db.getUser(user).getSubscriptionType().equals("PREMIUM")) {
			if (this.getType().equals("popular")) {
				this.getRecommendationPopular(db);
			} else if (this.getType().equals("favorite")) {
				this.getRecommendationFavorite(db);
			} else {
				this.getRecommendationSearch(db);
			}
			
			return;
		}
		
		this.message = "PopularRecommendation cannot be applied!";
	}
	
	/**
     * Intoarce 1 daca a gasit un video nevazut, altfel intoarce 0 si
     * modifica mesajul corepunzator starii
     */
	private int getNotViewedShow(Database db, ArrayList<Video> shows, 
																String type) {
		for (Video show : shows) {
			String title = show.getTitle();
			if (!db.getUser(user).getHistory().containsKey(title)) {
				this.message = type + "Recommendation result: " + title;
				return 1;
			}
		}
		
		this.message = type + "Recommendation cannot be applied!";
		return 0;
	}
	
	/**
     * Clasa de tip Comparator care compara evaluarea a 2 video-uri, daca cele
     * 2 video-uri au acelasi scor se pastreaza pozitia relativa din baza de
     * date
     */
	private class RatingComparator implements Comparator<Video> {
		@Override
		public int compare(Video video1, Video video2) {
			if (video1.getRating().compareTo(video2.getRating()) == 0)
				return video2.getVideoId().compareTo(video1.getVideoId());
			
			return video1.getRating().compareTo(video2.getRating());
	    }
	}
	
	/**
     * Compara doua genuri dupa numarul total de vizualizari
     */
	private class GenreViewComparator implements Comparator<GenreList> {
		@Override
		public int compare(GenreList genre1, GenreList genre2) {		
			return genre1.getViews().compareTo(genre2.getViews());
	    }
	}
	
	private class FavoriteComparator implements Comparator<Video> {
		@Override
		public int compare(Video video1, Video video2) {
			if (video1.getNumberOfFavorites().compareTo(video2.getNumberOfFavorites()) == 0)
				return video2.getVideoId().compareTo(video1.getVideoId());
			
			return video1.getNumberOfFavorites().compareTo(video2.getNumberOfFavorites());
	    }
	}
	
	/**
     * Intoarce video-ul cu scorul cel mai bun dintre cele nevazute de
     * utilizator
     */
	private void getRecommendationBestUnseen(Database db) {
		ArrayList<Video> shows = db.getShows();
		shows.sort(new RatingComparator().reversed());

		this.getNotViewedShow(db, db.getShows(), "BestRatedUnseen");
	}
	
	/**
     * Cauta primul video nevizualizat din cel mai popular gen si prin
     * intermediul metodei getNotViewedShow scrie rezultatul in mesaj. Daca 
     * nu s-a gasit niciun video nevizualizat in cel mai popular gen, atunci 
     * se cauta in al doilea cel mai popular gen, daca nici acolo se ia 
     * urmatorul gen pana fie gaseste un show nevazut sau da eroare
     */
	private void getRecommendationPopular(Database db) {
		db.getGenres().sort(new GenreViewComparator().reversed());
		for (GenreList genre : db.getGenres()) {
			if (this.getNotViewedShow(db, genre.getShows(), "Popular") == 1)
				return;
		}
	}
	
	/**
     * Intoarce prin intermediul mesajului cel mai placut video care e lista de 
     * favorite a cel putin 1 utilizator, daca nu exista un astfel de video 
     * nu intoarce niciun rezultat
     */
	private void getRecommendationFavorite(Database db) {
		ArrayList<Video> showsFiltered = new ArrayList<Video>(db.getShows());
		showsFiltered.sort(new FavoriteComparator().reversed());
		for (Video show : db.getShows()) {
			if (show.getNumberOfFavorites() == 0)
				showsFiltered.remove(show);
		}

		this.getNotViewedShow(db, db.getShows(), "Favorite");
	}
	
	private void getRecommendationSearch(Database db) {
		if (this.genre == null) {
			this.message = "SearchRecommendation cannot be applied!";
			return;
		}
		
		GenreList genreListSearch = new GenreList();
		for (GenreList genreList : db.getGenres()) {
			genreListSearch = genreList;
			if(this.genre.equals(genreList.getName())) {
				break;
			}
		}
		
		// creez lista cu show-urile nevazute
		ArrayList<Video> showsFromGenre = new ArrayList<Video>();
		for (Video show : genreListSearch.getShows()) {
			String title = show.getTitle();
			if (!db.getUser(user).getHistory().containsKey(title)) {
				showsFromGenre.add(show);
			}
		}
		
		if (showsFromGenre.isEmpty()) {
			this.message = "SearchRecommendation cannot be applied!";
			return;
		}
		
		// ma folosesc de comparatorul deja creat in Query
		Query query = new Query();
		showsFromGenre.sort(query.getRatingComparator());
		this.message = query.createMessageShow(showsFromGenre, 
								"SearchRecommendation", showsFromGenre.size());
	}
}
