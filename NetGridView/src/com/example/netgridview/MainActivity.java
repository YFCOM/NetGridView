package com.example.netgridview;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.widget.GridView;

import com.android.volley.RequestQueue;

public class MainActivity extends Activity {

	/**
	 * 模拟数据源里的url
	 */
	private String[] imageUrls = {
			"http://img.my.csdn.net/uploads/201403/03/1393854094_4652.jpg",
			"http://img.my.csdn.net/uploads/201403/03/1393854084_6138.jpg",
			"http://img.my.csdn.net/uploads/201403/03/1393854084_1323.jpg",
			"http://img.my.csdn.net/uploads/201403/03/1393854084_8439.jpg",
//			"http://img.my.csdn.net/uploads/201403/03/1393854083_6511.jpg",
			"http://www.iconpng.com/png/seopack-full/wifi55.png",
			"http://img.my.csdn.net/uploads/201403/03/1393854083_2323.jpg" };

	/**
	 * 模拟数据源里的文字
	 */
	private String[] texts = { "photo1", "photo2", "photo3", "photo4",
			"photo5", "photo6" };

	/**
	 * 请求队列
	 */
	private RequestQueue mQueue;

	/**
	 * 组件组成部分
	 */
	private GridView gridView;
	
	/**
	 * 组件声明
	 */
	private ImageGridView imageGridView;
	
	/**
	 * 装载数据源的ArrayList,还没有进行封装和数据源改造
	 */
	private ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	
	private MainActivity mainActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageGridView = (ImageGridView) findViewById(R.id.imageGridView);
		
		HashMap<String,Object> map0 = new HashMap<String, Object>();
		map0.put("images", imageUrls[0]);
		map0.put("texts", texts[0]);
		list.add(map0);
		
		HashMap<String,Object> map1 = new HashMap<String, Object>();
		map1.put("images", imageUrls[1]);
		map1.put("texts", texts[1]);
		list.add(map1);
		
		HashMap<String,Object> map2 = new HashMap<String, Object>();
		map2.put("images", imageUrls[2]);
		map2.put("texts", texts[2]);
		list.add(map2);
		
		HashMap<String,Object> map3 = new HashMap<String, Object>();
		map3.put("images", imageUrls[3]);
		map3.put("texts", texts[3]);
		list.add(map3);
		
		HashMap<String,Object> map4 = new HashMap<String, Object>();
		map4.put("images", imageUrls[4]);
		map4.put("texts", texts[4]);
		list.add(map4);
		
		HashMap<String,Object> map5 = new HashMap<String, Object>();
		map5.put("images", imageUrls[5]);
		map5.put("texts", texts[5]);
		list.add(map5);
		
		HashMap<String,Object> map6 = new HashMap<String, Object>();
		list.add(map6);
		
		//20160629
		imageGridView.setGridArrayList(list);
		
	}

}
