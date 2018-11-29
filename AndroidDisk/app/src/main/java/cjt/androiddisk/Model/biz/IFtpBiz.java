package cjt.androiddisk.Model.biz;

/**
 * Created by CJT on 2018/11/26.
 */

public interface IFtpBiz {
    /**
     * 创建服务器配置文件
     */
    void createConfigFile();

    /**
     * 开启FTP服务器
     * @param ip 本机ip
     */
    void startFtpServer(String ip);

    /**
     * 关闭FTP服务器
     */
    void stopFtpServer();
}
