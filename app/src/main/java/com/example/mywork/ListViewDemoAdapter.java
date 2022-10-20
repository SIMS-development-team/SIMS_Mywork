package com.example.mywork;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.ListAdapter;

public class ListViewDemoAdapter extends SimpleAdapter {
    private static final String TAG = ListViewDemoAdapter.class.getSimpleName();

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * LayoutInflater
     */
    private LayoutInflater mInflater;

    private List<Map<String,String>> datas;

    /*public ListViewDemoAdapter(Context context,List<Map<String,String>> list){
        this.mContext = context;
        this.datas = list;
        this.mInflater = LayoutInflater.from(context);
    }*/
    public ListViewDemoAdapter(Context context,

                               ArrayList data, int resource,

                               String[] from, int[] to) {

        super(context, data, resource, from, to);

        this.mInflater = LayoutInflater.from(context); // 创建视图容器并设置上下文

        this.mContext=context;
        this.datas = data;
        //listItem=data;

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Map<String, String> getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_5,null);
           // holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.item1 = (TextView)convertView.findViewById(R.id.item1);
            holder.item2 = (Spinner) convertView.findViewById(R.id.item2);
            holder.item3 = (EditText) convertView.findViewById(R.id.item3);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        /*holder.item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"setOnClickListener-->onClick...");
                //回调传递点击的view
                mItemOnClickListener.itemOnClickListener(v);
            }
        });*/
        ViewHolder finalHolder = holder;
        holder.item2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // TODO
                Log.d(TAG,"setOnItemSelectedListener-->onClick...");
                if (pos==0){
                    finalHolder.item3.setVisibility(View.INVISIBLE);
                }
                else if(pos==1){
                    finalHolder.item3.setVisibility(View.VISIBLE);
                }
                else{
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO
            }
        });

        return convertView;
    }

    private ItemOnClickListener mItemOnClickListener;

    public void setmItemOnClickListener(ItemOnClickListener listener){
        Log.d(TAG,"setmItemOnClickListener...");
       // this.mItemOnClickListener = listener;
    }

    public interface ItemOnClickListener{
        /**
         * 传递点击的view
         * @param view
         */
        public void itemOnClickListener(View view);
    }

    public class ViewHolder{
        public TextView item1;
        public Spinner item2;
        public EditText item3;
    }
}