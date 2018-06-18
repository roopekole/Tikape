/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.drinkit.domain;

/**
 *
 * @author KOLEHRO1
 */
public class Annos {
    
    private int id;
    private String nimi;
    
    
    public Annos(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
        
    }
    
    public int getId() {
        return id;
    }
    
    public String getNimi() {
        return nimi;
    }
   
    
}
