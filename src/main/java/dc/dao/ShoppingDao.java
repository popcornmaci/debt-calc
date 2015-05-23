package dc.dao;

import java.time.LocalDateTime;
import java.util.List;

import dc.model.Person;
import dc.model.Shopping;

public interface ShoppingDao {
	public Shopping getShoppingByDate(LocalDateTime	 ld);
	public boolean insertShopping(Shopping sp);
	public boolean updateShopping(Shopping sp);
	public List<Shopping> getShoppingsByPerson(Person p);
	public List<Person> getAllPerson();
}
