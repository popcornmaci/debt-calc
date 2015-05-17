package dc.model;

import java.util.HashMap;
import java.util.Map;

public class Person {
	private String name;
	private Map<Person, Double> debt;
	public Person(String name) {
		this.name = name;
		this.debt = new HashMap<Person, Double>();
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
}
