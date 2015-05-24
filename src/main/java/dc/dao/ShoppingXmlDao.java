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


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringJoiner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dc.model.Person;
import dc.model.Shopping;
/**
 * Ez az osztály xml fájlban történő olvasást, írást implementálja.
 * @author Kökéndy Tímea
 *
 */
public class ShoppingXmlDao implements ShoppingDao {
	/**
	 * A forrás dokumentum.
	 */
	private Document doc;
	/**
	 * ShoppingXmlDao loggere.
	 */
	private static Logger logger = LoggerFactory.getLogger(ShoppingXmlDao.class);
	/**
	 * Példányosít egy {@code ShoppingXmlDao} objektumot egy File alapján.
	 * @param path a File
	 */
	public ShoppingXmlDao(File path){
		File dir = path.getParentFile();
		File file = path;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			if (file.exists()) {
				doc = builder.parse(file);
			} else {
				dir.mkdirs();
				doc = builder.newDocument();
				doc.appendChild(doc.createElement("shoppings"));
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Példányosít egy {@code ShoppingXmlDao} objektumot az alap elérési útvonallal (userhome).
	 */
	public ShoppingXmlDao() {
		this(new File(new File(System.getProperty("user.home"), "debt-calc"), "shoppings.xml"));
	}

	@Override
	public boolean insertShopping(Shopping sp) {
		Element shoppingElem = doc.createElement("shopping");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm",Locale.US);
		shoppingElem.setAttribute("date", sp.getDate().format(dtf));
		doc.getElementsByTagName("shoppings").item(0).appendChild(shoppingElem);
		sp.generateDebt();

		Element personsElem = doc.createElement("persons");
		shoppingElem.appendChild(personsElem);

		for (Person p : sp.getPaid().keySet()) {
			Element personElem = doc.createElement("person");
			personsElem.appendChild(personElem);
			
			Element nameElem = doc.createElement("name");
			nameElem.appendChild(doc.createTextNode(p.getName()));
			personElem.appendChild(nameElem);
			
			Element paidElem = doc.createElement("paid");
			paidElem.appendChild(doc.createTextNode(String.valueOf(sp.getPaid().get(p))));
			personElem.appendChild(paidElem);
		}
		
		Element itemsElem= doc.createElement("items");
		shoppingElem.appendChild(itemsElem);
		
		for (Double val : sp.getItems().keySet()) {
			Element itemElem = doc.createElement("item");
			itemsElem.appendChild(itemElem);
			
			Element valueElem = doc.createElement("value");
			valueElem.appendChild(doc.createTextNode(String.valueOf(val)));
			itemElem.appendChild(valueElem);
			
			Element namesElem = doc.createElement("names");
			StringJoiner sj= new StringJoiner(";");
			for (Person p : sp.getItems().get(val)) {
				sj.add(p.getName());
			}
			namesElem.appendChild(doc.createTextNode(sj.toString()));
			itemElem.appendChild(namesElem);	
		}
		
		Element personDebtElem= doc.createElement("person_debt");
		shoppingElem.appendChild(personDebtElem);
		
		for (Person person : sp.getPaid().keySet()) {
			if(person.getBalanceMinus()!=0){
				Element personElem=doc.createElement("person");
				personElem.setAttribute("name", person.getName());
				personDebtElem.appendChild(personElem);
				
				for (Person person2 : person.getDebt().keySet()) {
					if(person.getDebtTo(person2)<0){
						Element personsDebtElem= doc.createElement("persons");
						personElem.appendChild(personsDebtElem);
						
						Element nameElem= doc.createElement("name");
						nameElem.appendChild(doc.createTextNode(person2.getName()));
						personsDebtElem.appendChild(nameElem);
						
						Element valueElem= doc.createElement("value");
						valueElem.appendChild(doc.createTextNode(String.valueOf(person2.getDebtTo(person))));
						personsDebtElem.appendChild(valueElem);
						
					}
				}
			}
		}

		try {
			writeXml();
			logger.info("A vásárlás sikeresen be lett szúrva.");
			return true;
		} catch (TransformerException e) {
			e.printStackTrace();
			logger.error("A beszúrás sikertelen volt.");
		}
		return false;
	}
	/**
	 * Ez a metódus kiírja a fájlba a felépített dokumentumot.
	 * @throws TransformerException ha hiba történik a kiírás során
	 */
	private void writeXml() throws TransformerException{

		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource ds = new DOMSource(doc);
		StreamResult r = new StreamResult(new File(new File(System.getProperty("user.home"), "debt-calc"),"shoppings.xml"));
		tf.transform(ds, r);
		}
	

	@Override
	public List<Person> getAllPerson() {
		NodeList shoppings = doc.getElementsByTagName("shopping");
		Set<Person> set= new HashSet<Person>();
		for (int i = 0; i < shoppings.getLength(); i++) {
			Element shoppingElem = (Element)shoppings.item(i);
			NodeList persons = shoppingElem.getElementsByTagName("person");
			for (int j = 0; j < persons.getLength(); j++) {
				Element personElem = (Element)persons.item(j);
				set.add(new Person(personElem.getElementsByTagName("name").item(0).getTextContent()));
			}
		}
		List<Person> listp= new ArrayList<Person>(set);
		logger.info("{} db személy lett beolvasva",listp.size());
		return listp;
	}

	@Override
	public Shopping getShoppingByDate(LocalDateTime ld) {
		NodeList shoppings = doc.getElementsByTagName("shopping");
		for (int i = 0; i < shoppings.getLength(); i++) {
			Element shoppingElem = (Element)shoppings.item(i);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm",Locale.US);
			LocalDateTime ldt = LocalDateTime.parse(shoppingElem.getAttribute("date"),dtf);
			if(ld.equals(ldt)){
				Shopping sp = new Shopping(ld);
				Element personsElem = (Element) shoppingElem.getElementsByTagName("persons").item(0);
				NodeList person = personsElem.getElementsByTagName("person");
				for (int j = 0; j < person.getLength(); j++) {
					Element personElem = (Element) person.item(j);
					sp.addPerson(new Person(personElem.getElementsByTagName("name").item(0).getTextContent()), 
							Double.valueOf(personElem.getElementsByTagName("paid").item(0).getTextContent()));
				}
				NodeList items = shoppingElem.getElementsByTagName("item");
				for (int j = 0; j < items.getLength(); j++) {
					Element itemElem = (Element) items.item(j);
					double value = Double.valueOf(itemElem.getElementsByTagName("value").item(0).getTextContent());
					String str = itemElem.getElementsByTagName("names").item(0).getTextContent();
					String[] strt = str.split(";");
					List<Person> listp = new ArrayList<Person>();
					for (String string : strt) {
						listp.add(sp.getPerson(string));
					}
					sp.addItem(value, listp);
				}
				Element personDebtElem = (Element)shoppingElem.getElementsByTagName("person_debt").item(0);
				NodeList personElem = personDebtElem.getElementsByTagName("person");
				for (int j = 0; j < personElem.getLength(); j++) {
					Element debtNode = (Element) personElem.item(j);
					NodeList personsElem2 = debtNode.getElementsByTagName("persons");
					for (int k = 0; k < personsElem2.getLength(); k++) {
						Element personElem2= (Element) personsElem2.item(k);
						Person p = sp.getPerson(debtNode.getAttribute("name"));
						Person p2 = sp.getPerson(personElem2.getElementsByTagName("name").item(0).getTextContent());
						Double d = Double.valueOf(personElem2.getElementsByTagName("value").item(0).getTextContent());
						p.addDebt(p2, -d);
					}
				}
				logger.info("A vásárlás sikeresen kiolvasva");
				return sp;
			}
		}
		logger.info("Nincs ilye dátumú vásárlás");
		return null;
	}
	@Override
	public List<Shopping> getShoppingsByPerson(Person p) {
		NodeList shoppings = doc.getElementsByTagName("shopping");
		List<Shopping> listsp= new ArrayList<Shopping>();
		for (int i = 0; i < shoppings.getLength(); i++) {
			Element shoppingElem = (Element)shoppings.item(i);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm",Locale.US);
			LocalDateTime ldt = LocalDateTime.parse(shoppingElem.getAttribute("date"),dtf);
			Shopping sp = getShoppingByDate(ldt);
			if(sp.getPerson(p)!=null){
				listsp.add(sp);
			}
		}
		logger.info("{} db vásárlás lett beolvasva",listsp.size());
		return listsp;
	}

}
