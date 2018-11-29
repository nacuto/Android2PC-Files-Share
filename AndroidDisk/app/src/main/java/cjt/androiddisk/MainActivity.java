package cjt.androiddisk;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cjt.androiddisk.Model.bean.WifiInfo;
import cjt.androiddisk.Model.biz.NetWorkStateReceiver;
import cjt.androiddisk.presenter.MainActivityPresenter;
import cjt.androiddisk.view.IMainActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMainActivity{

    private Button switchBtn,configBtn;
    private TextView closedHintTV,ipAddressTV,wifiSIDTV;
    private LinearLayout openHintLL;
    private ImageView wifiIconIV;

    private NetWorkStateReceiver netWorkStateReceiver;

    private MainActivityPresenter mPresenter;

    private String hostIp=null;

    private enum STATE{OPEN,CLOSED,NO_WIFI}
    private STATE state;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        setContentView(R.layout.activity_main);

        initView();
        verifyPermission();
        registerBroadcast();

    }

    private void initView() {
        switchBtn = (Button)findViewById(R.id.switchBtn);
            switchBtn.setTypeface(Typeface.createFromAsset(getAssets(),"iconfont/iconfont.ttf"));
            switchBtn.setOnClickListener(this);
        configBtn = (Button)findViewById(R.id.configBtn);
            configBtn.setOnClickListener(this);
        closedHintTV = (TextView)findViewById(R.id.closedHintTV);
        openHintLL = (LinearLayout)findViewById(R.id.openHintLL);
        ipAddressTV = (TextView)findViewById(R.id.ipAddressTV);
        wifiSIDTV = (TextView)findViewById(R.id.wifiSIDTV);
        wifiIconIV = (ImageView)findViewById(R.id.wifiIconIV);

        mPresenter = new MainActivityPresenter(this);

        state = STATE.CLOSED;
    }

    private void registerBroadcast() {
        if (netWorkStateReceiver==null)
            netWorkStateReceiver = new NetWorkStateReceiver(mPresenter);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
        System.out.println("注册NetWorkState广播");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.switchBtn:
                switch (state){
                    case CLOSED:
                        mPresenter.startFtpServer();
                        openState();
                        break;
                    case OPEN:
                        mPresenter.stopFtpServer();
                        closedState();
                        break;
                }
                break;
            case R.id.configBtn:
                startActivity(new Intent(this,ConfigActivity.class));
                break;
        }
    }

    @Override
    public void verifyPermission() {
        mPresenter.verifyPermission();
    }

    @Override
    public void startFtpServer() {
        mPresenter.startFtpServer();
    }

    @Override
    public void stopFtpServer() {

    }

    @Override
    public void netWorkStateChange(WifiInfo wifiInfo) {
        if (wifiInfo.isConnected()){
            wifiSIDTV.setText(wifiInfo.getSID());
            wifiIconIV.setImageResource(R.mipmap.wifi_large_x2);
            switchBtn.setEnabled(true);
            hostIp = wifiInfo.getIpv4();
            closedState();
        }
        else{
            wifiSIDTV.setText(R.string.NoWifi);
            wifiIconIV.setImageResource(R.mipmap.no_wifi);
            switchBtn.setEnabled(false);
            noWifiState();
        }
    }

    @Override
    public void closedState() {
        state = STATE.CLOSED;
        switchBtn.setText(R.string.MainActivityStartBtn);
        configBtn.setEnabled(true);
        closedHintTV.setVisibility(View.VISIBLE);
        openHintLL.setVisibility(View.GONE);
    }

    @Override
    public void openState() {
        state = STATE.OPEN;
        switchBtn.setText(R.string.MainActivityStopBtn);
        configBtn.setEnabled(false);
        closedHintTV.setVisibility(View.GONE);
        openHintLL.setVisibility(View.VISIBLE);
        ipAddressTV.setText("ftp://"+hostIp+":8090/");
    }

    @Override
    public void noWifiState() {
        state = STATE.NO_WIFI;
        switchBtn.setText(R.string.MainActivityNoWifiBtn);
        configBtn.setEnabled(true);
        closedHintTV.setVisibility(View.VISIBLE);
        openHintLL.setVisibility(View.GONE);

    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(netWorkStateReceiver);
        System.out.println("注销NetWorkState广播");
        super.onDestroy();
    }
}
