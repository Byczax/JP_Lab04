package data.dao;

import java.util.List;

public interface DAO<T> {

    List<T> getAll();

    void add(T t);

    void update(String t, String[] params);

    void delete(String t);

    void readFile(String params);

}

