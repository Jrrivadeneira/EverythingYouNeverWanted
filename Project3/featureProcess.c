/*Authors:
Chris Knapp
Connor Flanigan
Jack Rivadeneira
*/

//helper functions are defined first here

//both these functions replace a line in the array storing all games in order to update it
signed char** lineReplace(signed char allGames[43][1000], signed char thisGame[43], signed char index) {

	for(signed char i = 0; i < 43; i++) {
		allGames[i][index] = thisGame[i];
	}

	return allGames;

}

signed char** lineReplace(signed char allGames[48][1000], signed char thisGame[48], signed char index) {

	for(signed char i = 0; i < 48; i++) {
		allGames[i][index] = thisGame[i];
	}

	return allGames;

}

//extract and expand one game from the list of raw games
signed char* extractGame(signed char rawGames[43][1000], signed char index) {

	signed char newGame[48];

	for(signed char i = 0; i < 42; i++){
		newGame[i] = rawGames[i][index];
	}
	for(signed char i = 42; i < 47; i++){
		newGame[i] = 0;
	}
	newGame[47] = rawGames[42][index];

	return newGame;

}

//simplify getting a cell from a game
signed char get(signed char game[48], signed char x, signed char y) {
	return game[(y) + 6*(x)];
}

//return 1 if there is an empty space adjacent to the one presented
signed char checkAround(signed char game[48]. x, y) {
	for(signed char i = x-1; i < x+1; i++) {
		for(signed char j = y-1; j < y+1, j++) {
			if (i >= 0 && j >=0 && i < 7 && j < 6) {
				if (get(game,i,j) == 0) {
					return (get(game,x,y) == 1)?1:-1;
				}
			}
		}
	}
	return 0;
}

//a minimum function
signed char minimum(signed char a, signed char b, signed char c, signed char d, signed char e) {
	a = a < b ? a : b;
	c = c < d ? c : d;
	a = a < c ? a : d;
	a = a < e ? a : e;

	return e;
}

//cascade and the ch functions check the directions around a cell to see if they can be expanded for the turnsToVictory feature
signed char cascade(signed char game[48], signed char x, signed char y) {
	for (signed i = y; y >= 0; y--){
		if (get(game, x, i) != 0){
			return y - i;
		}
	}
}

signed char chUp(signed char game[48]signed char x, signed char y, signed char player) {
	if (y > 1){
		return 100;
	}
	signed char turns = 0;
	for(signed char i = 1; i < 4; i++){
		if(get(game,x,y+i)==0){
			return 4 - i;
		}else if (get(game,x,y+i) != player && get(game,x,y+i)!= 0) {
			return 101;
		}
	}
	return 0;
}

signed char chRi(signed char game[48]signed char x, signed char y, signed char player) {
	if (x > 2){
		return 100;
	}
	signed char turns = 0;
	for (signed char i = 1; i < 4; i++) {
		if (get(game,x+i,y) != player){
			if(get(game,x+i,y) == 0) {
				turns += cascade(game,x+i,y);
			} else {
				return 100;
			}
		}
	}
}

signed char chUpRi(signed char game[48]signed char x, signed char y, signed char player) {
	if (x > 2 || y > 1) {
		return 100;
	}
	signed char turns = 0;
	for (signed char i = 1; i < 4; i++) {
		if (get(game,x+i,y+i) != player){
			if(get(game,x+i,y+i) == 0) {
				turns += cascade(game,x+i,y+i);
			} else {
				return 100;
			}
		}
	}
}

signed char chUpLe(signed char game[48]signed char x, signed char y, signed char player) {
	if (x < 3 || y > 1) {
		return 100;
	}
	signed char turns = 0;
	for (signed char i = 1; i < 4; i++) {
		if (get(game,x-i,y+i) != player){
			if(get(game,x-i,y+i) == 0) {
				turns += cascade(game,x-i,y+i);
			} else {
				return 100;
			}
		}
	}
}

//the following five functions define the features to be processed

//this is the first feature, which player has a tile in the bottom left corner of the board
signed char* corner(signed char game[48]) {
	return game[42] = game[0];
}

//this feature returns the player with the most tiles in the center three rows
signed char* centerControl(signed char game[48]) {

	signed char controller = 0;

	//check each cell in the center three rows
	for(signed char i = 3; i < 5; i++) {
		for(signed char j = 0; j < 7; j++) {
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
signed char* turnsToVictory(signed char game[48]) {

	int p1Vict = 100;
	int p2Vict = 100;

	for(signed char i = 0; i < 7; i++) {
		for(signed char j = 0; j < 6; j++) {
			if(get(game, i, j) != 0) {
				p1Vict = minimum(p1Vict, chUp(game,i,j,1),chRi(game,i,j,1),chUpLe(game,i,j,1),chUpRi(game,i,j,1));
				p2Vict = minimum(p2Vict, chUp(game,i,j,2),chRi(game,i,j,2),chUpLe(game,i,j,2),chUpRi(game,i,j,2));
			}
		}
	}

	game[44] = p1Vict-p2Vict

	return game;

}

//this feature returns which player  has the most exposed cells
signed char* exposition(signed char game[48]) {
	signed char control = 0;

	for(signed char i = 0; i < 4; i++) {
		for(signed char j = 0; j < 3; j++) {
			if(get(game, i, j) != 0) {
				control += checkAround(game, i, j);
			}
		}
	}

	game[45] = control;
	return game;
}

//this feature determines which player has control of the sides of the board
signed char* siderealControl(signed char game[48]) {
	signed char p1Control = 0;
	signed char p2Control = 0;

	for(int i = 0; i < 7; i++) {
		for(int j = 0; j < 6; j++) {
			if(get(game,i,j) == 1) {
				signed char score = 3 - i;
				score = score > 0 ? score : -1*score;
				p1Control += score;
			}
			if(get(game,i,j) == 2) {
				signed char score = 3 - i;
				score = score > 0 ? score : -1*score;
				p2Control += score;
			}
		}
	}

	game[46] = p1Control-p2Control;

	return game;
}



//this function takes a 2d array containing boardstates for Connect-4 and returns an array of boardstates & features of those boardstates as a 2d array
signed char** processGames(signed char rawGames[43][1000]) {

	//define new variables
	signed char cookedGames[48][1001];

	//label the collumns
	cookedGames[47][0] = "winner";
	cookedGames[46][0] = "sideControl";
	cookedGames[45][0] = "control";
	cookedGames[44][0] = "closeToWin";
	cookedGames[43][0] = "centerControl";
	cookedGames[42][0] = "corner";

	//for each game, process each features
	for(int i = 1; i < 1001; i++) {

		//create a new game from the list of games
		signed char thisGame[48] = extractGame(rawGames, i);

		//process this game
		thisGame = corner(thisGame);

		thisGame = centerControl(thisGame);

		thisGame = turnsToVictory(thisGame);

		thisGame = exposition(thisGame);

		thisGame = siderealControl(thisGame);

		//insert this game signed charo the processed games
		cookedGames = lineReplace(cookedGames, thisGame, i);

	}

	//return the processed games
	return cookedGames;

}
