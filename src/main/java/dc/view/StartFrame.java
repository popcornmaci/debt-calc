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


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class StartFrame extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame frame = new StartFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		setResizable(false);
		Font f = new Font("tahoma", Font.PLAIN, 15);
		setBounds(100, 100, 375, 247);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		StartFrame.this.setTitle("Tartoz\u00E1s Kalkul\u00E1tor");
		
		JButton newShoppingButton = new JButton("\u00DAj v\u00E1s\u00E1rl\u00E1s felv\u00E9tele");
		newShoppingButton.setFont(f);
		newShoppingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewShoppingPersonFrame nspf = new NewShoppingPersonFrame(StartFrame.this);
				nspf.setVisible(true);
				StartFrame.this.setVisible(false);
			}
		});
		newShoppingButton.setBounds(10, 73, 165, 52);
		getContentPane().add(newShoppingButton);
		
		JButton loadShoppingButton = new JButton("Tartoz\u00E1s Kalkul\u00E1tor");
		loadShoppingButton.setFont(f);
		loadShoppingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ShoppingListing sl = new ShoppingListing(StartFrame.this);
				sl.setVisible(true);
				StartFrame.this.setVisible(false);
			}
		});
		loadShoppingButton.setBounds(194, 73, 165, 52);
		getContentPane().add(loadShoppingButton);

	}
}
