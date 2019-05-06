package cjt.androiddisk.Model.biz;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

import java.io.File;
import java.io.FileOutputStream;

import cjt.androiddisk.Model.bean.FtpInfo;
import cjt.androiddisk.Model.bean.WifiInfo;
import cjt.androiddisk.Model.utils.MD5;

import android.content.Context;
import android.util.Log;


/**
 * Created by CJT on 2018/11/26.
 */

public class FtpBiz implements IFtpBiz{

    private FtpServer ftpServer = null;

    @Override
    public void createConfigFile(Context context, FtpInfo ftpInfo) {
        StringBuffer config = new StringBuffer();
        String head;
        if ( ftpInfo.isAnonymous() ) head = "ftpserver.user.anonymous.";
        else head = "ftpserver.user."+ftpInfo.getUser()+".";
        config.append(head).append("userpassword=").append(MD5.msgToMD5L32(ftpInfo.getPassword())).append("\n")
                .append(head).append("homedirectory=").append(ftpInfo.getHomeDir()).append("\n")
                .append(head).append("enableflag=true\n")
                .append(head).append("writepermission=").append(ftpInfo.isWritePermission()).append("\n")
                .append(head).append("maxloginnumber=").append(ftpInfo.getMaxLoginNum()).append("\n")
                .append(head).append("maxloginperip=").append(ftpInfo.getMaxLoginNum()).append("\n")
                .append(head).append("idletime=300\n")
                .append(head).append("uploadrate=").append(ftpInfo.getTransRate()).append("\n")
                .append(head).append("downloadrate=").append(ftpInfo.getTransRate()).append("\n");
        String confText = String.valueOf(config);

        FileOutputStream fos;
        try {
            fos = context.openFileOutput("users.properties",Context.MODE_PRIVATE);
            fos.write(confText.getBytes());
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void startFtpServer(Context context, WifiInfo wifiInfo, FtpInfo ftpInfo) {
        FtpServerFactory ftpServerFactory = new FtpServerFactory();
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();
        //设置配置文件
        File[] files = context.getFilesDir().listFiles();
        File configFile = null;
        for (File f:files)
            if (f.getName().equals("users.properties")){
                configFile = f;
                Log.i(this.toString(),"find Users.properties");
                break;
            }
        userManagerFactory.setFile(configFile);
        ftpServerFactory.setUserManager(userManagerFactory.createUserManager());
        // 设置监听IP和端口号
        Log.i(this.toString(), String.valueOf(ftpInfo.getPort()));
        listenerFactory.setServerAddress(wifiInfo.getIpv4());
        listenerFactory.setPort(ftpInfo.getPort());
        ftpServerFactory.addListener("default",listenerFactory.createListener());
        // 开启FTP服务
        ftpServer = ftpServerFactory.createServer();
        try {
            ftpServer.start();
        } catch (FtpException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopFtpServer() {
        if (ftpServer!=null) ftpServer.stop();
        ftpServer=null;
    }
}
