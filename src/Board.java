import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Interfaces.BoardI;
import Interfaces.HeuristicI;


public class Board implements BoardI {
	
	private int[][] board;
	private int EMPTY = -1;
	private int DIMENSION = 3;
	private Stack<Point> plays;
	private HeuristicI scorer;
	private int WON = 100;
	private boolean playmade = false;

	public Board() {
		board = new int[DIMENSION][DIMENSION];
		for (int row=0; row<DIMENSION; row++) {
			for (int col=0; col<DIMENSION; col++) {
				board[row][col] = EMPTY;
			}
		}
		plays = new Stack<Point>();
		scorer = new NumWins();
	}
	
	public Board(int[][] state) {
		board = new int[DIMENSION][DIMENSION];
		for (int row=0; row<DIMENSION; row++) {
			for (int col=0; col<DIMENSION; col++) {
				board[row][col] = state[row][col];
			}
		}
		plays = new Stack<Point>();
		scorer = new NumWins();
	}
	
	@Override
	public int[][] getState() {
		return this.board;
	}

	// Play move with x,y coordinates
	// @Override
	public void play(int x, int y, int player) {
		if (board[x][y] == EMPTY && 
				x < DIMENSION && y < DIMENSION &&
				x >= 0 && y >= 0 && !isOver()) {
			board[x][y] = player;
			plays.push(new Point(x,y));
			playmade = true;
		} else {
			playmade = false;
			System.out.println("Invalid Move");
		}
	}
	
	// Play move with index
	public void play(int index, int player) {
		int x, y;
		switch (index) {
			case 1: x = 0; y = 0; break;
			case 2: x = 0; y = 1; break;
			case 3: x = 0; y = 2; break;
			case 4: x = 1; y = 0; break;
			case 5: x = 1; y = 1; break;
			case 6: x = 1; y = 2; break;
			case 7: x = 2; y = 0; break;
			case 8: x = 2; y = 1; break;
			case 9: x = 2; y = 2; break;
			default: x = -1; y = -1; break;
		}
		if (board[x][y] == EMPTY && 
				x < DIMENSION && y < DIMENSION &&
				x >= 0 && y >= 0 && !isOver()) {
			board[x][y] = player;
			plays.push(new Point(x,y));
			playmade = true;
		} else {
			playmade = false;
			System.out.println("Invalid Move");
		}
	}
	
	public void undoPlay() {
		Point move;
		if (plays.size() != 0 && playmade) {
			move = plays.pop();
			board[move.x][move.y] = EMPTY;
		}
	}

	@Override
	public boolean isOver() {
		int numEmpty = 0;
		for (int[] row : board) {
			for (int val : row) {
				if (val == EMPTY) numEmpty++;
			}
		}
		if (numEmpty == 0) return true;
		if (scorer.scoreboard(this, 1) == WON || scorer.scoreboard(this, 0) == WON) return true;
		return false;
	}

	@Override
	public List<Point> getChildren() {
		List<Point> children = new ArrayList<Point>();
		for (int row=0; row<DIMENSION; row++) {
			for (int col=0; col<DIMENSION; col++) {
				if (board[row][col] == EMPTY) {
					children.add(new Point(row, col));
				}
			}
		}
		return children;
	}

}
