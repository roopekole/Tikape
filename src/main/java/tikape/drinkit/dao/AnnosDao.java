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

/**
 *
 * @author KOLEHRO1
 */
public class AnnosDao implements Dao<Annos,Integer> {
    
    private Database database;

    public AnnosDao(Database database) {
        this.database = database;
    }
    @Override
    public List<Annos> findAll() throws SQLException {
        List<Annos> annokset = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT id, nimi FROM annos").executeQuery()) {

            while (result.next()) {
                annokset.add(new Annos(result.getInt("id"), result.getString("nimi")));
            }
            
        }

        return annokset;
    }
    
    @Override
    public Annos findOne(Integer id) throws SQLException {
    
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT nimi, id FROM Annos WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                return new Annos(result.getInt("id"), result.getString("nimi"));
            }
        }

        return null;
    }
    
    
    public Annos save(Annos object) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos"
                + " (nimi)"
                + " VALUES (?)");
        stmt.setString(1, object.getNimi());
        
        
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Annos"
                + " WHERE nimi = ?");
        stmt.setString(1, object.getNimi());
        ResultSet rs = stmt.executeQuery();
        
        
        rs.next(); // vain 1 tulos

        Annos t = new Annos(rs.getInt("id"),rs.getString("nimi"));
        rs.close();

        conn.close();
        
        return t;
    }
    

}
