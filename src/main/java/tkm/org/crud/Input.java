package tkm.org.crud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cassandraclient.beans.Device;
import com.google.gson.Gson;

public class Input {

	Scanner sc;
	Device d;
	String UDID;

	static Logger log = Logger.getLogger(Main.class.getName());

	public void fileinput(CRUD op) {

		InputStream fis;
		BufferedReader reader;
		// StringBuilder out;
		String line;
		Gson g;
		int i = 0;
		fis = ClassLoader.getSystemClassLoader().getResourceAsStream("inputdata.txt");
		long l1 = System.currentTimeMillis(), l2, l3;
		reader = new BufferedReader(new InputStreamReader(fis));
		// out = new StringBuilder();
		g = new Gson();
		d = new Device();
		try {
			while ((line = reader.readLine()) != null) {
				d = g.fromJson(line, Device.class);
				op.create(d);
				// System.out.println(line);
				++i;
			}
			l2 = System.currentTimeMillis();
			l3 = l2 - l1;

			 System.out.println("Created!!! time taken to read "+i+"is :"+l3);
			//log.info("Created!!! time taken to read " + i + "is :" + l3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(out.toString());
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
