package Domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class RunningCharacter extends Character {

    public RunningCharacter(int x, int y, int imgNum) throws FileNotFoundException {
        super(x, y, imgNum);
        setSprite();
    }
    
    public void setSprite() throws FileNotFoundException{
        ArrayList<Image> sprite = super.getSprite();
        for(int i = 0; i < 8; i++){
            sprite.add(new Image(new FileInputStream("src/Assets/Running"+i+".png")));
        }//for derecha
        sprite.add(new Image(new FileInputStream("src/Assets/Standing0.png")));
        super.setSprite(sprite);
        int j=0;
        for(int i = 9; i < 17; i++){
            ImageView imageViewLeft= new ImageView(sprite.get(j));
            imageViewLeft.setScaleX(-1);
            WritableImage writableImage= new WritableImage((int)sprite.get(j).getWidth(), (int)sprite.get(j).getHeight());
            ImageView imageViewWritable= new ImageView(writableImage);
            imageViewWritable.resize(sprite.get(j).getWidth(), sprite.get(j).getHeight());
            SnapshotParameters snapParams= new SnapshotParameters();
            snapParams.setFill(Color.TRANSPARENT);
            imageViewLeft.snapshot(snapParams, writableImage);
            sprite.add(writableImage);
            j++;
        }//for izquierda
        ImageView imageViewLeft= new ImageView(sprite.get(8));
        imageViewLeft.setScaleX(-1);
        WritableImage writableImage= new WritableImage((int)sprite.get(8).getWidth(), (int)sprite.get(8).getHeight());
        ImageView imageViewWritable= new ImageView(writableImage);
        imageViewWritable.resize(sprite.get(8).getWidth(), sprite.get(8).getHeight());
        SnapshotParameters snapParams= new SnapshotParameters();
        snapParams.setFill(Color.TRANSPARENT);
        imageViewLeft.snapshot(snapParams, writableImage);
        sprite.add(writableImage);
    }//setSprite
    
    
    @Override
    public void run() {
        ArrayList<Image> sprite = super.getSprite();
        super.setImage(sprite.get(8));
        super.setX(100);
        while (true) {
            try {
                super.setImage(sprite.get(8));
                Thread.sleep(800);
                while(this.getX()<500){
                    int i=0;
                    while(i<8){
                        super.setX(this.getX()+5);
                        super.setImage(sprite.get(i));
                        Thread.sleep(20);
                        i++;
                    }//while
                }//while derecha
                super.setImage(sprite.get(8));
                Thread.sleep(800);
                super.setImage(sprite.get(17));
                Thread.sleep(800);
                while(this.getX()>100){
                    int i=9;
                    while(i<17){
                        super.setX(this.getX()-4);
                        super.setImage(sprite.get(i));
                        Thread.sleep(40);
                        i++;
                    }//while
                }//while izquierda
                super.setImage(sprite.get(17));
                Thread.sleep(800);
            }//try 
            catch (InterruptedException ex) {}
        }//catch
    }//run()
}//final de la clase
