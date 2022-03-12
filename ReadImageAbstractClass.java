package package2;

import java.awt.image.BufferedImage;
import java.io.IOException;

//clasa abstracta, poate reprezenta primul nivel de mostenire
public abstract class ReadImageAbstractClass {
	abstract public BufferedImage readFile(String path) throws IOException;
	abstract public BufferedImage[] readMultipleFiles(int...x) throws IOException;
	
	public void description(){
		System.out.println("Clasa abstracta, mostenire nivel 1");
	}
}
