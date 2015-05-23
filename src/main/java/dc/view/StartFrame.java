package dc.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		setBounds(100, 100, 375, 247);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		StartFrame.this.setTitle("Tartoz\u00E1s Kalkul\u00E1tor");
		
		JButton newShoppingButton = new JButton("\u00DAj v\u00E1s\u00E1rl\u00E1s felv\u00E9tele");
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
