package pineapple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game implements ActionListener{ 
    JFrame frame;
    GameArea board;
    JPanel menu;
    JButton play;
    JButton restart;
    JButton quit;
    JLabel text;
    public static int state=0;
    
    public Game()
    {
        frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Tettis by Pineapple Inc.");
        frame.setSize(720,540);
        frame.setResizable(false);
        frame.revalidate(); //refreshes all components
        frame.setLocationRelativeTo(null); //centralizes frame
        
        play=new JButton();                                    //Start Button
        play.setBackground(new Color(132, 91,  179));
        play.setForeground(new Color(221, 189, 244));
        play.setText("Start");
        play.setBounds(70,150,100, 30);
        play.setFont(new Font("Papyrus", Font.BOLD,16));
        play.setFocusable(false);
        play.addActionListener(this);
        
        restart=new JButton();                                   //Restart Button
        restart.setBackground(new Color(132, 91,  179));
        restart.setForeground(new Color(221, 189, 244));
        restart.setText("Restart");
        restart.setBounds(70,230,100, 30);
        restart.setFont(new Font("Papyrus", Font.BOLD,16));
        restart.setFocusable(false);
        restart.addActionListener(this);
        
        quit=new JButton();                                     //Quit Button
        quit.setBackground(new Color(132, 91,  179));
        quit.setForeground(new Color(221, 189, 244));
        quit.setText("Quit");
        quit.setBounds(70,310,100, 30);
        quit.setFont(new Font("Papyrus", Font.BOLD,16));
        quit.setFocusable(false);
        quit.addActionListener(this);
        
        board=new GameArea();
        board.add(play);
        board.add(restart);
        board.add(quit);
        board.setLayout(null);
        
        frame.add(board);
        frame.addKeyListener(board);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==play)
        {
            board.start();
        }
        if(e.getSource()==restart)
        {
            frame.dispose();
            new Game();
        }  
        if(e.getSource()==quit)
        {
            frame.dispose();
        }  
    }

    
    
}