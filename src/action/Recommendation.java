package action;

import java.util.ArrayList;
import java.util.Comparator;

import entertainment.Genre;
import entertainment.GenreList;
import entertainment.Video;
import main.Database;
import utils.Utils;

public final class Recommendation extends Action {
    private String user;
    private Genre genre;

    public Recommendation(final int actionId, final String actionType, final String type,
                                                    final String user, final String genre) {
        super(actionId, actionType, type);
        this.user = user;
        this.genre = null;
        if (genre != null) {
            this.genre = Utils.stringToGenre(genre);
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(final Genre genre) {
        this.genre = genre;
    }

    /**
     * Executa cautarea dorita de utilizator si intoarce prin intermediul
     * mesajului rezultatul cautarii. Un utilizator care nu are subscriptie
     * premium nu poate sa utilizeze cautarile premium
     */
    public void execute(final Database db) {
        db.updateShows();
        db.updateGenres();

        // la standard intai cauta prin movies si dupa prin serials
        if (this.getType().equals("standard")) {
            ArrayList<Video> movies, serials;
            movies = new ArrayList<Video>(db.getMovies().values());
            serials = new ArrayList<Video>(db.getSerials().values());
            movies.sort(new OrderComparator());
            serials.sort(new OrderComparator());

            final Integer found = this.getNotViewedShow(db, movies, "Standard");
            if (found.equals(0)) {
                this.getNotViewedShow(db, serials, "Standard");
            }

            return;
        } else if (this.getType().equals("best_unseen")) {
            this.getRecommendationBestUnseen(db);
            return;
        }

        if (db.getUser(user).getSubscriptionType().equals("PREMIUM")) {
            if (this.getType().equals("popular")) {
                this.getRecommendationPopular(db);
            } else if (this.getType().equals("favorite")) {
                this.getRecommendationFavorite(db);
            } else {
                this.getRecommendationSearch(db);
            }

            return;
        }

        this.message = "PopularRecommendation cannot be applied!";
    }

    /**
     * Intoarce 1 daca a gasit un video nevazut, altfel intoarce 0 si
     * modifica mesajul corepunzator starii
     */
    private int getNotViewedShow(final Database db, final ArrayList<Video> shows,
                                                                final String type) {
        for (final Video show : shows) {
            final String title = show.getTitle();
            if (!db.getUser(this.user).getHistory().containsKey(title)) {
                this.message = type + "Recommendation result: " + title;
                return 1;
            }
        }

        this.message = type + "Recommendation cannot be applied!";
        return 0;
    }

    /**
     * Clasa de tip Comparator care compara evaluarea a 2 video-uri, daca cele
     * 2 video-uri au acelasi scor se pastreaza pozitia relativa din baza de
     * date
     */
    private class RatingComparator implements Comparator<Video> {
        @Override
        public int compare(final Video video1, final Video video2) {
            if (video1.getRating().compareTo(video2.getRating()) == 0) {
                return video2.getVideoId().compareTo(video1.getVideoId());
            }

            return video1.getRating().compareTo(video2.getRating());
        }
    }

    /**
     * Compara doua genuri dupa numarul total de vizualizari
     */
    private class GenreViewComparator implements Comparator<GenreList> {
        @Override
        public int compare(final GenreList genre1, final GenreList genre2) {
            return genre1.getViews().compareTo(genre2.getViews());
        }
    }

    /**
     * Compara doua video-uri dupa ID pastrand astfel o ordine fireasca in
     * baza de date
     */
    private class OrderComparator implements Comparator<Video> {
        @Override
        public int compare(final Video video1, final Video video2) {
            return video1.getVideoId().compareTo(video2.getVideoId());
        }
    }

    /**
     * Compara doua video-uri dupa numarul total de selectari favorite ale
     * utilizatorilor, in caz de egal se pastreaza ordinea din baza de date
     */
    private class FavoriteComparator implements Comparator<Video> {
        @Override
        public int compare(final Video video1, final Video video2) {
            if (video1.getNumberOfFavorites().compareTo(video2.getNumberOfFavorites()) == 0) {
                return video2.getVideoId().compareTo(video1.getVideoId());
            }

            return video1.getNumberOfFavorites().compareTo(video2.getNumberOfFavorites());
        }
    }

    /**
     * Intoarce video-ul cu scorul cel mai bun dintre cele nevazute de
     * utilizator
     */
    private void getRecommendationBestUnseen(final Database db) {
        final ArrayList<Video> shows = db.getShows();
        shows.sort(new RatingComparator().reversed());

        this.getNotViewedShow(db, db.getShows(), "BestRatedUnseen");
    }

    /**
     * Cauta primul video nevizualizat din cel mai popular gen si prin
     * intermediul metodei getNotViewedShow scrie rezultatul in mesaj. Daca
     * nu s-a gasit niciun video nevizualizat in cel mai popular gen, atunci
     * se cauta in al doilea cel mai popular gen, daca nici acolo se ia
     * urmatorul gen pana fie gaseste un show nevazut sau da eroare. Filmele
     * au prioritate peste seriale
     */
    private void getRecommendationPopular(final Database db) {
        db.getGenres().sort(new GenreViewComparator().reversed());
        for (final GenreList genreList : db.getGenres()) {
            genreList.getMovies().sort(new OrderComparator());
            genreList.getSerials().sort(new OrderComparator());
            if (this.getNotViewedShow(db, genreList.getMovies(), "Popular") == 1) {
                return;
            }

            if (this.getNotViewedShow(db, genreList.getSerials(), "Popular") == 1) {
                return;
            }
        }
    }

    /**
     * Intoarce prin intermediul mesajului cel mai placut video care e lista de
     * favorite a cel putin 1 utilizator, daca nu exista un astfel de video
     * nu intoarce niciun rezultat
     */
    private void getRecommendationFavorite(final Database db) {
        final ArrayList<Video> showsFiltered = new ArrayList<Video>(db.getShows());
        showsFiltered.sort(new FavoriteComparator().reversed());
        for (final Video show : db.getShows()) {
            if (show.getNumberOfFavorites() == 0) {
                showsFiltered.remove(show);
            }
        }

        this.getNotViewedShow(db, showsFiltered, "Favorite");
    }

    /**
     * Mesajul intoarce o lista cu video-uri nevazute de utilizator ordonate
     * dupa scor
     */
    private void getRecommendationSearch(final Database db) {
        if (this.genre == null) {
            this.message = "SearchRecommendation cannot be applied!";
            return;
        }

        GenreList genreListSearch = new GenreList();
        for (final GenreList genreList : db.getGenres()) {
            genreListSearch = genreList;
            if (this.genre.equals(genreList.getName())) {
                break;
            }
        }

        // creez lista cu show-urile nevazute
        final ArrayList<Video> showsFromGenre = new ArrayList<Video>();
        for (final Video show : genreListSearch.getMovies()) {
            final String title = show.getTitle();
            if (!db.getUser(user).getHistory().containsKey(title)) {
                showsFromGenre.add(show);
            }
        }

        for (final Video show : genreListSearch.getSerials()) {
            final String title = show.getTitle();
            if (!db.getUser(user).getHistory().containsKey(title)) {
                showsFromGenre.add(show);
            }
        }

        if (showsFromGenre.isEmpty()) {
            this.message = "SearchRecommendation cannot be applied!";
            return;
        }

        // ma folosesc de comparatorul deja creat in Query
        final Query query = new Query();
        showsFromGenre.sort(query.getRatingComparator());

        Integer showsNumber = showsFromGenre.size();
        String outputSearch = "SearchRecommendation";
        this.message = query.createMessageShow(showsFromGenre, outputSearch, showsNumber);
    }
}
