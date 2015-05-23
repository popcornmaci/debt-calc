package dc.view;

import javax.swing.JPanel;

import dc.model.Person;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

public class DebtPanel extends JPanel {
	private Person p;
	/**
	 * Create the panel.
	 */
	public DebtPanel(Person p, Person p2) {
		this.p=p;
		setLayout(null);
		this.setPreferredSize(new Dimension(400,30));
		
		JLabel label = new JLabel(p2.getName());
		label.setBounds(10, 8, 90, 14);
		add(label);
		
		JLabel lblNewLabel = new JLabel(String.valueOf(p.getDebtTo(p2)));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(110, 8, 79, 14);
		add(lblNewLabel);
	}

}
