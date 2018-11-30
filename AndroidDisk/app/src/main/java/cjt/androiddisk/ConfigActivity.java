package cjt.androiddisk;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by CJT on 2018/11/27.
 */

public class ConfigActivity extends AppCompatActivity implements View.OnClickListener {

//    private Button unSaveBtn,saveBtn;
    private TextView unSaveBtn,saveBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_config);
        initView();

    }

    private void initView() {
        unSaveBtn = (TextView) findViewById(R.id.unSaveBtn);
            unSaveBtn.setTypeface(Typeface.createFromAsset(getAssets(),"iconfont/iconfont.ttf"));
            unSaveBtn.setOnClickListener(this);
        saveBtn = (TextView) findViewById(R.id.saveBtn);
            saveBtn.setTypeface(Typeface.createFromAsset(getAssets(),"iconfont/iconfont.ttf"));
            saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.unSaveBtn:

                finish();
                break;
            case R.id.saveBtn:

                finish();
                break;
        }
    }
}
