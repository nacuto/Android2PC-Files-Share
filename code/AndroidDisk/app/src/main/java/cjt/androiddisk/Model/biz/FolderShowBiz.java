package cjt.androiddisk.Model.biz;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by CJT on 2019/3/17.
 */

public class FolderShowBiz {

    public List<File> getAllFiles(String currDir){
        List<File> list = new ArrayList<>();
        File[] files = new File(currDir).listFiles();
        if(files!=null)
            for(File f:files)
                if(!f.getName().startsWith("."))
                    list.add(f);
        mySort(list);
//        for(File f : list){
//            System.out.println(f.getName());
//        }

        return list;
    }

    private void mySort(List<File> list) {
        Collections.sort(list, new Comparator<File>() {
            @Override
            public int compare(File a, File b) {
                if(a.isDirectory()&&b.isDirectory()||a.isFile()&&b.isFile())
                    return a.getName().toLowerCase().compareTo(b.getName().toLowerCase());
                // 文件夹在前
                return a.isDirectory()? -1:1;
            }
        });
    }

    public boolean isEmpty(String dir){
        File[] files = new File(dir).listFiles();
        for(File f : files)
            if(!f.getName().startsWith("."))
                return false;
        return true;
    }

}
