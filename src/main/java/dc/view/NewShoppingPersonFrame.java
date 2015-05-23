package dc.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dc.model.Person;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class NewShoppingPersonFrame extends JFrame {

	private JPanel contentPane;
	private List<JLabel> personNumber;
	private List<JTextField> personName;
	private JFrame parent;

	/**
	 * Create the frame.
	 */
	public NewShoppingPersonFrame(JFrame parent) {
		this.parent=parent;
		personNumber = new ArrayList<JLabel>();
		personName = new ArrayList<JTextField>();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		NewShoppingPersonFrame.this.setTitle("Szem\u00E9lyek hozz\u00E1ad\u00E1sa");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSzemlyekSzma = new JLabel("Szem\u00E9lyek sz\u00E1ma:");
		lblSzemlyekSzma.setBounds(10, 11, 121, 21);
		contentPane.add(lblSzemlyekSzma);

		JPanel panel = new JPanel();
		panel.setBounds(10, 66, 414, 184);
		contentPane.add(panel);
		panel.setLayout(null);

		JSpinner spinner = new JSpinner();
		spinner.setEditor(new JSpinner.DefaultEditor(spinner));
		((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setValue(2);
		spinner.setBounds(135, 11, 39, 20);
		spinner.addChangeListener(new ChangeListener() {
			int oldValue = 2;

			public void stateChanged(ChangeEvent arg0) {
				int x = (Integer) spinner.getValue();
				if (oldValue < x) {
					JTextField jt = new JTextField();
					JLabel lblPersons = new JLabel(String.format(
							"%d. Szem\u00E9ly:", x));
					if (x > 5) {
						jt.setBounds(285, 11 + 35 * (x - 6), 120, 20);
						lblPersons.setBounds(210, 11 + 35 * (x - 6), 75, 20);
					} else {
						jt.setBounds(81, 11 + 35 * (x - 1), 120, 20);
						lblPersons.setBounds(10, 11 + 35 * (x - 1), 75, 20);
					}
					personName.add(jt);
					panel.add(jt);
					personNumber.add(lblPersons);
					panel.add(lblPersons);

				} else if (oldValue > x) {
					JTextField tf = personName.remove(x);
					panel.remove(tf);
					JLabel jl = personNumber.remove(x);
					panel.remove(jl);
				}
				oldValue = x;
				panel.repaint();
			}
		});
		spinner.setModel(new SpinnerNumberModel(2, 2, 10, 1));
		contentPane.add(spinner);

		JLabel lblNevek = new JLabel("Nevek:");
		lblNevek.setBounds(10, 43, 70, 20);
		contentPane.add(lblNevek);

		JLabel lblPersons = new JLabel("1. Szem\u00E9ly:");
		lblPersons.setBounds(10, 11, 70, 20);
		personNumber.add(lblPersons);
		panel.add(lblPersons);

		JTextField jt = new JTextField();
		jt.setBounds(81, 11, 120, 20);
		personName.add(jt);
		panel.add(jt);

		JLabel lblPersons2 = new JLabel("2. Szem\u00E9ly:");
		lblPersons2.setBounds(10, 11+35, 70, 20);
		personNumber.add(lblPersons2);
		panel.add(lblPersons2);

		JTextField jt2 = new JTextField();
		jt2.setBounds(81, 11+35, 120, 20);
		personName.add(jt2);
		panel.add(jt2);
		
		JButton btnNewButton = new JButton("Vissza");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				NewShoppingPersonFrame.this.dispose();
			}
		});
		btnNewButton.setBounds(236, 10, 89, 23);
		contentPane.add(btnNewButton);

		JButton NextButton = new JButton("Tov\u00E1bb");
		NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Person> persons = new ArrayList<Person>();
				for (JTextField jt : personName) {
					if (!jt.getText().trim().isEmpty()) {
						persons.add(new Person(jt.getText()));
					}
				}
				if(persons.size()!=(Integer)spinner.getValue()){
					JOptionPane.showMessageDialog(null, "Hibás név!","Hiba", JOptionPane.ERROR_MESSAGE);
					return;
				}
				NewShoppingItemFrame nsif = new NewShoppingItemFrame(NewShoppingPersonFrame.this,persons);
				nsif.setVisible(true);
				NewShoppingPersonFrame.this.setVisible(false);
			}
		});
		NextButton.setBounds(335, 10, 89, 23);
		contentPane.add(NextButton);
		
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
				NewShoppingPersonFrame.this.dispose();
			}
		});
		contentPane.add(btnMgse);
	}
	public JFrame getParent(){
		return parent;
	}
}
