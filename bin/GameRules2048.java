//*********************************************************
// Name: Chang-Syuan Wu		
// Date: Jan 17, 2018
//
// Purpose: 
//		Java program to play the 2048 game
//
// Methods:
//		move(String) - Moves the board in the direction provided by the String value and combines equal numbers. Returns a boolean indicating if the board was changed by this move
//		isTileEmpty(int, int) - Returns a boolean indicating if the tile at the location provided is empty
//		generateTile() - Adds a new tile with a value of 2 or 4 in a random empty space on the board. Returns an int array containing the value and location of the new tile
//		updateBoard(int, int, int) - Sets the value of the tile at the location provided to the provided value
//		resetGame() - Resets the board to empty, score to 0, and moveable state of the board to true
//		getTile(int, int) - Returns the int value of the tile at the location provided
//		toString() - Returns a String containing the current state of the board
//		getScore() - Returns the score
//		incrementScore(int) - Increases the score by the int value provided
//		getBestScore() - Returns the best score
//		isGameOver() - Returns a boolean indicating if there are no longer any possible moves
//		isWin() - Returns a boolean indicating if there is a tile with the value of 2048
//		loadCheatBoard - Sets the state of the board to match the cheat board
//
// Notes:
//		- Stated values for some booleans in if statements to improve readability
//
//*********************************************************

package game2048;

import java.util.concurrent.ThreadLocalRandom;

public class GameRules2048 {

	// Create 2d array representing the board
	private int[][] board = new int[4][4];

	// Test/cheat board for starting the board with certain tiles
	private int[][] cheatBoard = new int[][]{
		{ 1024, 1024, 0, 0 },
		{ 0, 0, 0, 0 },
		{ 0, 0, 0, 0 },
		{ 0, 0, 0, 0 }
	};

	// Create variables for storing the score
	private int score = 0, bestScore = 0;

	// Variable for storing the movable state of the board
	private boolean moveable = true;

