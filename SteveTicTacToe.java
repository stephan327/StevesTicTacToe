// This program will play Tic Tac Toe w the user

import java.util.Random; // needed to add some variety to the opening moves of the game


class TicTacToe {
	

	char user, comp;  // These variables will assigned either X or O, depending on who goes 1st
	char answer, ignore;
	int wins = 0, losses = 0, draws = 0; // These variables track your stats against the computer.
	int moves = 0;  // When this reaches 9 and there isn't a winner then the game is a draw
	String winner = "No";
	

	private char board[][] = { // This 2D array will be the game board
		{ 'X', 'O', 'X' },		// The initial assignment is just decoration, the 
		{ 'O', 'X', 'O' },		// board will be cleared when the game starts.
		{ 'X', 'O', 'X' }
	};

	void clearBoard() { // This method fills each position on the board with a blank space.
						// Thereby clearing the board after a game.
		for ( int i = 0 ; i < 3 ; i++) {
			for ( int i2 = 0; i2 < 3 ; i2++) {
				board[i][i2] = ' ';
			}
		}
	} // END of clearBoard method
	
	void drawBoard() {		// This method draws the current game board on the screen.
		System.out.println("\n\n\n\t\t|\t\t|");
		for (int i = 0; i < 3; i++) {
			System.out.println("\t" + board[i][0] + "\t|"
								+ "\t" + board[i][1] + "\t|"
								+ "\t" + board[i][2] + "\n\t\t|\t\t|");
			if ( i < 2) 
				System.out.println("  -------------\t| -------------\t| -------------\n\t\t|\t\t|");
		}
		System.out.println();
	} // END of drawBoard method
	
	void drawDiagram() {	//This method draws a mini gameboard on the screen to aid the player in making their move
							// It shows each available open space and its corresponding number to make it easier to pick
							// a position.  All positions that have already been taken show a "-" instead of a number.
		int ctr = 1;
		for (int i = 0; i < 3; i++) {
			System.out.print("\t\t      ");
			for (int i2 = 0; i2 < 3; i2++) {
				if ( i2 < 2)
					if (board[i][i2] == ' ') 
						System.out.print(ctr + "|");
					else
						System.out.print("-|");
				else
					if (board[i][i2] == ' ') 
						System.out.println(ctr);
					else
						System.out.println("-");
				ctr++;
			}
		}
	} // END of drawDiagram method

	boolean playOrNot() 
	throws java.io.IOException {
		do {
			if (wins + losses + draws == 0)
				System.out.println("\nWould you like to play a game of Tic Tac Toe?");
			else {
				System.out.println("Wins:" + wins + "\t   Losses:" + losses + "\tDraws:" + draws);
				System.out.println("Would you like to play another game of Tic Tac Toe?");
			}
			System.out.println("Enter y for Yes or n for No:");
			answer = (char) System.in.read();
			do {
				ignore = (char) System.in.read();
			} while(ignore != '\n');
			if (answer != 'y' && answer != 'n')
				System.out.println("\nThat is not a valid choice!\n");
		} while (answer != 'y' && answer != 'n');
		if (answer == 'y')
			return true;
		else {
			System.out.println("\nMaybe some other time then. Goodbye.");
			return false;
		}
	} // END of playOrNot method

	void whoGoesFirst() { // Determines who goes first as X, and assigns O to the other
		System.out.println("\n\nI will randomly determine who goes first....\n");
		Random random = new Random();
		int randomInt = random.nextInt(2);
		if (randomInt == 0) {
			System.out.println("I go first! I will play as the X's and you get to be the O's.\n");
			comp = 'X';
			user = 'O';
		}
		else {
			System.out.println("You go first! You will play as the X's and I get to be the O's.\n");
			user = 'X';
			comp = 'O';
		}
	} // END of whoGoesFirst method

