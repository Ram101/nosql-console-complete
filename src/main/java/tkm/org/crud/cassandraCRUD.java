package tkm.org.crud;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ColumnSliceIterator;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.SliceQuery;

import com.cassandraclient.beans.Device;

public class cassandraCRUD implements CRUD {
	Cluster cluster = null;
	 static Logger log = Logger.getLogger(Main.class.getName());
	Keyspace keyspace = null;
    ConfigurationManager C= ConfigurationManager.get();
	@Override
	public boolean createconn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(Device device) {
		// TODO Auto-generated method stub
		getConfig();
		
		//System.out.println("Entered the creation region");
		if (null != cluster && null != keyspace) {

			ColumnFamilyDefinition columnFamily = HFactory
					.createColumnFamilyDefinition("Mobiledevice1", "devices",
							ComparatorType.UTF8TYPE);
			Keyspace keyspace = HFactory.createKeyspace("Mobiledevice1",
					cluster);

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
			
		}
		
		
		return false;
	}

	@Override
	public Device read(String UDID) {
		// TODO Auto-generated method stub
		getConfig();
		Device d= new Device();
		if (null != cluster && null != keyspace) {

			ColumnFamilyDefinition columnFamily = HFactory

			.createColumnFamilyDefinition("Mobiledevice1",

			"devices", ComparatorType.UTF8TYPE);

			SliceQuery<String, String, String> query = HFactory

			.createSliceQuery(keyspace, StringSerializer.get(),

			StringSerializer.get(), StringSerializer.get())

			.setKey(UDID).setColumnFamily(columnFamily.getName());

			ColumnSliceIterator<String, String, String> iterator = new ColumnSliceIterator<String, String, String>(

			query, null, "\u00FFF", false);

			while (iterator.hasNext()) {

				HColumnImpl<String, String> column = (HColumnImpl<String, String>) iterator

				.next();

//				System.out
//
//				.println(column.getName() + " = " + column.getValue());
			if(column.getName().equals("Friendlyname"))
			{
				d.setFriendlyname(column.getValue());
			}
			if(column.getName().equals("Manufacture_Date"))
			{
				d.setManidate(column.getValue());
			}
			if(column.getName().equals("Serialnum"))
			{
				d.setSerialnum(Integer.parseInt(column.getValue()));
			}
			if(column.getName().equals("UDID"))
			{
				d.setUdid(column.getValue());
			}
			}
		}
		return  d;
	}

	@Override
	public boolean delete(String UDID) {
		// TODO Auto-generated method stub
		read(UDID);
		System.out.println("Deletion mode ");
		getConfig();
		if (null != cluster && null != keyspace) {

			StringSerializer stringSerializer = StringSerializer.get();

			Mutator<String> mutator = HFactory.createMutator(keyspace,
					stringSerializer);

			mutator.delete(UDID, "devices", "UDID", stringSerializer);
			mutator.delete(UDID, "devices", "Friendlyname", stringSerializer);
			mutator.delete(UDID, "devices", "Serialnum", stringSerializer);
			mutator.delete(UDID, "devices", "Manufacture_Date",
					stringSerializer);

		} else {
			//System.out.println("Record does not exisit!!!");
			log.info("Record does not exisit!!!");
		}

		return false;
	}

	@Override
	public boolean update(Device device) {
		// TODO Auto-generated method stub
		getConfig();
		if (null != cluster && null != keyspace) {

			ColumnFamilyDefinition columnFamily = HFactory

			.createColumnFamilyDefinition("Mobiledevice1",

			"devices", ComparatorType.UTF8TYPE);

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

				System.out.println("Updated..");
				//log.info("Updated..");
			}
			mutator.execute();
		}
		return false;
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

	public void getConfig() {
		
		cluster = HFactory.getOrCreateCluster("Test Cluster", C.getProperty("host")+":"+C.getProperty("port"));
		keyspace = HFactory.createKeyspace("Mobiledevice1", cluster);
	}
}
