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


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dc.model.Person;

@SuppressWarnings("serial")
public class NewShoppingPersonFrame extends JFrame {

	private JPanel contentPane;
	private List<JLabel> personNumber;
	private List<JTextField> personName;
	private JFrame parent;
	private static Logger logger = LoggerFactory.getLogger(NewShoppingPersonFrame.class);

	/**
	 * Create the frame.
	 */
	public NewShoppingPersonFrame(JFrame parent) {
		setResizable(false);		
		Font f = new Font("tahoma", Font.BOLD, 10);
		this.parent=parent;
		personNumber = new ArrayList<JLabel>();
		personName = new ArrayList<JTextField>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		NewShoppingPersonFrame.this.setTitle("Szem\u00E9lyek hozz\u00E1ad\u00E1sa");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSzemlyekSzma = new JLabel("Szem\u00E9lyek sz\u00E1ma:");
		lblSzemlyekSzma.setBounds(10, 11, 121, 21);
		lblSzemlyekSzma.setFont(f);
		contentPane.add(lblSzemlyekSzma);

		JPanel panel = new JPanel();
		panel.setBounds(10, 66, 414, 184);
		contentPane.add(panel);
		panel.setLayout(null);

		JSpinner spinner = new JSpinner();
		spinner.setEditor(new JSpinner.DefaultEditor(spinner));
		((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setValue(2);
		spinner.setBounds(135, 11, 39, 20);
		spinner.addChangeListener(new ChangeListener() {
			int oldValue = 2;

			public void stateChanged(ChangeEvent arg0) {
				int x = (Integer) spinner.getValue();
				if (oldValue < x) {
					JTextField jt = new JTextField();
					JLabel lblPersons = new JLabel(String.format(
							"%d. Szem\u00E9ly:", x));
					lblPersons.setFont(f);
					if (x > 5) {
						jt.setBounds(285, 11 + 35 * (x - 6), 120, 20);
						lblPersons.setBounds(210, 11 + 35 * (x - 6), 75, 20);
					} else {
						jt.setBounds(81, 11 + 35 * (x - 1), 120, 20);
						lblPersons.setBounds(10, 11 + 35 * (x - 1), 75, 20);
					}
					personName.add(jt);
					panel.add(jt);
					personNumber.add(lblPersons);
					panel.add(lblPersons);

				} else if (oldValue > x) {
					JTextField tf = personName.remove(x);
					panel.remove(tf);
					JLabel jl = personNumber.remove(x);
					panel.remove(jl);
				}
				oldValue = x;
				panel.repaint();
			}
		});
		spinner.setModel(new SpinnerNumberModel(2, 2, 10, 1));
		contentPane.add(spinner);

		JLabel lblNevek = new JLabel("Nevek:");
		lblNevek.setBounds(10, 43, 70, 20);
		contentPane.add(lblNevek);

		JLabel lblPersons = new JLabel("1. Szem\u00E9ly:");
		lblPersons.setFont(f);
		lblPersons.setBounds(10, 11, 70, 20);
		personNumber.add(lblPersons);
		panel.add(lblPersons);

		JTextField jt = new JTextField();
		jt.setBounds(81, 11, 120, 20);
		personName.add(jt);
		panel.add(jt);

		JLabel lblPersons2 = new JLabel("2. Szem\u00E9ly:");
		lblPersons2.setBounds(10, 11+35, 70, 20);
		lblPersons2.setFont(f);
		personNumber.add(lblPersons2);
		panel.add(lblPersons2);

		JTextField jt2 = new JTextField();
		jt2.setBounds(81, 11+35, 120, 20);
		personName.add(jt2);
		panel.add(jt2);
		
		JButton btnNewButton = new JButton("Vissza");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				NewShoppingPersonFrame.this.dispose();
			}
		});
		btnNewButton.setBounds(236, 10, 89, 23);
		contentPane.add(btnNewButton);

		JButton NextButton = new JButton("Tov\u00E1bb");
		NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Person> persons = new ArrayList<Person>();
				for (JTextField jt : personName) {
					if (!jt.getText().trim().isEmpty()) {
						persons.add(new Person(jt.getText()));
					}
				}
				if(persons.size()!=(Integer)spinner.getValue()){
					JOptionPane.showMessageDialog(null, "Hibás név!","Hiba", JOptionPane.ERROR_MESSAGE);
					logger.error("Érvénytelen művelet!");
					return;
				}
				NewShoppingItemFrame nsif = new NewShoppingItemFrame(NewShoppingPersonFrame.this,persons);
				nsif.setVisible(true);
				NewShoppingPersonFrame.this.setVisible(false);
				logger.info("Következő frame");
			}
		});
		NextButton.setBounds(335, 10, 89, 23);
		contentPane.add(NextButton);
	}
	public JFrame getParent(){
		return parent;
	}
}
