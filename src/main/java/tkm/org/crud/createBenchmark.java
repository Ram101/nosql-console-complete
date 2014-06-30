package tkm.org.crud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.cassandraclient.beans.Device;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;

public class createBenchmark {
	
	
	
	String line;
    public void createB(){
    	
    	long count=0,avg=0;
    	
    	Cluster cluster = HFactory.getOrCreateCluster("Test Cluster","localhost:9160");
    	Keyspace keyspace = HFactory.createKeyspace("Mobiledevice1",cluster);
    	ColumnFamilyDefinition columnFamily = HFactory.createColumnFamilyDefinition("Mobiledevice1", "devices",	ComparatorType.UTF8TYPE);
    	Device device = new Device();
    	Gson g = new Gson();
    	do{
    	InputStream fis = ClassLoader.getSystemClassLoader().getResourceAsStream("inputdata.txt");
    	BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
    	long l1 = System.currentTimeMillis(), l2, l3;
    	long cnt=0;
	  try {
		while ((line = reader.readLine()) != null) {
				device = g.fromJson(line, Device.class);
		  		
		StringSerializer stringSerializer = StringSerializer.get();
		Mutator<String> mutator = HFactory.createMutator(keyspace,
				stringSerializer);

		Map<String, String> map = processKeyValueMap(device);
		Set set = map.entrySet();

		Iterator i = set.iterator();

		while (i.hasNext()) {

			Map.Entry entry = (Map.Entry) i.next();

			mutator.addInsertion(device.getUdid(), columnFamily

			.getName(), HFactory.createStringColumn(entry

			.getKey().toString(), entry.getValue().toString()));

			
		}
		mutator.execute();
		++cnt;
		
  }
	} catch (JsonSyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  l2 = System.currentTimeMillis();
		l3 = l2 - l1;
        ++count;
		 System.out.println("Created!!! time taken to read "+cnt+"is :"+l3);
		 avg+=l3;
    } while(count<5);
    avg/=5;
    System.out.println("avgerage time taken to create is  "+avg);
  }
	
  public Map processKeyValueMap(Device device) {
		HashMap returnMap = new HashMap<String, String>();
		if (null != device.getUdid() && null != device.getFriendlyname()) {
			returnMap.put("UDID", device.getUdid());
			returnMap.put("Friendlyname", device.getFriendlyname());
			returnMap.put("Serialnum", Integer.toString(device.getSerialnum()));
			returnMap.put("Manufacture_Date", device.getManidate());
		}
		return returnMap;
	}
  
	
		
}
