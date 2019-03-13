/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg195_3populateprivate_idenney;

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
    protected static final CommandItem[] SELECT_ROW_TABLE_PRIVATE()
    {
        CommandItem[] value = new CommandItem[] 
        {
            new CommandItem ("SELECT * FROM db_9b2d05_cs19501.private WHERE studentId ={0};",1)
        };
        return value;
    }
    protected static final CommandItem[] UPDATE_ROW_TABLE_PRIVATE() {
        CommandItem[] value = new CommandItem[]
        {
            new CommandItem ("UPDATE db_9b2d05_cs19501.private SET ssn='{0}',noContact={1},canDonate={2},trackPHD={3} WHERE studentId = {4};",1)
        };
        return value;
    }
    protected  static final CommandItem[] GET_ROWS_TABLE_COMMENTS() {
        CommandItem[] value = new CommandItem[] {
            new CommandItem ("SELECT id,entryDate,comments FROM db_9b2d05_cs19501.comments WHERE studentId = {0};",1)
        };
        return value;
    }
    protected static final CommandItem[] INSERT_ROW_TABLE_COMMENTS() {
        CommandItem[] value = new CommandItem[] {
            new CommandItem ("INSERT INTO db_9b2d05_cs19501.comments (studentId,entryDate,comments) VALUES ({0},'{1}','{2}');",1)
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
            //FormPopulateTables form= new FormPopulateTables();
            //form.setVisible(true);
            DialogPopulateStudentPrivateInfo dialog = new DialogPopulateStudentPrivateInfo(frame,true);
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
