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
public class BodyTypeListItem  extends ArrayAdapter<ParseObject>{
	 
	 
		protected Context mContext; 
		protected List<ParseObject> bodyTypesArray;
		
		public BodyTypeListItem (Context context, List<ParseObject> _bodyType){
			super(context, R.layout.body_type_item_list, _bodyType);
			mContext = context;
			bodyTypesArray = _bodyType;
		}
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			 ViewHolder holder;
			
			if(convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.body_type_item_list, null);
				holder = new ViewHolder();
				 
				holder.titleLabel = (TextView)convertView.findViewById(R.id.bodyType_list_item_title);
				holder.subTitleLabel = (TextView)convertView.findViewById(R.id.bodyType_list_item_subtitle); 
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder)convertView.getTag();
			}
			
		//	ParseObject voter = mVoters.get(position);
			 
			holder.subTitleLabel.setText( "loremn"  );
			holder.titleLabel.setText( "ipsumn" );
			
			return convertView;
		}

		private static class ViewHolder{
			//ImageView iconImageView;
			TextView titleLabel;
			TextView subTitleLabel;
			
		}
		public void refill(List<ParseObject> bodyTypes){
		//	mVoters.clear();
		//	mVoters.addAll(voters);
			bodyTypesArray.clear();
			bodyTypesArray.addAll(bodyTypes);
			notifyDataSetChanged();
		}
		
	}