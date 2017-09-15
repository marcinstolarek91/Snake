package game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class Game {
	private long gameSpeed;
	private Snake snake = new Snake();
	private GameWindow gameWindow = new GameWindow();
	private static final long TIME_TO_CHANGE_SPEED = 10000;
	private static final long START_DELAY = 2000;
	private Timer timerMove;
	private Point food = new Point(0, 0);
	
	public Game(int dimX, int dimY) {
		Point.boardDimX = dimX;
		Point.boardDimY = dimY;
		gameSpeed = 1500;
		snake = new Snake();
		generateFood();
		timerMove = new Timer();
		setGameSpeed(START_DELAY);
	}
	public void setGameSpeed(long delay) {
		timerMove.cancel();
		timerMove.schedule(new MoveTask(), delay, gameSpeed);
	}
	/**
	 * 
	 * @return Food on free point on board
	 */
	private void generateFood() {
		int x, y;
		do {
			x = (new Random()).nextInt(Point.boardDimX);
			y = (new Random()).nextInt(Point.boardDimY);
		}while(snake.getElements().contains(new Point(x, y)));
		food = new Point(x, y);
	}
	private void moveSnake() {
		if (snake.move(food))
			generateFood();
		if (!snake.isAlive())
			endGame();
	}
	private void endGame() {
		//TODO
	}
	
	private class GameWindow extends JFrame implements KeyListener {
		public GameWindow() {
			addKeyListener(this);
		}
		public void flushScreen() {
			//TODO
		}
		@Override
		public void keyPressed(KeyEvent arg0) {
			switch (arg0.getKeyCode()) {
				case KeyEvent.VK_UP: snake.setMoveDir(MoveDirection.UP); break;
				case KeyEvent.VK_RIGHT: snake.setMoveDir(MoveDirection.RIGHT); break;
				case KeyEvent.VK_DOWN: snake.setMoveDir(MoveDirection.DOWN); break;
				case KeyEvent.VK_LEFT: snake.setMoveDir(MoveDirection.LEFT); break;
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}
	private class MoveTask extends TimerTask {
		private long timeSpendAtThatSpeed;
		public MoveTask() {
			super();
			timeSpendAtThatSpeed = 0;
		}
		@Override
		public void run() {
			moveSnake();
			gameWindow.flushScreen();
			timeSpendAtThatSpeed += gameSpeed;
			if (timeSpendAtThatSpeed >= TIME_TO_CHANGE_SPEED) {
				timeSpendAtThatSpeed = 0;
				gameSpeed = (long) ((float) gameSpeed * 0.9);
				setGameSpeed(0L);
			}
		}
	}
}
