package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class StartMenuFrame extends JFrame {

	private JPanel contentPane;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public StartMenuFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnComenzar = new JButton("Comenzar");
		btnComenzar.setBounds(205, 188, 117, 25);
		contentPane.add(btnComenzar);

		JLabel lblNumeroDeJugarores = new JLabel("Numero de Jugarores: ");
		lblNumeroDeJugarores.setBounds(29, 137, 164, 15);
		contentPane.add(lblNumeroDeJugarores);

		JComboBox cmbBox = new JComboBox();
		cmbBox.setModel(new DefaultComboBoxModel(new String[] { "2", "3", "4" }));
		cmbBox.setSelectedIndex(0);
		cmbBox.setBounds(197, 132, 125, 24);
		contentPane.add(cmbBox);

		btnComenzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final SetPlayersFrame setP = new SetPlayersFrame((String) cmbBox.getSelectedItem());
				setP.setVisible(true);
				dispose();

			}

		});
	}
}
