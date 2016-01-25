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
		if(isFirstTurn && isFirst)
		{
			
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
