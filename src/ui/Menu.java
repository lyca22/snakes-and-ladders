package ui;
import model.*;

import java.util.Scanner;

public class Menu {

	private final static int PLAY = 1;
	private final static int SCORE_BOARD = 2;
	private final static int EXIT = 3;

	private static Scanner sc = new Scanner(System.in);
	SnakesAndLadders snakesAndLadders;
	static Match match;

	public Menu(){
		snakesAndLadders = new SnakesAndLadders();
	}

	public void showMenu(){
		System.out.println("Escoja una de las siguientes opciones:");
		System.out.println("1) Para iniciar una partida");
		System.out.println("2) Para ver el tablero de posiciones");
		System.out.println("3) Salir");

	}

	public int readOption(){
		int choice = sc.nextInt();
		sc.nextLine();
		return choice;
	}


	public Match mainInfo() {
		String firstEntry = sc.nextLine();
		String separator = " "; 
		if(firstEntry != null) {
			String[] gameValues = firstEntry.split(separator);
			int boardWidth = Integer.parseInt(gameValues[0]);
			int boardLength = Integer.parseInt(gameValues[1]);
			int snakeAmount = Integer.parseInt(gameValues[2]);
			int ladderAmount = Integer.parseInt(gameValues[3]);

			try {
				int playerAmount = Integer.parseInt(gameValues[4]);
				match = new Match(boardWidth, boardLength, snakeAmount, ladderAmount, playerAmount);
				assignSymbols(playerAmount, 0, match);

			} catch (Exception e) {
				String symbols = gameValues[4];
				int playerAmount = symbols.length();
				match = new Match(boardWidth, boardLength, snakeAmount, ladderAmount, playerAmount);
				assignSymbols(playerAmount, 0, match, symbols);
			}

		}

		return match;
	}

	public void assignSymbols(int playerAmount, int index, Match match, String symbols) {
		char symbol;
		if(playerAmount > 0) {
			symbol = symbols.charAt(index);
			match.addPlayer(symbol);
			assignSymbols(playerAmount-1, index+1, match);

		}

	}

	public void assignSymbols(int playerAmount, int index, Match match) {
		String symbols = "*!OX%$#+&";
		char symbol;
		if(playerAmount > symbols.length()) {
			playerAmount = symbols.length();
			match.setPlayerAmount(playerAmount);
		}
		if(playerAmount > 0) {
			symbol = symbols.charAt(index);
			match.addPlayer(symbol);
			assignSymbols(playerAmount-1, index+1, match);
		}

	}

	public void backToMenu(Match match) {
		match.setHasEnded(true);
		startProgram();
	}


	public void askNickname(Match match) {
		match.getWinner();
		System.out.println("Ingrese su nombre/nickname:");
		String nickname = sc.nextLine();
		match.getWinner().setNickname(nickname);
	}


	public void start() {
		Match match = mainInfo();
		match.startGame();

		if(!match.hasEnded()) {
			String entry = sc.nextLine();
			if(entry.equalsIgnoreCase("menu")) {
				backToMenu(match);
				match.setHasEnded(true);
			}else if(entry.equalsIgnoreCase("simul")) {
				//simul
			}else {
				//
			}
		}
		askNickname(match);
	}


	public void doOperation(int choice, Match match) {
		switch (choice){
		case PLAY:
			start();
			break;
		case SCORE_BOARD:
			break;
		case EXIT:
			break;
		default: 
			System.out.println("Opcion invalida, ingrese nuevamente");

		}
	}


	//HACERLO RECURSIVO
	public void startProgram() {
		int option;
		do{
			showMenu();
			option = readOption();
			doOperation(option,match);
		}while (option!=3);
	}
}
