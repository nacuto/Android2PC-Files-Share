package cjt.androiddisk.Model.bean;

/**
 * Created by CJT on 2018/11/26.
 */

public class WifiInfo {

    private String SID,ipv4;
    private boolean connected;

    public String getSID(){
        return SID;
    }
    public String getIpv4(){
        return ipv4;
    }
    public boolean isConnected(){
        return connected;
    }

    public void setSID(String SID){
        this.SID = SID;
    }
    public void setConnected(boolean connected){
        this.connected = connected;
    }
    public void setIpv4(String ipv4){
        this.ipv4 = ipv4;
    }

}
