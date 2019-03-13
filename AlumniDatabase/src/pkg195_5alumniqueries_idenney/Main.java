/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg195_5alumniqueries_idenney;

import Common.CommandItem;
import DataTable.DataTable;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isaac Denney
 */
public class Main {
// <editor-fold desc="MySQL Server Code">
    private static final String SERVER_PATH = "jdbc:mysql://mysql5016.site4now.net"; //"jdbc:mysql://sql3.freemysqlhosting.net/sql3143466";
    private static final String SERVER_USER = "9b2d05_cs19501"; // "sql3143466";
    private static final String SERVER_PASSWORD = "Confidant6^"; //"4uncsTCw4J";
    
    protected static Connection getConnection() {
        Connection value = null;
        try {
            value = DriverManager.getConnection(
                    SERVER_PATH,   //database url
                    SERVER_USER,  //MySQL username
                    SERVER_PASSWORD);   //MySQL password
            //if (!value.isClosed())
                //System.out.println("*** Connect ***\n");
            
        } catch (Exception ex) {
            value=null;
        }
 
        return value;
    }
    protected static boolean closeConnection(Connection value) {
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
    // </editor-fold>
    
    protected static final CommandItem[] SELECT_ROWS_FULLNAME_SSN_TABLE_STUDENTS_PRIVATE()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT REPLACE(CONCAT(IFNULL(firstName,''),' ',IFNULL(middleName,''),' ',IFNULL(lastName,'')),'  ',' ') fullName, ssn FROM db_9b2d05_cs19501.students s LEFT JOIN db_9b2d05_cs19501.private p ON s.id=p.studentId;",1)
        };
        return value;
    }
    protected static final CommandItem[] SELECT_ROWS_EMAIL_TABLE_STUDENTS_EMAILS()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT emailAddress FROM db_9b2d05_cs19501.emails WHERE studentId=(SELECT id FROM db_9b2d05_cs19501.students WHERE firstName{0} AND middleName{1} AND lastName{2} LIMIT 1);",1)
        };
        return value;
    }
    protected static final CommandItem[] SELECT_ROWS_MAJORS_TABLE_MAJORSINFO()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT majorAbbr,majorName FROM db_9b2d05_cs19501.majors m LEFT JOIN db_9b2d05_cs19501.majorsinfo i ON m.majorId=i.id WHERE m.studentId=(SELECT id FROM db_9b2d05_cs19501.students WHERE firstName{0} AND middleName{1} AND lastName{2} LIMIT 1);",1)
        };
        return value;
    }
    protected static final CommandItem[] SELECT_ROWS_FULLNAME_TABLE_MAJORS()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT REPLACE(CONCAT(IFNULL(firstName,''),' ',IFNULL(middleName,''),' ',IFNULL(lastName,'')),'  ',' ') fullName FROM db_9b2d05_cs19501.students WHERE id IN (SELECT studentId FROM db_9b2d05_cs19501.majors m WHERE m.majorId=(SELECT id FROM db_9b2d05_cs19501.majorsinfo WHERE majorAbbr LIKE 'CS' LIMIT 1));",1)
        };
        return value;
    }
    protected static final CommandItem[] SELECT_ROW_ISBARBARACS_TABLE_STUDENTS()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT IFNULL((SELECT 'yes' FROM db_9b2d05_cs19501.students s LEFT JOIN db_9b2d05_cs19501.majors m ON s.id=m.studentId LEFT JOIN db_9b2d05_cs19501.majorsinfo i ON m.majorId=i.id WHERE i.majorAbbr='CS' AND s.firstName='Barbara' AND s.middleName IS NULL AND s.lastName='Johnson'),'no') IsCSMajor;",1)
        };
        return value;
    }
    protected static final CommandItem[] SELECT_ROWS_ONEMAJOR_TABLE_STUDENTS()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT * FROM (SELECT REPLACE(CONCAT(IFNULL(s.firstName,''),' ',IFNULL(s.middleName,''),' ',IFNULL(s.lastName,'')),'  ',' ') fullName, (SELECT COUNT(majorId) FROM db_9b2d05_cs19501.majors m WHERE s.id=m.studentId) majorCount FROM db_9b2d05_cs19501.students s) a WHERE majorCount=1;",1)
        };
        return value;
    }
    protected static final CommandItem[] SELECT_ROWS_NAME_EMAILS_TABLE_STUDENTS()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT REPLACE(CONCAT(IFNULL(s.firstName,''),' ',IFNULL(s.middleName,''),' ',IFNULL(s.lastName,'')),'  ',' ') fullName,emailAddress FROM db_9b2d05_cs19501.students s LEFT JOIN db_9b2d05_cs19501.emails e ON s.id=e.studentId LEFT JOIN db_9b2d05_cs19501.majors m ON s.id=m.studentId LEFT JOIN db_9b2d05_cs19501.majorsinfo i ON m.majorId=i.id WHERE i.majorAbbr='CS';",1)
        };
        return value;
    }
    protected static final CommandItem[] SELECT_ROWS_NAME_NOTCS_TABLE_STUDENTS()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT REPLACE(CONCAT(IFNULL(s.firstName,''),' ',IFNULL(s.middleName,''),' ',IFNULL(s.lastName,'')),'  ',' ') fullName FROM db_9b2d05_cs19501.students s WHERE s.id NOT IN (SELECT m.studentId FROM db_9b2d05_cs19501.majors m LEFT JOIN db_9b2d05_cs19501.majorsinfo i ON m.majorId=i.id WHERE i.majorAbbr='CS')",1)
        };
        return value;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        runMain();
    }
    public static void runMain() {
        Frame frame = new Frame();
        runMain(frame);
        frame.dispose();
    }
    public static void runMain(Frame frame) {
        if (frame!=null) {
            //FormPopulateTables form= new FormPopulateTables();
            //form.setVisible(true);
            DialogAlumniQueries dialog = new DialogAlumniQueries(frame,true);
            frame.setVisible(false);
            dialog.setVisible(true);
            frame.setVisible(true);
        }
        
    }
        
    public static void print(String text) {
        print(text,true);
    }
    public static void print(String text, boolean newLine) {
        if (newLine) {
            System.out.println(text);
        } else {
            System.out.print(text);
        }
    }
    protected static void showCommands(CommandItem[] commands) {
        print("------\nRunning the following commands:\n" + getCommands(commands) +"\n------");
    }
    protected static String getCommands(CommandItem[] commands) {
        String value = "";
        for (int i=0;i<commands.length;i++) {
            if (i>0) value+="\n";
            value += commands[i].getQuery();
        }
        return value;
    }
    protected static ArrayList runCommands(CommandItem[] commands) {
        Statement stmt;
        ArrayList value=null;
        Connection conn=null;
        try {
            conn = getConnection();
            if (conn!=null) {
                if (!conn.isClosed()) {
                    stmt=conn.createStatement();
                    value = new ArrayList();
                    for (int i=0;i<commands.length;i++) {
                        String command = commands[i].getQuery();
                        if (command.contains("INSERT")) {
                            value.add(stmt.executeUpdate(command));
                        } else if (command.contains("UPDATE")) {
                            value.add(stmt.executeUpdate(command));
                        } else if (command.contains("DELETE")) {
                            value.add(stmt.executeUpdate(command));
                        } else if (command.contains("SELECT")) {
                            ResultSet rs = stmt.executeQuery(command);
                            DataTable dt = new DataTable(rs);
                            value.add(dt);
                        } else {
                            value.add(stmt.executeUpdate(command));
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn!=null) {if (!conn.isClosed()) {conn.close();}}
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return value;
    }
}
