/*public class MiniMax{
//add to Tile.java
	public int alphaBetaMiniMax(int alpha, int beta, int depth, char turn){
		//prune
		if(beta <= alpha){
			if( symbol == "X" ){ return Integer.MAX_VALUE;
			else return Integer.MIN_VALUE;		
		}
		
		if(depth == -1 || !checkWin().equals("")) return evaluateBoard();
		
		List<Point> pointsAvailable = getAvailableStates();
		
		if(pointsAvailable.isEmpty()) return 0;
		
		if(depth == 0) rootsChildrenScore.clear();
		
		//set so it can catch the first value encountered as max or min
		int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;
		
		//for each available point
		for(int i = 0; i < pointsAvailable.size(); i++){
			Point point = pointsAvailable.get(i);
			
			int currentScore = 0;
		
			if(turn == "X"){
				placeAMove(point, "X");
				//MAXIMUM VALUE for player 1
				currentScore = alphaBetaMinimax(alpha, beta, depth+1, "O");
				maxValue = Math.max(maxValue, currentScore);
				
				//set alpha - lower boundary
				alpha = Math.max(currentScore, alpha);
				
				if(depth == 0){
					rootsChilrenScore.add(new PointAndScores(currentScore, point));
				}
			}else if(turn == "O"){
				//MINIMUM VALUE for player 2
				placeAMove(point, "O");
				
				currentScore = alphaBetaMinimax(alpha, beta, depth+1, "X");
				minValue = Math.min(minValue, currentScore);
				
				//set beta - upper boundary
				beta = Math.min(currentScore, beta);			
			}
			
			//reset board
			board[point.x][point.y] = 0;
			
			if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) break;			
		}
		return turn == "X" ? maxValue : minValue;
 	
	}

	public int evaluateBoard(){
		int score = 0;
		
		//check rows
		for(int i = 0; i < 3; i++){
			int blank = 0;
			int X = 0;
			int O = 0;	
			
			for(int j = 0; j < 3; j++){
				if(board[i][j].getText().equals("")) blank++;				
				else if(board[i][j].getText().equals("X")) X++;
				else O++;				
			}
			score += changeInScore(X, O);		
		}	
		
		//check cols
		for(int j = 0; j < 3; j++){
			int blank = 0;
			int X = 0;
			int O = 0;	
			
			for(int i = 0; i < 3; i++){
				if(board[i][j].getText().equals("")) blank++;				
				else if(board[i][j].getText().equals("X")) X++;
				else O++;				
			}
			score += changeInScore(X, O);		
		}
		
		
        int blank = 0;
        int X = 0;
        int O = 0;

        //Check diagonal (first)
        for (int i = 0, j = 0; i < 3; ++i, ++j) {
            if (board[i][j].getText().equals("X"))  X++;
            else if (board[i][j].getText().equals("O")) O++;
            else blank++;
        }
        score+=changeInScore(X, O);

        blank = 0;
        X = 0;
        O = 0;

        //Check Diagonal (Second)
        for (int i = 2, j = 0; i > -1; --i, ++j) {
            if (board[i][j].getText().equals("X")) X++;
            else if (board[i][j].getText().equals("O")) O++;
            else blank++;
        }
		  score+=changeInScore(X, O);

        return score;
	
	}
	
	 private int changeInScore(int X, int O){
        int change;
        if (X == 3) {
            change = 100;
        } else if (X == 2 && O == 0) {
            change = 10;
        } else if (X == 1 && O == 0) {
            change = 1;
        } else if (O == 3) {
            change = -100;
        } else if (O == 2 && X == 0) {
            change = -10;
        } else if (O == 1 && X == 0) {
            change = -1;
        } else {
            change = 0;
        } 
        return change;
    }

	   public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScore.size(); i++) {
            if (MAX < rootsChildrenScore.get(i).score) {
                MAX = rootsChildrenScore.get(i).score;
                best = i;
            }
        }

        return rootsChildrenScore.get(best).point;
    }


	public void placeAMove(Point point, char player){
		board[point.x][point.y] = player;
	}

	public List<Point> getAvailableStates() {
        availablePoints = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j].getText().equals("")) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }

}*/
