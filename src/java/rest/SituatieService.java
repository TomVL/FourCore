/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import projecten2.Afbeelding;
import projecten2.Gebruiker;
import projecten2.Melding;
import projecten2.Situatie;

@Stateless
@Path("situatie")
    
public class SituatieService {
    
    @Resource(name = "root/onzebuurt")
    private DataSource source;
    
     @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMelding(Melding m) {
        try (Connection conn = source.getConnection()) {
            if (m.getGebruiker() == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een situatie moet een auteur hebben.").build());
            }

            
                try (PreparedStatement stat = conn.prepareStatement("INSERT INTO "
                        + "tblsituatie VALUES(?, ?, ?, ?, ?, ?, ?, ?)")) {
                    stat.setInt(1, 0);
                    stat.setInt(2, m.getGebruiker().getGebruikerId());
                    stat.setString(3, m.getTitel());
                    stat.setString(4, m.getType());
                    stat.setString(5, m.getLocatie());
                    stat.setString(6, m.getOmschrijving());
                    stat.setDate(7, m.getDatum());
                    stat.setInt(8, m.getAfbeelding().getAfbeeldingId());
                    stat.executeUpdate();
                }
             
             }   catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
       return Response.created(URI.create("/" + m.getMeldingId())).build();

    }
    
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Situatie> getAllSituaties() {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM tblsituatie INNER JOIN tblgebruiker ON tblsituatie.gebruikerId = tblgebruiker.gebruikerId INNER JOIN tblafbeelding ON tblsituatie.afbeeldingId = tblafbeelding.afbeeldingId")) 
            
            {
                try (ResultSet rs = stat.executeQuery()) {
                    List<Situatie> results = new ArrayList<>();
                    while (rs.next()) {
                     
                        Situatie s = new Situatie();
                        s.setMeldingId(rs.getInt("situatieId"));
                        s.setTitel(rs.getString("titel"));
                        s.setType(rs.getString("type"));
                        s.setLocatie(rs.getString("locatie"));
                        s.setOmschrijving(rs.getString("omschrijving"));
                        s.setDatum(rs.getDate("datum"));
                        
                        
                        Gebruiker g = new Gebruiker();
                        g.setGebruikerId(rs.getInt("tblgebruiker.gebruikerId"));
                        s.setGebruiker(g);
                        
                        Afbeelding a = new Afbeelding();
                        a.setAfbeeldingId(rs.getInt("tblafbeelding.afbeeldingId"));
                        s.setAfbeelding(a);
                       results.add(s);
                    }
                    return results;
                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Situatie getSituatie(@PathParam("id") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(
                    "SELECT * FROM tblsituatie INNER JOIN tblgebruiker ON tblsituatie.gebruikerId = tblgebruiker.gebruikerId INNER JOIN tblafbeelding ON tblsituatie.afbeeldingId = tblafbeelding.afbeeldingId WHERE tblsituatie.situatieId = ?")) {
                                
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Situatie s = new Situatie();
                        s.setMeldingId(rs.getInt("situatieId"));
                        s.setTitel(rs.getString("titel"));
                        s.setType(rs.getString("type"));
                        s.setLocatie(rs.getString("locatie"));
                        s.setOmschrijving(rs.getString("omschrijving"));
                        s.setDatum(rs.getDate("datum"));
                        
                        Gebruiker g = new Gebruiker();
                        g.setGebruikerId(rs.getInt("tblgebruiker.gebruikerId"));
                        g.setFacebookAccount(rs.getString("facebookaccount"));
                        g.setTwitterAccount(rs.getString("twitteraccount"));
                        g.setAfbeelding(new Afbeelding(rs.getInt("tblgebruiker.afbeeldingId"), null, null));
                        s.setGebruiker(g);
                        
                        Afbeelding a = new Afbeelding();
                        a.setAfbeeldingId(rs.getInt("tblafbeelding.afbeeldingId"));
                        s.setAfbeelding(a);
                        
                        return s;
                    } else {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
        
    }
    
    @Path("{id}")
    @DELETE
    public void deleteSituatie(@PathParam("id") int id) {
        int afbeeldingId = 1;
        try (Connection conn = source.getConnection()) {
            PreparedStatement stat1 = conn.prepareStatement("SELECT * FROM tblsituatie WHERE situatieId = ?");
            stat1.setInt(1, id);
            try(ResultSet rs = stat1.executeQuery()){
                if( !rs.next()){
                    throw new WebApplicationException(Response.Status.NOT_FOUND);
                }
                else
                    afbeeldingId = rs.getInt("afbeeldingId");
            }
            
            if (afbeeldingId != 1){
                PreparedStatement stat2 = conn.prepareStatement("DELETE FROM tblsituatie WHERE afbeeldingId = ?");
                stat2.setInt(1, afbeeldingId);
                stat2.executeUpdate();
            }
            
            PreparedStatement stat3 = conn.prepareStatement("DELETE FROM tblsituatie WHERE situatieId = ?");
            stat3.setInt(1, id);
            stat3.executeUpdate();

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateSituatie(@PathParam("id") int situatieId, Situatie s) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM tblsituatie WHERE situatieId = ?")) {
                stat.setInt(1, situatieId);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            try (PreparedStatement stat = conn.prepareStatement("UPDATE tblsituatie SET titel = ?, type = ?, locatie = ?, omschrijving = ? WHERE situatieId = ?")) {
                stat.setInt(5, situatieId);
                stat.setString(1, s.getTitel());
                stat.setString(2, s.getType());
                stat.setString(3, s.getLocatie());
                stat.setString(4, s.getOmschrijving());
               

                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
}