package threads;

import domain.SynchronizedBuffer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import projectInterface.Maze;


public class MItem extends Thread{
    
    private SynchronizedBuffer myBuffer;
    private int direction, coordX, coordY;
    private boolean wall;
    private Maze myMaze;
    private BufferedImage image;
    private boolean pause;
    private boolean destroy;
    private boolean run;
    private String nameC;
    
    public MItem(SynchronizedBuffer myBuffer, Maze maze,String name) throws IOException{
        this.setName("MItem");
        this.nameC = name;
        this.myBuffer = myBuffer;
        this.direction = 0;
        this.coordX = 0;
        this.coordY = 0;
        this.image = ImageIO.read(new File("./ProjectImages/item.png"));
        this.wall = false;
        this.myMaze = maze;
        this.pause = false;
        this.destroy=false;
        this.run = true;
    }

    @Override
    public void run() {

        //animacion de la destruccion del item por el personaje Furious
        if(isDestroy()==true){
            try {
                int counter= 1;
                while(counter<4){

                    BufferedImage imageDestruction= ImageIO.read(new File("./ProjectImages/itemdestroyed"+counter+".png"));
                    setImage(imageDestruction);
                    System.out.println("./ProjectImages/itemdestroyed"+counter+".png");
                    counter++;
                    Thread.sleep(1000);
                }//while
                brake();
            } catch (InterruptedException ex) {
                Logger.getLogger(MItem.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        getMyBuffer().set(coordY, coordX);
        myMaze.paintAgaint();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MItem.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (run == true) {
            while(pause == true)
            {
             try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
            }   
            }
            
             if(isDestroy()==true){
            try {
                int counter= 1;
                while(counter<4){

                    BufferedImage imageDestruction= ImageIO.read(new File("./ProjectImages/itemdestroyed"+counter+".png"));
                    setImage(imageDestruction);
                    System.out.println("./ProjectImages/itemdestroyed"+counter+".png");
                    counter++;
                    Thread.sleep(1000);
                }//while
                brake();
            } catch (InterruptedException ex) {
                Logger.getLogger(MItem.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

            while (getMyBuffer().isOccupied() == true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MItem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            getMyBuffer().setOccupied(true);
            OUTER:
            OUTER_1:
            OUTER_2:
            OUTER_3:
            while (true) {
                if (this.direction == 0) {
                    if (getCoordX() - 1 >= 0) {
                        switch (getMyBuffer().get(getCoordY(), getCoordX() - 1)) {
                            case 2:
                                getMyBuffer().set(getCoordY(), getCoordX());
                                myMaze.deleteItem(this.getNameC());
                                myMaze.repaint();
                                myMaze.reduce(getCoordY(), getCoordX() - 1);
                                this.brake();
                                break OUTER;
                            case 0:
                                getMyBuffer().set(getCoordY(), getCoordX() - 1);
                                getMyBuffer().set(getCoordY(), getCoordX());
                                setCoordX(getCoordX() - 1);
                                break OUTER;
                            default:
                                direction = 1;
                                break;
                        }
                    } else {
                        this.direction = 1;
                    }
                } else if (this.direction == 1) {
                    if (getCoordX() + 1 <= 16) {
                        switch (getMyBuffer().get(getCoordY(), getCoordX() + 1)) {
                            case 2:
                                getMyBuffer().set(getCoordY(), getCoordX());
                                myMaze.deleteItem(this.getNameC());
                                myMaze.reduce(getCoordY(), getCoordX() - 1);
                                myMaze.repaint();
                                this.brake();
                                break OUTER_1;
                            case 0:
                                getMyBuffer().set(getCoordY(), getCoordX() + 1);
                                getMyBuffer().set(getCoordY(), getCoordX());
                                setCoordX(getCoordX() + 1);
                                break OUTER_1;
                            default:
                                direction = 0;
                                break;
                        }
                    } else {
                        this.direction = 0;
                    }
                }
                if (direction == 2) {
                    if (getCoordY() - 1 >= 0) {
                        switch (getMyBuffer().get(getCoordY()-1, getCoordX())) {
                            case 2:
                                getMyBuffer().set(getCoordY(), getCoordX());
                                myMaze.deleteItem(this.getNameC());
                                myMaze.repaint();
                                myMaze.reduce(getCoordY()-1, getCoordX());
                                this.brake();
                                break OUTER_2;
                            case 0:
                                getMyBuffer().set(getCoordY() - 1, getCoordX());
                                getMyBuffer().set(getCoordY(), getCoordX());
                                setCoordY(getCoordY() - 1);
                                break OUTER_2;
                            default:
                                direction = 3;
                                break;
                        }
                    } else {
                        direction = 3;
                    }
                } else if (direction == 3) {
                    if (getCoordY() + 1 <= 14) {
                        switch (getMyBuffer().get(getCoordY()+1, getCoordX())) {
                            case 2:
                                getMyBuffer().set(getCoordY(), getCoordX());
                                myMaze.deleteItem(this.getNameC());
                                myMaze.repaint();
                                myMaze.reduce(getCoordY()+1, getCoordX());
                                this.brake();
                                break OUTER_3;
                            case 0:
                                getMyBuffer().set(getCoordY() + 1, getCoordX());
                                getMyBuffer().set(getCoordY(), getCoordX());
                                setCoordY(getCoordY() + 1);
                                break OUTER_3;
                            default:
                                this.direction = 2;
                                break;
                        }
                    } else {
                        this.direction = 2;
                    }
                }
            }
            myMaze.paintAgaint();
            getMyBuffer().setOccupied(false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void choiceDirection() {
        while (true) {
            int rnd = (int) (Math.random() * 4);
            if (rnd == 0) {
                if (getCoordX() - 1 >= 0) {
                    if (getMyBuffer().get(getCoordY(), getCoordX() - 1) == 0) {
                        setDirection(rnd);
                        break;
                    }
                }

            }
            if (rnd == 1) {
                if (getCoordX() + 1 <= 16) {
                    if (getMyBuffer().get(getCoordY(), getCoordX() + 1) == 0) {
                        setDirection(rnd);
                        break;
                    }
                }
            }
            if (rnd == 2) {
                if (getCoordY() - 1 >= 0) {
                    if (getMyBuffer().get(getCoordY() - 1, getCoordX()) == 0) {
                        setDirection(rnd);
                        break;
                    }

                }
            }
            if (rnd == 3) {
                if (getCoordY() + 1 <= 14) {
                    if (getMyBuffer().get(getCoordY() + 1, getCoordX()) == 0) {
                        setDirection(rnd);
                        break;
                    }

                }

            }

        }
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
    
    public SynchronizedBuffer getMyBuffer() {
        return myBuffer;
    }

    public void setMyBuffer(SynchronizedBuffer myBuffer) {
        this.myBuffer = myBuffer;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public Maze getMyMaze() {
        return myMaze;
    }

    public void setMyMaze(Maze myMaze) {
        this.myMaze = myMaze;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String name) {
        this.nameC = name;
    }
    
        /**
     * @return the destroy
     */
    public boolean isDestroy() {
        return destroy;
    }

    /**
     * @param destroy the destroy to set
     */
    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
    
}
