package cjt.androiddisk.Model.biz;

import android.content.Context;
import android.content.SharedPreferences;

import cjt.androiddisk.Model.bean.FtpInfo;

/**
 * Created by CJT on 2018/12/5.
 */

public class FtpSPBiz {

    private static final String SP_NAME = "FtpConfig";
    private static final int SP_MODE = Context.MODE_PRIVATE;

    public void initSP(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME,SP_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        FtpInfo ftpInfo = new FtpInfo();
        ftpInfo.defaultInfo();
        editor.putBoolean("exist",true);
        editor.putString("user",ftpInfo.getUser());
        editor.putString("password",ftpInfo.getPassword());
        editor.putString("homeDir",ftpInfo.getHomeDir());
        editor.putInt("port",ftpInfo.getPort());
        editor.putInt("maxLoginNum",ftpInfo.getMaxLoginNum());
        editor.putInt("transRate",ftpInfo.getTransRate());
        editor.putBoolean("anonymous", ftpInfo.isAnonymous());
        editor.putBoolean("writePermission", ftpInfo.isWritePermission());
        editor.apply();
    }

    public FtpInfo getFtpInfo(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME,SP_MODE);
        FtpInfo ftpInfo = new FtpInfo();
        if (!sharedPreferences.getBoolean("exist",false)){
            initSP(context);
            ftpInfo.defaultInfo();
        }
        else {
            ftpInfo.setUser(sharedPreferences.getString("user",""));
            ftpInfo.setPassword(sharedPreferences.getString("password",""));
            ftpInfo.setHomeDir(sharedPreferences.getString("homeDir",""));
            ftpInfo.setPort(sharedPreferences.getInt("port",-1));
            ftpInfo.setMaxLoginNum(sharedPreferences.getInt("maxLoginNum",-1));
            ftpInfo.setTransRate(sharedPreferences.getInt("transRate",-1));
            ftpInfo.setAnonymous(sharedPreferences.getBoolean("anonymous", false));
            ftpInfo.setWritePermission(sharedPreferences.getBoolean("writePermission", false));
        }
        return ftpInfo;
    }

    public void setFtpSP(Context context, FtpInfo ftpInfo){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME,SP_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("exist",true);
        editor.putString("user",ftpInfo.getUser());
        editor.putString("password",ftpInfo.getPassword());
        editor.putInt("port",ftpInfo.getPort());
        editor.putInt("maxLoginNum",ftpInfo.getMaxLoginNum());
        editor.putInt("transRate",ftpInfo.getTransRate());
        editor.putBoolean("anonymous", ftpInfo.isAnonymous());
        editor.putBoolean("writePermission", ftpInfo.isWritePermission());
        editor.putString("homeDir",ftpInfo.getHomeDir());
        editor.apply();
    }

}
