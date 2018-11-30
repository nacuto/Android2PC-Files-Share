package cjt.androiddisk.Model.biz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import cjt.androiddisk.presenter.MainActivityPresenter;


/**
 * Created by CJT on 2018/11/26.
 */

public class NetWorkStateReceiver extends BroadcastReceiver{
    private MainActivityPresenter mPresenter;

    public NetWorkStateReceiver(MainActivityPresenter mPresenter){
        this.mPresenter = mPresenter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取wifi连接的信息
        NetworkInfo wifiConnInf = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        // 获取所连接wifi的信息
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (wifiConnInf.isConnected()) System.out.println("连接上"+wifiMgr.getConnectionInfo().getSSID());
        else System.out.println("断开wifi");

        mPresenter.onNetWorkStateChange(wifiConnInf,wifiMgr);
    }
}
