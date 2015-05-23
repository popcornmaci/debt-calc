package dc.view;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dc.model.Shopping;

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
