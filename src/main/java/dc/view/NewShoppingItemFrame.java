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


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dc.model.Person;
import dc.model.Shopping;

@SuppressWarnings("serial")
public class NewShoppingItemFrame extends JFrame {

	private JPanel contentPane;
	private List<Person> person;
	private List<JTextField> itemValue;
	private List<JCheckBox[]> personCheckbox;
	private List<JPanel> panels;
	private JFrame parent;
	private static Logger logger = LoggerFactory
			.getLogger(NewShoppingItemFrame.class);

	/**
	 * Create the frame.
	 */
	public NewShoppingItemFrame() {
		setResizable(false);
		person = new ArrayList<Person>();
		itemValue = new ArrayList<JTextField>();
		personCheckbox = new ArrayList<JCheckBox[]>();
		panels = new ArrayList<JPanel>();
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		NewShoppingItemFrame.this.setTitle("\u00C1ruk megad\u00E1sa");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Vissza");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				NewShoppingItemFrame.this.dispose();
			}
		});
		btnNewButton.setBounds(236, 10, 89, 23);
		contentPane.add(btnNewButton);

		JButton btnMgse = new JButton("M\u00E9gse");
		btnMgse.setBounds(286, 32, 89, 23);
		btnMgse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame p = getParent();
				while (p.getParent() != null) {
					JFrame pparent = (JFrame) p.getParent();
					p.dispose();
					p = pparent;
				}
				p.setVisible(true);
				NewShoppingItemFrame.this.dispose();
			}
		});
		contentPane.add(btnMgse);

		JButton NextButton = new JButton("Tov\u00E1bb");
		NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Shopping sp = new Shopping(LocalDateTime.now());
				int code = 0;
				for (int i = 0; i < itemValue.size(); i++) {
					double value = Double.valueOf(itemValue.get(i).getText());
					for (int j = i + 1; j < itemValue.size(); j++) {
						if (Double.valueOf(itemValue.get(j).getText()).equals(
								Double.valueOf(itemValue.get(i).getText()))) {
							JOptionPane.showMessageDialog(null,
									"Nem lehet két egyforma összeg!", "Hiba",
									JOptionPane.ERROR_MESSAGE);
							logger.error("Ugyanolyan értékek");
							code = 1;
							return;
						}
					}
					if (code > 0) {
						return;
					}
					List<Person> lp = new ArrayList<Person>();
					for (int j = 0; j < person.size(); j++) {
						if (personCheckbox.get(i)[j].isSelected()) {
							lp.add(person.get(j));
						}
						sp.addPerson(person.get(j), 0.0);
					}
					for (int k = 0; k < itemValue.size(); k++) {
						if (Double.valueOf(itemValue.get(k).getText()) == 0.0) {
							JOptionPane.showMessageDialog(null,
									"Nem lehet nulla az ár!", "Hiba",
									JOptionPane.ERROR_MESSAGE);
							logger.error("Nulla érték maradt");
							code = 1;
							return;
						}
					}
					if (code > 0) {
						return;
					}
					if (lp.isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Minden sorban pipálj be egyet!", "Hiba",
								JOptionPane.ERROR_MESSAGE);
						logger.error("Nincs minden sorban pipa");
						return;
					}
					sp.addItem(value, lp);
				}
				NewShoppingMoneyFrame nsmf = new NewShoppingMoneyFrame(
						NewShoppingItemFrame.this, sp);
				nsmf.setVisible(true);
				NewShoppingItemFrame.this.setVisible(false);
				logger.info("Következő frame");
			}
		});
		NextButton.setBounds(335, 10, 89, 23);
		contentPane.add(NextButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 414, 193);
		contentPane.add(scrollPane);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0, 0));
		scrollPane.setViewportView(panel_1);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new GridLayout(0, 1, 0, 1));

		JButton NewItemButton = new JButton("\u00DAj \u00E1r");
		NewItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel jp = new JPanel();
				jp.setLayout(null);
				jp.setPreferredSize(new Dimension(1000, 30));
				JCheckBox[] jcb = generateCheckbox(jp);
				JTextField jt = new JTextField();
				jt.setText("0.0");
				jt.addKeyListener(new KeyAdapter() {
					private int dots(){
						for (int i = 0; i < jt.getText().length(); i++) {
							if(jt.getText().charAt(i)== '.'){
								return 1;
							}
						}
						return 0;
					}
					public void keyTyped(KeyEvent e) {
						char c = e.getKeyChar();
						if ((c < '0' || c > '9') && c != '\b') {
							if (c != '.' || dots() > 0) {
								e.consume();
							}
					}
				}});
				jt.setBounds(10, 11, 50, 20);
				panel_2.add(jp);
				panels.add(jp);
				itemValue.add(jt);
				personCheckbox.add(jcb);
				jp.add(jt);
				contentPane.repaint();
				NewShoppingItemFrame.this.revalidate();

			}
		});
		NewItemButton.setBounds(10, 10, 89, 23);
		contentPane.add(NewItemButton);
	}

	public NewShoppingItemFrame(JFrame parent, List<Person> persons) {
		this();
		this.parent = parent;
		person = persons;

	}

	private JCheckBox[] generateCheckbox(JPanel jp) {
		JCheckBox[] jcb = new JCheckBox[person.size()];
		for (int i = 0; i < jcb.length; i++) {
			jcb[i] = new JCheckBox(person.get(i).getName());
			jcb[i].setBounds(66 + (97 * i), 10, 95, 20);
			jp.add(jcb[i]);

		}
		return jcb;
	}

	public JFrame getParent() {
		return parent;
	}
}
