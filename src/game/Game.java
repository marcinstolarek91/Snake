package game;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game {
	private long gameSpeed;
	private Snake snake = new Snake();
	private GameWindow gameWindow = new GameWindow();
	private static final long TIME_TO_CHANGE_SPEED = 10000;
	private static final long START_DELAY = 2000;
	private Timer timerMove;
	private Point food = new Point(0, 0);
	public static int boardDimX, boardDimY;
	
	public Game(int dimX, int dimY) {
		Game.boardDimX = dimX;
		Game.boardDimY = dimY;
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
			x = (new Random()).nextInt(Game.boardDimX);
			y = (new Random()).nextInt(Game.boardDimY);
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
		timerMove.cancel();
		final class EndText extends JFrame implements ActionListener {
			private JLabel text = new JLabel("End of the game. Your result is " + snake.getPoints());
			private Button button = new Button("Exit");
			public EndText() {
				super("The end of game");
				setLayout(null);
				setVisible(true);
				setSize(150,50);
				text.setFont(new Font("Arial", Font.BOLD, 12));
				text.setLocation(0, 0);
				text.setSize(110, 50);;
				button.setLocation(110, 0);
				button.setSize(40, 0);
				add(text);
				add(button);
				button.addActionListener(this);
			}
			
			public void exit() {
				this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
				System.exit(0);
			}
			
			@Override
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == button)
					exit();
			}
		}
		
		EndText endText = new EndText();
	}
	
	private class GameWindow extends JFrame implements KeyListener {
		private JPanel board = new JPanel();
		private JPanel results = new JPanel();
		private JPanel [][] boardElements;
		private JLabel actualResult;
		private JLabel bestResult;
		public Color emptyColor = Color.GRAY;
		public Color snakeColor = Color.GREEN;
		public Color snakeHeadColor = Color.BLUE;
		public Color foodColor = Color.RED;
		
		public GameWindow() {
			super("Snake");
			generateScreen();
			generateBoardElements();
			generateResultsElements();
			flushScreen();
			addKeyListener(this);
		}
		private void generateScreen() {
			GridLayout boardGrid = new GridLayout(Game.boardDimX, Game.boardDimY);
			GridLayout resultsGrid = new GridLayout(2,1);
			
			boardGrid.setHgap(1);
			boardGrid.setVgap(1);
			board.setLocation(0,0);
			board.setSize(Game.boardDimX * 20, Game.boardDimY * 20);
			board.setLayout(boardGrid);
			add(board);
			results.setLocation(Game.boardDimX * 20,0);
			results.setSize(120,60);
			results.setLayout(resultsGrid);
			add(results);
			this.setLayout(null); // null layout for GameWindow
			this.setSize(board.getSize().width + results.getSize().width, board.getSize().height + results.getSize().height);
			this.setLocation(10,10);
			setVisible(true);
		}
		private void generateBoardElements() {
			boardElements = new JPanel[Game.boardDimX][Game.boardDimY];
			for (int y = 0; y < Game.boardDimY; y++) {
				 for (int x = 0; x < Game.boardDimX; x++) {
					boardElements[x][y] = new JPanel();
					board.add(boardElements[x][y]);
				}
			}
		}
		private void generateResultsElements() {
			bestResult = new JLabel("Best result: " + 0);
			actualResult = new JLabel("Actual result: 0");
			results.add(bestResult);
			results.add(actualResult);
		}
		private void setElementEmpty(int x, int y) {
			if (!isElementInRange(x, y))
				return;
			boardElements[x][y].setBackground(emptyColor);
		}
		private void setElementSnakeHead(int x, int y) {
			if (!isElementInRange(x, y))
				return;
			boardElements[x][y].setBackground(snakeHeadColor);
		}
		private void setElementSnake(int x, int y) {
			if (!isElementInRange(x, y))
				return;
			boardElements[x][y].setBackground(snakeColor);
		}
		private void setElementFood(int x, int y) {
			if (!isElementInRange(x, y))
				return;
			boardElements[x][y].setBackground(foodColor);
		}
		private boolean isElementInRange(int x, int y) {
			if (x < 0 || x >= Game.boardDimX)
				return false;
			else if (y < 0 || y >= Game.boardDimY)
				return false;
			return true;
		}
		public void flushScreen() {
			for (int x = 0; x < Game.boardDimX; x++) {
				 for (int y = 0; y < Game.boardDimY; y++) {
					if (snake.getElements().contains(new Point(x, y))) {
						if (snake.getHead().equals(new Point(x, y)))
							setElementSnakeHead(x, y);
						else
							setElementSnake(x, y);
					}
					else if (food.equals(new Point(x, y)))
						setElementFood(x, y);
					else
						setElementEmpty(x, y);
				}
			}
			//bestResult = new JLabel("Best result: " + 0);
			actualResult = new JLabel("Actual result: " + snake.getPoints());
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
