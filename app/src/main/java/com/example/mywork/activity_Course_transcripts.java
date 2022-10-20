package com.example.mywork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class activity_Course_transcripts extends AppCompatActivity {

    private String Tid;  //老师的工号
    private String Tcourse; //该老师所教授的课程
    private SQLiteDatabase myDb;
    private MyDAO myDAO;

    private ListView listView;
    private List<Map<String,String>> listData;
    private Map<String,String> listItem;
    private SimpleAdapter listAdapter;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_transcripts);
        Intent intent  = getIntent();
        //Tid = intent.getStringExtra("T_id");
        //Toast.makeText(this,"按课程查询成绩",Toast.LENGTH_SHORT).show();
        myDAO = new MyDAO(this);
        myDb = myDAO.get_Db();

       // Cursor c = myDb.rawQuery("select * from Tpersonal_info where 工号=?", new String[]{Tid});
      //  c.moveToFirst();
       // Tcourse = c.getString(c.getColumnIndex("教授课程"));      //根据工号查找Tcourse

        Cursor c= myDb.rawQuery("select * from Course",null);
        c.moveToFirst();
        listView = (ListView)findViewById (R.id.listView_allCourse);   //用listView布局来显示成绩，listItem ,listData的作用去百度
        listData = new ArrayList<Map<String,String>>();
        listItem=new HashMap<String,String  >();
        listItem.put("_cname","课程名称");
        listItem.put("_cid","课程编号");
        listItem.put("_cteacher", "授课老师");
        listData.add(listItem);

        while(c.isAfterLast() == false)
        {
            String course_name, course_id, course_teacher;
            int course_num;
            course_name = c.getString(0);
            course_id = c. getString(1);
            course_teacher = c.getString(2);
            listItem=new HashMap<String,String  >();
            listItem.put("_cname",course_name);
            listItem.put("_cid",course_id);
            listItem.put("_cteacher", course_teacher);
            listData.add(listItem);
            c.moveToNext();
        }
        listAdapter = new SimpleAdapter(this,
                listData,
                R.layout.list_item_3, //自行创建的列表项布局
                new String[]{"_cname","_cid","_cteacher"},
                //new String[]{"_cname","_cid"},
                //new int[]{R.id.key,R.id.value});
                new int[]{R.id.item1,R.id.item2,R.id.item3});
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>=1) {
                    Map<String, String> data = listData.get(i);
                    String course_name = data.get("_cname");
                    Intent intent = new Intent(getApplicationContext(), activity_onecourse_tspt.class);
                    intent.putExtra("Coursename", course_name);
                    intent.putExtra("t_coursename",Tcourse);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
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
                Toast.makeText(this,"按课程查询成绩",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_student:
                intent = new Intent(getApplicationContext(), activity_Student_transcripts.class);
                startActivity(intent);
                //Toast.makeText(this,"You clicked second item",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;


    }
}