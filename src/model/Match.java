package model;

public class Match {

	private final static int FIRST_FIELD_NUMBER = 1;
	private final static int DEFAULT_ATTEMPTS = 10;

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
	}

	public void startGame() {
		int fieldAmount = boardWidth * boardLength;
		createBoard(fieldAmount, FIRST_FIELD_NUMBER, first);
		addAllSnakes(snakeAmount, fieldAmount, DEFAULT_ATTEMPTS);
		addAllLadders(ladderAmount, fieldAmount, DEFAULT_ATTEMPTS);
	}

	//Not sure if this method works. It requires testing.

	public void createBoard(int fieldAmount, int fieldNumber, Field lastField) {
		Field toAddField = new Field(fieldNumber);
		if(lastField == null && fieldAmount > 0) {
			lastField = toAddField;
			createBoard(fieldAmount-1, fieldNumber+1, lastField.getNext());
		}
	}

	public Field searchField(Field lastField, int position) {
		Field nextField;
		if((position-1) > 0) {
			nextField = lastField.getNext();
			searchField(nextField, position-1);
		}
		return lastField;
	}

	public void addSnake(int fieldAmount, int tries) {
		int start = randomNumberWithRange(boardLength+1, fieldAmount-1);
		int aux = start;
		if(aux%boardLength == 0) {
			aux--;
		}
		int multiple = (aux - (aux%boardLength));
		int end = randomNumberWithRange(1, multiple);
		Field startField = searchField(first, start);
		Field endField = searchField(first, end);
		if(startField.getSnake() == null && endField.getSnake() == null && startField.getLadder() == null && endField.getLadder() == null) {
			startField.setSnake(endField);
		}else if(tries > 0){
			addSnake(fieldAmount, tries-1);
		}
	}

	public void addAllSnakes(int snakeAmount, int fieldAmount, int tries) {
		if(snakeAmount > 0) {
			addSnake(fieldAmount, tries);
			addAllSnakes(snakeAmount-1, fieldAmount, tries);
		}
	}

	public void addLadder(int fieldAmount, int tries) {
		int start = randomNumberWithRange(2, fieldAmount-boardLength);
		int aux = start;
		if(aux%boardLength == 0) {
			aux--;
		}
		int multiple = (aux - (aux%boardLength));
		multiple += (boardLength+1);
		int end = randomNumberWithRange(multiple, fieldAmount);
		Field startField = searchField(first, start);
		Field endField = searchField(first, end);
		if(startField.getSnake() == null && endField.getSnake() == null && startField.getLadder() == null && endField.getLadder() == null) {
			startField.setLadder(endField);
		}else if(tries > 0){
			addLadder(fieldAmount, tries-1);
		}
	}

	public void addAllLadders(int ladderAmount, int fieldAmount, int tries) {
		if(ladderAmount > 0) {
			addLadder(fieldAmount, tries);
			addAllLadders(ladderAmount-1, fieldAmount, tries);
		}
	}

	public int randomNumberWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int)(Math.random() * range) + min;
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