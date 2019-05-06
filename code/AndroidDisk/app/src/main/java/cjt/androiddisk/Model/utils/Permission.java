package cjt.androiddisk.Model.utils;

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

public class Permission {

    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    public  void verifyPermission(Context context){
        // 判断当前系统是否是Android6.0(对应API 23)以及以上
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            // 读写权限
            int checkResult = ActivityCompat.checkSelfPermission(
                    context,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkResult!= PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions((Activity) context,PERMISSIONS_STORAGE,1);
            // GPS权限
            checkResult = ActivityCompat.checkSelfPermission(
                    context,Manifest.permission.ACCESS_COARSE_LOCATION);
            if(checkResult!=PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},2);

        }
    }

}
