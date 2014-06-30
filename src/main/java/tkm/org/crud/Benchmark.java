package tkm.org.crud;

import com.cassandraclient.beans.Device;

public class Benchmark {
	long tcass,tmongo,tredis,temp,t1,t2,t3; 
	Device d;
	int progress = 0;

	public long getTcass() {
		return tcass;
	}

	public void setTcass(int tcass) {
		this.tcass = tcass;
	}

	public long getTmongo() {
		return tmongo;
	}

	public void setTmongo(int tmongo) {
		this.tmongo = tmongo;
	}

	public long getTredis() {
		return tredis;
	}

	public void setTredis(int tredis) {
		this.tredis = tredis;
	}

	private int threshold = 10000;

	public void benchget()
	{   
		progress = 0;
		DBClient dbclient = new DBClient();
		CRUD db = dbclient.createObj("cassandra");
		for(int j=0;j<10;j++)
		{
			t1=System.currentTimeMillis();	

			for(int i=0 ;i<threshold;++i) {
				d=db.read(Integer.toString(i));
				progress++;
			}

			t2=System.currentTimeMillis();
			t3=t2-t1;
			temp+=t3;

		}
		tcass=temp/10000;
		temp=0;
		db = dbclient.createObj("mongodb");
		for(int j=0;j<10;j++)
		{
			t1=System.currentTimeMillis();	
			for(int i=0 ;i<threshold;++i) {
				d=db.read(Integer.toString(i));
				progress++;
			}
			t2=System.currentTimeMillis();
			t3=t2-t1;
			temp+=t3;
		}
		tmongo=temp/10000;
		temp=0;
		db = dbclient.createObj("redis");
		for(int j=0;j<10;j++)
		{
			t1=System.currentTimeMillis();	
			for(int i=0 ;i<threshold;++i) {
				d=db.read(Integer.toString(i));
				progress++;
			}
			t2=System.currentTimeMillis();
			t3=t2-t1;
			temp+=t3;
		}
		tredis=temp/10000;
	}

	public int getProgress() {
		return progress;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
}
