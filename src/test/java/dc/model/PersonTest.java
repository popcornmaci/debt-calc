package dc.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {
	Person v;
	Person p;
	
	@Before
	public void setUp(){
		v= new Person("VérPistike");
		p = new Person("Petike");
	}
	
	@Test
	public void testOneDebt() {

		v.addDebt(p, 165.0);
		assertEquals(165.0, v.getDebt().get(p), 0.0001);
		assertEquals(-165.0, p.getDebt().get(v), 0.0001);
		
	}
	@Test
	public void testMultiDebt() {

		v.addDebt(p, 165.0);
		p.addDebt(v, 100.5);
		assertEquals(64.5, v.getDebt().get(p), 0.0001);
		
	}
	@Test
	public void testBalance(){
		v.addDebt(p, 432.75);
		assertEquals(432.75, v.getBalance(), 0.0001);
		assertEquals(-432.75, p.getBalance(), 0.0001);
	}

}
