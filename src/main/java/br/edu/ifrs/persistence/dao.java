package br.edu.ifrs.persistence;

import java.util.List;

public interface dao<T> {

    public void insert(T obj);

    public void update(T obj);

    public void delete(int id);

    public T find(int id);

    public List<T> findAll(int offset, int limit);
}
