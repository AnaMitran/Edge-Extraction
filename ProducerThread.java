package package2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ProducerThread extends Thread{
	private BufferClass buffer;
	private String path;
	
	public ProducerThread(BufferClass buffer, String path) {
		this.buffer = buffer;
		this.path = path;
	}
	
	public void run () {
		//incep masurarea timpului de citire
		long time = System.currentTimeMillis();
		
		//folosesc un vector pentru a obtine sferturile de imagine
		BufferedImage images[]= new BufferedImage[4];
		
		try {	
			BufferedImage image = null;
			try (FileInputStream stream = new FileInputStream(path)) {
				image = ImageIO.read(new File(path));
			} catch (FileNotFoundException e) {
				System.out.println("Fisierul nu a fost gasit!");
			}
			//numarul de fragmente in care se imparte imaginea pe linii si coloane
			int row = 2;
			int col = 2;
			
			//dimensiunile unui fragment de imagine
			int width = image.getWidth()/ col;
			int height = image.getHeight() / row;
	
			int x = 0;
			int y = 0;
			int count = 0;
	
			for (int i = 0; i < row; i++) {
				y = 0;
				for (int j = 0; j < col; j++) {
					try {
						images[count++] = image.getSubimage(y, x, width, height);
						y += width;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				x += height;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		for (int i = 0; i < 4; i++) {
			try {
				buffer.put(images[i]);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("Producatorul a pus sfertul\t" + (i+1) + "\tdin imagine");
			try {
				sleep (1000);
			} catch (InterruptedException e) {}
		}
		//prin diferenta de timp, aflu durata citirii
		time = System.currentTimeMillis() - time;
		System.out.println("Citirea din fisier cu multithreading a durat " + time / 1000.0f + " secunde");
	}
}
