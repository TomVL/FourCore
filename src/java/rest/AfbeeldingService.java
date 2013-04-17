package rest;

import projecten2.Afbeelding;
import projecten2.Gebruiker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("afbeeldingen")
public class AfbeeldingService {

    @Resource(name = "root/onzebuurt")
    private DataSource source;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public int addAfbeelding(Afbeelding a) {
        int prim = 0;
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("insert into tblafbeelding values(?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stat.setInt(1, 0);
                File image = a.getAfbeeldingIn();
                FileInputStream fis = new FileInputStream(image);
                stat.setBinaryStream(2, fis, image.length());
                stat.executeUpdate();

                ResultSet rs = stat.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    prim = rs.getInt(1);
                    return prim;
                } else {
                    return prim;
                }
            }

        } catch (SQLException e) {
            throw new WebApplicationException(e);
        } catch (FileNotFoundException e) {
            throw new WebApplicationException(e);
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Afbeelding getAfbeelding(@PathParam("id") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(
                    "SELECT * FROM tblafbeelding WHERE afbeeldingId = ?")) {

                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Afbeelding a = new Afbeelding();
                        a.setAfbeeldingId(rs.getInt("afbeeldingId"));

                        a.setAfbeeldingUit(ImageIO.read(rs.getBinaryStream("afbeelding")));
                        return a;
                    } else {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }

    }

}
