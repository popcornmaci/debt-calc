package dc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person {
	private String name;
	private Map<Person, Double> debt;
	public Person(String name) {
		this.name = name;
		this.debt = new HashMap<Person, Double>();
	}
	public static Person from(Person p,List<Shopping> shl){
		Person person = new Person(p.getName());
		for (Shopping shopping : shl) {
			Person ps = shopping.getPerson(person);
			for(Person ps2 : ps.getDebt().keySet()){
				person.addDebt(new Person(ps2.getName()), ps.getDebtTo(ps2));
			}	
		}
		return person;
	}
	public void addDebt(Person p, Double d){
		if(!debt.containsKey(p)){
			debt.put(p, d);
		}
		else{
			debt.put(p,(debt.get(p)+d));
		}
		if(!p.debt.containsKey(this)){
			p.debt.put(this, -d);
		}
		else{
			p.debt.put(this, (p.debt.get(this)-d));
		}
	}
	public String getName() {
		return name;
	}
	public Map<Person, Double> getDebt() {
		return debt;
	}
	public double getBalance(){
		return getBalancePlus()+getBalanceMinus();
	}
	public double getBalancePlus(){
		double sum=0;
		for (Person p : debt.keySet()) {
			if(debt.get(p)>0.0)
			sum+= getDebtTo(p);
		}
		return sum;
	}
	public double getBalanceMinus(){
		double sum=0;
		for (Person p : debt.keySet()) {
			if(debt.get(p)<0.0)
			sum+= getDebtTo(p);
		}
		return sum;
	}
	public double getDebtTo(Person p){
		if(debt.containsKey(p)){
			return debt.get(p);
		}
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return name;
	}
}
