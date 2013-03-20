package projecten2;

import javax.swing.*;
import java.sql.*;

public class DBConnectie {
    
    public Connection connectDB(){
          
    try {
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/projectenDB", "root", "toor");
    System.out.println("> Databank connectie geslaagd!");
    Statement stat = con.createStatement();
    stat.executeUpdate("INSERT INTO tblgebruiker VALUES(0, 0, 0)");
    stat.close();
    return con;
    }
    catch(SQLException e) {
        JOptionPane.showMessageDialog(null, e);
        return null;
    }
    }
    
    
}
