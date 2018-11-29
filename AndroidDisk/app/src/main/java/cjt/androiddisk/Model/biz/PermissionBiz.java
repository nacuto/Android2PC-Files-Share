package cjt.androiddisk.Model.biz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import cjt.androiddisk.MainActivity;

/**
 * Created by CJT on 2018/11/26.
 */

public class PermissionBiz {

    Context mainActivity;
    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;

    public PermissionBiz(Context mainActivity){
        this.mainActivity = mainActivity;
    }

    public  void verifyPermission(){
        // 判断当前系统是否是Android6.0(对应API 23)以及以上
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int checkResult = ActivityCompat.checkSelfPermission(
                    mainActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkResult!= PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions((Activity) mainActivity,PERMISSIONS_STORAGE,REQUEST_PERMISSION_CODE);
        }
    }

}
