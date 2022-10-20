package com.example.mywork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_Student_transcripts extends AppCompatActivity {

    private MyDAO myDAO;
    private SQLiteDatabase myDb;
    private ListView listView;
    private List<Map<String,String>> listData;
    private Map<String,String> listItem;
    private SimpleAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_transcripts);
        Intent intent  = getIntent();
        myDAO = new MyDAO(this);
        myDb = myDAO.get_Db();

        Cursor c = myDb.rawQuery("select * from Spersonal_info ", null);
        c.moveToFirst();
        listView = (ListView) findViewById(R.id.listView_studetnOption);
        listData = new ArrayList<Map<String,String>>();
        listItem=new HashMap<String,String  >();
        listItem.put("id","学号");
        listItem.put("name","姓名");
        listData.add(listItem);

        while(c.isAfterLast() == false)
        {
            String id, name;
            id = c.getString(0);
            name = c. getString(1);
            listItem=new HashMap<String,String  >();
            listItem.put("id",id);
            listItem.put("name",name);
            listData.add(listItem);
            c.moveToNext();
        }
        listAdapter = new SimpleAdapter(this,
                listData,
                R.layout.list_item, //自行创建的列表项布局
                new String[]{"id","name"},
                //new String[]{"_cname","_cid"},
                //new int[]{R.id.key,R.id.value});
                new int[]{R.id.key,R.id.value});
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>=1) {
                    Map<String, String> data = listData.get(i);
                    String sid = data.get("id");
                    Intent intent = new Intent(getApplicationContext(), activity_Mytranscripts.class);
                    intent.putExtra("S_id", sid);
                    startActivity(intent);
                }
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId())
        {
            case R.id.item_course:
                intent = new Intent(getApplicationContext(),activity_Course_transcripts.class);
                startActivity(intent);
                break;
            case R.id.item_student:
                //intent = new Intent(getApplicationContext(), activity_Student_transcripts.class);
                //startActivity(intent);
                //Toast.makeText(this,"You clicked second item",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;


    }
}