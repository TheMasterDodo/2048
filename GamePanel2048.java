//*********************************************************
// Name: Chang-Syuan Wu		
// Date: Jan 17, 2018
//
// Purpose: 
//		Java program to play the 2048 game
//
// Methods:
//		findTileColor(int) - Returns a Color object containing the tile background color of the int value
//		findFontColor(int) - Returns a Color object containing the tile font color of the int value
//		actionPerformed(ActionEvent) - Determines which button (Exit or New Game/Try Again) is pressed
//		resetGame() - Resets the board and starts a new game
//		generateTile() - Adds a new tile with a value of 2 or 4 in a random empty space on the board
//		updateTiles() - Updates the board on the GUI to match the board in the rules
//		move(String) - Moves the board in the direction provided by the String value
//
// Notes:
//		- Stated values for some booleans in if statements to improve readability
//		- Use the boolean variable `cheat` to set whether to use the cheatBoard in GameRules as the initial board
//
//*********************************************************

package game2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

public class GamePanel2048 extends JLayeredPane implements ActionListener{

	// Default serial version UID
	private static final long serialVersionUID = 1L;

	// Declare a rules instance
	private GameRules2048 rules2048;

	// Set up constants for colour of tiles
	private int[] tileValues = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
	private Color[] tileColors = {Color.decode("#E8E0D8"), Color.decode("#E8DFC8"), Color.decode("#EFAF78"), Color.decode("#F09060"), Color.decode("#F07858"), Color.decode("#EF5837"), Color.decode("#E8C870"), Color.decode("#E8C860"), Color.decode("#E8C850"), Color.decode("#E8C038"), Color.decode("#E8C028")};
	static final Color emptyTileColor = Color.decode("#CDC1B4");

	// Model
	private JLabel[][] tile;
	private JLabel boardLabel, titleLabel, scoreLabelUpper, scoreLabelMiddle, scoreLabelLower, bestScoreLabelUpper, bestScoreLabelMiddle, bestScoreLabelLower, simpleInstructionsLabel, detailedInstructionsLabel, winLabel, gameOverLabel;
	private JButton newGameButton, exitButton, tryAgainButton;

