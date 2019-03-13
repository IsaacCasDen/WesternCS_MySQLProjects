/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg195_2populatetables_idenney;

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
    
    private static FrameOutput getOutputWindow() {
        if (mOutputWindow==null) {
            mOutputWindow=new FrameOutput();
            mOutputWindow.setVisible(true);
        }
        return mOutputWindow;
    }
    private static FrameOutput mOutputWindow=null;
    
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
    
    protected static final CommandItem[] SELECT_ROWS_TABLE_STUDENTS()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT id,firstName,middleName,lastName FROM db_9b2d05_cs19501.students;",1)
        };
        return value;
    }
    protected static final CommandItem[] SELECT_ROWS_TABLE_MAJORSINFO()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT id,majorAbbr,majorName FROM db_9b2d05_cs19501.majorsinfo;",1)
        };
        return value;
    };
    protected static final CommandItem[] SELECT_ROW_TABLE_MAJORSINFO()
    {
        CommandItem[] value = new CommandItem[]
        {
                new CommandItem ("SELECT id,majorAbbr,majorName FROM db_9b2d05_cs19501.majorsinfo WHERE id = {0};",1)
        };
        return value;
    };
    protected static final CommandItem[] INSERT_ROW_TABLE_MAJORSINFO() {
        CommandItem[] value = new CommandItem[]
        {
                new CommandItem ("INSERT INTO db_9b2d05_cs19501.majorsinfo (majorAbbr,majorName) VALUES ('{0}','{1}');",1)
        };
        return value;
    }
    protected static final CommandItem[] UPDATE_ROW_TABLE_MAJORSINFO() {
        CommandItem[] value = new CommandItem[]
        {
                new CommandItem ("UPDATE db_9b2d05_cs19501.majorsinfo SET majorAbbr = '{0}',majorName = '{1}' WHERE id = {2};",1)
        };
        return value;
    }
    protected static final CommandItem[] DELETE_ROW_TABLE_MAJORSINFO() {
        CommandItem[] value = new CommandItem[]
        {
                new CommandItem ("DELETE from db_9b2d05_cs19501.majorsinfo WHERE id = {0};",1)
        };
        return value;
    }
    protected static final CommandItem[] SELECT_ROW_TABLE_STUDENTS()
    {
        CommandItem[] value = new CommandItem[]
        {
                new CommandItem ("SELECT id,firstName,middleName,lastName FROM db_9b2d05_cs19501.students WHERE id = {0};",1)
        };
        return value;
    };
    protected static final CommandItem[] SELECT_ROWS_STUDENT_TABLE_EMAILS()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT id,studentId,emailAddress FROM db_9b2d05_cs19501.emails WHERE studentId={0};",1)
        };
        return value;
    }
    protected static final CommandItem[] DELETE_ROW_STUDENT_EMAIL_TABLE_EMAILS()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("DELETE FROM db_9b2d05_cs19501.emails WHERE id={0};",1)
        };
        return value;
    }
        protected static final CommandItem[] DELETE_ROW_STUDENT_MAJOR_TABLE_MAJORS()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("DELETE FROM db_9b2d05_cs19501.majors WHERE studentId={0} AND majorId={1};",1)
        };
        return value;
    }
    protected static final CommandItem[] SELECT_ROWS_STUDENT_TABLE_MAJORS()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT id,majorAbbr,majorName FROM db_9b2d05_cs19501.majorsinfo WHERE id IN (SELECT majorId FROM db_9b2d05_cs19501.majors WHERE studentId = {0});",1)
        };
        return value;
    }
    protected static final CommandItem[] INSERT_ROW_TABLE_EMAILS() {
        CommandItem[] value = new CommandItem[]
        {
                new CommandItem ("INSERT INTO db_9b2d05_cs19501.emails (studentId,emailAddress) VALUES ({0},'{1}');",1)
        };
        return value;
    }
    protected static final CommandItem[] UPDATE_ROW_TABLE_EMAILS() {
        CommandItem[] value = new CommandItem[]
        {
                new CommandItem ("UPDATE db_9b2d05_cs19501.emails SET emailAddress='{0}' WHERE id = {1};",1)
        };
        return value;
    }
    protected static final CommandItem[] INSERT_ROW_TABLE_MAJORS() {
        CommandItem[] value = new CommandItem[]
        {
                new CommandItem ("INSERT INTO db_9b2d05_cs19501.majors (studentId,majorId) VALUES ({0},{1});",1)
        };
        return value;
    }
    protected static final CommandItem[] INSERT_ROW_TABLE_STUDENTS() {
        CommandItem[] value = new CommandItem[]
        {
                new CommandItem ("INSERT INTO db_9b2d05_cs19501.students (firstName,middleName,lastName) VALUES ('{0}','{1}','{2}');",1),
                new CommandItem ("INSERT INTO db_9b2d05_cs19501.private (studentId) VALUES (LAST_INSERT_ID())",1)
        };
        return value;
    }
    protected static final CommandItem[] UPDATE_ROW_TABLE_STUDENTS() {
        CommandItem[] value = new CommandItem[]
        {
                new CommandItem ("UPDATE db_9b2d05_cs19501.students SET firstName='{0}',middleName='{1}',lastName='{2}' WHERE id = '{3}';",1)
        };
        return value;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        runMain();
    }
    protected static void runMain() {
        Frame frame = new Frame();
        runMain(frame);
        frame.dispose();
    }
    public static void runMain(Frame frame) {
        if (frame!=null) {
            getOutputWindow().clearOutput();
            //FormPopulateTables form= new FormPopulateTables();
            //form.setVisible(true);
            DialogPopulateTables dialog = new DialogPopulateTables(frame,true);
            frame.setVisible(false);
            dialog.setVisible(true);
            frame.setVisible(true);
        }
        
    }
    
    protected static void print(String value) {
        print(value,true);
    }
    protected static void print(String value,boolean newLine) {
        getOutputWindow().addOutput(value,newLine);
    }
/*    
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
*/
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
        Statement stmt=null;
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
                        if (command.indexOf("INSERT")>=0) {
                            value.add(stmt.executeUpdate(command));
                        } else if (command.indexOf("UPDATE")>=0) {
                            value.add(stmt.executeUpdate(command));
                        } else if (command.indexOf("DELETE")>=0) {
                            value.add(stmt.executeUpdate(command));
                        } else if (command.indexOf("SELECT")>=0) {
                            ResultSet rs = stmt.executeQuery(command);
                            DataTable dt = new DataTable(rs);
                            value.add(dt);
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
