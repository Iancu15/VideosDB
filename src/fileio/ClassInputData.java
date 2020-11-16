package fileio;

import java.util.ArrayList;
import java.util.List;

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
     * Transform un ArrayList<ActorInputData> intr-un ArrayList<Actor>
     * iactors prescurtare de la input_actors, analog la celelalte clase
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
     * Transform un ArrayList<UserInputData> intr-un ArrayList<User>
     */
	public ArrayList<User> getUsers(List<UserInputData> input_users) {
		ArrayList<UserInputData> iusers;
	    iusers = (ArrayList<UserInputData>) input_users;
	    ArrayList<User> users = new ArrayList<User>();
	    
	    for (UserInputData iuser : iusers) {
	    	User user;
	    	user = new User(iuser.getUsername(), iuser.getSubscriptionType(),
	    				         iuser.getHistory(), iuser.getFavoriteMovies());
	    	users.add(user);
	    }
	    
	    return users;
	}
	
	/**
     * Transform un ArrayList<MovieInputData> intr-un ArrayList<Movie>
     */
	public ArrayList<Movie> getMovies(List<MovieInputData> input_movies) {
		ArrayList<MovieInputData> imovies;
	    imovies = (ArrayList<MovieInputData>) input_movies;
	    ArrayList<Movie> movies = new ArrayList<Movie>();
	    
	    for (MovieInputData imovie : imovies) {
	    	Movie movie;
	    	movie = new Movie(imovie.getTitle(), imovie.getYear(), 
	    			imovie.getDuration(), imovie.getCast(), imovie.getGenres());
	    	movies.add(movie);
	    }
	    
	    return movies;
	}
	
	/**
     * Transform un ArrayList<SerialInputData> intr-un ArrayList<Serial>
     */
	public ArrayList<Serial> getSerials(List<SerialInputData> input_serials) {
		ArrayList<SerialInputData> iserials;
	    iserials = (ArrayList<SerialInputData>) input_serials;
	    ArrayList<Serial> serials = new ArrayList<Serial>();
	    
	    for (SerialInputData iserial : iserials) {
	    	Serial serial;
	    	serial = new Serial(iserial.getTitle(), iserial.getYear(), 
	    								iserial.getCast(), iserial.getGenres(), 
	    					   iserial.getNumberSeason(), iserial.getSeasons());
	    	serials.add(serial);
	    }
	    
	    return serials;
	}
	
	/**
     * Transform un ArrayList<ActionInputData> intr-un ArrayList<Serial>
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
	    												 iaction.getTitle());
	    		
	    	} else {
	    		action = new Recommendation(iaction.getActionId(), 
	    						     iaction.getType(), iaction.getActionType(),
	    						               iaction.getUsername());
	    	}
	    	
	    	actions.add(action);
	    }
	    
	    return actions;
	}
}
