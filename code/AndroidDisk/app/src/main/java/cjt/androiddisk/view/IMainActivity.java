package cjt.androiddisk.view;

import android.content.Context;

import cjt.androiddisk.Model.bean.WifiInfo;

/**
 * Created by CJT on 2018/11/26.
 */

public interface IMainActivity {

    Context getContext();

    void stateBarSetting();

    void initView();

    void verifyPermission();

    void registerBroadcast();

    void initFtpConfig();

    void startFtpServer();

    void stopFtpServer();

    void netWorkStateChange(WifiInfo wifiInfo);

    void UIClosedState();

    void UIOpenState();

    void UINoWifiState();


}
