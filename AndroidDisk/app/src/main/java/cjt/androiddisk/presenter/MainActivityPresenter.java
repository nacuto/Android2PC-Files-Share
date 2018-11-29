package cjt.androiddisk.presenter;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.util.Locale;

import cjt.androiddisk.MainActivity;
import cjt.androiddisk.Model.bean.WifiInfo;
import cjt.androiddisk.Model.biz.FtpBiz;
import cjt.androiddisk.Model.biz.PermissionBiz;
import cjt.androiddisk.view.IMainActivity;

/**
 * Created by CJT on 2018/11/26.
 */

public class MainActivityPresenter {
    private Context activity;
    private PermissionBiz permissionBiz;
    private FtpBiz ftpBiz;
    private IMainActivity mainActivity;


    public MainActivityPresenter(MainActivity activity){
        this.activity = activity;
        this.mainActivity = activity;
        permissionBiz = new PermissionBiz(this.activity);
        ftpBiz = new FtpBiz(this.activity);
    }

    public void verifyPermission(){
        permissionBiz.verifyPermission();
    }


    public void startFtpServer(){
        ftpBiz.createConfigFile();
    }

    public void stopFtpServer(){

    }

    public void onNetWorkStateChange(NetworkInfo wifiConnInf, WifiManager wifiMgr){
        WifiInfo wifiInfo = new WifiInfo();
        wifiInfo.setConnected(wifiConnInf.isConnected());
        wifiInfo.setSID(wifiMgr.getConnectionInfo().getSSID());
        int ipInt = wifiMgr.getConnectionInfo().getIpAddress();
        String ipv4 = String.format(
                Locale.getDefault(),
                "%d.%d.%d.%d",
                (ipInt&0xff), (ipInt>>8&0xff), (ipInt>>16&0xff), (ipInt>>24&0xff));
        wifiInfo.setIpv4(ipv4);
        mainActivity.netWorkStateChange(wifiInfo);
    }



}
