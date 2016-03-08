package com.uic.happyfit.adapters;

import java.util.List;

import android.content.Context;
import android.provider.ContactsContract.Contacts.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.uic.happyfit.R; 
import com.uic.happyfit.data.DataConstants;
public class ItemListProgressReports  extends ArrayAdapter<ParseObject>{
	 
	 
		protected Context mContext;
		protected List<ParseObject> mVoters;
		protected List<String> mMonths;
		protected List<ParseObject> mProgress;
		
		public ItemListProgressReports (Context context, List<ParseObject> progress){
			super(context, R.layout.exercise_list_item, progress);
			mContext = context;
		 	mProgress = progress;
		}
		 
		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			ViewHolder holder;
			
			if(convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.exercise_list_item, null);
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
			holder.titleLabel.setText( "Week : "+ mProgress.get( position ).getInt(DataConstants.COLUMN_ROW_ID)  ); 
			
			double previous =  Double.parseDouble(mProgress.get(position).getString(DataConstants.COLUMN_PROGRESS_BODY_CURRENT));
			double current = Double.parseDouble(mProgress.get(position).getString(DataConstants.COLUMN_PROGRESS_BODY_PREVIOUS));
			double result = current - previous;
			if(result <= 0 ){
				result = result * -1;
				holder.subTitleLabel.setText( "Weight Gained +"+ result +"" );
			}
			else
				
				holder.subTitleLabel.setText( "Weight Reduced +"+ result +"" );
			/*
			if( current > previous ){
				holder.subTitleLabel.setText( "Weight Gained +"+ (previous - current )+"" );
			}else
				holder.subTitleLabel.setText( "Weight Reduced +"+  (previous - current)+"" );*/
			   
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