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
				text += current.getWinner().getNickname() + "(" + current.getWinner().getSymbol()+ "): " + current.getWinner().getScore() + "\n";
			}else if(current.getLeft() == null) {
				text += current.getWinner().getNickname() + "(" + current.getWinner().getSymbol()+ "): " + current.getWinner().getScore() + "\n";
				text += getScores(current.getRight());
			}else if(current.getRight() == null){
				text += getScores(current.getLeft());
				text += current.getWinner().getNickname() + "(" + current.getWinner().getSymbol()+ "): " + current.getWinner().getScore() + "\n";
			}else {
				text += getScores(current.getLeft());
				text += current.getWinner().getNickname() + "(" + current.getWinner().getSymbol()+ "): " + current.getWinner().getScore() + "\n";
				text += getScores(current.getRight());
			}
		}
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