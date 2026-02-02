package monPackage;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class WhacAMole {
	int boardWidth = 600;
	int boardHeight = 650; // 50px en plus pour l'écriture
	
	JFrame frame = new JFrame("Mario: Whack A Mole");
	JLabel textLabel = new JLabel();
	JPanel textPanel = new JPanel();
	JPanel boardPanel = new JPanel();
	
	JButton[] board = new JButton[9];
	
	ImageIcon moleIcon;
	ImageIcon plantIcon;
	
	JButton currMoleTile;
	JButton currPlantTile;
	
	Random random = new Random();
	Timer setMoleTimer;
	Timer setPlantTimer;
	
	int score = 0;
	
	
	WhacAMole() {
		frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		textLabel.setText("Score: 0");
		textLabel.setOpaque(true);
		
		textPanel.setLayout(new BorderLayout());
		textPanel.add(textLabel);
		frame.add(textPanel, BorderLayout.NORTH);
		
		boardPanel.setLayout(new GridLayout(3,3));
		// boardPanel.setBackground(Color.black);
		frame.add(boardPanel);
		
		Image plantImg = new ImageIcon(getClass().getResource("./img/piranha.png")).getImage();
		plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
		
		Image moleImg = new ImageIcon(getClass().getResource("./img/monty.png")).getImage();
		moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
		
		for (int i = 0; i < 9; i++) {
			JButton tile = new JButton();
			board[i] = tile;
			boardPanel.add(tile);
			tile.setFocusable(false);
			// tile.setIcon(moleIcon);
			tile.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent e) {
					JButton tile = (JButton) e.getSource();
					if (tile == currMoleTile) {
						score += 10;
						textLabel.setText("Score :" + Integer.toString(score));
					} else if (tile == currPlantTile) {
						textLabel.setText("Game Over :" + Integer.toString(score));
						setMoleTimer.stop();
						setPlantTimer.stop();
						for (int i = 0; i < 9; i++) {
							board[i].setEnabled(false);
						}
					}
				}
			});
		}
		
		setMoleTimer = new Timer(1000, new ActionListener() {
			// chaque seconde une action sera performer
			public void actionPerformed(ActionEvent e) {
				if (currMoleTile != null) {
					currMoleTile.setIcon(null);
					currMoleTile = null;
				}
				
				int num = random.nextInt(9); // donne un chiffre aléatoire entre 0 et 8
				JButton tile = board[num];
				
				// Si une tile est occupé par une plante on va sur une autre tile
				if (currPlantTile == tile) return;
				
				currMoleTile = tile;
				currMoleTile.setIcon(moleIcon);
			}
		});
		
		setPlantTimer = new Timer(1000, new ActionListener() {
			// chaque seconde une action sera performer
			public void actionPerformed(ActionEvent e) {
				if (currPlantTile != null) {
					currPlantTile.setIcon(null);
					currPlantTile = null;
				}
				
				int num = random.nextInt(9); // donne un chiffre aléatoire entre 0 et 8
				JButton tile = board[num]; // Le chiffre aléatoire sera attribuer à une tile
				
				// Si une tile est occupé par monty on va sur une autre tile
				if (currMoleTile == tile) return;
				
				currPlantTile = tile; // La plante ira sur une des tiles
				currPlantTile.setIcon(plantIcon); // elle s'affichera
			}
		});
		
		// On lance le Timer pour placer la plante et monty sur les cases
		setMoleTimer.start();
		setPlantTimer.start();
		
		// On s'assure que tous les components soient load avant qu'on affiche la fenêtre
		frame.setVisible(true);
	}
}
