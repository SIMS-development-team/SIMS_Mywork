package com.example.mywork;

import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

    private List<Entity> list;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<Entity> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_5, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.item1);
            viewHolder.spinner = (Spinner) convertView.findViewById(R.id.item2);
            viewHolder.editText = (EditText) convertView.findViewById(R.id.item3);

            viewHolder.editText.setTag(position);
            viewHolder.editText.addTextChangedListener(new MyTextChangeListener(viewHolder));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.editText.setTag(position);
        }
        Entity entity = list.get(position);
        viewHolder.editText.setText(entity.getValue());
        viewHolder.textView.setText(entity.getKey());


        MyAdapter.ViewHolder finalHolder = viewHolder;
        finalHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // TODO
                //Log.d(TAG,"setOnItemSelectedListener-->onClick...");
                if (pos==0){
                    finalHolder.editText.setVisibility(View.INVISIBLE);
                    finalHolder.editText.setText("");
                }
                else if(pos==1){
                    finalHolder.editText.setVisibility(View.VISIBLE);
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

    public class ViewHolder {
        TextView textView;
        EditText editText;
        Spinner spinner;
    }

    private class MyTextChangeListener implements TextWatcher {

        private ViewHolder holder;

        public MyTextChangeListener(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void afterTextChanged(Editable s) {
            int position = (Integer) holder.editText.getTag();
            Entity entity = list.get(position);
            entity.setValue(s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }
    }

    public  List<Entity> getData() {
        return list;
    }

}
