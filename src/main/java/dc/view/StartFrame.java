package dc.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private JFrame parent;
	public StartFrame() {
		this.parent=null;
		setResizable(false);
		setBounds(100, 100, 375, 247);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton newShoppingButton = new JButton("\u00DAj v\u00E1s\u00E1rl\u00E1s");
		newShoppingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewShoppingPersonFrame nspf = new NewShoppingPersonFrame(StartFrame.this);
				nspf.setVisible(true);
				StartFrame.this.setVisible(false);
			}
		});
		newShoppingButton.setBounds(48, 73, 121, 52);
		getContentPane().add(newShoppingButton);
		
		JButton loadShoppingButton = new JButton("V\u00E1s\u00E1rl\u00E1s bet\u00F6lt\u00E9se");
		loadShoppingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		loadShoppingButton.setBounds(208, 73, 121, 52);
		getContentPane().add(loadShoppingButton);

	}
}
