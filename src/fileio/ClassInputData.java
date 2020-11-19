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

public class ClassInputData {
	/**
     * Transforma un ArrayList<ActorInputData> intr-un ArrayList<Actor>
     */
	public ArrayList<Actor> getActors(List<ActorInputData> input_actors) {
	    ArrayList<ActorInputData> iactors;
	    iactors = (ArrayList<ActorInputData>) input_actors;
	    ArrayList<Actor> actors = new ArrayList<Actor>();
	    
	    for (ActorInputData iactor : iactors) {
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
	public Map<String, User> getUsers(List<UserInputData> input_users) {
		ArrayList<UserInputData> iusers;
	    iusers = (ArrayList<UserInputData>) input_users;
	    Map<String, User> users = new HashMap<>();
	    
	    for (UserInputData iuser : iusers) {
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
	public Map<String, Movie> getMovies(List<MovieInputData> input_movies) {
		ArrayList<MovieInputData> imovies;
	    imovies = (ArrayList<MovieInputData>) input_movies;
	    Map<String, Movie> movies = new HashMap<>();
	    
	    for (MovieInputData imovie : imovies) {
	    	Movie movie;
	    	movie = new Movie(imovie.getTitle(), imovie.getYear(), 
	    			imovie.getDuration(), imovie.getCast(), imovie.getGenres());
	    	
	    	movies.put(movie.getTitle(), movie);
	    }
	    
	    return movies;
	}
	
	/**
     * Transforma un ArrayList<UserInputData> intr-un Map<String, User> unde
     * cheia este reprezentata de username
     */
	public Map<String, Serial> getSerials(List<SerialInputData> input_serials) {
		ArrayList<SerialInputData> iserials;
	    iserials = (ArrayList<SerialInputData>) input_serials;
	    Map<String, Serial> serials = new HashMap<>();
	    
	    for (SerialInputData iserial : iserials) {
	    	Serial serial;
	    	serial = new Serial(iserial.getTitle(), iserial.getYear(), 
	    								iserial.getCast(), iserial.getGenres(), 
	    					   iserial.getNumberSeason(), iserial.getSeasons());
	    	
	    	serials.put(serial.getTitle(), serial);
	    }
	    
	    return serials;
	}
	
	/**
     * Transforma un ArrayList<ActionInputData> intr-un ArrayList<Serial>
     */
	public ArrayList<Action> getActions(List<ActionInputData> input_actions) {
		ArrayList<ActionInputData> iactions;
	    iactions = (ArrayList<ActionInputData>) input_actions;
	    ArrayList<Action> actions = new ArrayList<Action>();
	    
	    for (ActionInputData iaction : iactions) {
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
	    					   iaction.getActionType(), iaction.getActionType(),
	    						     iaction.getUsername(), iaction.getGenre());
	    	}
	    	
	    	actions.add(action);
	    }
	    
	    return actions;
	}
}
