package game;
import java.util.ArrayList;
import java.util.List;

public class Snake {
	private List<Point> elements = new ArrayList<>();
	private MoveDirection moveDir;
	private int points;
	private boolean alive;
	
	public Snake() {
		for (int i = 1; i <= 5; i++)
			elements.add(new Point(0,Point.boardDimY - i));
		moveDir = MoveDirection.UP;
		points = 5;
		alive = true;
	}
	public Point getHead() {
		return elements.get(elements.size() - 1);
	}
	public Point getTail() {
		return elements.get(0);
	}
	public MoveDirection getMoveDir() {
		return moveDir;
	}
	public int getLength() {
		return elements.size();
	}
	public List<Point> getElements(){
		return elements;
	}
	public int getPoints() {
		return points;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setMoveDir(MoveDirection moveDir) {
		this.moveDir = moveDir;
	}
	/**
	 * 
	 * @param food - food parameters
	 * @return true - if snake have eaten food
	 * false - otherwise
	 */
	public boolean move(Point food) {
		Point newHead = generateNewHead();
		if (elements.contains(newHead))
			getToDie();
		else {
			addNewHead(newHead);
			if (!food.equals(newHead)) // if eating then no move tail, only get new head (snake is raising)
				moveTail();
			else {
				addPoints();
				return true;
			}
		}
		return false;
	}
	private Point generateNewHead() {
		switch (moveDir) {
			case UP: return new Point(getHead().getX(), Point.upY(getHead().getY()));
			case RIGHT:	return new Point(Point.rightX(getHead().getX()), getHead().getY());
			case DOWN: return new Point(getHead().getX(), Point.downY(getHead().getY()));
			case LEFT: return new Point(Point.leftX(getHead().getX()), getHead().getY());
			default: return null;
		}
	}
	private void addNewHead(Point newHead) {
		elements.add(newHead);
	}
	private void moveTail() {
		elements.remove(0);
	}
	private void getToDie() {
		alive = false;
	}
	private void addPoints() {
		++points;
	}
}
