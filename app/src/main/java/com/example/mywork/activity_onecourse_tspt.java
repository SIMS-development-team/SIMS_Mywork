package com.example.mywork;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class activity_onecourse_tspt extends AppCompatActivity {

    private String Coursename;
    private String teacher_coursename;
    private MyDAO myDAO;
    private SQLiteDatabase myDb;
    private ListView listView;
    private List<Map<String,String>> listData;
    private Map<String,String> listItem;
    private SimpleAdapter listAdapter;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onecourse_tspt);
        myDAO = new MyDAO(this);
        myDb = myDAO.get_Db();
        TextView textView_coursename = findViewById(R.id.textView_coursename);

        Intent intent = getIntent();
        Coursename = intent.getStringExtra("Coursename");//获得所要查询的课程名称
        textView_coursename.setText(Coursename);
        teacher_coursename = intent.getStringExtra("t_coursename"); //获得当前老师所教课程，若两个课程名称不相等，则老师没有权限修改成绩。
        //Toast.makeText(getApplicationContext(), Coursename, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, teacher_coursename, Toast.LENGTH_SHORT).show();
        Cursor c= myDb.rawQuery("select * from Stranscripts ", null);
        c.moveToFirst();

        listView = (ListView)findViewById (R.id.listView_transcripts);   //用listView布局来显示成绩
        listData = new ArrayList<Map<String,String>>();
        listItem=new HashMap<String, String>();
        listItem.put("sid","学号");                                 //每列的列名
        listItem.put("sname","姓名");
        listItem.put("score", "成绩");
        listData.add(listItem);

        while(c.isAfterLast() == false)
        {
            String id, name, score;

            id = c.getString(0);
            name = c. getString(1);
            score = Integer.toString(c.getInt(c.getColumnIndex(Coursename)));  //将整型的成绩转换成String
            if(score.equals("-1"))
                score = "未选";
            listItem=new HashMap<String,String  >();
            listItem.put("sid",id);
            listItem.put("sname",name);
            listItem.put("score", score);
            listData.add(listItem);
            c.moveToNext();
        }
        listAdapter = new SimpleAdapter(this,
                listData,
                R.layout.list_item_3, //自行创建的列表项布局
                new String[]{"sid","sname","score"},
                //new String[]{"_cname","_cid"},
                //new int[]{R.id.key,R.id.value});
                new int[]{R.id.item1,R.id.item2,R.id.item3});
        listView.setAdapter(listAdapter);



    }
}