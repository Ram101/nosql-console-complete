package org.tkm.backingbeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tkm.org.crud.MongoCRUD;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.cassandraclient.beans.Device;


public class LazyMongoDataModel extends LazyDataModel<Device> {
	private List<Device> datasource;
	private MongoDB service;
	
	
	public LazyMongoDataModel(List<Device> datasource) {
		this.datasource = datasource;
		
	}

	@Override
	public Device getRowData(String rowKey) {
		for (Device device : datasource) {
			if (device.getUdid().equals(rowKey))
				return device;
		}
		return null;
	}

	@Override
	public Object getRowKey(Device car) {
		return car.getUdid();
	}

	@Override
	public List<Device> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		   Device d=new Device();
		List<Device> data = new ArrayList<Device>();
		datasource = new ArrayList<Device>();
		MongoCRUD db =new MongoCRUD();
		int fl=0;
		for (int i = 0; i < pageSize; i++) {
			d=db.read(Integer.toString(i+first));
			if(d.getSerialnum()!=0)		
			datasource.add(db.read(Integer.toString(i+first)));
		}
		
		
		
		System.out.println(fl);
		// filter
		
		for (Device device : datasource) {
			boolean match = true;
	        		
			if (filters != null) {
				
				for (Iterator<String> it = filters.keySet().iterator(); it.hasNext() ;) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
//						String fieldValue = String.valueOf(device.getClass()
//								.getField(filterProperty).get(device));
						String fieldValue =String.valueOf((db.read((String)filterValue)).getUdid());
						device=(db.read((String)filterValue));
						System.out.println(filterValue);
						if (filterValue == null
								|| fieldValue
										.startsWith(filterValue.toString())) {
							match = true;
							fl=1;
						
						} else {
							match = false;
							break;
						}
					} catch (Exception e) {
						match = false;
					}
				}
			}
			if(fl==1)
			{
				data.add(device);
				break;
			}
			
			if (match) {
				data.add(device);
				
			}
		}
		
		// sort
		if (sortField != null) {
			Collections.sort(data, new LazySorter(sortField, sortOrder));
		}
		// rowCount
		
		
		int dataSize = data.size();
			
		this.setRowCount(100100);
		if(fl==1)
		this.setRowCount(dataSize);	
		// paginate
		if (dataSize > pageSize) {
			try {
				return data.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				return data.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return data;
		}
	}
}
