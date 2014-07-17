import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Interfaces.AiI;
import Interfaces.BoardI;
import Interfaces.HeuristicI;
import Interfaces.NineBoardI;

/**
 * Our AI uses 4 interfaces, AiI, BoardI, HeuristicI, NineBoardI.
 * 
 * The BoardI interfaces represents a single board in the ultimate tic tac toe game, 
 * it stores the moves in a 2D integer array, where -1 represents an empty slot, 
 * 0 represents player O and 1 represents player X.
 * 
 * The NineBoardI class stores all nine boards, the HeuristicI interface represents the
 * heuristic used and the AiI represents the AI.
 * 
 * The NumWins class is the heuristic implementation that is used in our AI. It scores
 * a board based on how many winnable rows there are for a player subtracted by number of
 * winnable rows of the opponent.
 * 
 * The AI class implements a minmax with alpha beta pruning which can take in different heuristics
 * to score the board. The minmax is also depth limited so the search space can be limited to either 
 * make our AI easier/harder or to make it run within a reasonable time.
 * 
 * Considering that minmax returns an integer value, we decided to perform the first max step in the
 * minmax algorithm externally so we could attribute it to a move. We also employ a stack to keep track
 * of the last move so that the algorithm could recurse properly.
 */


public class Main {
	
	public static void main (String[] args) {
		int aiDepth = 6;
		BoardI board;
		NineBoardI nineboard = null;
		Ai ai = null;
		int boardIndex = 0;
		HeuristicI scorer = null;
		String currentPlayer = null;
		String host = new String("localhost");
		int port = 0;
		
		nineboard = new NineBoard(1);
		nineboard.setCurrentBoard(5);
		scorer = new NumWins();
		AiI X = new Ai(1, 0, 6);
		AiI O = new Ai(0, 1, 6);
		
		while (!nineboard.isOver()) {
			X.makePlay(nineboard, scorer);
			nineboard.printNineBoard();
			O.makePlay(nineboard, scorer);
			nineboard.printNineBoard();
		}
		
		
//		
//		if (args[0].equals("-p")) {
//			port = Integer.parseInt(args[1]);
//		}
//		try {
//			System.out.println("Connecting to port " + port + "...");
//			Socket client = new Socket(host, port);
//			System.out.println("Just connected to " + client.getRemoteSocketAddress());			
//			
//			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			System.out.println("bufferedReader set up complete");
//			
//			// init()
//			String input = in.readLine();
//			System.out.println("Received: " + input);
//			String pattern = "^init.*";
//			Pattern r = Pattern.compile(pattern);
//			Matcher m = r.matcher(input);
//			if (m.matches()) {
//				int[][] state = {
//						{-1, -1, -1},
//						{-1, -1, -1},
//						{-1, -1, -1}
//				};
//				board = new Board(state);
//				scorer = new NumWins();
//			} else {
//				System.out.println("Did not receive init() call from server.");
//		        client.close();
//		        return;
//			}
//			
//			// start(x) or start(o)
//			input = in.readLine();
//			System.out.println("Received: " + input);
//			pattern = "^start\\((\\w)\\).*";
//			r = Pattern.compile(pattern);
//			m = r.matcher(input);
//			if (m.matches()) {
//				nineboard = new NineBoard(1);
//				if (m.group(1).equals("x")) {
//					ai = new Ai(1, 0, aiDepth);
//					currentPlayer = new String("X");
//				} else if (m.group(1).equals("o")) {
//					ai = new Ai(0, 1, aiDepth);
//					currentPlayer = new String("O");
//				}
//				System.out.println("Start() took in: " + m.group(1));
//			} else {
//				System.out.println("Did not receive start() call from server.");
//		        client.close();
//		        return;
//			}
//			
//			// second_move(i,i)
//			input = in.readLine();
//			System.out.println("Received: " + input);
//			pattern = "^second_move\\((\\d),(\\d)\\).*";
//			r = Pattern.compile(pattern);
//			m = r.matcher(input);
//			if (m.matches()) {
//				int boardNum = Integer.parseInt(m.group(1));
//				int firstMove = Integer.parseInt(m.group(2));
//				System.out.println("boardNum = " + boardNum);
//				System.out.println("firstMove = " + firstMove);
//				nineboard.setCurrentBoard(boardNum);
//				nineboard.play(firstMove);
//				nineboard.printNineBoard();
//				boardIndex = ai.makePlay(nineboard, scorer);
//				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
//				out.println(boardIndex);
//				System.out.println("move made: " + boardIndex);
//				
//			// third_move(i,i,i)	
//			} else {
//				pattern = "^third_move\\((\\d),(\\d),(\\d)\\).*";
//				r = Pattern.compile(pattern);
//				m = r.matcher(input);
//				if (m.matches()) {
//					int boardNum = Integer.parseInt(m.group(1));
//					int firstMove = Integer.parseInt(m.group(2));
//					int secondMove = Integer.parseInt(m.group(3));
//					nineboard.setCurrentBoard(boardNum);
//					nineboard.play(firstMove);
//					nineboard.printNineBoard();
//					nineboard.play(secondMove);
//					nineboard.printNineBoard();
//					boardIndex = ai.makePlay(nineboard, scorer);
//					nineboard.printNineBoard();
//					PrintWriter out = new PrintWriter(client.getOutputStream(), true);
//					out.println(boardIndex);
//					System.out.println("move made: " + boardIndex);
//
//				} else {
//					System.out.println("Did not receive second_move() or third_move() call from server.");
//			        client.close();
//			        return;
//				}
//			}
//			
//			while (true) {
//				
//				input = in.readLine();
//				System.out.println("Received: " + input);
//				
//				// next_move(i)
//				pattern = "^next_move\\((\\d)\\).*";
//				r = Pattern.compile(pattern);
//				m = r.matcher(input);
//				if (m.matches()) {
//					int prevMove = Integer.parseInt(m.group(1));
//					nineboard.play(prevMove);
//					boardIndex = ai.makePlay(nineboard, scorer);
//					nineboard.printNineBoard();
//					PrintWriter out = new PrintWriter(client.getOutputStream(), true);
//					out.println(boardIndex);
//					System.out.println("move made: " + boardIndex);
//
//				}
//					
//				// last_move(i)
//				pattern = "^last_move\\((\\d)\\).*";
//				r = Pattern.compile(pattern);
//				m = r.matcher(input);
//				if (m.matches()) {
//					int prevMove = Integer.parseInt(m.group(1));
//					nineboard.play(prevMove);
//					nineboard.printNineBoard();
//				}
//				
//				// win()
//				pattern = "^win(\\(\\w*\\)).*";
//				r = Pattern.compile(pattern);
//				m = r.matcher(input);
//				if (m.matches()) {
//					System.out.println("Player " + currentPlayer + " wins " + m.group(1));
//				}
//				
//				// loss()
//				pattern = "^loss(\\(\\w*\\)).*";
//				r = Pattern.compile(pattern);
//				m = r.matcher(input);
//				if (m.matches()) {
//					System.out.println("Player " + currentPlayer + " loses " + m.group(1));
//				}
//				
//				// draw()
//				pattern = "^draw(\\(\\w*\\)).*";
//				r = Pattern.compile(pattern);
//				m = r.matcher(input);
//				if (m.matches()) {
//					System.out.println("Player " + currentPlayer + " loses " + m.group(1));
//				}
//				
//				// end()
//				pattern = "^end.*";
//				r = Pattern.compile(pattern);
//				m = r.matcher(input);
//				if (m.matches()) {
//					client.close();
//					return;
//				}
//			}
//				        
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		
//		int boardIndex = 5;
//		while (!nineboard.isOver()) {
//			boardIndex = X.makePlay(nineboard.getBoard(boardIndex), scorer);
//			nineboard.printNineBoard();
//			if (!nineboard.isOver()) {
//				boardIndex = O.makePlay(nineboard.getBoard(boardIndex), scorer);
//				nineboard.printNineBoard();
//			}
//		}
//		if (nineboard.getCurrentPlayer() == 1){
//			System.out.println("O Wins!");
//		} else {
//			System.out.println("X Wins!");			
//		}

		
		
//		while (!board.isOver()) {
//			X.makePlay(board, scorer);
//			board.printBoard();
//			O.makePlay(board, scorer);
//			board.printBoard();
//		}
		
	}
}
