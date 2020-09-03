package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.Player;
import tiles.StreetTile;
import tiles.TileGame;

class MenosButton {
	private JButton button;

	public MenosButton(JButton button, AddHouseFrame frame, int index) {
		this.button = button;

		this.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				frame.setHousesOnFrame(sortLabelList(frame.getHousesOnFrame()));
				frame.setHotelsOnFrame(sortLabelList(frame.getHotelsOnFrame()));

				ArrayList<JLabel> houses = new ArrayList<JLabel>();

				for (JLabel label : frame.getHousesOnFrame())
					if (button.getY() == label.getY())
						houses.add(label);

				for (JLabel label : frame.getHotelsOnFrame()) {
					if (button.getY() == label.getY()) {
						if (frame.getNumHousesLeft() < 4) {
							// hay que preguntar si quiere recibir dinero por
							// las casas que no pueda colocar
							// al quitar el hotel

						} else {
							label.setBounds(190 + (26 * (frame.getNumHotelsIndex() + 1)), 48, 17, 17);

							if (frame.getNumHotelsLeft() != (frame.getNumHotelsIndex() + 1)) {
								label.setVisible(false);
								frame.getHotelsOnFrame().get(frame.getNumHotelsLeft()).setVisible(true);
							}
							frame.setNumHotelsLeft(frame.getNumHotelsLeft() + 1);
							frame.setNumHotelsIndex(frame.getNumHotelsIndex() + 1);

							frame.getListBtnMas().get(index).getButton().setVisible(true);

							for (int i = 0; i < 4; i++) {

								JLabel addingLabel = frame.getHousesOnFrame().get(frame.getNumHousesIndex());
								addingLabel.setBounds(210 + (15 * i), button.getY(), 11, 17);
								if (frame.getNumHousesLeft() != (frame.getNumHousesIndex() + 1)) {
									addingLabel.setVisible(true);
									frame.getHousesOnFrame().get(frame.getNumHousesLeft()).setVisible(false);
								}

								frame.setNumHousesIndex(frame.getNumHousesIndex() - 1);
								frame.setNumHousesLeft(frame.getNumHousesLeft() - 1);
							}
						}
					}
				}

				if (houses.size() > 0) {

					JLabel label = houses.get(0);
					label.setBounds(190 + (15 * (frame.getNumHousesIndex() + 1)), 27, 11, 17);

					if (frame.getNumHousesLeft() != (frame.getNumHousesIndex() + 1)) {
						label.setVisible(false);
						frame.getHousesOnFrame().get(frame.getNumHousesLeft()).setVisible(true);
					}
					frame.setNumHousesLeft(frame.getNumHousesLeft() + 1);
					frame.setNumHousesIndex(frame.getNumHousesIndex() + 1);

					if (houses.size() == 1)
						frame.getListBtnMenos().get(index).getButton().setVisible(false);

				}
				frame.getLbHousesPrice().setText("Price : £ " + frame.calculatePrice());
			}
		});
	}

	private ArrayList<JLabel> sortLabelList(ArrayList<JLabel> labelsList) {

		ArrayList<JLabel> newLabelsList, usedLabelsList;
		newLabelsList = new ArrayList<JLabel>();
		usedLabelsList = new ArrayList<JLabel>();

		for (JLabel label : labelsList) {
			if (label.getY() == 48 || label.getY() == 27) {
				newLabelsList.add(label);
			} else {
				usedLabelsList.add(label);
			}
		}

		for (JLabel label : usedLabelsList) {
			newLabelsList.add(label);
		}

		return newLabelsList;
	}

	public JButton getButton() {
		return button;
	}

}

class MasButton {
	private JButton button;

