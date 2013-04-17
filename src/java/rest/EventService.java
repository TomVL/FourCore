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
import projecten2.Event;
import projecten2.Gebruiker;
import projecten2.Melding;
import projecten2.Situatie;

/**
 *
 * @author Tom
 */
@Stateless
@Path("event")
public class EventService {
    @Resource(name = "root/onzebuurt")
    private DataSource source;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEvent(Event e) {
        try (Connection conn = source.getConnection()) {
            if (e.getGebruiker() == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een event moet een auteur hebben.").build());
            }
           
                try (PreparedStatement stat = conn.prepareStatement("INSERT INTO "
                        + "tblevent VALUES(?, ?, ?, ?, ?, ?, ?, ?)")) {
                    stat.setInt(1, 0);
                    stat.setInt(2, e.getGebruiker().getGebruikerId());
                    stat.setString(3, e.getTitel());
                    stat.setString(4, e.getType());
                    stat.setString(5, e.getLocatie());
                    stat.setString(6, e.getOmschrijving());
                    stat.setDate(7, e.getDatum());
                    stat.setInt(8, e.getAfbeelding().getAfbeeldingId());
                    stat.executeUpdate();
                }
              

            }   catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
        
       return Response.created(URI.create("/" + e.getMeldingId())).build();

    }
@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAllEvents() {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM tblevent "
                    + "INNER JOIN tblgebruiker ON tblevent.gebruikerId = tblgebruiker.gebruikerId "
                    + "INNER JOIN tblafbeelding ON tblevent.afbeeldingId = tblafbeelding.afbeeldingId")) 
            
            {
                try (ResultSet rs = stat.executeQuery()) {
                    List<Event> results = new ArrayList<>();
                    while (rs.next()) {
                     
                        Event e = new Event();
                        e.setMeldingId(rs.getInt("eventId"));
                        e.setTitel(rs.getString("titel"));
                        e.setType(rs.getString("type"));
                        e.setLocatie(rs.getString("locatie"));
                        e.setOmschrijving(rs.getString("omschrijving"));
                        e.setDatum(rs.getDate("datum"));
                        
                        
                        Gebruiker g = new Gebruiker();
                        g.setGebruikerId(rs.getInt("tblgebruiker.gebruikerId"));
                        e.setGebruiker(g);
                        
                        Afbeelding a = new Afbeelding();
                        a.setAfbeeldingId(rs.getInt("tblafbeelding.afbeeldingId"));
                        e.setAfbeelding(a);
                       results.add(e);
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
    public Event getEvent(@PathParam("id") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(
                    "SELECT * FROM tblevent "
                    + "INNER JOIN tblgebruiker ON tblevent.gebruikerId = tblgebruiker.gebruikerId "
                    + "INNER JOIN tblafbeelding ON tblevent.afbeeldingId = tblafbeelding.afbeeldingId "
                    + "WHERE tblevent.eventId = ?")) {
                                
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Event e = new Event();
                        e.setMeldingId(rs.getInt("eventId"));
                        e.setTitel(rs.getString("titel"));
                        e.setType(rs.getString("type"));
                        e.setLocatie(rs.getString("locatie"));
                        e.setOmschrijving(rs.getString("omschrijving"));
                        e.setDatum(rs.getDate("datum"));
                        
                        Gebruiker g = new Gebruiker();
                        g.setGebruikerId(rs.getInt("tblgebruiker.gebruikerId"));
                        g.setFacebookAccount(rs.getString("facebookaccount"));
                        g.setTwitterAccount(rs.getString("twitteraccount"));
                        g.setAfbeelding(new Afbeelding(rs.getInt("tblgebruiker.afbeeldingId"), null, null));
                        e.setGebruiker(g);
                        
                        Afbeelding a = new Afbeelding();
                        a.setAfbeeldingId(rs.getInt("tblafbeelding.afbeeldingId"));
                        e.setAfbeelding(a);
                        
                        return e;
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
    public void deleteEvent(@PathParam("id") int id) {
        int afbeeldingId = 1;
        try (Connection conn = source.getConnection()) {
            PreparedStatement stat1 = conn.prepareStatement("SELECT * FROM tblevent WHERE eventId = ?");
            stat1.setInt(1, id);
            try(ResultSet rs = stat1.executeQuery()){
                if( !rs.next()){
                    throw new WebApplicationException(Response.Status.NOT_FOUND);
                }
                else
                    afbeeldingId = rs.getInt("afbeeldingId");
            }
            
            if (afbeeldingId != 1){
                PreparedStatement stat2 = conn.prepareStatement("DELETE FROM tblevent WHERE afbeeldingId = ?");
                stat2.setInt(1, afbeeldingId);
                stat2.executeUpdate();
            }
            
            PreparedStatement stat3 = conn.prepareStatement("DELETE FROM tblevent WHERE eventId = ?");
            stat3.setInt(1, id);
            stat3.executeUpdate();

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEvent(@PathParam("id") int eventId, Event e) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM tblevent WHERE eventId = ?")) {
                stat.setInt(1, eventId);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            try (PreparedStatement stat = conn.prepareStatement("UPDATE tblevent SET titel = ?, type = ?, locatie = ?, omschrijving = ? WHERE eventId = ?")) {
                stat.setInt(5, eventId);
                stat.setString(1, e.getTitel());
                stat.setString(2, e.getType());
                stat.setString(3, e.getLocatie());
                stat.setString(4, e.getOmschrijving());
               

                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
}
