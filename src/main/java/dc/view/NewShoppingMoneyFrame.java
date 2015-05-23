package dc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dc.dao.ShoppingDao;
import dc.dao.ShoppingXmlDao;
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
			jtf = new JTextField();
			jtf.setText("0.0");
			jtf.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e){
					char c = e.getKeyChar();
					if((c < '0'|| c>'9') && c!='\b'){
						if(c!='.'){
							e.consume();
						}
					}
				}
			});
			jtf.setBounds(110, 11, 50, 20);
			this.add(jtf);
			jlbl = new JLabel(p.getName());
			jlbl.setBounds(10, 11, 95, 20);
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
	private Shopping sp;
	private List<PersonPanel> ppl;
	private JFrame parent;
	/**
	 * Create the frame.
	 * @param  
	 */
	public NewShoppingMoneyFrame(JFrame parent, Shopping sp) {
		this.sp = sp;
		ppl = new ArrayList<NewShoppingMoneyFrame.PersonPanel>();
		this.parent=parent;
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		NewShoppingMoneyFrame.this.setTitle("Fizetett \u00F6sszegek");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 414, 195);
		getContentPane().add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0,0));
		scrollPane.setViewportView(panel_1);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new GridLayout(0,1,0,1));
		
		for (Person p : sp.getPaid().keySet()) {
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
		
		JButton btnOk = new JButton("Ok\u00E9");
		btnOk.setBounds(335, 10, 89, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShoppingDao dao = new ShoppingXmlDao();
				for (PersonPanel personPanel : ppl) {
					sp.getPaid().put(personPanel.getP(),Double.valueOf(personPanel.getJtf().getText()));
				}
				dao.insertShopping(sp);
				
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
		getContentPane().add(btnOk);
		
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
		
		JLabel lblsszegek = new JLabel("Összegek:");
		lblsszegek.setBounds(10, 36, 106, 14);
		getContentPane().add(lblsszegek);
	}
	public JFrame getParent(){
		return parent;
	}
}
