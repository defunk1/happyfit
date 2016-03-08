package com.uic.happyfit;
 
import java.util.HashMap;

import com.uic.happyfit.data.DataConstants;
import com.uic.happyfit.data.UserDataSource;
import com.uic.happyfit.data.WeightCalculator;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
public class ReportsFragment extends Fragment {
	UserDataSource usrDataSource;
	HashMap<String, Object> mUser;
	ImageView imv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_report,
				container, false);
		 
		initialize(rootView); 
		return rootView;
	}

	private void initialize(View skeleton){
	
		
		usrDataSource = new UserDataSource(this.getActivity());
		usrDataSource.open();
		 mUser = usrDataSource.findUser(1); 
		
		TextView label_bbw = (TextView)skeleton.findViewById(R.id.report_label_dbw);
		TextView label_ter = (TextView)skeleton.findViewById(R.id.report_label_ter);
		TextView label_bmi = (TextView)skeleton.findViewById(R.id.report_label_bmi);
		TextView label_status = (TextView)skeleton.findViewById(R.id.report_label_status);
		WeightCalculator wc = new WeightCalculator();
		
		String _gender = mUser.get(DataConstants.COLUMN_USER_GENDER).toString();
		String _height = mUser.get(DataConstants.COLUMN_USER_HEIGHT).toString();
		double ter = 0.0; 
		if(  Double.parseDouble(_height) <= 4.9 ) { 
			     ter = wc.get_TER_BELOW(_gender, getBodyCode(), Double.parseDouble(_height));
		 }
		 else{
			  ter = wc.get_TER(_gender, getBodyCode(), _height); 
		 }
		 double _weight = Double.parseDouble(mUser.get(DataConstants.COLUMN_USER_WEIGHT).toString() );
		 double bbw = wc.get_DBW( _gender, 	_height );
			
		 double bmi = wc.get_BMI( _weight , _height); 
	     double temp = Double.parseDouble( mUser.get(DataConstants.COLUMN_USER_WEIGHT).toString() ) * 2.2;
	    
	     label_bbw.setText(" "+ bbw);
	     label_ter.setText(" "+ ter);
		 label_bmi.setText(" "+ bmi);
		 label_status.setText(" "+ wc.get_STATUS(bmi) ); 
		
		 imv = (ImageView)skeleton.findViewById(R.id.report_image_body);
		 setImage(  _gender, bmi );
		    
	}
	private void setImage( String gender, double bmi ){
		bmi = Math.round(bmi);
		 if( gender.equals("male") ){
			 	if( bmi <= 17 ){
			 		//below 17
			 		imv.setImageResource(R.drawable.m_a);
			 	}else if( bmi >= 18 && bmi <=19 ){
			 		// birahi si b
			 		imv.setImageResource(R.drawable.m_b);
			 	}else if( bmi >= 20 && bmi <= 22){
			 		// birahi si c
			 		imv.setImageResource(R.drawable.m_c);
			 	}else if(bmi >=23 && bmi<=24){
			 		// birahi d
			 		imv.setImageResource(R.drawable.m_d);
			 	}else if( bmi >= 25 && bmi<= 26 ){
			 		imv.setImageResource(R.drawable.m_e);
			 	}else if( bmi >= 27 && bmi <= 29){
			 		imv.setImageResource(R.drawable.m_f);
			 	}else if( bmi >= 30 && bmi <= 37 ){
			 		imv.setImageResource(R.drawable.m_i);
			 	}
			  
		 }
		 if( gender.equals("female") ){
			 if( bmi <= 17 ){
			 		//below 17
			 		imv.setImageResource(R.drawable.f_a);
			 	}else if( bmi >= 18 && bmi <=19 ){
			 		// birahi si b
			 		imv.setImageResource(R.drawable.f_b);
			 	}else if( bmi >= 20 && bmi <= 22){
			 		// birahi si c
			 		imv.setImageResource(R.drawable.f_c);
			 	}else if(bmi >=23 && bmi<=24){
			 		// birahi d
			 		imv.setImageResource(R.drawable.f_d);
			 	}else if( bmi >= 25 && bmi<= 26 ){
			 		imv.setImageResource(R.drawable.f_e);
			 	}else if( bmi >= 27 && bmi <= 29){
			 		imv.setImageResource(R.drawable.f_g);
			 	}else if( bmi >= 30 && bmi <= 60 ){
			 		imv.setImageResource(R.drawable.f_i);
			 	}
			  
		 }
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		usrDataSource.close();
	}
	@Override
	public void onResume() {
		super.onResume();
		 
		 
	}

	private int getBodyCode(){
		String bodyTypeFromDatabase = mUser.get(DataConstants.COLUMN_USER_BODY_TYPE).toString();
		if( bodyTypeFromDatabase.equals(DataConstants.VALUE_USER_ACITIVTY_BEDREST) ){
			return DataConstants.CODE_BEDREST;
		}
		else if( bodyTypeFromDatabase.equals(DataConstants.VALUE_USER_ACITIVTY_SEDETARY) ){
			return DataConstants.CODE_SEDETARY;
		}
		else if( bodyTypeFromDatabase.equals(DataConstants.VALUE_USER_ACTIVITY_LIGHT) ){
			return DataConstants.CODE_LIGHT;
		} 	
		else if( bodyTypeFromDatabase.equals(DataConstants.VALUE_USER_ACTIVITY_MODERATE) ){
			return DataConstants.CODE_MODERATE;
		} 	
		else if( bodyTypeFromDatabase.equals(DataConstants.VALUE_USER_ACTIVITY_Active) ){
			return DataConstants.CODE_ACTIVE;
		} 	
		else return 0;
	}
}








