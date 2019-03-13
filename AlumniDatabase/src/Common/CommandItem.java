/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author IsaacCD
 */
public class CommandItem {
    String mQuery;
    int mResultType;
    
    public String getQuery() {
        return mQuery;
    }
    public void setQuery(String value) {
        mQuery=value;
    }
    
    public int getResultType() {
        return mResultType;
    }
    public void setResultType(int value) {
        mResultType=value;
    }
    
    public CommandItem(String query,int resulttype) {
        setQuery(query);
        setResultType(resulttype);
    }
}
