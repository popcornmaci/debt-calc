package dc.model;

/*
 * #%L
 * Debt Calculator
 * %%
 * Copyright (C) 2015 Faculty of Informatics, University of Debrecen
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * Személyek reprezentálására szolgáló osztály. 
 * @author Kökéndy Tímea
 *
 */
public class Person {
	/**
	 * A személy neve.
	 */
	private String name;
	/**
	 * A személy tartozásai.
	 * Az egyes személyeknél lévő érték lehet negatív és pozitív, 
	 * ha a személy tartozik az illetőnek, illetve ha a személynek tartozik az illető.
	 */
	private Map<Person, Double> debt;
	/**
	 * Példányosít egy {@code Person} objektumot a megadott névvel.
	 * 
	 * @param name a személy neve
	 */
	public Person(String name) {
		this.name = name;
		this.debt = new HashMap<Person, Double>();
	}
	/**
	 * Létrehoz egy {@code Person} objektumot a megadott személy alapján
	 * olyan módon, hogy a megadott {@link Shopping} objektumokból összegzi a tartozásait.
	 * 
	 * @param p az a személy, akinek összegezni kívánjuk a tartozásait
	 * @param shl az összegzendő vásárlások
	 * @return az összegzett tartozású személy
	 */
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
	/**
	 * Hozzáad ehhez a személyhez egy tartozást a megadott személy és érték alapján.
	 * Ha ez a személy tartozik a paraméterben megadottnak, akkor az érték legyen negatív.
	 * Ha már létezik tartozás a két személy között, akkor a metódus hozzáadja az új értéket a régihez.
	 * Ez a metódus a paraméterben megadott személy tartozását is frissíti.
	 * @param p a tartozás másik résztvevője
	 * @param d a tartozas értéke
	 */
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
	/**
	 * Megadja ennek a személynek a nevét.
	 * @return a személy neve
	 */
	public String getName() {
		return name;
	}
	/**
	 * Megadja a tartozás tábláját ennek a személynek.
	 * @return a személy tartozásai
	 */
	public Map<Person, Double> getDebt() {
		return debt;
	}
	/**
	 * A személy egyenlegét adja meg.
	 * @return a személy egyenlege
	 */
	public double getBalance(){
		return getBalancePlus()+getBalanceMinus();
	}
	/**
	 * Megadja azt az összértéket, amivel a személynek tartoznak.
	 * @return az összérték, amivel a személynek tartoznak
	 */
	public double getBalancePlus(){
		double sum=0;
		for (Person p : debt.keySet()) {
			if(debt.get(p)>0.0)
			sum+= getDebtTo(p);
		}
		return sum;
	}
	/**
	 * Megadja azt az összértéket, amivel a személy tartozik másoknak.
	 * @return az összérték, amellyel a személy tartozik
	 */
	public double getBalanceMinus(){
		double sum=0;
		for (Person p : debt.keySet()) {
			if(debt.get(p)<0.0)
			sum+= getDebtTo(p);
		}
		return sum;
	}
	/**
	 * Megadja a a megadott személy felé irányuló tartozást.
	 * @param p a másik személy
	 * @return negatív érték, ha ez a személy tartozik a másiknak, pozitív, ha ennek a személynek tartozik a másik, egyébként 0
	 */
	public double getDebtTo(Person p){
		if(debt.containsKey(p)){
			return debt.get(p);
		}
		return 0;
	}
	@Override
	public int hashCode() {
		int result = 1;
		result = name.hashCode();
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Person){
			Person po = (Person) obj;
			return name.equals(po.name);
		}
		return false;
	}
	@Override
	public String toString() {
		return name;
	}
}
