package dbtimekeeping;

import java.util.ArrayList;

public interface IDBTimekeeping<T> {
	
    public T getATimekeepingByID(String id);
    
    public ArrayList<T> getTimekeepingsByEmployeeID(String employeeID);
    
    public ArrayList<T> getAllTimekeepings(); 
}
