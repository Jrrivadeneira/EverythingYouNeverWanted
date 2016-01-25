//Authors: Chris Knapp, Connor Flanigan, Jack Rivadeneira

package player;

public class BoardState {

	// empty cell : 0
	// our team cell : 1
	// their team cell : 2
	byte[][] board;
	boolean isOurTurn;
	boolean canIDrop;
	boolean canTheyDrop;
	int[] size;
	int N;

	BoardState(int width, int height, int N, boolean start) {

		this.board = new byte[width][height];
		this.N = N;
		this.isOurTurn = start;
		this.canIDrop = true;
		this.canTheyDrop = true;
		this.size = new int[] { width, height };

	}

	BoardState(byte[][] board, boolean ourTurn, boolean us, boolean them, int[] size, int N) {

		this.board = board;
		this.isOurTurn = ourTurn;
		this.canIDrop = us;
		this.canTheyDrop = them;
		this.size = size;
		this.N = N;

	}

	boolean isValidMove(int position, boolean drop) {

		// if the move is a drop
		if (drop) {

			// have they dropped yet?
			if ((isOurTurn && !this.canIDrop) || (!isOurTurn && !this.canTheyDrop)) {
				return false;
			}

			final byte chip = this.board[position][0];

			// the move is valid if it's our piece and our turn or the other way
			// around
			return (((chip == 1) && this.isOurTurn) || ((chip == 2) && !this.isOurTurn));

			// if it's not a drop
		} else {

			// the move is valid if the top row is empty
			if (this.board[position][this.size[1] - 1] != 0) {
				return true;
			}

		}

		return false;

	}

	public BoardState drop(int position) {

		final BoardState newBoard = this;

		for (int i = 0; i < this.getHeight() - 1; i++) {

			newBoard.board[position][i] = newBoard.board[position][i + 1];

		}

		newBoard.board[position][this.getHeight() - 1] = 0;

		if (newBoard.isOurTurn) {
			newBoard.canIDrop = false;
		} else {
			newBoard.canTheyDrop = false;
		}

		newBoard.isOurTurn = !newBoard.isOurTurn;

		return newBoard;

	}

	public BoardState place(int position) {

		final BoardState newBoard = this;

		for (int i = 0; i < this.getHeight(); i++) {
			if (newBoard.board[position][i] == 0) {

				newBoard.board[position][i] = (byte) (this.isOurTurn ? 1 : 2);

			}
		}

		if (newBoard.isOurTurn) {
			newBoard.canIDrop = false;
		} else {
			newBoard.canTheyDrop = false;
		}

		newBoard.isOurTurn = !newBoard.isOurTurn;

		return newBoard;

	}
	
	//+-------+
	//|L  U  Q|
	//| L U Q |
	//|  LUQ  |
	//|000XRRR|
	//|0000000|
	//|0000000|
	//+-------+
	
	public int calcHeuristic() {
		int best = this.N;
		int worst = this.N;

		// check each cell from left to right and then down to up
		for (int i = 0; i < this.size[0]; i++) {
			for (int j = 0; j < this.size[1]; j++) {
				final boolean canLeft = (i - N) >= 0;
				final boolean canUp = (i + N) < this.size[0];
				final boolean canRight = (j + N) < this.size[1];
				
				//if it's our tile
				if (this.board[i][j] == 1) {
					
					//if there's space to match upwards
					if (canUp) {
						//if there's space to move up and left
						if (canLeft) {
							//our best score is the minimum of the best so far and each direction
							best = Math.min(this.upLeftD(i, j, 1), best);
						}
						best = Math.min(this.checkVert(i, j, 1), best);
						//if there's space to move up and right
						if (canRight) {
							best = Math.min(upRightD(i, j, 1), best);
						}
					}
					//if there's space to move just right
					if (canRight) {
						best = Math.min(checkRight(i, j, 1), best);
					}

				}
				
				//if it's their tile
				if (this.board[i][j] == 2) {

					if (canUp) {
						if (canLeft) {
							worst = Math.max(this.upLeftD(i, j, 2), worst);
						}
						worst = Math.min(this.checkVert(i, j, 2), worst);
						if (canRight) {
							worst = Math.min(upRightD(i, j, 2), worst);
						}
					}
					if (canRight) {
						worst = Math.min(checkRight(i, j, 2), worst);
					}

				}

			}
			
		}
		
		//return the number of turns until the opponent wins minus the number of turns until you win
		return worst - best;

	}