	void playerTurn() 
	throws java.io.IOException { // Allows the player to take a position on the board
		int row = 0, column = 0, playerChoice = 0;
		boolean validMove = false;
		//char ignore;
		do {
			drawBoard();
			drawDiagram();
			System.out.println("Press g to Give Up or:");
			System.out.println("Enter the number of the position where you want to place your " + user + ":");
			playerChoice = (char) System.in.read();
			do {
				ignore = (char) System.in.read(); // discards any extra characters
			} while(ignore != '\n');
			
			if (playerChoice != 'g') {

				playerChoice -= 49;  // I wish I could explain this.  I don't know why I had to subtract 49.  I only figured it out through the
									 // judicious use of println with my variables.  My best guess is that it has something to do with the fact
									 // that I read the input as a char and assigned it to an int variable.
				if ((playerChoice < 9) && (playerChoice >= 0)) {
					
					row = playerChoice / 3;		// This part I was kind of proud of.  I almost resorted to making the board a 1-dimensional
					column = playerChoice % 3;	// array but then I realized that dividing by the row length would give me the the row, while the
												// remainder would give me the column.
					if (board[row][column] == ' ') {	// checks to make sure that the player selected an open space
						board[row][column] = user;
						validMove = true;
						return;
					}
				}
			}
			else {   // This triggers if the player entered "g" for Give Up.
				validMove = true;
				winner = "computer";
				losses++;
				
			}

			
			if (validMove == false)
				System.out.println("\nThat is not a valid move.");
		} while (validMove == false);
	} // END of playerTurn method

	boolean compLogicWinningMove() { // This method searches for a winning move.  If it can't
									// find one it will then search for a chance to block the 
									// user's winning move.
		int sum1; // This is the target of the search.  It is the sum of 2 X's or O's and an available space
		int sum2; // This holds the sum of the row, column, or diagonal being looked at.
		boolean validMove = false;
		sum1 = 32 + comp * 2; 
		for (int i3=0; i3<2; i3++){
			for (int i=0; i<3; i++) {
				sum2 = 0;
				sum2 = board[0][i] + board[1][i] + board[2][i];  // This part is checking the columns for a possible winning move
				if (sum1 == sum2) {
					for (int i2 = 0; i2<3; i2++){
						if (board[i2][i] == ' ') {
							board[i2][i] = comp;
							validMove = true; 
							return true;
							}
						}
					}
				sum2 = board[i][0] + board[i][1] + board[i][2]; // This part checks the rows for a winning move
				if (sum1 == sum2) {
					for (int i2 = 0; i2<3; i2++){
						if (board[i][i2] == ' ') {
							board[i][i2] = comp;
							validMove = true; 
							return true;
							}
						}
					}
				}
			sum2 = board[0][0] + board[1][1] + board[2][2]; // This sums the top-left/bottom-right diagonal
			if (sum1 == sum2) {
				if (board[0][0] == ' ') {
					board[0][0] = comp;
					validMove = true; 
					return true;
					}
				else if (board[1][1] == ' ') {
					board[1][1] = comp;
					validMove = true; 
					return true;
					}
				else if (board[2][2] == ' ') {
					board[2][2] = comp;
					validMove = true; 
					return true;
					}		
				}
			sum2 = board[0][2] + board[1][1] + board[2][0]; // This sums the top-right/bottom-left diagonal
			if (sum1 == sum2) {
				if (board[0][2] == ' ') {
					board[0][2] = comp;
					validMove = true; 
					return true;
					}
				else if (board[1][1] == ' ') {
					board[1][1] = comp;
					validMove = true; 
					return true;
					}
				else if (board[2][0] == ' ') {
					board[2][0] = comp;
					validMove = true; 
					return true;
					}		
			}
			sum1 = 32 + user * 2;
		}
	return false;
	} // END of compLogicWinningMove method

