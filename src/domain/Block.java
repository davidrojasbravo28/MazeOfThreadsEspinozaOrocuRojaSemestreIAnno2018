/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.image.BufferedImage;

/**
 *
 * @author mary_
 */
public class Block {
    
    //Atributos
    private int coordX;
    private int coordY;
    public static BufferedImage block;
    
    //Constructores

    public Block(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public Block() {
        this.coordX = 0;
        this.coordY = 0;
    }
    
    // Métodos Accesores

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

    public static BufferedImage getBlock() {
        return block;
    }

    public static void setBlock(BufferedImage block) {
        Block.block = block;
    }
    
    
    
}
