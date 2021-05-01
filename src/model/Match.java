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

	/**
	 * Constructor method of Match. <br>
	 * @param boardWidth
	 * @param boardLength
	 * @param snakeAmount
	 * @param ladderAmount
	 * @param playerAmount
	 */
	
	public Match(int boardWidth, int boardLength, int snakeAmount, int ladderAmount, int playerAmount) {
		setBoardWidth(boardWidth);
		setBoardLength(boardLength);
		setFieldAmount(boardWidth*boardLength);
		setSnakeAmount(snakeAmount);
		setLadderAmount(ladderAmount);
		setPlayerAmount(playerAmount);
		setHasEnded(false);
	}

	/**
	 * Starts the game, creating the match's board and filling it with snakes and ladders based on the match's attributes. <br>
	 * Pre: A match instance has been created. <br>
	 * Post: A board has been created with the match's attributes. <br>
	 */
	
	public void startGame() {
		int fieldAmount = boardWidth * boardLength;
		createBoard(fieldAmount, FIRST_FIELD_NUMBER);
		addAllSnakes(snakeAmount, fieldAmount, DEFAULT_ATTEMPTS, FIRST_SNAKE_SYMBOL);
		addAllLadders(ladderAmount, fieldAmount, DEFAULT_ATTEMPTS, FIRST_LADDER_SYMBOL);
	}

	/**
	 * The match's board is created with a certain amount of fields which have a field number. <br>
	 * At first it checks if 'fieldAmount' is greater than 0. If that's the case, it adds a field with 'fieldNumber' as a parameter and calls this same method again. <br>
	 * This method is called by itself with 'fieldAmount-1' and 'fieldNumber+1' as parameters. <br>
	 * Pre: The method startGame() has been called. <br>
	 * Post: A board has been created. <br>
	 * @param fieldAmount the amount of fields of the board.
	 * @param fieldNumber the field number of the current field.
	 */
	
	private void createBoard(int fieldAmount, int fieldNumber) {
		if(fieldAmount > 0) {
			addField(fieldNumber);
			createBoard(fieldAmount-1, fieldNumber+1);
		}
	}

	/**
	 * Adds a field to the match's board.
	 * Creates an instance of Field using 'fieldNumber' as a parameter and assigns it to 'first' if it is null.
	 * If it isn't the case, it calls the method addField with first and the new Field instance as a parameter. <br>
	 * Pre: The method createBoard() has been called. <br>
	 * Post: A field has been created and added to the board. <br>
	 * @param fieldNumber the field number of the created field.
	 */
	
	private void addField(int fieldNumber) {
		Field toAddField = new Field(fieldNumber);
		if(first == null) {
			first = toAddField;
		}else {
			addField(first, toAddField);
		}
	}

	/**
	 * Adds a field to the match's board. <br>
	 * Assigns 'toAddField' as the current's 'next' if it is null. If that isn't the case, the method calls itself again with current's 'next' as a parameter. <br>
	 * Pre: The method addField() has been called. <br>
	 * Post: A field has been added to the board. <br>
	 * @param current the current field of the board.
	 * @param toAddField the field that is going to be added.
	 */
	
	private void addField(Field current, Field toAddField) {
		if(current.getNext() == null) {
			current.setNext(toAddField);
		}else {
			addField(current.getNext(), toAddField);
		}
	}

	/**
	 * Returns a field given an initial field and a position. <br>
	 * If 'position-1' is greater than 0, the method will call himself again, either with 'lastField' or 'nextField' and 'position-1' as parameters.
	 * 'lastField' is assigned to the method's value <br>
	 * If that isn't the case, the method will return lastField. <br>
	 * Pre: A board has been created.
	 * Post: Returns the board's field given the initial field and a position.
	 * @param lastField the initial field.
	 * @param position the position after that initial field.
	 * @return Returns the board's field given the initial field and a position.
	 */
	
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

	/**
	 * This method adds a certain amount of snakes to the board and assigns them a symbol.
	 * If snakeAmount is greater than 0, a snake will be attempted to be added and the method will call himself again, with 'snakeAmount-1' and 'count+1' as parameters. <br>
	 * Pre: A board has been created. <br>
	 * Post: A certain amount of snakes have been added to the board. <br>
	 * @param snakeAmount the amount of snakes to be added.
	 * @param fieldAmount the board's field amount.
	 * @param tries the number of tries for adding a snake.
	 * @param count a counter to give the snake's symbol.
	 */
	
	public void addAllSnakes(int snakeAmount, int fieldAmount, int tries, int count) {
		if(snakeAmount > 0) {
			addSnake(fieldAmount, tries, count);
			addAllSnakes(snakeAmount-1, fieldAmount, tries, count+1);
		}
	}

	/**
	 * This method adds a snake to the board and assigns it a symbol. <br>s
	 * A start and end position are defined, then the snake is created if those positions do not have snakes or ladders. <br>
	 * Pre: The method addAllSnakes has been called. <br>
	 * Post: A snake has been added to the board. <br>
	 * @param fieldAmount the board's field amount.
	 * @param tries the number of tries for adding a snake.
	 * @param count a counter to give the snake's symbol.
	 */
	
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

	/**
	 * This method adds a certain amount of ladders to the board and assigns them a symbol. <br>
	 * If ladderAmount is greater than 0, a ladder will be attempted to be added and the method will call himself again, with 'ladderAmount-1' and 'count+1' as parameters. <br>
	 * Pre: A board has been created. <br>
	 * Post: A certain amount of ladders have been added to the board. <br>
	 * @param ladderAmount the amount of ladders to be added.
	 * @param fieldAmount the board's field amount.
	 * @param tries the number of tries for adding a ladder.
	 * @param count a counter to give the ladder's symbol.
	 */
	
	public void addAllLadders(int ladderAmount, int fieldAmount, int tries, int count) {
		if(ladderAmount > 0) {
			addLadder(fieldAmount, tries, count);
			addAllLadders(ladderAmount-1, fieldAmount, tries, count+1);
		}
	}

	/**
	 * This method adds a ladder to the board and assigns it a symbol. <br>
	 * A start and end position are defined, then the ladder is created if those positions do not have snakes or ladders. <br>
	 * Pre: The method addAllLadders has been called. <br>
	 * Post: A ladder has been added to the board. <br>
	 * @param fieldAmount the board's field amount.
	 * @param tries the number of tries for adding a ladder.
	 * @param count a counter to give the ladder's symbol.
	 */
	
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

	/**
	 * It returns a random number given a range. <br>
	 * Post: Returns a random int given a range of ints. <br>
	 * @param min the minimal number.
	 * @param max the maximum number.
	 * @return Returns a random int given a range of ints.
	 */
	
	private int randomNumberWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int)(Math.random() * range) + min;
	}

	/**
	 * Creates and adds a player to the match. <br>
	 * A player is created using 'symbol' as a parameter, and it is assigned to 'firstPlayer' if it is null.
	 * If it isn't the case, the method addPlayer is called with 'firstPlayer' and 'newPlayer' as parameters. <br>
	 * Pre: A match instance has been created. <br>
	 * Post: A player has been added to the match. <br>
	 * @param symbol - the player's symbol to represent them in the game.
	 */
	
	public void addPlayer(char symbol) {
		Player newPlayer = new Player(symbol);
		if(firstPlayer == null) {
			firstPlayer = newPlayer;
			firstPlayer.setPosition(getFirst());
		}else {
			addPlayer(firstPlayer, newPlayer);
		}
	}

	/**
	 * Adds a player to the match. <br>
	 * Assigns 'newPlayer' to the current's 'next' if it is null. If it isn't the case, the method is called again with the current's 'next' as a parameter.
	 * Pre: The method addPlayer has been called and 'firstPlayer' hasn't been null. <br>
	 * Post: A player has been added to the match. <br>
	 * @param current the current player in the list.
	 * @param newPlayer the player that is gonna be added.
	 */
	
	private void addPlayer(Player current, Player newPlayer) {
		if(current.getNext() == null) {
			current.setNext(newPlayer);
			current.getNext().setPosition(getFirst());
		}else {
			addPlayer(current.getNext(), newPlayer);
		}
	}

	/**
	 * Returns a player given an initial player and a position.
	 * If 'position-1' is greater than 0, the method will call himself again with 'selectedPlayer' and 'position-1' as parameters.
	 * 'lastPlayer' is assigned to the method's value <br>
	 * If that isn't the case, the method will return 'lastPlayer'. <br>
	 * Pre: A player has been added before.
	 * Post: Returns a player given an initial player and a position.
	 * @param lastPlayer the initial player of the list.
	 * @param position the position after that initial player.
	 * @return Returns a player given an initial player and a position.
	 */
	
	public Player getPlayer(Player lastPlayer, int position) {
		Player selectedPlayer;
		if((position-1) > 0) {
			selectedPlayer = lastPlayer.getNext();
			lastPlayer = getPlayer(selectedPlayer, position-1);
		}
		return lastPlayer;
	}

	/**
	 * Moves one of the added players depending of the match's turn.
	 * It moves the player 1 to 6 fields away from the initial field randomly. <br>
	 * Pre: A board has been created, the players have been added. <br>
	 * Post: Players move 1 to 6 fields in a random way, depending on the match's turn. <br>
	 * @param turn the match's current turn.
	 * @param fieldAmount the board's field amount.
	 * @return The amount of moves the moving player has done.
	 */
	
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

	/**
	 * Moves the given player to a field depending on the amount of moves. <br>
	 * Pre: A board has been created, a player has been added. <br>
	 * Post: A player has been moved to a new field given the amount of moves. <br>
	 * @param player the player that is going to move.
	 * @param moves the amount of fields that is going to go through.
	 * @param fieldAmount the board's field amount.
	 */
	
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

	/**
	 * It returns a String with the match's board and all its elements. <br>
	 * Pre: A board has been created. <br>
	 * Post: The board is returned in a String, which shows all the board's fields and elements. <br>
	 * @param showPlayers either shows players or not.
	 * @return Returns a String with the match's board and all its elements.
	 */
	
	public String boardToString(boolean showPlayers) {
		return boardToString(getFirst(), getFieldAmount(), showPlayers);
	}
	
	/**
	 * It returns a String with the match's board and all its elements. <br>
	 * Pre: The method boardToString has been called. <br>
	 * Post: The board is returned in a String, which shows all the board's fields and elements. <br>
	 * @param lastField the initial field of the board.
	 * @param fieldAmount the amount of fields of the board.
	 * @param showPlayers either shows players or not.
	 * @return Returns a String with the match's board and all its elements.
	 */
	
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

	/**
	 * Gets a String with inverted fields given an initial field and an amount of fields. <br>
	 * Pre: The method boardToString has been called. <br>
	 * Post: Returns a String of inverted fields iven an initial field and an amount of fields. <br>
	 * @param lastField the initial field.
	 * @param count the amount of fields after the initial fields.
	 * @param showPlayers either shows players or not.
	 * @return Returns a String of inverted fields iven an initial field and an amount of fields.
	 */
	
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

	/**
	 * Checks if the players are positioned in the current field, if that's the case, it returns a String with their symbols. <br>
	 * Pre: A board has been created and players have been added. <br>
	 * Post: Returns a String with the players' symbols if they are positioned in the given field. <br>
	 * @param currentField the current field of the board.
	 * @param currentPlayer the current player that is going to be checked.
	 * @return Returns a String with the players' symbols if they are positioned in the given field.
	 */
	
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

	/**
	 * Gets a String with the symbols of the player who didn't win the match. <br>
	 * Pre: A match has been created, players have been added and a winner has been decided. <br>
	 * Post: Returns a String with the symbols of the player who didn't win the match. <br>
	 * @return Returns a String with the symbols of the player who didn't win the match.
	 */
	
	public String showNotWinnerSymbols() {
		return showNotWinnerSymbols(firstPlayer);
	}
	
	/**
	 * Gets a String with the symbol of the player who didn't win the match. <br>
	 * If 'currentPlayer' isn't null, their symbol is going to be added to the String if they aren't the winner of the match. <br>
	 * Pre: The method showNotWinnerSymbols() has been called. <br>
	 * Post: Gets a String with the symbol of the player who didn't win the match. <br>
	 * @param currentPlayer the current player in the list.
	 * @return Gets a String with the symbol of the player who didn't win the match.
	 */
	
	private String showNotWinnerSymbols(Player currentPlayer) {
		String text = "";
		if(currentPlayer != null) {
			if(currentPlayer.getSymbol() != winner.getSymbol()) {
				text += currentPlayer.getSymbol();
			}
			Player nextPlayer = currentPlayer.getNext();
			text += showNotWinnerSymbols(nextPlayer);
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
		if(snakeAmount > 26) {
			snakeAmount = 26;
		}
		this.snakeAmount = snakeAmount;
	}

	public int getLadderAmount() {
		return ladderAmount;
	}

	public void setLadderAmount(int ladderAmount) {
		if(ladderAmount > 26) {
			ladderAmount = 26;
		}
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