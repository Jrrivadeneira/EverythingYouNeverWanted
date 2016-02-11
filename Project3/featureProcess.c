/*Authors:
Chris Knapp
Connor Flanigan
Jack Rivadeneira
*/

//helper functions are defined first here

//both these functions replace a line in the array storing all games in order to update it
int** lineReplace(int allGames[43][1000], int thisGame[43], int index) {

	for(int i = 0; i < 43; i++) {
		allGames[i][index] = thisGame[i];
	}

	return allGames;
	
}

int** lineReplace(int allGames[48][1000], int thisGame[48], int index) {

	for(int i = 0; i < 48; i++) {
		allGames[i][index] = thisGame[i];
	}

	return allGames;
	
}

//extract and expand one game from the list of raw games
int* extractGame(int rawGames[43][1000], int index) {
	
	int newGame[48];
	
	for(int i = 0; i < 42; i++){
		newGame[i] = rawGames[i][index];
	}
	for(int i = 42; i < 47; i++){
		newGame[i] = 0;
	}
	newGame[47] = rawGames[42][index];
	
	return newGame;

}

int get(int game[48], int x, int y) {
	return game[(y-1) + 6*(x-1)];
}



//the following five functions define the features to be processed

//this is the first feature, which player has a tile in the bottom left corner of the board
int* corner(int game[48]) {
	return game[42] = game[0];
}

//this feature returns the player with the most tiles in the center three rows
int* centerControl(int game[48]) {
	
	int controller = 0;
	
	//check each cell in the center three rows
	for(int i = 3; i < 5; i++) {
		for(int j = 0; j < 7; j++) {
			//add for player1,subtract for player2
			if(get(game, i, j) == 1) {
				controller++;
			}
			if(get(game, i, j) == 2) {
				controller--;
			}
		}
	}
	
	//return which player has more control of the center, or 0 if equal control is exerted
	if (controller > 0){
		game[43] = 1;
	}else if (controller < 0){
		game[43] = 2;
	}else{
		game[43] = 0;
	}
	
	return game
	
}

//this feature returns the player who requires the fewest odd number of moves until victory
int* turnsToVictory(int game[48]) {
	return game;
}

//this feature returns which player  has the most exposed cells
int* exposition(int game[48]) {
	return game;
}

//this feature determines which player has control of the sides of the board
int* siderealControl(int game[48]) {
	
}



//this function takes a 2d array containing boardstates for Connect-4 and returns an array of boardstates & features of those boardstates as a 2d array
int** processGames(int rawGames[43][1000]) {
	
	//define new variables
	int cookedGames[48][1000];
	
	//for each game, process each features
	for(int i = 0; i < 1000; i++) {
		
		//create a new game from the list of games
		int thisGame[48] = extractGame(rawGames, i);
		
		//process this game
		thisGame = corner(thisGame);
		
		thisGame = centerControl(thisGame);
		
		thisGame = turnsToVictory(thisGame);
		
		thisGame = exposition(thisGame);
		
		thisGame = siderealControl(thisGame);
		
		//insert this game into the processed games
		cookedGames = lineReplace(cookedGames, thisGame, i);
		
	}
	
	//return the processed games
	return cookedGames;
	
}