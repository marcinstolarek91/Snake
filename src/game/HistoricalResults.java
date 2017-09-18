package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HistoricalResults implements Serializable {
	private static final long serialVersionUID = -7937002185849132963L;
	private List<Integer> results = new ArrayList<>();
	
	public HistoricalResults() {
		results.clear();
		results.add(new Integer(0));
	}
	public void sortResults() {;
		results.sort(null);
	}
	public void addResult(int result) {
		if (results.contains(new Integer(0))) // delete first element (0)
			results.remove(new Integer(0));
		results.add(new Integer(result));
		sortResults();
	}
	public List<Integer> getResults(){
		return results;
	}
	public int getBestResult() {
		return results.get(results.size() - 1);
	}
}
