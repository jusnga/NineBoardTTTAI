import java.awt.Point;
import java.util.Stack;

import Interfaces.BoardI;
import Interfaces.NineBoardI;


public class NineBoard implements NineBoardI{
	private BoardI[][] board;
	private int DIMENSION = 3;
	private int currentPlayer;
	private BoardI currentBoard;
	private Stack<BoardI> plays;
	private boolean playmade = false;
	private boolean gameOver = false;

	
	public NineBoard(int first) {
		this.currentPlayer = first;
		board = new Board[DIMENSION][DIMENSION];
		for (int row=0; row<DIMENSION; row++) {
			for (int col=0; col<DIMENSION; col++) {
				board[row][col] = new Board();
			}
		}
		plays = new Stack<BoardI>();
	}
	
	// Get board from coordinates (x,y)
	public BoardI getBoard(int x, int y) {
		if (x < DIMENSION && y < DIMENSION 
				&& x >= 0 && y >= 0) {
			return board[x][y];
		} else {
			System.out.println("Invalid board");
			return null;
		}
	}
	
	// Get board from cell index
	public BoardI getBoard(int index) {
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
		
		if (x < DIMENSION && y < DIMENSION 
				&& x >= 0 && y >= 0) {
			return board[x][y];
		} else {
			System.out.println("Invalid board");
			return null;
		}
	}

	
	private void changePlayer() {
		currentPlayer = currentPlayer==1 ? 0 : 1;
	}
	
	public void printNineBoard() {
		BoardI currentBoard;
		int[][] boardState;
		int[][] nineBoard= new int[9][9];

		// Create current state of 9x9 board
		for (int bigX = 0; bigX < DIMENSION; bigX++){
			for (int bigY = 0; bigY < DIMENSION; bigY++){
				
				currentBoard = getBoard(bigX, bigY);
				boardState = currentBoard.getState();

				for(int i = 0; i < DIMENSION; i++){
					for(int j = 0; j < DIMENSION; j++){
						nineBoard[(bigX * 3) + i][(bigY * 3) + j] = boardState[i][j]; 
					}
				}
			}
		}

		// Print 9x9 Board
		String borderl = " ";
		String borderr = " ";
		for (int row = 0; row < DIMENSION * DIMENSION; row ++) {
			for (int col = 0; col < DIMENSION * DIMENSION; col ++) {
				if (nineBoard[row][col] == -1) {System.out.print(borderl + "." + borderr);}
				if (nineBoard[row][col] == 1) {System.out.print(borderl + "X" + borderr);} 
				if (nineBoard[row][col] == 0) {System.out.print(borderl + "O" + borderr);}
				if (col == 2 || col == 5){System.out.print("|");}
			}
			System.out.println();
			if (row == 2 || row == 5) {
				System.out.println("---------+---------+---------");
			}
		}
		System.out.println();
		System.out.println();


	}
	
	public boolean isOver(){
		gameOver = false;
		for(int i = 1; i <= 9; i++) {
			if(getBoard(i).isOver()) {
				gameOver = true;
				return gameOver;
			}
		}
		return gameOver;
	}
	
	public void play(int index) {
		Point p = indexToCoord(index);
		play(p.x, p.y);
	}
	
	public void play(int x, int y) {
		if (x < DIMENSION && y < DIMENSION 
				&& x >= 0 && y >= 0 && !isOver()) {
			currentBoard.play(x, y, currentPlayer);
			plays.push(currentBoard);
			changePlayer();
			currentBoard = board[x][y];
			playmade = true;
		} else {
			playmade = false;
			System.out.println("Invalid play on nineboard");
		}
	}
	
	public BoardI[][] getBoards() {
		return this.board;
	}
	
	public BoardI getCurrentBoard() {
		return currentBoard;
	}
	
	public void undoPlay() {
		if (plays.size() != 0 && playmade) {
			BoardI board = plays.pop();
			board.undoPlay();
			currentBoard = board;
			changePlayer();
		}
	}
	
	public void setCurrentBoard(int index) {
		Point p = indexToCoord(index);
		currentBoard = board[p.x][p.y];
	}
	
	private Point indexToCoord(int index) {
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
		return (new Point(x,y));
	}
}