	public GamePanel2048() {

		// Create new instance of GameRules2048
		rules2048 = new GameRules2048();

		// Use exact locations instead of layout manager
		setLayout(null);

		// Add title
		titleLabel = new JLabel("2048");
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleLabel.setVerticalAlignment(SwingConstants.TOP);
		titleLabel.setFont(new Font("Clear Sans", Font.BOLD, 70));
		titleLabel.setForeground(Color.decode("#776E64"));
		titleLabel.setBounds(25, 15, 200, 70);
		add(titleLabel);

		// Add score counter
		scoreLabelUpper = new JLabel("SCORE");
		scoreLabelUpper.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabelUpper.setVerticalAlignment(SwingConstants.BOTTOM);
		scoreLabelUpper.setFont(new Font("Clear Sans", Font.BOLD, 12));
		scoreLabelUpper.setForeground(Color.decode("#EEE4DA").brighter());
		scoreLabelUpper.setBackground(Color.decode("#BBADA0"));
		scoreLabelUpper.setBounds(385, 30, 65, 20);
		scoreLabelUpper.setOpaque(true);
		add(scoreLabelUpper);

		scoreLabelMiddle = new JLabel("");
		scoreLabelMiddle.setBackground(Color.decode("#BBADA0"));
		scoreLabelMiddle.setBounds(385, 50, 65, 3);
		scoreLabelMiddle.setOpaque(true);
		add(scoreLabelMiddle);

		scoreLabelLower = new JLabel("0");
		scoreLabelLower.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabelLower.setVerticalAlignment(SwingConstants.TOP);
		scoreLabelLower.setFont(new Font("Clear Sans", Font.BOLD, 20));
		scoreLabelLower.setForeground(Color.decode("#FFFFFF"));
		scoreLabelLower.setBackground(Color.decode("#BBADA0"));
		scoreLabelLower.setBounds(385, 53, 65, 30);
		scoreLabelLower.setOpaque(true);
		add(scoreLabelLower);

		// Add best score counter
		bestScoreLabelUpper = new JLabel("BEST");
		bestScoreLabelUpper.setHorizontalAlignment(SwingConstants.CENTER);
		bestScoreLabelUpper.setVerticalAlignment(SwingConstants.BOTTOM);
		bestScoreLabelUpper.setFont(new Font("Clear Sans", Font.BOLD, 12));
		bestScoreLabelUpper.setForeground(Color.decode("#EEE4DA").brighter());
		bestScoreLabelUpper.setBackground(Color.decode("#BBADA0"));
		bestScoreLabelUpper.setBounds(460, 30, 65, 20);
		bestScoreLabelUpper.setOpaque(true);
		add(bestScoreLabelUpper);

		bestScoreLabelMiddle = new JLabel("");
		bestScoreLabelMiddle.setBackground(Color.decode("#BBADA0"));
		bestScoreLabelMiddle.setBounds(460, 50, 65, 3);
		bestScoreLabelMiddle.setOpaque(true);
		add(bestScoreLabelMiddle);

		bestScoreLabelLower = new JLabel("0");
		bestScoreLabelLower.setHorizontalAlignment(SwingConstants.CENTER);
		bestScoreLabelLower.setVerticalAlignment(SwingConstants.TOP);
		bestScoreLabelLower.setFont(new Font("Clear Sans", Font.BOLD, 20));
		bestScoreLabelLower.setForeground(Color.decode("#FFFFFF"));
		bestScoreLabelLower.setBackground(Color.decode("#BBADA0"));
		bestScoreLabelLower.setBounds(460, 53, 65, 30);
		bestScoreLabelLower.setOpaque(true);
		add(bestScoreLabelLower);

		// Add board background
		boardLabel = new JLabel("");
		boardLabel.setBackground(Color.decode("#BBADA0"));
		boardLabel.setBounds(25, 270, 500, 500);
		boardLabel.setOpaque(true);
		add(boardLabel);

		// Add 4x4 board of tiles
		tile = new JLabel[4][4];
		for (int row = 0; row < 4; row++){
			for (int col = 0; col < 4; col++) {
				tile[row][col] = new JLabel(""); 
				tile[row][col].setHorizontalAlignment(SwingConstants.CENTER);
				tile[row][col].setVerticalAlignment(SwingConstants.CENTER);
				tile[row][col].setBackground(Color.decode("#CDC1B4")); 
				tile[row][col].setFont(new Font("Clear Sans", Font.BOLD, 35));
				tile[row][col].setBounds(40 + (121 * col), 285 + (121 * row), 106, 106);
				tile[row][col].setOpaque(true);
				add(tile[row][col], 2);
			}
		}

		// Add simple instructions
		simpleInstructionsLabel = new JLabel("<html>Join the numbers and get to the <strong>2048 tile!</strong></html>");
		simpleInstructionsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		simpleInstructionsLabel.setVerticalAlignment(SwingConstants.CENTER);
		simpleInstructionsLabel.setFont(new Font("Clear Sans", Font.PLAIN, 16));
		simpleInstructionsLabel.setForeground(Color.decode("#776E64"));
		simpleInstructionsLabel.setBounds(27, 120, 370, 30);
		add(simpleInstructionsLabel);

		// Add detailed instructions
		detailedInstructionsLabel = new JLabel("<html><strong>HOW TO PLAY:</strong> Use your <strong>arrow keys</strong> or <strong>WASD keys</strong> to move the tiles. When two tiles with the same number touch, they <strong>merge into one!</strong></html>");
		detailedInstructionsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		detailedInstructionsLabel.setVerticalAlignment(SwingConstants.CENTER);
		detailedInstructionsLabel.setFont(new Font("Clear Sans", Font.PLAIN, 16));
		detailedInstructionsLabel.setForeground(Color.decode("#776E64"));
		detailedInstructionsLabel.setBounds(25, 163, 360, 100);
		add(detailedInstructionsLabel);

		// Add invisible win label 
		winLabel = new JLabel("You Win!");
		winLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winLabel.setVerticalAlignment(SwingConstants.CENTER);
		winLabel.setFont(new Font("Clear Sans", Font.BOLD, 50));
		winLabel.setForeground(Color.decode("#FFFFFF"));
		winLabel.setBackground(new Color(254, 220, 1, 100));
		winLabel.setOpaque(true);
		winLabel.setBounds(25, 270, 500, 500);
		winLabel.setVisible(false);
		add(winLabel, 1);

		// Add invisible game over label 
		gameOverLabel = new JLabel("Game over!");
		gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameOverLabel.setVerticalAlignment(SwingConstants.CENTER);
		gameOverLabel.setFont(new Font("Clear Sans", Font.BOLD, 50));
		gameOverLabel.setForeground(Color.decode("#776E64"));
		gameOverLabel.setBackground(new Color(224, 213, 201, 100));
		gameOverLabel.setOpaque(true);
		gameOverLabel.setBounds(25, 270, 500, 500);
		gameOverLabel.setVisible(false);
		add(gameOverLabel, 1);

		// Add Exit button
		Font buttonFont = new Font("Clear Sans", Font.BOLD, 15);

		exitButton = new JButton("Exit");
		exitButton.setHorizontalAlignment(SwingConstants.CENTER);
		exitButton.setVerticalAlignment(SwingConstants.CENTER);
		exitButton.setFont(buttonFont);
		exitButton.setForeground(Color.decode("#FFFFFF"));
		exitButton.setBackground(Color.decode("#8F7A66"));
		exitButton.setBounds(395, 195, 130, 40);
		exitButton.setOpaque(true);
		add(exitButton);

		// Add New Game button
		newGameButton = new JButton("New Game");
		newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		newGameButton.setVerticalAlignment(SwingConstants.CENTER);
		newGameButton.setFont(buttonFont);
		newGameButton.setForeground(Color.decode("#FFFFFF"));
		newGameButton.setBackground(Color.decode("#8F7A66"));
		newGameButton.setBounds(395, 120, 130, 40);
		newGameButton.setOpaque(true);
		newGameButton.setFocusable(false);
		add(newGameButton);

		// Add invisible Try Again button
		tryAgainButton = new JButton("Try Again");
		tryAgainButton.setHorizontalAlignment(SwingConstants.CENTER);
		tryAgainButton.setVerticalAlignment(SwingConstants.CENTER);
		tryAgainButton.setFont(buttonFont);
		tryAgainButton.setForeground(Color.decode("#FFFFFF"));
		tryAgainButton.setBackground(Color.decode("#8F7A66"));
		tryAgainButton.setBounds(215, 580, 130, 40);
		tryAgainButton.setOpaque(true);
		tryAgainButton.setFocusable(false);
		tryAgainButton.setVisible(false);
		tryAgainButton.setEnabled(false);
		add(tryAgainButton, 2);

		// Add event listeners for the buttons
		exitButton.addActionListener(this);
		newGameButton.addActionListener(this);
		tryAgainButton.addActionListener(this);

		// Lay out the components
		validate();

		// Whether to load the test/cheat board defined in the rules class
		boolean cheat = true;
		if (cheat == true) {
			rules2048.loadCheatBoard();
			updateTiles();
		} else {
			// Generate the starting tiles
			generateTile();
			generateTile();
		}
	}

