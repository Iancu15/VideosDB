package main;

import java.util.ArrayList;
import java.util.Map;

import action.Action;
import actor.Actor;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.ClassInputData;
import fileio.Input;
import user.User;

/**
 * Stocheaza toate obiectele relevante proiectului: actori, useri, filme,
 * seriale si actiuni.
 */
public class Database {
	private ArrayList<Actor> actors;
	private Map<String, User> users;
	private Map<String, Movie> movies;
	private Map<String, Serial> serials;
	private ArrayList<Action> actions;
	
	public Database(Input input) {
		ClassInputData classInputData = new ClassInputData();
        
        this.actors = classInputData.getActors(input.getActors());
        this.users = classInputData.getUsers(input.getUsers());
        this.movies = classInputData.getMovies(input.getMovies());
        this.serials = classInputData.getSerials(input.getSerials());
        this.actions = classInputData.getActions(input.getCommands());
	}

	public ArrayList<Actor> getActors() {
		return actors;
	}

	public Map<String, User> getUsers() {
		return users;
	}

	public Map<String, Movie> getMovies() {
		return movies;
	}

	public Map<String, Serial> getSerials() {
		return serials;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}
	
	/**
	 * Returneaza obiectul de tip User specific username-ului
	 */
	public User getUser(String username) {
		return this.users.get(username);
	}
	
	/**
	 * Returneaza obiectul de tip Video specific titlului
	 */
	public Video getShow(String title) {
		if (this.movies.containsKey(title))
			return this.movies.get(title);
		
		return this.serials.get(title);
	}
	
	/**
	 * Actualizeaza scorul filmelor si serialelor
	 */
	public void updateShows() {
		for (Movie movie : this.movies.values()) {
			movie.calculateRating();
			System.out.println(movie.getTitle() + " " + movie.getRating());
		}
		
		for (Serial serial : this.serials.values()) {
			serial.calculateRating();
			System.out.println(serial.getTitle() + " " + serial.getRating());
		}
	}
	
	/**
	 * Actualizeaza scorul actorilor
	 */
	public void updateActors() {
		for (Actor actor : this.actors) {
			actor.calculateRating(this);
			actor.calculateAwards();
			System.out.println(actor.getName() + " " + actor.getRating() + " " + actor.getNumberOfAwards());
		}
	}
}