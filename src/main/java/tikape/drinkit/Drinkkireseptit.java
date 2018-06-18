package tikape.drinkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.drinkit.dao.Dao;
import tikape.drinkit.dao.RaakaAineDao;
import tikape.drinkit.dao.AnnosDao;
import tikape.drinkit.dao.TilastoDao;
import tikape.drinkit.database.Database;
import tikape.drinkit.domain.RaakaAine;
import tikape.drinkit.domain.Annos;
import spark.Spark;
import java.util.stream.*;

public class Drinkkireseptit {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        Database database = new Database("jdbc:sqlite:db/drinkit.db");
        RaakaAineDao rd = new RaakaAineDao(database);
        AnnosDao ad = new AnnosDao(database);
        TilastoDao td = new TilastoDao(database);
        
        Spark.get("/", (req, res) -> {
            HashMap<String,List<Annos>> map = new HashMap<>();
            List<Annos> list = ad.findAll();          
            map.put("drinkit", list);
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/drinkki/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkki", ad.findOne(Integer.parseInt(req.params("id"))));
            map.put("raakaaineet", rd.findAll(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/raakaaine/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaine", rd.findOne(Integer.parseInt(req.params("id"))));
            map.put("drinkit", td.findByRaakaAine(Integer.parseInt(req.params("id"))));
            map.put("tilasto", td.tilastoRaakaAine(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "raakaaine");
        }, new ThymeleafTemplateEngine());
        
        /*Spark.post("/messages/:id", (req, res) -> {
            Date now = new Date();
            System.out.println(now);
            RaakaAine m = new RaakaAine(0,req.queryParams("content"), Integer.parseInt(req.params("id")), new Date());
            System.out.println(m.getMessage());
            
            md.save(m);
            res.redirect("/messages/" + Integer.parseInt(req.params("id")));
            
            return "";
        });
        */
        Spark.post("/drinkki", (req, res) -> {
            Annos t = new Annos(0,req.queryParams("nimi"));
                     
            Annos this_t = ad.save(t);
            res.redirect("/lisaaraakaaine/" + this_t.getId());
            
            return "";
        });
        
        Spark.get("/lisaaraakaaine/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkki", ad.findOne(Integer.parseInt(req.params("id"))));
            map.put("raakaaineet", rd.findAll(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "drinkitaddnew");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/lisaaraakaaine", (req, res) -> {
            RaakaAine t = new RaakaAine(0,req.queryParams("nimi"),Integer.parseInt(req.queryParams("jarjestys")), Integer.parseInt(req.queryParams("maara")), req.queryParams("ohje"));
            int id = Integer.parseInt(req.queryParams("drinkki"));
            RaakaAine this_t = rd.save(t, id);
            res.redirect("/lisaaraakaaine/" + id);
            
            return "";
        });
        
    }
    
    
}
