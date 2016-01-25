//Authors: Chris Knapp, Connor Flanigan, Jack Rivadeneira

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

	// root node
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

	// other nodes
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

	private MiniMax run() {

		if (this.parent != null) {
			this.alpha = this.parent.alpha;
			this.beta = this.parent.beta;
		}

		// generate children for nodes without
		if (this.children.size() == 0) {
			for (int i = -1 * this.board.getWidth(); i < this.board.getWidth(); i++) {

				// check dropping moves
				if (i < 0) {

					if (this.board.isValidMove(-i - 1, true)) {

						this.children.add(this.spawnChild(this.board.drop(i)));

					}

				}
				// check placing moves
				if (i > 0) {

					if (this.board.isValidMove(i - 1, false)) {

						this.children.add(this.spawnChild(this.board.place(i)));

					}

				}

			}

			// run alpha-beta on children
			//this.run();

			// otherwise run minimax with pruning
		} else {

			if (this.isMax) {

				int v = -2147483648;
				for (final MiniMax child : this.children) {

					v = Math.max(v, child.score);
					this.alpha = Math.max(this.alpha, v);
					if (this.beta <= this.alpha) {
						break;
					}

				}

				this.score = v;

			} else {

				int v = 2147483647;
				for (final MiniMax child : this.children) {

					v = Math.min(v, child.score);
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

	public int maxTheMin() {

		this.run();

		int bestScore = -100;
		MiniMax bestMove = null;

		for (final MiniMax node : this.children) {
			if (node.score > bestScore) {
				bestScore = node.score;
				bestMove = node;
			}
		}

		return this.calcMove(bestMove);

	}

	private int calcMove(MiniMax bestMove) {

		for (int i = 0; i < this.board.size[0]; i++) {
			for (int j = this.board.size[1] - 1; j >= 0; j--) {
				final int diff = this.board.board[i][j]
						- bestMove.board.board[i][j];
				if (diff != 0) {
					return (int) (Math.signum(diff) * i);
				}
			}
		}

		return 0;
	}

	private MiniMax spawnChild(BoardState board) {

		final MiniMax newNode = this;

		this.score = newNode.board.calcHeuristic();

		return newNode;

	}

	private int getScore() {

		return this.score;

	}

}