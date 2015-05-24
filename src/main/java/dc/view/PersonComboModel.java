package dc.view;

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


import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import dc.model.Person;

@SuppressWarnings("serial")
public class PersonComboModel extends AbstractListModel<Person> implements
		ComboBoxModel<Person> {
	private List<Person> listp;
	private Person selection;
	
	public PersonComboModel(List<Person> listp) {
		super();
		this.listp = listp;
		selection = null;
	}

	@Override
	public int getSize() {
		return listp.size();
	}

	@Override
	public Person getElementAt(int index) {
		return listp.get(index);
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selection = (Person) anItem;
	}

}
