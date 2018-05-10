package Domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class JumpingCharacter extends Character {
     
    public JumpingCharacter(int x, int y, int imgNum) throws FileNotFoundException {
        super(x, y, imgNum);
        setSprite();
    }
    
    public void setSprite() throws FileNotFoundException{
        ArrayList<Image> sprite = super.getSprite();
        sprite.add(new Image(new FileInputStream("src/Assets/Jumping2.png")));
        sprite.add(new Image(new FileInputStream("src/Assets/Jumping0.png")));
        sprite.add(new Image(new FileInputStream("src/Assets/Jumping1.png")));
    }//setSprite

    @Override
    public void run() {
        ArrayList<Image> sprite = super.getSprite();
        super.setImage(sprite.get(0));
        this.setY(370);
        while (true) {
            try {
                Thread.sleep(800);
                while(this.getY()>270){
                    this.setY(this.getY()-2);
                    super.setImage(sprite.get(1));
                    Thread.sleep(10);
                }//while sube
                while(this.getY()<370){
                    this.setY(this.getY()+2);
                    super.setImage(sprite.get(2));
                    Thread.sleep(10);
                }//while baja
                super.setImage(sprite.get(0));
                this.setY(this.getY());
            }//try 
            catch (InterruptedException ex) {} 
        }//while
    }//run
}//fin de la clase
