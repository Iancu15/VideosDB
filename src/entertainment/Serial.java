package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Serial extends Video {
    private int numberOfSeasons;
    private ArrayList<Season> seasons;

    public Serial(final String title, final int year, final ArrayList<String> cast,
     final ArrayList<String> genres, final int numberOfSeasons,
     final ArrayList<Season> seasons, final int serialId) {
        super(title, year, cast, genres, serialId);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.calculateDuration();
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(final int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(final ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    /**
     * Adauga nota data de un utilizator unui sezon in lista cu note a
     * sezonului respectiv
     */
    public void giveRating(final Double grade, final Integer season) {
        final Season realSeason = this.seasons.get(season - 1);
        final List<Double> ratings = realSeason.getRatings();
        ratings.add(grade);
        realSeason.setRatings(ratings);
    }

    /**
     * Calculeaza media intre scorurile primite de fiecare sezon in parte
     */
    public Double calculateRating() {
        Double ratingSerial = 0.0;
        for (final Season season : seasons) {
            Double ratingSeason = 0.0;
            final List<Double> ratings = season.getRatings();

            if (!ratings.isEmpty()) {
                ratingSeason = this.calculateRatingSeason(ratings);
            }

            ratingSerial += ratingSeason;
        }

        this.rating = ratingSerial / this.numberOfSeasons;
        return this.rating;
    }

    /**
     * Calculeaza media notelor date unui sezon pe baza notelor date de catre
     * utilizatori sezonului respectiv
     */
    private Double calculateRatingSeason(final List<Double> ratings) {
        if (ratings.isEmpty()) {
            return 0.0;
        }

        Double ratingSeason = 0.0;
        for (final Double rating : ratings) {
            ratingSeason += rating;
        }

        return ratingSeason / ratings.size();
    }

    /**
     * Calculeaza durata intregului serial in minute
     */
    public void calculateDuration() {
        int duration = 0;
        for (final Season season : seasons) {
            duration += season.getDuration();
        }

        this.duration = duration;
    }
}
