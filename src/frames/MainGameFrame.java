package frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.Player;
import tiles.ChanceTile;
import tiles.CompanyTile;
import tiles.ComunityTile;
import tiles.FreeTile;
import tiles.JailTile;
import tiles.RailRoadTile;
import tiles.StreetTile;
import tiles.TaxTile;
import tiles.TileGame;

@SuppressWarnings("serial")
public class MainGameFrame extends JFrame {

	private JPanel contentPane, playerPanel;

	private JLabel lbFicha1, lbFicha2, lbFicha3, lbFicha4;
	private JLabel lbTablero, lbDado, lbMoney, lbNamePlayer;
	private JList<String> freeJailList;

	private JButton btnDado, btnTerminar, btnBuySell, btnDeshipotecar, btnHipotecar, btnAddHouse;

	@SuppressWarnings("rawtypes")
	private JList[] propertiesList;
	private ArrayList<String> chanceCards, comunityCards;
	private ArrayList<Player> players;
	private ArrayList<TileGame> tilesGame;

	private int playerTurn, doble, dado1, dado2, numhouses, numhotels;

	private Player player;
	private BuySellFrame[] buySellFrames;
	private Hipotecar[] hipotecar;
	private AddHouseFrame[] addHouseFrame;

	public MainGameFrame(String[] namePlayers) {

		chanceCards = new ArrayList<String>();
		comunityCards = new ArrayList<String>();

		setPlayersToGame(namePlayers);
		setTilesGame();
		setChanceCards();
		setComunityCards();

		this.playerTurn = 0;
		this.player = players.get(0);
		this.buySellFrames = new BuySellFrame[players.size()];
		this.hipotecar = new Hipotecar[players.size()];
		this.addHouseFrame = new AddHouseFrame[players.size()];
		this.numhotels = 12;
		this.numhouses = 32;

		for (int i = 0; i < players.size(); i++) {
			this.buySellFrames[i] = new BuySellFrame();
			this.hipotecar[i] = new Hipotecar();
			this.addHouseFrame[i] = new AddHouseFrame(players.get(i));
		}

		startGame();

	}

