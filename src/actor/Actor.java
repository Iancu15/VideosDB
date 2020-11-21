package actor;

import java.util.ArrayList;
import java.util.Map;

import entertainment.Video;
import main.Database;

public class Actor {
	private String name;
	private String careerDescription;
	private ArrayList<String> filmography;
	private Map<ActorsAwards, Integer> awards;
	/**
	 * Media scorurilor obtinute de filmele si serialele in care a jucat
	 */
	private Double rating;
	/**
	 * Numarul de premii obtinute de actor
	 */
	private Integer numberOfAwards = 0;
	
	public Actor(String name,  String careerDescription,
		    ArrayList<String> filmography,  Map<ActorsAwards, Integer> awards) {
		this.name = name;
		this.careerDescription = careerDescription;
		this.filmography = filmography;
		this.awards = awards;
		this.rating = 0.0;
		this.calculateAwards();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCareerDescription() {
		return careerDescription;
	}
	public void setCareerDescription(String careerDescription) {
		this.careerDescription = careerDescription;
	}
	public ArrayList<String> getFilmography() {
		return filmography;
	}
	public void setFilmography(ArrayList<String> filmography) {
		this.filmography = filmography;
	}
	public Map<ActorsAwards, Integer> getAwards() {
		return awards;
	}
	public void setAwards(Map<ActorsAwards, Integer> awards) {
		this.awards = awards;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getNumberOfAwards() {
		return numberOfAwards;
	}

	public void setNumberOfAwards(Integer numberOfAwards) {
		this.numberOfAwards = numberOfAwards;
	}

	/**
	 * Calculeaza scorul actorului curent facand media scorurilor obtinute
	 * de filmele si serialele in care a jucat
	 */
	public void calculateRating(Database db) {
		Double ratingActor = 0.0;
		int numberOfRatedShows = 0;
		for (String showTitle : filmography) {
			Video show = db.getShow(showTitle);
			if (show != null) {
				Double ratingShow = show.getRating();
				if (ratingShow != 0) {
					ratingActor += ratingShow;
					numberOfRatedShows++;
				}
			}
		}
		
		if (ratingActor != 0)
			this.rating = ratingActor/numberOfRatedShows;
	}
	
	/**
	 * Calculeaza numarul total de premii detinute de actor
	 */
	public void calculateAwards() {
		for (Integer numberOfAwards : this.awards.values()) {
			this.numberOfAwards += numberOfAwards;
		}
	}
	
	/**
	 * La afisare intoarce numele actorului
	 */
	@Override
	public String toString() {
		return this.getName();
	}
}
