package com.uic.happyfit.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject; 
import com.uic.happyfit.R;
public class ItemList extends ArrayAdapter<String>{
 
 
	protected Context mContext;
	protected List<ParseObject> mVoters;
	protected List<String> mMonths;
	
	public ItemList (Context context, List<String> months){
		super(context, R.layout.unsynced_list_item, months);
		mContext = context;
		mMonths = months;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent){

		 ViewHolder holder;
		
		if(convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.unsynced_list_item, null);
			holder = new ViewHolder();
			holder.iconImageView = (ImageView)convertView.findViewById(R.id.unsynced_list_item_icon);
			holder.titleLabel = (TextView)convertView.findViewById(R.id.unsynced_list_item_title);
			holder.subTitleLabel = (TextView)convertView.findViewById(R.id.unsynced_list_item_subtitle); 
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		
	//	ParseObject voter = mVoters.get(position);
		 
		holder.subTitleLabel.setText(  "Reduced : (-"+ (float)(Math.random() * 40)  +") kilos"  );
		holder.titleLabel.setText( mMonths.get(position) );
		
		return convertView;
	}

	private static class ViewHolder{
		ImageView iconImageView;
		TextView titleLabel;
		TextView subTitleLabel;
		
	}
	public void refill(List<String> months){
	//	mVoters.clear();
	//	mVoters.addAll(voters);
		mMonths.clear();
		mMonths.addAll(months);
		notifyDataSetChanged();
	}
	
}