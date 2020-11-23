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
public final class Database {
    private final ArrayList<Actor> actors;
    private final Map<String, User> users;
    private final Map<String, Movie> movies;
    private final Map<String, Serial> serials;
    private final ArrayList<Action> actions;
    /**
     * Un array cu toate video-urile
     */
    private final ArrayList<Video> shows;
    /**
     * O lista cu genuri care au in componenta video-urile ce au respectivul
     * gen
     */
    private ArrayList<GenreList> genres;

    public Database(final Input input) {
        final ClassInputData classInputData = new ClassInputData();

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
    public User getUser(final String username) {
        return this.users.get(username);
    }

    /**
     * Intoarce obiectul de tip Video specific titlului
     */
    public Video getShow(final String title) {
        if (this.movies.containsKey(title)) {
            return this.movies.get(title);
        }

        return this.serials.get(title);
    }

    /**
     * Actualizeaza datele neconstante ale video-urilor
     */
    public void updateShows() {
        for (final Video show : this.shows) {
            show.calculateRating();
            show.calculateViews(this);
            show.calculateFavorites(this);
        }
    }

    /**
     * Actualizeaza datele neconstante ale actorilor
     */
    public void updateActors() {
        for (final Actor actor : this.actors) {
            actor.calculateRating(this);
        }
    }

    /**
     * Actualizeaza numarul total de vizualizari ale listelor de genuri
     */
    public void updateGenres() {
        for (final GenreList genre : this.genres) {
            genre.calculateViews();
        }
    }

    /**
     * Creeaza o lista ce contine toate video-urile impartite pe genuri
     */
    public void makeGenreLists() {
        this.genres = new ArrayList<GenreList>();
        for (final Genre genre : Genre.values()) {
            final GenreList genreList = new GenreList(genre);
            for (final Movie movie : this.movies.values()) {
                if (movie.getGenres().contains(genre)) {
                    genreList.getMovies().add(movie);
                }
            }

            for (final Serial serial : this.serials.values()) {
                if (serial.getGenres().contains(genre)) {
                    genreList.getSerials().add(serial);
                }
            }

            genres.add(genreList);
        }
    }
}
