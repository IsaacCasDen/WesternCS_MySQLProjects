/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isaac Denney Isaac.Denney@western.edu
 */
public class Comment {
    
    private int mId;
    private Date mDate;
    private String mText;
    
    public int getIndex() {
        return mId;
    }
    public Date getDate() {
        return mDate;
    }
    public String getText() {
        return mText;
    }
    
    public Comment(Date datestamp,String text) {
        this(-1,datestamp,text);
    }
    public Comment(int id,Date datestamp,String text) {
        mId=id;
        mDate=datestamp;
        mText=text;
    }
    
    @Override
    public String toString() {
        return getDate() + " - " + getText();
    }
}
