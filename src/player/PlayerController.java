package player;

import java.io.*;
import java.util.*;

public class PlayerController 
{
	String name = "You didn't ask for this";
	int timeLimit;
	Process p1;
	Process p2;
	BufferedWriter output;
	Scanner stdin = new Scanner(new BufferedInputStream(System.in));
	byte[][] board;
	String input = "";
	
	public void Start()
	{
		sendName();
		init();
		run();
	}
	
	public void sendName()
	{
		System.out.println(name);
	}
	
	public void init()
	{
		readConfig();
	}
	
	public void run()
	{
		
	}
	
	public void readConfig()
	{
		while(stdin.hasNext())
		{
			input = input + stdin.next();
		}
	}
	
}
