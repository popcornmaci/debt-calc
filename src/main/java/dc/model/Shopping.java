package dc.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class Shopping {
	private LocalDateTime date;
	private Map<Double, List<Person>> items;
	private Map<Person, Double> paid;
	public Shopping(LocalDateTime date) {
		this.date = date;
		this.items = new HashMap<Double, List<Person>>();
		this.paid = new HashMap<Person, Double>();
	}
	public void generateDebt(){
		Map<Person,Double> map = new HashMap<Person, Double>();
		for (Person p : paid.keySet()) {
			double sum=0;
			for (Double d : items.keySet()) {
				if(items.get(d).contains(p)){
					sum+=d/items.get(d).size();
				}
			}
			if(paid.get(p)-sum!=0){
				map.put(p,paid.get(p)-sum);
			}
		}
		while(!map.isEmpty()){
			if(map.size()==1){
				break;
			}
			List<Map.Entry<Person, Double>> list = new ArrayList<Map.Entry<Person,Double>>(map.entrySet());
			Collections.sort(list,new Comparator<Map.Entry<Person, Double>>() {

				@Override
				public int compare(Entry<Person, Double> o1, Entry<Person, Double> o2) {
					return Double.compare(o1.getValue(), o2.getValue());
				}
			});
			Person a = list.get(0).getKey();
			Person b= list.get(list.size()-1).getKey();
			double dif = map.get(b)+map.get(a);
			if(dif<0){
				a.addDebt(b, -map.get(b));
				map.remove(b);
				map.put(a,dif);
			}
			else if(dif>0){
				a.addDebt(b, map.get(a));
				map.remove(a);
				map.put(b, dif);
			}
			else{
				a.addDebt(b, map.get(a));
				map.remove(a);
				map.remove(b);
			}
		}
	}
	public void addItem(double price, List<Person> people){
		items.put(price, people);
	}
	public void addPerson(Person person, Double d){
		paid.put(person, d);
	}
	public LocalDateTime getDate() {
		return date;
	}
	public Person getPerson(Person p){
		return getPerson(p.getName());
		
	}
	public Person getPerson(String name) {
		for (Person p : paid.keySet()) {
			if(p.getName().equals(name)){
				return p;
			}
		}
		return null;
	}
	@Override
	public String toString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm",Locale.US);
		return date.format(dtf);
	}
	public Map<Double, List<Person>> getItems() {
		return items;
	}
	public Map<Person, Double> getPaid() {
		return paid;
	}
	public int getPersonNumber() {
		return paid.keySet().size();
	}
	
}
