package ui;

import java.util.Scanner;

import model.Match;
import model.SnakesAndLadders;

public class Menu {

	private final static int PLAY = 1;
	private final static int SCOREBOARD = 2;
	private final static int EXIT = 3;
	private final static int FIRST_TURN = 1;
	private final static boolean SHOW_FIELD_NUMBERS = false;
	private final static boolean SHOW_PLAYERS = true;

	private SnakesAndLadders sal;
	private Scanner sc;

	public Menu() {
		setSal(new SnakesAndLadders());
		sc = new Scanner(System.in);
	}


	/**
	 *Groups all the methods needed to make the program work. <br>
	 *<b>Pre: </b>  <br>
	 *<b>Post: </b> Starts the program using showMenu() and doOperation().  <br>
	 */

	public void startProgram() {
		showMenu();
		int option = readOption();
		doOperation(option);
		if(option != EXIT) {
			startProgram();
		}
	}


	/**
	 *Shows the menu to the user. <br>
	 *<b>Pre: </b>  <br>
	 *<b>Post: </b> Shows all the options available. <br>
	 */

	public void showMenu() {
		String text = "1. Play the game.\n";
		text += "2. Show scoreboard.\n";
		text += "3. Exit the game.\n\n";
		text += "Please enter an option.\n";
		System.out.println(text);
	}


	/**
	 *Reads the option of the user to select an option. <br>
	 *<b>Pre: </b> The user has to enter a number between 1 and 3. <br>
	 *<b>Post: </b> The option will be saved. <br>
	 */

	public int readOption() {
		int option;
		try {
			option = Integer.valueOf(sc.nextLine());
		}catch(Exception e) {
			option = -1;
			System.out.println("Please select an option. Press enter to continue.\n");
			sc.nextLine();
		}
		return option;
	}

	/**
	 *Does an operation according to the user's input. <br>
	 *<b>Pre: </b> The user has to choose an option. <br>
	 *<b>Post: </b> It will do what the user wants. <br> 
	 *@param option It is the choice of the user. <br> 
	 */

	public void doOperation(int option) {
		switch(option) {
		case PLAY:
			try {
				startGame();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Please enter the required information. Press enter to continue.\n");
				sc.nextLine();
			}
			break;
		case SCOREBOARD:
			showScores();
			break;
		case EXIT:
			System.out.println("Game closed. Press enter to continue.\n");
			sc.nextLine();
			break;
		}
	}


	/**
	 *Starts the game with the given information. <br>
	 *<b>Pre: </b> The user has to enter all the information needed. <br>
	 *<b>Post: </b> Starts the game with the given information. <br> 
	 */

	public void startGame() throws Exception {
		getGameInformation();
		Match currentMatch = sal.getMatch();
		System.out.println("\n" + currentMatch.boardToString(SHOW_FIELD_NUMBERS) + "\n");
		System.out.println(currentMatch.boardToString(SHOW_PLAYERS)+ "\n");
		System.out.println("Press enter to make a move, enter 'menu' to end the game and go to the menu, enter 'simul' to enable simulation mode, enter num to see the numerated board.");
		boolean saveScore = executeGameLoop(currentMatch, FIRST_TURN);
		saveScore(saveScore, currentMatch);
	}


	/**
	 *Creates a match with the given information (width, length, snake amount, ladder amount and player amount or their symbols). <br>
	 *<b>Pre: </b> It's called in the startGame method. <br>
	 *<b>Post: </b> Creates a match with the given information. <br> 
	 */

	public void getGameInformation() {
		System.out.println("Enter width, length, snake amount, ladder amount and player amount or their symbols.");
		String userEntry = sc.nextLine();
		if(userEntry != null) {
			String[] gameValues = userEntry.split(" ");
			int boardWidth = Integer.parseInt(gameValues[0]);
			int boardLength = Integer.parseInt(gameValues[1]);
			int snakeAmount = Integer.parseInt(gameValues[2]);
			int ladderAmount = Integer.parseInt(gameValues[3]);
			int playerAmount;
			String symbols;
			try {
				symbols = "*!OX%$#+&";
				playerAmount = Integer.parseInt(gameValues[4]);
			}catch(Exception e) {
				symbols = gameValues[4];
				playerAmount = symbols.length();
			}
			Match match = new Match(boardWidth, boardLength, snakeAmount, ladderAmount, playerAmount);
			sal.setMatch(match);
			sal.getMatch().startGame();
			addPlayers(symbols, playerAmount);
		}else {
			getGameInformation();
		}
	}


	/**
	 *Adds players to the current match. <br>
	 *<b>Pre: </b> It's called in the getGameInformation method. <br>
	 *<b>Post: </b> Adds players to the current match. <br> 
	 */

	public void addPlayers(String symbols, int playerAmount) {
		addPlayers(symbols, playerAmount, 0);
	}

