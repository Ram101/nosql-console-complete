package org.tkm.backingbeans;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import com.cassandraclient.beans.Device;



public class LazySorter implements Comparator<Device>
{
private String sortField;
private SortOrder sortOrder;
 
	    public LazySorter(String sortField, SortOrder sortOrder) {
	        this.sortField = sortField;
	        this.sortOrder = sortOrder;
	    }
	 
	    public int compare(Device device1, Device device2) {
	        try {
	            Object value1 = Device.class.getField(this.sortField).get(device1);
	            Object value2 = Device.class.getField(this.sortField).get(device2);
	 
	            int value = ((Comparable)value1).compareTo(value2);
	             
	            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
	        }
	        catch(Exception e) {
	            throw new RuntimeException();
	        }
	    }
	}

