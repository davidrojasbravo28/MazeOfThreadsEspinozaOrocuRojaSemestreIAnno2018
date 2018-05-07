/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Domain.Mosaic;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author david
 */
public class MosaicFile {
    
    //atributos
    private RandomAccessFile randomAccesFile;
    private int regsQuantity;//cantidad registros
    private String myFilePath;//ruta archivo

    public MosaicFile(String myFilePath) throws FileNotFoundException {
        this.randomAccesFile= new RandomAccessFile(myFilePath, "rw");
        this.regsQuantity=1;
        this.myFilePath= myFilePath;
    }//constructor 
    
    public MosaicFile(File file) throws IOException{
        //guardo la ruta
        myFilePath= file.getPath();
        //indico tamaño máximo
        //validación
        if(file.exists() && !file.isFile()){
            throw new IOException(file.getName()+" invalid file");
        }//if
        else{
            //crea una nueva instancia de RAF
            randomAccesFile= new RandomAccessFile(file, "rw");
            //indica cuantos registros tiene el archivo
            this.regsQuantity=1;
        }//else
    }//constructor
    
    //cierra archivo
    public void close() throws IOException{
        randomAccesFile.close();
    }//fin cerrar
    
    
    //insertar nuevo registro
    public void addMosaic(Mosaic mosaic) throws IOException{
        //guarda Mosaic en MosaicFile
        randomAccesFile.seek(0);
        randomAccesFile.writeInt(mosaic.getWidth());
        randomAccesFile.writeInt(mosaic.getHeight());
        randomAccesFile.writeInt(mosaic.getCellSize());
        randomAccesFile.writeUTF(mosaic.getImageMosaic());
        this.regsQuantity++;
    }//addMosaic
    
   //Regresa el mosaic guardado en el archivo 
   public Mosaic getMosaic() throws IOException{
       if(this.regsQuantity==0){
           System.err.println("Empty File");
           return null;
       }else{
           //coloca el brazo en el lugar correcto
           randomAccesFile.seek(0);
           try { 
               //lectura
               Mosaic mosaicTemp= new Mosaic();
               mosaicTemp.setWidth(randomAccesFile.readInt());
               mosaicTemp.setHeight(randomAccesFile.readInt());
               mosaicTemp.setCellSize(randomAccesFile.readInt());
               mosaicTemp.setImageMosaic(randomAccesFile.readUTF());

               return mosaicTemp;    
           } catch (EOFException ex) {
               return null;
           }//regresa null si el archivo leído no tiene objetos Mosaic               
       }//else
           
   }//obtener Mosaic

    /**
     * @return the myFilePath
     */
    public String getMyFilePath() {
        return myFilePath;
    }//getMyFilePath
    
}//fin clase
