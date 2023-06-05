package pineapple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EndScreen extends JFrame implements ActionListener{
    JButton ok;
    JLabel text;
    JLabel prompt;
    JPanel panel;
    JTextField name;
    public EndScreen()
    {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Game Over");
        this.setSize(300,180);
        this.setResizable(false);
        this.revalidate(); //refreshes all components
        this.setLocationRelativeTo(null); //centralizes frame
        this.setLayout(new BorderLayout());
        
        panel=new JPanel();
        panel.setVisible(true);
        panel.setBackground(new Color(73,  77,  95  ));
        panel.setLayout(null);
        
        name=new JTextField();
        name.setBounds(100,50,150,30);
        name.setFont(new Font("Papyrus", Font.BOLD,14));
        name.setForeground(new Color(132, 91,  179));
        name.setBackground(new Color(229, 234, 245));
        
        text=new JLabel();  
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setBounds(65,10,150,30);
        text.setFont(new Font("Papyrus", Font.BOLD,24));
        text.setForeground(new Color(160, 210, 235));
        text.setBackground(new Color(221, 189, 244));
        text.setText("Game Over");
        
        prompt=new JLabel();  
        prompt.setHorizontalAlignment(JLabel.CENTER);
        prompt.setBounds(20,50,80,30);
        prompt.setFont(new Font("Papyrus", Font.BOLD,20));
        prompt.setForeground(new Color(221, 189, 244));
        prompt.setBackground(new Color(221, 189, 244));
        prompt.setText("Name: ");
        
        ok=new JButton();
        ok.setForeground(new Color(132, 91,  179));
        ok.setBackground(new Color(221, 189, 244));
        ok.setText("OK");
        ok.setBounds(90,95,100, 30);
        ok.setFont(new Font("Papyrus", Font.BOLD,16));
        ok.setFocusable(false);
        ok.addActionListener(this);
        
        panel.add(ok);
        panel.add(text);
        panel.add(name);
        panel.add(prompt);
        this.add(panel);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ok)
        {
            this.dispose();
        }
        }
    
}
