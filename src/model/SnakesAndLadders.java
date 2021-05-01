package model;

public class SnakesAndLadders {

	private Match match;
	private Match root;

	/**
	 * Constructor method of SnakesAndLadders.
	 */
	
	public SnakesAndLadders() {
	}
	
	/**
	 * Appends the current match to the class' binary tree, which is sorted by the winner's score. <br>
	 * This method checks if the reference 'root' is null and assigns it to the current match.
	 * If that isn't the case, the method addScores is called with 'root' as a parameter. <br>
	 * Pre: A match has been created and the winner has been decided. <br>
	 * Post: The match is added to the binary tree and is stored using the winner's score. <br>
	 */
	
	public void addScores() {
		if(root == null) {
			root = match;
		}else {
			addScores(root);
		}
	}
	
	/**
	 * This method checks if the winner's score of the current match is lesser than the current node's, then it assigns the current match to the node's right/left if it is null.
	 * If it isn't null, then the method calls itself again with right/left as a parameter. <br>
	 * The method uses the node's right when the condition is true, otherwise it uses left. <br>
	 * Pre: The method addScores() has been called. <br>
	 * Post: The match is added to the binary tree and is stored using the winner's score. <br>
	 * @param current the current node of the binary tree.
	 */
	
	private void addScores(Match current) {
		if(match.getWinner().getScore() <= current.getWinner().getScore()) {
			if(current.getRight() == null) {
				current.setRight(match);
			}else {
				addScores(current.getRight());
			}
		}else {
			if(current.getLeft() == null) {
				current.setLeft(match);
			}else {
				addScores(current.getLeft());
			}
		}
	}
	
	/**
	 * This method uses Inorder traversal to go through the binary tree and get the matches' information as a String. <br>
	 * The String will contain the matches' winner, his score and symbol, the board width, length, snakes and ladders, the match's player amount and their symbols. <br>
	 * It calls the private method getScores with 'root' as a parameter. <br>
	 * Pre: A match has been created and saved in the binary tree (otherwise it returns an empty String). <br>
	 * Post: Returns a String with the matches' information. <br>
	 * @return Returns a String with the matches' information.
	 */
	
	public String getScores() {
		return getScores(root);
	}

	/**
	 * This method uses Inorder traversal to go through the binary tree and get the matches' information. <br>
	 * Pre: The method getScores() has been called. <br>
	 * Post: Returns a String with the matches' information. <br>
	 * @param current the current node of the binary tree. Starts as the tree's root.
	 * @return Returns a String with the matches' information.
	 */
	
	private String getScores(Match current) {
		String text = "";
		if(current != null) {
			if(current.getLeft() == null && current.getRight() == null) {
				text += getScoreText(current);
			}else if(current.getLeft() == null) {
				text += getScoreText(current);
				text += getScores(current.getRight());
			}else if(current.getRight() == null){
				text += getScores(current.getLeft());
				text += getScoreText(current);
			}else {
				text += getScores(current.getLeft());
				text += getScoreText(current);
				text += getScores(current.getRight());
			}
		}
		return text;
	}

	/**
	 * This method gets the match information and puts it in a String. <br>
	 * The String will contain the match's winner, his score and symbol, the board width, length, snakes and ladders, the match's player amount and their symbols. <br>
	 * Pre: The method getScores() has been called and 'current' hasn't been null. <br>
	 * Post: Returns a String with the match's information. <br>
	 * @param current the current node of the binary tree. In this case, it is the current match.
	 * @return Returns a String with the match's information.
	 */
	
	private String getScoreText(Match current) {
		String text = "Match winner: " + current.getWinner().getNickname() + "(" + current.getWinner().getSymbol()+ ") |";
		text += " Winner's score: " + current.getWinner().getScore() + " |";
		text += " Board width: " + current.getBoardWidth() + " |";
		text += " Board length: " + current.getBoardLength() + " |";
		text += " Snakes: " + current.getSnakeAmount() + " |";
		text += " Ladders: " + current.getLadderAmount() + " |";
		text += " Player amount: " + current.getPlayerAmount() + " |";
		text += " Remaining player symbols: " + current.showNotWinnerSymbols() + "\n";
		return text;
	}
	
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Match getRoot() {
		return root;
	}

	public void setRoot(Match root) {
		this.root = root;
	}

}