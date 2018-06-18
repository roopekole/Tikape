/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.drinkit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.drinkit.database.Database;
import tikape.drinkit.domain.RaakaAine;
import tikape.drinkit.domain.Annos;
import tikape.drinkit.domain.Tilasto;
import java.util.*;

/**
 *
 * @author KOLEHRO1
 */

    

public class TilastoDao {
 private Database database;

    public TilastoDao(Database database) {
        this.database = database;
    } 
    
    public List<Annos> findByRaakaAine(int raakaaineid) throws SQLException {
        List<Annos> annokset = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select id, nimi from annos where id in (select annos_id from annosraakaaine where raakaaine_id = ?)");
            stmt.setInt(1, raakaaineid);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                annokset.add(new Annos(result.getInt("id"), result.getString("nimi")));
            }
        }

        return annokset;
    }    
    public Tilasto tilastoRaakaAine(int raakaaineid) throws SQLException {
        int count = 0;
        int popular = 1;
        int percent = 0;
        //Count
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select count(annos_id) as count from annosraakaaine where raakaaine_id = ?");
            stmt.setInt(1, raakaaineid);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
             count = result.getInt("count");
            }
        }
        //Percent
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select (select count(raakaaine_id) from annosraakaaine where raakaaine_id = ? )*100 / (select count(id) from annos) as percent");
            stmt.setInt(1, raakaaineid);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
             percent = result.getInt("percent");
            }
        }
        //Popularity
        //Since it's quite clumsy to get rank with SQLite, let Java get the position in the query result
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select count(raakaaine_id) as count, raakaaine_id from annosraakaaine group by raakaaine_id order by count desc");
            

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                System.out.println(popular);
             if(raakaaineid == result.getInt("count")) {
                 break;
             } else {
                 popular++;
             }
             
            }
        }
        Tilasto t = new Tilasto(count,popular,percent);
        return t;
    }    
}
