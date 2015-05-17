package dc.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import dc.model.Person;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import java.awt.GridLayout;

public class NewShoppingItemFrame extends JFrame {

	private JPanel contentPane;
	private List<Person> person;
	private List<JTextField> itemValue;
	private List<JCheckBox[]> personCheckbox;
	private List<JPanel> panels;
	/**
	 * Create the frame.
	 */
	public NewShoppingItemFrame() {
		person = new ArrayList<Person>();
		itemValue = new ArrayList<JTextField>();
		personCheckbox = new ArrayList<JCheckBox[]>();
		panels = new ArrayList<JPanel>();
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("M\u00E9gse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(236, 10, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton NextButton = new JButton("Tov\u00E1bb");
		NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		NextButton.setBounds(335, 10, 89, 23);
		contentPane.add(NextButton);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 414, 193);
		contentPane.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0,0));
		scrollPane.setViewportView(panel_1);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new GridLayout(0,1,0,1));
		
		JButton NewItemButton = new JButton("\u00DAj \u00E1r");
		NewItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel jp = new JPanel();
				jp.setLayout(null);
				jp.setPreferredSize(new Dimension(1000,30));
				JCheckBox[] jcb = generateCheckbox(jp);
				JTextField jt = new JTextField();
				jt.setBounds(10, 11, 50, 20);
				panel_2.add(jp);
				panels.add(jp);
				itemValue.add(jt);
				personCheckbox.add(jcb);
				jp.add(jt);
				contentPane.repaint();
				NewShoppingItemFrame.this.revalidate();

			}
		});
		NewItemButton.setBounds(10, 10, 89, 23);
		contentPane.add(NewItemButton);
	}

	public NewShoppingItemFrame(List<Person> persons) {
		this();
		person=persons;
		
	}
	private JCheckBox[] generateCheckbox(JPanel jp){
		JCheckBox[] jcb= new JCheckBox[person.size()];
		for (int i = 0; i < jcb.length; i++) {
			jcb[i]=new JCheckBox(person.get(i).getName());
			jcb[i].setBounds(66+(85*i), 10, 83, 20);
			jp.add(jcb[i]);
			
		}
		return jcb;
	}
}
