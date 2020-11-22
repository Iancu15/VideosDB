package action;

import java.util.ArrayList;
import java.util.List;

import entertainment.Genre;
import utils.Utils;

public class Filter {
	private String year;
	private Genre genre;
	private ArrayList<String> words;
	private ArrayList<String> awards;
	/**
	 * Este 1 in caz ca a cerut utilizatorul o interogare pentru un gen care nu
	 * exista, altfel ramane 0
	 */
	private boolean error;
	
	public Filter(String year, String genre, List<String> words, 
														List<String> awards) {
		this.year = year;
		this.genre = null;
		this.error = false;
		if (genre != null) {
			this.genre = Utils.stringToGenre(genre);
			if(this.genre == null)
				this.error = true;
		}
		
		this.words = (ArrayList<String>) words;
		this.awards = (ArrayList<String>) awards;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
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

	public boolean getError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
}