	public MasButton(JButton button, AddHouseFrame frame, int index) {
		this.button = button;

		this.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				frame.setHousesOnFrame(sortLabelList(frame.getHousesOnFrame()));
				frame.setHotelsOnFrame(sortLabelList(frame.getHotelsOnFrame()));

				ArrayList<JLabel> houses = new ArrayList<JLabel>();

				for (JLabel label : frame.getHousesOnFrame())
					if (button.getY() == label.getY())
						houses.add(label);

				if (houses.size() < 4) {

					if (frame.getNumHousesLeft() < 0) {

					} else {

						JLabel addingLabel = frame.getHousesOnFrame().get(frame.getNumHousesIndex());
						addingLabel.setBounds(210 + (15 * houses.size()), button.getY(), 11, 17);
						addingLabel.setVisible(true);

						frame.setNumHousesIndex(frame.getNumHousesIndex() - 1);
						frame.setNumHousesLeft(frame.getNumHousesLeft() - 1);

						if (frame.getNumHousesLeft() != (frame.getNumHousesIndex() + 1))
							frame.getHousesOnFrame().get(frame.getNumHousesLeft()).setVisible(false);

						if (houses.size() == 0)
							frame.getListBtnMenos().get(index).getButton().setVisible(true);
					}

				} else {

					if (frame.getNumHotelsLeft() > 0) {
						JLabel addingLabel = frame.getHotelsOnFrame().get(frame.getNumHotelsIndex());
						addingLabel.setBounds(210, button.getY(), 17, 17);
						addingLabel.setVisible(true);

						for (JLabel houseLabel : houses) {
							houseLabel.setBounds(190 + (15 * (frame.getNumHousesIndex() + 1) + 1), 27, 11, 17);

							if (frame.getNumHousesLeft() != (frame.getNumHousesIndex() + 1)) {
								houseLabel.setVisible(false);
								frame.getHousesOnFrame().get(frame.getNumHousesLeft()).setVisible(true);
							}
							frame.setNumHousesLeft(frame.getNumHousesLeft() + 1);
							frame.setNumHousesIndex(frame.getNumHousesIndex() + 1);
						}

						frame.setNumHotelsIndex(frame.getNumHotelsIndex() - 1);
						frame.setNumHotelsLeft(frame.getNumHotelsLeft() - 1);

						if (frame.getNumHotelsLeft() != (frame.getNumHotelsIndex() + 1))
							frame.getHotelsOnFrame().get(frame.getNumHotelsLeft()).setVisible(false);

						button.setVisible(false);
					}

				}
				frame.getLbHousesPrice().setText("Price : £ " + frame.calculatePrice());
			}

		});
	}

	private ArrayList<JLabel> sortLabelList(ArrayList<JLabel> labelsList) {

		ArrayList<JLabel> newLabelsList, usedLabelsList;
		newLabelsList = new ArrayList<JLabel>();
		usedLabelsList = new ArrayList<JLabel>();

		for (JLabel label : labelsList) {
			if (label.getY() == 48 || label.getY() == 27) {
				newLabelsList.add(label);
			} else {
				usedLabelsList.add(label);
			}
		}

		for (JLabel label : usedLabelsList) {
			newLabelsList.add(label);
		}

		return newLabelsList;
	}

	public JButton getButton() {
		return button;
	}
}

@SuppressWarnings("serial")
public class AddHouseFrame extends JFrame {

	private JPanel contentPane;
	private int numHousesIndex, numHotelsIndex, numHotelsLeft, numHousesLeft;
	private Player player;
	private JButton btnSalir, btnAdd;
	private JPanel playerPanel;
	private JLabel lbHousesPrice, lbPlayerMoney;

	private JList<String>[] propertiesList;
	private ArrayList<JLabel> housesOnFrame, hotelsOnFrame;
	private ArrayList<MasButton> listBtnMas;
	private ArrayList<MenosButton> listBtnMenos;

	public AddHouseFrame(Player player) {

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1024, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.player = player;

		setFrame();

	}

