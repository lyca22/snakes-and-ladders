package model;

public class SnakesAndLadders {

	private Match match;
	private Player root;

	public SnakesAndLadders() {
	}

	public void addScores() {
		addScores(match.getWinner());
	}
	
	public void addScores(Player player) {
		if(root == null) {
			root = player;
		}else {
			addScores(root, player);
		}
	}
	
	public void addScores(Player current, Player player) {
		if(player.getScore() <= current.getScore()) {
			if(current.getLeft() == null) {
				current.setLeft(player);
			}else {
				addScores(current.getLeft(), player);
			}
		}else {
			if(current.getRight() == null) {
				current.setRight(player);
			}else {
				addScores(current.getRight(), player);
			}
		}
	}

	public String getScores() {
		return getScores(root);
	}

	public String getScores(Player current) {
		String text = "";
		if(current.getLeft() == null && current.getRight() == null) {
			text += current.getScore() + " ";
		}else if(current.getLeft() == null) {
			text += current.getScore() + " ";
			text += getScores(current.getRight());
		}else if(current.getRight() == null){
			text += getScores(current.getLeft());
			text += current.getScore() + " ";
		}else {
			text += getScores(current.getLeft());
			text += current.getScore() + " ";
			text += getScores(current.getRight());
		}
		return text;
	}
	
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Player getRoot() {
		return root;
	}

	public void setRoot(Player root) {
		this.root = root;
	}

}