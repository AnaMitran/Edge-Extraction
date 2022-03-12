package package2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EdgeExtraction extends ImageClass implements EdgeExtractionInterface { //mostenire
	
	private BufferedImage image = null;
	
	public EdgeExtraction(BufferedImage img){
		this.image=img;
	}
	
	//implementarea metodelor abstracte din interfata
	
	@Override
	public int[][] convertToGrayscale() {
		//dimensiunile imaginii
		int width = image.getWidth();
		int height = image.getHeight();
				        
		int[][] result;
	    result = new int[width][height];
				
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				//citesc RGB-ul pixelilor pe rand
				int pixel = image.getRGB(i, j);
				//impart informatia pe canale de culoare elementara: R, G, B
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = pixel & 0xff;
				//tonul de gri se obtine prin media valorilor canalelor de culoare        
				int grayscale = (r+g+b)/3; 
				//retin informatia in matricea creata anterior
				result[i][j] = grayscale;  
				// RGB-ul se inlocuieste cu grayscale
				pixel = (grayscale << 24) | (grayscale << 16) | (grayscale << 8) | grayscale;
				image.setRGB(i, j, pixel);
			}
		}
		return result;
	}
	
	@Override
	public void extractEdges() {
		
		long startTime = System.currentTimeMillis(); // masurarea timpului de executie pentru extragerea marginilor
				
		//dimensiunile imaginii
		int width = image.getWidth();
		int height = image.getHeight();
		
		//ETAPA 1: convertirea imaginii la grayscale(tonuri de gri)
		int [][] result = new int[width][height];
		result= convertToGrayscale();
		
	    //ETAPA 2: aplicarea filtrelor(Gx, Gy) pe directiile x si y pentru a obtine gradientul
		
		//filtrele orizontal si vertical pentru convolutie
		int[][] Gx = {{-1,0,1},
					  {-1,0,1},
					  {-1,0,1}};
				
		int[][] Gy = {{-1,-1,-1},
					  {0,  0, 0},
					  {1,  1, 1}};
		
		for (int y = 1; y < height-1; y++) {
			for (int x = 1; x < width-1; x++) {
				//portiune 3x3 din imagine in convolutie cu Gx
				int gx = (Gx[0][0] * result[x-1][y-1]) + (Gx[0][1] * result[x][y-1]) + (Gx[0][2] * result[x+1][y-1]) +
		                 (Gx[1][0] * result[x-1][y])   + (Gx[1][1] * result[x][y])   + (Gx[1][2] * result[x+1][y]) + 
		                 (Gx[2][0] * result[x-1][y+1]) + (Gx[2][1] * result[x][y+1]) + (Gx[2][2] * result[x+1][y+1]);
				//portiune 3x3 din imagine in convolutie cu Gy
		        int gy = (Gy[0][0] * result[x-1][y-1]) + (Gy[0][1] * result[x][y-1]) + (Gy[0][2] * result[x+1][y-1]) +
		        		 (Gy[1][0] * result[x-1][y])   + (Gy[1][1] * result[x][y])   + (Gy[1][2] * result[x+1][y]) + 
		                 (Gy[2][0] * result[x-1][y+1]) + (Gy[2][1] * result[x][y+1]) + (Gy[2][2] * result[x+1][y+1]);
		        
		        //magnitudinea gradientului
		        int pixel = (int) Math.sqrt((gx * gx) + (gy * gy));
		        
		        //valoarea noua a pixelului trebuie sa fie tot in domeniul 0-255
		        if (pixel>255) {
		            pixel = 255;
		        } else if (pixel<0) {
		            pixel = 0;
		        }
		        
		        //formarea pixelului tip RGB
		        Color newPixel = new Color(pixel,pixel,pixel);
		        image.setRGB(x, y, newPixel.getRGB());
		     }
	    }
		// Se calculeaza timpul necesar procesarii imaginii si se afiseaza
		long stopTime = System.currentTimeMillis() - startTime;
		System.out.println("Transformarea imaginii a durat " + stopTime / 1000.0f + " secunde");
	}
	
	public void saveImage(String path) throws IOException{
		long startTime = System.currentTimeMillis();
		
		try (FileOutputStream stream = new FileOutputStream(path)) {
			ImageIO.write(image, "BMP", stream);//salvare tot in format .bmp
		} catch (FileNotFoundException e) {
			System.out.println("Calea invalida!");
			return;
		} catch (IOException e) {
			System.out.println("Eroare in timpul salvarii");
			return;
		}
		// intrevalul de timp necesar scrierii in fisierul destinatie si se afiseaza
		long stopTime = System.currentTimeMillis() - startTime;

		System.out.println("Scrirea in fisier a durat " + stopTime / 1000.0f + " secunde");
	}
}

