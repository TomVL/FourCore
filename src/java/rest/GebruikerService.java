package rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import projecten2.*;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.sql.DataSource;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Stateless
@Path("gebruiker")
public class GebruikerService {

    @Resource(name = "root/onzebuurt")
    private DataSource source;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGebruiker(Gebruiker g) throws FileNotFoundException {

        try (Connection conn = source.getConnection()) {

            try (PreparedStatement stat = conn.prepareStatement("INSERT INTO tblgebruiker VALUES(?, ?, ?, ?)")) {
                
                stat.setInt(1, 0);
                stat.setString(2, g.getFacebookAccount());
                stat.setString(3, g.getTwitterAccount());
                stat.setInt(4, 1);
                stat.executeUpdate();


            } catch (SQLException ex) {
                throw new WebApplicationException(ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(GebruikerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.created(URI.create("/" + g.getGebruikerId())).build();

    }

 
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Gebruiker getGebruiker(@PathParam("id") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(
                    "SELECT * FROM tblgebruiker "
                    + "INNER JOIN tblafbeelding ON tblgebruiker.afbeeldingId = tblafbeelding.afbeeldingId "
                    + "WHERE tblgebruiker.gebruikerId = ?")) {

                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Gebruiker g = new Gebruiker();
                        g.setGebruikerId(rs.getInt("tblgebruiker.gebruikerId"));
                        g.setFacebookAccount(rs.getString("facebookaccount"));
                        g.setTwitterAccount(rs.getString("twitteraccount"));

                        Afbeelding a = new Afbeelding();
                        a.setAfbeeldingId(rs.getInt("afbeeldingId"));
                        g.setAfbeelding(a);

                        return g;
                    } else {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Gebruiker> getAllUsers() {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM tblgebruiker")) {
                try (ResultSet rs = stat.executeQuery()) {
                    List<Gebruiker> results = new ArrayList<>();
                    while (rs.next()) {
                        Gebruiker g = new Gebruiker();
                        g.setGebruikerId(rs.getInt("gebruikerId"));
                        g.setFacebookAccount(rs.getString("facebookaccount"));
                        g.setTwitterAccount(rs.getString("twitteraccount"));
                        Afbeelding a = new Afbeelding();
                        a.setAfbeeldingId(rs.getInt("afbeeldingId"));
                        g.setAfbeelding(a);
                        results.add(g);
                    }
                    return results;
                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @Path("{id}")
    @DELETE
    public void deleteGebruiker(@PathParam("id") int id) {
        int afbeeldingId = 1;
        try (Connection conn = source.getConnection()) {
            PreparedStatement stat1 = conn.prepareStatement("SELECT * FROM tblgebruiker WHERE gebruikerId = ?");
            stat1.setInt(1, id);
            try(ResultSet rs = stat1.executeQuery()){
                if( !rs.next()){
                    throw new WebApplicationException(Status.NOT_FOUND);
                }
                else
                    afbeeldingId = rs.getInt("afbeeldingId");
            }
            
            if (afbeeldingId != 1){
                PreparedStatement stat2 = conn.prepareStatement("DELETE FROM tblafbeelding WHERE afbeeldingId = ?");
                stat2.setInt(1, afbeeldingId);
                stat2.executeUpdate();
            }
            
            PreparedStatement stat3 = conn.prepareStatement("DELETE FROM tblgebruiker WHERE gebruikerId = ?");
            stat3.setInt(1, id);
            stat3.executeUpdate();

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(@PathParam("id") int gebruikerId, Gebruiker g) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM tblgebruiker WHERE gebruikerId = ?")) {
                stat.setInt(1, gebruikerId);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            try (PreparedStatement stat = conn.prepareStatement("UPDATE tblgebruiker SET facebookaccount = ?, twitteraccount =? WHERE gebruikerId = ?")) {
                stat.setInt(3, gebruikerId);
                stat.setString(1, g.getFacebookAccount());
                stat.setString(2, g.getTwitterAccount());
               

                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
}
