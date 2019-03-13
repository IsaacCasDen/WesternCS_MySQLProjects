/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTable;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Isaac Denney
 */
public class DataColumn {
        
        private DataTable mParent;
        private String mName;
        private ArrayList mData;
        private String mType;
        
        public String getType() {
            return mType;
        }
        
        public int getIndex() {
            return mParent.getColumnIndex(this.getName());
        }
        
        public int getRowCount() {
            return mData.size();
        }
        
        public String getName() {
            return mName;
        }
        
        public ArrayList toArrayList() {
            return (ArrayList) mData.clone();
        }
        
        protected Object getValue(int index) {
            return mData.get(index);
        }
        protected void setValue(int index,Object value) {
            mData.set(index,value);
        }
        protected void addValue(Object value) {
            mData.add(value);
        }
        protected void removeValue(int index) {
            mData.remove(index);
        }
        
        public DataColumn(String colName, String typeName) throws Exception {
            if (colName.equals("")) { throw new InvalidColumnException("Column must have name"); }
            if (typeName.equals("")) { throw new InvalidColumnException("Column must have a type"); }
            
            mData=getList(typeName);
            mName=colName;
        }
        public DataColumn(DataTable parent,String colName, String typeName) throws Exception {
            if (parent==null) { throw new Exception("Parent must be assigned"); }
            if (colName.equals("")) { throw new Exception("Column must have name"); }
            if (typeName.equals("")) { throw new Exception("Column must have a type"); }
            
            mData=getList(typeName);
            mParent=parent;
            mName=colName;
        }
        protected void setParent(DataTable parent) {
            this.mParent=parent;
        }
        protected boolean setRowCount(int rowCount) {
            if (rowCount>this.getRowCount()) {
                for (int i=this.getRowCount();i<rowCount;i++) {
                    this.mData.add(null);
                }
                return true;
            } else {
                return false;
            }
        }
        
        private ArrayList getList(String typeName) throws Exception {
            ArrayList value=null;

            switch (typeName.toUpperCase()) {
                case "VARCHAR":
                    value=new ArrayList<String>();
                    mType = String.class.getSimpleName();
                    break;
                case "STRING":
                    value=new ArrayList<String>();
                    mType = String.class.getSimpleName();
                    break;
                case "DOUBLE":
                    value=new ArrayList<Double>();
                    mType = Double.class.getSimpleName();
                    break;
                case "DECIMAL":
                    value=new ArrayList<Double>();
                    mType = Double.class.getSimpleName();
                    break;
                case "TINYINT":
                    value=new ArrayList<Boolean>();
                    mType = boolean.class.getSimpleName();
                case "INT":
                    value=new ArrayList<Integer>();
                    mType = Integer.class.getSimpleName();
                    break;
                case "BIGINT":
                    value=new ArrayList<Long>();
                    mType = Long.class.getSimpleName();
                case "DATE":
                    value=new ArrayList<Date>();
                    mType = Date.class.getSimpleName();
                    break;
                case "DATETIME":
                    value=new ArrayList<Date>();
                    mType = Date.class.getSimpleName();
                    break;
                default:
                    throw new Exception("Column must have a valid type");
            }

            return value;
        }
        
        public class InvalidColumnException extends Exception {
            public InvalidColumnException(String message) {
                super(message);
            }
        }
    }
