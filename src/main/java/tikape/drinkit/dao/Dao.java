/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.drinkit.dao;

/**
 *
 * @author KOLEHRO1
 */
import java.sql.*;
import java.util.*;

public interface Dao<T, K> {

    T findOne(K key) throws SQLException;

    List<T> findAll() throws SQLException;
    
    //void save(T object) throws SQLException;
    
    //void update(T object) throws SQLException;

    //void delete(K key) throws SQLException;
}
