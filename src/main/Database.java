package main;

import java.util.ArrayList;
import java.util.Map;

import action.Action;
import actor.Actor;
import entertainment.Genre;
import entertainment.GenreList;
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
	/**
	 * Un array cu toate video-urile
	 */
	private ArrayList<Video> shows;
	/**
	 * O lista cu genuri care au in componenta video-urile ce au respectivul
	 * gen
	 */
	private ArrayList<GenreList> genres;
	
	public Database(Input input) {
		ClassInputData classInputData = new ClassInputData();
        
        this.actors = classInputData.getActors(input.getActors());
        this.users = classInputData.getUsers(input.getUsers());
        this.movies = classInputData.getMovies(input.getMovies());
        this.serials = classInputData.getSerials(input.getSerials());
        this.actions = classInputData.getActions(input.getCommands());
        
        this.shows = new ArrayList<Video>();
        this.shows.addAll(this.movies.values());
        this.shows.addAll(this.serials.values());
        this.makeGenreLists();
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
	
	public ArrayList<Video> getShows() {
		return shows;
	}

	public ArrayList<GenreList> getGenres() {
		return genres;
	}

	/**
	 * Intoarce obiectul de tip User specific username-ului
	 */
	public User getUser(String username) {
		return this.users.get(username);
	}
	
	/**
	 * Intoarce obiectul de tip Video specific titlului
	 */
	public Video getShow(String title) {
		if (this.movies.containsKey(title))
			return this.movies.get(title);
		
		return this.serials.get(title);
	}
	
	/**
	 * Actualizeaza datele neconstante ale video-urilor
	 */
	public void updateShows() {
		for (Video show : this.shows) {
			show.calculateRating();
			show.calculateViews(this);
			show.calculateFavorites(this);
		}
	}
	
	/**
	 * Actualizeaza datele neconstante ale actorilor
	 */
	public void updateActors() {
		for (Actor actor : this.actors) {
			actor.calculateRating(this);
		}
	}
	
	/**
	 * Actualizeaza numarul total de vizualizari ale listelor de genuri
	 */
	public void updateGenres() {
		for (GenreList genre : this.genres) {
			genre.calculateViews();
		}
	}
	
	/**
	 * Creeaza o lista ce contine toate video-urile impartite pe genuri
	 */
	public void makeGenreLists() {
		this.genres = new ArrayList<GenreList>();
		for(Genre genre : Genre.values()) {
			GenreList genreList = new GenreList(genre);
			for (Movie movie : this.movies.values()) {
				if (movie.getGenres().contains(genre)) {
					genreList.getMovies().add(movie);
				}
			}
			
			for (Serial serial : this.serials.values()) {
				if(serial.getGenres().contains(genre)) {
					genreList.getSerials().add(serial);
				}
			}
			
			genres.add(genreList);
		}
	}
}