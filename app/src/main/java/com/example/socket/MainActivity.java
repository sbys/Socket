package com.example.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editText;
    ListView listView;
    static String str;
    static InputStream is;
    Handler handler;
    ClientThread clientThread;
    ArrayList<HashMap<String ,Object>> listitem=new ArrayList<HashMap<String, Object>>();

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button=(Button)findViewById(R.id.button);
        editText=(EditText)findViewById(R.id.editText);
        listView=(ListView)findViewById(R.id.listview);
            final SimpleAdapter simpleAdapter=new SimpleAdapter(this,  listitem,R.layout.item,new String[]{"Mes"},new int[]{R.id.text});
        handler=new Handler() {

            public void handleMessage(Message mes){
                if (mes.what==0x123){
                    HashMap map=new HashMap();
                    map.put("Mes",mes.obj.toString());
                    listitem.add(map);
                    listView.setAdapter(simpleAdapter);
                }
            }
        };
        clientThread=new ClientThread(handler);
        new Thread(clientThread).start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message mes =new Message();
                mes.what=0x345;
                mes.obj=editText.getText().toString();
                clientThread.revHandler.sendMessage(mes);
                editText.setText("");

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
