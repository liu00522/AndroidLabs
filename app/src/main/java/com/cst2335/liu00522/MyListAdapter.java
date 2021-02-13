package com.cst2335.liu00522;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class MyListAdapter extends BaseAdapter {
    private List<Message> list;
    private LayoutInflater inflater;

    public MyListAdapter(Context context, List<Message> data) {
        this.list = data;
        inflater = LayoutInflater.from(context);       //获取布局
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {      //如果类型是0
                holder = new ViewHolder();
                //通过LayoutInflater实例化布局
                convertView = inflater.inflate(R.layout.receiverlayout, null);
                //绑定id
                holder.img = (ImageView) convertView.findViewById(R.id.avatar);
                holder.title = (TextView) convertView.findViewById(R.id.receiverText);
            } else {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.senderlayout, null);
                holder.img = (ImageView) convertView.findViewById(R.id.avatar);
                holder.title = (TextView) convertView.findViewById(R.id.senderText);
            }
            convertView.setTag(holder);         //为View设置tag
        } else {
            holder = (ViewHolder) convertView.getTag();      //通过tag找到缓存的布局
        }
        //设置布局中控件要显示的视图
        holder.img.setImageBitmap(list.get(position).getIcon());
        holder.title.setText(list.get(position).getContent());
        return convertView;     //返回一个view
    }


    private class ViewHolder {
        public ImageView img;
        public TextView title;

    }
}
