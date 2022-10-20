package com.example.mywork;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class activity_modify extends AppCompatActivity {

    private MyDAO myDAO;
    private ListView listView;
    private List<Entity> Data;
    private MyAdapter myAdapter;
    private MyAdapter_2 myAdapter_2;
    private Button button;
    private String[] grade;
    private String[] info;
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        init();
        init_Listview();
        addListener();
    }

    private void init_Listview() {
        Data = new ArrayList<Entity>();
        String[] C = myDAO.getInfoname();
        Cursor c = myDAO.getStudentInfoById(sid);
        c.moveToFirst();
        for (int i = 0; i < C.length; i++) {
            Entity entity = new Entity();
            entity.setKey(C[i]);
            entity.setValue(c.getString(i));
            Data.add(entity);
        }
        listView = (ListView) findViewById(R.id.lv_info);
        myAdapter_2 = new MyAdapter_2(activity_modify.this, Data);
        listView.setAdapter(myAdapter_2);
    }



    private void customDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity_modify.this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(activity_modify.this, R.layout.custom_2, null);
        dialog.setView(dialogView);


        Data = new ArrayList<Entity>();
        String[] C = myDAO.getCoursename();
        String[] G = myDAO.getTranscriptsById(sid);
        for (int i = 0; i < C.length; i++) {
            Entity entity = new Entity();
            entity.setKey(C[i]);
            entity.setValue(G[i]);
            Data.add(entity);


            listView = (ListView) dialogView.findViewById(R.id.lv_grade);
            myAdapter = new MyAdapter(activity_modify.this, Data);
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
                    Toast.makeText(activity_modify.this, "成绩信息保存成功!", Toast.LENGTH_SHORT).show();
                }
            });
            bt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Toast.makeText(activity_modify.this, "成绩信息未保存", Toast.LENGTH_SHORT).show();
                }
            });


            dialog.show();


        }
    }
    private void init()
    {
        myDAO = new MyDAO(this);
        Intent intent = getIntent();
        sid = intent.getStringExtra("id");
        grade = new String[myDAO.getCoursename().length];
        for(int i=0;i<grade.length;i++)
            grade[i] = "-1";
    }
    private void addListener()
    {
        button = (Button) findViewById(R.id.btn_modifyGrade);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                customDialog();
            }
        });
        Button bt_sureADD = findViewById(R.id.btn_sureModify);
        bt_sureADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info = new String[myDAO.getInfoname().length];
                List<Entity> list = myAdapter_2.getData();
                int idx = 0;
                for (Entity entity : list) {
                    String temp = entity.getValue();
                    if(temp.equals(""))
                        temp = "未知";
                    info[idx++] = temp;
                }

                    myDAO.deleteStudent(sid);       //现将该学生删除
                    myDAO.addStudent(info, grade);  //再将填好的信息新建一个学生实现修改
                    Toast.makeText(activity_modify.this, "修改成功", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
