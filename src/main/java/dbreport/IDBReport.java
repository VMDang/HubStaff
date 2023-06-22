package dbreport;

import java.util.ArrayList;

public interface IDBReport<T> {
	
	public ArrayList<T> getReportByUnitID(String unitID);
	
	public ArrayList<T> getReportAll();
    
}
