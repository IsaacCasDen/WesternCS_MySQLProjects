/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg195_1createtables_idenney;

import Common.CommandItem;
import DataTable.DataTable;
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

    private static FrameOutput getOutputWindow() {
        if (mOutputWindow==null) {
            mOutputWindow=new FrameOutput();
        }
        mOutputWindow.setVisible(true);
        return mOutputWindow;
    }
    private static FrameOutput mOutputWindow=null;
    
    // <editor-fold desc="MySQL Server Code">
    private static final String SERVER_PATH = "jdbc:mysql://mysql5016.site4now.net"; //"jdbc:mysql://sql3.freemysqlhosting.net/sql3143466";
    private static final String SERVER_USER = "9b2d05_cs19501"; // "sql3143466";
    private static final String SERVER_PASSWORD = "Confidant6^"; //"4uncsTCw4J";
    
    protected static Connection getConnection() {
        Connection value;
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
    
    protected static final CommandItem[] DROP_TABLES()
            {
                CommandItem[] value = new CommandItem[] {
                    new CommandItem ("DROP TABLE IF EXISTS db_9b2d05_cs19501.majors;",0),
                    new CommandItem ("DROP TABLE IF EXISTS db_9b2d05_cs19501.comments;",0),
                    new CommandItem ("DROP TABLE IF EXISTS db_9b2d05_cs19501.private;",0),
                    new CommandItem ("DROP TABLE IF EXISTS db_9b2d05_cs19501.emails;",0),
                    new CommandItem ("DROP TABLE IF EXISTS db_9b2d05_cs19501.students;",0),
                    new CommandItem ("DROP TABLE IF EXISTS db_9b2d05_cs19501.majorsinfo;",0)
                };
                return value;
            };
    protected static final CommandItem[] CREATE_TABLE_STUDENTS()
            {
                CommandItem[] value = new CommandItem[] {
                    new CommandItem("CREATE TABLE db_9b2d05_cs19501.students ( id INT AUTO_INCREMENT PRIMARY KEY, firstName VARCHAR(40), middleName VARCHAR(40), lastName VARCHAR(40) NOT NULL );",0)
                };
                return value;
            };
    protected static final CommandItem[] CREATE_TABLE_PRIVATE()
            {
                CommandItem[] value = new CommandItem[] {
                    new CommandItem("CREATE TABLE db_9b2d05_cs19501.private ( studentId INT UNIQUE PRIMARY KEY, ssn VARCHAR(11) NULL UNIQUE, noContact BOOLEAN DEFAULT FALSE, canDonate BOOLEAN DEFAULT FALSE, trackPHD BOOLEAN DEFAULT FALSE, CONSTRAINT private_id_fk_students FOREIGN KEY (studentId) REFERENCES students (id) ON DELETE CASCADE );",0)
                };
                return value;
            };
    protected static final CommandItem[] CREATE_TABLE_COMMENTS()
            {
                CommandItem[] value = new CommandItem[] {
                    new CommandItem("CREATE TABLE db_9b2d05_cs19501.comments ( id INT AUTO_INCREMENT PRIMARY KEY, studentId INT, entryDate DATETIME, comments VARCHAR(255),CONSTRAINT comments_id_fk_private FOREIGN KEY (studentId) REFERENCES private (studentId) ON DELETE CASCADE );",0)
                };
                return value;
            };
    protected static final CommandItem[] CREATE_TABLE_EMAILS()
            {
                CommandItem[] value = new CommandItem[] {
                    new CommandItem("CREATE TABLE db_9b2d05_cs19501.emails ( id INT AUTO_INCREMENT PRIMARY KEY, studentId INT NOT NULL, emailAddress VARCHAR(255) NOT NULL, CONSTRAINT emails_id_fk_students FOREIGN KEY (studentId) REFERENCES students (id) ON DELETE CASCADE );",0)
                };
                return value;
            };
    protected static final CommandItem[] CREATE_TABLE_MAJORSINFO()
            {
                CommandItem[] value = new CommandItem[] {
                    new CommandItem("CREATE TABLE db_9b2d05_cs19501.majorsinfo ( id INT AUTO_INCREMENT PRIMARY KEY, majorAbbr VARCHAR(10) UNIQUE NOT NULL, majorName VARCHAR(40) UNIQUE NOT NULL );",0)
                };
                return value;
            };
    protected static final CommandItem[] CREATE_TABLE_MAJORS()
            {
                CommandItem[] value = new CommandItem[] {
                    new CommandItem("CREATE TABLE db_9b2d05_cs19501.majors ( id INT AUTO_INCREMENT PRIMARY KEY, studentId INT NOT NULL,majorId INT NOT NULL, FOREIGN KEY (studentId) REFERENCES students (id) ON DELETE CASCADE , FOREIGN KEY (majorId) REFERENCES majorsinfo (id) ON DELETE CASCADE );",0)
                };
                return value;
            };
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            
        }
        getOutputWindow().clearOutput();
        createTables();
    }
    protected static void print(String value) {
        print(value,true);
    }
    protected static void print(String value,boolean newLine) {
        getOutputWindow().addOutput(value,newLine);
    }
    private static void createTables() {
        dropTables();
        createTableStudents();
        createTablePrivate();
        createTableComments();
        createTableEmails();
        createTableMajorsInfo();
        createTableMajors();
    }
    private static void dropTables() {
        CommandItem[] commands=DROP_TABLES();
        print("Running the following commands:\n" + getCommands(DROP_TABLES()));
        ArrayList value = runCommands(commands);
        //if ((boolean)value[0]) {print("Success!");} else {print((String)value[1]);}
    }
    private static void createTableStudents(){
        CommandItem[] commands=CREATE_TABLE_STUDENTS();
        print("Running the following commands:\n" + getCommands(commands));
        ArrayList value = runCommands(commands);
        //if ((boolean)value[0]) {print("Success!");} else {print((String)value[1]);}
    }
    private static void createTablePrivate() {
        CommandItem[] commands=CREATE_TABLE_PRIVATE();
        print("Running the following commands:\n" + getCommands(commands));
        ArrayList value = runCommands(commands);
        //if ((boolean)value[0]) {print("Success!");} else {print((String)value[1]);}
    }
    private static void createTableComments() {
        CommandItem[] commands=CREATE_TABLE_COMMENTS();
        print("Running the following commands:\n" + getCommands(commands));
        ArrayList value = runCommands(commands);
        //if ((boolean)value[0]) {print("Success!");} else {print((String)value[1]);}
    }
    private static void createTableEmails() {
        CommandItem[] commands=CREATE_TABLE_EMAILS();
        print("Running the following commands:\n" + getCommands(commands));
        ArrayList value = runCommands(commands);
        //if ((boolean)value[0]) {print("Success!");} else {print((String)value[1]);}
    }
    private static void createTableMajorsInfo() {
        CommandItem[] commands=CREATE_TABLE_MAJORSINFO();
        print("Running the following commands:\n" + getCommands(commands));
        ArrayList value = runCommands(commands);
        //if ((boolean)value[0]) {print("Success!");} else {print((String)value[1]);}
    }
    private static void createTableMajors() {
        CommandItem[] commands=CREATE_TABLE_MAJORS();
        print("Running the following commands:\n" + getCommands(commands));
        ArrayList value = runCommands(commands);
        //if ((boolean)value[0]) {print("Success!");} else {print((String)value[1]);}
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
}
