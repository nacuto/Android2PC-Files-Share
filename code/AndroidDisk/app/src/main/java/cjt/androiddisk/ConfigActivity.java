package cjt.androiddisk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.SelectDialog;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cjt.androiddisk.Model.bean.FtpInfo;
import cjt.androiddisk.presenter.ConfigActivityPresenter;
import cjt.androiddisk.view.IConfigActivity;
import cjt.androiddisk.view.MyLVAdapter;

/**
 * Created by CJT on 2018/11/27.
 */

public class ConfigActivity extends AppCompatActivity implements IConfigActivity, View.OnClickListener {

    private ConfigActivity me;

    private TextView unSaveBtn,saveBtn,iconTV1,iconTV2,iconTV3,iconTV4,iconTV5,
                    userPasswordTV,portTV,maxLoginNumTV, transRateTV,homeDirTV;
    private ConstraintLayout anonymousCL,writePermissionCL,userPasswordCL,
                            portCL,maxLoginNumCL,transRateCL,homeDirCL;
    private Switch isAnonymousSw,writePermissionSw;

    private FtpInfo ftpInfo;
    private int port,maxLoginNum,transRate;
    private String user,password,homeDir;

    private List<File> list = new ArrayList<>();
    MyLVAdapter adapter;
    private String currDir;
    private static final String SDCard = Environment.getExternalStorageDirectory().getAbsolutePath();


