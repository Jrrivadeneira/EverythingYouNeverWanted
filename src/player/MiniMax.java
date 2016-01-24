package player;

import java.util.LinkedList;

public class MiniMax {
	
	MiniMax parent;
	LinkedList<MiniMax> children;
	BoardState board;
	int alpha;
	int beta;
	int score;
	boolean isTerminal;
	boolean isMax;
	int depth;
	
	//root node
	MiniMax(BoardState board, boolean isMax) {
		this.parent = null;
		this.children = new LinkedList<MiniMax>();
		this.board = board;
		this.alpha = -2000000000;
		this.beta = 2000000000;
		this.score = 0;
		this.isTerminal = false;
		this.isMax = isMax;
		this.depth = 0;
		
	}
	
	//other nodes
	MiniMax(BoardState board, boolean isMax, int alpha, int beta, MiniMax parent) {
		this.parent = parent;
		this.children = new LinkedList<MiniMax>();
		this.board = board;
		this.alpha = alpha;
		this.beta = beta;
		this.score = parent.getScore();
		this.isTerminal = true;
		this.isMax = isMax;
		this.depth = 0;
		
	}

	public MiniMax run() {
		
		if (this.parent != null) {
			this.alpha = this.parent.alpha;
			this.beta = this.parent.beta;
		}
		
		//generate children for nodes without
		if (this.children.size() == 0) {
			for (int i = -1*this.board.getWidth(); i < this.board.getWidth(); i++) {
				
				//check dropping moves
				if (i < 0) {
					
					if (this.board.isValidMove(i + 1, true)) {
						
						this.children.add(this.spawnChild(this.board.drop(i)));
						
					}
					
				}
				//check placing moves
				if (i > 0) {
					
					if (this.board.isValidMove(i-1, false)) {
						
						this.children.add(this.spawnChild(this.board.place(i)));
						
					}
					
				}
				
				//run alpha-beta on children
				this.run();
				
			}
			
		//otherwise run minimax with pruning
		} else {
			
			if (this.isMax) {
				
				int v = -2147483648;
				for (final MiniMax child : this.children) {
					
					v = Math.max(v, child.run().score);
					this.alpha = Math.max(this.alpha, v);
					if (this.beta <= this.alpha) {
						break;
					}
					
				}
				
				this.score = v;
				
			} else {
				
				int v = 2147483647;
				for (final MiniMax child : this.children) {
					
					v = Math.min(v, child.run().score);
					this.beta = Math.min(v, this.beta);
					if (this.beta <= this.alpha) {
						break;
					}
					
				}
				
				this.score = v;
				
			}
			
		}
		
		return this;
		
	}
	
	private MiniMax spawnChild(BoardState board) {
	
		final MiniMax newNode = this;
		
		newNode.board.calcHeuristic();
		
		return newNode;
		
	}

	private int getScore() {
		
		return this.score;
	
	}

}