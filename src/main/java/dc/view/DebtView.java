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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dc.model.Person;
import dc.model.Shopping;

@SuppressWarnings("serial")
public class DebtView extends JFrame {
	private JFrame parent;
	private List<Shopping> shl;
	private Person p;

	/**
	 * Create the frame.
	 */
	public DebtView(JFrame fparent, List<Shopping> shpl, Person pe) {
		setResizable(false);		
		Font f = new Font("tahoma", Font.BOLD, 10);
		this.parent = fparent;
		this.shl = shpl;
		this.p = pe;
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		DebtView.this.setTitle("\u00D6sszes tartoz\u00E1s");

		JLabel lblNewLabel = new JLabel(String.format(
				"%s tartozik a k\u00F6vetkez\u0151 embereknek:", p.getName()));
		lblNewLabel.setBounds(10, 32, 264, 14);
		lblNewLabel.setFont(f);
		getContentPane().add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 414, 193);
		getContentPane().add(scrollPane);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0, 0));
		scrollPane.setViewportView(panel_1);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new GridLayout(0, 1, 0, 1));

		p = Person.from(p, shl);
		for (Person person : p.getDebt().keySet()) {
			if (p.getDebtTo(person) < 0) {
				DebtPanel dp = new DebtPanel(p, person);
				panel_2.add(dp);
			}
		}
		if(panel_2.getComponentCount()==0){
			panel_2.add(new JLabel("Nem tartozik senkinek."));
		}
		getContentPane().revalidate();

		JButton btnNewButton = new JButton("Vissza");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				DebtView.this.dispose();
			}
		});
		btnNewButton.setBounds(286, 10, 89, 23);
		getContentPane().add(btnNewButton);
	}
}
