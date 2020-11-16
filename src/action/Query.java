package action;

import java.util.List;

public class Query extends Action {
	private int number;
	private String sort_type;
	private String criteria;
	private Filter filters;
	
	public Query(int actionId, String type, String actionType, int number, 
										  String sort_type, String criteria, 
										  		List<List<String>> filters) {
		super(actionId, actionType, type);
		this.number = number;
		this.sort_type = sort_type;
		this.criteria = criteria;
		
		String year = isNull(filters, 0);
		String genre = isNull(filters, 1);
		this.filters = new Filter(year, genre, filters.get(2), filters.get(3));
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getSort_type() {
		return sort_type;
	}

	public void setSort_type(String sort_type) {
		this.sort_type = sort_type;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public Filter getFilters() {
		return filters;
	}

	public void setFilters(Filter filters) {
		this.filters = filters;
	}
	
	/**
     * se foloseste in constructor pentru year si genre la filters
     * In caz ca filters are totul null imi da ExceptionOutofBound
     * in acel caz year sau genre trebuie facute null
     */
	private String isNull(List<List<String>> filters, int index) {
		try {
			if (filters.get(index).isEmpty())
				return null;
			
			return filters.get(index).get(index);
		} catch (Exception e) {
			return null;
		}
	}
}
