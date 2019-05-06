package cjt.androiddisk.view;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import cjt.androiddisk.R;

/**
 * Created by CJT on 2019/3/16.
 */

public class MyLVAdapter extends BaseAdapter {
    Context context;
    List<File> list;

    public MyLVAdapter(Context context, List<File> list){
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH viewHolder;
        if ( view==null ){
            view = View.inflate(context, R.layout.item_listview,null);
            viewHolder = new VH(view);
            view.setTag(viewHolder);
        }else viewHolder = (VH) view.getTag();

        // 设置数据
        File file = (File) getItem(i);
        String fileName = file.getName();
        if(file.isDirectory())
            viewHolder.imageView.setImageResource(R.mipmap.folder);
        else if (fileName.endsWith(".jpg")||fileName.endsWith(".png")||fileName.endsWith(".gif"))
            viewHolder.imageView.setImageResource(R.mipmap.picture);
        else if (fileName.endsWith(".txt")||fileName.endsWith(".log"))
            viewHolder.imageView.setImageResource(R.mipmap.text);
        else if (fileName.endsWith(".xlsx")||fileName.endsWith(".xls")||fileName.endsWith(".csv"))
            viewHolder.imageView.setImageResource(R.mipmap.excel);
        else if (fileName.endsWith(".doc")||fileName.endsWith(".docx"))
            viewHolder.imageView.setImageResource(R.mipmap.word);
        else if (fileName.endsWith(".ppt")||fileName.endsWith(".pptx"))
            viewHolder.imageView.setImageResource(R.mipmap.powerpoint);
        else if (fileName.endsWith(".pdf"))
            viewHolder.imageView.setImageResource(R.mipmap.pdf);
        else if (fileName.endsWith(".rar")||fileName.endsWith(".zip"))
            viewHolder.imageView.setImageResource(R.mipmap.zip);
        else viewHolder.imageView.setImageResource(R.mipmap.unknown);
        viewHolder.name.setText(fileName);

        return view;
    }

    public class VH{
        ImageView imageView;
        TextView name;

        public VH(View view){
            imageView = view.findViewById(R.id.imageView);
            name = view.findViewById(R.id.fileNameTV);
        }
    }
}
