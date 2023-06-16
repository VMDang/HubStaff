package hrsystem;

import java.util.ArrayList;

public interface IHRSystem<T>{
	
    public T getAEmployee(String id);

    public ArrayList<T> getAllEmployees();
    
}
