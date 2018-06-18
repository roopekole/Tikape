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
public class Tilasto {
    private int count;
    private int popular;
    private int percent;
    
    public Tilasto(int count, int popular, int percent) {
        this.count = count;
        this.popular = popular;
        this.percent = percent;
    }
    public int getCount() {
        return count;
    }
    public int getPopular() {
        return popular;
    }
    public int getPercent() {
        return percent;
    }
}