	// Constructor
	public GameRules2048() {

		// Initialize the board
		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < 4; k++) {
				board[i][k] = 0;
			}
		}
	}

	public void loadCheatBoard() {

		// Set the board state to match the cheat board's state
		board = cheatBoard;
	}

	public boolean move(String direction) {

		// Check if the board is allowed to move
		if (moveable == false) {
			return false;
		}

		// Store the current state of the board in a variable
		int[][] oldBoard = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				oldBoard[i][j] = board[i][j];
			}
		}

		// Create a 2d array of booleans representing the board and initialize it to false
		boolean[][] wasTileCombinedThisTurn = new boolean[4][4];
		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < 4; k++) {
				wasTileCombinedThisTurn[i][k] = false;
			}
		}


		switch (direction) {

		case "up":

			// Take each column and start from the 2nd row from the top, going down
			for (int col = 0; col < 4; col++) {

				for (int row = 1; row < 4; row++) {

					// Select only non-zero values which are not on the top-most row
					if (board[row][col] != 0 && row != 0) {

						// If the tile above is equal and neither it nor the current tile were combined this turn, combine them
						if (board[row - 1][col] == board[row][col] && (wasTileCombinedThisTurn[row - 1][col] == false) && (wasTileCombinedThisTurn[row][col] == false)) {
							board[row - 1][col] = board[row - 1][col] * 2;
							board[row][col] = 0;

							// Set the "was combined this turn" flag
							wasTileCombinedThisTurn[row - 1][col] = true;

							// Increment the score
							incrementScore(board[row - 1][col]);
						}

						// Move value up if space above is empty (is 0)
						if (board[row - 1][col] == 0) {
							board[row - 1][col] = board[row][col];
							board[row][col] = 0;

							// Move the "was combined this turn" flag
							if (wasTileCombinedThisTurn[row][col] == true) {
								wasTileCombinedThisTurn[row - 1][col] = true;
								wasTileCombinedThisTurn[row][col] = false;
							}

							// Make program check the value that was shifted up by one row on the next loop 
							row = row - 2;
						}
					}
				}
			}
			break;

		case "down":

			// Take each column and start from the 2nd row from the bottom, going up
			for (int col = 0; col < 4; col++) {

				for (int row = 2; row >= 0; row--) {

					// Select only non-zero values which are not on the bottom-most row
					if (board[row][col] != 0 && row != 3) {

						// If the tile below is equal and neither it nor the current tile were combined this turn, combine them
						if (board[row + 1][col] == board[row][col] && (wasTileCombinedThisTurn[row + 1][col] == false) && (wasTileCombinedThisTurn[row][col] == false)) {
							board[row + 1][col] = board[row + 1][col] * 2;
							board[row][col] = 0;

							// Set the "was combined this turn" flag
							wasTileCombinedThisTurn[row + 1][col] = true;

							// Increment the score
							incrementScore(board[row + 1][col]);
						}

						// Move value down if space below is empty (is 0)
						if (board[row + 1][col] == 0) {
							board[row + 1][col] = board[row][col];
							board[row][col] = 0;

							// Move the "was combined this turn" flag
							if (wasTileCombinedThisTurn[row][col] == true) {
								wasTileCombinedThisTurn[row + 1][col] = true;
								wasTileCombinedThisTurn[row][col] = false;
							}

							// Make program check the value that was shifted down by one row on the next loop 
							row = row + 2;
						}
					}
				}
			}
			break;

		case "left":

			// Take each row and start from the 2nd column from the left, going right
			for (int row = 0; row < 4; row++) {

				for (int col = 1; col < 4; col++) {

					// Select only non-zero values which are not in the leftmost column
					if (board[row][col] != 0 && col != 0) {

						// If the tile to the left is equal and neither it nor the current tile were combined this turn, combine them
						if (board[row][col - 1] == board[row][col] && (wasTileCombinedThisTurn[row][col - 1] == false) && (wasTileCombinedThisTurn[row][col] == false)) {
							board[row][col - 1] = board[row][col - 1] * 2;
							board[row][col] = 0;

							// Set the "was combined this turn" flag
							wasTileCombinedThisTurn[row][col - 1] = true;

							// Increment the score
							incrementScore(board[row][col - 1]);
						}

						// Move value left if space to the left is empty (is 0)
						if (board[row][col - 1] == 0) {
							board[row][col - 1] = board[row][col];
							board[row][col] = 0;

							// Move the "was combined this turn" flag
							if (wasTileCombinedThisTurn[row][col] == true) {
								wasTileCombinedThisTurn[row][col - 1] = true;
								wasTileCombinedThisTurn[row][col] = false;
							}

							// Make program check the value that was shifted left by one column on the next loop 
							col = col - 2;
						}
					}
				}
			}
			break;

		case "right":

			// Take each row and start from the 2nd column from the right, going left
			for (int row = 0; row < 4; row++) {

				for (int col = 2; col >= 0; col--) {

					// Select only non-zero values which are not in the rightmost column
					if (board[row][col] != 0 && col != 3) {

						// If the tile to the right is equal and neither it nor the current tile were combined this turn, combine them
						if (board[row][col + 1] == board[row][col] && (wasTileCombinedThisTurn[row][col + 1] == false) && (wasTileCombinedThisTurn[row][col] == false)) {
							board[row][col + 1] = board[row][col + 1] * 2;
							board[row][col] = 0;

							// Set the "was combined this turn" flag
							wasTileCombinedThisTurn[row][col + 1] = true;

							// Increment the score
							incrementScore(board[row][col + 1]);
						}

						// Move value right if space to the right is empty (is 0)
						if (board[row][col + 1] == 0) {
							board[row][col + 1] = board[row][col];
							board[row][col] = 0;

							// Move the "was combined this turn" flag
							if (wasTileCombinedThisTurn[row][col] == true) {
								wasTileCombinedThisTurn[row][col + 1] = true;
								wasTileCombinedThisTurn[row][col] = false;
							}

							// Make program check the value that was shifted right by one column on the next loop 
							col = col + 2;
						}
					}
				}
			}
			break;
		}

		// Determine whether the board has been changed by this move and exit with the corresponding boolean
		int identicalCounter = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == oldBoard[i][j]) {
					identicalCounter++;
				}
			}
		}
		if (identicalCounter == 16) {
			return false;
		} else {
			return true;
		}
	}

	public int[] generateTile() {

		// Declare/Initialize variables
		int emptyTiles = 0, tileCounter = 0, tileRow = 0, tileColumn = 0, randTileNum;
		int[] tileValueAndLocation;

		// Get a random decimal number between 0 and 1
		double random = Math.random();

		// Count how many empty tiles there are
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (isTileEmpty(i, j)) {
					emptyTiles++;
				}
			}
		}

		// Choose a random number between 1 and the number of empty tiles there are
		randTileNum = ThreadLocalRandom.current().nextInt(1, emptyTiles + 1);

		// Find the location of the #th empty tile
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {

				if (isTileEmpty(i, j)) {
					tileCounter++;

					if (tileCounter == randTileNum) {
						tileRow = i;
						tileColumn = j;
					}
				}
			}
		}

		// Store the tile location information in an array
		tileValueAndLocation = new int[3];
		tileValueAndLocation[1] = tileRow;
		tileValueAndLocation[2] = tileColumn;

		// 90% of spawning a "2" tile
		if (random <= 0.9) {

			// Set the tile value to 2
			tileValueAndLocation[0] = 2;
		}
		// 10% chance of spawning a "4" tile
		else {

			// Set the tile value to 4
			tileValueAndLocation[0] = 4;
		}

		// Update the board with the new tile
		updateBoard(tileValueAndLocation[1], tileValueAndLocation[2], tileValueAndLocation[0]);

		// Return the value and location of the tile
		return tileValueAndLocation;
	}

	public void updateBoard(int row, int col, int value) {
		// Set the value of the provided tile to the provided value
		board[row][col] = value;
	}

	public void resetGame() {

		// Reset the board to it's initial state
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				board[i][j] = 0;
			}
		}

		// Reset the score
		score = 0;

		// Reset the moveable status
		moveable = true;
	}

	public void incrementScore(int addedScore) {

		// Add to score and if new score is higher than best score, update best score
		score += addedScore;

		if (score > bestScore) {
			bestScore = score;
		}
	}

	public boolean isTileEmpty(int row, int col) {

		// If the provided tile is not empty, return false
		if (board[row][col] != 0) {
			return false;
		} 

		// Else, return true
		return true;
	}

	public boolean isGameOver() {

		// Loop through each tile of the board
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {

				// If the tile is empty, it is not game over
				if (isTileEmpty(row, col)) {
					return false;
				}

				// Check whether tile above can be combined, if it can, it is not game over
				if (row - 1 >= 0) {
					if (board[row - 1][col] == board[row][col]) {
						return false;
					}
				}

				// Check whether tile below can be combined, if it can, it is not game over
				if (row + 1 <= 3) {
					if (board[row + 1][col] == board[row][col]) {
						return false;
					}
				}

				// Check whether tile to the left can be combined, if it can, it is not game over
				if (col - 1 >= 0) {
					if (board[row][col - 1] == board[row][col]) {
						return false;
					}
				}

				// Check whether tile to the right can be combined, if it can, it is not game over
				if (col + 1 <= 3) {
					if (board[row][col + 1] == board[row][col]) {
						return false;
					}
				}
			}
		}

		// If there are no empty tiles and no valid moves, it is game over
		moveable = false;
		return true;
	}

	public boolean isWin() {
		// If there is a number that is equal to or greater than 2048, the player has won
		for (int i = 0; i < 4; i++) {
			for (int h = 0; h < 4; h++) {

				if (board[i][h] >= 2048) {
					moveable = false;
					return true;
				}
			}
		}

		// Else, the player has not won yet
		return false;
	}

	public int getTile(int row, int col) {
		// Return the provided tile's value
		return board[row][col];
	}

	public int getScore() {
		return score;
	}

	public int getBestScore() {
		return bestScore;
	}

	public String toString() {

		// Store the state of the board in a String and return it
		String returnString = "";

		for (int i = 0; i < 4; i++) {
			for (int h = 0; h < 4; h++) {

				returnString += board[i][h];

				if (h != 3) {
					returnString += ", ";
				}
			}

			if (i != 3) {
				returnString += "\n";
			}
		}

		return returnString;
	}
}
