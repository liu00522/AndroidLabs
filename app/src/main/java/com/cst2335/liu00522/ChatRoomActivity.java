package com.cst2335.liu00522;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private MyListAdapter myListAdapter;

    private EditText editText;
    private Button recvBtn;
    private Button sendBtn;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_chat_room);
        listView = findViewById(R.id.listView);

        Message msg = new Message();

        ArrayList<Message> list = new ArrayList<>();
        MyListAdapter myListAdapter = new MyListAdapter(this, list);
        listView.setAdapter(myListAdapter);


        //  alert to be added
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialogBuilder.setTitle(R.string.deleteThis).setMessage(getString(R.string.selectedRow) + position
                        + "\n" + getString(R.string.dbID) + id).
                        setPositiveButton(R.string.yesBtn, (click, arg) -> {
                            list.remove(position);
                            myListAdapter.notifyDataSetChanged();
                        }).setNegativeButton(R.string.noBtn, (click, arg) -> {
                }).create().show();

                return true;
            }
        });


        editText = findViewById(R.id.editText);
        recvBtn = findViewById(R.id.receiveBtn);
        recvBtn.setOnClickListener(click -> {
            String textRecv = editText.getText().toString();
            list.add(new Message(0, textRecv));
            editText.setText("");
            myListAdapter.notifyDataSetChanged();
        });


        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(click -> {
            String textRecv = editText.getText().toString();
            list.add(new Message(1, textRecv));
            editText.setText("");
            myListAdapter.notifyDataSetChanged();

        });

    }
}

