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
@Path("like")
public class LikeService {
    
    @Resource(name = "root/onzebuurt")
    private DataSource source;
    
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Like getLike(@PathParam("id") int id) {
        
        int sitid = 0;
        int eveid = 0;
        try (Connection conn = source.getConnection()) {
            try(PreparedStatement stat = conn.prepareStatement("SELECT * FROM tbllike WHERE likeId = ?")){
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
                    "SELECT * FROM tbllike INNER JOIN tblgebruiker ON tbllike.gebruikerId = tblgebruiker.gebruikerId INNER JOIN tblsituatie ON tbllike.situatieId = tblsituatie.situatieId INNER JOIN tblevent ON tbllike.eventId = tblevent.eventId WHERE tbllike.likeId = ?")) {
                                
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Like l = new Like();
                        l.setLikeId(rs.getInt("likeId"));
                                                
                        Melding m = new Melding();
                        if(sitid != 2 && eveid == 1){
                            m.setMeldingId(rs.getInt("tblsituatie.situatieId"));
                        }
                        if(sitid == 2 && eveid != 1){
                            m.setMeldingId(rs.getInt("tblevent.eventId"));
                        }
                        l.setMelding(m);
                        
                        Gebruiker g = new Gebruiker();
                        g.setGebruikerId(rs.getInt("tblgebruiker.gebruikerId"));
                        l.setGebruiker(g);
                        
                        return l;
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
    public Response addLike(Like l) {
        try (Connection conn = source.getConnection()) {
            if (l.getGebruiker() == null || l.getMelding() == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een Like moet een auteur en melding hebben.").build());
            }
            int meldingId = l.getMelding().getMeldingId();
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
            
                try (PreparedStatement stat = conn.prepareStatement("INSERT INTO tbllike VALUES(?, ?, ?, ?)")) {
                    stat.setInt(1, 0);
                    stat.setInt(2, l.getGebruiker().getGebruikerId());
                    if (sofe == 2){
                        stat.setInt(3, l.getMelding().getMeldingId());
                        stat.setInt(4, 1);
                    }
                    if (sofe == 1){
                        stat.setInt(4, l.getMelding().getMeldingId());
                        stat.setInt(3, 2);
                    }
                    stat.executeUpdate();
                    
                }
             
             }   catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
       return Response.created(URI.create("/" + l.getLikeId())).build();

    }
    
    @Path("{id}")
    @DELETE
    public void deleteLike(@PathParam("id") int id) {
        
        try (Connection conn = source.getConnection()) {
            PreparedStatement stat1 = conn.prepareStatement("SELECT * FROM tbllike WHERE likeId = ?");
            stat1.setInt(1, id);
            try(ResultSet rs = stat1.executeQuery()){
                if( !rs.next()){
                    throw new WebApplicationException(Response.Status.NOT_FOUND);
                }
            }
            
            PreparedStatement stat3 = conn.prepareStatement("DELETE FROM tbllike WHERE likeId = ?");
            stat3.setInt(1, id);
            stat3.executeUpdate();

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    
}