package player;

public class BoardState {

	//     empty cell : 0
	//  our team cell : 1
	//their team cell : 2
	byte[][]  board;
	boolean isOurTurn;
	boolean canIDrop;
	boolean canTheyDrop;
	int[] size;
	
	BoardState(int width, int height, boolean start) {
		
		this.board = new byte[width][height];
		this.isOurTurn = start;
		this.canIDrop = true;
		this.canTheyDrop = true;
		this.size = new int[] {width, height};
		
	}
	
	BoardState(byte[][] board, boolean ourTurn, boolean us, boolean them, int[] size) {
		
		this.board = board;
		this.isOurTurn = ourTurn;
		this.canIDrop = us;
		this.canTheyDrop = them;
		this.size = size;
		
	}
	
	boolean isValidMove(int position, boolean drop) {
		
		//if the move is a drop
		if (drop) {
			
			//have they dropped yet?
			if((isOurTurn && !this.canIDrop) || (!isOurTurn && !this.canTheyDrop)) {
				return false;
			}
			
			final byte chip = this.board[position][0];
			
			//the move is valid if it's our piece and our turn or the other way around
			return (((chip == 1) && this.isOurTurn) || ((chip == 2) && !this.isOurTurn));
			
		//if it's not a drop
		} else {
			
			//the move is valid if the top row is empty
			if (this.board[position][this.size[1]-1] != 0) {
				return true;
			}
			
		}
		
		return false;
		
	}
	
}
