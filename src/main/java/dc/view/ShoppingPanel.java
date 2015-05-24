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


import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dc.model.Shopping;

@SuppressWarnings("serial")
public class ShoppingPanel extends JPanel {
	private Shopping sp;
	private JCheckBox checkBox;
	/**
	 * Create the panel.
	 */
	public ShoppingPanel(Shopping sp) {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(400, 30));
		this.sp = sp;
		
		checkBox = new JCheckBox("");
		checkBox.setBounds(10, 4, 21, 23);
		add(checkBox);
		
		JLabel lblNewLabel = new JLabel(String.format("%s, %d f\u0151", sp,sp.getPersonNumber()));
		lblNewLabel.setBounds(39, 10, 351, 14);
		add(lblNewLabel);	
	}
	public boolean isChecked(){
		return checkBox.isSelected(); 
	}
	public Shopping getShopping() {
		return sp;
	}
}
