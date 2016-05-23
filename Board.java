
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.ImageIcon;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.*;
import java.io.*;

public class Board{
	
	Tile[][] board = new Tile[3][3];
	ArrayList<Point> availablePoints;
	ArrayList<PointsAndScores> rootsChildrenScore = new ArrayList<PointsAndScores>();
	public static boolean player_turn;
	
	public Board(int choice){
		char playerMove;
	
		if(choice == 1){
			playerMove = 'X';
			player_turn = true;
		}else{
			playerMove = 'O';
			player_turn = false;
		}
		
		for(int i = 0; i < 3; i++){	
			for(int j = 0; j < 3; j++){
				board[i][j] = new Tile(playerMove, this);
			}
		}	
	}
	
	public int evaluateBoard(){
		
		int score = 0;
		
		 //Check all rows
        for (int i = 0; i < 3; i++) {
            int blank = 0;
            int X = 0;
            int O = 0;
            for (int j = 0; j < 3; j++) {
                if (board[i][j].value == 0)
                    blank++;
                else if (board[i][j].value == 1)
                    X++;
                else 
                    O++;
                
            } 
            score+=changeInScore(X, O); 
        }

        //Check all columns
        for (int j = 0; j < 3; j++) {
            int blank = 0;
            int X = 0;
            int O = 0;
            for (int i = 0; i < 3; i++) {
                if (board[i][j].value == 0) {
                    blank++;
                } else if (board[i][j].value == 1) {
                    X++;
                } else {
                    O++;
                } 
            }
            score+=changeInScore(X, O);
        }

        int blank = 0;
        int X = 0;
        int O = 0;

        //Check diagonal (first)
        for (int i = 0, j = 0; i < 3; ++i, ++j) {
            if (board[i][j].value == 1) {
                X++;
            } else if (board[i][j].value == 2) {
                O++;
            } else {
                blank++;
            }
        }

        score+=changeInScore(X, O);

        blank = 0;
        X = 0;
        O = 0;

        //Check Diagonal (Second)
        for (int i = 2, j = 0; i > -1; --i, ++j) {
            if (board[i][j].value == 1) {
                X++;
            } else if (board[i][j].value == 2) {
                O++;
            } else {
                blank++;
            }
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

	public int alphaBetaMinimax(int alpha, int beta, int depth, int turn){
        
        if(beta<=alpha){ 
        	//System.out.println("Pruning at depth = "+depth);
        	if(turn == 1) return Integer.MAX_VALUE; 
        	else return Integer.MIN_VALUE; 
        }
        
        if(depth == -1 || isGameOver()) return evaluateBoard();
        
        ArrayList<Point> pointsAvailable = getAvailableStates();
        
        if(pointsAvailable.isEmpty()) return 0;
        
        if(depth==0) rootsChildrenScore.clear(); 
        
        int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;
        
        for(int i=0;i<pointsAvailable.size(); ++i){
            Point point = pointsAvailable.get(i);
            
            int currentScore = 0;
            if(turn == 1){
          	//maxValue fxn  
                placeAMove(point, 1); 
                currentScore = alphaBetaMinimax(alpha, beta, depth+1, 2);
                maxValue = Math.max(maxValue, currentScore); 
                
                //Set alpha
                alpha = Math.max(currentScore, alpha);
                
                if(depth == 0)
                    rootsChildrenScore.add(new PointsAndScores(currentScore, point));
            }else if(turn == 2){
            //minValue fxn
                placeAMove(point, 2);
                currentScore = alphaBetaMinimax(alpha, beta, depth+1, 1); 
                minValue = Math.min(minValue, currentScore);
                
                //Set beta
                beta = Math.min(currentScore, beta);
            }
            //reset board
            board[point.x][point.y].value = 0; 
            
            //If a pruning has been done, don't evaluate the rest of the sibling states
            if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) break;
        }
        return turn == 1 ? maxValue : minValue;
    }  

 	public ArrayList<Point> getAvailableStates() {
        availablePoints = new ArrayList<Point>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j].value == 0) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }

    public void placeAMove(Point point, int player) {
        board[point.x][point.y].value = player;   //player = 1 for X, 2 for O
    }
    
    public void computerMove(Point p){
    		board[p.x][p.y].computerMove();
    }

    public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScore.size(); ++i) {
            if (MAX < rootsChildrenScore.get(i).score) {
                MAX = rootsChildrenScore.get(i).score;
                best = i;
            }
        }

        return rootsChildrenScore.get(best).point;
    }
    
    public void resetBoard() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                board[i][j].value = 0;
            }
        }
    } 
    
    public boolean isGameOver() {
        //Game is over is someone has won, or board is full (draw)
        return (hasXWon() || hasOWon() || getAvailableStates().isEmpty());
    }

    public boolean hasXWon() {
        if ((board[0][0].value == board[1][1].value && board[0][0].value == board[2][2].value && board[0][0].value == 1) || (board[0][2].value == board[1][1].value && board[0][2].value == board[2][0].value && board[0][2].value == 1)) {
            //System.out.println("X Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((board[i][0].value == board[i][1].value && board[i][0].value == board[i][2].value && board[i][0].value == 1)
                    || (board[0][i].value == board[1][i].value && board[0][i].value == board[2][i].value && board[0][i].value == 1))) {
                // System.out.println("X Row or Column win");
                return true;
            }
        }
        return false;
    }

    public boolean hasOWon() {
        if ((board[0][0].value == board[1][1].value && board[0][0].value == board[2][2].value && board[0][0].value == 2) || (board[0][2].value == board[1][1].value && board[0][2].value == board[2][0].value && board[0][2].value == 2)) {
            // System.out.println("O Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0].value == board[i][1].value && board[i][0].value == board[i][2].value && board[i][0].value == 2)
                    || (board[0][i].value == board[1][i].value && board[0][i].value == board[2][i].value && board[0][i].value == 2)) {
                //  System.out.println("O Row or Column win");
                return true;
            }
        }
		return false;
	}
	

}
