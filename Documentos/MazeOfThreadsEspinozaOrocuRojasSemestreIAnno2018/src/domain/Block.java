package domain;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Marco y Maria
 *
 */
public class Block {
    
    /**
     * Atributos
     */
    private int coordX;
    private int coordY;
    public static BufferedImage block;
    /**
     * Constructor
     */
    
    public Block(int cordX, int cordY) throws IOException{
        this.coordX = cordX;
        this.coordY = cordY;
    }
    
    public Block() throws IOException{
        this.coordX = 0;
        this.coordY = 0;
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
    public BufferedImage getBlock() {
        return block;
    }

    public void setBlock(BufferedImage block) {
        this.block = block;
    }

}
