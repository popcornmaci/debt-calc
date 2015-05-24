package dc.dao;

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
import java.util.List;

import dc.model.Person;
import dc.model.Shopping;
/**
 * Dao interfész
 * metódusokat szolgáltat az adatok elérésére.
 * @author Kökéndy Tímea
 *
 */
public interface ShoppingDao {
	/**
	 * Ez a metódus visszaad egy {@link Shopping} objektumot a megadott dátum alapján.
	 * @param ld a dátum
	 * @return a vásárlás a megadott dátum alapján
	 */
	public Shopping getShoppingByDate(LocalDateTime	 ld);
	/**
	 * Ez a metódus beszúrja az adatbázisba a megadott vásárlást.
	 * @param sp a vásárlás
	 * @return visszaadja a beszúrás sikerességét
	 */
	public boolean insertShopping(Shopping sp);
	/**
	 * Ez a metódus visszaad {@link Shopping} objektumok egy listáját, amelyet a megadott személy alapján állít össze.
	 * @param p a személy
	 * @return azok a vásárlások, amelyekben a megadott személy részt vett.
	 */
	public List<Shopping> getShoppingsByPerson(Person p);
	/**
	 * Ez a metódus az adatbázisban lévő összes személyt megadja.
	 * @return az összes vásárlásban résztvevő összes személy
	 */
	public List<Person> getAllPerson();
}
