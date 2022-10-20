package com.example.mywork;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_add extends AppCompatActivity {
    private MyDAO myDAO;
    private ListView listView;
    private List<Entity> Data;
    private Entity Item;
    private MyAdapter myAdapter;
    private MyAdapter_2 myAdapter_2;
    private Button button;
    private String[] grade;
    private String[] info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        myDAO = new MyDAO(this);
        Intent intent = getIntent();
        init_Listview();

        button = (Button) findViewById(R.id.btn_all);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                customDialog();
            }
        });
        Button bt_sureADD = findViewById(R.id.btn_sureADD);
        bt_sureADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info = new String[myDAO.getInfoname().length];
                List<Entity> list = myAdapter_2.getData();
                int idx = 0;
                for (Entity entity : list) {
                    String temp = entity.getValue();
                    info[idx++] = temp;
                }
                if(myDAO.queryStudent(info[0]))
                    Toast.makeText(activity_add.this, "已存在学号与之相同的学生！！", Toast.LENGTH_SHORT).show();
                else {
                    myDAO.addStudent(info, grade);
                    Toast.makeText(activity_add.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void init_Listview() {
        Data = new ArrayList<Entity>();
        String[] C = myDAO.getInfoname();
        for (int i = 0; i < C.length; i++) {
            Entity entity = new Entity();
            entity.setKey(C[i]);
            entity.setValue("");
            Data.add(entity);
        }
        listView = (ListView) findViewById(R.id.listView_info);
        myAdapter_2 = new MyAdapter_2(activity_add.this, Data);
        listView.setAdapter(myAdapter_2);
    }



    private void customDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity_add.this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(activity_add.this, R.layout.custom_2, null);
        dialog.setView(dialogView);


        Data = new ArrayList<Entity>();
        String[] C = myDAO.getCoursename();
        for (int i = 0; i < C.length; i++) {
            Entity entity = new Entity();
            entity.setKey(C[i]);
            entity.setValue("");
            Data.add(entity);


            listView = (ListView) dialogView.findViewById(R.id.lv_grade);
            myAdapter = new MyAdapter(activity_add.this, Data);
            listView.setAdapter(myAdapter);
            Button bt_sure = dialogView.findViewById(R.id.bt_sure);
            Button bt_cancel =dialogView. findViewById(R.id.bt_cancel);
            bt_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    grade = new String[myDAO.getCoursename().length];
                    List<Entity> list = myAdapter.getData();
                    int idx = 0;
                    for (Entity entity : list) {
                        String temp = entity.getValue();
                        if (temp.equals(""))
                            grade[idx++] = "-1";
                        else
                            grade[idx++] = temp;
                    }
                    dialog.dismiss();
                    Toast.makeText(activity_add.this, "成绩信息保存成功!", Toast.LENGTH_SHORT).show();
                }
            });
            bt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Toast.makeText(activity_add.this, "成绩信息未保存", Toast.LENGTH_SHORT).show();
                }
            });


            dialog.show();


        }
    }
}




//通过listview让用户设置选课及分数
         /*listView = findViewById(R.id.listView_course);
         listData = new ArrayList<Map<String, String>>();
         String[] course = myDAO.getCoursename();
         for(int i=0;i<course.length;i++)
         {
             String cname = course[i];
             listItem = new HashMap<String, String>();
             listItem.put("cname",cname);
             listData.add(listItem);
         }
        /* listAdapter = new SimpleAdapter(this,
                 listData,
                 R.layout.list_item_5, //自行创建的列表项布局
                 new String[]{"cname"},
                 new int[]{R.id.item1});*/
        /* myAdapter = new ListViewDemoAdapter(getBaseContext(),listData,R.layout.list_item_5,
                                            new String[]{"cname"},
                                            new int[]{R.id.item1});*/
//listView.setAdapter(myAdapter);
// listView .setAdapter(listAdapter);
        /*String i = "B20030119 汪宇翔 B200301 2020级 男 计算机学院 计算机科学与技术 汉族";
        String sele = "已选 已选 已选 已选 已选 已选 未选 未选 未选 未选 未选 未选 未选 未选 未选 未选";
        String g= "91 94 91 92 95 97 94 95 91 99 -1 -1 -1 88 90 100";
        myDAO.addStudent(i.split(" "),sele.split(" "),g.split(" "));*/