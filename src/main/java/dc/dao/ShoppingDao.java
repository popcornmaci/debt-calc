package dc.dao;

import java.time.LocalDateTime;
import java.util.List;

import dc.model.Person;
import dc.model.Shopping;
/**
 * Dao interfész
 * metódusokat szolgáltat az adatok elérésére.
 * @author Tímea
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
