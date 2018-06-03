package Domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class StandingCharacter extends Character {

    public StandingCharacter(int x, int y, int imgNum) throws FileNotFoundException {
        super(x, y, imgNum);
        setSprite();
    }
    
    public void setSprite() throws FileNotFoundException{
        ArrayList<Image> sprite = super.getSprite();
        for(int i = 0; i < 4; i++){
            sprite.add(new Image(new FileInputStream("src/Assets/Standing"+i+".png")));
        }//for
        sprite.add(new Image(new FileInputStream("src/Assets/Standing0.png")));
        super.setSprite(sprite);
    }//setSprite

    @Override
    public void run() {
        ArrayList<Image> sprite = super.getSprite();
        super.setImage(sprite.get(0));
        while (true) {
            try {
                Thread.sleep(2000);
                int i=0;
                while(i<5){
                    super.setImage(sprite.get(i));
                    i++;
                    Thread.sleep(20);
                }//while
            }//try 
            catch (InterruptedException ex) { }
        }//catch
    }//run()
}//final clase
