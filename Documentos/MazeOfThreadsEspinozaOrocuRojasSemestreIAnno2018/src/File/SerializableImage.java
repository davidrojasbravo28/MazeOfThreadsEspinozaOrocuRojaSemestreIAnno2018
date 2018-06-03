package File;

import domain.Block;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public class SerializableImage {
    
    public void writeObject(ObjectOutputStream out, ArrayList<Block> blocks) throws IOException {
        out.writeObject("MazeLevel3");
        out.writeObject(0);
        out.writeObject(5);
        out.writeObject(4);
        out.writeObject(16);
        out.writeObject(0);
        out.writeObject(12);
        out.writeObject(13);
        out.writeObject(16);
        out.writeObject(blocks.size());

        for (int i = 0; i < blocks.size(); i++) {
            out.writeObject(blocks.get(i).getCoordX());
            out.writeObject(blocks.get(i).getCoordY());
            ImageWriter writer = (ImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
            writer.setOutput(ImageIO.createImageOutputStream(out));
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.85f);
            writer.write(null, new IIOImage(blocks.get(i).getBlock(), null, null), param);
        }
    }
    
    public Object[] readObject(String level) throws IOException, ClassNotFoundException {
        
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("./File/" + level +".bin"));
        ArrayList<Block> blocks = new ArrayList<>();
        int[] entranceExit;
        if(level.equals("MazeLevel1") || level.equals("MazeLevel2")) {
            entranceExit = new int[4];
            System.out.println(in.readObject());
            entranceExit[0] = (int) (in.readObject());
            entranceExit[1] = (int) (in.readObject());
            entranceExit[2] = (int) (in.readObject());
            entranceExit[3] = (int) (in.readObject());
            int size = (int) (in.readObject());
            for (int i = 0; i < size; i++) {
                Block block = new Block();
                block.setCoordX((int) (in.readObject()));
                block.setCoordY((int) (in.readObject()));
                block.setBlock(ImageIO.read(ImageIO.createImageInputStream(in)));
                blocks.add(block);
            }
        } else {
            entranceExit = new int[8];
            System.out.println(in.readObject());
            entranceExit[0] = (int) (in.readObject());
            entranceExit[1] = (int) (in.readObject());
            entranceExit[2] = (int) (in.readObject());
            entranceExit[3] = (int) (in.readObject());
            entranceExit[4] = (int) (in.readObject());
            entranceExit[5] = (int) (in.readObject());
            entranceExit[6] = (int) (in.readObject());
            entranceExit[7] = (int) (in.readObject());
            int size = (int) (in.readObject());
            for (int i = 0; i < size; i++) {
                Block block = new Block();
                block.setCoordX((int) (in.readObject()));
                block.setCoordY((int) (in.readObject()));
                block.setBlock(ImageIO.read(ImageIO.createImageInputStream(in)));
                blocks.add(block);
            }
        }
        Object[] toReturn = new Object[2];
        toReturn[0] = blocks;
        toReturn[1] = entranceExit;
        return toReturn;
    }
    
    
    
}
