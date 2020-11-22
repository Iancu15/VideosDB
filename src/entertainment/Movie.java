package entertainment;

import java.util.ArrayList;

public final class Movie extends Video {
    /**
     * O lista cu notele date de utilizatori
     */
    private ArrayList<Double> ratings;

    public Movie(final String title, final int year, final int duration,
    final ArrayList<String> cast, final ArrayList<String> genres, final int movieId) {
        super(title, year, cast, genres, movieId);
        this.duration = duration;
        this.ratings = new ArrayList<Double>();
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final ArrayList<Double> rating) {
        this.ratings = rating;
    }

    /**
     * Adauga o nota data de un utilizator, nu se specifica utilizatorul
     */
    public void giveRating(final Double grade) {
        this.ratings.add(grade);
    }

    /**
     * Calculeaza media notelor date de utilizatori filmului
     */
    public Double calculateRating() {
        if (this.ratings.isEmpty()) {
            this.rating = 0.0;
            return this.rating;
        }

        Double ratingMovie = 0.0;
        for (final Double rating : this.ratings) {
            ratingMovie += rating;
        }

        this.rating = ratingMovie / this.ratings.size();
        return this.rating;
    }
}
