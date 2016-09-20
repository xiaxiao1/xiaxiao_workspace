package com.example.slidecutlistview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.slidecutlistview.SlideCutListView.RemoveDirection;
import com.example.slidecutlistview.SlideCutListView.RemoveListener;

public class MainActivity extends Activity implements RemoveListener{
	public int  currentIndex=0;
	private SlideCutListView slideCutListView ;
	private MyArrayAdapter<String> adapter;
	private List<String> dataSourceList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		slideCutListView = (SlideCutListView) findViewById(R.id.slideCutListView);
		slideCutListView.setRemoveListener(this);
		
		for(int i=0; i<200; i++){
			dataSourceList.add("xiaxiao " + i);
		}
		
		adapter = new MyArrayAdapter<String>(this, R.layout.listview_item, R.id.list_item, dataSourceList);
		slideCutListView.setAdapter(adapter);
		
		slideCutListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MainActivity.this, dataSourceList.get(position), Toast.LENGTH_SHORT).show();
			}
		});
	}

	
	//����ɾ��֮��Ļص�����
	@Override
	public void removeItem(RemoveDirection direction, int position) {
		currentIndex=position;
		adapter.remove(adapter.getItem(position));

		
		
		switch (direction) {
		case RIGHT:
			Toast.makeText(this, "����ɾ��  "+ position, Toast.LENGTH_SHORT).show();
			break;
		case LEFT:
			Toast.makeText(this, "����ɾ��  "+ position, Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		
	}


	class MyArrayAdapter<T> extends ArrayAdapter<T> {
		public MyArrayAdapter(Context context, int resource, int textViewResourceId, List<T>
				objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v= super.getView(position, convertView, parent);
			slide(position,v);
			return v;
		}
	}

	public void slide(int position,View v) {
		if (position>=currentIndex) {
			Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.up);
			v.startAnimation(animation);
		}

	}

}
