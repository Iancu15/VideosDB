package action;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import actor.Actor;
import entertainment.Genre;
import entertainment.Video;
import main.Database;
import user.User;
import utils.Utils;

public class Query extends Action {
	private Integer number;
	private String sortType;
	private String criteria;
	private Filter filters;
	
	public Query(int actionId, String actionType, String type, int number, 
										  String sortType, String criteria, 
										  		List<List<String>> filters) {
		super(actionId, actionType, type);
		this.number = number;
		this.sortType = sortType;
		this.criteria = criteria;
		
		String year = isNull(filters, 0);
		String genre = isNull(filters, 1);
		this.filters = new Filter(year, genre, filters.get(2), filters.get(3));
	}

	public Query() {
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getsortType() {
		return sortType;
	}

	public void setsortType(String sortType) {
		this.sortType = sortType;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public Filter getFilters() {
		return filters;
	}

	public void setFilters(Filter filters) {
		this.filters = filters;
	}
	
	/**
     * Se foloseste in constructor pentru year si genre la filters
     * in caz ca filters are totul null imi da ExceptionOutofBound
     * in acel caz year sau genre trebuie facute null
     */
	private String isNull(List<List<String>> filters, int index) {
		try {
			if (filters.get(index).isEmpty())
				return null;
			
			return filters.get(index).get(0);
		} catch (Exception ExceptionOutOfBound) {
			return null;
		}
	}
	
	/**
     * Executa o cautare in functie de tipul de obiect specificat
     */
	public void execute(Database db) {
		db.updateShows();
		db.updateActors();
		
		if (this.getType().equals("actors")) {
			this.sortActors(db.getActors());
		} else if (this.getType().equals("users")) {
			this.sortUsers(new ArrayList<User>(db.getUsers().values()));
		} else if (this.getType().equals("movies")){
			this.sortShows(new ArrayList<Video>(db.getMovies().values()));
		} else {
			this.sortShows(new ArrayList<Video>(db.getSerials().values()));
		}
	}
	
	/**
     * Clasa de tip Comparator care compara evaluarea a 2 actori, daca cei
     * 2 actori au acelasi scor se compara lexicografic dupa nume
     */
	private class ActorRatingComparator implements Comparator<Actor> {
		@Override
		public int compare(Actor actor1, Actor actor2) {
			if (actor1.getRating().compareTo(actor2.getRating()) == 0)
				return actor1.getName().compareTo(actor2.getName());
			
			return actor1.getRating().compareTo(actor2.getRating());
	    }
	}
	
	/**
     * Clasa de tip Comparator care compara evaluarea a 2 video-uri, daca cele
     * 2 video-uri au acelasi scor se compara lexicografic dupa titlu
     */
	private class ShowRatingComparator implements Comparator<Video> {
		@Override
		public int compare(Video video1, Video video2) {
			if (video1.getRating().compareTo(video2.getRating()) == 0)
				return video1.getTitle().compareTo(video2.getTitle());
			
			return video1.getRating().compareTo(video2.getRating());
	    }
	}
	
	/**
     * Returneaza un ShowRatingComparator, se foloseste in Recommendation
     */
	public Comparator<Video> getRatingComparator() {
		return new ShowRatingComparator();
	}
	
	/**
     * Clasa de tip Comparator care compara numele a 2 actori lexicografic
     */
	private class NameComparator implements Comparator<Actor> {
		@Override
		public int compare(Actor actor1, Actor actor2) {
	        return actor1.getName().compareTo(actor2.getName());
	    }
	}
	
	/**
     * Clasa de tip Comparator care compara numarul de premii castigate de 2
     * actori, in cazul in care au castigat la fel de multe premii se compara
     * lexicografic numele lor
     */
	private class AwardsComparator implements Comparator<Actor> {
		@Override
		public int compare(Actor actor1, Actor actor2) {
			if (actor1.getNumberOfAwards().compareTo(actor2.getNumberOfAwards()) == 0)
				return actor1.getName().compareTo(actor2.getName());
			
			return actor1.getNumberOfAwards().compareTo(actor2.getNumberOfAwards());
	    }
	}
	
	/**
     * Clasa de tip Comparator care compara durata a 2 video-uri, daca cele
     * 2 video-uri au aceeasi durata se compara lexicografic dupa titlu
     */
	private class DurationComparator implements Comparator<Video> {
		@Override
		public int compare(Video video1, Video video2) {
			if (video1.getDuration().compareTo(video2.getDuration()) == 0)
				return video1.getTitle().compareTo(video2.getTitle());
			
			return video1.getDuration().compareTo(video2.getDuration());
	    }
	}
	
	/**
     * Clasa de tip Comparator care compara numarul de selectari drept favorit
     * a 2 video-uri, daca cele 2 video-uri sunt la fel de placute se compara 
     * lexicografic dupa titlu
     */
	private class FavoriteComparator implements Comparator<Video> {
		@Override
		public int compare(Video video1, Video video2) {
			if (video1.getNumberOfFavorites().compareTo(video2.getNumberOfFavorites()) == 0)
				return video1.getTitle().compareTo(video2.getTitle());
			
			return video1.getNumberOfFavorites().compareTo(video2.getNumberOfFavorites());
	    }
	}
	
	/**
     * Clasa de tip Comparator care compara numarul de vizualizari a 2 video-uri
     * , daca cele 2 video-uri au acelasi numar de vizualizari se compara 
     * lexicografic dupa titlu
     */
	private class ViewsComparator implements Comparator<Video> {
		@Override
		public int compare(Video video1, Video video2) {
			if (video1.getNumberOfViews().compareTo(video2.getNumberOfViews()) == 0)
				return video1.getTitle().compareTo(video2.getTitle());
			
			return video1.getNumberOfViews().compareTo(video2.getNumberOfViews());
	    }
	}
	
	/**
     * Clasa de tip Comparator care numarul de evaluari oferite de 2 utilizatori
     * , daca cei 2 utilizatori sunt la fel de critici atunci se compara
     * lexicografic dupa username
     */
	private class NumberOfRatingsComparator implements Comparator<User> {
		@Override
		public int compare(User user1, User user2) {
			if (user1.getNumberOfRatings().compareTo(user2.getNumberOfRatings()) == 0)
				return user1.getUsername().compareTo(user2.getUsername());
			
			return user1.getNumberOfRatings().compareTo(user2.getNumberOfRatings());
	    }
	}
	
	/**
     * Creeaza mesajul ce va reprezenta raspunsul cautarii utilizatorului pentru
     * o interogare dupa actori
     */
	private void createMessageActor(ArrayList<Actor> actors) {
		ArrayList<Actor> actorNames = new ArrayList<Actor>();
		if (this.number == null) {
			this.number = actors.size();
		}
		
		for (int i = 0; i < this.number; i++) {
			if (i == actors.size())
				break;
			
			actorNames.add(actors.get(i));
		}
		
		this.message = "Query result: " + actorNames.toString();
	}
	
	/**
     * Intoarce un sir cu titlurile primelor number video-uri din lista
     * primita ca parametru. Parametrul type ajuta in intoarcerea unui mesaj
     * personalizat pentru a fi folosit si in alte comenzi (Ex: Recommendation)
     */
	public String createMessageShow(ArrayList<Video> shows, String type,
																int number) {
		ArrayList<String> showNames = new ArrayList<String>();
		for (int i = 0; i < number; i++) {
			if (i == shows.size())
				break;
			
			showNames.add(shows.get(i).getTitle());
		}
		
		return type + " result: " + showNames.toString();
	}
	
	/**
     * Creeaza mesajul ce va reprezenta raspunsul cautarii utilizatorului pentru
     * o interogare dupa alti utilizatori
     */
	private void createMessageUser(ArrayList<User> users) {
		ArrayList<String> userNames = new ArrayList<String>();
		for (int i = 0; i < this.number; i++) {
			if (i == users.size())
				break;
			
			userNames.add(users.get(i).getUsername());
		}
		
		this.message = "Query result: " + userNames.toString();
	}
	
	/**
     * Sorteaza actorii in functie de criteriu si creeaza mesajul dorit de
     * utilizator
     */
	public void sortActors(ArrayList<Actor> actors) {
		ArrayList<Actor> sortedActors;
		if (this.criteria.equals("average")) {
			sortedActors = this.sortActorsAverage(actors);
		} else if (this.criteria.equals("awards")) {
			sortedActors = this.sortActorsAwards(actors);
		} else {
			sortedActors = this.sortActorsFilters(actors);
		}
		
		this.createMessageActor(sortedActors);
	}
	
	/**
     * Sorteaza crescator sau descrescator o lista de actori in functie de
     * evaluarea primita. Nu se iau in considerare actorii fara evaluari
     */
	private ArrayList<Actor> sortActorsAverage(ArrayList<Actor> actors) {
		ArrayList<Actor> ratedActors = new ArrayList<Actor>();
		for (Actor actor : actors) {
			if (actor.getRating() != 0)
				ratedActors.add(actor);
		}
		
		if (this.sortType.equals("asc"))
			ratedActors.sort(new ActorRatingComparator());
		else
			ratedActors.sort(new ActorRatingComparator().reversed());
		
		return ratedActors;
	}
	
	/**
     * Sorteaza actorii, care au castigat premiile specificate, lexicografic
     */
	private ArrayList<Actor> sortActorsAwards(ArrayList<Actor> actors) {
		ArrayList<Actor> actorsWithAwards = new ArrayList<Actor>();
		for (Actor actor : actors) {
			int hasAward = 1;
			for (String award : this.filters.getAwards()) {
				if (!actor.getAwards().containsKey(Utils.stringToAwards(award))) {
					hasAward = 0;
					break;
				}
			}
			
			if (hasAward == 1)
				actorsWithAwards.add(actor);
		}
		
		if (this.sortType.equals("asc"))
			actorsWithAwards.sort(new AwardsComparator());
		else
			actorsWithAwards.sort(new AwardsComparator().reversed());
		
		return actorsWithAwards;
	}
	
	/**
     * Sorteaza actorii, a caror descriere foloseste cuvintele specificate,
     * lexicografic
     */
	private ArrayList<Actor> sortActorsFilters(ArrayList<Actor> actors) {
		ArrayList<Actor> actorsWithWords = new ArrayList<Actor>();
		for (Actor actor : actors) {
			int hasWord = 1;
			for (String word : this.filters.getWords()) {
				hasWord = 0;
				// split imparte dupa space, cratima, punct si virgula
				for(String wordCopy : 
								actor.getCareerDescription().split("[ -.,]")) {
					if (wordCopy.toLowerCase().equals(word)) {
						hasWord = 1;
						break;
					}
				}

				if (hasWord == 0)
					break;
			}
			
			if (hasWord == 1)
				actorsWithWords.add(actor);
		}
		
		if (this.sortType.equals("asc"))
			actorsWithWords.sort(new NameComparator());
		else
			actorsWithWords.sort(new NameComparator().reversed());
		
		return actorsWithWords;
	}
	
	/**
     * Sorteaza video-urile care respecta anul si genul in functie de
     * criteriul specificat
     */
	public void sortShows(ArrayList<Video> shows) {
		ArrayList<Video> showsFiltered = new ArrayList<Video>();
		for (Video show : shows) {
			if (!show.getYear().toString().equals(this.filters.getYear())
					&& this.filters.getYear() != null || this.filters.getError())
				continue;
			
			if (this.filters.getGenre() != null) {
				for (Genre genre : show.getGenres()) {
					if (genre.equals(this.filters.getGenre())) {
						showsFiltered.add(show);
						break;
					}
				}
			} else {
				showsFiltered.add(show);
			}
		}
		
		// in cazul in care trebuie interogate show-urile favorite elimin
		// show-urile care nu sunt favorate de nimeni
		if (this.criteria.equals("favorite")) {
			for (Video show : shows) {
				if (show.getNumberOfFavorites() == 0)
					showsFiltered.remove(show);
			}
		}
		
		// in cazul in care trebuie interogate show-urile cu cele mai multe
		// vizionari elimin pe cele care n-au fost vizionate
		if (this.criteria.equals("most_viewed")) {
			for (Video show : shows) {
				if (show.getNumberOfViews() == 0)
					showsFiltered.remove(show);
			}
		}
		
		Comparator<Video> comparator;
		if (this.criteria.equals("ratings")) {
			comparator = new ShowRatingComparator();
		} else if (this.criteria.equals("favorite")) {
			comparator = new FavoriteComparator();
		} else if (this.criteria.equals("longest")) {
			comparator = new DurationComparator();
		} else {
			comparator = new ViewsComparator();
		}
		
		if (this.sortType.equals("asc"))
			showsFiltered.sort(comparator);
		else
			showsFiltered.sort(comparator.reversed());
		
		this.message = this.createMessageShow(showsFiltered, "Query", 
																   this.number);
	}
	
	/**
     * Sorteaza utilizatorii dupa numarul de evaluari date video-urilor, daca
     * utilizatorul nu a evaluat nimic atunci nu va aparea
     */
	public void sortUsers(ArrayList<User> users) {
		ArrayList<User> usersFiltered = new ArrayList<User>();
		for (User user : users) {
			if (user.getNumberOfRatings() == 0)
				continue;
			
			usersFiltered.add(user);
		}
		
		if (this.sortType.equals("asc"))
			usersFiltered.sort(new NumberOfRatingsComparator());
		else
			usersFiltered.sort(new NumberOfRatingsComparator().reversed());
		
		this.createMessageUser(usersFiltered);
	}
}