	public void start() {

		uploadFrame();

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				player.setMoney(player.getMoney() - calculatePrice());

				for (int indexPorpertiesList = 0; indexPorpertiesList < 8; indexPorpertiesList++) {
					if (propertiesList[indexPorpertiesList].isVisible()) {
						for (int indexProperty = 0; indexProperty < propertiesList[indexPorpertiesList].getModel()
								.getSize(); indexProperty++) {
							for (TileGame property : player.getMainGameFrame().getTilesGame()) {
								if (property.getName().equals(
										propertiesList[indexPorpertiesList].getModel().getElementAt(indexProperty))) {
									int numhouses = 0;
									for (JLabel label : housesOnFrame) {
										if (label.getY() == propertiesList[indexPorpertiesList].getY()
												+ 18 * indexProperty)
											numhouses++;
									}

									for (JLabel label : hotelsOnFrame) {
										if (label.getY() == propertiesList[indexPorpertiesList].getY()
												+ 18 * indexProperty)
											numhouses = 5;
									}

									StreetTile street = (StreetTile) property;
									street.setHouses(numhouses);

									if (numhouses != 5) {

										for (int indexHouses = 0; indexHouses < numhouses; indexHouses++)
											street.getHouseLabels().get(indexHouses).setVisible(true);

										for (int indexHouses = numhouses; indexHouses < street.getHouseLabels()
												.size(); indexHouses++)
											street.getHouseLabels().get(indexHouses).setVisible(false);

										street.getHotelLabel().setVisible(false);

									} else {

										street.getHotelLabel().setVisible(true);

										for (int indexHouses = 0; indexHouses < street.getHouseLabels()
												.size(); indexHouses++)
											street.getHouseLabels().get(indexHouses).setVisible(false);

									}

								}

							}
						}
					}
				}

				player.getMainGameFrame().setNumhouses(numHousesLeft);
				player.getMainGameFrame().setNumhotels(numHotelsLeft);
				uploadFrame();
			}
		});

		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				player.getMainGameFrame().uploadPlayerFrame();
				player.getMainGameFrame().getBtnTerminar().setVisible(true);
				player.getMainGameFrame().getBtnOfrecer().setVisible(true);
				player.getMainGameFrame().getBtnDeshipotecar().setVisible(true);
				player.getMainGameFrame().getBtnHipotecar().setVisible(true);
				player.getMainGameFrame().getBtnAddHouse().setVisible(true);

				player.getMainGameFrame().getLbMoney().setText(lbPlayerMoney.getText());
				setVisible(false);
				dispose();
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void setFrame() {
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(895, 15, 117, 25);
		contentPane.add(btnSalir);

		JLabel lbPlayer = new JLabel(player.getName());
		lbPlayer.setFont(new Font("Century Schoolbook L", Font.BOLD | Font.ITALIC, 54));
		lbPlayer.setBounds(173, 20, 260, 60);
		contentPane.add(lbPlayer);

		lbPlayerMoney = new JLabel("Money : £ " + player.getMoney());
		lbPlayerMoney.setBounds(27, 99, 167, 15);
		contentPane.add(lbPlayerMoney);

		lbHousesPrice = new JLabel("Price : £ 0");
		lbHousesPrice.setBounds(27, 134, 167, 15);
		contentPane.add(lbHousesPrice);

		playerPanel = new JPanel();
		playerPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerPanel.setPreferredSize(new Dimension(820, 770));
		playerPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(playerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(27, 149, 830, 443);
		contentPane.add(scrollPane);

		JLabel lbHousesLeft = new JLabel("Houses remaining: ");
		lbHousesLeft.setBounds(27, 27, 145, 17);
		playerPanel.add(lbHousesLeft);

		JLabel lbHotelsLeft = new JLabel("Hotels remaining: ");
		lbHotelsLeft.setBounds(27, 48, 145, 17);
		playerPanel.add(lbHotelsLeft);

		housesOnFrame = new ArrayList<JLabel>();
		Image imageHouse = new ImageIcon(this.getClass().getResource("/houseHorizontal.png")).getImage();
		for (int i = 0; i < 32; i++) {
			JLabel label = new JLabel();
			label.setIcon(new ImageIcon(imageHouse));
			playerPanel.add(label);
			housesOnFrame.add(label);
		}

		hotelsOnFrame = new ArrayList<JLabel>();
		Image imageHotel = new ImageIcon(this.getClass().getResource("/hotelHorizontal.png")).getImage();
		for (int i = 0; i < 12; i++) {
			JLabel label = new JLabel();
			label.setIcon(new ImageIcon(imageHotel));
			playerPanel.add(label);
			hotelsOnFrame.add(label);
		}

		listBtnMas = new ArrayList<MasButton>();
		for (int i = 0; i < 22; i++) {
			listBtnMas.add(new MasButton(new JButton("+"), this, i));
			playerPanel.add(listBtnMas.get(i).getButton());

		}

		listBtnMenos = new ArrayList<MenosButton>();
		for (int i = 0; i < 22; i++) {
			listBtnMenos.add(new MenosButton(new JButton("-"), this, i));
			playerPanel.add(listBtnMenos.get(i).getButton());
		}

		propertiesList = new JList[8];
		setPropertiesList();

		btnAdd = new JButton("add");
		btnAdd.setBounds(748, 12, 61, 25);
		playerPanel.add(btnAdd);
	}

	private void uploadFrame() {

		numHotelsLeft = player.getMainGameFrame().getNumhotels();
		numHousesLeft = player.getMainGameFrame().getNumhouses();

		for (int i = 0; i < 32; i++) {
			if (i < numHousesLeft) {
				housesOnFrame.get(i).setVisible(true);
			} else {
				housesOnFrame.get(i).setVisible(false);
			}
			housesOnFrame.get(i).setBounds(190 + (15 * i), 27, 11, 17);

		}

		for (int i = 0; i < 12; i++) {
			if (i < numHotelsLeft) {
				hotelsOnFrame.get(i).setVisible(true);
			} else {
				hotelsOnFrame.get(i).setVisible(false);
			}
			hotelsOnFrame.get(i).setBounds(190 + (26 * i), 48, 17, 17);
		}

		for (int i = 0; i < 22; i++) {
			listBtnMas.get(i).getButton().setVisible(false);
			listBtnMenos.get(i).getButton().setVisible(false);
		}

		uploadPropertiesList();
		lbHousesPrice.setText("Price : £ " + calculatePrice());
		lbPlayerMoney.setText("Money : £ " + player.getMoney());

	}

	private void uploadPropertiesList() {
		int y;

		numHousesIndex = 31;
		numHotelsIndex = 11;

		for (int index = 0; index < 8; index++) {

			if (index == 0) {
				y = propertiesList[index].getY();
			} else {
				y = propertiesList[index - 1].getY() + propertiesList[index - 1].getHeight();

				if (y != propertiesList[index - 1].getY() && player.getProperties()[index + 2].size() > 0)
					y += 20;
			}

			boolean isHipotecada = false;

			for (TileGame property : player.getProperties()[index + 2]) {
				StreetTile street = (StreetTile) property;
				if (street.getHipotecaLabel().isVisible())
					isHipotecada = true;
			}

			if (!isHipotecada && ((player.getProperties()[index + 2].size() == 2 && (index == 0 || index == 7))
					|| (player.getProperties()[index + 2].size() == 3 && !(index == 0 || index == 7)))) {

				propertiesList[index].setModel(getTileListModel(player.getProperties()[index + 2]));
				propertiesList[index].setVisible(true);

				propertiesList[index].setBounds(propertiesList[index].getX(), y, propertiesList[index].getWidth(),
						6 + (17 * player.getProperties()[index + 2].size()));

				setHousesOnFrame(player.getProperties()[index + 2], index);

			} else {
				propertiesList[index].setBounds(propertiesList[index].getX(), y, propertiesList[index].getWidth(), 0);
				propertiesList[index].setVisible(false);
			}
		}
	}

	private void setPropertiesList() {

		int[][] colors = { { 128, 0, 0 }, { 173, 216, 230 }, { 255, 105, 180 }, { 255, 140, 0 }, { 255, 0, 0 },
				{ 255, 255, 0 }, { 34, 139, 34 }, { 0, 0, 205 } };

		for (int index = 0; index < 8; index++) {
			propertiesList[index] = new JList<String>();
			propertiesList[index]
					.setBorder(new LineBorder(new Color(colors[index][0], colors[index][1], colors[index][2]), 3));
			propertiesList[index].setBounds(27, 70, 174, 0);
			propertiesList[index].setVisible(false);
			playerPanel.add(propertiesList[index]);
		}

	}

	private DefaultListModel<String> getTileListModel(ArrayList<TileGame> propertyList) {

		DefaultListModel<String> propertyListModel = new DefaultListModel<>();

		for (TileGame property : propertyList)
			propertyListModel.addElement(property.getName());
		return propertyListModel;
	}

	private void setHousesOnFrame(ArrayList<TileGame> propertyList, int index) {

		for (int i = 0; i < propertyList.size(); i++) {
			StreetTile street = (StreetTile) propertyList.get(i);
			if (street.getHouses() == 5) {
				if (numHotelsIndex < 0) {

				} else {
					hotelsOnFrame.get(numHotelsIndex).setBounds(210, propertiesList[index].getY() + (18 * i), 17, 17);
					hotelsOnFrame.get(numHotelsIndex).setVisible(true);
					numHotelsIndex--;
				}

			} else {
				for (int j = 0; j < street.getHouses(); j++) {
					if (numHousesIndex < 0) {

					} else {
						housesOnFrame.get(numHousesIndex).setBounds(210 + (15 * j),
								propertiesList[index].getY() + (18 * i), 11, 17);
						housesOnFrame.get(numHousesIndex).setVisible(true);
						numHousesIndex--;
					}
				}
			}

			int indexMas = 2 + ((index - 1) * 3);
			if (index == 0)
				indexMas = 0;

			indexMas += i;

			listBtnMas.get(indexMas).getButton().setBounds(280, propertiesList[index].getY() + (18 * i), 55, 17);
			listBtnMenos.get(indexMas).getButton().setBounds(340, propertiesList[index].getY() + (18 * i), 55, 17);

			if (street.getHouses() < 5) {
				listBtnMas.get(indexMas).getButton().setVisible(true);
			} else {
				listBtnMas.get(indexMas).getButton().setVisible(false);
			}

			if (street.getHouses() > 0) {
				listBtnMenos.get(indexMas).getButton().setVisible(true);
			} else {
				listBtnMenos.get(indexMas).getButton().setVisible(false);
			}

		}
	}

	public int calculatePrice() {
		int totalPrice = 0, numHouses, multiplierPrice;

		for (int index = 0; index < 8; index++) {
			if (propertiesList[index].isVisible()) {
				for (int i = 0; i < player.getProperties()[index + 2].size(); i++) {
					TileGame property = player.getProperties()[index + 2].get(i);
					StreetTile street = (StreetTile) property;
					numHouses = 0;
					multiplierPrice = index / 2;

					for (JLabel label : housesOnFrame) {
						if (propertiesList[index].getY() + (18 * i) == label.getY())
							numHouses++;
					}

					for (JLabel label : hotelsOnFrame) {
						if (propertiesList[index].getY() + (18 * i) == label.getY())
							numHouses += 5;
					}

					numHouses -= street.getHouses();

					if (numHouses > 0) {
						totalPrice += 50 * (multiplierPrice + 1) * numHouses;
					} else if (numHouses < 0) {
						totalPrice += 25 * (multiplierPrice + 1) * numHouses;
					}
				}
			}
		}

		return totalPrice;
	}

	// getters and setters

	public int getNumHousesIndex() {
		return numHousesIndex;
	}

	public void setNumHousesIndex(int numHousesLeft) {
		this.numHousesIndex = numHousesLeft;
	}

	public int getNumHotelsIndex() {
		return numHotelsIndex;
	}

	public void setNumHotelsIndex(int numHotelsLeft) {
		this.numHotelsIndex = numHotelsLeft;
	}

	public ArrayList<MasButton> getListBtnMas() {
		return listBtnMas;
	}

	public ArrayList<MenosButton> getListBtnMenos() {
		return listBtnMenos;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<JLabel> getHousesOnFrame() {
		return housesOnFrame;
	}

	public ArrayList<JLabel> getHotelsOnFrame() {
		return hotelsOnFrame;
	}

	public void setHousesOnFrame(ArrayList<JLabel> housesOnFrame) {
		this.housesOnFrame = housesOnFrame;
	}

	public void setHotelsOnFrame(ArrayList<JLabel> hotelsOnFrame) {
		this.hotelsOnFrame = hotelsOnFrame;
	}

	public int getNumHotelsLeft() {
		return numHotelsLeft;
	}

	public void setNumHotelsLeft(int numHotelsLeft) {
		this.numHotelsLeft = numHotelsLeft;
	}

	public int getNumHousesLeft() {
		return numHousesLeft;
	}

	public void setNumHousesLeft(int numHousesLeft) {
		this.numHousesLeft = numHousesLeft;
	}

	public JLabel getLbHousesPrice() {
		return lbHousesPrice;
	}

	public JLabel getLbPlayerMoney() {
		return lbPlayerMoney;
	}

}
