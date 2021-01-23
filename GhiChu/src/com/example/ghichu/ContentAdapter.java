package com.example.ghichu;

import java.util.List;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContentAdapter extends BaseAdapter {

	private MainActivity context;
	private int layout;
	private List<Content> contentList;
	
	public ContentAdapter(MainActivity context, int layout, List<Content> contentList) {
		super();
		this.context = context;
		this.layout = layout;
		this.contentList = contentList;
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contentList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private class ViewHolder{
		TextView txtContent;
		ImageView ivDelete,ivEdit;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();;
		
		if( convertView == null){
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layout, null);
			holder.txtContent = (TextView) convertView.findViewById(R.id.txtContent);
			holder.ivDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
			holder.ivEdit = (ImageView) convertView.findViewById(R.id.ivEdit);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final Content content = contentList.get(position);
		
		holder.txtContent.setText(content.getContent());
		
		//Bắt sự kiện cho Edit và Delete
		holder.ivEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				context.DialogEdit(content.getId(),content.getContent());
				//Toast.makeText(context, "Sửa"+content.getContent(), Toast.LENGTH_SHORT).show();
			}
		});
		
		holder.ivDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				context.DialogDelete(content.getId(), content.getContent());
			}
		});
		
		return convertView;
	}
}
