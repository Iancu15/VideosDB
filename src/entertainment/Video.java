package entertainment;

import java.util.ArrayList;
import java.util.List;

public class Video {
	private String title;
	private int release_year;
	List<Genre> genres;
	
	public Video(String title, int release_year) {
		this.genres = new ArrayList<>();
		this.title = title;
		this.release_year = release_year;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getRelease_year() {
		return release_year;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setRelease_year(int release_year) {
		this.release_year = release_year;
	}
}
