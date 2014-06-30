package tkm.org.crud;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.kohsuke.randname.RandomNameGenerator;

import com.cassandraclient.beans.*;
import com.google.gson.Gson;

public class recordgen {

	public static void main(String arg[]) {
		
		Gson g = new Gson();
		Device d = new Device();
		int i;
		Random r = new Random();
		System.out.println("Enter Number of records to be generated:");
		int gen= Integer.parseInt(System.console().readLine());
		RandomNameGenerator rnd = new RandomNameGenerator(0);
		Date truncatedDate = DateUtils.truncate(new Date(), Calendar.DATE);
		try {
			PrintWriter writer;
			try {
				writer = new PrintWriter("E:/inputdata1.json", "UTF-8");
				for (i = 0; i < gen; ++i) {
					d.setUdid(Integer.toString(i));
					d.setFriendlyname(rnd.next());
					d.setSerialnum(r.nextInt(gen));
					d.setManidate(truncatedDate.toString());
					writer.println(g.toJson(d));

				}
				writer.close();

			} catch (Exception O) {
				System.out.println(O.getMessage());
			}
		} catch (Exception E) {
			System.out.println(E.getMessage());
		}
	}

}
