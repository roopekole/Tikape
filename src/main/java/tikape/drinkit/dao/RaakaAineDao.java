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
import java.util.*;

/**
 *
 * @author KOLEHRO1
 */
public class RaakaAineDao implements Dao<RaakaAine, Integer> {

    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<RaakaAine> findAll(int drinkkiid) throws SQLException {
        List<RaakaAine> aineet = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select raakaaine.id as id, raakaaine.nimi as nimi, annosraakaaine.jarjestys, annosraakaaine.maara, annosraakaaine.ohje from raakaaine \n" +
                "left join annosraakaaine on\n" +
                "raakaaine.id = annosraakaaine.raakaaine_id\n" +
                "where annos_id = ? ORDER BY jarjestys asc");
            stmt.setInt(1, drinkkiid);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                aineet.add(new RaakaAine(result.getInt("id"), result.getString("nimi"), result.getInt("jarjestys"), result.getInt("maara"), result.getString("ohje")));
            }
        }

        return aineet;
    }

    @Override
    public RaakaAine findOne(Integer id) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT nimi, id FROM Raakaaine WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                return new RaakaAine(result.getInt("id"), result.getString("nimi"),0,0,"");
            }
        }

        return null;
    }
    
    private RaakaAine findByName(String name) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi FROM Raakaaine WHERE nimi = ?");
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new RaakaAine(result.getInt("id"), result.getString("nimi"),0,0,"0");
        }
    }
     
    public RaakaAine save(RaakaAine object, int id) throws SQLException {
        
        //Validate whether raaka-aine exists
        RaakaAine byName = findByName(object.getNimi());

        if (byName != null) {
            Connection conn2 = database.getConnection();
            PreparedStatement stmt2 = conn2.prepareStatement("INSERT INTO AnnosRaakaaine"
                    + " (jarjestys,maara,ohje,annos_id,raakaaine_id)"
                    + " VALUES (?,?,?,?,?)");
            stmt2.setInt(1, object.getJarjestys());
            stmt2.setInt(2, object.getMaara());
            stmt2.setString(3, object.getOhje());
            stmt2.setInt(4, id);
            stmt2.setInt(5, byName.getId());
            stmt2.executeUpdate();
            conn2.close();
            return byName;
            
        } 

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)");
            stmt.setString(1, object.getNimi());
            stmt.executeUpdate();
            conn.close();
        }

        //return findByName(object.getNimi());
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Raakaaine"
                + " WHERE nimi = ?");
        stmt.setString(1, object.getNimi());
        ResultSet rs = stmt.executeQuery();
        
        
        
        rs.next(); // vain 1 tulos

        RaakaAine t = new RaakaAine(rs.getInt("id"),rs.getString("nimi"),0,0,"0");
        stmt = conn.prepareStatement("INSERT INTO AnnosRaakaaine"
                + " (jarjestys,maara,ohje,annos_id,raakaaine_id)"
                + " VALUES (?,?,?,?,?)");
        
        stmt.setInt(1, object.getJarjestys());
        stmt.setInt(2, object.getMaara());
        stmt.setString(3, object.getOhje());
        stmt.setInt(4, id);
        stmt.setInt(5, rs.getInt("id"));
        stmt.executeUpdate();
        conn.close();  
        
        
        return new RaakaAine(0,"0",0,0,"0");
    }
    

    

   
   
}
