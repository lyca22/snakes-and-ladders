package model;

public class Field {

	private int fieldNumber;
	private Field next;
	private Field snake;
	private Field ladder;
	private Field parent;
	private String symbol;
	
	/**
	 *Constructor method for Field. <br>
	 *<b>Pre: </b>  <br>
	 *<b>Post: </b> Creates a field <br>
	 *@param fieldNumber It is number of the field. <br>
	 */

	public Field(int fieldNumber) {
		setFieldNumber(fieldNumber);
	}

	public int getFieldNumber() {
		return fieldNumber;
	}

	public void setFieldNumber(int fieldNumber) {
		this.fieldNumber = fieldNumber;
	}

	public Field getNext() {
		return next;
	}

	public void setNext(Field next) {
		this.next = next;
	}

	public Field getSnake() {
		return snake;
	}

	public void setSnake(Field snake) {
		this.snake = snake;
	}

	public Field getLadder() {
		return ladder;
	}

	public void setLadder(Field ladder) {
		this.ladder = ladder;
	}

	public Field getParent() {
		return parent;
	}

	public void setParent(Field parent) {
		this.parent = parent;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}