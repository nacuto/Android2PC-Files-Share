package cjt.androiddisk.Model.bean;

/**
 * Created by CJT on 2018/11/27.
 */

public class FtpInfo {
    private int port;
    private String user,password;

    public void setPort(int port){
        this.port = port;
    }
    public void setUser(String user){
        this.user = user;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public int getPort(){
        return port;
    }
    public String getUser(){
        return user;
    }
    public String getPassword(){
        return password;
    }


}
