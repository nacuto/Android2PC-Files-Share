package cjt.androiddisk.view;

import android.content.Context;
import android.widget.EditText;

/**
 * Created by CJT on 2019/3/11.
 */

public interface IConfigActivity {

    Context getContext();

    void stateBarSetting();

    void bindId();

    void initView();

    void unSave();

    void save();

    void clickAnonymous();

    void clickUserPass();

    void clickPort();

    void clickWritePermission();

    void clickMaxLogin();

    void clickTransRate();

    void clickHomeDir();

    void userPassDialog(boolean invalid);

    void normalDialog(String title, int data, String info, boolean invalid);

    void homeDirDialog();

    void showKeyboard(EditText editText);



}
