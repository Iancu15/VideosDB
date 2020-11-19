package entertainment;

import java.util.ArrayList;
import java.util.List;

public class Serial extends Video {
	private int numberOfSeasons;
	private ArrayList<Season> seasons;
	
	public Serial(String title, int year, ArrayList<String> cast, 
     ArrayList<String> genres, int numberOfSeasons, ArrayList<Season> seasons) {
		super(title, year, cast, genres);
		this.numberOfSeasons = numberOfSeasons;
		this.seasons = seasons;
	}

	public int getNumberOfSeasons() {
		return numberOfSeasons;
	}

	public void setNumberOfSeasons(int numberOfSeasons) {
		this.numberOfSeasons = numberOfSeasons;
	}

	public ArrayList<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(ArrayList<Season> seasons) {
		this.seasons = seasons;
	}

	/**
     * Adauga nota data de un utilizator unui sezon in lista cu note a
     * sezonului respectiv
     */
    public void giveRating(Double grade, Integer season) {
    	Season realSeason = this.seasons.get(season - 1);
    	List<Double> ratings = realSeason.getRatings();
    	ratings.add(grade);
    	realSeason.setRatings(ratings);
    }
    
    /**
     * Calculeaza media intre scorurile primite de fiecare sezon in parte
     */
    public Double calculateRating() {
    	Double ratingSerial = 0.0;
    	for (Season season : seasons) {
    		Double ratingSeason = 0.0;
    		List<Double> ratings = season.getRatings();
    		
    		if (!ratings.isEmpty()) {
    			ratingSeason = this.calculateRatingSeason(ratings);
    		}
    		
    		ratingSerial += ratingSeason;
    	}
    	
    	this.rating = ratingSerial/this.numberOfSeasons;
    	return this.rating;
    }
    
    /**
     * Calculeaza media notelor date unui sezon pe baza notelor date de catre
     * utilizatori sezonului respectiv
     */
    private Double calculateRatingSeason(List<Double> ratings) {
    	if (ratings.isEmpty()) {
    		return 0.0;
    	}
    	
    	Double ratingSeason = 0.0;
    	for (Double rating : ratings) {
    		ratingSeason += rating;
    	}
    	
    	return ratingSeason/ratings.size();
    }
}
