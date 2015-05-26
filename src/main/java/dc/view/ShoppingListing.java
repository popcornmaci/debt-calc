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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dc.dao.ShoppingXmlDao;
import dc.model.Person;
import dc.model.Shopping;

@SuppressWarnings("serial")
public class ShoppingListing extends JFrame {
	private List<Person> listp;
	private ShoppingXmlDao sxdao;
	private JFrame parent;
	private List<ShoppingPanel> shpanell;
		
	/**
	 * Create the frame.
	 */
	public ShoppingListing(JFrame parent) {
		setResizable(false);
		sxdao= new ShoppingXmlDao();
		listp = sxdao.getAllPerson();
		this.parent=parent;
		shpanell = new ArrayList<ShoppingPanel>();
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		ShoppingListing.this.setTitle("Szem\u00E9ly kiv\u00E1laszt\u00E1sa");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 414, 195);
		getContentPane().add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0,0));
		scrollPane.setViewportView(panel_1);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new GridLayout(0,1,0,1));
		
		JComboBox<Person> comboBox = new JComboBox<Person>(new PersonComboModel(listp));
		comboBox.setBounds(10, 18, 200, 20);
		getContentPane().add(comboBox);
		
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				shpanell.clear();
				panel_2.removeAll();
				List<Shopping> shl= sxdao.getShoppingsByPerson((Person)comboBox.getSelectedItem());
				for (Shopping shopping : shl) {
						shpanell.add(new ShoppingPanel(shopping));
						panel_2.add(shpanell.get(shpanell.size()-1));
				}
				ShoppingListing.this.revalidate();
				
			}
		});
		JButton btnNewButton = new JButton("Vissza");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				ShoppingListing.this.dispose();
			}
		});
		btnNewButton.setBounds(236, 10, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton NextButton = new JButton("Tov\u00E1bb");
		NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Shopping> list = new ArrayList<Shopping>();
				for (ShoppingPanel sp : shpanell) {
					if(sp.isChecked()){
						list.add(sp.getShopping());
					}
				}
				if(list.isEmpty()){
					JOptionPane.showMessageDialog(null, "Legalább egyet pipálj be!","Hiba", JOptionPane.ERROR_MESSAGE);
					return;
				}
				DebtView dv = new DebtView(ShoppingListing.this, list, (Person)comboBox.getSelectedItem());
				dv.setVisible(true);
				ShoppingListing.this.setVisible(false);
			}
		});
		NextButton.setBounds(335, 10, 89, 23);
		getContentPane().add(NextButton);
		
		JLabel lblVsrlsok = new JLabel("Vásárlások:");
		lblVsrlsok.setBounds(10, 42, 124, 14);
		getContentPane().add(lblVsrlsok);
		
		JLabel lblNv = new JLabel("Név:");
		lblNv.setBounds(10, 5, 46, 14);
		getContentPane().add(lblNv);
		

		
		

	}
	public JFrame getParent(){
		return parent;
	}
}
