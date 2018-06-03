package threads;

import domain.SynchronizedBuffer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import projectInterface.AnimationWindow;
import projectInterface.Maze;

public class Furious extends Thread{
    
    private SynchronizedBuffer myBuffer;
    private int direction, coordX, coordY;
    private BufferedImage image;
    private boolean wall;
    private Maze myMaze;
    private boolean pause;
    private boolean run;
    private String nameC;
    
    public Furious(SynchronizedBuffer myBuffer, Maze maze, int coordX, int coordY, String name)
            throws IOException{
        this.setName("Furious");
        this.nameC = name;
        this.myBuffer = myBuffer;
        this.myMaze = maze;
        this.direction = 3;
        this.coordX = coordX;
        this.coordY = coordY;
        this.image = ImageIO.read(new File("./ProjectImages/cakeAbajo2.png"));
        this.wall = false;
        this.pause = false;
        this.run = true;
        
    }

    @Override
    public void run() {
        getMyBuffer().set(coordY, coordX);
        myMaze.paintAgaint();
        try {
            Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Furious.class.getName()).log(Level.SEVERE, null, ex);
            }
        while(run == true){
            while(pause == true)
            {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Furious.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            while(getMyBuffer().isOccupied() == true){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Furious.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            getMyBuffer().setOccupied(true);
            while(true)
            {  
                int random = (int) (Math.random()*4);
                if(random == 0){
                    if(getCoordX() -1 >= 0)
                    {
                        if(getMyBuffer().get(getCoordY(),getCoordX()-1) == 4)
                        {
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordX(getCoordX() - 1);
                            myMaze.repaint();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.repaint();
                            myMaze.deleteFurious(this.getNameC());
                            if(myMaze.getWinner() == 0) {
                                    myMaze.setWinner(1);
                                try {
                                    myMaze.pause();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                myMaze.setNameWinner(this.getNameC());
                                Sound sound;
                                try {
                                    sound = new Sound();
                                    sound.start();
                                } catch (LineUnavailableException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                for(int i=0; i<30; i++){
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    myMaze.repaint();
                                }
                                try {
                                    myMaze.resume();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            myMaze.setWinner(2);
                            myMaze.repaint();
                            this.brake();
      
                            AnimationWindow.positions.append(getNameC()+": "+ 
                                    AnimationWindow.jlbTime.getText() + "\n");
                            break;
                        }
                        //Si se encuentra con el item lo elimina
                        else if(getMyBuffer().get(getCoordY(),getCoordX()-1) == 3)
                        {
                            myMaze.animatedDeleteItemCoord(getCoordY(), getCoordX()-1);
                            try {
                                Thread.sleep(3003);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Furious.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.deleteItemCoord(getCoordY(), getCoordX()-1);
                            myBuffer.setZero(getCoordY(),getCoordX()-1);
                            break;                            
                        }
                        else if(getMyBuffer().get(getCoordY(),getCoordX()-1) ==0)
                        {
                            if(direction != 1 || wall == true)
                            {
                            getMyBuffer().set(getCoordY(),getCoordX()-1);
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordX(getCoordX() - 1);
                            direction = random;
                            wall = false;
                            break;
                            }
                        }
                        else
                        {
                            if(direction == 0)
                                wall = true;
                        }
                    
                    }
                    else
                        {
                            if(direction == 0)
                                wall = true;
                        }
                }
                else if(random == 1){
                    if(getCoordX() +1 <= 16)
                    {
                        if(getMyBuffer().get(getCoordY(),getCoordX()+1) == 4)
                        {
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordX(getCoordX() + 1);
                            myMaze.repaint();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.repaint();
                            myMaze.deleteFurious(this.getNameC());
                            if(myMaze.getWinner() == 0) {
                                    myMaze.setWinner(1);
                                try {
                                    myMaze.pause();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                myMaze.setNameWinner(this.getNameC());
                                Sound sound;
                                try {
                                    sound = new Sound();
                                    sound.start();
                                } catch (LineUnavailableException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                for(int i=0; i<30; i++){
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    myMaze.repaint();
                                }
                                try {
                                    myMaze.resume();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            myMaze.setWinner(2);
                            myMaze.repaint();
                            this.brake();
      
                            AnimationWindow.positions.append(getNameC()+": "+ 
                                    AnimationWindow.jlbTime.getText() + "\n");
                            break;
                        }
                        //Si se encuentra con el item lo elimina
                        else if(getMyBuffer().get(getCoordY(),getCoordX()+1) == 3)
                        {
                            myMaze.animatedDeleteItemCoord(getCoordY(), getCoordX()+1);
                            try {
                                Thread.sleep(3003);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Furious.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.deleteItemCoord(getCoordY(), getCoordX()+1);
                            myBuffer.setZero(getCoordY(),getCoordX()+1);
                            break;                            
                        }//else if
                        
                        else if(getMyBuffer().get(getCoordY(),getCoordX()+1) ==0)
                        {
                            if(direction != 0 || wall == true)
                            {
                            getMyBuffer().set(getCoordY(),getCoordX()+1);
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordX(getCoordX() + 1);
                            direction = random;
                            wall = false;
                            break;
                            }
                        }
                        else
                        {
                            if(direction == 1)
                                wall = true;
                        }
                    
                }
                    else
                        {
                            if(direction == 1)
                                wall = true;
                        }
                }
                else if(random == 2){
                    
                    if(getCoordY() -1 >= 0)
                    {
                        if(getMyBuffer().get(getCoordY()-1, getCoordX()) == 4)
                        {
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordY(getCoordY() - 1);
                            myMaze.repaint();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.repaint();
                            myMaze.deleteFurious(this.getNameC());
                            if(myMaze.getWinner() == 0) {
                                    myMaze.setWinner(1);
                                try {
                                    myMaze.pause();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                myMaze.setNameWinner(this.getNameC());
                                Sound sound;
                                try {
                                    sound = new Sound();
                                    sound.start();
                                } catch (LineUnavailableException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                for(int i=0; i<30; i++){
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    myMaze.repaint();
                                }
                                try {
                                    myMaze.resume();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            myMaze.setWinner(2);
                            myMaze.repaint();
                            this.brake();
      
                            AnimationWindow.positions.append(getNameC()+": "+ 
                                    AnimationWindow.jlbTime.getText() + "\n");
                            break;
                        }
                        //Si se encuentra con el item lo elimina
                        else if(getMyBuffer().get(getCoordY()-1,getCoordX()) == 3)
                        {
                            myMaze.animatedDeleteItemCoord(getCoordY()-1, getCoordX());
                            try {
                                Thread.sleep(3003);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Furious.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.deleteItemCoord(getCoordY()-1, getCoordX());
                            myBuffer.setZero(getCoordY()-1,getCoordX());
                            break;

                            
                        }
                        else if(getMyBuffer().get(getCoordY()-1, getCoordX()) ==0)
                        {
                            if(direction != 3 || wall == true)
                        {
                            getMyBuffer().set(getCoordY()-1, getCoordX());
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordY(getCoordY() - 1);
                            direction = random;
                            wall = false;
                            break;
                        }
                    }
                        else
                        {
                            if(direction == 2)
                                wall = true;
                        }
                    }
                    else
                        {
                            if(direction == 2)
                                wall = true;
                        }
                }
                else if(random == 3){
                    if(getCoordY() +1 <= 14)
                    {
                        if(getMyBuffer().get(getCoordY()+1, getCoordX()) == 4)
                        {
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordY(getCoordY() + 1);
                            myMaze.repaint();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.repaint();
                            myMaze.deleteFurious(this.getNameC());
                            if(myMaze.getWinner() == 0) {
                                    myMaze.setWinner(1);
                                try {
                                    myMaze.pause();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                myMaze.setNameWinner(this.getNameC());
                                Sound sound;
                                try {
                                    sound = new Sound();
                                    sound.start();
                                } catch (LineUnavailableException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                for(int i=0; i<30; i++){
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    myMaze.repaint();
                                }
                                try {
                                    myMaze.resume();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            myMaze.setWinner(2);
                            myMaze.repaint();
                            this.brake();
      
                            AnimationWindow.positions.append(getNameC()+": "+ 
                                    AnimationWindow.jlbTime.getText() + "\n");
                            break;
                        }
                        //Si se encuentra con el item lo elimina
                        else if(getMyBuffer().get(getCoordY()+1,getCoordX()) == 3)
                        {
                            myMaze.animatedDeleteItemCoord(getCoordY()+1, getCoordX());
                            try {
                                Thread.sleep(3003);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Furious.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.deleteItemCoord(getCoordY()+1, getCoordX());
                            myBuffer.setZero(getCoordY()+1,getCoordX());
                            break;
                            
                        }
                        else if(getMyBuffer().get(getCoordY()+1, getCoordX()) ==0)
                        {
                            if(direction !=2 || wall == true)
                            {
                            getMyBuffer().set(getCoordY()+1, getCoordX());
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordY(getCoordY() + 1);
                            direction = random;
                            wall = false;
                            break;
                            }
                        }
                        else
                        {
                           if(direction == 3)
                                wall = true;
                        }
                    }
                    else
                        {
                            if(direction == 3)
                                wall = true;
                        }
                }
            }
            myMaze.paintAgaint();
            getMyBuffer().setOccupied(false);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Furious.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
    }
    
    private  BufferedImage copy()
    {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    public BufferedImage getImage() {
        if(direction ==3)
        {
        BufferedImage ima1 = copy();
        Graphics gr = ima1.getGraphics();
        gr.setColor(Color.blue);
        gr.setFont( new Font("Arial",Font.PLAIN,12));
        gr.drawString(this.getNameC(),10,10);    
        
        AffineTransform tx = AffineTransform.getRotateInstance(1.5,ima1.getWidth()/2, ima1.getHeight()/2);
        AffineTransformOp opTranform = new AffineTransformOp(tx,
                                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        ima1 = opTranform.filter(ima1, null);
        return ima1;
        }
        if(direction == 2)
        {
        BufferedImage ima2 = copy();
        Graphics gr = ima2.getGraphics();
        gr.setColor(Color.blue);
        gr.setFont( new Font("Arial",Font.PLAIN,12));
        gr.drawString(this.getNameC(),10,10);
        AffineTransform tx = AffineTransform.getRotateInstance(4.7,ima2.getWidth()/2, ima2.getHeight()/2);
        AffineTransformOp opTranform = new AffineTransformOp(tx,
                                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        ima2 = opTranform.filter(ima2, null);
        return ima2;
        }
        if(direction == 0)
        {
            BufferedImage ima3 = copy();
            AffineTransform flipHorizontal = AffineTransform.getScaleInstance(-1,
                    1);
            flipHorizontal.translate(-ima3.getHeight(null), 0);

            AffineTransformOp opTranform = new AffineTransformOp(flipHorizontal,
                    AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            ima3 = opTranform.filter(ima3, null);
            Graphics gr = ima3.getGraphics();
            gr.setColor(Color.blue);
            gr.setFont( new Font("Arial",Font.PLAIN,12));
            gr.drawString(this.getNameC(),10,10);
            return ima3;
        }
        if(direction == 1)
        {
            BufferedImage ima4 = copy();
            Graphics gr = ima4.getGraphics();
            gr.setColor(Color.blue);
            gr.setFont( new Font("Arial",Font.PLAIN,12));
            gr.drawString(this.getNameC(),10,10);
            return ima4;
        }
        
        return null;
    }
    
    public void pause() 
    {
        this.pause = true;
    }
    
    public void playAgain()
    {
        this.pause = false;
    }
    public void brake()
    {
        this.run = false;
    }
    /**
     * @return the myBuffer
     */
    public SynchronizedBuffer getMyBuffer() {
        return myBuffer;
    }

    /**
     * @param myBuffer the myBuffer to set
     */
    public void setMyBuffer(SynchronizedBuffer myBuffer) {
        this.myBuffer = myBuffer;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return the coordX
     */
    public int getCoordX() {
        return coordX;
    }

    /**
     * @param coordX the coordX to set
     */
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    /**
     * @return the coordY
     */
    public int getCoordY() {
        return coordY;
    }

    /**
     * @param coordY the coordY to set
     */
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    /**
     * @param image the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * @return the wall
     */
    public boolean isWall() {
        return wall;
    }

    /**
     * @param wall the wall to set
     */
    public void setWall(boolean wall) {
        this.wall = wall;
    }

    /**
     * @return the myMaze
     */
    public Maze getMyMaze() {
        return myMaze;
    }

    /**
     * @param myMaze the myMaze to set
     */
    public void setMyMaze(Maze myMaze) {
        this.myMaze = myMaze;
    }

    /**
     * @return the pause
     */
    public boolean isPause() {
        return pause;
    }

    /**
     * @param pause the pause to set
     */
    public void setPause(boolean pause) {
        this.pause = pause;
    }

    /**
     * @return the run
     */
    public boolean isRun() {
        return run;
    }

    /**
     * @param run the run to set
     */
    public void setRun(boolean run) {
        this.run = run;
    }

    /**
     * @return the nameC
     */
    public String getNameC() {
        return nameC;
    }

    /**
     * @param nameC the nameC to set
     */
    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    
}
