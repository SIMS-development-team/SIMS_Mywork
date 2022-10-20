package com.example.mywork;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_allStudent extends AppCompatActivity {

    private MyDAO myDAO;

    private ListView listView;
    private List<Map<String, String>> listData;
    private Map<String, String> listItem;
    private SimpleAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student);
        Intent intent = getIntent();
        myDAO = new MyDAO(this);
        initView();

    }
    private void initView()
    {
        Cursor c = myDAO.getAllStudent();
        c.moveToFirst();
        listView = (ListView) findViewById(R.id.listView_studentlist);
        listData = new ArrayList<Map<String, String>>();
        listItem = new HashMap<String, String>();
        listItem.put("id", "学号");
        listItem.put("name", "姓名");
        listData.add(listItem);
        while (!c.isAfterLast()) {
            String Sid = c.getString(0), Sname = c.getString(1);
            listItem = new HashMap<String, String>();
            listItem.put("id", Sid);
            listItem.put("name", Sname);
            listData.add(listItem);


            c.moveToNext();
        }
        listAdapter = new SimpleAdapter(this,
                listData,
                R.layout.list_item, //自行创建的列表项布局
                new String[]{"id", "name"},
                new int[]{R.id.key, R.id.value});
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>=1) {
                    Map<String, String> data = listData.get(i);
                    String sid = data.get("id");
                    Intent intent = new Intent(getApplicationContext(), activity_modify.class);
                    intent.putExtra("id", sid);
                    startActivity(intent);
                }
            }
        });
    }
}