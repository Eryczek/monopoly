package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.Player;
import main.Utils;
import tiles.StreetTile;
import tiles.TileGame;

@SuppressWarnings("serial")
public class BuySellFrame extends JFrame {

	private JPanel contentPane;
	private Player player, otherPlayer;

	private JComboBox<String> cmbPlayers;
	private JLabel lbPlayerMoney, lbOtherPlayerMoney, lbPlayerMoneyAdded, lbOtherPlayerMoneyAdded;
	private JLabel[] colorLabels;
	private ArrayList<JLabel> playerPropertyColorLabels, otherPlayerPropertyColorLabels, playerSellPropertyColorLabels,
			otherPlayerBuyPropertyColorLabels;
	private JButton btnSalir, btnPlayerAddToSell, btnPlayerDeleteAll, btnPlayerDelete, btnPlayerAddCard,
			btnPlayerAddMoney, btnOtherPlayerAddToBuy, btnOtherPlayerDeleteAll, btnOtherPlayerDelete,
			btnOtherPlayerAddCard, btnOtherPlayerAddMoney, btnOffer;
	private JList<String> playerFreeJailList, playerPropertiesList, playerSellingList, otherPlayerFreeJailList,
			otherPlayerPropertiesList, otherPlayerSellingList;
	private JPanel playerPanel, otherPlayerPanel;
	private JSpinner spPlayerAddMoney, spOtherPlayerAddMoney;

	public BuySellFrame() {
		setColorLabels();
	}

