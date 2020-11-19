package action;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import actor.Actor;
import entertainment.Video;
import main.Database;
import user.User;
import utils.Utils;

public class Query extends Action {
	private int number;
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
			
			return filters.get(index).get(index);
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
		} else if (this.getType().equals("movies")) {
			//
		} else if (this.getType().equals("shows")){
			//
		} else {
			//
		}
	}
	
	/**
     * Clasa de tip Comparator care compara evaluarea a 2 actori, daca cei
     * 2 actori au acelasi scor se compara lexicografic
     */
	private class RatingComparator implements Comparator<Actor> {
		@Override
		public int compare(Actor actor1, Actor actor2) {
			if (actor1.getRating().compareTo(actor2.getRating()) == 0)
				return actor1.getName().compareTo(actor2.getName());
			
			return actor1.getRating().compareTo(actor2.getRating());
	    }
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
			if (actor1.getRating().compareTo(actor2.getRating()) == 0)
				return actor1.getName().compareTo(actor2.getName());
			
			return actor1.getNumberOfAwards().compareTo(actor1.getNumberOfAwards());
	    }
	}
	
	/**
     * Sorteaza actorii in functie de criteriu si creeaza mesajul dorit de
     * utilizator
     */
	public void sortActors(ArrayList<Actor> actors) {
		ArrayList<Actor> sortedActors = new ArrayList<Actor>();
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
     * evaluarea primita de acestia
     */
	private ArrayList<Actor> sortActorsAverage(ArrayList<Actor> actors) {
		if (this.sortType.equals("asc"))
			actors.sort(new RatingComparator());
		else
			actors.sort(new RatingComparator().reversed());
		
		return actors;
	}
	
	/**
     * Creeaza mesajul ce va reprezenta raspunsul cautarii utilizatorului
     */
	private void createMessageActor(ArrayList<Actor> actors) {
		ArrayList<String> actorNames = new ArrayList<String>();
		for (int i = 0; i < this.number; i++) {
			if (i == actors.size())
				break;
			
			actorNames.add(actors.get(i).getName());
		}
		
		this.message = "Query result: " + actorNames.toString();
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
				if (!actor.getCareerDescription().toLowerCase().contains(word)) {
					hasWord = 0;
					break;
				}
			}
			
			if (hasWord == 1)
				actorsWithWords.add(actor);
		}
		
		if (this.sortType.equals("asc"))
			actors.sort(new NameComparator());
		else
			actors.sort(new NameComparator().reversed());
		
		return actorsWithWords;
	}
}
