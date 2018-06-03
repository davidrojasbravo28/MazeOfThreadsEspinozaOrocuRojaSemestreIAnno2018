package threads;

import domain.SynchronizedBuffer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
import static projectInterface.AnimationWindow.jlbTime;
import projectInterface.Maze;

public class Fast extends Thread {

    private SynchronizedBuffer myBuffer;
    private int direction, coordX, coordY, moves = 0;
    private BufferedImage image;
    private boolean wall;
    private Maze myMaze;
    private boolean fastLoading;//boolean, si Fast esta cargando deja de moverse
    private boolean pause;
    private boolean run;
    private String nameC;

    public Fast(SynchronizedBuffer myBuffer, Maze myMaze, int coordX, int coordY, String name)
            throws IOException {
        
        this.myMaze = myMaze;
        this.nameC = name;
        this.setName("Fast");
        this.myBuffer = myBuffer;
        
        this.direction = 3;
        
        this.coordX = coordX;
        this.coordY = coordY;
        
        this.image = ImageIO.read(new File("./ProjectImages/finAdelante2.png"));
        
        this.wall = false; //pared
        this.fastLoading=false;
        this.pause = false;
        this.run = true;

    }

    @Override
    public void run() {
        if(fastLoading==true){
            
        }
        getMyBuffer().set(coordY, coordX);
        myMaze.paintAgaint();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (run == true) {
            while (pause == true) {                              
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (moves == 10) {
                setFastLoading(true);
                //loading
                int second= 1;
                while(second<6){
                    try {
                        BufferedImage loadingImage= ImageIO.read(new File("./ProjectImages/finAdelanteload"+second+".png"));
                        setImage(loadingImage);
                        second++;
                        Thread.sleep(1000);
                    } catch (IOException ex) {
                        Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                }
                moves = 0;  
                try {
                    this.image= ImageIO.read(new File("./ProjectImages/finAdelante2.png"));
                    setImage(this.image);
                } catch (IOException ex) {
                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                }
                setFastLoading(false);
            }
            while (getMyBuffer().isOccupied() == true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            getMyBuffer().setOccupied(true);
            while (true) {
                int random = (int) (Math.random() * 4);
                if (random == 0) {
                    if (getCoordX() - 1 >= 0) {
                        if (getMyBuffer().get(getCoordY(), getCoordX() - 1) == 4) {
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordX(getCoordX() - 1);
                            myMaze.repaint();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.repaint();
                            myMaze.deleteFast(this.getNameC());
                            if (myMaze.getWinner() == 0) {
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
                                for (int i = 0; i < 30; i++) {
                                    try {
                                        Thread.sleep(70);
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

                            AnimationWindow.positions.append(getNameC() + ": "
                                    + AnimationWindow.jlbTime.getText() + "\n");
                            break;
                        } else if (getMyBuffer().get(getCoordY(), getCoordX() - 1) == 0) {
                            if (direction != 1 || wall == true) {
                                getMyBuffer().set(getCoordY(), getCoordX() - 1);
                                getMyBuffer().set(getCoordY(), getCoordX());
                                setCoordX(getCoordX() - 1);
                                direction = random;
                                wall = false;
                                break;
                            }
                        } else {
                            if (direction == 0) {
                                wall = true;
                            }
                        }

                    } else {
                        if (direction == 0) {
                            wall = true;
                        }
                    }
                } else if (random == 1) {
                    if (getCoordX() + 1 <= 16) {
                        if (getMyBuffer().get(getCoordY(), getCoordX() + 1) == 4) {
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordX(getCoordX() + 1);
                            myMaze.repaint();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.repaint();
                            myMaze.deleteFast(this.getNameC());
                            if (myMaze.getWinner() == 0) {
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
                                for (int i = 0; i < 30; i++) {
                                    try {
                                        Thread.sleep(70);
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

                            AnimationWindow.positions.append(getNameC() + ": "
                                    + AnimationWindow.jlbTime.getText() + "\n");
                            break;
                        } else if (getMyBuffer().get(getCoordY(), getCoordX() + 1) == 0) {
                            if (direction != 0 || wall == true) {
                                getMyBuffer().set(getCoordY(), getCoordX() + 1);
                                getMyBuffer().set(getCoordY(), getCoordX());
                                setCoordX(getCoordX() + 1);
                                direction = random;
                                wall = false;
                                break;
                            }
                        } else {
                            if (direction == 1) {
                                wall = true;
                            }
                        }

                    } else {
                        if (direction == 1) {
                            wall = true;
                        }
                    }
                } else if (random == 2) {

                    if (getCoordY() - 1 >= 0) {
                        if (getMyBuffer().get(getCoordY() - 1, getCoordX()) == 4) {
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordY(getCoordY() - 1);
                            myMaze.repaint();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.repaint();
                            myMaze.deleteFast(this.getNameC());
                            if (myMaze.getWinner() == 0) {
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
                                for (int i = 0; i < 30; i++) {
                                    try {
                                        Thread.sleep(70);
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

                            AnimationWindow.positions.append(getNameC() + ": "
                                    + AnimationWindow.jlbTime.getText() + "\n");
                            break;
                        } else if (getMyBuffer().get(getCoordY() - 1, getCoordX()) == 0) {
                            if (direction != 3 || wall == true) {
                                getMyBuffer().set(getCoordY() - 1, getCoordX());
                                getMyBuffer().set(getCoordY(), getCoordX());
                                setCoordY(getCoordY() - 1);
                                direction = random;
                                wall = false;
                                break;
                            }
                        } else {
                            if (direction == 2) {
                                wall = true;
                            }
                        }
                    } else {
                        if (direction == 2) {
                            wall = true;
                        }
                    }
                } else if (random == 3) {
                    if (getCoordY() + 1 <= 14) {
                        if (getMyBuffer().get(getCoordY() + 1, getCoordX()) == 4) {
                            getMyBuffer().set(getCoordY(), getCoordX());
                            setCoordY(getCoordY() + 1);
                            myMaze.repaint();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            myMaze.repaint();
                            myMaze.deleteFast(this.getNameC());
                            if (myMaze.getWinner() == 0) {
                                myMaze.setWinner(1);
                                try {
                                    myMaze.pause();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                myMaze.setNameWinner(this.getNameC());
                                Sound sound = null;
                                try {
                                    sound = new Sound();
                                    sound.start();
                                } catch (LineUnavailableException ex) {
                                    Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                for (int i = 0; i < 30; i++) {
                                    try {
                                        Thread.sleep(70);
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

                            AnimationWindow.positions.append(getNameC() + ": "
                                    + AnimationWindow.jlbTime.getText() + "\n");
                            break;
                        } else if (getMyBuffer().get(getCoordY() + 1, getCoordX()) == 0) {
                            if (direction != 2 || wall == true) {
                                getMyBuffer().set(getCoordY() + 1, getCoordX());
                                getMyBuffer().set(getCoordY(), getCoordX());
                                setCoordY(getCoordY() + 1);
                                direction = random;
                                wall = false;
                                break;
                            }
                        } else {
                            if (direction == 3) {
                                wall = true;
                            }
                        }
                    } else {
                        if (direction == 3) {
                            wall = true;
                        }
                    }
                }
            }
            moves++;
            myMaze.paintAgaint();
            getMyBuffer().setOccupied(false);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Fast.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private BufferedImage copy() {
        ColorModel cm = getActualImageToEdit().getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = getActualImageToEdit().copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    private BufferedImage getActualImageToEdit(){
        return this.image;
    }

    public void pause() {
        this.pause = true;
    }

    public void playAgain() {
        this.pause = false;
    }

    public void brake() {
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

    public BufferedImage getImage() {
        if (direction == 3) {
            BufferedImage ima1 = copy();
            Graphics gr = ima1.getGraphics();
            gr.setColor(Color.black);
            gr.setFont(new Font("Arial", Font.PLAIN, 12));
            gr.drawString(this.getNameC(), 10, 10);

            AffineTransform tx = AffineTransform.getRotateInstance(1.5, ima1.getWidth() / 2, ima1.getHeight() / 2);
            AffineTransformOp opTranform = new AffineTransformOp(tx,
                    AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            ima1 = opTranform.filter(ima1, null);
            return ima1;
        }
        if (direction == 2) {
            BufferedImage ima2 = copy();
            Graphics gr = ima2.getGraphics();
            gr.setColor(Color.black);
            gr.setFont(new Font("Arial", Font.PLAIN, 12));
            gr.drawString(this.getNameC(), 10, 10);
            AffineTransform tx = AffineTransform.getRotateInstance(4.7, ima2.getWidth() / 2, ima2.getHeight() / 2);
            AffineTransformOp opTranform = new AffineTransformOp(tx,
                    AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            ima2 = opTranform.filter(ima2, null);
            return ima2;
        }
        if (direction == 0) {
            BufferedImage ima3 = copy();
            AffineTransform flipHorizontal = AffineTransform.getScaleInstance(-1,
                    1);
            flipHorizontal.translate(-ima3.getHeight(null), 0);

            AffineTransformOp opTranform = new AffineTransformOp(flipHorizontal,
                    AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            ima3 = opTranform.filter(ima3, null);
            Graphics gr = ima3.getGraphics();
            gr.setColor(Color.black);
            gr.setFont(new Font("Arial", Font.PLAIN, 12));
            gr.drawString(this.getNameC(), 10, 10);
            return ima3;
        }
        if (direction == 1) {
            BufferedImage ima4 = copy();
            Graphics gr = ima4.getGraphics();
            gr.setColor(Color.black);
            gr.setFont(new Font("Arial", Font.PLAIN, 12));
            gr.drawString(this.getNameC(), 10, 10);
            return ima4;
        }

        return null;
    }


    public void setImage(BufferedImage image) {
        this.image = image;
    }


    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
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

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }
    
        /**
     * @return the fastLoading
     */
    public boolean isFastLoading() {
        return fastLoading;
    }

    /**
     * @param fastLoading the fastLoading to set
     */
    public void setFastLoading(boolean fastLoading) {
        this.fastLoading = fastLoading;
    }

}
