package Interfaces;

import java.awt.Point;
import java.util.List;

public interface BoardI {
	
	public int[][] getState();
	
	public void play(int x, int y, int player);
	
	public void play(int index, int player);
	
	public void undoPlay();
	
	public boolean isOver();
	
	public List<Point> getChildren();
}
