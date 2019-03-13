/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isaac Denney
 */
public class DataTable {
    private ArrayList columns=new ArrayList();
    private boolean showRowId=true;
    private boolean showColumnHeader=true;
    
    public int getColumnCount() {
        return columns.size();
    }
    public int getRowCount() {
        return ((DataColumn)columns.get(0)).getRowCount();
    }
    
    public boolean getShowRowIdEnabled() {
        return showRowId;
    }
    public void setShowRowIdEnabled(boolean value) {
        showRowId=value;
    }
    
    public boolean getShowColumnHeaderEnabled() {
        return showColumnHeader;
    }
    public void setShowColumnHeaderEnabled(boolean value) {
        showColumnHeader=value;
    }
    
    public DataColumn[] getColumns() {
        return (DataColumn[]) columns.clone();
    }
    
    public int getColumnIndex(String name) {
        for (int i=0;i<columns.size();i++) {
            if (((DataColumn) columns.get(i)).getName().toLowerCase().equals(name.toLowerCase())) {
                return i;
            }
        }
        
        return -1;
    }
    public DataColumn getColumn(String name) {
        for (int i=0;i<columns.size();i++) {
            if (((DataColumn)columns.get(i)).getName().toLowerCase().equals(name.toLowerCase())) {
                return getColumn(i);
            }
        }
        
        return null;
    }
    public DataColumn getColumn(int index) {
        return (DataColumn)columns.get(index);
    }
    
    public DataRow getRow(int index) {
        DataRow value=new DataRow(this,index);
        
        return value;
    }
    
    public Object getValue(int col, int row) {
        return getColumn(col).getValue(row);
    }
    public void setValue(int col,int row, Object value) {
        ((DataColumn)columns.get(col)).setValue(row,value);
    }
    
    public DataTable(java.sql.ResultSet value) {
        try {
            if (value!=null) {
            initialize(value);
        } else {
            throw new Exception("ResultSet cannot be null");
        }
        } catch (Exception ex) {
            
        }
        
    }
    
    private void initialize(ResultSet value) {
        try {
            ResultSetMetaData md = value.getMetaData();
            int rowId=0;
            
            columns.add(new DataColumn(this,"rowId","Int"));
            for (int c=0;c<md.getColumnCount();c++) {
                String typeString = md.getColumnTypeName(c+1);
                columns.add(new DataColumn(this,md.getColumnName(c+1),typeString));
            }
            
            while (value.next())
            {
                ((DataColumn)columns.get(0)).addValue(rowId);
                for (int c=1;c<columns.size();c++) {
                    ((DataColumn)columns.get(c)).addValue(value.getObject(c));
                }
                rowId++;
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void addColumn(DataColumn column) {
        if (column!=null) {
            if (getColumn(column.getName())!=null) {
                try {
                    throw new ColumnAlreadyExistsException("Column " + column.getName() + "Already exists");
                } catch (ColumnAlreadyExistsException ex) {
                    Logger.getLogger(DataTable.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                
                column.setParent(this);
                if (!column.setRowCount(this.getRowCount())) {
                    setRowCount(column.getRowCount());
                }
                columns.add(column);
            }
        }
    }
    private void setRowCount(int rowCount) {
        if (rowCount>this.getRowCount()) {
            for (int i=this.getRowCount();i<rowCount;i++) {
                this.setValue(0, i, i);
                for (int c=1;c<this.getColumnCount();c++) {
                    this.setValue(c, i, null);
                }
            }
        }
    }
    
    public String toString() {
        return toString(true);
    }
    public String toString(boolean useTab) {
        String value="";
        
        String sepChar="   ";
        if (!useTab) {
            sepChar="|";
        }
        
        int startInt=0;
        if (!showRowId) { startInt=1; }
        
        for (int c=startInt;c<columns.size();c++) {
            int maxChar=0;
            DataColumn col = getColumn(c);
            if (getShowColumnHeaderEnabled()) {
                if (col.getName().length()>maxChar) { maxChar=col.getName().length(); }
                if (value.contains("{colHead}")) {
                    value=value.replace("{colHead}\n","{colHead_"+ c +"}" + sepChar + "{colHead}\n");
                } else {
                    value+="{colHead_"+ c +"}" + sepChar + "{colHead}\n";
                }
                if (c==columns.size()-1) {
                    value=value.replace(sepChar + "{colHead}\n","\n");
                }
            }
            
            for (int r = 0;r<getRowCount();r++) {
                if (value.contains("{nextCol," + r + "}\n")) {
                    value=value.replace("{nextCol," + r + "}\n","{" + c + "," + r + "}" + sepChar + "{nextCol," + r + "}\n");
                } else {
                    value+="{" + c + "," + r + "}" + sepChar + "{nextCol," + r + "}\n";
                }
                if (col.getValue(r)!=null) {
                    int thisLen = col.getValue(r).toString().length();
                    if(thisLen>maxChar) {maxChar=thisLen;}
                }
                if (c==columns.size()-1) {
                    value=value.replace(sepChar + "{nextCol," + r + "}\n","\n");
                }
            }
            
            if (getShowColumnHeaderEnabled()) {value=value.replace("{colHead_" + c + "}",toFixedLength(col.getName(),maxChar));}
            for (int r = 0;r<getRowCount();r++) {
                if (col.getValue(r)!=null) {
                    value=value.replace("{" + c + "," + r + "}",toFixedLength(col.getValue(r).toString(),maxChar));
                } else {
                    value=value.replace("{" + c + "," + r + "}",toFixedLength("",maxChar));
                }
            }
        }
        
        return value;
    }
    private String toFixedLength(String text, int length) {
        String value = text;
        
        for (int i=0;i<(length-text.length());i++) {
            value+=" ";
        }
        
        return value;
    }
    
    public class ColumnAlreadyExistsException extends Exception {
        public ColumnAlreadyExistsException(String message) {
            super(message);
        }
    }
}

