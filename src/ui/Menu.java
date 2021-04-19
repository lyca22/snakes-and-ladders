package ui;
import model.*;

import java.util.Scanner;

public class Menu {

	private final static int PLAY = 1;
	private final static int SCORE_BOARD = 2;
	private final static int EXIT = 3;

	private static Scanner sc = new Scanner(System.in);
	SnakesAndLadders snakesAndLadders;

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


