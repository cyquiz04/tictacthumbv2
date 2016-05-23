
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.ImageIcon;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.*;
import java.io.*;

public class GameWindow{
		
	static JFrame frame;
	static JPanel panel;
	static int choice;	
	static Board b;
	
	public static void main(String args[]){
	
		XorO();
		
	}
	
	public static void gamePlay(){
	/*	if (choice == 2) {
			b.computerMove( new Point (1, 1) );
			b.player_turn = true;
		}else{
			b.player_turn = false;
		}*/
	
		Computer c = new Computer(b);
		c.start();	
	}

	public static void createWindow(){
		frame.setVisible(false);
		
		b = new Board(choice);
		
		frame = new JFrame("Tic Tac Thumb");
		panel = new JPanel();
		
		frame.setPreferredSize(new Dimension(600, 600));
		panel.setPreferredSize(new Dimension(600, 600));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane();
		
		panel.setLayout(new GridLayout(3, 3));
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				panel.add(b.board[i][j]);
			}
		}
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);	
	}
	
	
	public static void XorO(){
		
		frame = new JFrame("Tic Tac Thumb");
		JPanel panel = new JPanel();
		
		frame.setPreferredSize(new Dimension(600, 600));
		panel.setPreferredSize(new Dimension(600, 600));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane();
		
		panel.setLayout(new GridLayout(1, 2));
		
		ActionListener onClick = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JButton source = 	(JButton) e.getSource();
				if(source.getText().equals("X")) playerFirst();	
				else computerFirst();
				createWindow();
				gamePlay();
			}
		};
		
		JButton xbutton = new JButton("X");
		JButton obutton = new JButton("O");
		
		xbutton.setFont(new Font("Arial", Font.PLAIN, 200));
		obutton.setFont(new Font("Arial", Font.PLAIN, 200));	
		xbutton.setBackground(Color.black);
		xbutton.setForeground(Color.white);
		obutton.setBackground(Color.black);
		obutton.setForeground(Color.white);
		
		xbutton.addActionListener(onClick);
		obutton.addActionListener(onClick);
			
		panel.add(xbutton);
		panel.add(obutton);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}	
	
	public static void playerFirst(){
		choice = 1;
	}
	
	public static void computerFirst(){
		choice = 2;
	}




}
