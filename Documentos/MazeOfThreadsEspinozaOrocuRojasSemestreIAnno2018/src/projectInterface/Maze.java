package projectInterface;

import domain.Block;
import domain.SynchronizedBuffer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import threads.Fast;
import threads.Furious;
import threads.MItem;
import threads.Smart;
import static projectInterface.AnimationWindow.jlbTime;

public class Maze extends JPanel implements MouseListener {

    private Graphics2D graphics2D;
    private SynchronizedBuffer myBuffer;
    private ArrayList<Block> blocks;
    private ArrayList<Fast> faster;
    private ArrayList<MItem> items;
    private ArrayList<Furious> furious;
    private ArrayList<Smart> smart;
    private BufferedImage flag;
    private BufferedImage cup;
    private Image image;
    private int winner;
    private int quantityFast, quantityFurious, quantitySmart, quantityItems;
    private int[] entranceExit;
    private String nameWinner;
  
    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg); //To change body of generated methods, choose Tools | Templates.
    }
        
    public Maze(SynchronizedBuffer myBuffer, int fast, int furious, int smart, int item, 
            ArrayList<Block> blocks, int[] entranceExit) throws IOException {
        this.myBuffer = myBuffer;
        this.blocks = new ArrayList<>();
        this.blocks = blocks;
        
        this.updateBuffer(blocks);
        this.setSize(681, 601);
        this.addMouseListener(this);
        this.setVisible(true);
        
        this.entranceExit = entranceExit;
        this.faster = new ArrayList<>();
        this.furious = new ArrayList<>();
        this.items = new ArrayList<>();
        this.smart = new ArrayList<>();
        this.flag = ImageIO.read(new File("./ProjectImages/flag.png"));
        this.cup = ImageIO.read(new File("./ProjectImages/cupChampion.png"));
        
        this.quantityFast = fast;
        this.quantityFurious = furious;
        this.quantitySmart = smart;
        this.quantityItems = item;
        
        if (entranceExit.length == 4) {
            myBuffer.setExit(entranceExit[2], entranceExit[3]);
        } else {
            myBuffer.setExit(entranceExit[2], entranceExit[3]);
            myBuffer.setExit(entranceExit[6], entranceExit[7]);
        }

        this.winner = 0;
       
        Block.block = blocks.get(0).getBlock();

        try {
            this.image = ImageIO.read(new File("blanco.png"));
        } catch (IOException ex) {
            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initImage() {
        try {
            this.image = ImageIO.read(new File("blanco.png"));
        } catch (IOException ex) {
            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//fondo del laberinto

    public void pause() throws InterruptedException {

        for (Fast faster1 : faster) {
            faster1.pause();
        }
        for (Furious furiou : furious) {
            furiou.pause();
        }
        for (Smart smart1 : smart) {
            smart1.pause();
        }
        for (MItem item: items){
            item.pause();
        }
    }//boton de pausa 

    public void resume() throws InterruptedException {
        for (Fast faster1 : faster) {
            faster1.playAgain();
        }
        for (Furious furiou : furious) {
            furiou.playAgain();
        }
        for (MItem item: items){
            item.playAgain();
        }
        for (Smart smart1 : smart) {
            smart1.playAgain();
        }
    }//boton de play

    public void stop() throws InterruptedException {
        for (Fast faster1 : faster) {
            faster1.brake();
        }
        for (Furious furiou : furious) {
            furiou.brake();
        }
        for (Smart smart1 : smart) {
            smart1.brake();
        }
        for (MItem item : items) {
            item.brake();
        }
    }//boton de stop para detener por completo el hilo
   
    private void paintMaze(Graphics2D graphics2D) {
        if (this.entranceExit.length == 4) {
            graphics2D.drawImage(flag, entranceExit[3] * 40, entranceExit[2] * 40, 40, 40, this);
        } else {
            graphics2D.drawImage(flag, entranceExit[3] * 40, entranceExit[2] * 40, 40, 40, this);
            graphics2D.drawImage(flag, entranceExit[7] * 40, entranceExit[6] * 40, 40, 40, this);
        }
        for (int i = 0; i < blocks.size(); i++) {
            graphics2D.drawImage(blocks.get(i).getBlock(), blocks.get(i).getCoordX(),
                    blocks.get(i).getCoordY(), 40, 40, null);
        }
        for (Fast fast : faster) {
            graphics2D.setColor(Color.blue);
            graphics2D.drawImage(fast.getImage(), fast.getCoordX() * 40, fast.getCoordY() * 40, 40, 40, this);
        }
        for (Furious furious1 : furious) {
            graphics2D.setColor(Color.green);
            graphics2D.drawImage(furious1.getImage(), furious1.getCoordX() * 40, furious1.getCoordY() * 40, 40, 40, this);
        }
        for (Smart smart1 : smart) {
            graphics2D.setColor(Color.red);
            graphics2D.drawImage(smart1.getImage(), smart1.getCoordX() * 40, smart1.getCoordY() * 40, 40, 40, this);
        }
        for (MItem item : items) {
            graphics2D.setColor(Color.yellow);
            graphics2D.drawImage(item.getImage(), item.getCoordX() * 40, item.getCoordY() * 40, 40, 40, this);
        }

        if (this.getWinner() == 1) {
            graphics2D.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            graphics2D.fillRect(215, 80, 30, 30);
            graphics2D.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            graphics2D.fillRect(255, 60, 30, 30);
            graphics2D.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            graphics2D.fillRect(295, 40, 30, 30);
            graphics2D.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            graphics2D.fillRect(335, 20, 30, 30);
            graphics2D.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            graphics2D.fillRect(375, 40, 30, 30);
            graphics2D.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            graphics2D.fillRect(415, 60, 30, 30);
            graphics2D.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            graphics2D.fillRect(455, 80, 30, 30);
            graphics2D.drawImage(cup, 200, 120, 300, 300, this);
            graphics2D.drawString(this.getNameWinner(), 300, 450);
        }//Cuando llega el primer Thread, se pinta una imagen con cuadros de coleres indicando que hubo un ganador
    }

    public void deleteFast(String name) {
        for (int i = 0; i < faster.size(); i++) {
            if (faster.get(i).getNameC().equals(name)) {
                faster.remove(i);
            }
        }
    }

    public void deleteFurious(String name) {
        for (int i = 0; i < furious.size(); i++) {
            if (furious.get(i).getNameC().equals(name)) {

                furious.remove(i);
            }
        }
    }

    public void deleteSmart(String name) {
        for (int i = 0; i < smart.size(); i++) {
            if (smart.get(i).getNameC().equals(name)) {
                smart.remove(i);
            }
        }
    }

    public void deleteItem(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getNameC().equals(name)) {
                items.remove(i);//Cambios..................................
            }
        }
    }

    public void deleteItemCoord(int coordY, int coordX) {
        for (int i = 0; i < items.size(); i++) {
            if (coordX == items.get(i).getCoordX() && coordY == items.get(i).getCoordY())//Cambios..................................
            {
                items.get(i).setDestroy(true);
            }
        }
    }
    
    //anima la destrucción del item
    public void animatedDeleteItemCoord(int coordY, int coordX){
        for (int i = 0; i < items.size(); i++) {
            if (coordX == items.get(i).getCoordX() && coordY == items.get(i).getCoordY())//Cambios..................................
            {
                items.get(i).setDestroy(true);
                items.remove(i);
            }
        }
    }

    public void reduce(int coordY, int coordX) {
        for (Smart smart1 : smart) {
            if (coordX == smart1.getCoordX() && coordY == smart1.getCoordY()) {//Cambios..................................
                smart1.reduceTime();

            }
        }
    }

    public void evaluatingBlock(MouseEvent myEvent) throws IOException {
        if (myEvent.getX() / 40 == entranceExit[3] && myEvent.getY() / 40 == entranceExit[2]) {
            myBuffer.setExit(entranceExit[2], entranceExit[3]);
        } else {
            myBuffer.set(myEvent.getY() / 40, myEvent.getX() / 40);
        }
        addBlock((myEvent.getX() / 40) * 40, (myEvent.getY() / 40) * 40);
    }

    public void addBlock(int x, int y) throws IOException {
        boolean exists = false;
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getCoordX() == x && blocks.get(i).getCoordY() == y) {
                blocks.remove(i);
                exists = true;
            }
        }
        if (exists == false) {
            blocks.add(new Block(x, y));
        }
        repaint();
    }

    public void paintAgaint() {
        repaint();
        SwingUtilities.updateComponentTreeUI(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphics2D = (Graphics2D) getGraphicsImage();
        this.paintMaze(graphics2D);
        g.drawImage(image, 0, 0, this);
        initImage();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if ((me.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK) {
            myBuffer.setOccupied(true);
            try {
                if (validatePosition(me.getX() / 40, me.getY() / 40) == false) {
                    evaluatingBlock(me);
                }
            } catch (IOException ex) {
                Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
            }
            myBuffer.setOccupied(false);
        } else {
            try {
                if (validatePosition(me.getX() / 40, me.getY() / 40) == false
                        && getMyBuffer().get(me.getY() / 40, me.getX() / 40) != 1) {
                    MItem item = new MItem(myBuffer, this, "MItem " + Integer.toString(quantityItems));
                    quantityItems++;
                    item.setCoordX(me.getX() / 40);
                    item.setCoordY(me.getY() / 40);
                    item.choiceDirection();
                    this.items.add(item);
                    this.paintAgaint();
                    item.start();
                }
            } catch (IOException e) {
            }

        }
    }

    public void updateBuffer(ArrayList<Block> blocks) {
        for (int i = 0; i < blocks.size(); i++) {
            myBuffer.set(blocks.get(i).getCoordY() / 40, blocks.get(i).getCoordX() / 40);
        }
    }

    public boolean validatePosition(int coordX, int coordY) {
        for (Fast fast : faster) {
            if (coordX == fast.getCoordX() && coordY == fast.getCoordY()) {
                return true;
            }
        }
        for (Furious furious1 : furious) {
            if (coordX == furious1.getCoordX() && coordY == furious1.getCoordY()) {
                return true;
            }
        }
        for (Smart smart1 : smart) {
            if (coordX == smart1.getCoordX() && coordY == smart1.getCoordY()) {
                return true;
            }
        }
        for (MItem item : items) {
            if (coordX == item.getCoordX() && coordY == item.getCoordY()) {
                return true;
            }
        }
        return false;
    }

    public Graphics getGraphicsImage() {
        return this.image.getGraphics();
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    public void createAndStart() {
        Runnable myRunnable = () -> {
            int fast1 = quantityFast;
            int furious1 = quantityFurious;
            int item= quantityItems;
            int smart1 = quantitySmart;
            int total = fast1 + furious1 + smart1;
            int i = 0;
            boolean entrance = true;
            while (total > i) {
                Random rnd = new Random();
                int num = (int) (rnd.nextDouble() * 3 + 1);
                
                switch (num) {
                    
                    case 1:
                        if (fast1 != 0) {

                            if (this.entranceExit.length == 8) {

                                if (entrance == true) {

                                    if (getMyBuffer().get(entranceExit[0], entranceExit[1]) != 1
                                            && getMyBuffer().get(entranceExit[0], entranceExit[1]) != 2) {

                                        try {
                                            this.faster.add(new Fast(myBuffer, this,
                                                    entranceExit[1], entranceExit[0],
                                                    Integer.toString(i + 1)));

                                        } catch (IOException ex) {
                                            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        i++;
                                        fast1--;
                                        this.faster.get(faster.size() - 1).start();
                                        entrance = false;

                                    } else {
                                        entrance = false;
                                    }

                                } else {

                                    if (getMyBuffer().get(entranceExit[4], entranceExit[5]) != 1
                                            && getMyBuffer().get(entranceExit[4], entranceExit[5]) != 2) {

                                        try {
                                            this.faster.add(new Fast(myBuffer, this,
                                                    entranceExit[1], entranceExit[0],
                                                    Integer.toString(i + 1)));
                                        } catch (IOException ex) {
                                            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        i++;
                                        fast1--;
                                        this.faster.get(faster.size() - 1).start();
                                        entrance = true;
                                    } else {
                                        entrance = true;
                                    }
                                }

                            } else {

                                if (getMyBuffer().get(entranceExit[0], entranceExit[1]) != 1
                                        && getMyBuffer().get(entranceExit[0], entranceExit[1]) != 2) {

                                    try {
                                        this.faster.add(new Fast(myBuffer, this,
                                                entranceExit[1], entranceExit[0],
                                                Integer.toString(i + 1)));
                                    } catch (IOException ex) {
                                        Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    i++;
                                    fast1--;
                                    this.faster.get(faster.size() - 1).start();
                                }

                            }

                        }
                        break;
                    case 2:
                        if (furious1 != 0) {

                            if (entranceExit.length == 8) {

                                if (entrance == true) {

                                    if (getMyBuffer().get(entranceExit[0], entranceExit[1]) != 1
                                            && getMyBuffer().get(entranceExit[0], entranceExit[1]) != 2) {

                                        try {
                                            this.furious.add(new Furious(myBuffer, this,
                                                    entranceExit[1], entranceExit[0],
                                                    Integer.toString(i + 1)));
                                        } catch (IOException ex) {
                                            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        i++;
                                        furious1--;
                                        this.furious.get(furious.size() - 1).start();
                                        entrance = false;

                                    } else {
                                        entrance = false;
                                    }

                                } else {

                                    if (getMyBuffer().get(entranceExit[4], entranceExit[5]) != 1
                                            && getMyBuffer().get(entranceExit[4], entranceExit[5]) != 2) {

                                        try {
                                            this.furious.add(new Furious(myBuffer, this,
                                                    entranceExit[1], entranceExit[0],
                                                    Integer.toString(i + 1)));
                                            System.out.println(i);
                                        } catch (IOException ex) {
                                            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        i++;
                                        furious1--;
                                        this.furious.get(furious.size() - 1).start();
                                        entrance = true;

                                    } else {
                                        entrance = true;
                                    }

                                }

                            } else {

                                if (getMyBuffer().get(entranceExit[0], entranceExit[1]) != 1
                                        && getMyBuffer().get(entranceExit[0], entranceExit[1]) != 2) {

                                    try {
                                        this.furious.add(new Furious(myBuffer, this,
                                                entranceExit[1], entranceExit[0],
                                                Integer.toString(i + 1)));
                                        System.out.println(i);
                                    } catch (IOException ex) {
                                        Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    i++;
                                    furious1--;
                                    this.furious.get(furious.size() - 1).start();
                                }

                            }

                        }
                        break;

                    case 3:
                        if (smart1 != 0) {
                            if (this.entranceExit.length == 8) {

                                if (entrance == true) {

                                    if (getMyBuffer().get(entranceExit[0], entranceExit[1]) != 1
                                            && getMyBuffer().get(entranceExit[0], entranceExit[1]) != 2) {

                                        try {
                                            this.smart.add(new Smart(myBuffer, this,
                                                    entranceExit[1], entranceExit[0],
                                                    Integer.toString(i + 1)));
                                        } catch (IOException ex) {
                                            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        i++;
                                        smart1--;
                                        this.smart.get(smart.size() - 1).start();
                                        entrance = false;

                                    } else {
                                        entrance = false;
                                    }

                                } else {

                                    if (getMyBuffer().get(entranceExit[4], entranceExit[5]) != 1
                                            && getMyBuffer().get(entranceExit[4], entranceExit[5]) != 2) {

                                        try {
//                                            this.smart.add(new Smart(myBuffer, this,
//                                                    entranceExit[5], entranceExit[4],
//                                                    ("Smart "+Integer.toString(quantitySmart - smart1+1))));
                                            this.smart.add(new Smart(myBuffer, this,
                                                    entranceExit[1], entranceExit[0],
                                                    Integer.toString(i + 1)));
                                        } catch (IOException ex) {
                                            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        i++;
                                        smart1--;
                                        this.smart.get(smart.size() - 1).start();
                                        entrance = true;
                                    } else {
                                        entrance = true;
                                    }
                                }

                            } else {

                                if (getMyBuffer().get(entranceExit[0], entranceExit[1]) != 1
                                        && getMyBuffer().get(entranceExit[0], entranceExit[1]) != 2) {

                                    try {
//                                        this.smart.add(new Smart(myBuffer, this,
//                                                entranceExit[1], entranceExit[0],
//                                                ("Smart "+Integer.toString(quantitySmart - smart1+1))));
                                        this.smart.add(new Smart(myBuffer, this,
                                                entranceExit[1], entranceExit[0],
                                                Integer.toString(i + 1)));
                                    } catch (IOException ex) {
                                        //Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    i++;
                                    smart1--;
                                    this.smart.get(smart.size() - 1).start();
                                }

                            }

                        }
                        break;
                

                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        Thread making = new Thread(myRunnable);
        making.start();

    }

    /**
     * @return the graphics2D
     */
    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    /**
     * @param graphics2D the graphics2D to set
     */
    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
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
     * @return the blocks
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    /**
     * @param blocks the blocks to set
     */
    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }
    

    /**
     * @return the faster
     */
    public ArrayList<Fast> getFaster() {
        return faster;
    }

    /**
     * @param faster the faster to set
     */
    public void setFaster(ArrayList<Fast> faster) {
        this.faster = faster;
    }

    /**
     * @return the items
     */
    public ArrayList<MItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(ArrayList<MItem> items) {
        this.items = items;
    }

    /**
     * @return the furious
     */
    public ArrayList<Furious> getFurious() {
        return furious;
    }

    /**
     * @param furious the furious to set
     */
    public void setFurious(ArrayList<Furious> furious) {
        this.furious = furious;
    }

    /**
     * @return the smart
     */
    public ArrayList<Smart> getSmart() {
        return smart;
    }

    /**
     * @param smart the smart to set
     */
    public void setSmart(ArrayList<Smart> smart) {
        this.smart = smart;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * @return the winner
     */
    public int getWinner() {
        return winner;
    }

    /**
     * @param winner the winner to set
     */
    public void setWinner(int winner) {
        this.winner = winner;
    }

    /**
     * @return the nameWinner
     */
    public String getNameWinner() {
        return nameWinner;
    }

    /**
     * @param nameWinner the nameWinner to set
     */
    public void setNameWinner(String nameWinner) {
        this.nameWinner = nameWinner;
    }


}
