package com.cst2335.liu00522;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class MyListAdapter extends BaseAdapter {
    private List<Message> list;
    private LayoutInflater inflater;

    public MyListAdapter(Context context, List<Message> data) {
        this.list = data;
        inflater = LayoutInflater.from(context);
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
            if (getItemViewType(position) == 0) {
                holder = new ViewHolder();
                // inflate layout
                convertView = inflater.inflate(R.layout.receiverlayout, null);
                //  set layout
                holder.textBox = (TextView) convertView.findViewById(R.id.receiverText);
            } else {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.senderlayout, null);
                holder.textBox = (TextView) convertView.findViewById(R.id.senderText);
            }
            convertView.setTag(holder);         // set tag
        } else {
            holder = (ViewHolder) convertView.getTag();      // find tag
        }
        holder.textBox.setText(list.get(position).getContent());
        return convertView;
    }


    private class ViewHolder {
        public TextView textBox;

    }
}
