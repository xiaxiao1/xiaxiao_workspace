package com.xiaxiao.listview_editview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Student> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        datas = new ArrayList<>();
        for (int i=0; i<150;i++) {
            datas.add(new Student("hello world " + i));
        }
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);

        Fan f = new Fan();
        f.print(new Student("ccc"));

    }


    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Holder holder;
//            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.edit, null);
                holder = new Holder(convertView);
                convertView.setTag(holder);
//            } else {
//                holder = (Holder)convertView.getTag();
//            }
            int cc=(int)(Math.random()*360);
            holder.rv.runPercent(position*3);
            holder.editText.setText(datas.get(position).getName());
            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

//                    Log.i("xx","s"+s);
                }

                @Override
                public void afterTextChanged(Editable s) {
//                    datas.get(position).setName(s.toString());
//                    datas.set(position, new Student(s.toString()));
                    Log.i("xx","oor: "+datas.get(position).getName()+"   now: "+s.toString());
                }
            });
//            holder.editText.setSelection(datas.get(position).getName().length());
            return convertView;
        }



        class Holder {
            EditText editText;
            RingView2 rv;

            public  Holder(View view) {
                editText = (EditText) view.findViewById(R.id.edit);
                rv = (RingView2) view.findViewById(R.id.rv);
            }
        }
    }
}

