/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SqlManager;

import DataTable.DataTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isaac Denney
 */
public class SqlManager {

    public static String serverPath; //"jdbc:mysql://sql3.freemysqlhosting.net/sql3143466"
    public static String serverUser; //"sql3143466"
    public static String serverPassword; //"4uncsTCw4J"
    
    public Connection getConnection() {
        Connection value = null;
        try {
            value = DriverManager.getConnection(
                    this.serverPath,   //database url
                    this.serverUser,  //MySQL username
                    this.serverPassword);   //MySQL password
            //if (!value.isClosed())
                //System.out.println("*** Connect ***\n");
        } catch (Exception ex) {
            value=null;
        }
 
        return value;
    }
    public boolean closeConnection(Connection value) {
        if (value!=null) {
            try {
                value.close();
                //if (value.isClosed())
                    //System.out.println("\n*** Disconnect ***\n");

                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }
    
    public SqlManager(String serverPath,String serverUser,String serverPassword) {
        
        SqlManager.serverPath=serverPath;
        SqlManager.serverUser=serverUser;
        SqlManager.serverPassword=serverPassword;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            
        }
    }
    
    public DataTable getResults(String sqlCommand) {
        
        DataTable value=null;
        
        try
        {
            ResultSet rs;
            Statement stmt;
            Connection con=getConnection();
            if (con!=null&&sqlCommand.length()>0) {
                stmt = con.createStatement();
                rs = stmt.executeQuery(sqlCommand);
                value = new DataTable(rs);
            }
            closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return value;
    }
}
