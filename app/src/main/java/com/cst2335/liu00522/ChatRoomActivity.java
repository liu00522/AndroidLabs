package com.cst2335.liu00522;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private MyListAdapter myListAdapter;
    //    private ArrayList<String> elementsRev = new ArrayList<>();
    private ArrayList<String> elementsRecv = new ArrayList<>();
    //    private ArrayList<String> elementsSend = new ArrayList<>();
    private ArrayList<String> display = new ArrayList<>();
    private EditText editText;
    private Button recvBtn;
    private Button sendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*
        elements.add("test1");
        elements.add("test2");
        elements.add("test3");
        elements.add("test4");*/

        setContentView(R.layout.activity_chat_room);

        myListAdapter = new MyListAdapter();
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(myListAdapter);


        editText = findViewById(R.id.editText);
        recvBtn = findViewById(R.id.receiveBtn);
        recvBtn.setOnClickListener(click -> {
            String textRecv = editText.getText().toString();
            editText.setText(null);
            elementsRecv.add(textRecv);
            myListAdapter.notifyDataSetChanged();
        });

        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(click -> {
            String textSend = editText.getText().toString();
            editText.setText(null);
            elementsRecv.add(textSend);
            myListAdapter.notifyDataSetChanged();

        });

    }


    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return elementsRecv.size();
        }

        @Override
        public Object getItem(int position) {
            return "this is row :" + position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inf = getLayoutInflater();


            View leftRow = inf.inflate(R.layout.row_layout1, parent, false);

            TextView leftRowText = leftRow.findViewById(R.id.textGoesHere);
            leftRowText.setText(elementsRecv.get(position));
            return leftRow;

        }
    }
}