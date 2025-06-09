package com.example.casestudy.service;

import java.sql.SQLException;
import java.util.List;

public interface IGeneralService<E> {
    List<E> findAllWithStoreProcedure();
    void saveWithStoreProcedure(E entity) throws SQLException;
    public E findById(int id);
    public boolean update(E entity) throws SQLException;
    public boolean delete(int id) throws SQLException;
}
