package test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.MoveDirection;
import game.Point;
import game.Snake;
import static org.junit.Assert.*;

public class TestSnake {
	private Snake snake1;
	private int dimXOriginal = Game.boardDimX;
	private int dimYOriginal = Game.boardDimY;
	private Point head, tail;

	@Before
	public void setUp() throws Exception {
		Game.boardDimX = 6;
		Game.boardDimY = 6;
		snake1 = new Snake();
	}
	@Test
	public void testAddNewHead() {
		snake1.move(new Point(0, 0));
		assertEquals(snake1.getLength(), 6);
	}
	@Test
	public void testAddNewHead2() {
		tail = snake1.getTail();
		assertEquals(snake1.getElements().get(0), tail);
	}
	@Test
	public void testAddNewHead3() {
		head = snake1.getHead();
		assertEquals(snake1.getElements().get(snake1.getLength() - 1), head);
	}
	@Test
	public void testAddNewHead4() {
		snake1.setMoveDir(MoveDirection.RIGHT);
		tail = snake1.getElements().get(0);
		snake1.move(new Point(5, 5));
		assertNotEquals(snake1.getElements().get(0), tail);
	}
	@Test
	public void testAddNewHead5() {
		snake1.setMoveDir(MoveDirection.RIGHT);
		snake1.move(new Point(5, 5));
		snake1.setMoveDir(MoveDirection.DOWN);
		snake1.move(new Point(5, 5));
		snake1.setMoveDir(MoveDirection.LEFT);
		snake1.move(new Point(5, 5));
		assertFalse(snake1.isAlive());
	}
	@Test
	public void testMoveThroughWalls() {
		snake1.setMoveDir(MoveDirection.LEFT);
		snake1.move(new Point(5, 5));
		assertEquals(snake1.getHead(), new Point(5, 1));
	}
	@Test
	public void testMoveThroughWalls2() {
		snake1.setMoveDir(MoveDirection.LEFT);
		snake1.move(new Point(5, 5));
		snake1.setMoveDir(MoveDirection.UP);
		snake1.move(new Point(3, 3));
		assertEquals(snake1.getHead(), new Point(5, 0));
	}
	@Test
	public void testReverse() {
		snake1.setMoveDir(MoveDirection.DOWN);
		assertNotEquals(snake1.getMoveDir(), MoveDirection.DOWN);
	}
	@After
	public void tearDown() {
		Game.boardDimX = dimXOriginal;
		Game.boardDimY = dimYOriginal;
	}
}
