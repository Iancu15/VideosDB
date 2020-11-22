package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;

public final class User {
    private String username;
    private String subscriptionType;
    private Map<String, Integer> history;
    private ArrayList<String> favoriteShows;
    /**
     * O lista cu filmele care au primit nota de la utilizator
     */
    private ArrayList<String> ratedMovies;
    /**
     * Un hashmap cu cheia titlul serialului si valoarea sezoanele care au
     * primit nota de la utilizator
     */
    private Map<String, ArrayList<Integer>> ratedSerials;
    /**
     * Variabila retine numarul de scoruri oferite de utilizator;
     */
    private Integer numberOfRatings;

    public User(final String username, final String subscriptionType,
            final Map<String, Integer> history, final ArrayList<String> favoriteShows) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteShows = favoriteShows;
        this.ratedSerials = new HashMap<>();
        this.ratedMovies = new ArrayList<String>();
        this.numberOfRatings = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(final String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(final Map<String, Integer> history) {
        this.history = history;
    }

    public ArrayList<String> getFavoriteShows() {
        return favoriteShows;
    }

    public void setFavoriteShows(final ArrayList<String> favoriteShows) {
        this.favoriteShows = favoriteShows;
    }

    public ArrayList<String> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<String> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public Map<String, ArrayList<Integer>> getRatedSerials() {
        return ratedSerials;
    }

    public void setRatedSerials(final Map<String, ArrayList<Integer>> ratedSerials) {
        this.ratedSerials = ratedSerials;
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(final Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    /**
     * Incrementeaza numarul de vizualizari daca a show-ul a fost deja
     * vizualizat de user, in caz contrar il adauga in istoric cu numarul
     * de vizionari 1
     */
    public String viewVideo(final String title) {
        final Integer value = this.history.putIfAbsent(title, 1);

        if (value != null) {
            this.history.replace(title, value + 1);
        }

        Integer numberOfViews = this.history.get(title);
        return "success -> " + title + " was viewed with total views of " + numberOfViews;
    }

    /**
     * Adauga un show la favorite daca n-a fost deja adaugat si incrementeaza
     * numarul de favorite primite de show
     */
    public String addFavourite(final Video show) {
        final String title = show.getTitle();
        if (!this.history.containsKey(title)) {
            return "error -> " + title + " is not seen";
        }

        if (!this.favoriteShows.contains(title)) {
            this.favoriteShows.add(title);
            return "success -> " + title + " was added as favourite";
        }

        return "error -> " + title + " is already in favourite list";
    }

    /**
     * Adauga titlul in lista filmelor care au primit nota daca utilizatorul
     * a vazut filmul si nu i-a dat nota pana acum si afiseaza succes, in caz
     * contrar afiseaza fail. Metoda este folosita de catre cealalta metoda
     * giveRating
     */
    private String giveRating(final Video show, final Double grade) {
        final String title = show.getTitle();

        if (!this.history.containsKey(title)) {
            return "error -> " + title + " is not seen";
        }

        if (this.ratedMovies.contains(title)) {
            return "error -> " + title + " has been already rated";
        }

        this.ratedMovies.add(title);
        final Movie movie = (Movie) show;
        movie.giveRating(grade);
        this.numberOfRatings++;

        return "success -> " + title + " was rated with " + grade + " by " + this.username;
    }

    /**
     * Adauga sezonul in lista sezoanelor care au primit nota pentru serialul
     * aferent, daca nu exista serialul adauga o intrare noua pentru el.
     * In cazul in care serialul nu a fost vazut sau sezonul deja a primit nota
     * nu face nimic si intoarce mesajul de fail, in caz contrar intoarce
     * mesajul de succes. Daca primeste drept numarul sezonului 0 il considera
     * film si apeleaza metoda pentru filme returnand mesajul functiei
     */
    public String giveRating(final Video show, final Double grade, final Integer season) {
        if (season == 0) {
            return giveRating(show, grade);
        }

        final String title = show.getTitle();

        if (!this.history.containsKey(title)) {
            return "error -> " + title + " is not seen";
        }

        if (this.ratedSerials.containsKey(title)) {
            final ArrayList<Integer> seasons = this.ratedSerials.get(title);
            if (seasons.contains(season)) {
                return "error -> " + title + " has been already rated";
            }

            seasons.add(season);
            this.ratedSerials.replace(title, seasons);
            final Serial serial = (Serial) show;
            serial.giveRating(grade, season);
            this.numberOfRatings++;

            String success = "success -> " + title + " was rated with " + grade;
            return success + " by " + this.username;
        }

        final ArrayList<Integer> seasons = new ArrayList<Integer>();
        seasons.add(season);
        this.ratedSerials.put(title, seasons);
        final Serial serial = (Serial) show;
        serial.giveRating(grade, season);
        this.numberOfRatings++;

        return "success -> " + title + " was rated with " + grade + " by " + this.username;
    }

    /**
     * La afisare intoarce numele utilizatorului
     */
    @Override
    public String toString() {
        return this.getUsername();
    }
}
