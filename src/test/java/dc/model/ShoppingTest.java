package dc.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ShoppingTest {
	Shopping s1;
	Person p;
	Person v;
	@Before
	public void setUp() throws Exception {
		s1= new Shopping(LocalDateTime.now());
		v = new Person("VérPistike");
		p = new Person("Petike");
		s1.addItem(500.0, Arrays.asList(new Person[]{v,p}));
		s1.addPerson(v, 500.0);
		s1.addPerson(p, 0.0);
		s1.generateDebt();
	}

	@Test
	public void testCtor() {
		assertNotNull(s1);
	}
	
	@Test
	public void testGenerateD() {
		Shopping s2 = new Shopping(LocalDateTime.now());
		Person v2 = new Person("VérPistike");
		Person p2 = new Person("Petike");
		Person b2 = new Person("Béla");
		s2.addItem(200.0, Arrays.asList(new Person[]{b2}));
		s2.addItem(400.0, Arrays.asList(new Person[]{v2,p2}));
		s2.addItem(300.0,Arrays.asList(new Person[]{v2}));

		s2.addPerson(v2, 400.0);
		s2.addPerson(p2, 500.0);
		s2.addPerson(b2, 0.0);
		s2.generateDebt();
		
		assertEquals(-100, v2.getDebtTo(p2), 0.0001);
		assertEquals(-200, b2.getDebtTo(p2), 0.0001);
		
	
	}
	@Test
	public void testAddItem() {
		s1.addItem(666.0, Arrays.asList(new Person[]{v,p}));
		assertTrue(s1.getItems().containsKey(666.0));
	}
	@Test
	public void testGetters() {
		Map<Double, List<Person>> map =  new HashMap<Double, List<Person>>();
		map.put(500.0, Arrays.asList(new Person[]{v,p}));
		assertEquals(map, s1.getItems());
		Map<Person, Double> map2 = new HashMap<Person, Double>();
		map2.put(v, 500.0);
		map2.put(p, 0.0);
		assertEquals(map2, s1.getPaid());
		LocalDateTime ldt = LocalDateTime.now();
		assertEquals(ldt, s1.getDate());
		assertEquals(2, s1.getPersonNumber());
	}
	@Test
	public void testGetPerson() {
		Person po = s1.getPerson(v);
		assertEquals(v, po);
		assertEquals(v, s1.getPerson("VérPistike"));
		assertTrue(s1.getPerson("Pite")==null);
	}
	@Test
	public void testGenerateD2() {
		Shopping s2 = new Shopping(LocalDateTime.now());
		Person v2 = new Person("VérPistike");
		Person p2 = new Person("Petike");
		s2.addItem(400.0, Arrays.asList(new Person[]{v2,p2}));
		s2.addItem(300.0,Arrays.asList(new Person[]{v2}));
		s2.addPerson(v2, 500.0);
		s2.addPerson(p2, 200.0);
		
		assertEquals(0.0, v2.getDebtTo(p2), 0.0001);
	}
}
