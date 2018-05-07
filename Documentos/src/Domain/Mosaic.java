/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author david
 */
public class Mosaic {
    
    //atributos
    private int width;
    private int height;
    private int cellSize;
    private String imageMosaic;

    public Mosaic() {
        this.width= 400;
        this.height= 300;
        this.cellSize=100;
        this.imageMosaic=" ";
    }//constructor default

    public Mosaic(int width, int height, int cellSize, String imageMosaic) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.imageMosaic = imageMosaic;
    }//constructor sobrecargado

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the cellSize
     */
    public int getCellSize() {
        return cellSize;
    }

    /**
     * @param cellSize the cellSize to set
     */
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    /**
     * @return the imageMosaic
     */
    public String getImageMosaic() {
        return imageMosaic;
    }

    /**
     * @param imageMosaic the imageMosaic to set
     */
    public void setImageMosaic(String imageMosaic) {
        this.imageMosaic = imageMosaic;
    }

    //tamanno del objeto Mosaic
    public int sizeInBytes(){
        return 4+4+4+this.imageMosaic.length()*2;
    }//sizeInBytes
}//fin clase
