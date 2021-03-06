package game;

public class Point {
	private int x, y;
	
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public static int leftX(int actualX) {
		return (actualX == 0) ? Game.boardDimX - 1 : --actualX;
	}
	public static int rightX(int actualX) {
		return (actualX == Game.boardDimX - 1) ? 0 : ++actualX;
	}
	public static int upY(int actualY) {
		return (actualY == 0) ? Game.boardDimY - 1 : --actualY;
	}
	public static int downY(int actualY) {
		return (actualY == Game.boardDimY - 1) ? 0 : ++actualY;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
}
