package cjt.androiddisk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import cjt.androiddisk.Model.bean.FtpInfo;
import cjt.androiddisk.Model.bean.WifiInfo;
import cjt.androiddisk.presenter.MainActivityPresenter;
import cjt.androiddisk.view.IMainActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMainActivity{

    private RadioButton switchBtn,configBtn;
    private TextView closedHintTV,ipAddressTV,wifiSIDTV;
    private LinearLayout openHintLL;
    private ImageView wifiIconIV;

    private MainActivityPresenter mPresenter;

    private String hostIp=null;

    private enum STATE{OPEN,CLOSED,NO_WIFI}
    private STATE state=STATE.CLOSED;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // APP沉浸式设置
        stateBarSetting();
        setContentView(R.layout.activity_main);
        // 初始化VIEW与MainActivityPresenter
        initView();
        mPresenter = new MainActivityPresenter(this);
        // 申请读写存储卡权限
        verifyPermission();
        // 注册广播，获取WIFI状态变化
        registerBroadcast();
    }


    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    public void stateBarSetting() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void initView() {
        switchBtn = (RadioButton) findViewById(R.id.switchBtn);
            switchBtn.setTypeface(Typeface.createFromAsset(getAssets(),"iconfont/iconfont.ttf"));
            switchBtn.setOnClickListener(this);
        configBtn = (RadioButton)findViewById(R.id.configBtn);
            configBtn.setOnClickListener(this);
        closedHintTV = (TextView)findViewById(R.id.closedHintTV);
        openHintLL = (LinearLayout)findViewById(R.id.openHintLL);
        ipAddressTV = (TextView)findViewById(R.id.ipAddressTV);
        wifiSIDTV = (TextView)findViewById(R.id.wifiSIDTV);
        wifiIconIV = (ImageView)findViewById(R.id.wifiIconIV);
    }

    @Override
    public void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mPresenter.netWorkStateReceiver, filter);
        System.out.println("注册NetWorkState广播");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.switchBtn:
                switch (state){
                    case CLOSED:
                        UIOpenState();
                        startFtpServer();
                        break;
                    case OPEN:
                        UIClosedState();
                        stopFtpServer();
                        break;
                }
                break;
            case R.id.configBtn:
                startActivityForResult(new Intent(this,ConfigActivity.class),0);
                break;
        }
    }


    @Override
    public void verifyPermission() {
        mPresenter.verifyPermission();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ( requestCode==1 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            initFtpConfig();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void initFtpConfig() {
        mPresenter.initFtpSP();
    }

    @Override
    public void startFtpServer() {
        mPresenter.startFtpServer();
    }

    @Override
    public void stopFtpServer() {
        mPresenter.stopFtpServer();
    }

    @Override
    public void netWorkStateChange(WifiInfo wifiInfo) {
        if (wifiInfo.isConnected()){
            wifiSIDTV.setText(wifiInfo.getSID());
            wifiIconIV.setImageResource(R.mipmap.wifi_large_x2);
            switchBtn.setEnabled(true);
            hostIp = wifiInfo.getIpv4();
            // 网络状态改变，停止FTP服务，UI呈关闭状态
            stopFtpServer();
            UIClosedState();
        }
        else{
            wifiSIDTV.setText(R.string.NoWifi);
            wifiIconIV.setImageResource(R.mipmap.no_wifi);
            switchBtn.setEnabled(false);
            // 不处于WIFI的网络环境下，停止FTP服务，UI呈无WIFI状态
            stopFtpServer();
            UINoWifiState();
        }
    }

    @Override
    public void UIClosedState() {
        state = STATE.CLOSED;
        switchBtn.setText(R.string.MainActivityStartBtn);
        configBtn.setEnabled(true);
        closedHintTV.setVisibility(View.VISIBLE);
        openHintLL.setVisibility(View.GONE);
    }

    @Override
    public void UIOpenState() {
        state = STATE.OPEN;
        switchBtn.setText(R.string.MainActivityStopBtn);
        configBtn.setEnabled(false);
        closedHintTV.setVisibility(View.GONE);
        openHintLL.setVisibility(View.VISIBLE);
        ipAddressTV.setText("ftp://"+hostIp+":"+mPresenter.getFtpInfo().getPort());
    }

    @Override
    public void UINoWifiState() {
        state = STATE.NO_WIFI;
        switchBtn.setText(R.string.MainActivityNoWifiBtn);
        configBtn.setEnabled(true);
        closedHintTV.setVisibility(View.VISIBLE);
        openHintLL.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mPresenter.netWorkStateReceiver);
        System.out.println("注销NetWorkState广播");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode==0 && resultCode==RESULT_OK ){
            mPresenter.saveReturnConf((FtpInfo) data.getSerializableExtra("ftpInfo"));
        }

    }
}
