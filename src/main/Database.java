package main;

import java.util.ArrayList;
import java.util.Map;

import action.Action;
import actor.Actor;
import entertainment.Movie;
import entertainment.Serial;
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
	private ArrayList<Movie> movies;
	private ArrayList<Serial> serials;
	private ArrayList<Action> actions;
	
	public Database(Input input) {
		ClassInputData classinputdata = new ClassInputData();
        
        this.actors = classinputdata.getActors(input.getActors());
        this.users = classinputdata.getUsers(input.getUsers());
        this.movies = classinputdata.getMovies(input.getMovies());
        this.serials = classinputdata.getSerials(input.getSerials());
        this.actions = classinputdata.getActions(input.getCommands());
	}

	public ArrayList<Actor> getActors() {
		return actors;
	}

	public Map<String, User> getUsers() {
		return users;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public ArrayList<Serial> getSerials() {
		return serials;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}
	
	public User getUser(String username) {
		return this.users.get(username);
	}
}