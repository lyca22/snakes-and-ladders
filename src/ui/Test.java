package ui;

import model.Match;
import model.Player;
import model.SnakesAndLadders;

public class Test {

	public static void main(String[] args) {

		SnakesAndLadders sal = new SnakesAndLadders();
		Match match = new Match(7, 7, 2, 2, 1);
		sal.setMatch(match);
		match.startGame();
		match.addPlayer('%');
		System.out.println(match.boardToString(match.getFirst(), match.getBoardLength()*match.getBoardWidth(), false) + "\n");
		System.out.println(match.boardToString(match.getFirst(), match.getBoardLength()*match.getBoardWidth(), true) + "\n");

		match.movePlayer(match.getFirstPlayer(), 48, 49);
		System.out.println(match.boardToString(match.getFirst(), match.getBoardLength()*match.getBoardWidth(), true) + "\n");

		System.out.println(match.hasEnded());
		System.out.println(match.getWinner() + "\n");

		match.movePlayer(match.getFirstPlayer(), 10, 49);
		System.out.println(match.boardToString(match.getFirst(), match.getBoardLength()*match.getBoardWidth(), true) + "\n");

		System.out.println(match.hasEnded());
		System.out.println(match.getWinner() + "\n");

		Player player0 = new Player('%');
		Player player1 = new Player('%');
		Player player2 = new Player('%');
		Player player3 = new Player('%');

		player0.setMoves(1);
		player1.setMoves(1);
		player2.setMoves(1);
		player3.setMoves(1);

		player0.setScore(player1.calculateScore(5*5));
		player1.setScore(player1.calculateScore(2*2));
		player2.setScore(player1.calculateScore(3*3));
		player3.setScore(player1.calculateScore(4*4));

		sal.addScores(player0);
		sal.addScores(player1);
		sal.addScores(player2);
		sal.addScores(player3);

		System.out.println(sal.getScores());

	}

}
