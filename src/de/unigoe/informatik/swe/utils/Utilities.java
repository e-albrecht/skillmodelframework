package de.unigoe.informatik.swe.utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
 
/**
 * This utility extracts files and directories of a standard zip file to
 * a destination directory.
 * @author www.codejava.net
 *
 */
public class Utilities {
    
    public static byte[] serializeObject(Object o) {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	try {
			ObjectOutputStream oout = new ObjectOutputStream(baos);
			oout.writeObject(o);
			oout.close();
			return baos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public static Object deSerializeObject(byte[] stream) {
    	ByteArrayInputStream bais = new ByteArrayInputStream(stream);
    	try {
			ObjectInputStream in = new ObjectInputStream(bais);
			Object o = in.readObject();
			in.close();
			
			bais.close();
			return o;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;  	
    }
    
    public static void serializeObjectToFile(Object o, String filename) {
    	try {
   	       FileOutputStream fos = new FileOutputStream(filename);
   	       ObjectOutputStream oout = new ObjectOutputStream(fos);
   	       oout.writeObject(o);
   	       oout.close();
   	       fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static Object deSerializeObjectFromFile(String filename) {
    	try {
   	       FileInputStream fis = new FileInputStream(filename);
   	       ObjectInputStream in = new ObjectInputStream(fis);
   	       Object o = in.readObject();
   	       in.close();
   	       fis.close();
   	       return o;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
}
    