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
/**
 * 
 * Vásárlás reprezentálására szolgáló osztály.
 * @author Kökéndy Tímea
 *
 */
public class Shopping {
	/**
	 * A vásárlás dátuma.
	 */
	private LocalDateTime date;
	/**
	 * A vásárolt áruk.
	 */
	private Map<Double, List<Person>> items;
	/**
	 * A vásárlásban résztvevő személyek által fizetett összegek.
	 */
	private Map<Person, Double> paid;
	/**
	 * Példányosít egy {@code Shopping} objektumot a megadott dátum alapján.
	 * @param date a vásárlás dátuma
	 */
	public Shopping(LocalDateTime date) {
		this.date = date;
		this.items = new HashMap<Double, List<Person>>();
		this.paid = new HashMap<Person, Double>();
	}
	/**
	 * Vásárlásban résztvevő személyek tartozásait felépíti.
	 */
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
			//ha nem tudta rendezni az egész tartozást
			if(dif<0){
				a.addDebt(b, -map.get(b));
				map.remove(b);
				map.put(a,dif);
			}
			//ha rendezte és már nincs másfelé tartozása
			else if(dif>0){
				a.addDebt(b, map.get(a));
				map.remove(a);
				map.put(b, dif);
			}
			//ha pont ki tudta egyenlíteni
			else{
				a.addDebt(b, map.get(a));
				map.remove(a);
				map.remove(b);
			}
		}
	}
	/**
	 * Hozzáad ehhez a vásárláshoz egy áru értékét és azon személyeket, akik azt az árut vásárolják.
	 * @param price az áru értéke
	 * @param people azok személyek, akik vásárolják az árut
	 */
	public void addItem(double price, List<Person> people){
		items.put(price, people);
	}
	/**
	 * Hozzáad ehhez a vásárláshoz egy személyt azzal az összeggel, amennyit fizetett a vásárlás során.
	 * @param person a személy, aki fizetett a vásárlás során
	 * @param d az összeg, amelyet a megadott személy fizetett
	 */
	public void addPerson(Person person, Double d){
		paid.put(person, d);
	}
	/**
	 * Megadja a vásárlás dátumát.
	 * @return a vásárlás dátuma (év,honap,nap,óra,perc)
	 */
	public LocalDateTime getDate() {
		return date;
	}
	/**
	 * Megadja a vásárlásban résztvevő személyt (ha létezik) a megadott személy alapján.
	 * @param p a személy
	 * @return a vásárlásban résztvevő személy, {@code null} ha nem létezik
	 */
	public Person getPerson(Person p){
		return getPerson(p.getName());
	}
	/**
	 * Megadja a vásárlásban résztvevő személyt (ha létezik) a megadott név alapján.
	 * @param name a személy neve
	 * @return a vásárlásban résztvevő személy, {@code null} ha nem létezik
	 */
	public Person getPerson(String name) {
		for (Person p : paid.keySet()) {
			if(p.getName().equals(name)){
				return p;
			}
		}
		return null;
	}
	/**
	 * Megadja a vásárolt árukat és azokat a személyeket, akik az árukat vették.
	 * @return a vásárolt áruk
	 */
	public Map<Double, List<Person>> getItems() {
		return items;
	}
	/**
	 * Megadja a vásárlás során fizetett összegeket személyenként.
	 * @return a vásárlásban fizetett összegek személyenként
	 */
	public Map<Person, Double> getPaid() {
		return paid;
	}
	/**
	 * Megadja a vásárlásban résztvevő személyek számát.
	 * @return vásárlásban résztvevő személyek száma
	 */
	public int getPersonNumber() {
		return paid.keySet().size();
	}
	/**
	 * Megadja az áruk összértékét.
	 * @return az áruk összértéke
	 */
	public double getSum(){
		double sum=0.0;
		for (Double d : items.keySet()) {
			sum += d;
		}
		return sum;
	}
	@Override
	public String toString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm",Locale.US);
		return date.format(dtf);
	}
	@Override
	public int hashCode() {
		int result = 1;
		result =date.hashCode();
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Shopping){
			Shopping s=(Shopping) obj;
			return date.equals(s.date);
		}
		return false;
	}
	
}
