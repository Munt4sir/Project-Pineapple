package pineapple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Shape {
    
    //variables added by me
    private int gridRow=20;    //size of main grid, cells etc.
    private int gridCol=10;
    private int cellSize=24;
    private int boardX=240;    //position of the board.
    private int boardY=12;
    
    //variables by the guide
    private int normal=500;          //variables for the timers to change speeds.
    private int fast=50;
    private int moveDelay=normal;
    private long beginTime;
    
    Random random = new Random();
    
    private int x=random.nextInt(1,6), y=-2;  //the X and Y coords of the board grid in which the block will spawn. Here, the X coordinate is randomly generated so that the shape stays within the border.
    private int delX=0;                    //the deviation of X by which the brick will move when a key is pressed. Set to 0 by default for no key press.
    private boolean collision= false;   //pretty self explanatory.
    
    private int[][] coords;
    private GameArea board;
    private Color color;
    
    public Shape(int[][] coords, GameArea board, Color color){
        this.coords=coords;
        this.board=board;
        this.color=color;
        
    }
    
    public void update() //detecting and updating collision of the bricks
    {
        
        if(collision) //if collision is detected, the colors of the grid where the brick last was is updated and the grid is changed to a new grid with the updated colors 
        {
            for(int i=0; i<coords.length;i++)    //checks if
            {    
                for(int j = 0; j<coords[0].length; j++)
                {
                    if(coords[i][j] != 0 && y>=0)
                    {
                        board.getGrid()[y+i][x+j] = color;
                    }
                }
            }
            
            checkLine();  //Check if line completed
            
            board.newShape();
            return;
        }
        
        boolean moveX=true; //boolean to define whether X movement is possible
        
        if((x+delX+coords[0].length <= 10) && (x+delX >= 0))   //Movement on x axis
        {
            for(int i=0;i<coords.length;i++)   //Checks if any other shapes are around
            {
                for(int j=0;j<coords[i].length;j++)
                {
                    if(coords[i][j]!=0 && y>-1)
                            {    
                                if(board.getGrid()[y+i][j+x+delX]!=null)
                                {
                                    moveX=false;
                                }
                            }
                }
            }
            if(moveX && !collision)
                 x+=delX;
        }
        delX=0;
        if(System.currentTimeMillis() - beginTime > moveDelay)  //movement on y axis
            {
                if(y+coords.length<20)  
                {
                    for(int i=0;i<coords.length;i++)
                    {
                        for(int j=0;j<coords[i].length;j++)
                        {
                            if(coords[i][j]!=0 && y>-2)
                            {    
                                if(board.getGrid()[y+1+i][j+x]!=null)  //Checks for every coloured cell of the current block if the cell below is empty or not
                                {
                                    collision=true;
                                }
                            }    
                        }
                    }
                    if(!collision)
                    {
                        y++;
                    }
                }
                else
                {
                    collision=true;
                }
                beginTime = System.currentTimeMillis();
            }
    }
    
    public void checkLine()
    {
        int bot=board.getGrid().length-1;
        for(int top=board.getGrid().length-1; top>0; top--)
        {
            int count=0;
            for(int col=0;col<board.getGrid()[0].length;col++)
            {
                if(board.getGrid()[top][col] != null)
                {
                    count++;
                }
                board.getGrid()[bot][col] = board.getGrid()[top][col];
            }
            if(count==10)
                board.score();
            if(count<board.getGrid()[0].length)
            {
                bot--;
            }
        }
    }
    
    public void rotate()     //rotate by 90 degree
    {
        if(y<0)       //to avoid arrayIndexOutOfBounds
            return;
        
        int[][]rotatedShape=transpose(coords);  //Rotation is achieved by transposing and reversing the rows of the shape matrix
        reverseRow(rotatedShape);
        
        if(x+rotatedShape[0].length>GameArea.gridCol || y+rotatedShape.length>GameArea.gridRow)   //to avoid arrayIndexOutOfBounds that may be caused by theshapes flipping
        {
            return;
        }
        
        for(int i=0;i<rotatedShape.length;i++)
        {
            for(int j=0;j<rotatedShape[i].length;j++)
            {
                if(rotatedShape[i][j] != 0)
                {
                    if(board.getGrid()[y+i][x+j] != null)
                    {
                        return;
                    }
                }
            }
        }
        
        coords=rotatedShape;
    }
    
    public int[][] transpose(int[][] matrix)
    {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for(int i=0;i<matrix.length;i++)
        {
            for(int j=0;j<matrix[0].length;j++)
            {
                temp[j][i]=matrix[i][j];
            }
        }
        return temp;
    }
    
    public void reverseRow(int[][] matrix)
    {
        int mid=matrix.length/2;
        for(int i=0;i<mid;i++)
        {
            int temp[] = matrix[i];
            matrix[i]=matrix[matrix.length-i-1];
            matrix[matrix.length-i-1]=temp;
        }
    }
    
    public void render(Graphics g)   //draws the current shape
    {
        for(int i=0; i<coords.length;i++)
        {    
            for(int j = 0; j<coords[0].length; j++)
            {
                if(coords[i][j] != 0 && i+y>=0)     //i+y>=0 checks if the shape is outside the boundaries
                {
                    g.setColor( this.color );
                    g.fillRect(boardX+ (j+x)*cellSize, boardY+ (i+y)*cellSize, cellSize, cellSize);
                    g.setColor( new Color(73,  77,  95 ) );  //border color
                    g.drawRect(boardX+ (j+x)*cellSize, boardY+ (i+y)*cellSize, cellSize, cellSize); //draw border
                }
            }
        }
    }
    
    public void setX(int x)
    {
        this.x=x;
    }
    public void setY(int y)
    {
        this.y=y;
    }
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }
    
    public void reset()
    {
        x=random.nextInt(1,6);
        y=-2;
        collision=false;
    }
    
    public int[][] getMat()
    {
        return this.coords;
    }
    
    public void speedUp()
    {
        moveDelay=fast;
    }
    public void speedDown()
    {
        moveDelay=normal;
    }
    public void moveLeft()
    {
        delX=-1;
    }
    public void moveRight()
    {
        delX=1;
    }
}
