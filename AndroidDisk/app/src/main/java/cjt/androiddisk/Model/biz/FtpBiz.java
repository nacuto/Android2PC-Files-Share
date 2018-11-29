package cjt.androiddisk.Model.biz;

import org.apache.ftpserver.FtpServer;

import java.io.File;
import java.io.FileOutputStream;

import cjt.androiddisk.R;

import android.content.Context;


/**
 * Created by CJT on 2018/11/26.
 */

public class FtpBiz implements IFtpBiz{

    private Context mainActivity;

    private String hostIp = "";
    private int port = 8090;
    private String dirName = "/mnt/sdcard/AndroidDisk/";
    private String filename = dirName+"users.properties";
    private FtpServer ftpServer = null;

    public FtpBiz(Context mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void createConfigFile() {
        File dir = new File(dirName);
        if (!dir.exists()) dir.mkdir();
        // 将配置文件写入文件
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(mainActivity.getString(R.string.users).getBytes());
            if (fos!=null) fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void startFtpServer(String ip) {

    }

    @Override
    public void stopFtpServer() {

    }
}
