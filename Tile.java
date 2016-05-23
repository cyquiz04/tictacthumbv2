
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.ImageIcon;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.*;
import java.io.*;

public class Tile extends JButton implements ActionListener{

	//public Point coord;
	int value;
	static char playerMove;
	static char computerMove;
	static boolean player_turn;
	static Board board;
	
	public Tile(char symbol, Board b){
		this.value = 0;
		playerMove = symbol;
		if(playerMove == 'X') computerMove = 'O';
		else computerMove = 'X';	
		this.setBackground(Color.black);
		this.setForeground(Color.white);
		this.setOpaque(true);
      this.setFont(new Font("Arial", Font.PLAIN, 90));		
		this.addActionListener(this);
		System.out.println(playerMove);
		board = b;
		
	}
	
	public void actionPerformed(ActionEvent e){
		this.userMove();
	}
	
	public void userMove(){
		if( this.value == 0 ) this.setText(Character.toString(playerMove));
		
		if(playerMove == 'X') this.value = 1;
		else this.value = 2;	
		
		board.player_turn = false;
		
	}
	
	public void computerMove(){
		if( this.value == 0 ) this.setText(Character.toString(computerMove));
		
		if(computerMove == 'X') this.value = 1;
		else this.value = 2;	
	}
	

}