	public Color findTileColor(int tileValue) {

		// Find the index of the tileValue in the tile values array and return the color stored at the corresponding index in the tile colors array
		for (int i = 0; i < tileValues.length; i ++) {
			if (tileValues[i] == tileValue) {
				return tileColors[i];
			}
		}

		// If the tile value is not found, return the empty tile color
		return emptyTileColor;
	}

	public Color findFontColor(int tileValue) {

		// Return the color associated with the tile value
		if (tileValue == 2) {
			return Color.decode("#6C645D");
		} 
		else if (tileValue == 4) {
			return Color.decode("#716962");
		} 
		// If the value is not 2 or 4, the font color is white
		else {
			return Color.decode("#FFFFFF");
		}
	}

	public void actionPerformed(ActionEvent e) { 

		// Ask the event which button it represents 
		if (e.getActionCommand().equals("Exit"))  {
			System.exit(0);
		}

		if (e.getActionCommand().equals("New Game") || e.getActionCommand().equals("Try Again")){
			// Call the method to reset the game
			resetGame();
		}
	}

	public void resetGame() {

		// Reset the board in the game rules class
		rules2048.resetGame();

		// Reset the board on the panel
		for (int row = 0; row < 4; row++){
			for (int col = 0; col < 4; col++) {
				tile[row][col].setBackground(Color.decode("#CDC1B4")); 
				tile[row][col].setText("");
			}
		}

		// Reset the score counter
		scoreLabelLower.setText("0");

		// Hide the win and gameOver labels
		winLabel.setVisible(false);
		gameOverLabel.setVisible(false);
		tryAgainButton.setEnabled(false);
		tryAgainButton.setVisible(false);

		// Generate new starting tiles
		generateTile();
		generateTile();
	}

	public void generateTile() {

		// Get the values and locations of the new tiles
		int[] tileValueAndLocation = rules2048.generateTile();

		int tileValue = tileValueAndLocation[0];

		// Update states for the new tile (Set tile background to be different to notify player that this is a new tile)
		tile[tileValueAndLocation[1]][tileValueAndLocation[2]].setText(Integer.toString(tileValue));
		tile[tileValueAndLocation[1]][tileValueAndLocation[2]].setForeground(findFontColor(tileValue));
		tile[tileValueAndLocation[1]][tileValueAndLocation[2]].setBackground(Color.CYAN);

		// Update the panel
		repaint();

	}

	public void updateTiles() {

		// Update each tile with the proper text, font color, and background color
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {

				int tileValue = rules2048.getTile(i, j);

				if (tileValue == 0) {
					tile[i][j].setText("");
				} else {
					tile[i][j].setText(Integer.toString(tileValue));
				}

				tile[i][j].setForeground(findFontColor(tileValue));
				tile[i][j].setBackground(findTileColor(tileValue));

			}
		}
		
		// Set the score and best score
		scoreLabelLower.setText(Integer.toString(rules2048.getScore()));
		bestScoreLabelLower.setText(Integer.toString(rules2048.getBestScore()));

		// Update the board
		repaint();
	}

	public void move(String direction) {
		
		// Move the board in the rules class in the provided direction and update the board in the panel
		boolean hasMoved = rules2048.move(direction);
		updateTiles();
		
		// Check if the player has won
		if (rules2048.isWin()) {
			winLabel.setVisible(true);
			tryAgainButton.setEnabled(true);
			tryAgainButton.setVisible(true);
			return;
		}
		
		// If the player has moved the board, generate new tiles
		if (hasMoved) {
			generateTile();
		}

		// Check if it is game over
		if (rules2048.isGameOver()) {
			gameOverLabel.setVisible(true);
			tryAgainButton.setEnabled(true);
			tryAgainButton.setVisible(true);
		}
	}
}
