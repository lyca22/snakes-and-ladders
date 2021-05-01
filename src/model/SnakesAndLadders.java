package model;

public class SnakesAndLadders {

	private Match match;
	private Match root;

	public SnakesAndLadders() {
	}
	
	public void addScores() {
		if(root == null) {
			root = match;
		}else {
			addScores(root);
		}
	}
	
	public void addScores(Match current) {
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
	
	public String getScores() {
		return getScores(root);
	}

	public String getScores(Match current) {
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