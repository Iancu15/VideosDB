package actor;

import java.util.ArrayList;
import java.util.Map;

import entertainment.Video;
import main.Database;

public final class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> filmography;
    private Map<ActorsAwards, Integer> awards;
    /**
     * Media scorurilor obtinute de filmele si serialele in care a jucat
     */
    private Double rating;
    /**
     * Numarul de premii obtinute de actor
     */
    private Integer numberOfAwards = 0;

    public Actor(final String name,  final String careerDescription,
            final ArrayList<String> filmography,  final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.rating = 0.0;
        this.calculateAwards();
    }

    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public String getCareerDescription() {
        return careerDescription;
    }
    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }
    public ArrayList<String> getFilmography() {
        return filmography;
    }
    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }
    public void setAwards(final Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public Integer getNumberOfAwards() {
        return numberOfAwards;
    }

    public void setNumberOfAwards(final Integer numberOfAwards) {
        this.numberOfAwards = numberOfAwards;
    }

    /**
     * Calculeaza scorul actorului curent facand media scorurilor obtinute
     * de filmele si serialele in care a jucat
     */
    public void calculateRating(final Database db) {
        Double ratingActor = 0.0;
        int numberOfRatedShows = 0;
        for (final String showTitle : filmography) {
            final Video show = db.getShow(showTitle);
            if (show != null) {
                final Double ratingShow = show.getRating();
                if (ratingShow != 0) {
                    ratingActor += ratingShow;
                    numberOfRatedShows++;
                }
            }
        }

        if (ratingActor != 0) {
            this.rating = ratingActor / numberOfRatedShows;
        }
    }

    /**
     * Calculeaza numarul total de premii detinute de actor
     */
    public void calculateAwards() {
        for (final Integer numberAwards : this.awards.values()) {
            this.numberOfAwards += numberAwards;
        }
    }

    /**
     * La afisare intoarce numele actorului
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
