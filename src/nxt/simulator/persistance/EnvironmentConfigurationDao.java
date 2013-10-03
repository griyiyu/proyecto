package nxt.simulator.persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import nxt.simulator.EnvironmentConfiguration;

public class EnvironmentConfigurationDao {
	
	public void saveEnvironment(EnvironmentConfiguration environment, File file) {
		ObjectOutputStream oos;
        try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
	        oos = new ObjectOutputStream(fileOutputStream);
	        oos.writeObject(environment);
	        oos.close();
	        System.out.println("exito");
        } catch (FileNotFoundException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        }	
	}	
	
	public EnvironmentConfiguration getEnvironment(File file) {
		EnvironmentConfiguration savedEnvironment = null;
        try {
	        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
	        savedEnvironment = (EnvironmentConfiguration) ois.readObject();
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

