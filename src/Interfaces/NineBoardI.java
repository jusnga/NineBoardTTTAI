package Interfaces;

public interface NineBoardI {
	public boolean isOver();
	
	public void play(int index);

	public void play(int x, int y);
	
	public void printNineBoard();
	
	public BoardI getBoard(int x, int y);
	
	public BoardI[][] getBoards();
	
	public BoardI getCurrentBoard();
	
	public void undoPlay();
	
	public void setCurrentBoard(int index);
}
