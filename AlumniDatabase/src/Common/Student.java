/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author Isaac Denney
 */
public class Student {
        
    private int mId;
    private String mFirst;
    private String mMiddle;
    private String mLast;
    
    public int getId() {
        return mId;
    }
    
    public String getFirstName() {
        return mFirst;
    }
    public void setFirstName(String value) {
        mFirst=value;
    }
    
    public String getMiddleName() {
        return mMiddle;
    }
    public void setMiddleName(String value) {
        mMiddle=value;
    }
    
    public String getLastName() {
        return mLast;
    }
    public void setLastName(String value) {
        mLast=value;
    }
    
    public Student(String first,String middle,String last) {
        this(-1,first,middle,last);
    }
    public Student(int id, String first,String middle, String last) {
        mId=id;
        mFirst=first;
        mMiddle=middle;
        mLast=last;
        
        if (mFirst==null) {mFirst="";}
        if (mMiddle==null) {mMiddle="";}
        if (mLast==null) {mLast="";}
    }
    
    public String toString() {
        String value="";
        
        if (getFirstName()==null||getFirstName().equals("null")||getFirstName().trim().length()==0) {} else {value+=getFirstName();}
        if (getMiddleName()==null||getMiddleName().equals("null")||getMiddleName().trim().length()==0) {} else {if(value.length()>0){ value+=" ";} value+=getMiddleName();}
        if (getLastName()==null||getLastName().equals("null")||getLastName().trim().length()==0) {} else {if(value.length()>0){ value+=" ";} value+=getLastName();}
        if (getId()==-1) {value+="*";} else {value="("+ getId() +") "+value;}
        
        return value;
    }
    
}
