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


import javax.swing.JPanel;

import dc.model.Person;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

@SuppressWarnings("serial")
public class DebtPanel extends JPanel {
	private Person p;
	/**
	 * Create the panel.
	 */
	public DebtPanel(Person pp, Person p2) {
		this.p=pp;
		setLayout(null);
		this.setPreferredSize(new Dimension(400,30));
		
		JLabel label = new JLabel(p2.getName());
		label.setBounds(10, 8, 90, 14);
		add(label);
		
		JLabel lblNewLabel = new JLabel(String.valueOf(-p.getDebtTo(p2)));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(110, 8, 79, 14);
		add(lblNewLabel);
	}

}