	public void setBuySell(ArrayList<Player> players, int playerTurn) {
		this.player = players.get(playerTurn);

		setBuySellPanel();
		uploadPlayerFrame();

		cmbPlayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				for (Player oPlayer : player.getMainGameFrame().getPlayers()) {

					if (oPlayer.getName().equals(cmbPlayers.getSelectedItem())) {
						otherPlayer = oPlayer;
						lbOtherPlayerMoney.setText("Money : £" + otherPlayer.getMoney());

						uploadOtherPlayerFrame();
					}

				}
			}
		});

		btnOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				StringBuilder sb = new StringBuilder();
				sb.append(player.getName() + " quiere cambiar:");

				if (Utils.parseInt(lbPlayerMoneyAdded.getText().substring(25)) > 0)
					sb.append("\n- £" + lbPlayerMoneyAdded.getText().substring(25));

				if (playerSellingList.getModel().getSize() > 0)
					for (int i = 0; i < playerSellingList.getModel().getSize(); i++)
						sb.append("\n- " + playerSellingList.getModel().getElementAt(i));

				if (sb.length() > (player.getName().length() + 16) && (otherPlayerSellingList.getModel().getSize() > 0
						|| Utils.parseInt(lbOtherPlayerMoneyAdded.getText().substring(25)) > 0)) {

					sb.append("\npor :");

					if (Utils.parseInt(lbOtherPlayerMoneyAdded.getText().substring(25)) > 0)
						sb.append("\n- £" + lbOtherPlayerMoneyAdded.getText().substring(25));

					if (otherPlayerSellingList.getModel().getSize() > 0)
						for (int i = 0; i < otherPlayerSellingList.getModel().getSize(); i++)
							sb.append("\n- " + otherPlayerSellingList.getModel().getElementAt(i));

					sb.append("\n" + otherPlayer.getName() + " deseas hacer los cambios?");

					switch (JOptionPane.showConfirmDialog(null, sb)) {
					case 0:
						player.setMoney(
								player.getMoney() + Utils.parseInt(lbOtherPlayerMoneyAdded.getText().substring(25)));
						otherPlayer.setMoney(otherPlayer.getMoney()
								- Utils.parseInt(lbOtherPlayerMoneyAdded.getText().substring(25)));

						otherPlayer.setMoney(
								otherPlayer.getMoney() + Utils.parseInt(lbPlayerMoneyAdded.getText().substring(25)));
						player.setMoney(player.getMoney() - Utils.parseInt(lbPlayerMoneyAdded.getText().substring(25)));

						for (int i = 0; i < playerSellingList.getModel().getSize(); i++) {
							if (playerSellingList.getModel().getElementAt(i).toString().equals("Chance Jail Free")) {
								player.setChFreeJail(false);
								otherPlayer.setChFreeJail(true);
							} else if (playerSellingList.getModel().getElementAt(i).toString()
									.equals("Comunity Jail Free")) {
								player.setCoFreeJail(false);
								otherPlayer.setCoFreeJail(true);
							} else {
								for (int j = 0; j < 10; j++) {
									for (int k = 0; k < player.getProperties()[j].size(); k++) {

										if (player.getProperties()[j].get(k).getName()
												.equals(playerSellingList.getModel().getElementAt(i))) {

											player.getProperties()[j].get(k).setOwner(otherPlayer);

											switch (j) {

											case 0:
												otherPlayer.getRailRoads().add(player.getProperties()[j].get(k));
												player.getRailRoads().remove(player.getProperties()[j].get(k));
												break;

											case 1:
												otherPlayer.getCompanies().add(player.getProperties()[j].get(k));
												player.getCompanies().remove(player.getProperties()[j].get(k));
												break;

											case 2:
												otherPlayer.getBrownStreet()
														.add((StreetTile) player.getProperties()[j].get(k));
												player.getBrownStreet()
														.remove((StreetTile) player.getProperties()[j].get(k));
												break;

											case 3:
												otherPlayer.getLightBlueStreet()
														.add((StreetTile) player.getProperties()[j].get(k));
												player.getLightBlueStreet()
														.remove((StreetTile) player.getProperties()[j].get(k));
												break;

											case 4:
												otherPlayer.getPurpleStreet()
														.add((StreetTile) player.getProperties()[j].get(k));
												player.getPurpleStreet()
														.remove((StreetTile) player.getProperties()[j].get(k));
												break;

											case 5:
												otherPlayer.getOrangeStreet()
														.add((StreetTile) player.getProperties()[j].get(k));
												player.getOrangeStreet()
														.remove((StreetTile) player.getProperties()[j].get(k));
												break;

											case 6:
												otherPlayer.getRedStreet()
														.add((StreetTile) player.getProperties()[j].get(k));
												player.getRedStreet()
														.remove((StreetTile) player.getProperties()[j].get(k));
												break;

											case 7:
												otherPlayer.getYellowStreet()
														.add((StreetTile) player.getProperties()[j].get(k));
												player.getYellowStreet()
														.remove((StreetTile) player.getProperties()[j].get(k));
												break;

											case 8:
												otherPlayer.getGreenStreet()
														.add((StreetTile) player.getProperties()[j].get(k));
												player.getGreenStreet()
														.remove((StreetTile) player.getProperties()[j].get(k));
												break;

											case 9:
												otherPlayer.getDarkBlueStreet()
														.add((StreetTile) player.getProperties()[j].get(k));
												player.getDarkBlueStreet()
														.remove((StreetTile) player.getProperties()[j].get(k));
												break;
											default:
												break;
											}

										}
									}
								}
							}

						}

						for (int i = 0; i < otherPlayerSellingList.getModel().getSize(); i++) {
							if (otherPlayerSellingList.getModel().getElementAt(i).toString()
									.equals("Chance Jail Free")) {
								otherPlayer.setChFreeJail(false);
								player.setChFreeJail(true);
							} else if (otherPlayerSellingList.getModel().getElementAt(i).toString()
									.equals("Comunity Jail Free")) {
								otherPlayer.setCoFreeJail(false);
								player.setCoFreeJail(true);
							} else {
								for (int j = 0; j < 10; j++) {
									for (int k = 0; k < otherPlayer.getProperties()[j].size(); k++) {
										if (otherPlayer.getProperties()[j].get(k).getName()
												.equals(otherPlayerSellingList.getModel().getElementAt(i))) {

											otherPlayer.getProperties()[j].get(k).setOwner(player);

											switch (j) {

											case 0:
												player.getRailRoads().add(otherPlayer.getProperties()[j].get(k));
												otherPlayer.getRailRoads()
														.remove(otherPlayer.getProperties()[j].get(k));
												break;

											case 1:
												player.getCompanies().add(otherPlayer.getProperties()[j].get(k));
												otherPlayer.getCompanies()
														.remove(otherPlayer.getProperties()[j].get(k));
												break;

											case 2:
												player.getBrownStreet()
														.add((StreetTile) otherPlayer.getProperties()[j].get(k));
												otherPlayer.getBrownStreet()
														.remove(otherPlayer.getProperties()[j].get(k));
												break;

											case 3:
												player.getLightBlueStreet()
														.add((StreetTile) otherPlayer.getProperties()[j].get(k));
												otherPlayer.getLightBlueStreet()
														.remove(otherPlayer.getProperties()[j].get(k));
												break;

											case 4:
												player.getPurpleStreet()
														.add((StreetTile) otherPlayer.getProperties()[j].get(k));
												otherPlayer.getPurpleStreet()
														.remove(otherPlayer.getProperties()[j].get(k));
												break;

											case 5:
												player.getOrangeStreet()
														.add((StreetTile) otherPlayer.getProperties()[j].get(k));
												otherPlayer.getOrangeStreet()
														.remove(otherPlayer.getProperties()[j].get(k));
												break;

											case 6:
												player.getRedStreet()
														.add((StreetTile) otherPlayer.getProperties()[j].get(k));
												otherPlayer.getRedStreet()
														.remove(otherPlayer.getProperties()[j].get(k));
												break;

											case 7:
												player.getYellowStreet()
														.add((StreetTile) otherPlayer.getProperties()[j].get(k));
												otherPlayer.getYellowStreet()
														.remove(otherPlayer.getProperties()[j].get(k));
												break;

											case 8:
												player.getGreenStreet()
														.add((StreetTile) otherPlayer.getProperties()[j].get(k));
												otherPlayer.getGreenStreet()
														.remove(otherPlayer.getProperties()[j].get(k));
												break;

											case 9:
												player.getDarkBlueStreet()
														.add((StreetTile) otherPlayer.getProperties()[j].get(k));
												otherPlayer.getDarkBlueStreet()
														.remove(otherPlayer.getProperties()[j].get(k));
												break;
											default:
												break;
											}

										}
									}
								}
							}

						}

						player.getMainGameFrame().uploadPlayerFrame();
						player.getMainGameFrame().getBtnTerminar().setVisible(true);
						player.getMainGameFrame().getBtnOfrecer().setVisible(true);
						player.getMainGameFrame().getLbMoney().setText("Money : £ " + player.getMoney());
						setVisible(false);
						dispose();
						break;

					case 1:
						players.get(playerTurn).getMainGameFrame().uploadPlayerFrame();
						players.get(playerTurn).getMainGameFrame().getBtnTerminar().setVisible(true);
						players.get(playerTurn).getMainGameFrame().getBtnOfrecer().setVisible(true);
						setVisible(false);
						dispose();
						break;

					default:
						break;
					}
				}

			}
		});

		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				players.get(playerTurn).getMainGameFrame().uploadPlayerFrame();
				players.get(playerTurn).getMainGameFrame().getBtnTerminar().setVisible(true);
				players.get(playerTurn).getMainGameFrame().getBtnOfrecer().setVisible(true);
				players.get(playerTurn).getMainGameFrame().getBtnDeshipotecar().setVisible(true);
				players.get(playerTurn).getMainGameFrame().getBtnHipotecar().setVisible(true);
				players.get(playerTurn).getMainGameFrame().getBtnAddHouse().setVisible(true);
				setVisible(false);
				dispose();
			}
		});

		btnPlayerAddCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<String> property = new DefaultListModel<String>();
				boolean isCardInTheList = false;

				if (playerSellingList.getModel().getSize() > 0) {
					for (int i = 0; i < playerSellingList.getModel().getSize(); i++) {
						if (!playerSellingList.getModel().getElementAt(i)
								.contains(playerFreeJailList.getSelectedValue())) {
							property.addElement(playerSellingList.getModel().getElementAt(i));
						} else {
							isCardInTheList = true;
						}
					}
				}

				if (!isCardInTheList && (playerFreeJailList.getSelectedIndex() != -1)) {
					property.addElement(playerFreeJailList.getSelectedValue());
					playerSellingList.setBounds(280, 200, 174, playerSellingList.getHeight() + 17);

					if (playerSellPropertyColorLabels.size() > playerSellingList.getModel().getSize()) {
						playerSellPropertyColorLabels.get(playerSellingList.getModel().getSize()).setVisible(false);
					} else {
						JLabel label = new JLabel();
						label.setBounds(254, 203 + (17 * playerSellPropertyColorLabels.size()), 25, 17);
						label.setVisible(false);
						playerPanel.add(label);
						playerSellPropertyColorLabels.add(label);
					}

					playerSellingList.setModel(property);
				}

			}
		});

		btnPlayerDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (playerSellingList.getSelectedIndex() != -1) {
					DefaultListModel<String> sellingListModel = (DefaultListModel<String>) playerSellingList.getModel();

					for (int i = playerSellingList.getSelectedIndex(); i < playerSellingList.getModel()
							.getSize(); i++) {
						if (i < playerSellingList.getModel().getSize() - 1) {
							playerSellPropertyColorLabels.get(i)
									.setIcon(playerSellPropertyColorLabels.get(i + 1).getIcon());
							playerSellPropertyColorLabels.get(i).setVisible(true);

							if (playerSellingList.getModel().getElementAt(i + 1).toString().equals("Chance Jail Free")
									|| playerSellingList.getModel().getElementAt(i + 1).toString()
											.equals("Comunity Jail Free")) {
								playerSellPropertyColorLabels.get(i).setVisible(false);
							}
						} else {
							playerSellPropertyColorLabels.get(i).setVisible(false);
						}
					}

					sellingListModel.remove(playerSellingList.getSelectedIndex());
					playerSellingList.setModel(sellingListModel);
					playerSellingList.setBounds(280, 200, 174, playerSellingList.getHeight() - 17);

				}

			}
		});

		btnPlayerAddMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((int) spPlayerAddMoney.getValue() <= player.getMoney() && (int) spPlayerAddMoney.getValue() >= 0) {
					lbPlayerMoneyAdded.setText("Money to buy properties: " + spPlayerAddMoney.getValue());
				} else {
					if ((int) spPlayerAddMoney.getValue() > player.getMoney())
						JOptionPane.showMessageDialog(null, "You have insuficient founds!");
					if ((int) spPlayerAddMoney.getValue() < 0)
						JOptionPane.showMessageDialog(null, "Can not be below 0!");
				}
			}
		});

		btnPlayerDeleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				playerSellingList.setModel(new DefaultListModel<String>());
				playerSellingList.setBounds(280, 200, 174, 6);

				for (JLabel label : playerSellPropertyColorLabels)
					label.setVisible(false);
			}
		});

		btnPlayerAddToSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<String> property = new DefaultListModel<String>();
				boolean isPropertyInTheList = false;

				if (playerSellingList.getModel() != null) {
					for (int i = 0; i < playerSellingList.getModel().getSize(); i++) {
						if (!playerSellingList.getModel().getElementAt(i)
								.contains(playerPropertiesList.getSelectedValue())) {
							property.addElement(playerSellingList.getModel().getElementAt(i));
						} else {
							isPropertyInTheList = true;
						}
					}
				}

				if (!isPropertyInTheList && (playerPropertiesList.getSelectedIndex() != -1)) {

					if (playerSellPropertyColorLabels.size() > playerSellingList.getModel().getSize()) {
						playerSellPropertyColorLabels.get(playerSellingList.getModel().getSize()).setIcon(
								playerPropertyColorLabels.get(playerPropertiesList.getSelectedIndex()).getIcon());
						playerSellPropertyColorLabels.get(playerSellingList.getModel().getSize()).setVisible(true);
					} else {
						JLabel label = new JLabel();
						label.setBounds(254, 203 + (17 * playerSellPropertyColorLabels.size()), 25, 17);
						label.setIcon(playerPropertyColorLabels.get(playerPropertiesList.getSelectedIndex()).getIcon());
						label.setVisible(true);
						playerPanel.add(label);
						playerSellPropertyColorLabels.add(label);
					}

					property.addElement(playerPropertiesList.getSelectedValue());

					playerSellingList.setBounds(280, 200, 174, playerSellingList.getHeight() + 17);

					playerSellingList.setModel(property);
				}

			}
		});

		btnOtherPlayerAddCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<String> property = new DefaultListModel<String>();
				boolean isCardInTheList = false;

				if (otherPlayerSellingList.getModel().getSize() != 0) {
					for (int i = 0; i < otherPlayerSellingList.getModel().getSize(); i++) {
						if (!otherPlayerSellingList.getModel().getElementAt(i)
								.contains(otherPlayerFreeJailList.getSelectedValue())) {
							property.addElement(otherPlayerSellingList.getModel().getElementAt(i));
						} else {
							isCardInTheList = true;
						}
					}
				}

				if (!isCardInTheList && (otherPlayerFreeJailList.getSelectedIndex() != -1)) {
					property.addElement(otherPlayerFreeJailList.getSelectedValue());
					otherPlayerSellingList.setBounds(280, 200, 174, otherPlayerSellingList.getHeight() + 17);

					if (otherPlayerBuyPropertyColorLabels.size() > otherPlayerSellingList.getModel().getSize()) {
						otherPlayerBuyPropertyColorLabels.get(otherPlayerSellingList.getModel().getSize())
								.setVisible(false);
					} else {
						JLabel label = new JLabel();
						label.setBounds(254, 203 + (17 * otherPlayerBuyPropertyColorLabels.size()), 25, 17);
						label.setVisible(false);
						otherPlayerPanel.add(label);
						otherPlayerBuyPropertyColorLabels.add(label);
					}

					otherPlayerSellingList.setModel(property);
				}

			}
		});

		btnOtherPlayerDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (otherPlayerSellingList.getSelectedIndex() != -1) {
					DefaultListModel<String> sellingListModel = (DefaultListModel<String>) otherPlayerSellingList
							.getModel();

					for (int i = otherPlayerSellingList.getSelectedIndex(); i < otherPlayerSellingList.getModel()
							.getSize(); i++) {
						if (i < otherPlayerSellingList.getModel().getSize() - 1) {
							otherPlayerBuyPropertyColorLabels.get(i)
									.setIcon(otherPlayerBuyPropertyColorLabels.get(i + 1).getIcon());
							otherPlayerBuyPropertyColorLabels.get(i).setVisible(true);

							if (otherPlayerSellingList.getModel().getElementAt(i + 1).toString()
									.equals("Chance Jail Free")
									|| otherPlayerSellingList.getModel().getElementAt(i + 1).toString()
											.equals("Comunity Jail Free")) {
								otherPlayerBuyPropertyColorLabels.get(i).setVisible(false);
							}
						} else {
							otherPlayerBuyPropertyColorLabels.get(i).setVisible(false);
						}
					}

					sellingListModel.remove(otherPlayerSellingList.getSelectedIndex());
					otherPlayerSellingList.setModel(sellingListModel);
					otherPlayerSellingList.setBounds(280, 200, 174, otherPlayerSellingList.getHeight() - 17);

				}

			}
		});

		btnOtherPlayerAddMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((int) spOtherPlayerAddMoney.getValue() <= otherPlayer.getMoney()
						&& (int) spOtherPlayerAddMoney.getValue() >= 0) {
					lbOtherPlayerMoneyAdded.setText("Money to buy properties: " + spOtherPlayerAddMoney.getValue());
				} else {

					if ((int) spPlayerAddMoney.getValue() > player.getMoney())
						JOptionPane.showMessageDialog(null, otherPlayer.getName() + " has insuficient founds!");
					if ((int) spPlayerAddMoney.getValue() < 0)
						JOptionPane.showMessageDialog(null, "Can not be below 0!");
				}
			}
		});

		btnOtherPlayerDeleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				otherPlayerSellingList.setModel(new DefaultListModel<String>());
				otherPlayerSellingList.setBounds(280, 200, 174, 6);
				for (JLabel label : otherPlayerBuyPropertyColorLabels)
					label.setVisible(false);

			}
		});

		btnOtherPlayerAddToBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<String> property = new DefaultListModel<String>();
				boolean isPropertyInTheList = false;

				if (otherPlayerSellingList.getModel() != null) {
					for (int i = 0; i < otherPlayerSellingList.getModel().getSize(); i++) {
						if (!otherPlayerSellingList.getModel().getElementAt(i)
								.contains(otherPlayerPropertiesList.getSelectedValue())) {
							property.addElement(otherPlayerSellingList.getModel().getElementAt(i));
						} else {
							isPropertyInTheList = true;
						}
					}
				}

				if (!isPropertyInTheList && (otherPlayerPropertiesList.getSelectedIndex() != -1)) {
					if (otherPlayerBuyPropertyColorLabels.size() > otherPlayerSellingList.getModel().getSize()) {
						otherPlayerBuyPropertyColorLabels.get(otherPlayerSellingList.getModel().getSize())
								.setIcon(otherPlayerPropertyColorLabels
										.get(otherPlayerPropertiesList.getSelectedIndex()).getIcon());
						otherPlayerBuyPropertyColorLabels.get(otherPlayerSellingList.getModel().getSize())
								.setVisible(true);
					} else {
						JLabel label = new JLabel();
						label.setBounds(254, 203 + (17 * otherPlayerBuyPropertyColorLabels.size()), 25, 17);
						label.setIcon(otherPlayerPropertyColorLabels.get(otherPlayerPropertiesList.getSelectedIndex())
								.getIcon());
						label.setVisible(true);
						otherPlayerPanel.add(label);
						otherPlayerBuyPropertyColorLabels.add(label);
					}

					property.addElement(otherPlayerPropertiesList.getSelectedValue());
					otherPlayerSellingList.setBounds(280, 200, 174, otherPlayerSellingList.getHeight() + 17);

					otherPlayerSellingList.setModel(property);
				}

			}
		});

	}

	private void setBuySellPanel() {

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1024, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setPlayerPanel();
		setOtherPlayerPanel();

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(895, 15, 117, 25);
		contentPane.add(btnSalir);

		JLabel lbPlayer = new JLabel(player.getName());
		lbPlayer.setFont(new Font("Century Schoolbook L", Font.BOLD | Font.ITALIC, 54));
		lbPlayer.setBounds(173, 20, 260, 60);
		contentPane.add(lbPlayer);

		int index = 0;
		String[] playersComboBox = new String[player.getMainGameFrame().getPlayers().size() - 1];
		for (Player p : player.getMainGameFrame().getPlayers()) {
			if (p.getNumPlayer() != player.getNumPlayer()) {
				playersComboBox[index] = p.getName();
				index++;
			}
		}

		cmbPlayers = new JComboBox<String>();
		cmbPlayers.setModel(new DefaultComboBoxModel<String>(playersComboBox));
		cmbPlayers.setBounds(857, 62, 143, 24);
		contentPane.add(cmbPlayers);

		btnOffer = new JButton("Offer");
		btnOffer.setBounds(521, 20, 70, 25);
		contentPane.add(btnOffer);

		lbOtherPlayerMoney = new JLabel("Money : £0");
		lbOtherPlayerMoney.setBounds(520, 79, 124, 15);
		contentPane.add(lbOtherPlayerMoney);

		lbPlayerMoney = new JLabel("Money : £ " + player.getMoney());
		lbPlayerMoney.setBounds(27, 79, 167, 15);
		contentPane.add(lbPlayerMoney);

	}

	private void setPlayerPanel() {
		playerSellPropertyColorLabels = new ArrayList<JLabel>();

		playerPanel = new JPanel();
		playerPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerPanel.setPreferredSize(new Dimension(460, 600));
		playerPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(playerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(27, 95, 480, 500);
		contentPane.add(scrollPane);

		playerPropertiesList = new JList<String>();
		playerPropertiesList.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		playerPropertiesList.setBounds(29, 115, 174, 6);
		playerPropertiesList.setVisible(false);
		playerPanel.add(playerPropertiesList);

		playerFreeJailList = new JList<String>();
		playerFreeJailList.setBackground(UIManager.getColor("Panel.background"));
		playerFreeJailList.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Free Jail Cards",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		playerFreeJailList.setBounds(280, 19, 174, 56);
		playerFreeJailList.setVisible(false);
		playerPanel.add(playerFreeJailList);

		playerSellingList = new JList<String>();
		playerSellingList.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		playerSellingList.setBounds(280, 200, 174, 6);
		playerPanel.add(playerSellingList);

		btnPlayerDelete = new JButton("Delete");
		btnPlayerDelete.setBounds(280, 120, 117, 25);
		playerPanel.add(btnPlayerDelete);

		btnPlayerDeleteAll = new JButton("Delete All");
		btnPlayerDeleteAll.setBounds(280, 85, 117, 25);
		playerPanel.add(btnPlayerDeleteAll);

		JLabel lbPlayerOwned = new JLabel("Properties owned");
		lbPlayerOwned.setBounds(39, 97, 140, 15);
		playerPanel.add(lbPlayerOwned);

		JLabel lbPlayerExchange = new JLabel("Properties to exchange");
		lbPlayerExchange.setBounds(280, 175, 174, 25);
		playerPanel.add(lbPlayerExchange);

		btnPlayerAddToSell = new JButton("Add Property");
		btnPlayerAddToSell.setBounds(29, 68, 140, 25);
		playerPanel.add(btnPlayerAddToSell);

		spPlayerAddMoney = new JSpinner();
		spPlayerAddMoney.setBounds(140, 7, 88, 20);
		playerPanel.add(spPlayerAddMoney);

		JLabel lbPlayerMoneyToAdd = new JLabel("Money to add:");
		lbPlayerMoneyToAdd.setBounds(29, 9, 120, 15);
		playerPanel.add(lbPlayerMoneyToAdd);

		btnPlayerAddMoney = new JButton("Add Money");
		btnPlayerAddMoney.setBounds(29, 34, 117, 25);
		playerPanel.add(btnPlayerAddMoney);

		btnPlayerAddCard = new JButton("Add Card");
		btnPlayerAddCard.setBounds(180, 47, 98, 25);
		playerPanel.add(btnPlayerAddCard);

		lbPlayerMoneyAdded = new JLabel("Money to buy properties: 0");
		lbPlayerMoneyAdded.setBounds(215, 155, 250, 15);
		playerPanel.add(lbPlayerMoneyAdded);

	}

	private void setOtherPlayerPanel() {

		otherPlayerPropertyColorLabels = new ArrayList<JLabel>();
		otherPlayerBuyPropertyColorLabels = new ArrayList<JLabel>();

		otherPlayerPanel = new JPanel();
		otherPlayerPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		otherPlayerPanel.setPreferredSize(new Dimension(460, 600));
		otherPlayerPanel.setLayout(null);
		otherPlayerPanel.setVisible(false);

		JScrollPane scrollOtherPlayer = new JScrollPane(otherPlayerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollOtherPlayer.setBounds(520, 95, 480, 500);
		contentPane.add(scrollOtherPlayer);

		otherPlayerPropertiesList = new JList<String>();
		otherPlayerPropertiesList.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		otherPlayerPropertiesList.setBounds(29, 115, 174, 6);
		otherPlayerPropertiesList.setVisible(false);
		otherPlayerPanel.add(otherPlayerPropertiesList);

		otherPlayerFreeJailList = new JList<String>();
		otherPlayerFreeJailList.setBackground(UIManager.getColor("Panel.background"));
		otherPlayerFreeJailList.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Free Jail Cards",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		otherPlayerFreeJailList.setBounds(280, 19, 174, 56);
		otherPlayerFreeJailList.setVisible(false);
		otherPlayerPanel.add(otherPlayerFreeJailList);

		otherPlayerSellingList = new JList<String>();
		otherPlayerSellingList.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		otherPlayerSellingList.setBounds(280, 200, 174, 6);
		otherPlayerPanel.add(otherPlayerSellingList);

		btnOtherPlayerDelete = new JButton("Delete");
		btnOtherPlayerDelete.setBounds(280, 120, 117, 25);
		otherPlayerPanel.add(btnOtherPlayerDelete);

		btnOtherPlayerDeleteAll = new JButton("Delete All");
		btnOtherPlayerDeleteAll.setBounds(280, 85, 117, 25);
		otherPlayerPanel.add(btnOtherPlayerDeleteAll);

		JLabel lbOtherPlayerOwned = new JLabel("Properties owned");
		lbOtherPlayerOwned.setBounds(39, 97, 140, 15);
		otherPlayerPanel.add(lbOtherPlayerOwned);

		JLabel lbOtherPlayerExchange = new JLabel("Properties to exchange");
		lbOtherPlayerExchange.setBounds(280, 175, 174, 25);
		otherPlayerPanel.add(lbOtherPlayerExchange);

		btnOtherPlayerAddToBuy = new JButton("Add Property");
		btnOtherPlayerAddToBuy.setBounds(29, 68, 140, 25);
		otherPlayerPanel.add(btnOtherPlayerAddToBuy);

		spOtherPlayerAddMoney = new JSpinner();
		spOtherPlayerAddMoney.setBounds(140, 7, 88, 20);
		otherPlayerPanel.add(spOtherPlayerAddMoney);

		JLabel lbOtherPlayerMoneyToAdd = new JLabel("Money to add:");
		lbOtherPlayerMoneyToAdd.setBounds(29, 9, 120, 15);
		otherPlayerPanel.add(lbOtherPlayerMoneyToAdd);

		btnOtherPlayerAddMoney = new JButton("Add Money");
		btnOtherPlayerAddMoney.setBounds(29, 34, 117, 25);
		otherPlayerPanel.add(btnOtherPlayerAddMoney);

		btnOtherPlayerAddCard = new JButton("Add Card");
		btnOtherPlayerAddCard.setBounds(180, 47, 98, 25);
		otherPlayerPanel.add(btnOtherPlayerAddCard);

		lbOtherPlayerMoneyAdded = new JLabel("Money to buy properties: 0");
		lbOtherPlayerMoneyAdded.setBounds(215, 155, 250, 15);
		otherPlayerPanel.add(lbOtherPlayerMoneyAdded);

	}

	private void setColorLabels() {

		String[] colors = { "/blackColor.png", "/greyColor.png", "/brownColor.png", "/lightBlueColor.png",
				"/purpleColor.png", "/orangeColor.png", "/redColor.png", "/yellowColor.png", "/greenColor.png",
				"/darkBlueColor.png" };
		colorLabels = new JLabel[10];

		for (int i = 0; i < 10; i++) {
			colorLabels[i] = new JLabel();
			Image image = new ImageIcon(this.getClass().getResource(colors[i])).getImage();
			colorLabels[i].setIcon(new ImageIcon(image));
			colorLabels[i].setBounds(0, 118, 25, 17);
		}
	}

	private void uploadPlayerFrame() {

		boolean empty = true;
		int numProperties = 0;
		playerPropertyColorLabels = new ArrayList<JLabel>();

		DefaultListModel<String> propertiesListModel = new DefaultListModel<>();

		for (int i = 0; i < 10; i++) {
			if (player.getProperties()[i].size() > 0) {
				for (TileGame property : player.getProperties()[i]) {
					if (property.getHouses() == 0) {
						propertiesListModel.addElement(property.getName());

						JLabel label = new JLabel();
						label.setBounds(3, colorLabels[i].getY() + (17 * numProperties), 25, 17);
						label.setIcon(colorLabels[i].getIcon());
						label.setVisible(true);
						playerPanel.add(label);
						playerPropertyColorLabels.add(label);
						numProperties++;
						empty = false;
					}
				}

			}
		}

		if (empty) {
			playerPropertiesList.setVisible(false);
		} else {
			playerPropertiesList.setModel(propertiesListModel);
			playerPropertiesList.setBounds(29, 115, 174, 6 + (17 * numProperties));
			playerPropertiesList.setVisible(true);
		}

		DefaultListModel<String> jailListModel = new DefaultListModel<>();

		if (player.getChFreeJail() || player.getCoFreeJail()) {

			if (player.getChFreeJail())
				jailListModel.addElement("Chance Jail Free");

			if (player.getCoFreeJail())
				jailListModel.addElement("Comunity Jail Free");

			playerFreeJailList.setModel(jailListModel);
			playerFreeJailList.setVisible(true);
		} else {
			playerFreeJailList.setVisible(false);
		}

	}

	private void uploadOtherPlayerFrame() {

		boolean empty = true;
		int numProperties = 0;

		otherPlayerSellingList.setModel(new DefaultListModel<String>());
		otherPlayerSellingList.setBounds(280, 200, 174, 6);

		if (otherPlayerPropertyColorLabels.size() > 0) {
			for (int i = 0; i < otherPlayerPropertyColorLabels.size(); i++) {
				otherPlayerPropertyColorLabels.get(i).setVisible(false);
			}
		}

		DefaultListModel<String> propertiesListModel = new DefaultListModel<>();

		for (int i = 0; i < 10; i++) {
			if (otherPlayer.getProperties()[i].size() > 0) {
				for (TileGame property : otherPlayer.getProperties()[i]) {

					propertiesListModel.addElement(property.getName());

					if (otherPlayerPropertyColorLabels.size() > numProperties) {
						otherPlayerPropertyColorLabels.get(numProperties).setVisible(true);
						otherPlayerPropertyColorLabels.get(numProperties).setIcon(colorLabels[i].getIcon());
					} else {
						JLabel label = new JLabel();
						label.setBounds(3, 118 + (17 * numProperties), 25, 17);
						label.setIcon(colorLabels[i].getIcon());
						label.setVisible(true);
						otherPlayerPanel.add(label);
						otherPlayerPropertyColorLabels.add(label);
					}

					numProperties++;
				}
				empty = false;
			}
		}

		if (empty) {
			otherPlayerPropertiesList.setVisible(false);
		} else {
			otherPlayerPropertiesList.setModel(propertiesListModel);
			otherPlayerPropertiesList.setBounds(29, 115, 174, 6 + (17 * numProperties));
			otherPlayerPropertiesList.setVisible(true);
		}

		DefaultListModel<String> jailListModel = new DefaultListModel<>();

		if (otherPlayer.getChFreeJail() || otherPlayer.getCoFreeJail()) {

			if (otherPlayer.getChFreeJail())
				jailListModel.addElement("Chance Jail Free");

			if (otherPlayer.getCoFreeJail())
				jailListModel.addElement("Comunity Jail Free");

			otherPlayerFreeJailList.setModel(jailListModel);
			otherPlayerFreeJailList.setVisible(true);
		} else {
			otherPlayerFreeJailList.setVisible(false);
		}

		otherPlayerPanel.setVisible(true);

	}
}
