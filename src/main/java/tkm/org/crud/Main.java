package tkm.org.crud;

import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class Main {

	static Logger log = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws IOException {
		// PropertyConfigurator.configure("log4j.properties");
				// BasicConfigurator.configure();
       CLiOps d=new CLiOps();
       d.interact();
	}

}
