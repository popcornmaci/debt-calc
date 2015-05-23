package dc.view;

import dc.dao.ShoppingXmlDao;
import dc.model.Person;
import dc.model.Shopping;

public class Main {

	public static void main(String[] args) {
		ShoppingXmlDao asd= new ShoppingXmlDao();
		Person p = new Person("p");
		for (Double string : asd.getShoppingsByPerson(p).get(0).getPerson(p).getDebt().values()) {
			System.out.println(string);
		}

	}

}
