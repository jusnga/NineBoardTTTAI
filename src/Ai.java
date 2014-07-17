import java.awt.Point;

import Interfaces.AiI;
import Interfaces.HeuristicI;
import Interfaces.NineBoardI;


public class Ai implements AiI {
	private HeuristicI scorer;
	private int computer, opponent;
	private int searchDepth;
	
	public Ai(int computer, int opponent, int depth) {
		this.computer = computer;
		this.opponent = opponent;
		this.searchDepth = depth;
	}
	
	@Override
	public int makePlay(NineBoardI nineboard, HeuristicI heuristic) {
		this.scorer = heuristic;
		int bestScore = Integer.MIN_VALUE;
		Point bestMove = null;
		if (nineboard.isOver()) {
			return 0;
		}
		for (Point child : nineboard.getCurrentBoard().getChildren()) {
			nineboard.play(child.x, child.y);
			int score = minMax(nineboard, opponent, 
					Integer.MIN_VALUE, Integer.MAX_VALUE, searchDepth);
			if (score > bestScore) {
				bestScore = score;
				bestMove = child;
			}
			nineboard.undoPlay();
		}
		if (bestMove != null) {
			nineboard.play(bestMove.x, bestMove.y);
			return (bestMove.x * 3) + bestMove.y + 1;
		}
		return 0;
	}
	
	private int minMax(NineBoardI nineboard, int maxplayer, int alpha, int beta, int depth) {
		int bestScore;
		
		if (nineboard.isOver() || depth == 0) {
			return scorer.score(nineboard, computer);
		} else {
			if (maxplayer == computer) {
				for (Point child : nineboard.getCurrentBoard().getChildren()) {
					nineboard.play(child.x, child.y);
					alpha = Math.max(alpha, minMax(nineboard, opponent, alpha, beta, depth-1));
					nineboard.undoPlay();
					if (beta <= alpha) break;
				}
				bestScore = alpha;
			} else {
				for (Point child : nineboard.getCurrentBoard().getChildren()) {
					nineboard.play(child.x, child.y);
					beta = Math.min(beta, minMax(nineboard, computer, alpha, beta, depth-1));
					nineboard.undoPlay();
					if (beta <= alpha) break;
				}
				bestScore = beta;
			}
		}
		
		return bestScore;
	}
	
	public int getPlayer() {
		return computer;
	}
	
	public int getOpponent() {
		return opponent;
	}
}
