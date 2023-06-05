package pineapple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameArea extends JPanel implements KeyListener
{
    public static int PLAY=0;
    public static int PAUSE=1;
    public static int OVER=2;
    
    public int state=PLAY;
    
    private int fps=60;
    private int delay=fps/1000;
    
    public static final int gridRow=20;    //size of main grid, cells etc.
    public static final int gridCol=10;
    private int cellSize=24;
    private int boardX=240;    //position of the board.
    private int boardY=12;
    
    private int score=0;
    private int[][] heart;
    
    JLabel title;
    JLabel subtitle;
    JLabel scoreT;
    JLabel scoreN;
    //Creating some colors for ease of use.
    Color lightRed = new Color(239,83,80);
    Color lightYellow = new Color(255,238,88);
    Color lightGreen = new Color(38,166,154);
    Color lightBlue = new Color(66,165,245);
    Color lightViolet = new Color(126,87,194);
    Color lightGrass = new Color(156,204,101);
    Color lightOrange = new Color(255,167,38);
    
    private Color[][] grid = new Color[gridRow][gridCol]; //a color matrix to store which grid cells have colors in them
    private Timer looper;
    Random random = new Random();
          
    private Shape[] shapes = new Shape[7];
    private Shape currentShape;
    
    
    
    public GameArea()
    {
        this.setBackground( new Color(73,  77,  95  ) ); 
        this.setLayout(null);
        
        heart= new int[][]{            //creating the heart
        {0, 1, 1, 0, 0, 1, 1, 0},
        {1, 2, 2, 1, 1, 3, 3, 1},
        {1, 2, 2, 2, 2, 2, 3, 1},
        {1, 2, 2, 2, 2, 2, 2, 1},
        {0, 1, 2, 2, 2, 2, 1, 0},
        {0, 0, 1, 2, 2, 1, 0, 0},
        {0, 0, 0, 1, 1, 0, 0, 0}
        };
        
        shapes[0] = new Shape(new int[][]{  
        {1, 1, 1},
        {0, 1, 0}
        }, this, lightGreen );
        
        shapes[1] = new Shape(new int[][]{  
        {1, 1, 1, 1}
        }, this, lightBlue );
        
        shapes[2] = new Shape(new int[][]{  
        {1, 1, 1},
        {1, 0, 0}
        }, this, lightOrange );
        
        shapes[3] = new Shape(new int[][]{  
        {1, 1, 1},
        {0, 0, 1}
        }, this, lightViolet );
        
        shapes[4] = new Shape(new int[][]{  
        {1, 1},
        {1, 1}
        }, this, lightYellow );
        
        shapes[5] = new Shape(new int[][]{  
        {0, 1, 1},
        {1, 1, 0}
        }, this, lightGrass );
        
        shapes[6] = new Shape(new int[][]{  
        {1, 1, 0},
        {0, 1, 1}
        }, this, lightRed );
        
        title = new JLabel();
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Papyrus", Font.BOLD,30));
        title.setForeground(new Color(160, 210, 235));
        title.setText("TETRIS");
        title.setBounds(28,30,175,50);
        
        scoreT = new JLabel();
        scoreT.setHorizontalAlignment(JLabel.CENTER);
        scoreT.setFont(new Font("Papyrus", Font.BOLD,24));
        scoreT.setForeground(new Color(132, 91,  179));
        scoreT.setText("Score:");
        scoreT.setBounds(boardX+277, boardY+100, 150, 40);
        
        scoreN = new JLabel();
        scoreN.setHorizontalAlignment(JLabel.CENTER);
        scoreN.setFont(new Font("Papyrus", Font.BOLD,24));
        scoreN.setForeground(new Color(132, 91,  179));
        scoreN.setText(String.valueOf(score));
        scoreN.setBounds(boardX+277, boardY+140, 150, 60);
        
        subtitle = new JLabel();
        subtitle.setHorizontalAlignment(JLabel.CENTER);
        subtitle.setFont(new Font("Papyrus", Font.BOLD,14));
        subtitle.setForeground(new Color(221, 189, 244) );
        subtitle.setText("A game by Pineapple inc.");
        subtitle.setBounds(28,60,175,50);
        
        this.add(title);
        this.add(subtitle);
        this.add(scoreT);
        this.add(scoreN);
        
        currentShape= shapes[random.nextInt(7)];
        
        looper=new Timer(delay,new ActionListener()   //delay=time for each loop
        {
            @Override
            public void actionPerformed(ActionEvent e)   //does the actions multiple times
            {
                update();  //updates the shape posiion every time 
                repaint(); //repaints the board everytime
               
                
            }
        });
        
    }
    
    public void start()
    {
        looper.start();
    }
    
    public void stop()
    {
        looper.stop();
    }
    
    public Color[][] getGrid()
    {
        return grid;
    }
    
    public void score()
    {
        score+=5;
    }
    
    public void update()
    {
        if(state==PLAY)
            currentShape.update();
        if(state==OVER)
        {
            stop();
            new EndScreen();
        }
        
    }
    
    public void newShape()    //creates a new shape and calls reset method to refresh previous shape
    {
        currentShape.reset();
        currentShape= shapes[random.nextInt(7)];   
        scoreN.setText(String.valueOf(score));
        checkOver();
        
    }
    
    public void checkOver()
    {
        int[][] coords = currentShape.getMat();
        for(int i=0;i<coords.length;i++)            //checks every block of the shape if the square 
        {                                           //is empty or not
            for(int j=0;j<coords[0].length;j++)
            {
                if(coords[i][j]!=0)
                {
                    if(i+currentShape.getY()>=0)
                    {
                        if(grid[i+currentShape.getY() ][j+currentShape.getX()] !=null)
                        {
                            state=OVER;
                        }
                    }
                }
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g)  //paint method to paint cells and shapes as needed
    {
        super.paintComponent(g);
        g.setColor( new Color(229, 234, 245) );
        g.fillRect(boardX, boardY, 240, 480);  //drawing the game area(the limits of the tetris shapes)
        
        currentShape.render(g);
        
        g.setFont(new Font("Papyrus", Font.BOLD,20));
        g.setColor( new Color(160, 210, 235) );       
        g.drawString("Made By-", 500, 35);
        g.setFont(new Font("Papyrus", Font.PLAIN,14));
        g.setColor( new Color(221, 189, 244) );
        g.drawString("Muntasir, Bishal,", 500, 60);
        g.drawString("Shovon & Masuk", 500, 80);
        
        //Painting ScoreBoard->
        
        g.setColor( new Color(132, 91,  179) );  //Dark Purple box paint
        g.fillRect(boardX+263, boardY+100, 180, 100);
        
        g.setColor( new Color(221, 189, 244) );  //Light purple box paint
        g.fillRect(boardX+277, boardY+100, 150, 100);
        
        g.drawRect(69,149,101, 31);  //Button border paint
        g.drawRect(69,229,101, 31);
        g.drawRect(69,309,101, 31);
        
        g.setColor( new Color(73,  77,  95 ) );
        g.drawRect(boardX+277, boardY+100, 150, 40);  //Scoreboard border paint
        g.drawRect(boardX+277, boardY+140, 150, 60);
        
        for(int i=0; i<heart.length;i++)              //drawing the heart
        {    
            for(int j = 0; j<heart[0].length; j++)
            {
                if(heart[i][j] != 0)
                {
                    if(heart[i][j]==1)
                        g.setColor( new Color(132, 91,  179) );
                    else if(heart[i][j]==2)
                        g.setColor( new Color(221, 189, 244) );
                    else if(heart[i][j]==3)
                        g.setColor( new Color(229, 234, 245) );
                    g.fillRect(boardX+272+ j*20, boardY+300+ i*20, 20, 20);
                    g.setColor( new Color(73,  77,  95 ) );
                    g.drawRect(boardX+272+ j*20, boardY+300+ i*20, 20, 20);
                }
            }
        }
        
        for(int i=0; i<grid.length;i++)  //drawing the updated play grid
        {    
            for(int j = 0; j<grid[0].length; j++)
            {
                if(grid[i][j] != null)
                {
                    g.setColor( grid[i][j] );
                    g.fillRect(boardX+ j*cellSize, boardY+ i*cellSize, cellSize, cellSize);
                    g.setColor( new Color(73,  77,  95 ) );
                    g.drawRect(boardX+ j*cellSize, boardY+ i*cellSize, cellSize, cellSize);
                }
            }
        }
        
    }
    
    
    
    //Below are the keylistener methods to register key presses
    
    @Override
    public void keyTyped(KeyEvent e) {
        //Useless
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            currentShape.speedUp();                      //Speed Up if down key is pressed
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            currentShape.moveLeft();                     //Move left if left key is pressed
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            currentShape.moveRight();                    //Move right if right key is pressed
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            currentShape.rotate();                       //Rotate 90 deg if up key is pressed
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            currentShape.speedDown();
        }
    }
}
