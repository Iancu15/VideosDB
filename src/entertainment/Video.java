package entertainment;

import java.util.ArrayList;

import main.Database;
import user.User;
import utils.Utils;

public abstract class Video {
    private String title;
    private Integer year;
    private ArrayList<String> cast;
    private ArrayList<Genre> genres;
    /**
     * Pozitia video-ului in baza de date
     */
    private Integer videoId;
    /**
     * Media notelor primite de catre video
     */
    protected Double rating;
    /**
     * Numarul de vizualizari ale video-ului de catre utilizatori
     */
    protected Integer numberOfViews;
    /**
     * Numarul de utilizatori care au selectat video-ul drept favorit
     */
    protected Integer numberOfFavorites;
    /**
     * Durata in minute a show-ului (la seriale se ia doar durata
     * unui episod pe sezon)
     */
    protected Integer duration;

    public Video(final String title, final int year, final ArrayList<String> cast,
                         final ArrayList<String> genres, final Integer videoId) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = this.stringstoGenres(genres);
        this.videoId = videoId;
        this.rating = 0.0;
        this.numberOfFavorites = 0;
        this.numberOfViews = 0;
    }

    /**
     * Intoarce titlul video-ului
     */
    public String getTitle() {
        return title;
    }

    /**
     * Intoarce anul de aparitie al video-ului
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Intoarce o lista cu actorii care au participat in video
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * Intoarce o lista cu genurile video
     */
    public ArrayList<Genre> getGenres() {
        return genres;
    }

    /**
     * Intoarce scorul video-ului
     */
    public Double getRating() {
        return rating;
    }

    /**
     * Intoarce numarul de vizionari ale video-ului
     */
    public Integer getNumberOfViews() {
        return numberOfViews;
    }

    /**
     * Intoarce numarul de utilizatori care au video-ul la favorite
     */
    public Integer getNumberOfFavorites() {
        return numberOfFavorites;
    }

    /**
     * Intoarce durata video-ului
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Intoarce ID-ul video-ului
     */
    public Integer getVideoId() {
        return videoId;
    }

    /**
     * Calculeaza media notelor primite de catre video de la utilizatori
     */
    public abstract Double calculateRating();

    /**
     * Calculeaza numarul total de vizionari primite de la utilizatori
     */
    public void calculateViews(final Database db) {
        int views = 0;
        for (final User user : db.getUsers().values()) {
            views += user.getHistory().getOrDefault(this.title, 0);
        }

        this.numberOfViews = views;
    }

    /**
     * Calculeaza numarul de selectii drept favorit ale unui show in functie
     * de lista de favorite a utilizatorilor la momentul curent
     */
    public void calculateFavorites(final Database db) {
        int favorites = 0;
        for (final User user : db.getUsers().values()) {
            if (user.getFavoriteShows().contains(this.title)) {
                favorites++;
            }
        }

        this.numberOfFavorites = favorites;
    }

    /**
     * Transforma o lista genuri tip string intr-o lista de genuri tip Genre
     */
    public ArrayList<Genre> stringstoGenres(final ArrayList<String> genreStrings) {
        final ArrayList<Genre> genresArray = new ArrayList<Genre>();
        for (final String genreString : genreStrings) {
            genresArray.add(Utils.stringToGenre(genreString));
        }

        return genresArray;
    }

    /**
     * La afisare intoarce titlul video-ului
     */
    @Override
    public String toString() {
        return this.getTitle();
    }
}
