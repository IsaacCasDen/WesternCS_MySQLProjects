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
public class ListItem {
        
        private int mIndex=-1;
        private String mDetail;
        
        public int getIndex() {
            return mIndex;
        }
        public String getDetail() {
            return mDetail;
        }
        public void setDetail(String value) {
            mDetail=value;
        }
        
        public ListItem(int index, String detail) {
            mIndex=index;
            mDetail=detail;
        }
        
        public String toString() {
            return getDetail();
        }
        
    }
