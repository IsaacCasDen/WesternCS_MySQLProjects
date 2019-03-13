/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTable;

import java.util.ArrayList;

/**
 *
 * @author Isaac Denney
 */
public class DataRow {
        private DataTable mParent;
        private ArrayList columns=new ArrayList();
        private boolean showRowId=true;
        private boolean showColumnHeader=true;
        private int mIndex;
        
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
            return mParent.getColumns();
        }
        
        public int getColumnIndex(String name) {
            return mParent.getColumnIndex(name);
        }
        public DataColumn getColumn(String name) {
            return mParent.getColumn(name);
        }
        public DataColumn getColumn(int index) {
            return mParent.getColumn(index);
        }
        
        public Object getValue(int col) {
            return getColumn(col).getValue(mIndex);
        }
        public void setValue(int col,Object value) {
            ((DataColumn)columns.get(col)).setValue(mIndex,value);
        }
        
        private DataRow() {
            
        }
        protected DataRow(DataTable parent,int rowIndex) {
            mParent=parent;
            mIndex=rowIndex;
        }
}
