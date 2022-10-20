package com.example.mywork;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDAO {
    private SQLiteDatabase myDb;  //类的成员
    private DBHelper dbHelper;  //类的成员

    public MyDAO(Context context) {  //构造方法，参数为上下文对象
        //第1参数为上下文，第2参数为数据库名
        dbHelper = new DBHelper(context, "info_System.db", null, 26);
        myDb = dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase get_Db() {
        return myDb;
    }

    public void deleteStudent(String sid) {
        String where1 = "id=" + sid;
        String where2 = "学号=" + sid;
        myDb.execSQL("delete from Saccount where id=?", new String[]{sid});
        myDb.execSQL("delete from Stranscripts where 学号=?", new String[]{sid});


        //更新该学生已选课程的选课人数
        Cursor c = myDb.rawQuery("select * from Smy_course where 学号=?", new String[]{sid});
        c.moveToFirst();
        int cnt = c.getColumnCount();
        for (int i = 2; i < cnt; i++) {
            if (c.getString(i).equals("已选")) {
                String course = c.getColumnName(i);
                Cursor cur = myDb.rawQuery("select * from Course where 课程名称=?", new String[]{course});
                cur.moveToFirst();
                @SuppressLint("Range") int num = cur.getInt(cur.getColumnIndex("选课人数"));
                int latest_num = num - 1;
                myDb.execSQL("update Course set 选课人数=? where 课程名称=?", new Object[]{latest_num, course});
            }
        }
        myDb.execSQL("delete from Smy_course where 学号=?", new String[]{sid});
        myDb.execSQL("delete from Spersonal_info where 学号=?", new String[]{sid});
        //myDb.delete(DBHelper.TB_NAME_4,where2,null);
        //myDb.delete(DBHelper.TB_NAME_3,where2,null);
    }

    public Cursor queryCourse() {    //查询所有记录
        return myDb.rawQuery("select * from Course", null);
    }

    @SuppressLint("Range")
    //返回所有课程的名称
    public String[] getCoursename() {
        Cursor c = queryCourse();
        String[] res = new String[c.getCount()];
        int idx = 0;
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            //res[idx++] = c.getString(c.getColumnIndex("课程名称"));
            String tmp = c.getString(0);
            res[idx++] = tmp;
            c.moveToNext();
        }
        return res;
    }

    //返回个人信息的名称，即学号，姓名，学院，专业等：
    public String[] getInfoname() {
        Cursor c = myDb.rawQuery("select * from Spersonal_info", null);
        int num = c.getColumnCount();
        String[] res = new String[num];
        int idx = 0;
        for (int i = 0; i < num; i++)
            res[idx++] = c.getColumnName(i);
        return res;
    }

    //返回数据库中是否已经存在学号为id的学号
    public boolean queryStudent(String id) {
        Cursor c = myDb.rawQuery("select * from Spersonal_info where 学号=?", new String[]{id});
        if (c.getCount() == 0)
            return false;
        else
            return true;
    }

    public void addStudent(String[] info, String[] grade) {
        String[] course_selected = new String[grade.length];
        for (int i = 0; i < course_selected.length; i++)
            if (grade[i].equals("-1"))
                course_selected[i] = "未选";
            else
                course_selected[i] = "已选";
        ContentValues values = new ContentValues();
        String[] course = getCoursename();
        //新建学生的账号密码
        values.put("id", info[0]);
        values.put("password", "6666");
        values.put("name", info[1]);  //添加学号，密码，姓名，默认密码为6666；
        myDb.insert("Saccount", null, values);

        //新建学生的个人信息表
        values = new ContentValues();
        values.put("学号", info[0]);
        values.put("姓名", info[1]);
        values.put("班级", info[2]);
        values.put("年级", info[3]);
        values.put("性别", info[4]);
        values.put("学院", info[5]);
        values.put("专业", info[6]);
        values.put("民族", info[7]);
        myDb.insert("Spersonal_info", null, values);

        //新建学生的选课表
        values = new ContentValues();
        values.put("学号", info[0]);
        values.put("姓名", info[1]);
        for (int i = 0; i < course.length; i++) {
            values.put(course[i], course_selected[i]);
            //同步更新已选课程的选课人数：
            if (course_selected[i].equals("已选")) {
                Cursor cur = myDb.rawQuery("select * from Course where 课程名称=?", new String[]{course[i]});
                cur.moveToFirst();
                @SuppressLint("Range") int num = cur.getInt(cur.getColumnIndex("选课人数"));
                int latest_num = num + 1;
                // @SuppressLint("Range") String num = cur.getString(cur.getColumnIndex("选课人数"));
                // int latest_num = Integer.toString(Integer.parseInt(num)+1);
                myDb.execSQL("update Course set 选课人数=? where 课程名称=?", new Object[]{latest_num, course[i]});
            }
        }
        myDb.insert("Smy_course", null, values);

        //新建学生成绩表
        values = new ContentValues();
        values.put("学号", info[0]);
        values.put("姓名", info[1]);
        for (int i = 0; i < course.length; i++) {
            if (course_selected[i].equals("已选")) {
                int Grade = Integer.parseInt(grade[i]);
                values.put(course[i], Grade);
            } else
                values.put(course[i], -1);
        }
        myDb.insert("Stranscripts", null, values);

    }

    public Cursor getAllStudent() {
        return myDb.rawQuery("select * from Spersonal_info", null);
    }

    public Cursor getStudentInfoById(String id) {
        return myDb.rawQuery("select * from Spersonal_info where 学号=?", new String[]{id});
    }

    @SuppressLint("Range")
    public String[] getTranscriptsById(String id) {
        String[] course = getCoursename();
        Cursor c = myDb.rawQuery("select * from Stranscripts where 学号=?", new String[]{id});
        c.moveToFirst();
        String[] res = new String[course.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = c.getString(c.getColumnIndex(course[i]));
        }
        return res;
    }

}
