package cjt.androiddisk.presenter;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.util.Locale;

import cjt.androiddisk.Model.bean.FtpInfo;
import cjt.androiddisk.Model.bean.WifiInfo;
import cjt.androiddisk.Model.biz.FtpBiz;
import cjt.androiddisk.Model.biz.NetWorkStateReceiver;
import cjt.androiddisk.Model.utils.Permission;
import cjt.androiddisk.Model.biz.FtpSPBiz;
import cjt.androiddisk.view.IMainActivity;

/**
 * Created by CJT on 2018/11/26.
 */

public class MainActivityPresenter {
    public NetWorkStateReceiver netWorkStateReceiver;
    private IMainActivity iMainActivity;
    private Context context;
    private WifiInfo wifiInfo;
    private Permission permission=new Permission();
    private FtpSPBiz ftpSPBiz=new FtpSPBiz();
    private FtpBiz ftpBiz=new FtpBiz();



    public MainActivityPresenter(IMainActivity iMainActivity){
        netWorkStateReceiver = new NetWorkStateReceiver(this);
        this.iMainActivity = iMainActivity;
        context = iMainActivity.getContext();
    }

    public void verifyPermission(){
        permission.verifyPermission(context);
    }

    public void initFtpSP(){
        new Thread(){
            @Override
            public void run() {
                ftpSPBiz.initSP(context);
            }
        }.start();
    }

    public FtpInfo getFtpInfo(){
        return ftpSPBiz.getFtpInfo(context);
    }

    public void startFtpServer(){
        new Thread(){
            @Override
            public void run() {
                FtpInfo ftpInfo = ftpSPBiz.getFtpInfo(context);
                ftpBiz.createConfigFile(context,ftpInfo);
                ftpBiz.startFtpServer(context,wifiInfo,ftpInfo);
                System.out.println("start Ftp");
            }
        }.start();
    }

    public void stopFtpServer(){
        new Thread(){
            @Override
            public void run() {
                ftpBiz.stopFtpServer();
                System.out.println("stop Ftp");
            }
        }.start();

    }

    public void onNetWorkStateChange(NetworkInfo wifiConnInf, WifiManager wifiMgr){
        wifiInfo = new WifiInfo();
        wifiInfo.setConnected(wifiConnInf.isConnected());
        wifiInfo.setSID(wifiMgr.getConnectionInfo().getSSID());
        int ipInt = wifiMgr.getConnectionInfo().getIpAddress();
        String ipv4 = String.format(
                Locale.getDefault(),
                "%d.%d.%d.%d",
                (ipInt&0xff), (ipInt>>8&0xff), (ipInt>>16&0xff), (ipInt>>24&0xff));
        wifiInfo.setIpv4(ipv4);
        iMainActivity.netWorkStateChange(wifiInfo);
    }

    public void saveReturnConf(FtpInfo ftpInfo){
        ftpSPBiz.setFtpSP(context,ftpInfo);
    }

}
