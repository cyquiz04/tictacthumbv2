
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.ImageIcon;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.*;
import java.io.*;

public class Computer extends Thread implements Runnable{

	private boolean running;
	private static Board board;
	
	public Computer(Board b){
		this.board = b;
		this.running = true;
	}
	
	public void run() {
		//System.out.println("Thread is about to run");
		while(this.running) {
			//System.out.println("Thread is running");
			while (!board.getAvailableStates().isEmpty()) {
				System.out.println("Inside that other loop");
				System.out.println(board.player_turn);
				if (!board.player_turn) {
				//if it is not player's turn

					try {
						Thread.sleep(1000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					
					board.alphaBetaMinimax(Integer.MIN_VALUE, Integer. MAX_VALUE, 0, 1);	
					
					for(PointsAndScores pas : board.rootsChildrenScore){
						board.computerMove(board.returnBestMove());
					}
					
					board.player_turn = true;
				}					
				
				if(board.isGameOver())	break;
					
				try{
					Thread.sleep(1000);				
				}catch(InterruptedException e){
					e.printStackTrace();
				}
					
			}
			
			if(board.isGameOver()) break;
			
					
		}
		if (board.hasXWon()) {
         if(GameWindow.choice==2) GameWindow.displayResult("Unfortunately, you lost!");
         else GameWindow.displayResult("You win!");
      } else if (board.hasOWon()) {
         if(GameWindow.choice==2) GameWindow.displayResult("You win!");
         else GameWindow.displayResult("Unfortunately, you lost!");
      } else {         
         GameWindow.displayResult("It's a draw!");
      }
	
	}


}
