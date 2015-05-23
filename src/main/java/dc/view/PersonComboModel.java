package dc.view;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import dc.model.Person;

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
