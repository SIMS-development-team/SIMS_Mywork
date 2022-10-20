package com.example.mywork;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MyAdapter_2 extends BaseAdapter {
    private List<Entity> list;
    private LayoutInflater inflater;

    public MyAdapter_2(Context context, List<Entity> list) {
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
        MyAdapter_2.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_4, null);
            viewHolder = new MyAdapter_2.ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.key);
            viewHolder.editText = (EditText) convertView.findViewById(R.id.value);

            viewHolder.editText.setTag(position);
            viewHolder.editText.addTextChangedListener(new MyAdapter_2.MyTextChangeListener(viewHolder));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyAdapter_2.ViewHolder) convertView.getTag();
            viewHolder.editText.setTag(position);
        }
        Entity entity = list.get(position);
        viewHolder.editText.setText(entity.getValue());
        viewHolder.textView.setText(entity.getKey());

        return convertView;
    }

    public class ViewHolder {
        TextView textView;
        EditText editText;
    }

    private class MyTextChangeListener implements TextWatcher {

        private MyAdapter_2.ViewHolder holder;

        public MyTextChangeListener(MyAdapter_2.ViewHolder holder) {
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
