package threads;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Sound extends Thread{
    
    Clip sound;
    File path;
    
    public Sound() throws LineUnavailableException{
        sound =  AudioSystem.getClip();
        path = new File("./Sounds/winSound.wav");
    }

    @Override
    public void run() {
        try {
            sound.open(AudioSystem.getAudioInputStream(path));
            sound.start();
            Thread.sleep(4000);
            sound.close();
        } catch (Exception e) {
        } catch (Throwable ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
