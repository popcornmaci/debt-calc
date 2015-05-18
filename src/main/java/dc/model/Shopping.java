package dc.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shopping {
	private LocalDate date;
	private Map<Double, List<Person>> items;
	private Map<Person, Double> paid;
	public Shopping(LocalDate date) {
		this.date = date;
		this.items = new HashMap<Double, List<Person>>();
		this.paid = new HashMap<Person, Double>();
	}
	public void addItem(double price, List<Person> people){
		items.put(price, people);
		buildPaid();
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
		buildPaid();
	}
	private void buildPaid() {
		for (List<Person> list : items.values()) {
			for (Person p : list) {
				if(paid.get(p)==null){
					paid.put(p, 0.0);
				}
			}
		}
	}
	public Map<Person, Double> getPaid() {
		return paid;
	}
	public void setPaid(Map<Person, Double> paid) {
		this.paid = paid;
	}
	
}