	boolean compLogicForkTest() {
		// Fork Method
		int forkCheck = comp + 32;
		int sum1, sum2, sum3;
		boolean validMove = false;
		for (int i=0; i<2; i++) { //This will run twice.  1st looking for a "fork", 2nd looking to block
								  // a possible fork by the player. A fork is when you give yourself two
								  // possible winning moves on your next turn.  Your opponent will only be
								  // able to block 1 of them.
			if (board[0][0] == ' ') { // If the top-left space is free, the application will search for a 
									  // fork opportunity in the three lines connecting to it
				sum1 = board[1][0] + board [2][0];
				sum2 = board[1][1] + board [2][2];
				sum3 = board[0][1] + board [0][2]; 
				if (sum1 == forkCheck) {
					if ((sum2 == forkCheck) || (sum3 == forkCheck)) {
						board[0][0] = comp;
						return true;
					}
				}
				else if ((sum2 == forkCheck) && (sum3 == forkCheck)) {
					board[0][0] = comp;
					return true;
				}	
			
			}
			if (board[0][2] == ' ') { // Same as above but with the top-right corner
				sum1 = board[0][0] + board [0][1];
				sum2 = board[1][1] + board [2][0];
				sum3 = board[1][2] + board [2][2];
				if (sum1 == forkCheck) {
					if ((sum2 == forkCheck) || (sum3 == forkCheck)) {
						board[0][2] = comp;
						return true;
					}
				}
				else if ((sum2 == forkCheck) && (sum3 == forkCheck)) {
					board[0][2] = comp;
					return true;
				}	
			
			}
			if (board[2][2] == ' ') { // Same as above but with the bottom-right corner
				sum1 = board[0][2] + board [1][2];
				sum2 = board[0][0] + board [1][1];
				sum3 = board[2][0] + board [2][1]; 
				if (sum1 == forkCheck) {
					if ((sum2 == forkCheck) || (sum3 == forkCheck)) {
						board[2][2] = comp;
						return true;
					}
				}
				else if ((sum2 == forkCheck) && (sum3 == forkCheck)) {
					board[2][2] = comp;
					return true;
				}	
			
			}
			if (board[2][0] == ' ') { // Same as above but with the bottom-left corner
				sum1 = board[0][0] + board [1][0];
				sum2 = board[0][2] + board [1][1];
				sum3 = board[2][2] + board [2][1];
				if (sum1 == forkCheck) {
					if ((sum2 == forkCheck) || (sum3 == forkCheck)) {
						board[2][0] = comp;
						return true;
					}
				}
				else if ((sum2 == forkCheck) && (sum3 == forkCheck)) {
					board[2][0] = comp;
					return true;
				}
			}	
			if (board[0][1] == ' ') { // Same as above but with the top-middle space.  There are only
									  // two lines (instead of 3) to check for this fork possibility.
				sum1 = board[0][0] + board [0][2];
				sum2 = board[1][1] + board [2][2];
				if ((sum1 == forkCheck) && (sum2 == forkCheck)) {
					board[0][1] = comp;
					return true;
				}
			}
			if (board[1][2] == ' ') { // Same as above but with the right-middle space. 
				sum1 = board[0][2] + board [2][2];
				sum2 = board[1][0] + board [1][1];
				if ((sum1 == forkCheck) && (sum2 == forkCheck)) {
					board[1][2] = comp;
					return true;
				}
			}
			if (board[2][1] == ' ') { // Same as above but with the bottom-middle space. 
				sum1 = board[0][1] + board [1][1];
				sum2 = board[2][0] + board [2][2];
				if ((sum1 == forkCheck) && (sum2 == forkCheck)) {
					board[2][1] = comp;
					return true;
				}
			}
			if (board[1][0] == ' ') { // Same as above but with the left-middle space. 
				sum1 = board[0][0] + board [0][2];
				sum2 = board[1][1] + board [1][2];
				if ((sum1 == forkCheck) && (sum2 == forkCheck)) {
					board[1][0] = comp;
					return true;
				}
			}
		forkCheck = user + 32; // This changes the value of forkCheck so that on the next iteration
							  // the application will be trying to BLOCK a fork.
		}
	return false;
	} // END of compLogicForkTest method

	boolean compLogicNextMove() {
		// compLogicNextMove Method
		int row, column;
		boolean validMove = false;
		Random random = new Random();
		int randomInt = random.nextInt(9);  // This part randomly selects a spot on the board and then cycles through the board from
		for (int i=0; i<9; i++) {			// that spot forward, searching for a space that the computer controls that is on a free row
			row = randomInt / 3;			// or column. The computer then randomly selects one of the 2 free spaces as its move for the turn.
			column = randomInt % 3;
			if (board[row][column] == comp) {
				int row1 = row + 1;
				if (row1 > 2)
					row1 = 0;
				int row2 = row - 1;						//This part is for the rows
				if (row2 < 0)
					row2 = 2;
				if ((board[row1][column] != user) && (board[row2][column] != user)) {
					int randomInt2 = random.nextInt(2);
					if (randomInt2 == 0) {
						board[row1][column] = comp;
						validMove = true;
						return true;
					}
					else {
						board[row2][column] = comp;
						validMove = true;
						return true;
					}
				}
				if (i < 9) {
					int col1 = column + 1;
					if (col1 > 2)
						col1 = 0;
					int col2 = column - 1;				// This part is for the columns
					if (col2 < 0)
						col2 = 2;
					if ((board[row][col1] != user) && (board[row][col2] != user)) {
						int randomInt2 = random.nextInt(2);
						if (randomInt2 == 0) {
							board[row][col1] = comp;
							validMove = true;
							return true;
						}
						else {
							board[row][col2] = comp;
							validMove = true;
							return true;
						}
					}
				}
			}
		randomInt++;  // By incrementing randomInt the application is able to cycle through the board
		if (randomInt > 8)
			randomInt = 0;
		}
	return false;
	} // END of compLogicNextMove method
	
