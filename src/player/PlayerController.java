package player;

import java.io.*;
import java.util.*;

public class PlayerController 
{
	//Team name
	String name = "You didn't ask for this";
	//Time to take a turn
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
	
	//Heuristics controller that evaluates moves
	MiniMax heur;
	
	public PlayerController(String playerName, int limit)
	{
		this.name = playerName;
		this.timeLimit = limit;
	}
	
	public void Start()
	{
		sendName();
		init();
		run();
	}
	
	//Send the referee the team name
	public void sendName()
	{
		System.out.println(name);
	}
	
	public void init()
	{
		readConfig();
		boardState = new BoardState(boardHeight, boardWidth, numPieces, isFirst);
	}
	
	public void run()
	{
		boolean isFirstTurn = true;
		boolean isPop = false;
		if(isFirstTurn && isFirst)
		{
			heur = new MiniMax(boardState, true);
			int move = heur.maxTheMin();

			sendMove(true, move);
			isFirstTurn = false;
		}
		else
		{
			heur = new MiniMax(boardState, true);
			int move = heur.maxTheMin();
			//A negative move value signifies popping a piece at that position
			if(move < 0)
			{
				//Get the position of the pop
				move = 0-move;
				isPop = true;
			}
			if(isPop)
			{
				sendMove(false, move);
			}
			else
			{
				sendMove(true, move);
			}
		}
		
	}
	
	public void sendMove(boolean isDrop, int pos)
	{
		if(isDrop)
		{
			System.out.println("1 "+pos);
		}
		else
		{
			System.out.println("0 "+pos);
		}
	}
	
	//Read the current board state
	public void readConfig()
	{
		//Take the referee's output and store it in a buffer
		while(stdin.hasNext())
		{
			input = input + stdin.next();
		}
		
		//Split the input into each individual element
		String[] tokens = input.split(" ");
		boardHeight = Integer.parseInt(tokens[0]);
		boardWidth = Integer.parseInt(tokens[1]);
		numPieces = Integer.parseInt(tokens[2]);
		if(Integer.parseInt(tokens[3])==1)
		{
			isFirst = true;
		}
		timeLimit = Integer.parseInt(tokens[4]);
		
	}
	
}
