package ui;

import model.Match;
import model.SnakesAndLadders;

public class Test {

	public static void main(String[] args) {
		SnakesAndLadders sal = new SnakesAndLadders();
		Match match = new Match(7, 7, 2, 2, 2);
		sal.setMatch(match);
		match.startGame();
		match.addPlayer('%');
		System.out.println(match.boardToString(match.getFirst(), match.getBoardLength()*match.getBoardWidth(), false));
		System.out.println(match.boardToString(match.getFirst(), match.getBoardLength()*match.getBoardWidth(), true));
	}

}
