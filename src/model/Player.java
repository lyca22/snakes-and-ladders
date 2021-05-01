package model;

public class Player {

	private char symbol;
	private String nickname;
	private int score;
	private int moves;
	private Player next;
	private Field position;

	public Player(char symbol) {
		setSymbol(symbol);
		setNickname("");
		setScore(0);
		setMoves(0);
	}

	public int calculateScore(int fieldAmount) {
		return fieldAmount*moves;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}
	
	public Player getNext() {
		return next;
	}

	public void setNext(Player next) {
		this.next = next;
	}

	public Field getPosition() {
		return position;
	}

	public void setPosition(Field position) {
		this.position = position;
	}

}