//Authors: Chris Knapp, Connor Flanigan, Jack Rivadeneira

package player;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.util.Scanner;

public class PlayerController {
	// Team name
	String name = "You didn't ask for this";
	// Time to take a turn
	int timeLimit;
	Process p1;
	Process p2;
	BufferedWriter output;
	Scanner stdin = new Scanner(new BufferedInputStream(System.in));
	byte[][] board;
	BoardState boardState;
	String input = "";
	Boolean isFirst = false;
	int boardHeight;
	int boardWidth;
	int numPieces;

	// Heuristics controller that evaluates moves
	MiniMax heur;

	public PlayerController(String playerName, int limit) {
		this.name = playerName;
		this.timeLimit = limit;
	}

	public static void main(String[] args) {
		final PlayerController pc = new PlayerController("Dude", 1);
		pc.Start();
	}

	public void Start() {
		sendName();
		init();
		run();
	}

	// Send the referee the team name
	public void sendName() {
		System.out.println(name);
	}

	public void init() {
		readConfig();
		boardState = new BoardState(boardHeight, boardWidth, numPieces, isFirst);
	}

	public void run() {
//		say("Made it to 1");
		boolean isFirstTurn = true;
		boolean isPop = false;
		boolean isFinished = false;

		if (isFirstTurn && isFirst) {
//			say("Somehow made it here.");
			heur = new MiniMax(boardState, true);
			int move = heur.maxTheMin();
			move = heur.maxTheMin();
			move = heur.maxTheMin();
			move = heur.maxTheMin();

			sendMove(true, move);
			isFirstTurn = false;
		} else {
			while (!isFinished) {
//				say("here is where i think i get stuck");
				if (stdin.hasNextLine()) {
					String theirMove = "";
//					while (stdin.hasNext()) {
						theirMove = stdin.nextLine();
//					}
//					say("Do I make it here?");
					if (theirMove == "win" || theirMove == "lose"
							|| theirMove == "draw") {
						isFinished = true;
						break;
					}
					final String[] tokens = theirMove.split(" ");
					boolean didTheyPop = false;
					if (Integer.parseInt(tokens[0]) == 0) {
						didTheyPop = true;
					}
					updateBoard(didTheyPop, Integer.parseInt(tokens[1]));
					heur = new MiniMax(boardState, true);
					int move = heur.maxTheMin();
					move = heur.maxTheMin();
					move = heur.maxTheMin();
					// A negative move value signifies popping a piece at that
					// position
					if (move < 0) {
						// Get the position of the pop
						move = 0 - move;
						isPop = true;
					}
					if (isPop) {
						sendMove(false, move);
					} else {
						sendMove(true, move);
					}
				}
			}
		}
	}

	public void updateBoard(boolean isDrop, int pos) {
		if (isDrop) {
			boardState = boardState.place(pos);
		} else {
			boardState = boardState.drop(pos);
		}
	}

	public void sendMove(boolean isDrop, int pos) {
//		say("do we make it here?");
		if (isDrop) {
			System.out.print("1 " + (pos - 1));
		} else {
			System.out.print("0 " + (pos - 1));
		}
	}

	private void say(String s) {
		System.out.println(("PlayerController: " + s));
		// javax.swing.JOptionPane.showConfirmDialog(null, ("PlayerController: "
		// + s));
	}

	// Read the current board state
	public void readConfig() {
		// Take the referee's output and store it in a buffer
		input = stdin.nextLine();
		// say("Recieved: " + input);
		// Split the input into each individual element
		final String[] tokens = input.split(" ");
		boardHeight = Integer.parseInt(tokens[0]);
		boardWidth = Integer.parseInt(tokens[1]);
		numPieces = Integer.parseInt(tokens[2]);
		if (Integer.parseInt(tokens[3]) == 1) {
			isFirst = true;
		}
		timeLimit = Integer.parseInt(tokens[4]);

	}

}
