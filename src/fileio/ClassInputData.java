package fileio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import action.Action;
import action.Command;
import action.Query;
import action.Recommendation;
import actor.Actor;
import entertainment.Movie;
import entertainment.Serial;
import user.User;

public final class ClassInputData {
    /**
     * Transforma un ArrayList<ActorInputData> intr-un ArrayList<Actor>
     */
    public ArrayList<Actor> getActors(final List<ActorInputData> inputActors) {
        ArrayList<ActorInputData> iactors;
        iactors = (ArrayList<ActorInputData>) inputActors;
        final ArrayList<Actor> actors = new ArrayList<Actor>();

        for (final ActorInputData iactor : iactors) {
            Actor actor;
            actor = new Actor(iactor.getName(), iactor.getCareerDescription(),
                                   iactor.getFilmography(), iactor.getAwards());
            actors.add(actor);
        }

        return actors;
    }

    /**
     * Transforma un ArrayList<UserInputData> intr-un Map<String, User> unde
     * cheia este reprezentata de username
     */
    public Map<String, User> getUsers(final List<UserInputData> inputUsers) {
        ArrayList<UserInputData> iusers;
        iusers = (ArrayList<UserInputData>) inputUsers;
        final Map<String, User> users = new HashMap<>();

        for (final UserInputData iuser : iusers) {
            User user;
            user = new User(iuser.getUsername(), iuser.getSubscriptionType(),
                                 iuser.getHistory(), iuser.getFavoriteMovies());
            users.put(iuser.getUsername(), user);
        }

        return users;
    }

    /**
     * Transforma un ArrayList<MovieInputData> intr-un Map<String, Movier> unde
     * cheia este reprezentata de username
     */
    public Map<String, Movie> getMovies(final List<MovieInputData> inputMovies) {
        ArrayList<MovieInputData> imovies;
        imovies = (ArrayList<MovieInputData>) inputMovies;
        final Map<String, Movie> movies = new HashMap<>();
        int movieId = 1;

        for (final MovieInputData imovie : imovies) {
            Movie movie;
            movie = new Movie(imovie.getTitle(), imovie.getYear(),
                    imovie.getDuration(), imovie.getCast(), imovie.getGenres(),
                                                                       movieId);

            movies.put(movie.getTitle(), movie);
            movieId++;
        }

        return movies;
    }

    /**
     * Transforma un ArrayList<UserInputData> intr-un Map<String, User> unde
     * cheia este reprezentata de username
     */
    public Map<String, Serial> getSerials(final List<SerialInputData> inputSerials) {
        ArrayList<SerialInputData> iserials;
        iserials = (ArrayList<SerialInputData>) inputSerials;
        final Map<String, Serial> serials = new HashMap<>();
        int serialId = 1;

        for (final SerialInputData iserial : iserials) {
            Serial serial;
            serial = new Serial(iserial.getTitle(), iserial.getYear(),
                                        iserial.getCast(), iserial.getGenres(),
                               iserial.getNumberSeason(), iserial.getSeasons(),
                                                                    serialId);

            serials.put(serial.getTitle(), serial);
            serialId++;
        }

        return serials;
    }

    /**
     * Transforma un ArrayList<ActionInputData> intr-un ArrayList<Serial>
     */
    public ArrayList<Action> getActions(final List<ActionInputData> inputActions) {
        ArrayList<ActionInputData> iactions;
        iactions = (ArrayList<ActionInputData>) inputActions;
        final ArrayList<Action> actions = new ArrayList<Action>();

        for (final ActionInputData iaction : iactions) {
            Action action;
            if (iaction.getType() == null) {
                action = new Query(iaction.getActionId(), iaction.getActionType(),
                                   iaction.getObjectType(), iaction.getNumber(),
                                   iaction.getSortType(), iaction.getCriteria(),
                                   iaction.getFilters());

            } else if (iaction.getTitle() != null) {
                action = new Command(iaction.getActionId(), iaction.getType(),
                                iaction.getActionType(), iaction.getUsername(),
                                        iaction.getTitle(), iaction.getGrade(),
                                                    iaction.getSeasonNumber());

            } else {
                action = new Recommendation(iaction.getActionId(),
                               iaction.getActionType(), iaction.getType(),
                                     iaction.getUsername(), iaction.getGenre());
            }

            actions.add(action);
        }

        return actions;
    }
}
