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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dc.dao.ShoppingDao;
import dc.dao.ShoppingXmlDao;
import dc.model.Person;
import dc.model.Shopping;

@SuppressWarnings("serial")
public class NewShoppingMoneyFrame extends JFrame {
	private class PersonPanel extends JPanel {
		private Person p;
		private JTextField jtf;
		private JLabel jlbl;

		public PersonPanel(Person p) {
			this.p = p;
			this.setLayout(null);
			this.setPreferredSize(new Dimension(400, 30));
			jtf = new JTextField();
			jtf.setText("0.0");
			jtf.addKeyListener(new KeyAdapter() {
				private int dots(){
					for (int i = 0; i < jtf.getText().length(); i++) {
						if(jtf.getText().charAt(i)== '.'){
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
				}
			});
			jtf.setBounds(110, 11, 50, 20);
			this.add(jtf);
			jlbl = new JLabel(p.getName());
			jlbl.setBounds(10, 11, 95, 20);
			this.add(jlbl);
		}

		public Person getP() {
			return p;
		}

		public JTextField getJtf() {
			return jtf;
		}

	}

	private Shopping sp;
	private List<PersonPanel> ppl;
	private JFrame parent;
	private static Logger logger = LoggerFactory
			.getLogger(NewShoppingMoneyFrame.class);

	/**
	 * Create the frame.
	 * 
	 * @param
	 */
	public NewShoppingMoneyFrame(JFrame parent, Shopping shp) {
		setResizable(false);		
		this.sp = shp;
		ppl = new ArrayList<NewShoppingMoneyFrame.PersonPanel>();
		this.parent = parent;
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		NewShoppingMoneyFrame.this.setTitle("Fizetett \u00F6sszegek");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 414, 195);
		getContentPane().add(scrollPane);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0, 0));
		scrollPane.setViewportView(panel_1);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new GridLayout(0, 1, 0, 1));

		for (Person p : sp.getPaid().keySet()) {
			PersonPanel pp = new PersonPanel(p);
			panel_2.add(pp);
			ppl.add(pp);
		}

		JButton btnNewButton = new JButton("Vissza");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				NewShoppingMoneyFrame.this.dispose();
			}
		});
		btnNewButton.setBounds(236, 10, 89, 23);
		getContentPane().add(btnNewButton);

		JButton btnOk = new JButton("Ok\u00E9");
		btnOk.setBounds(335, 10, 89, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double sum = 0;
				for (int i = 0; i < ppl.size(); i++) {
					sum += Double.valueOf(ppl.get(i).getJtf().getText());
				}
				if (sum == sp.getSum()) {
					ShoppingDao dao = new ShoppingXmlDao();
					for (PersonPanel personPanel : ppl) {
						sp.getPaid().put(personPanel.getP(),
								Double.valueOf(personPanel.getJtf().getText()));
					}
					dao.insertShopping(sp);

					JFrame p = getParent();
					while (p.getParent() != null) {
						JFrame pparent = (JFrame) p.getParent();
						p.dispose();
						p = pparent;
					}
					p.setVisible(true);
					NewShoppingMoneyFrame.this.dispose();

				} else {
					JOptionPane.showMessageDialog(null,
							"A fizetett összeg nem egyenlő az áruk összegével",
							"Hiba", JOptionPane.ERROR_MESSAGE);
					logger.error("Hibás összegek");
				}
			}
		});
		getContentPane().add(btnOk);

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
				NewShoppingMoneyFrame.this.dispose();
			}
		});
		getContentPane().add(btnMgse);

		JLabel lblsszegek = new JLabel("Összegek:");
		lblsszegek.setBounds(10, 35, 106, 18);
		getContentPane().add(lblsszegek);

		JLabel lblVgsszeg = new JLabel("Végösszeg: "
				+ String.valueOf(sp.getSum()));
		lblVgsszeg.setBounds(10, 14, 216, 18);
		getContentPane().add(lblVgsszeg);
	}

	public JFrame getParent() {
		return parent;
	}
}
