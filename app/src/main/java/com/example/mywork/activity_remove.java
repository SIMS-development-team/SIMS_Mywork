package com.example.mywork;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_remove extends AppCompatActivity {
    private MyDAO myDAO;
    private SQLiteDatabase myDb;
    private ListView listView;
    private List<Map<String, String>> listData;
    private Map<String, String> listItem;
    private SimpleAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        Toast.makeText(this, "sdfsad", Toast.LENGTH_SHORT).show();
        myDAO = new MyDAO(this);
        myDb = myDAO.get_Db();
        Intent intent = getIntent();

        Cursor c = myDb.rawQuery("select * from Saccount ", null);
        c.moveToFirst();
        listView = (ListView) findViewById(R.id.listView_removeList);
        listData = new ArrayList<Map<String, String>>();
        listItem = new HashMap<String, String>();
        listItem.put("id", "学号");
        listItem.put("name", "姓名");
        listData.add(listItem);
        while (!c.isAfterLast()) {
                String Sid = c.getString(0), Sname = c.getString(2);
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
                    customDialog(data.get("id"),i);
                }
            }
        });
    }

    private void customDialog(String id,int index) {                                 //生成“是否确定删除”对话框，并实现删除后listview也动态更新。
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您确定要删除该学生的所有信息吗\n(删除后该学生将完全地从系统中移除)");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               myDAO.deleteStudent(id);
                Toast.makeText(activity_remove.this, "删除成功!", Toast.LENGTH_SHORT).show();
                listData.remove(index);                                             //动态删除listview中的该listitem
                listAdapter.notifyDataSetChanged();
                listView.invalidate();
            }
        });
        builder.setNeutralButton("取消",null);
        builder.show(); //调用show()方法来展示对话框
    }
}