    private ConfigActivityPresenter mPresenter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏
        stateBarSetting();
        setContentView(R.layout.activity_config);
        // 设置对话框
        DialogSettings.style = DialogSettings.STYLE_IOS ;
        // 初始化变量
        me = this;
        mPresenter = new ConfigActivityPresenter(this);
        ftpInfo = mPresenter.getFtpInfo();
            port = ftpInfo.getPort();
            maxLoginNum = ftpInfo.getMaxLoginNum();
            transRate = ftpInfo.getTransRate();
            user = ftpInfo.getUser();
            password = ftpInfo.getPassword();
            homeDir = ftpInfo.getHomeDir();
        adapter = new MyLVAdapter(me,list);
        initView();
    }

    @Override
    public void stateBarSetting() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void initView() {
        bindId();
        // 设置sw状态
        isAnonymousSw.setChecked(ftpInfo.isAnonymous());
        userPasswordCL.setClickable(!ftpInfo.isAnonymous());
        userPasswordTV.setTextColor(ftpInfo.isAnonymous()?0xff989898:Color.BLACK);
        writePermissionSw.setChecked(ftpInfo.isWritePermission());
        // 设置提示信息
        portTV.setText("当前端口: "+port);
        maxLoginNumTV.setText("当前最大登录数: "+maxLoginNum);
        transRateTV.setText("当前传输速率: "+transRate/1000+"KB/s");
        homeDirTV.setText("当前主目录: "+dealDir(homeDir));
    }

    @Override
    public void bindId() {
        Typeface tf = Typeface.createFromAsset(getAssets(),"iconfont/iconfont.ttf");
        unSaveBtn = (TextView) findViewById(R.id.unSaveBtn);
            unSaveBtn.setTypeface(tf);
            unSaveBtn.setOnClickListener(this);
        saveBtn = (TextView) findViewById(R.id.saveBtn);
            saveBtn.setTypeface(tf);
            saveBtn.setOnClickListener(this);
        iconTV1 = (TextView) findViewById(R.id.iconTV1);
            iconTV1.setTypeface(tf);
        iconTV2 = (TextView) findViewById(R.id.iconTV2);
            iconTV2.setTypeface(tf);
        iconTV3 = (TextView) findViewById(R.id.iconTV3);
            iconTV3.setTypeface(tf);
        iconTV4 = (TextView) findViewById(R.id.iconTV4);
            iconTV4.setTypeface(tf);
        iconTV5 = (TextView) findViewById(R.id.iconTV5);
            iconTV5.setTypeface(tf);
        userPasswordTV = (TextView) findViewById(R.id.userPasswordTV);
        portTV = (TextView) findViewById(R.id.portTV);
        maxLoginNumTV = (TextView) findViewById(R.id.maxLoginNumTV);
        transRateTV = (TextView) findViewById(R.id.transRateTV);
        homeDirTV = (TextView) findViewById(R.id.homeDirTV);
        anonymousCL = (ConstraintLayout) findViewById(R.id.anonymousCL);
            anonymousCL.setOnClickListener(this);
        userPasswordCL = (ConstraintLayout) findViewById(R.id.userPasswordCL);
            userPasswordCL.setOnClickListener(this);
        portCL = (ConstraintLayout) findViewById(R.id.portCL);
            portCL.setOnClickListener(this);
        writePermissionCL = (ConstraintLayout) findViewById(R.id.writePermissionCL);
            writePermissionCL.setOnClickListener(this);
        maxLoginNumCL = (ConstraintLayout) findViewById(R.id.maxLoginNumCL);
            maxLoginNumCL.setOnClickListener(this);
        transRateCL = (ConstraintLayout) findViewById(R.id.transRateCL);
            transRateCL.setOnClickListener(this);
        homeDirCL = (ConstraintLayout) findViewById(R.id.homeDirCL);
            homeDirCL.setOnClickListener(this);
        isAnonymousSw = (Switch) findViewById(R.id.isAnonymousSw);
            isAnonymousSw.setOnClickListener(this);
        writePermissionSw = (Switch) findViewById(R.id.writePermissionSw);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.unSaveBtn:
                unSave();
                break;
            case R.id.saveBtn:
                save();
                break;
            case R.id.isAnonymousSw:
                clickAnonymous();
                break;
            case R.id.anonymousCL:
                isAnonymousSw.setChecked(!isAnonymousSw.isChecked());
                clickAnonymous();
                break;
            case R.id.userPasswordCL:
                clickUserPass();
                break;
            case R.id.portCL:
                clickPort();
                break;
            case R.id.writePermissionCL:
                clickWritePermission();
                break;
            case R.id.maxLoginNumCL:
                clickMaxLogin();
                break;
            case R.id.transRateCL:
                clickTransRate();
                break;
            case R.id.homeDirCL:
                clickHomeDir();
                break;
        }
    }

    @Override
    public void clickAnonymous() {
        userPasswordCL.setClickable(!isAnonymousSw.isChecked());
        userPasswordTV.setTextColor(!isAnonymousSw.isChecked()?Color.BLACK:0xff989898);
        if(!isAnonymousSw.isChecked()&&user.equals(""))
            userPassDialog(false);
    }

    @Override
    public void clickUserPass() {
        userPassDialog(false);
    }

    @Override
    public void clickPort() {
        normalDialog("端口号", port, "端口范围：1025 - 65534",false);
    }

    @Override
    public void clickWritePermission() {
        writePermissionSw.setChecked(!writePermissionSw.isChecked());
    }

    @Override
    public void clickMaxLogin() {
        normalDialog("最大登录数",maxLoginNum,"",false);
    }

    @Override
    public void clickTransRate() {
        normalDialog("传输速率",transRate/1000,"传输速率范围：0 - 3500 (KB/s)",false);
    }

    @Override
    public void clickHomeDir() {
        homeDirDialog();
    }

    @Override
    public void unSave() {
        finish();
    }

    @Override
    public void save() {
        // 保存设置内容
        FtpInfo ftpInfo = new FtpInfo();
        ftpInfo.setAnonymous(isAnonymousSw.isChecked());
        ftpInfo.setUser(user);
        ftpInfo.setPassword(password);
        ftpInfo.setPort(port);
        ftpInfo.setWritePermission(writePermissionSw.isChecked());
        ftpInfo.setMaxLoginNum(maxLoginNum);
        ftpInfo.setTransRate(transRate);
        ftpInfo.setHomeDir(homeDir);
        // 回传参数
        Intent intent = getIntent();
        intent.putExtra("ftpInfo",ftpInfo);
        setResult(RESULT_OK,intent);
        finish();
    }


    @Override
    public void userPassDialog(boolean invalid) {
        //初始化布局：
        View customView = LayoutInflater.from(me).inflate(R.layout.user_pass_dialog, null);
        final EditText userET = customView.findViewById(R.id.userET);
        userET.setText(user);
        final EditText passET = customView.findViewById(R.id.passET);
        passET.setText(password);
        TextView infoTV = customView.findViewById(R.id.infoTV);
        if(invalid) infoTV.setTextColor(0xFFFF4081);
        //启动对话框
        SelectDialog.show(me, "用户名与密码", null, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String user_ = String.valueOf(userET.getText());
                String password_ = String.valueOf(passET.getText());
                if ( !( user_.length()>=4&&user_.length()<=16 &&
                        password_.length()>=4&&password_.length()<=16 ) ){
                    userPassDialog(true);
                    return;
                }
                user = user_;
                password = password_;
            }
        }).setCustomView(customView).setCanCancel(true);
        // 显示软键盘
        showKeyboard(userET);
    }

    @Override
    public void normalDialog(final String title, final int data, final String info, boolean invalid) {
        //初始化布局：
        View customView = LayoutInflater.from(me).inflate(R.layout.one_input_dialog, null);
        final EditText editText = customView.findViewById(R.id.editText);
        editText.setText(String.valueOf(data));
        TextView infoTV = customView.findViewById(R.id.info);
        infoTV.setText(info);
        if(info.equals("")) infoTV.setVisibility(View.GONE);
        if(invalid) infoTV.setTextColor(0xFFFF4081);
        //启动对话框
        SelectDialog.show(me, title, null, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(String.valueOf(editText.getText()).equals("")){
                    normalDialog(title,data,info,true);
                    return;
                }
                int input = Integer.parseInt(String.valueOf(editText.getText()));
                switch (title){
                    case "端口号":
                        if(!(input>=1025&&input<=65534)){
                            normalDialog(title,data,info,true);
                            return;
                        }
                        else{
                            port = input;
                            portTV.setText(String.valueOf(input));
                        }
                        break;
                    case "最大登录数":
                        maxLoginNum = input;
                        break;
                    case "传输速率":
                        if(!(input>=0&&input<=3500)){
                            normalDialog(title,data,info,true);
                            return;
                        }
                        else transRate = input*1000;

                        break;
                }
            }
        }).setCustomView(customView).setCanCancel(true);
        // 显示软键盘
        showKeyboard(editText);
    }

    @Override
    public void homeDirDialog() {
        currDir = homeDir;
        View customView = View.inflate(me,R.layout.home_dir_dialog,null);
        final TextView upTV = customView.findViewById(R.id.upTV);
        upTV.setTypeface(Typeface.createFromAsset(getAssets(),"iconfont/up.ttf"));
        final TextView dirTV = customView.findViewById(R.id.dirTV);
        dirTV.setText(dealDir(currDir));
        final TextView emptyInfoTV = customView.findViewById(R.id.emptyInfoTV);
        final ListView listView = customView.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        updateListView(currDir,listView,emptyInfoTV);
        // ListView Item 监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                File file = list.get(i);
                if(file.isDirectory()){
                    // 下一个目录
                    currDir = file.getAbsolutePath().toString();
                    dirTV.setText(dealDir(currDir));
                    updateListView(currDir,listView,emptyInfoTV);
//                    System.out.println(dirTV.getText());
                }
            }
        });
        // upTV 点击监听
        upTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currDir = new File(currDir).getParent();
                dirTV.setText(dealDir(currDir));
                updateListView(currDir,listView,emptyInfoTV);
            }
        });
        // 启动对话框
        SelectDialog.show(me, "设置根目录", null, "选择", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                homeDir = currDir;
                homeDirTV.setText(dealDir(homeDir));
            }
        }).setCustomView(customView).setCanCancel(true);

//        System.out.println(SDCard);
    }

    private void updateListView(String dir, ListView listView, TextView emptyInfoTV) {
        list.clear();
        list.addAll(mPresenter.getFileList(dir));
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        if(mPresenter.dirIsEmpty(dir)){
            listView.setVisibility(View.GONE);
            emptyInfoTV.setVisibility(View.VISIBLE);
//            System.out.println("目录为空");
        }else{
            listView.setVisibility(View.VISIBLE);
            emptyInfoTV.setVisibility(View.GONE);
        }
    }

    private String dealDir(String dir){
        return dir.replaceFirst(SDCard,"/sdcard");
    }

    @Override
    public void showKeyboard(final EditText editText) {
        //设置可获得焦点
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        //请求获得焦点
        editText.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(editText!=null){
                    //调用系统输入法
                    InputMethodManager inputManager = (InputMethodManager) editText
                            .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(editText, 0);
                }
            }
        }, 300);

    }


}
