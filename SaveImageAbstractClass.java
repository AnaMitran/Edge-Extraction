 package package2;

import java.io.IOException;

//clasa abstracta care mosteneste o alta clasa abstracta
public abstract class SaveImageAbstractClass extends ReadImageAbstractClass{
	abstract public void saveImage(String path) throws IOException;
	abstract public void saveImage(); //polimorfism
	
	public void description(){
		System.out.println("Clasa abstracta, mostenire nivel 2");
	}
}
