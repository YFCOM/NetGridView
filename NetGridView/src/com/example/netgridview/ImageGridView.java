package com.example.netgridview;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * 九宫格组件
 * 
 * @author YFC
 * 
 */
public class ImageGridView extends GridView {

	/**
	 * GridView的数据适配器
	 */
	private GridAdapter adapter;

	private ArrayList<HashMap<String, Object>> mainMenus;

	/**
	 * 请求队列
	 */
	private RequestQueue mQueue;

	public ImageGridView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 限制九宫格组件的高度
		// Rect frame = new Rect();
		// getWindowVisibleDisplayFrame(frame);
		// int screenHeight = frame.height();
		// int gridViewHeight = (int) (screenHeight*0.3);
		// LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// params.height=gridViewHeight;
		// this.setLayoutParams(params);

		mQueue = Volley.newRequestQueue(getContext());

		/**
		 * 指定一个默认的显示行数
		 */
		setNumColumns(3);
		setSelector(new ColorDrawable(Color.TRANSPARENT));
		this.setOnItemClickListener(new ImageOnItemClickListener());
	}

	/**
	 * 自定GridView义适配器
	 * 
	 * @author YFC
	 * 
	 */
	class GridAdapter extends BaseAdapter {

		/**
		 * 用于载入布局文件的LayoutInflater
		 */
		private LayoutInflater inflater;

		/**
		 * 用于存储Activity传来数据源的ArrayList
		 */
		private ArrayList<HashMap<String, Object>> gridViewList;

		public GridAdapter(ArrayList<HashMap<String, Object>> list,
				Context context) {
			super();

			// 接收数据源
			gridViewList = list;

			// 实例化Layoutinflater对象
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return gridViewList.size();
		}

		@Override
		public Object getItem(int position) {
			return gridViewList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {

				// 载入布局文件
				convertView = inflater.inflate(R.layout.grid_item, null);

				// 实例化一个Holder对象
				viewHolder = new ViewHolder();

				// 得到grid_item布局文件里的文字和图片组件
				viewHolder.text = (TextView) convertView
						.findViewById(R.id.textView);
				viewHolder.image = (ImageView) convertView
						.findViewById(R.id.imageView);

				// 设置Tag
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// 对Holder里的两个组件设置数据
			// viewHolder.text.setText((String) gridViewList.get(position).get(
			// "caption"));
			// Bitmap bm =
			// BitmapFactory.decodeFile((String)gridViewList.get(position).get("menuIcon"));
			// viewHolder.image.setImageBitmap(bm);

			// viewHolder.image.setImageResource((Integer)
			// gridViewList.get(position).get("image"));
			// viewHolder.text.setText((String)gridViewList.get(position).get("text"));

			// viewHolder.image.setImageResource((Integer) gridViewList.get(
			// position).get("images"));

			if (gridViewList.get(position).get("images") != null) {
				requestBitmapVollery(
						(String) gridViewList.get(position).get("images"),
						viewHolder.image);

			} else {
				viewHolder.image.setImageResource(R.drawable.ic_launcher);
				System.out.println("图片数据URL缺失，请检查");
			}

			if (gridViewList.get(position).get("texts") != null) {
				viewHolder.text.setText((String) gridViewList.get(position)
						.get("texts"));
			} else {
				viewHolder.text.setText("暂无数据");
				System.out.println("文本数据暂无，请检查");

			}

			// 得到显示区域的高用来计算适应比例
			Rect frame = new Rect();
			getWindowVisibleDisplayFrame(frame);
			int screenHeight = frame.height();

			// 计算在当前屏幕下ImageView可以占的大小
			int imageSize = (int) (screenHeight * 0.08);
			// int textViewsize = (int) (screenHeight*0.02);

			// 得到ImageView的params参数
			ViewGroup.LayoutParams params = viewHolder.image.getLayoutParams();

			// 设置宽高
			params.height = imageSize;
			params.width = imageSize;

			// 给ImageView设置参数
			viewHolder.image.setLayoutParams(params);
			// viewHolder.text.setTextSize(textViewsize);

			return convertView;
		}

	}

	public void requestBitmapVollery(String imgUrl, final ImageView imageView) {
		ImageRequest imgRequest = new ImageRequest(imgUrl,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap bitmap) {
						// TODO Auto-generated method stub
						if (bitmap != null) {
							// Matrix matrix = new Matrix();
							// matrix.postScale(0.2f, 0.2f);
							// Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0,
							// bitmap.getWidth(), bitmap.getHeight(), matrix,
							// true);

//							BitmapDrawable dstmp = new BitmapDrawable(bitmap);
//
//							imageView.setImageDrawable(dstmp);
//							Bitmap bitmapss = compressImage(bitmap);
//							Bitmap bitmapss = comp(bitmap);
							imageView.setImageBitmap(bitmap);
							// bitmap.recycle();
						} else {
							imageView.setImageResource(R.drawable.ic_launcher);
						}

					}
				}, 0, 0,
				Config.ARGB_8888, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						System.out.println("请求失败时进行的处理");
					}
				});
		mQueue.add(imgRequest);
	}

	/**
	 * Holder类
	 * 
	 * @author YFC
	 * 
	 */
	class ViewHolder {

		// 每个格子中的文字内容
		public TextView text;

		// 每个格子的图片内容
		public ImageView image;
	}

	/**
	 * 接收数据源
	 * 
	 * @param list
	 */
	public void setGridArrayList(ArrayList<HashMap<String, Object>> list) {

		mainMenus = list;
		// ArrayList<HashMap<String,Object>> mainMenuList = new
		// ArrayList<HashMap<String,Object>>();
		//
		// for(int i = 0;i<mainMenus.size();i++){
		// HashMap<String,Object> mapgrid = new HashMap<String,Object>();
		//
		// String menuIcon=((StubObject)mainMenus.get(i)).getObject(MENUICON,
		// "").toString();
		// menuIcon = ResFileManager.IMAGE_DIR + "/" + menuIcon;
		//
		// File file = new File(menuIcon);
		// if (file.exists()) {
		//
		// mapgrid.put("menuIcon",menuIcon);
		// }else{
		// mapgrid.put("menuIcon","");
		// }
		// String caption=((StubObject)mainMenus.get(i)).getObject(CAPTION,
		// "").toString();
		//
		//
		// mapgrid.put("caption",caption);
		// mainMenuList.add(mapgrid);
		// }
		// adapter = new GridAdapter(mainMenuList, getContext());
		adapter = new GridAdapter(mainMenus, getContext());
		this.setAdapter(adapter);
	}

	class ImageOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Toast.makeText(getContext(), "点击了第" + position + "个条目",
					Toast.LENGTH_SHORT).show();
		}

	}



	/**
	 * 定义一个可以设置列数的函数
	 */
	public void setNumColumn(int num) {
		setNumColumns(num);
	}
}
