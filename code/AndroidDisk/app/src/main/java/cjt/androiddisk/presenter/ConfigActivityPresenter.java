package cjt.androiddisk.presenter;

import android.content.Context;

import java.io.File;
import java.util.List;

import cjt.androiddisk.Model.bean.FtpInfo;
import cjt.androiddisk.Model.biz.FolderShowBiz;
import cjt.androiddisk.Model.biz.FtpSPBiz;
import cjt.androiddisk.view.IConfigActivity;

/**
 * Created by CJT on 2019/3/11.
 */

public class ConfigActivityPresenter {

    private IConfigActivity iConfigActivity;
    private FtpSPBiz ftpSPBiz=new FtpSPBiz();
    private FolderShowBiz folderShowBiz = new FolderShowBiz();


    public ConfigActivityPresenter(IConfigActivity iConfigActivity){
        this.iConfigActivity = iConfigActivity;
    }

    public FtpInfo getFtpInfo(){
        return ftpSPBiz.getFtpInfo(iConfigActivity.getContext());
    }

    public List<File> getFileList(String dir){
        return folderShowBiz.getAllFiles(dir);
    }

    public boolean dirIsEmpty(String dir){
        return folderShowBiz.isEmpty(dir);
    }

}
