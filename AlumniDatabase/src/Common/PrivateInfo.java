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
public class PrivateInfo {
    
    private int mId=-1;
    private String[] mSSN = new String[3];
    private boolean mCanContact;
    private boolean mCanDonate;
    private boolean mTrackPHD;
    
    public int getIndex() {return mId;}
    public String[] getSSN() {return mSSN.clone();}
    public boolean getCanContact() {return mCanContact;}
    public boolean getCanDonate() {return mCanDonate;}
    public boolean getTrackPHD() {return mTrackPHD;}
    
    public PrivateInfo(String ssn,boolean canContact,boolean canDonate,boolean trackPHD) {
        this(-1,ssn,canContact,canDonate,trackPHD);
    }
    public PrivateInfo(int id,String ssn,boolean canContact,boolean canDonate,boolean trackPHD) {
        mId=id;
        mSSN=parseSSN(ssn);
        mCanContact=canContact;
        mCanDonate=canDonate;
        mTrackPHD=trackPHD;
    }
    private String[] parseSSN(String ssn) {
        String[] value=new String[3];
        if (ssn!=null&&ssn.length()==11) {
            String[] splitSSN = ssn.split("-");
            if (splitSSN.length==3) {
                for (int i=0;i<splitSSN.length;i++) {
                    value[i]=splitSSN[i];
                }
            }
        }
        return value;
    }
}
