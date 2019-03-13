/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg195_0alumnidatabase_idenney;

import java.util.Scanner;

/**
 *
 * @author Isaac Denney
 */
public class Main {

    private static Scanner keyboard = new Scanner(System.in);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AlumniDatabaseWindow frame = new AlumniDatabaseWindow();
        frame.setVisible(true);
        //runProgram();
    }
    
    private static void runProgram() {
        boolean runAgain=false;
        do {
            printAssignments();
            int inp;
            do {
                print("Please Enter a Package Number:");
                inp = getIntegerInput();
                if (!isValidPackage(inp)) print("Please enter a valid package number");
            } while (!isValidPackage(inp));
            switch(inp) {
                case 1:
                    pkg195_1createtables_idenney.Main.main(null);
                    break;
                case 2:
                    pkg195_2populatetables_idenney.Main.main(null);
                    break;
                case 3:
                    pkg195_3populateprivate_idenney.Main.main(null);
                    break;
                case 4:
                    pkg195_4deletefromtables_idenney.Main.main(null);
                    break;
                case 5:
                    pkg195_5alumniqueries_idenney.Main.main(null);
                    break;
            }
            print("Run Again (y/n)?");
            runAgain=getRunAgain();
        } while(runAgain);
    }
    private static boolean isValidPackage(int val) {
        boolean value;
        value=(val==1||val==2||val==3||val==4||val==5);
        return value;
    }
    private static int getIntegerInput() {
        int value=-1;
        String inp=keyboard.nextLine();
        String val="";
     
        for (int i = 0;i<inp.length();i++) {
            if(inp.charAt(i)>='0'&&inp.charAt(i)<='9') 
            {
                val+=inp.charAt(i);
            } else {
                return value;
            } 
        }
        
        value=Integer.parseInt(val);
        
        return value;
    }
    private static void printAssignments() {
        String output="Select Package to Run:\n";
        
        output+="1.\t195_createTables_idenney\n";
        output+="2.\t195_populateTables_idenney\n";
        output+="3.\t195_populatePrivate_idenney\n";
        output+="4.\t195_deleteFromTables_idenney\n";
        output+="5.\t195_alumniQueries_idenney\n";
        
        print(output);
    }
    private static boolean getRunAgain() {
        String inp = keyboard.nextLine().toLowerCase();
        return inp.charAt(0)=='y';
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
}
