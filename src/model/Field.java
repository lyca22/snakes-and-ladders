package model;

public class Field {

	private int fieldNumber;
	private Field next;
	private Field snake;
	private Field ladder;
	
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

}