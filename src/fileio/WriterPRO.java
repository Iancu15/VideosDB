package fileio;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Se foloseste de clasa Writer pentru a scrie output-ul in fisiere
 */
public class WriterPRO {
	Writer writer;
	JSONArray arrayResult;
	
	public WriterPRO(String filepath) throws IOException {
		this.writer = new Writer(filepath);
		this.arrayResult = new JSONArray();
	}
	
	/**
	 * Creeaza un obiect JSON pe baza parametrilor id si message. Adauga
	 * respectivul obiect in vectorul JSON destinat output-ului
	 */
	public void addObject(int id, String message) throws IOException {
		JSONObject jsonobject = writer.writeFile(id, null, message);
		
		this.arrayResult.add(jsonobject);
	}
	
	/**
	 * Scrie vectorul JSON in fisierul de output
	 */
	public void writeFile() {
		this.writer.closeJSON(this.arrayResult);
	}
}
