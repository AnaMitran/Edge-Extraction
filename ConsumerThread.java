package package2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ConsumerThread  extends Thread {
    private BufferClass buffer;
    private String path;
	
	public ConsumerThread(BufferClass buffer, String path) {
		this.buffer = buffer;
		this.path = path;
	}
    
    public void run () {
        BufferedImage images[] = new BufferedImage[4]; //preiau sferturile de imagine din buffer
        
        for (int i = 0; i < 4; i++) {
          images[i] = buffer.get();
          System.out.println("Consumatorul a primit sfertul\t" + (i+1) + "\tdin imagine");
          
          try {
			sleep (1000);
		  } catch (InterruptedException e) {}
        }

        int type = images[0].getType();
        int width = images[0].getWidth();
        int height = images[0].getHeight();

        BufferedImage mergedImage = new BufferedImage(2*width, 2*height, type);

        int k = 0;
        for ( int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                mergedImage.createGraphics().drawImage(images[k++], width * j, height * i, null);
            }
        }
        try {
            ImageIO.write(mergedImage, "bmp", new File("mergedImage.jpg")); 
            //se salveaza local imaginea obtinuta din sferturi
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //ETAPA DE PRELUCRARE DUPA CITIRE
        EdgeExtraction object = new EdgeExtraction(mergedImage);
        object.extractEdges();
        
        //SALVARE REZULTAT
        try {
			object.saveImage(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    }