	// for the next three check methods:
	// go through the sequence of tiles, starting at one past the first check if
	// there is an opposing tile
	// replace the value of the first opposing tile with the one above it if you
	// have not dropped yet, return as invalid if also opposing
	// if more than one opposing tiles, return as invalid
	// for each empty cell, add the number of empty cells under that one
	// inclusive
	private int checkRight(int i, int j, int player) {
		int score = 0;
		
		//have we checked if we can drop during the rest of this sequence
		boolean checkDrop = player == 1 ? this.canIDrop : this.canTheyDrop;
		
		//check each tile in the sequence
		for (int x = i; x <= i + this.N; x++) {
			
			if (this.board[x][j] == player) {     //if it's the current player's tile, do nothing
				continue;
			} else if (this.board[x][j] == 0) {   //if it's empty, see above
				for (int y = j; y < 0; y--) {
					if (this.board[x][y] != 0) {  //if it's an opponent tile, move to checkDrop
						break;
					}
					score++;
				}
			} else if (checkDrop) {               //consider the tile above the first opponent tile etc
				final int tempTile = j < this.size[1] - 1 ? this.board[x][j + 1] : 0;

				if (tempTile == 0) {
					score++;
				} else if (tempTile != player) {
					return 1000;
				}

				checkDrop = false;
			} else {
				return 1000;
			}

		}

		return score;
	}

	private int upRightD(int i, int j, int player) {
		int score = 0;

		boolean checkDrop = player == 1 ? this.canIDrop : this.canTheyDrop;

		for (int x = i; x <= i + this.N; x++) {
			for (int a = j; a < j + this.N; a++) {

				if (this.board[x][a] == player) {
					continue;
				} else if (this.board[x][a] == 0) {
					for (int y = a; y < 0; y--) {
						if (this.board[x][y] != 0) {
							break;
						}
						score++;
					}
				} else if (checkDrop) {
					final int tempTile = a < this.size[1] - 1 ? this.board[x][a + 1] : 0;

					if (tempTile == 0) {
						score++;
					} else if (tempTile != player) {
						return 1000;
					}

					checkDrop = false;
				} else {
					return 1000;
				}

			}
		}

		return score;

	}

	private int upLeftD(int i, int j, int player) {
		int score = 0;

		boolean checkDrop = player == 1 ? this.canIDrop : this.canTheyDrop;

		for (int x = i; x >= i - this.N; x--) {
			for (int a = j; a < j + this.N; a++) {

				if (this.board[x][a] == player) {
					continue;
				} else if (this.board[x][a] == 0) {
					for (int y = a; y < 0; y--) {
						if (this.board[x][y] != 0) {
							break;
						}
						score++;
					}
				} else if (checkDrop) {
					final int tempTile = a < this.size[1] - 1 ? this.board[x][a + 1] : 0;

					if (tempTile == 0) {
						score++;
					} else if (tempTile != player) {
						return 1000;
					}

					checkDrop = false;
				} else {
					return 1000;
				}

			}
		}

		return score;

	}

	private int checkVert(int i, int j, int player) {

		int best = this.N - 1;

		// check each cell above the given coordinate
		for (int y = j + 1; y < this.size[1]; y++) {

			if (this.board[i][y] == player) { // if this tile is mine, one turn
												// closer to victory
				best--;
			} else if (this.board[i][y] == 0) { // if it's empty, return the
												// number of tiles left until
												// you win
				return best;
			} else { // if an opponents tile is on top of yours, no vertical
						// sequence
				return 1000;

			}

		}

		return 0;

	}

	private int getHeight() {
		return this.size[1];
	}

	public int getWidth() {
		return this.size[0];
	}

}