	/*void compLogicSecondMove(); {
		
	}*/

	void compTurn() { // determines the most logical move for the copmuter and executes it
		boolean validMove = false;
		int row, column;
		drawBoard();
		System.out.println("My turn!");
		
			if (compLogicWinningMove())
				validMove = true;
			else if (compLogicForkTest()) 
				validMove = true;
			else if (compLogicNextMove())
				validMove = true;
			else {
				do {
					Random random = new Random();  		// If the computer could not find a place to go using the methods above, 
					int randomInt = random.nextInt(9);	// it randomly selects an available space.
					
					row = randomInt / 3;
					column = randomInt % 3;

					if (board[row][column] == ' ') {
						board[row][column] = comp;
						validMove = true;
					}
				} while (validMove == false);
			}
	} // END of compTurn method

	void checkForWin() {
		for (int i=0; i<3; i++) {
			if ((board[0][i]==board[1][i]) && (board[1][i]==board[2][i]))
				if (board[0][i]==user) {
					winner = "player";
					drawBoard();
					System.out.println("\nYOU WON!");
					return; }
				else if (board[0][i]==comp){
					winner = "computer";
					drawBoard();
					System.out.println("\nI WON!");
					return; }
			if ((board[i][0]==board[i][1]) && (board[i][0]==board[i][2]))
				if (board[i][0]==user) {
					winner = "player";
					drawBoard();
					System.out.println("\nYOU WON!");
					return; }
				else if (board[i][0]==comp){
					winner = "computer";
					drawBoard();
					System.out.println("\nI WON!");
					return; }
		}
		if ((board[0][0]==board[1][1]) && (board[1][1]==board[2][2]))
				if (board[1][1]==user) {
					winner = "player";
					drawBoard();
					System.out.println("\nYOU WON!");
					return; }
				else if (board[1][1]==comp){
					winner = "computer";
					drawBoard();
					System.out.println("\nI WON!");
				}
			if ((board[0][2]==board[1][1]) && (board[1][1]==board[2][0]))
				if (board[1][1]==user) {
					winner = "player";
					drawBoard();
					System.out.println("\nYOU WON!");
					return; }
				else if (board[1][1]==comp){
					winner = "computer";
					drawBoard();
					System.out.println("\nI WON!");
					return; }
		if ((winner == "No") && (moves == 9)) {
			winner = "draw";
			drawBoard();
			System.out.println("\nThis game was a draw.");
			return; }
	} // END of checkForWin method

	void beginGame()
	throws java.io.IOException {
		moves = 0;
		winner = "No";
		
		clearBoard();
		whoGoesFirst();
		
		do {
			if (user == 'X') 
				playerTurn();
			else
				compTurn();
			moves++;
			checkForWin();
			
			if (winner == "No"){
				if (user == 'X')
					compTurn();
				else
					playerTurn();
				moves++;
				checkForWin();
			}
			

		} while (winner == "No");
		switch(winner) {
				case "player":
					wins++;
					break;
				case "computer":
					losses++;
					break;
				case "draw":
					draws++;
					break;
		}
	} // END of beginGame method
}

class SteveTicTacToe {
	public static void main(String args[]) 
	throws java.io.IOException {

		TicTacToe newGame = new TicTacToe();

		newGame.drawBoard();
		if (newGame.playOrNot() == true) {
			
			do {
				newGame.beginGame();
			} while (newGame.playOrNot() == true);
			
		}
	}
}