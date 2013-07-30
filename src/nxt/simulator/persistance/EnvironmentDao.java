package nxt.simulator.persistance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import nxt.simulator.Environment;

public class EnvironmentDao {

	public void saveEnvironment(String environment) {
		ObjectOutputStream oos;
        try {
	        oos = new ObjectOutputStream(new FileOutputStream("flat-files/sample.dat"));
	        oos.writeObject(environment);
	        oos.close();
	        System.out.println("exito");
        } catch (FileNotFoundException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        }	

	}
	
	public String getEnvironment() {
		String savedEnvironment = "";
        try {
	        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("flat-files/sample.dat"));
	        savedEnvironment = (String) ois.readObject();
	        ois.close();
        } catch (FileNotFoundException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        } catch (ClassNotFoundException e) {
	        e.printStackTrace();
        }	
        return savedEnvironment;
	}
	
}
