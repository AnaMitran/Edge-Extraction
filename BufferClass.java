package package2;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class BufferClass { //cu sincronizare
	private BufferedImage image= null;
	private boolean available = false;
	
	public synchronized BufferedImage get() {
		while (!available) {
			try {
				wait();// Asteapta producatorul sa puna o valoare
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		available = false;
		notifyAll ();
		return image;
	}
	
	public synchronized void put (BufferedImage image) throws IOException {
		while (available) {
			try {
				wait();// Asteapta consumatorul sa preia valoarea
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.image = image;
		available = true;
		notifyAll ();
	}
}
