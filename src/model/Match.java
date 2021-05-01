package model;

public class Match {

	private final static int FIRST_FIELD_NUMBER = 1;
	private final static int DEFAULT_ATTEMPTS = 10;
	private final static int FIRST_SNAKE_SYMBOL = 0;
	private final static int FIRST_LADDER_SYMBOL = 1;
	private final static int MIN_MOVE_VALUE = 1;
	private final static int MAX_MOVE_VALUE = 6;

	private int boardWidth;
	private int boardLength;
	private int fieldAmount;
	private int snakeAmount;
	private int ladderAmount;
	private int playerAmount;
	private Field first;
	private Player firstPlayer;
	private boolean hasEnded;
	private Player winner;
	private Match right;
	private Match left;

	public Match(int boardWidth, int boardLength, int snakeAmount, int ladderAmount, int playerAmount) {
		setBoardWidth(boardWidth);
		setBoardLength(boardLength);
		setFieldAmount(boardWidth*boardLength);
		setSnakeAmount(snakeAmount);
		setLadderAmount(ladderAmount);
		setPlayerAmount(playerAmount);
		setHasEnded(false);
	}

	public void startGame() {
		int fieldAmount = boardWidth * boardLength;
		createBoard(fieldAmount, FIRST_FIELD_NUMBER);
		addAllSnakes(snakeAmount, fieldAmount, DEFAULT_ATTEMPTS, FIRST_SNAKE_SYMBOL);
		addAllLadders(ladderAmount, fieldAmount, DEFAULT_ATTEMPTS, FIRST_LADDER_SYMBOL);
	}

	private void createBoard(int fieldAmount, int fieldNumber) {
		if(fieldAmount > 0) {
			addField(fieldNumber);
			createBoard(fieldAmount-1, fieldNumber+1);
		}
	}

	private void addField(int fieldNumber) {
		Field toAddField = new Field(fieldNumber);
		if(first == null) {
			first = toAddField;
		}else {
			addField(first, toAddField);
		}
	}

	private void addField(Field current, Field toAddField) {
		if(current.getNext() == null) {
			current.setNext(toAddField);
		}else {
			addField(current.getNext(), toAddField);
		}
	}

	public Field searchField(Field lastField, int position) {
		Field nextField;
		if((position-1) > 0) {
			if(lastField.getFieldNumber() + position-1 <= fieldAmount) {
				nextField = lastField.getNext();
				lastField = searchField(nextField, position-1);
			}else {
				lastField = searchField(lastField, position-1);
			}
		}
		return lastField;
	}

	public void addAllSnakes(int snakeAmount, int fieldAmount, int tries, int count) {
		if(snakeAmount > 0) {
			addSnake(fieldAmount, tries, count);
			addAllSnakes(snakeAmount-1, fieldAmount, tries, count+1);
		}
	}

	private void addSnake(int fieldAmount, int tries, int count) {
		String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int start = randomNumberWithRange(boardLength+1, fieldAmount-1);
		int aux = start;
		if(aux%boardLength == 0) {
			aux--;
		}
		int multiple = (aux - (aux%boardLength));
		int end = randomNumberWithRange(1, multiple);
		Field startField = searchField(first, start);
		Field endField = searchField(first, end);
		if(startField.getSnake() == null && endField.getSnake() == null && startField.getLadder() == null && endField.getLadder() == null && startField.getParent() == null && endField.getParent() == null) {
			startField.setSymbol(String.valueOf(symbols.charAt(count)));
			endField.setSymbol(String.valueOf(symbols.charAt(count)));
			startField.setSnake(endField);
			endField.setParent(startField);
		}else if(tries > 0){
			addSnake(fieldAmount, tries-1, count);
		}
	}

	public void addAllLadders(int ladderAmount, int fieldAmount, int tries, int count) {
		if(ladderAmount > 0) {
			addLadder(fieldAmount, tries, count);
			addAllLadders(ladderAmount-1, fieldAmount, tries, count+1);
		}
	}

	private void addLadder(int fieldAmount, int tries, int count) {
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
		if(startField.getSnake() == null && endField.getSnake() == null && startField.getLadder() == null && endField.getLadder() == null && startField.getParent() == null && endField.getParent() == null) {
			startField.setSymbol(String.valueOf(count));
			endField.setSymbol(String.valueOf(count));
			startField.setLadder(endField);
			endField.setParent(startField);
		}else if(tries > 0){
			addLadder(fieldAmount, tries-1, count);
		}
	}

