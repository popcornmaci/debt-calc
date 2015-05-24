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

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {
	Person v;
	Person p;
	
	@Before
	public void setUp(){
		v = new Person("VérPistike");
		p = new Person("Petike");
	}
	
	@Test
	public void testDebtTo() {

		v.addDebt(p, 165.0);
		assertEquals(165.0, v.getDebtTo(p), 0.0001);
		assertEquals(-165.0, p.getDebtTo(v), 0.0001);
		assertEquals(0.0, p.getDebtTo(new Person("Pite")), 0.0001);
		
	}
	@Test
	public void testGetDebt() {
		Map<Person, Double> map = new HashMap<Person, Double>();
		v.addDebt(p, 165.0);
		map.put(p, 165.0);
		assertEquals(map, v.getDebt());
		
	}
	@Test
	public void testMultiDebt() {

		v.addDebt(p, 165.0);
		p.addDebt(v, 100.5);
		assertEquals(64.5, v.getDebtTo(p), 0.0001);
		
	}
	@Test
	public void testBalance(){
		v.addDebt(p, 432.75);
		assertEquals(432.75, v.getBalance(), 0.0001);
		assertEquals(-432.75, p.getBalance(), 0.0001);
	}
	@Test
	public void testEqualsHashCode() {

		assertTrue(v.equals(v));
		assertTrue(!v.equals(p));
		assertTrue(v.equals(new Person("VérPistike")));
		assertEquals("VérPistike".hashCode(),v.hashCode());
		assertTrue(!v.equals(null));
		assertTrue(!v.equals(1));
	}
	@Test
	public void testFrom(){
		Shopping s1 = new Shopping(LocalDateTime.now());
		Shopping s2 = new Shopping(LocalDateTime.now());
		s1.addItem(500.0, Arrays.asList(new Person[]{v,p}));
		s1.addPerson(v, 500.0);
		s1.addPerson(p, 0.0);
		s1.generateDebt();
		
		Person v2 = new Person("VérPistike");
		Person p2 = new Person("Petike");
		
		s2.addItem(400.0, Arrays.asList(new Person[]{v2,p2}));
		s2.addPerson(v2, 150.0);
		s2.addPerson(p2, 250.0);
		s2.generateDebt();
		
		Person pp = Person.from(p, Arrays.asList(new Shopping[]{s1,s2}));
		assertEquals(-200.0, pp.getDebtTo(v), 0.0001);
		
	}
}
