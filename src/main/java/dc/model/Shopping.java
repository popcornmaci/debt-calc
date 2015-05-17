package dc.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shopping {
	private LocalDate date;
	private Map<Double, List<Person>> items;
	public Shopping(LocalDate date) {
		this.date = date;
		this.items = new HashMap<Double, List<Person>>();
	}
	public void addItem(double price, List<Person> people){
		items.put(price, people);
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Map<Double, List<Person>> getItems() {
		return items;
	}
	public void setItems(Map<Double, List<Person>> items) {
		this.items = items;
	}
	
}
