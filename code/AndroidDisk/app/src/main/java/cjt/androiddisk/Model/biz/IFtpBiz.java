package cjt.androiddisk.Model.biz;

import android.content.Context;

import cjt.androiddisk.Model.bean.FtpInfo;
import cjt.androiddisk.Model.bean.WifiInfo;

/**
 * Created by CJT on 2018/11/26.
 */

public interface IFtpBiz {
    /**
     * @param context
     * @param ftpInfo
     */
    void createConfigFile(Context context, FtpInfo ftpInfo);

    /**
     * 开启FTP服务器
     * @param wifiInfo 获得本机ip
     * @param ftpInfo 获得FTP端口号
     */
    void startFtpServer(Context context, WifiInfo wifiInfo, FtpInfo ftpInfo);

    /**
     * 关闭FTP服务器
     */
    void stopFtpServer();
}
