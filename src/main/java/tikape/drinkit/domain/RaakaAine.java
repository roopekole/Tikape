/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.drinkit.domain;
import java.util.*;

/**
 *
 * @author KOLEHRO1
 */
public class RaakaAine {
    
    private int id;
    private String nimi;
    private int jarjestys;
    private int maara;
    private String ohje;
    
    public RaakaAine(int id, String nimi, int jarjestys, int maara, String ohje) {
        this.id = id;
        this.nimi = nimi;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }
    
    public int getId() {
        return id;
    }
    public String getNimi() {
        return nimi;
    }
    public int getMaara() {
        return maara;
    }
     public int getJarjestys() {
        return jarjestys;
    }
    public String getOhje() {
        return ohje;
    } 
}