	private int randomNumberWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int)(Math.random() * range) + min;
	}

	public void addPlayer(char symbol) {
		Player newPlayer = new Player(symbol);
		if(firstPlayer == null) {
			firstPlayer = newPlayer;
			firstPlayer.setPosition(getFirst());
		}else {
			addPlayer(firstPlayer, newPlayer);
		}
	}

	private void addPlayer(Player current, Player newPlayer) {
		if(current.getNext() == null) {
			current.setNext(newPlayer);
			current.getNext().setPosition(getFirst());
		}else {
			addPlayer(current.getNext(), newPlayer);
		}
	}

	public Player getPlayer(Player lastPlayer, int position) {
		Player selectedPlayer;
		if((position-1) > 0) {
			selectedPlayer = lastPlayer.getNext();
			lastPlayer = getPlayer(selectedPlayer, position-1);
		}
		return lastPlayer;
	}

	public int movePlayers(int turn, int fieldAmount) {
		int position = turn;
		if(turn > playerAmount) {
			if(turn%playerAmount == 0) {
				position = playerAmount;
			}else {
				position = turn%playerAmount;
			}
		}
		Player actualPlayer = getPlayer(firstPlayer, position);
		int moves = randomNumberWithRange(MIN_MOVE_VALUE, MAX_MOVE_VALUE);
		movePlayer(actualPlayer, moves+1, fieldAmount);
		return moves;
	}

	public void movePlayer(Player player, int moves, int fieldAmount) {
		Field actualField = player.getPosition();
		Field newField = searchField(actualField, moves);
		if(newField.getSnake() == null && newField.getLadder() == null) {
			player.setPosition(newField);
		}else if(newField.getSnake() != null){
			player.setPosition(newField.getSnake());
		}else {
			player.setPosition(newField.getLadder());
		}
		player.setMoves(player.getMoves()+1);
		if(player.getPosition().getFieldNumber() == fieldAmount) {
			setWinner(player);
			setHasEnded(true);
			player.setScore(player.calculateScore(fieldAmount));
		}
	}

	public String boardToString(boolean showPlayers) {
		return boardToString(getFirst(), getFieldAmount(), showPlayers);
	}
	
	public String boardToString(Field lastField, int fieldAmount, boolean showPlayers) {
		String text = "";
		Field nextField;
		if(fieldAmount > 0) {
			int aux = (lastField.getFieldNumber()-(lastField.getFieldNumber()%boardLength))/boardLength;
			if((aux%2 == 0 && lastField.getFieldNumber()%(boardLength*2) != 0) || (lastField.getFieldNumber()%boardLength == 0 && lastField.getFieldNumber()%(boardLength*2) != 0)) {
				if(showPlayers) {
					text += "[";
					text += goThroughPlayers(lastField, firstPlayer);
				}else {
					if(lastField.getFieldNumber() < 10) {
						text += "[ ";
					}else {
						text += "[";
					}
					text += lastField.getFieldNumber();
				}
				if(lastField.getSnake() != null || lastField.getLadder() != null || lastField.getParent() != null) {
					text += lastField.getSymbol() + "]";
				}else {
					text += " ]";
				}
				nextField = lastField.getNext();
				fieldAmount = fieldAmount-1;
			}else {
				text += "\n" + getOddRow(lastField, boardLength, showPlayers) + "\n";
				nextField = searchField(lastField, boardLength+1);
				fieldAmount = fieldAmount-boardLength;
			}
			text += boardToString(nextField, fieldAmount, showPlayers);
		}
		return text;
	}

	private String getOddRow(Field lastField, int count, boolean showPlayers) {
		String text = "";
		Field actualField;
		actualField = searchField(lastField, count);
		if(count > 0) {
			if(showPlayers) {
				text += "[";
				text += goThroughPlayers(actualField, firstPlayer);
			}else {
				if(actualField.getFieldNumber() < 10) {
					text += "[ ";
				}else {
					text += "[";
				}
				text += actualField.getFieldNumber();
			}
			if(actualField.getSnake() != null || actualField.getLadder() != null || actualField.getParent() != null) {
				text += actualField.getSymbol() + "]";
			}else {
				text += " ]";
			}
			text += getOddRow(lastField, count-1, showPlayers);
		}
		return text;
	}

	private String goThroughPlayers(Field currentField, Player currentPlayer) {
		String text = "";
		if(currentPlayer != null) {
			if(currentPlayer.getPosition().equals(currentField)) {
				text += currentPlayer.getSymbol();
			}else {
				text += " ";
			}
			text += goThroughPlayers(currentField, currentPlayer.getNext());
		}
		return text;
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

	public int getFieldAmount() {
		return fieldAmount;
	}

	public void setFieldAmount(int fieldAmount) {
		this.fieldAmount = fieldAmount;
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

	public Field getFirst() {
		return first;
	}

	public void setFirst(Field first) {
		this.first = first;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(Player firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public boolean hasEnded() {
		return hasEnded;
	}

	public void setHasEnded(boolean hasEnded) {
		this.hasEnded = hasEnded;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public Match getRight() {
		return right;
	}

	public void setRight(Match right) {
		this.right = right;
	}

	public Match getLeft() {
		return left;
	}

	public void setLeft(Match left) {
		this.left = left;
	}

}