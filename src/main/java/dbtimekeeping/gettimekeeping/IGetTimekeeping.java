package dbtimekeeping.gettimekeeping;

import java.util.ArrayList;

public interface IGetTimekeeping<T> {
	
    public T getATimekeepingByID(String id);
    
    public ArrayList<T> getTimekeepingsByEmployeeID(String employeeID);
    
    public ArrayList<T> getAllTimekeepings(); 
}
