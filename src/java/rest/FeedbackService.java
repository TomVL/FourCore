package rest;

import projecten2.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
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

@Stateless
@Path("feedback")
public class FeedbackService {
    
    @Resource(name = "jdbc/onzebuurt")
    private DataSource source;
    
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Feedback getFeedback(@PathParam("id") int id) {
        
        int sitid = 0;
        int eveid = 0;
        try (Connection conn = source.getConnection()) {
            try(PreparedStatement stat = conn.prepareStatement("SELECT * FROM tblfeedback WHERE feedbackId = ?")){
                stat.setInt(1, id);
                
                try(ResultSet rs = stat.executeQuery()){
                    if (rs.next()){
                        sitid = rs.getInt("situatieId");
                        eveid = rs.getInt("eventId");
                    }
                    else
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement(
                    "SELECT * FROM tblfeedback INNER JOIN tblgebruiker ON tblfeedback.gebruikerId = tblgebruiker.gebruikerId INNER JOIN tblsituatie ON tblfeedback.situatieId = tblsituatie.situatieId INNER JOIN tblevent ON tblfeedback.eventId = tblevent.eventId INNER JOIN tblafbeelding ON tblfeedback.afbeeldingId = tblafbeelding.afbeeldingId WHERE tblfeedback.feedbackId = ?")) {
                                
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Feedback f = new Feedback();
                        f.setFeedbackId(rs.getInt("feedbackId"));
                        f.setType(rs.getString("type"));
                        f.setOmschrijving(rs.getString("omschrijving"));
                        
                        Melding m = new Melding();
                        if(sitid != 2 && eveid == 1){
                            m.setMeldingId(rs.getInt("tblsituatie.situatieId"));
                        }
                        if(sitid == 2 && eveid != 1){
                            m.setMeldingId(rs.getInt("tblevent.eventId"));
                        }
                        f.setMelding(m);
                        
                        Gebruiker g = new Gebruiker();
                        g.setGebruikerId(rs.getInt("tblgebruiker.gebruikerId"));
                        f.setGebruiker(g);
                        
                        Afbeelding a = new Afbeelding();
                        a.setAfbeeldingId(rs.getInt("tblafbeelding.afbeeldingId"));
                        f.setAfbeelding(a);
                        
                        return f;
                    } else {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
        
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFeedback(Feedback f) {
        try (Connection conn = source.getConnection()) {
            if (f.getGebruiker() == null || f.getMelding() == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een Feedback moet een auteur en melding hebben.").build());
            }
            int meldingId = f.getMelding().getMeldingId();
            int sofe = 0;

            try(PreparedStatement stat = conn.prepareStatement("SELECT * FROM tblsituatie WHERE situatieId=?"))
            {
                stat.setInt(1, meldingId);
                try(ResultSet rs = stat.executeQuery()){
                    if (rs.next()){
                        sofe = 2;
                    }
                }
            }
            
            try(PreparedStatement stat = conn.prepareStatement("SELECT * FROM tblevent WHERE eventId=?"))
            {
                stat.setInt(1, meldingId);
                try(ResultSet rs = stat.executeQuery()){
                    if (rs.next()){
                        sofe = 1;
                    }
                }
            }
            
                try (PreparedStatement stat = conn.prepareStatement("INSERT INTO tblfeedback VALUES(?, ?, ?, ?, ?, ?, ?)")) {
                    stat.setInt(1, 0);
                    stat.setInt(2, f.getGebruiker().getGebruikerId());
                    if (sofe == 2){
                        stat.setInt(3, f.getMelding().getMeldingId());
                        stat.setInt(4, 1);
                    }
                    if (sofe == 1){
                        stat.setInt(4, f.getMelding().getMeldingId());
                        stat.setInt(3, 2);
                    }
                    stat.setString(5, f.getType());
                    stat.setString(6, f.getOmschrijving());
                    stat.setInt(7, f.getAfbeelding().getAfbeeldingId());
                    stat.executeUpdate();
                    
                }
             
             }   catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
       return Response.created(URI.create("/" + f.getFeedbackId())).build();

    }
    
    @Path("{id}")
    @DELETE
    public void deleteFeedback(@PathParam("id") int id) {
        int afbeeldingId = 1;
        try (Connection conn = source.getConnection()) {
            PreparedStatement stat1 = conn.prepareStatement("SELECT * FROM tblfeedback WHERE feedbackId = ?");
            stat1.setInt(1, id);
            try(ResultSet rs = stat1.executeQuery()){
                if( !rs.next()){
                    throw new WebApplicationException(Response.Status.NOT_FOUND);
                }
                else
                    afbeeldingId = rs.getInt("afbeeldingId");
            }
            
            if (afbeeldingId != 1){
                PreparedStatement stat2 = conn.prepareStatement("DELETE FROM tblafbeelding WHERE afbeeldingId = ?");
                stat2.setInt(1, afbeeldingId);
                stat2.executeUpdate();
            }
            
            PreparedStatement stat3 = conn.prepareStatement("DELETE FROM tblfeedback WHERE feedbackId = ?");
            stat3.setInt(1, id);
            stat3.executeUpdate();

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateFeedback(@PathParam("id") int feedbackId, Feedback f) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM tblfeedback WHERE feedbackId = ?")) {
                stat.setInt(1, feedbackId);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            try (PreparedStatement stat = conn.prepareStatement("UPDATE tblfeedback SET type = ?, omschrijving = ? WHERE feedbackId = ?")) {
                stat.setInt(3, feedbackId);
                stat.setString(1, f.getType());
                stat.setString(2, f.getOmschrijving());
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
}