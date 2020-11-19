package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;

public class User {
	private String username;
	private String subscriptionType;
	private Map<String, Integer> history;
	private ArrayList<String> favoriteShows;
	
	/**
	 * O lista cu filmele care au primit nota de la utilizator
	 */
	private ArrayList<String> ratedMovies;
	
	/**
	 * Un hashmap cu cheia titlul serialului si valoarea sezoanele care au
	 * primit nota de la utilizator
	 */
	private Map<String, ArrayList<Integer>> ratedSerials;
	
	public User(String username, String subscriptionType, 
			   Map<String, Integer> history, ArrayList<String> favoriteShows) {
		super();
		this.username = username;
		this.subscriptionType = subscriptionType;
		this.history = history;
		this.favoriteShows = favoriteShows;
		this.ratedSerials = new HashMap<>();
		this.ratedMovies = new ArrayList<String>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public Map<String, Integer> getHistory() {
		return history;
	}

	public void setHistory(Map<String, Integer> history) {
		this.history = history;
	}

	public ArrayList<String> getFavoriteShows() {
		return favoriteShows;
	}

	public void setFavoriteShows(ArrayList<String> favoriteShows) {
		this.favoriteShows = favoriteShows;
	}

	/**
	 * Incrementeaza numarul de vizualizari daca a show-ul a fost deja
	 * vizualizat de user, in caz contrar il adauga in istoric cu numarul
	 * de vizionari 1
	 */
	public String viewVideo(String title) {
		Integer value = this.history.putIfAbsent(title, 1);
		
		if(value != null)
				this.history.replace(title, value + 1);
		
		return "success -> " + title + " was viewed with total views of " + 
														this.history.get(title);
	}
	
	/**
	 * Adauga un show la favorite daca n-a fost deja adaugat
	 */
	public String addFavourite(String title) {
		if (!this.favoriteShows.contains(title)) {
			this.favoriteShows.add(title);
			return "success -> " + title + " was added as favourite";
		}
		
		return "fail -> You already have that show as favourite";
	}
	
	/**
	 * Adauga titlul in lista filmelor care au primit nota daca utilizatorul
	 * a vazut filmul si nu i-a dat nota pana acum si afiseaza succes, in caz
	 * contrar afiseaza fail. Metoda este folosita de catre cealalta metoda
	 * giveRating
	 */
	private String giveRating(Video show, Double grade) {
		String title = show.getTitle();
		
		if (!this.history.containsKey(title)) 
			return "fail -> You didn't watch the movie";
		
		if (this.ratedMovies.contains(title))
			return "error -> " + title + " has been already rated";
		
		this.ratedMovies.add(title);
		Movie movie = (Movie) show;
		movie.giveRating(grade);
		
		return "success -> " + title + " was rated with " + grade + " by " +
															this.username;
	}
	
	/**
	 * Adauga sezonul in lista sezoanelor care au primit nota pentru serialul
	 * aferent, daca nu exista serialul adauga o intrare noua pentru el.
	 * In cazul in care serialul nu a fost vazut sau sezonul deja a primit nota
	 * nu face nimic si intoarce mesajul de fail, in caz contrar intoarce
	 * mesajul de succes. Daca primeste drept numarul sezonului 0 il considera
	 * film si apeleaza metoda pentru filme returnand mesajul functiei
	 */
	public String giveRating(Video show, Double grade, Integer season) {
		if (season == 0)
			return giveRating(show, grade);
		
		String title = show.getTitle();
					
		if (!this.history.containsKey(title)) 
			return "fail -> You didn't watch the serial";
		
		if (this.ratedSerials.containsKey(title)) {
			ArrayList<Integer> seasons = this.ratedSerials.get(title);
			if (seasons.contains(season))
				return "fail -> You already graded the season";
			
			seasons.add(season);
			this.ratedSerials.replace(title, seasons);
			Serial serial = (Serial) show;
			serial.giveRating(grade, season);
			return "success -> " + title + " was rated with " + grade + " by " +
			this.username;
		}
		
		ArrayList<Integer> seasons = new ArrayList<Integer>();
		seasons.add(season);
		this.ratedSerials.put(title, seasons);
		Serial serial = (Serial) show;
		serial.giveRating(grade, season);
		
		return "success -> " + title + " was rated with " + grade + " by " +
		this.username;
	}
}