	public void startGame() {

		setFrame();
		uploadPlayerFrame();
		lbDado.setText(String.valueOf(dado1) + "  |  " + String.valueOf(dado2));

		btnDado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int nextPosition = 0;

				btnDado.setVisible(false);
				btnTerminar.setVisible(true);

				btnBuySell.setVisible(true);
				btnDeshipotecar.setVisible(true);
				btnHipotecar.setVisible(true);
				btnAddHouse.setVisible(true);

				drawDado();

				if (player.getJail() > 0)
					player.checkJailPayment();

				lbDado.setText(String.valueOf(dado1) + "  |  " + String.valueOf(dado2));

				nextPosition = getNextPosition();

				movePlayer(nextPosition);

				setActions();

				uploadPlayerFrame();

				btnAddHouse.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						btnBuySell.setVisible(false);
						btnTerminar.setVisible(false);
						btnDeshipotecar.setVisible(false);
						btnHipotecar.setVisible(false);
						btnAddHouse.setVisible(false);

						addHouseFrame[player.getNumPlayer() - 1].start();
						addHouseFrame[player.getNumPlayer() - 1].setVisible(true);

					}
				});

				btnBuySell.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						btnBuySell.setVisible(false);
						btnTerminar.setVisible(false);
						btnDeshipotecar.setVisible(false);
						btnHipotecar.setVisible(false);
						btnAddHouse.setVisible(false);

						buySellFrames[player.getNumPlayer() - 1].setVisible(true);
						buySellFrames[player.getNumPlayer() - 1].setBuySell(players, playerTurn);

					}
				});

				btnHipotecar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						btnBuySell.setVisible(false);
						btnTerminar.setVisible(false);
						btnDeshipotecar.setVisible(false);
						btnHipotecar.setVisible(false);
						btnAddHouse.setVisible(false);

						hipotecar[player.getNumPlayer() - 1].setVisible(true);
						hipotecar[player.getNumPlayer() - 1].setHipotecarFrame(player);

					}
				});

				btnDeshipotecar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						btnBuySell.setVisible(false);
						btnTerminar.setVisible(false);
						btnHipotecar.setVisible(false);
						btnDeshipotecar.setVisible(false);
						btnAddHouse.setVisible(false);

						hipotecar[player.getNumPlayer() - 1].setVisible(true);
						hipotecar[player.getNumPlayer() - 1].setDeshipotecarFrame(player);

					}
				});

				btnTerminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						btnBuySell.setVisible(false);
						btnDeshipotecar.setVisible(false);
						btnHipotecar.setVisible(false);
						btnAddHouse.setVisible(false);

						setNextTurnPlayer();
						startGame();

					}
				});

			}

		});

	}

	private void drawDado() {

		Random random = new Random();

		dado1 = random.nextInt(6) + 1;
		dado2 = random.nextInt(6) + 1;

		player.setDado(dado1 + dado2);

	}

	private void movePlayer(int nextPosition) {

		if (!(player.getJail() > 0 && player.getJail() < 3) || doble > 0) {
			player.setJail(0);
			player.movePlayer(nextPosition);
			lbMoney.setText("Money : £ " + player.getMoney());
		} else {
			player.setJail(player.getJail() + 1);
		}

	}

	private void setActions() {

		while (player.getAction()) {

			moveFichaLabel();

			tilesGame.get(player.getPosition()).getAction(player);

			lbMoney.setText("Money : £ " + player.getMoney());
		}

		player.setAction(true);

	}

	public void moveFichaLabel() {
		switch (playerTurn) {
		case 0:
			lbFicha1.setBounds(player.getPositionX(), player.getPositionY(), 20, 20);
			break;
		case 1:
			lbFicha2.setBounds(player.getPositionX(), player.getPositionY(), 20, 20);
			break;
		case 2:
			lbFicha3.setBounds(player.getPositionX(), player.getPositionY(), 20, 20);
			break;
		case 3:
			lbFicha4.setBounds(player.getPositionX(), player.getPositionY(), 20, 20);
			break;

		default:
			break;
		}
	}

	private int getNextPosition() {

		int nextPosition = (player.getDado() + player.getPosition()) % 40;

		if (checkThirdDouble(dado1, dado2))
			nextPosition = 30;

		return nextPosition;

	}

	private boolean checkThirdDouble(int dado1, int dado2) {
		if (dado1 == dado2) {
			doble++;
		} else {
			doble = 0;
		}

		if (doble == 3) {

			JOptionPane.showMessageDialog(null, "Tercer doble!", "Tercer doble", JOptionPane.INFORMATION_MESSAGE);
			doble = 0;
			if (player.getPosition() > 30)
				player.setGetPaid(false);
			return true;
		}
		return false;
	}

	private void setNextTurnPlayer() {

		if (doble == 0)
			playerTurn = (playerTurn + 1) % players.size();
		player = players.get(playerTurn);

	}

	@SuppressWarnings("unchecked")
	public void uploadPlayerFrame() {
		int y;

		for (int index = 0; index < 10; index++) {

			if (index == 0 || index == 1) {
				y = propertiesList[index].getY();
			} else {
				y = propertiesList[index - 2].getY() + propertiesList[index - 2].getHeight();

				if (y != propertiesList[index % 2].getY() && player.getProperties()[index].size() > 0)
					y += 20;
			}

			if (player.getProperties()[index].size() > 0) {
				propertiesList[index].setModel(getTileListModel(player.getProperties()[index]));
				propertiesList[index].setVisible(true);
				propertiesList[index].setBounds(propertiesList[index].getX(), y, propertiesList[index].getWidth(),
						6 + (17 * player.getProperties()[index].size()));

			} else {
				propertiesList[index].setBounds(propertiesList[index].getX(), y, propertiesList[index].getWidth(), 0);
				propertiesList[index].setVisible(false);
			}

		}

		if (player.getChFreeJail() || player.getCoFreeJail()) {

			DefaultListModel<String> dlm = new DefaultListModel<>();

			if (player.getChFreeJail())
				dlm.addElement("Chance Jail Free");

			if (player.getCoFreeJail())
				dlm.addElement("Comunity Jail Free");

			freeJailList.setModel(dlm);
			freeJailList.setVisible(true);
		} else {
			freeJailList.setVisible(false);
		}

	}

	private DefaultListModel<String> getTileListModel(ArrayList<TileGame> propertyList) {

		DefaultListModel<String> propertyListModel = new DefaultListModel<>();

		if (propertyList.size() > 0) {

			for (TileGame property : propertyList)
				propertyListModel.addElement(property.getName());
		}

		return propertyListModel;
	}

	private void setFrame() {
		Image imgPlayer1 = new ImageIcon(this.getClass().getResource("/ficha1.png")).getImage();
		Image imgPlayer2 = new ImageIcon(this.getClass().getResource("/ficha2.png")).getImage();
		Image imgPlayer3 = new ImageIcon(this.getClass().getResource("/ficha3.png")).getImage();
		Image imgPlayer4 = new ImageIcon(this.getClass().getResource("/ficha4.png")).getImage();

		Image imgTablero = new ImageIcon(this.getClass().getResource("/monopolyTable.png")).getImage();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lbFicha1 = new JLabel();
		lbFicha1.setIcon(new ImageIcon(imgPlayer1));
		lbFicha1.setBounds(players.get(0).getPositionX(), players.get(0).getPositionY(), 20, 20);
		contentPane.add(lbFicha1);

		lbFicha2 = new JLabel();
		lbFicha2.setIcon(new ImageIcon(imgPlayer2));
		lbFicha2.setBounds(players.get(1).getPositionX(), players.get(1).getPositionY(), 20, 20);
		contentPane.add(lbFicha2);

		if (players.size() > 2) {

			lbFicha3 = new JLabel();
			lbFicha3.setIcon(new ImageIcon(imgPlayer3));
			lbFicha3.setBounds(players.get(2).getPositionX(), players.get(2).getPositionY(), 20, 20);
			contentPane.add(lbFicha3);

			if (players.size() == 4) {

				lbFicha4 = new JLabel();
				lbFicha4.setIcon(new ImageIcon(imgPlayer4));
				lbFicha4.setBounds(players.get(3).getPositionX(), players.get(3).getPositionY(), 20, 20);
				contentPane.add(lbFicha4);
			}
		}

		setPropertyLabels();

		lbTablero = new JLabel();
		lbTablero.setIcon(new ImageIcon(imgTablero));
		lbTablero.setBounds(5, 5, 600, 600);
		contentPane.add(lbTablero);

		btnDado = new JButton("Dado");
		btnDado.setBounds(838, 12, 117, 25);
		contentPane.add(btnDado);

		btnTerminar = new JButton("Terminar");
		btnTerminar.setBounds(838, 12, 117, 25);
		contentPane.add(btnTerminar);
		btnTerminar.setVisible(false);

		btnAddHouse = new JButton("Add House");
		btnAddHouse.setBounds(623, 60, 117, 25);
		contentPane.add(btnAddHouse);
		btnAddHouse.setVisible(false);

		btnBuySell = new JButton("Buy / Sell");
		btnBuySell.setBounds(623, 87, 117, 25);
		contentPane.add(btnBuySell);
		btnBuySell.setVisible(false);

		lbDado = new JLabel("");
		lbDado.setFont(new Font("Dialog", Font.BOLD, 38));
		lbDado.setBounds(838, 53, 168, 57);
		contentPane.add(lbDado);

		playerPanel = new JPanel();
		playerPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerPanel.setBounds(620, 128, 420, 480);
		contentPane.add(playerPanel);
		playerPanel.setLayout(null);

		lbMoney = new JLabel("Money : £ " + player.getMoney());
		lbMoney.setBounds(12, 12, 167, 15);
		playerPanel.add(lbMoney);

		propertiesList = new JList[10];
		setPropertiesList();

		freeJailList = new JList<String>();
		freeJailList.setBackground(UIManager.getColor("Panel.background"));
		freeJailList.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Free Jail Cards",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		freeJailList.setBounds(26, 420, 174, 56);
		freeJailList.setVisible(false);
		playerPanel.add(freeJailList);

		btnHipotecar = new JButton("Hipotecar");
		btnHipotecar.setBounds(291, 7, 117, 25);
		btnHipotecar.setVisible(false);
		playerPanel.add(btnHipotecar);

		btnDeshipotecar = new JButton("Deshipotecar");
		btnDeshipotecar.setBounds(161, 7, 117, 25);
		btnDeshipotecar.setVisible(false);
		playerPanel.add(btnDeshipotecar);

		lbNamePlayer = new JLabel(player.getName() + " is your turn");
		lbNamePlayer.setBounds(630, 12, 200, 25);
		lbNamePlayer.setFont(new Font("Century Schoolbook L", Font.BOLD, 18));
		contentPane.add(lbNamePlayer);

	}

	// constructor methods
	private void setPropertiesList() {

		int[][] colors = { { 0, 0, 0 }, { 128, 128, 128 }, { 128, 0, 0 }, { 173, 216, 230 }, { 255, 105, 180 },
				{ 255, 140, 0 }, { 255, 0, 0 }, { 255, 255, 0 }, { 34, 139, 34 }, { 0, 0, 205 } };

		for (int index = 0; index < 10; index++) {
			propertiesList[index] = new JList<String>();
			propertiesList[index]
					.setBorder(new LineBorder(new Color(colors[index][0], colors[index][1], colors[index][2]), 3));
			if (index % 2 == 0) {
				propertiesList[index].setBounds(26, 52, 174, 0);
			} else {
				propertiesList[index].setBounds(230, 52, 174, 0);
			}

			propertiesList[index].setVisible(false);
			playerPanel.add(propertiesList[index]);
		}
	}

	private void setTilesGame() {
		tilesGame = new ArrayList<TileGame>();

		tilesGame.add(new FreeTile("Start", 0));
		tilesGame.add(new StreetTile("Mediterranean Avenue", 1, 60, 1, 2, 10, 30, 90, 160, 250, 473, 527,
				"/hipotecaDown.png", 49, 80));
		tilesGame.add(new ComunityTile("Comunity Chest", 2));
		tilesGame.add(new StreetTile("Baltic Avenue", 3, 60, 1, 4, 20, 60, 180, 320, 450, 378, 527, "/hipotecaDown.png",
				49, 80));
		tilesGame.add(new TaxTile("Income Tax", 4, 200));
		tilesGame.add(new RailRoadTile("Reading Rail Road", 5, 200, 275, 527, "/hipotecaDown.png", 49, 80));
		tilesGame.add(new StreetTile("Oriental Avenue", 6, 100, 2, 6, 30, 90, 270, 400, 550, 231, 527,
				"/hipotecaDown.png", 49, 80));
		tilesGame.add(new ChanceTile("Chance", 7));
		tilesGame.add(new StreetTile("Vermont Avenue", 8, 100, 2, 6, 30, 90, 270, 400, 550, 133, 527,
				"/hipotecaDown.png", 49, 80));
		tilesGame.add(new StreetTile("Connecticut Avenue", 9, 120, 2, 8, 40, 100, 300, 450, 600, 84, 527,
				"/hipotecaDown.png", 49, 80));

		tilesGame.add(new FreeTile("Jail", 10));
		tilesGame.add(new StreetTile("St. Charles Place", 11, 140, 3, 10, 50, 150, 450, 625, 750, 5, 476,
				"/hipotecaLeft.png", 80, 49));
		tilesGame.add(new CompanyTile("Electric Company", 12, 150, 5, 428, "/hipotecaLeft.png", 80, 49));
		tilesGame.add(new StreetTile("States Avenue", 13, 140, 3, 10, 50, 150, 450, 625, 750, 5, 379,
				"/hipotecaLeft.png", 80, 49));
		tilesGame.add(new StreetTile("Virginia Avenue", 14, 160, 3, 12, 60, 180, 500, 700, 900, 5, 330,
				"/hipotecaLeft.png", 80, 49));
		tilesGame.add(new RailRoadTile("Pennsylvania Rail Road", 15, 200, 5, 281, "/hipotecaLeft.png", 80, 49));
		tilesGame.add(new StreetTile("St. James Place", 16, 180, 4, 14, 70, 200, 550, 750, 950, 5, 232,
				"/hipotecaLeft.png", 80, 49));
		tilesGame.add(new ComunityTile("Comunity Chest", 17));
		tilesGame.add(new StreetTile("Tennessee Avenue", 18, 180, 4, 14, 70, 200, 550, 750, 950, 5, 134,
				"/hipotecaLeft.png", 80, 49));
		tilesGame.add(new StreetTile("New York Avenue", 19, 200, 4, 16, 80, 220, 600, 800, 1000, 5, 85,
				"/hipotecaLeft.png", 80, 49));

		tilesGame.add(new FreeTile("Parking Free", 20));
		tilesGame.add(new StreetTile("Kentuchy Avenue", 21, 220, 5, 18, 90, 250, 700, 875, 1050, 84, 5,
				"/hipotecaUp.png", 49, 80));
		tilesGame.add(new ChanceTile("Chance", 22));
		tilesGame.add(new StreetTile("Indiana Avenue", 23, 220, 5, 18, 90, 250, 700, 875, 1050, 181, 5,
				"/hipotecaUp.png", 49, 80));
		tilesGame.add(new StreetTile("Ilinois Avenue", 24, 240, 5, 20, 100, 300, 750, 925, 1100, 230, 5,
				"/hipotecaUp.png", 49, 80));
		tilesGame.add(new RailRoadTile("B&O Rail Road", 25, 200, 279, 5, "/hipotecaUp.png", 49, 80));
		tilesGame.add(new StreetTile("Atlantic Avenue", 26, 260, 6, 22, 110, 330, 800, 975, 1150, 328, 5,
				"/hipotecaUp.png", 49, 80));
		tilesGame.add(new StreetTile("Ventor Avenue", 27, 260, 6, 22, 110, 330, 800, 975, 1150, 377, 5,
				"/hipotecaUp.png", 49, 80));
		tilesGame.add(new CompanyTile("Water Works", 28, 150, 426, 5, "/hipotecaUp.png", 49, 80));
		tilesGame.add(new StreetTile("Marvin Gardens", 29, 280, 6, 24, 120, 360, 850, 1025, 1200, 475, 5,
				"/hipotecaUp.png", 49, 80));

		tilesGame.add(new JailTile("Go to Jail", 30));
		tilesGame.add(new StreetTile("Pacific Avenue", 31, 300, 7, 26, 130, 390, 900, 1100, 1275, 521, 85,
				"/hipotecaRight.png", 80, 49));
		tilesGame.add(new StreetTile("North Carolina Avenue", 32, 300, 7, 26, 130, 390, 900, 1100, 1275, 521, 134,
				"/hipotecaRight.png", 80, 49));
		tilesGame.add(new ComunityTile("Comunity Chest", 33));
		tilesGame.add(new StreetTile("Pennsylvania Avenue", 34, 320, 7, 28, 150, 450, 1000, 1200, 1400, 521, 232,
				"/hipotecaRight.png", 80, 49));
		tilesGame.add(new RailRoadTile("Short Line", 35, 200, 521, 281, "/hipotecaRight.png", 80, 49));
		tilesGame.add(new ChanceTile("Chance", 36));
		tilesGame.add(new StreetTile("Park Place", 37, 350, 8, 35, 175, 500, 1100, 1300, 1500, 521, 379,
				"/hipotecaRight.png", 80, 49));
		tilesGame.add(new TaxTile("Luxury Tax", 38, 100));
		tilesGame.add(new StreetTile("Boardwalk", 39, 400, 8, 50, 200, 600, 1400, 1700, 2000, 521, 477,
				"/hipotecaRight.png", 80, 49));
	}

	private void setPlayersToGame(String[] namePlayers) {
		players = new ArrayList<Player>();

		players.add(new Player(namePlayers[0], 1, 545, 515, this));
		players.add(new Player(namePlayers[1], 2, 570, 515, this));
		if (namePlayers.length >= 3)
			players.add(new Player(namePlayers[2], 3, 545, 540, this));
		if (namePlayers.length == 4)
			players.add(new Player(namePlayers[3], 4, 570, 540, this));

	}

	private void setPropertyLabels() {

		for (TileGame property : tilesGame) {
			if (property.getPosition() != 0 && property.getPosition() != 2 && property.getPosition() != 4
					&& property.getPosition() != 7 && property.getPosition() != 10 && property.getPosition() != 17
					&& property.getPosition() != 20 && property.getPosition() != 22 && property.getPosition() != 30
					&& property.getPosition() != 33 && property.getPosition() != 36 && property.getPosition() != 38) {

				if (property.getPosition() == 5 || property.getPosition() == 15 || property.getPosition() == 25
						|| property.getPosition() == 35) {
					RailRoadTile railRoad = (RailRoadTile) property;
					contentPane.add(railRoad.getHipotecaLabel());
				} else if (property.getPosition() == 12 || property.getPosition() == 28) {
					CompanyTile company = (CompanyTile) property;
					contentPane.add(company.getHipotecaLabel());
				} else {
					StreetTile street = (StreetTile) property;
					for (JLabel label : street.getHouseLabels()) {
						contentPane.add(label);
					}
					contentPane.add(street.getHotelLabel());
					contentPane.add(street.getHipotecaLabel());

				}

			}
		}
	}

	public void setChanceCards() {

		boolean jailCard = false;

		chanceCards.add("Avance hasta\nla Casilla de Salida.");
		chanceCards.add("Avance hasta Illinois Ave\nSi pasa por la Casilla de Salida\ncobra £200.");
		chanceCards.add("Avance hasta St. Charles Place\nSi pasa por la Casilla de Salida\ncobra £200.");
		chanceCards.add("Avance hasta la casilla de Luz o Agua\nmas cercana a tu posicion.");
		chanceCards.add("Avance hasta la estacion\nmas cercana a tu posicion.");
		chanceCards.add("El banco arroja un dividendo\na su favor de £150.");
		for (Player p : players)
			if (p.getChFreeJail())
				jailCard = true;
		if (!jailCard)
			chanceCards.add("Esta carta te libra de la carcel.");
		chanceCards.add("Retroceda 3 casillas.");
		chanceCards.add("Vaya directamente a la Carcel\nsi pasas por la Casilla de Salida\nno cobras £200.");
		chanceCards.add("Haga reparaciones en su casa:\nPaga por cada casa £25.\nPaga por cada hotel £100.");
		chanceCards.add("Pagas una tasa de £15.");
		chanceCards.add("Avance hasta Reading Railroad\nSi pasa por la Casilla de Salida\ncobra £200.");
		chanceCards.add("Avance hasta Boardwalk.");
		chanceCards.add("Pague a cada jugador £50.");
		chanceCards.add("Ha ganado el concurso de\npalabras cruzadas\nRecibes £100.");
		chanceCards.add("Recibes del Banco £150.");

	}

	public void setComunityCards() {
		boolean jailCard = false;

		comunityCards.add("Avance hasta\nla Casilla de Salida.");
		comunityCards.add("Pague la factura del Doctor £50.");
		comunityCards.add("Ganas la partida de poker,\nrecibes de cada jugador £50.");
		comunityCards.add("Hacienda le devuelve £20.");
		comunityCards.add("Recibes de tu Seguro de Vida £100.");
		comunityCards.add("Error en la Banca\nRecibes £150.");
		for (Player p : players)
			if (p.getCoFreeJail())
				jailCard = true;
		if (!jailCard)
			comunityCards.add("Esta carta te libra de la carcel.");
		comunityCards.add("Pague al Hospital £100.");
		comunityCards.add("Vaya directamente a la Carcel\nsi pasas por la Casilla de Salida\nno cobras £200.");
		comunityCards.add("Haga reparaciones en sus calles:\nPaga por cada casa £40.\nPaga por cada hotel £115.");
		comunityCards.add("Pague la factura del Colegio £50.");
		comunityCards.add("Ganaste el segundo premio\ndel concurso de belleza.\nRecibes £10.");
		comunityCards.add("Recibes £25 por un proyecto.");
		comunityCards.add("Es su cumpleaños\nRecibes de cada jugador £10.");
		comunityCards.add("El seguro de viaje le\npaga a usted £100.");
		comunityCards.add("Recibes £100.");
		comunityCards.add("La venta de tu Stock,\n te genera  £50.");
	}

	// getters and setters

	public void setDoble(int d) {
		this.doble = d;
	}

	public int getNumhotels() {
		return numhotels;
	}

	public void setNumhotels(int numhotels) {
		this.numhotels = numhotels;
	}

	public int getNumhouses() {
		return numhouses;
	}

	public void setNumhouses(int numhouses) {
		this.numhouses = numhouses;
	}

	public ArrayList<String> getChanceCards() {
		return chanceCards;
	}

	public ArrayList<String> getComunityCards() {
		return comunityCards;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<TileGame> getTilesGame() {
		return tilesGame;
	}

	public JButton getBtnTerminar() {
		return btnTerminar;
	}

	public JButton getBtnOfrecer() {
		return btnBuySell;
	}

	public JLabel getLbMoney() {
		return lbMoney;
	}

	public JButton getBtnHipotecar() {
		return btnHipotecar;
	}

	public JButton getBtnDeshipotecar() {
		return btnDeshipotecar;
	}

	public JButton getBtnAddHouse() {
		return btnAddHouse;
	}

	@SuppressWarnings("rawtypes")
	public JList[] getPropertiesList() {
		return propertiesList;
	}
}
