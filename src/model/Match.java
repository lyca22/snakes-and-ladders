package model;

public class Match {

	private int boardWidth;
	private int boardLength;
	private int snakeAmount;
	private int ladderAmount;
	private int playerAmount;
	private Player players;
	private Field first;
	private Match next;

	public Match(int boardWidth, int boardLength, int snakeAmount, int ladderAmount, int playerAmount) {
		setBoardWidth(boardWidth);
		setBoardLength(boardLength);
		setSnakeAmount(snakeAmount);
		setLadderAmount(ladderAmount);
		setPlayerAmount(playerAmount);
		addPlayerToPlayerList();
	}

	public void startGame() {

	}

	public void addPlayerToPlayerList() {
		//Recursion
	}

	public void movePlayer() {

	}

	public void finishGame() {

	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public void setBoardWidth(int boardWidth) {
		this.boardWidth = boardWidth;
	}

	public int getBoardLength() {
		return boardLength;
	}

	public void setBoardLength(int boardLength) {
		this.boardLength = boardLength;
	}

	public int getSnakeAmount() {
		return snakeAmount;
	}

	public void setSnakeAmount(int snakeAmount) {
		this.snakeAmount = snakeAmount;
	}

	public int getLadderAmount() {
		return ladderAmount;
	}

	public void setLadderAmount(int ladderAmount) {
		this.ladderAmount = ladderAmount;
	}

	public int getPlayerAmount() {
		return playerAmount;
	}

	public void setPlayerAmount(int playerAmount) {
		this.playerAmount = playerAmount;
	}

	public Player getPlayers() {
		return players;
	}

	public void setPlayers(Player players) {
		this.players = players;
	}

	public Field getFirst() {
		return first;
	}

	public void setFirst(Field first) {
		this.first = first;
	}

	public Match getNext() {
		return next;
	}

	public void setNext(Match next) {
		this.next = next;
	}

}