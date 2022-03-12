package package2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

//clasa care mosteneste clasa abstracta de nivel 2,
//deci mostenste si metodele clasei abstracte de nivel 1
//metodele abstracte trebuie implementate
public class ImageClass extends SaveImageAbstractClass{
	
	private BufferedImage image = null; 
	
	public ImageClass(){ //constructor fara parametru
		
	}
	
	public ImageClass(BufferedImage image){//constructor cu parametru
		this.image= image;
	}

	@Override
	public void saveImage(String path) throws IOException {
		try (FileOutputStream stream = new FileOutputStream(path)) {
			ImageIO.write(image, "BMP", stream);//salvare tot in format .bmp
		} catch (FileNotFoundException e) {
			System.out.println("Calea invalida!");
			return;
		} catch (IOException e) {
			System.out.println("Eroare in timpul salvarii");
			return;
		}
	}
	
	@Override
	public void saveImage() {
		try {
            ImageIO.write(image, "bmp", new File("test_salvare.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public BufferedImage readFile(String path) throws IOException { 
		//returneaza valoare pentru a putea folosi la citire multipla 
		try (FileInputStream stream = new FileInputStream(path)) {
			image = ImageIO.read(new File(path));
		} catch (FileNotFoundException e) {
			System.out.println("Fisierul nu a fost gasit!");
		}
		return image;
	}

	@Override
	public BufferedImage[] readMultipleFiles(int... x) throws IOException {
		BufferedImage [] images = new BufferedImage[x.length];
		Scanner scanner = new Scanner(System.in); //citire date introduse de la tastatura
		for(int i: x){
			
			System.out.println("Introduceti calea catre fisierul sursa:");
			String path = scanner.nextLine();
			images[i] = readFile(path);
		}
		scanner.close(); //se finalizeaza citirea de la consola
		return images;
	}

}
