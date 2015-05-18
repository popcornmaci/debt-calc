package dc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dc.model.Person;
import dc.model.Shopping;

public class NewShoppingMoneyFrame extends JFrame {
	private class PersonPanel extends JPanel{
		private Person p;
		private JTextField jtf;
		private JLabel jlbl;
		public PersonPanel(Person p){
			this.p=p;
			this.setLayout(null);
			this.setPreferredSize(new Dimension(400,30));
			jtf =new JTextField();
			jtf.setBounds(65, 11, 50, 20);
			this.add(jtf);
			jlbl = new JLabel(p.getName());
			jlbl.setBounds(10, 11, 50, 20);
			this.add(jlbl);
		}
		public Person getP() {
			return p;
		}
		public JTextField getJtf() {
			return jtf;
		}
		public JLabel getJlbl() {
			return jlbl;
		}
		
	}
	private Shopping shop;
	private List<PersonPanel> ppl;
	private JFrame parent;
	/**
	 * Create the frame.
	 * @param  
	 */
	public NewShoppingMoneyFrame(JFrame parent, Shopping sp) {
		shop = sp;
		ppl = new ArrayList<NewShoppingMoneyFrame.PersonPanel>();
		this.parent=parent;
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 414, 195);
		getContentPane().add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0,0));
		scrollPane.setViewportView(panel_1);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new GridLayout(0,1,0,1));
		
		for (Person p : shop.getPaid().keySet()) {
			PersonPanel pp= new PersonPanel(p);
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
		
		JButton btnMgse = new JButton("M\u00E9gse");
		btnMgse.setBounds(286, 32, 89, 23);
		btnMgse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame p = getParent();
				while(p.getParent()!=null){
					JFrame pparent = (JFrame) p.getParent();
					p.dispose();
					p=pparent;
				}
				p.setVisible(true);
				NewShoppingMoneyFrame.this.dispose();
			}
		});
		getContentPane().add(btnMgse);
	}
	public JFrame getParent(){
		return parent;
	}
}
