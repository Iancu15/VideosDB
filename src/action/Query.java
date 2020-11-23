package action;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import actor.Actor;
import common.Constants;
import entertainment.Genre;
import entertainment.Video;
import main.Database;
import user.User;
import utils.Utils;

public final class Query extends Action {
    private Integer number;
    private String sortType;
    private String criteria;
    private Filter filters;

    public Query(final int actionId, final String actionType, final String type, final int number,
                                          final String sortType, final String criteria,
                                                final List<List<String>> filters) {
        super(actionId, actionType, type);
        this.number = number;
        this.sortType = sortType;
        this.criteria = criteria;

        final String year = isNull(filters, 0);
        final String genre = isNull(filters, 1);
        this.filters = new Filter(year, genre, filters.get(2), filters.get(Constants.THREE));
    }

    public Query() {

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(final String sortType) {
        this.sortType = sortType;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(final String criteria) {
        this.criteria = criteria;
    }

    public Filter getFilters() {
        return filters;
    }

    public void setFilters(final Filter filters) {
        this.filters = filters;
    }

    /**
     * Se foloseste in constructor pentru year si genre la filters
     * in caz ca filters are totul null imi da ExceptionOutofBound
     * in acel caz year sau genre trebuie facute null
     */
    private String isNull(final List<List<String>> filtersVar, final int index) {
        try {
            if (filtersVar.get(index).isEmpty()) {
                return null;
            }

            return filtersVar.get(index).get(0);
        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * Executa o cautare in functie de tipul de obiect specificat
     */
    public void execute(final Database db) {
        db.updateShows();
        db.updateActors();

        if (this.getType().equals("actors")) {
            this.sortActors(db.getActors());
        } else if (this.getType().equals("users")) {
            this.sortUsers(new ArrayList<User>(db.getUsers().values()));
        } else if (this.getType().equals("movies")) {
            this.sortShows(new ArrayList<Video>(db.getMovies().values()));
        } else {
            this.sortShows(new ArrayList<Video>(db.getSerials().values()));
        }
    }

    /**
     * Clasa de tip Comparator care compara evaluarea a 2 actori, daca cei
     * 2 actori au acelasi scor se compara lexicografic dupa nume
     */
    private class ActorRatingComparator implements Comparator<Actor> {
        @Override
        public int compare(final Actor actor1, final Actor actor2) {
            if (actor1.getRating().compareTo(actor2.getRating()) == 0) {
                return actor1.getName().compareTo(actor2.getName());
            }

            return actor1.getRating().compareTo(actor2.getRating());
        }
    }

    /**
     * Clasa de tip Comparator care compara evaluarea a 2 video-uri, daca cele
     * 2 video-uri au acelasi scor se compara lexicografic dupa titlu
     */
    private class ShowRatingComparator implements Comparator<Video> {
        @Override
        public int compare(final Video video1, final Video video2) {
            if (video1.getRating().compareTo(video2.getRating()) == 0) {
                return video1.getTitle().compareTo(video2.getTitle());
            }

            return video1.getRating().compareTo(video2.getRating());
        }
    }

    /**
     * Returneaza un ShowRatingComparator, se foloseste in Recommendation
     */
    public Comparator<Video> getRatingComparator() {
        return new ShowRatingComparator();
    }

    /**
     * Clasa de tip Comparator care compara numele a 2 actori lexicografic
     */
    private class NameComparator implements Comparator<Actor> {
        @Override
        public int compare(final Actor actor1, final Actor actor2) {
            return actor1.getName().compareTo(actor2.getName());
        }
    }

    /**
     * Clasa de tip Comparator care compara numarul de premii castigate de 2
     * actori, in cazul in care au castigat la fel de multe premii se compara
     * lexicografic numele lor
     */
    private class AwardsComparator implements Comparator<Actor> {
        @Override
        public int compare(final Actor actor1, final Actor actor2) {
            if (actor1.getNumberOfAwards().compareTo(actor2.getNumberOfAwards()) == 0) {
                return actor1.getName().compareTo(actor2.getName());
            }

            return actor1.getNumberOfAwards().compareTo(actor2.getNumberOfAwards());
        }
    }

    /**
     * Clasa de tip Comparator care compara durata a 2 video-uri, daca cele
     * 2 video-uri au aceeasi durata se compara lexicografic dupa titlu
     */
    private class DurationComparator implements Comparator<Video> {
        @Override
        public int compare(final Video video1, final Video video2) {
            if (video1.getDuration().compareTo(video2.getDuration()) == 0) {
                return video1.getTitle().compareTo(video2.getTitle());
            }

            return video1.getDuration().compareTo(video2.getDuration());
        }
    }

    /**
     * Clasa de tip Comparator care compara numarul de selectari drept favorit
     * a 2 video-uri, daca cele 2 video-uri sunt la fel de placute se compara
     * lexicografic dupa titlu
     */
    private class FavoriteComparator implements Comparator<Video> {
        @Override
        public int compare(final Video video1, final Video video2) {
            if (video1.getNumberOfFavorites().compareTo(video2.getNumberOfFavorites()) == 0) {
                return video1.getTitle().compareTo(video2.getTitle());
            }

            return video1.getNumberOfFavorites().compareTo(video2.getNumberOfFavorites());
        }
    }

    /**
     * Clasa de tip Comparator care compara numarul de vizualizari a 2 video-uri
     * , daca cele 2 video-uri au acelasi numar de vizualizari se compara
     * lexicografic dupa titlu
     */
    private class ViewsComparator implements Comparator<Video> {
        @Override
        public int compare(final Video video1, final Video video2) {
            if (video1.getNumberOfViews().compareTo(video2.getNumberOfViews()) == 0) {
                return video1.getTitle().compareTo(video2.getTitle());
            }

            return video1.getNumberOfViews().compareTo(video2.getNumberOfViews());
        }
    }

    /**
     * Clasa de tip Comparator care numarul de evaluari oferite de 2 utilizatori
     * , daca cei 2 utilizatori sunt la fel de critici atunci se compara
     * lexicografic dupa username
     */
    private class NumberOfRatingsComparator implements Comparator<User> {
        @Override
        public int compare(final User user1, final User user2) {
            if (user1.getNumberOfRatings().compareTo(user2.getNumberOfRatings()) == 0) {
                return user1.getUsername().compareTo(user2.getUsername());
            }

            return user1.getNumberOfRatings().compareTo(user2.getNumberOfRatings());
        }
    }

    /**
     * Creeaza mesajul ce va reprezenta raspunsul cautarii utilizatorului pentru
     * o interogare dupa actori
     */
    private void createMessageActor(final ArrayList<Actor> actors) {
        final ArrayList<Actor> actorNames = new ArrayList<Actor>();
        if (this.number == null) {
            this.number = actors.size();
        }

        for (int i = 0; i < this.number; i++) {
            if (i == actors.size()) {
                break;
            }

            actorNames.add(actors.get(i));
        }

        this.message = "Query result: " + actorNames.toString();
    }

    /**
     * Intoarce un sir cu titlurile primelor number video-uri din lista
     * primita ca parametru. Parametrul type ajuta in intoarcerea unui mesaj
     * personalizat pentru a fi folosit si in alte comenzi (Ex: Recommendation)
     */
    public String createMessageShow(final ArrayList<Video> shows, final String type,
                                                            final Integer numberVar) {
        final ArrayList<String> showNames = new ArrayList<String>();
        Integer arraySize = numberVar;
        if (numberVar == null) {
            arraySize = shows.size();
        }

        for (int i = 0; i < arraySize; i++) {
            if (i == shows.size()) {
                break;
            }

            showNames.add(shows.get(i).getTitle());
        }

        return type + " result: " + showNames.toString();
    }

    /**
     * Creeaza mesajul ce va reprezenta raspunsul cautarii utilizatorului pentru
     * o interogare dupa alti utilizatori
     */
    private void createMessageUser(final ArrayList<User> users) {
        final ArrayList<String> userNames = new ArrayList<String>();
        if (this.number == null) {
            this.number = users.size();
        }

        for (int i = 0; i < this.number; i++) {
            if (i == users.size()) {
                break;
            }

            userNames.add(users.get(i).getUsername());
        }

        this.message = "Query result: " + userNames.toString();
    }

    /**
     * Sorteaza actorii in functie de criteriu si creeaza mesajul dorit de
     * utilizator
     */
    public void sortActors(final ArrayList<Actor> actors) {
        ArrayList<Actor> sortedActors;
        if (this.criteria.equals("average")) {
            sortedActors = this.sortActorsAverage(actors);
        } else if (this.criteria.equals("awards")) {
            sortedActors = this.sortActorsAwards(actors);
        } else {
            sortedActors = this.sortActorsFilters(actors);
        }

        this.createMessageActor(sortedActors);
    }

    /**
     * Sorteaza crescator sau descrescator o lista de actori in functie de
     * evaluarea primita. Nu se iau in considerare actorii fara evaluari
     */
    private ArrayList<Actor> sortActorsAverage(final ArrayList<Actor> actors) {
        final ArrayList<Actor> ratedActors = new ArrayList<Actor>();
        for (final Actor actor : actors) {
            if (actor.getRating() != 0) {
                ratedActors.add(actor);
            }
        }

        if (this.sortType.equals("asc")) {
            ratedActors.sort(new ActorRatingComparator());
        } else {
            ratedActors.sort(new ActorRatingComparator().reversed());
        }

        return ratedActors;
    }

    /**
     * Sorteaza actorii, care au castigat premiile specificate, lexicografic
     */
    private ArrayList<Actor> sortActorsAwards(final ArrayList<Actor> actors) {
        final ArrayList<Actor> actorsWithAwards = new ArrayList<Actor>();
        for (final Actor actor : actors) {
            int hasAward = 1;
            for (final String award : this.filters.getAwards()) {
                if (!actor.getAwards().containsKey(Utils.stringToAwards(award))) {
                    hasAward = 0;
                    break;
                }
            }

            if (hasAward == 1) {
                actorsWithAwards.add(actor);
            }
        }

        if (this.sortType.equals("asc")) {
            actorsWithAwards.sort(new AwardsComparator());
        } else {
            actorsWithAwards.sort(new AwardsComparator().reversed());
        }

        return actorsWithAwards;
    }

    /**
     * Sorteaza actorii, a caror descriere foloseste cuvintele specificate,
     * lexicografic
     */
    private ArrayList<Actor> sortActorsFilters(final ArrayList<Actor> actors) {
        final ArrayList<Actor> actorsWithWords = new ArrayList<Actor>();
        for (final Actor actor : actors) {
            int hasWord = 1;
            for (final String word : this.filters.getWords()) {
                hasWord = 0;
                // split imparte dupa space, cratima, punct si virgula
                for (final String wordCopy : actor.getCareerDescription().split("[ -.,]")) {
                    if (wordCopy.toLowerCase().equals(word)) {
                        hasWord = 1;
                        break;
                    }
                }

                if (hasWord == 0) {
                    break;
                }
            }

            if (hasWord == 1) {
                actorsWithWords.add(actor);
            }
        }

        if (this.sortType.equals("asc")) {
            actorsWithWords.sort(new NameComparator());
        } else {
            actorsWithWords.sort(new NameComparator().reversed());
        }

        return actorsWithWords;
    }

    /**
     * Sorteaza video-urile care respecta anul si genul in functie de
     * criteriul specificat
     */
    public void sortShows(final ArrayList<Video> shows) {
        final ArrayList<Video> showsFiltered = new ArrayList<Video>();
        for (final Video show : shows) {
            boolean isYear = show.getYear().toString().equals(this.filters.getYear());
            if (!isYear && this.filters.getYear() != null || this.filters.getError()) {
                continue;
            }

            if (this.filters.getGenre() != null) {
                for (final Genre genre : show.getGenres()) {
                    if (genre.equals(this.filters.getGenre())) {
                        showsFiltered.add(show);
                        break;
                    }
                }
            } else {
                showsFiltered.add(show);
            }
        }

        // elimin show-urile fara selectii de favorit pt interogare "favorite"
        if (this.criteria.equals("favorite")) {
            for (final Video show : shows) {
                if (show.getNumberOfFavorites() == 0) {
                    showsFiltered.remove(show);
                }
            }
        }

        // elimin show-urile nevizionate pt interogare "most_viewed"
        if (this.criteria.equals("most_viewed")) {
            for (final Video show : shows) {
                if (show.getNumberOfViews() == 0) {
                    showsFiltered.remove(show);
                }
            }
        }

        // elimin show-urile fara rating pt interogare "ratings"
        if (this.criteria.equals("ratings")) {
            for (final Video show : shows) {
                if (show.getRating() == 0) {
                    showsFiltered.remove(show);
                }
            }
        }

        Comparator<Video> comparator;
        if (this.criteria.equals("ratings")) {
            comparator = new ShowRatingComparator();
        } else if (this.criteria.equals("favorite")) {
            comparator = new FavoriteComparator();
        } else if (this.criteria.equals("longest")) {
            comparator = new DurationComparator();
        } else {
            comparator = new ViewsComparator();
        }

        if (this.sortType.equals("asc")) {
            showsFiltered.sort(comparator);
        } else {
            showsFiltered.sort(comparator.reversed());
        }

        this.message = this.createMessageShow(showsFiltered, "Query", this.number);
    }

    /**
     * Sorteaza utilizatorii dupa numarul de evaluari date video-urilor, daca
     * utilizatorul nu a evaluat nimic atunci nu va aparea
     */
    public void sortUsers(final ArrayList<User> users) {
        final ArrayList<User> usersFiltered = new ArrayList<User>();
        for (final User user : users) {
            if (user.getNumberOfRatings() == 0) {
                continue;
            }

            usersFiltered.add(user);
        }

        if (this.sortType.equals("asc")) {
            usersFiltered.sort(new NumberOfRatingsComparator());
        } else {
            usersFiltered.sort(new NumberOfRatingsComparator().reversed());
        }

        this.createMessageUser(usersFiltered);
    }
}
