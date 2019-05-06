package cjt.androiddisk.Model.bean;

import java.io.Serializable;

import cjt.androiddisk.Model.utils.MD5;

/**
 * Created by CJT on 2018/11/27.
 */

public class FtpInfo implements Serializable{
    private String user,password,homeDir;
    private int port,maxLoginNum,transRate;
    private boolean anonymous,writePermission;

    public void defaultInfo(){
        anonymous = true;
        user = "";
        password = "";
        homeDir = "/storage/emulated/0";
        port = 8090;            // 1025 - 65534
        maxLoginNum = 2;
        transRate = 1000000;   // 0~3500000 -> 0~3.5M
        writePermission = true;
    }

    public void setPort(int port){
        this.port = port;
    }
    public void setUser(String user){
        this.user = user;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setHomeDir(String homeDir){
        this.homeDir = homeDir;
    }
    public void setAnonymous(boolean anonymous){
        this.anonymous = anonymous;
    }
    public void setWritePermission(boolean writePermission){
        this.writePermission = writePermission;
    }
    public void setMaxLoginNum(int maxLoginNum){
        this.maxLoginNum = maxLoginNum;
    }
    public void setTransRate(int transRate){
        this.transRate = transRate;
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
    public String getHomeDir(){
        return homeDir;
    }
    public boolean isAnonymous(){
        return anonymous;
    }
    public boolean isWritePermission(){
        return writePermission;
    }
    public int getMaxLoginNum(){
        return maxLoginNum;
    }
    public int getTransRate(){
        return transRate;
    }


}
