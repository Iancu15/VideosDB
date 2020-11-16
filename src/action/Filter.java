package action;

import java.util.ArrayList;
import java.util.List;

public class Filter {
	private String year;
	private String genre;
	private ArrayList<String> words;
	private ArrayList<String> awards;
	
	public Filter(String year, String genre, List<String> words, 
														List<String> awards) {
		this.year = year;
		this.genre = genre;
		this.words = (ArrayList<String>) words;
		this.awards = (ArrayList<String>) awards;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public ArrayList<String> getWords() {
		return words;
	}

	public void setWords(ArrayList<String> words) {
		this.words = words;
	}

	public ArrayList<String> getAwards() {
		return awards;
	}

	public void setAwards(ArrayList<String> awards) {
		this.awards = awards;
	}
}
