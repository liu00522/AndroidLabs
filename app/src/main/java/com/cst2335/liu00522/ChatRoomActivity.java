package com.cst2335.liu00522;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
    Cursor results;
    ArrayList<Message> chatList = new ArrayList<>();
    ContentValues newRowValues = new ContentValues();
    SQLiteDatabase db;
    ArrayList<Message> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        listView = findViewById(R.id.listView);

        Message msg = new Message();

        MyListAdapter myListAdapter = new MyListAdapter(this, list);
        listView.setAdapter(myListAdapter);

        loadDataFromDatabase();

        //  alert to be added
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialogBuilder.setTitle(R.string.deleteThis).setMessage(getString(R.string.selectedRow) + position
                        + "\n" + getString(R.string.dbID) + id).
                        setPositiveButton(R.string.yesBtn, (click, arg) -> {
                            //  selectedMsg
                            Message selectedMsg = list.get(position);
                            //  delete row from database
                            deleteChat(selectedMsg);
                            //  remove from list
                            list.remove(position);
                            myListAdapter.notifyDataSetChanged();
                        }).setNegativeButton(R.string.noBtn, (click, arg) -> {
                }).create().show();


                return true;
            }
        });

        //  receive button
        editText = findViewById(R.id.editText);
        recvBtn = findViewById(R.id.receiveBtn);
        recvBtn.setOnClickListener(click -> {
            String textRecv = editText.getText().toString();

            // put chat string in the CHATS column
            newRowValues.put(MyOpener.COL_CHATS, textRecv);
            newRowValues.put(MyOpener.COL_Type, 0);
            // insert in the database
            long newID = db.insert(MyOpener.TABLE_NAME, null, newRowValues);


            list.add(new Message(0, textRecv, newID));
            editText.setText("");
            myListAdapter.notifyDataSetChanged();
        });

        //  send button
        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(click -> {
            String textRecv = editText.getText().toString();

            // put chat string in the CHATS column
            newRowValues.put(MyOpener.COL_CHATS, textRecv);
            newRowValues.put(MyOpener.COL_Type, 1);

            // insert in the database
            long newID = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            list.add(new Message(1, textRecv, newID));
            editText.setText("");
            myListAdapter.notifyDataSetChanged();

        });

    }

    private void loadDataFromDatabase() {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer
        db = dbOpener.getWritableDatabase();
        //   get all of the columns.
        String[] columns = {MyOpener.COL_ID, MyOpener.COL_CHATS, MyOpener.COL_Type};

        //query all the results from the database:
        results = db.query(false, MyOpener.TABLE_NAME, columns,
                null, null, null, null, null, null);

        printCursor(results, db.getVersion()); //this moves cursor past last row

        //  column index
        int chatColumnIndex = results.getColumnIndex(MyOpener.COL_CHATS);
        int idColumnIndex = results.getColumnIndex(MyOpener.COL_ID);
        int typeColumnIndex = results.getColumnIndex(MyOpener.COL_Type);

        results.moveToPosition((-1));
        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String chat = results.getString(chatColumnIndex);
            long id = results.getLong(idColumnIndex);
            int type = results.getInt(typeColumnIndex);

            //add the new Contact to the array list:
            list.add(new Message(type, chat, id));
        }

    }

    protected void deleteChat(Message msg) {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?",
                new String[]{Long.toString(msg.getId())});
    }

    ;

    public void printCursor(Cursor c, int version) {
        int version1 = db.getVersion();     //  version number
        int rowCount = c.getCount();           //  column count
        int columnCount = c.getColumnCount();
        int chatsColumnIndex = c.getColumnIndex(MyOpener.COL_CHATS);      //    index of chats column
        int typeColumnIndex = c.getColumnIndex(MyOpener.COL_Type);      //    index of type column
        String chatColumnName = c.getColumnName(chatsColumnIndex);        //    name of chatColumn


//        Log.d("name of column is", "printCursor: " + chatColumnName);


        Log.i("Version Number -  ", "Database Version No is : " + version1);
        Log.i("Column Number - ", "Number of columns is : " + columnCount);
        Log.i("Row Number - ", "Number of rows is : n" + rowCount);
        Log.i("Column Index - ", "Chat column index is : " + chatsColumnIndex);


        c.moveToFirst();
        while (!c.isAfterLast()) {

            for (int i = 0; i < c.getCount(); i++) {
                String chatMessage = c.getString(chatsColumnIndex);
                Log.i("msg", "fds" + chatMessage);
                c.moveToNext();
            }
        }
    }

}
