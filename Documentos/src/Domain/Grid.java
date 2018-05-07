/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author david
 */
public class Grid {
    
    //atributos
    private int cellSize;

    public Grid(int cellSize) {
        this.cellSize = cellSize;
    }//constructor
    
    //Calcula los puntos X para dibujar la cuadricula en el canvas y los retorna en un vector
    public double[] getPointX(Canvas canvas){
        int vectorSize= (((int)canvas.getWidth()/this.cellSize)+3);//divide el ancho del canvas entre el tamano de la celda de la cuadricula//
        double[] xVector= new double[vectorSize];//vector en el que se guardan los puntos x
        double x= 0;//punto x
        for(int i=0; i<xVector.length-1; i++){
            xVector[i]= x;
            x=x+cellSize;
        }//for getPointX
        return xVector;
    }//pointX()
    
    //Calcula los puntos y para dibujar la cuadricula en el canvas y los retorna en un vector
    public double[] getPointY(Canvas canvas){
        int vectorSize= (((int)canvas.getHeight()/this.cellSize)+3);//divide el alto del canvas entre el tamano de la celda de la cuadricula//
        double[] yVector= new double[vectorSize];//vector en el que se guardan los puntos y
        double y= 0;//punto y
        for(int j=0; j<yVector.length-1; j++){
            yVector[j]= y;
            y=y+cellSize;
        }//for
        return yVector;
    }//pointY()
    
    /*Dibuja una cuadricula en el canvas recibido utilizando los puntos X y Y obtenidos de los vectores 
    retornados por los metodos getPointX() y getPointY()*/
    public void drawGrid(GraphicsContext graphicsContext, Canvas canvas){
        for(int i= 0; i<getPointX(canvas).length-1; i++){
            graphicsContext.strokeLine(getPointX(canvas)[i], getPointY(canvas)[0], getPointX(canvas)[i], canvas.getHeight());
        }//dibuja lineas horizontales
        for(int j=0; j<getPointY(canvas).length-1; j++){            
            graphicsContext.strokeLine(getPointX(canvas)[0], getPointY(canvas)[j], canvas.getWidth(), getPointY(canvas)[j]);              
        }//dibujas líneas verticales
    }//drawGrid
    
}//final clase
