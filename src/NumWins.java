import Interfaces.BoardI;
import Interfaces.HeuristicI;
import Interfaces.NineBoardI;


public class NumWins implements HeuristicI {

	private int[][] state;
	private int DIMENSION = 3;
	private int EMPTY = -1;
	private int WON = 100;
	@Override
	public int score(NineBoardI nineboard, int player) {
		int value = 0;
		int tempval = 0;
		
		for(BoardI[] boards : nineboard.getBoards()) {
			for (BoardI board : boards) {
				if (Math.abs(tempval = scoreboard(board, player)) == WON) {
					return tempval;
				} else {
					value += tempval;
				}
			}	
		}
		return value;
	}
	
	public int scoreboard(BoardI board, int player) {
		int tempval;
		int value = 0;
		this.state = board.getState();
		
		//Check rows
		for (int i = 0; i < DIMENSION; i++) {
			if (Math.abs(tempval = scoreLine(state[i], player)) == WON) {
				return tempval;
			} else {
				value += tempval;
			}
		}
		
		//Inverse array
		int[][] invBoard = new int[DIMENSION][DIMENSION];
		for (int row=0; row<DIMENSION; row++) {
			for (int col=0; col<DIMENSION; col++) {
				invBoard[col][row] = state[row][col];
			}
		}
		
		//Check columns
		for (int i=0; i<DIMENSION; i++) {
			if (Math.abs(tempval = scoreLine(invBoard[i], player)) == WON) {
				return tempval;
			} else {
				value += tempval;
			}
		}
		
		//check diagonal value
		int[] diag1 = new int[DIMENSION];
		int[] diag2 = new int[DIMENSION];
		for (int i=0; i<DIMENSION; i++) {		
			diag1[i] = state[i][i];
			diag2[i] = state[DIMENSION - i - 1][i];
		}

		if (Math.abs(tempval = scoreLine(diag1, player)) == WON) {
			return tempval;
		} else {
			value += tempval;
		}
		if (Math.abs(tempval = scoreLine(diag2, player)) == WON) {
			return tempval;
		} else {
			value += tempval;
		}
		return value;
	}
	
	
	private int scoreLine(int[] line, int player) {
		int seenplayer = -2;
		int numPlayer = 0;
		for (int val : line) {
			if (seenplayer == -2 && val != EMPTY) {
				seenplayer = val;
				numPlayer++;
			} else if (val == seenplayer) {
				numPlayer++;
			} else if (val != seenplayer && val != EMPTY) {
				return 0;
			}
		}
		if (numPlayer == 3) {
			return seenplayer == player ? WON : -WON;
		} else if (seenplayer == -2) {
			return 0;
		} else {
			return seenplayer == player ? 1 : -1;
		}
	}

}
