package entertainment;

import java.util.ArrayList;

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
}