	public void addPlayers(String symbols, int playerAmount, int index) {
		char symbol;
		if(playerAmount > symbols.length()) {
			playerAmount = symbols.length();
			sal.getMatch().setPlayerAmount(playerAmount);
		}
		if(playerAmount > 0) {
			symbol = symbols.charAt(index);
			sal.getMatch().addPlayer(symbol);
			addPlayers(symbols, playerAmount-1, index+1);
		}
	}


	/**
	 *Allows the players to move on the current board and choose other game modes. <br>
	 *<b>Pre: </b> The user has to enter all the information needed. <br>
	 *<b>Post: </b> Returns the score saved. <br> 
	 *@param currentMatch It is the current game. <br> 
	 *@param turn It is the turn of each player. <br> 
	 */

	public boolean executeGameLoop(Match currentMatch, int turn) {
		boolean saveScore = false;
		if(!currentMatch.hasEnded()) {
			String entry = sc.nextLine();
			if(entry.equalsIgnoreCase("menu")) {
				currentMatch.setHasEnded(true);
				System.out.println();
				turn = turn-1;
			}else if(entry.equalsIgnoreCase("simul")) {
				simulationLoop(currentMatch, turn);
			}else if(entry.equalsIgnoreCase("num")) {
				System.out.println(currentMatch.boardToString(SHOW_FIELD_NUMBERS) + "\n");
				System.out.println(currentMatch.boardToString(SHOW_PLAYERS) + "\n");
				turn = turn-1;
			}else {
				int moves = currentMatch.movePlayers(turn, currentMatch.getFieldAmount());
				System.out.println(currentMatch.boardToString(SHOW_PLAYERS) + "\n");
				int position = turn;
				if(turn > currentMatch.getPlayerAmount()) {
					if(turn%currentMatch.getPlayerAmount() == 0) {
						position = currentMatch.getPlayerAmount();
					}else {
						position = turn%currentMatch.getPlayerAmount();
					}
				}
				System.out.println("Player "+ currentMatch.getPlayer(currentMatch.getFirstPlayer(), position).getSymbol() +" moved " + moves + " tiles.\n");
			}
			saveScore = executeGameLoop(currentMatch, turn+1);
		}else {
			if(!(currentMatch.getWinner() == null)) {
				saveScore = true;
			}
		}
		return saveScore;
	}


	/**
	 *Starts the simulation mode. <br>
	 *<b>Pre: </b> The user has to enter the word "simul" and it is called in the executeGameLoop method. <br>
	 *<b>Post: </b> Starts the simulation mode. <br> 
	 *@param currentMatch It is the current game. <br> 
	 *@param turn It is the turn of each player. <br> 
	 */

	public void simulationLoop(Match currentMatch, int turn) {
		if(!currentMatch.hasEnded()) {
			int moves = currentMatch.movePlayers(turn, currentMatch.getFieldAmount());
			System.out.println(currentMatch.boardToString(SHOW_PLAYERS) + "\n");
			int position = turn;
			if(turn > currentMatch.getPlayerAmount()) {
				if(turn%currentMatch.getPlayerAmount() == 0) {
					position = currentMatch.getPlayerAmount();
				}else {
					position = turn%currentMatch.getPlayerAmount();
				}
			}
			System.out.println("Player "+ currentMatch.getPlayer(currentMatch.getFirstPlayer(), position).getSymbol() +" moved " + moves + " tiles.\n");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			simulationLoop(currentMatch, turn+1);
		}
	}


	/**
	 *Shows the winner and saves the score. <br>
	 *<b>Pre: </b> It's called in the startGame method. <br>
	 *<b>Post: </b> Shows the winner player and saves the score. <br> 
	 *@param saveScore Indicates if the score was saved. <br> 
	 *@param currentMatch It is the current game. <br>
	 */


	public void saveScore(boolean saveScore, Match currentMatch) {
		if(saveScore) {
			System.out.println("The player with the symbol " + currentMatch.getWinner().getSymbol() + " won the game, with "+ currentMatch.getWinner().getMoves() + " moves!");
			System.out.println("Enter your nickname.");
			String name = sc.nextLine();
			currentMatch.getWinner().setNickname(name);
			sal.addScores();
			System.out.println("\nScore saved. Press enter to continue.");
			sc.nextLine();
		}
	}


	/**
	 * Shows the winner's score and the match's info. <br>
	 * <b>Pre: </b> The match is over. <br>
	 * <b>Post: </b> Shows the winner's score and the match's info. <br>
	 */

	public void showScores() {
		System.out.println(sal.getScores());
		System.out.println("Press enter to continue.");
		sc.nextLine();
	}

	public SnakesAndLadders getSal() {
		return sal;
	}

	public void setSal(SnakesAndLadders sal) {
		this.sal = sal;
	}

}
