package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Utils;

@SuppressWarnings("serial")
public class SetPlayersFrame extends JFrame {

	private JPanel contentPane;

	private String[] namePlayers;
	private JTextField txtPlayer1, txtPlayer2, txtPlayer3, txtPlayer4;

	public SetPlayersFrame(String numPlayers) {

		namePlayers = new String[Utils.parseInt(numPlayers)];

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblName1 = new JLabel("Name player1: ");
		lblName1.setBounds(71, 50, 115, 21);
		contentPane.add(lblName1);

		txtPlayer1 = new JTextField();
		txtPlayer1.setBounds(188, 50, 149, 21);
		contentPane.add(txtPlayer1);
		txtPlayer1.setColumns(10);

		JLabel lblName2 = new JLabel("Name player2: ");
		lblName2.setBounds(71, 76, 115, 21);
		contentPane.add(lblName2);

		txtPlayer2 = new JTextField(10);
		txtPlayer2.setBounds(188, 76, 149, 21);
		contentPane.add(txtPlayer2);
		txtPlayer2.setColumns(10);

		JButton btnStartGame = new JButton("Start game");
		btnStartGame.setBounds(218, 170, 117, 25);
		contentPane.add(btnStartGame);

		if (numPlayers.equals("3") || numPlayers.equals("4")) {

			JLabel lblName3 = new JLabel("Name player3: ");
			lblName3.setBounds(71, 102, 115, 21);
			contentPane.add(lblName3);

			txtPlayer3 = new JTextField();
			txtPlayer3.setBounds(188, 102, 149, 21);
			contentPane.add(txtPlayer3);
			txtPlayer3.setColumns(10);
		}

		if (numPlayers.equals("4")) {

			JLabel lblName4 = new JLabel("Name player4: ");
			lblName4.setBounds(71, 128, 115, 21);
			contentPane.add(lblName4);

			txtPlayer4 = new JTextField();
			txtPlayer4.setBounds(188, 128, 149, 21);
			contentPane.add(txtPlayer4);
			txtPlayer4.setColumns(10);
		}

		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String player1 = txtPlayer1.getText();
				namePlayers[0] = player1;

				String player2 = txtPlayer2.getText();
				namePlayers[1] = player2;

				if (numPlayers.equals("3") || numPlayers.equals("4")) {
					String player3 = txtPlayer3.getText();
					namePlayers[2] = player3;
				}

				if (numPlayers.equals("4")) {
					String player4 = txtPlayer4.getText();
					namePlayers[3] = player4;
				}

				MainGameFrame mainGameFrame = new MainGameFrame(namePlayers);
				mainGameFrame.setVisible(true);
				dispose();
			}
		});

	}
}
