package database;

import java.util.ArrayList;

public interface DAOPattern<T> {

    public T getById(String id);

    public ArrayList<T> getAll();

    public void insert(T t);

    public void update(T t);

    public void delete(T t);
}
