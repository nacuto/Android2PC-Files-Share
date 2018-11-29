package cjt.androiddisk.view;

import cjt.androiddisk.Model.bean.WifiInfo;

/**
 * Created by CJT on 2018/11/26.
 */

public interface IMainActivity {

    void verifyPermission();

    void startFtpServer();

    void stopFtpServer();

    void netWorkStateChange(WifiInfo wifiInfo);

    void closedState();

    void openState();

    void noWifiState();

}
