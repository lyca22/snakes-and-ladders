package ui;
import model.*;

import java.util.Scanner;

public class Menu {

	private final static int PLAY = 1;
	private final static int SCORE_BOARD = 2;
	private final static int EXIT = 3;

	private static Scanner sc = new Scanner(System.in);
	SnakesAndLadders snakesAndLadders;
	Match match;

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


	public void mainInfo(String separator) {
		String firstEntry = sc.nextLine();
		if(firstEntry != null) {
			String[] gameValues = firstEntry.split(separator);
			int boardWidth = Integer.parseInt(gameValues[0]);
			int boardLength = Integer.parseInt(gameValues[1]);
			int snakeAmount = Integer.parseInt(gameValues[2]);
			int ladderAmount = Integer.parseInt(gameValues[3]);

			try {
				int playerAmount = Integer.parseInt(gameValues[4]);
				assignSymbols(playerAmount, 0, match);
				match = new Match(boardWidth, boardLength, snakeAmount, ladderAmount, playerAmount);

			} catch (Exception e) {
				String symbols = gameValues[4];
				int playerAmount = symbols.length();
				assignSymbols(playerAmount, 0, match, symbols);
				match = new Match(boardWidth, boardLength, snakeAmount, ladderAmount, playerAmount);
			}

		}
	}

	public static void assignSymbols(int playerAmount, int index, Match match, String symbols) {
		char symbol;

		if(playerAmount > 0) {
			symbol= symbols.charAt(index);
			Player player = new Player (symbol);
			match.addPlayerToPlayerList(player, symbol);
			assignSymbols(playerAmount-1, index+1, match);

		}

	}

	public static void assignSymbols(int playerAmount, int index, Match match) {
		String symbols="*!OX%$#+&";
		char symbol;
		if(playerAmount > symbols.length()) {
			playerAmount=symbols.length();
		}
		if(playerAmount > 0) {
			symbol= symbols.charAt(index);
			Player player = new Player (symbol);
			match.addPlayerToPlayerList(player, symbol);
			assignSymbols(playerAmount-1, index+1, match);
		}

	}


	public void doOperation(int choice) {
		switch (choice){
		case PLAY:
			break;
		case SCORE_BOARD:
			break;
		case EXIT:
			break;
		default: 
			System.out.println("Opcion invalida, ingrese nuevamente");

		}
	}

	public void startProgram() {
		int option;
		do{
			showMenu();
			option = readOption();
			doOperation(option);
		}while (option!=3);
	}
}


