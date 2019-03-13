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
public class Major {
    private int mId;
    private String mAbbr;
    private String mName;
    
    public int getId() {
        return mId;
    }
    public String getAbbr() {
        return mAbbr;
    }
    public String getName() {
        return mName;
    }
    
    public Major(String abbr,String name) {
        this(-1,abbr,name);
    }
    public Major(int id,String abbr,String name) {
        mId=id;
        mAbbr=abbr;
        mName=name;
        
        if (mAbbr==null) {mAbbr="";}
        if (mName==null) {mName="";}
    }
    
    public String toString() {
        String value="";
        
        if (getAbbr()==null||getAbbr().equals("null")||getAbbr().trim().length()==0) {} else {value+=getAbbr();}
        if (getName()==null||getName().equals("null")||getName().trim().length()==0) {} else {if(value.length()>0){ value+=" - ";} value+=getName();}
        if (getId()==-1) {value+="*";} else {value="("+ getId() +") "+value;}
        
        return value;
    }
}
