package com.uic.happyfit.data;

import com.parse.ParseInstallation;

import android.R.integer;
import android.util.Log;

public class WeightCalculator {
	
	String LOGTAG = "LOGTAG";
	 
	
	public WeightCalculator(){
		
	} 
	
	/*public int getGender(){
		
	}
	private boolean checkValidHeight(){
		
	}*/
	public double get_TER( String gender, int bodyTypeCode,  String height  ){
		Log.i(LOGTAG, "---------------not below");
		double baseWeight =0;
		double baseHeight = 5.0;  
		double resultKilograms = 0.0;
		//Check if male or female
		//Default of male gender
		if(gender.equals("female")){
			baseWeight = 106;
		}
		else{
			baseWeight = 112;
		}  
		Log.i(LOGTAG,"Weight = " + baseWeight);
		// 1) Get height base of 5.0 feet
		 
	
		String height_String = height+"";
		int feet = Integer.parseInt( height_String.charAt(0)+"" ) ;

		String temp ="";
		double inch;
		
		for(int i = 2; i< height_String.length(); i++){
			temp += height_String.charAt(i);
		}
		inch = Double.parseDouble(temp);
		Log.i(LOGTAG, "inch is" + inch +" feet is :"+feet +"char at" + height_String.charAt(0) );
			Log.i(LOGTAG,  ""+ ((feet * 12) + inch) ) ;
			Log.i(LOGTAG,  ""+ ((5    * 12) + inch) );
			resultKilograms =  ( ((feet * 12) + inch)) -  ((5 * 12) + 0); 
			 
 			resultKilograms *= 4;
			 Log.i(LOGTAG,"5.0 % "+height +" = " + resultKilograms);
				
			 resultKilograms += baseWeight;  
			 Log.i(LOGTAG,"result kilo + baseWeight"+ baseWeight  + "= " + resultKilograms);
	 
		// 2) Get body type
		
		switch(bodyTypeCode){

		case DataConstants.CODE_SEDETARY:  
			resultKilograms *= 30;
			break;
		case DataConstants.CODE_BEDREST:
			resultKilograms *= 22.75;
			break;
		case DataConstants.CODE_LIGHT:
			resultKilograms *= 35;
			break;
		case DataConstants.CODE_MODERATE:
			resultKilograms *= 40;
			break;
		case DataConstants.CODE_ACTIVE:
			resultKilograms *= 45;
			break;
		}

		Log.i(LOGTAG, "result of "+ bodyTypeCode +" = "+ resultKilograms);
		
		// 3) if female 
		resultKilograms /= 2.2;

		Log.i(LOGTAG, "divided by 2.2  = "+ resultKilograms);

		Log.i(LOGTAG, "result returned is   = "+ (resultKilograms ) );
		
		
		return Math.round(resultKilograms/100)*100;
		
//		return resultKilograms;

	}
	
	public double get_BMI( double kilo, String height  ){ 		
		double BMI = 0.0;
		
		String height_String = height+"";
		int feet = Integer.parseInt( height_String.charAt(0)+"" ) ;

		String temp ="";
		double inch;
		
		for(int i = 2; i< height_String.length(); i++){
			temp += height_String.charAt(i);
		}
		inch = Double.parseDouble(temp);
	 	double inchToMeter = ((feet * 12) + inch)/39.37 ;
 	 	Log.i(LOGTAG, "----------"+inchToMeter);
	 	
	 	//  ACTUAL WEIGHT
	 	// -------------
	 	// HEIGHT(39)HEIGHT
	 	BMI = kilo/ (inchToMeter*inchToMeter);
	 	
	 	Log.i("LOGTAG", BMI+"");
	 	return BMI;
	}
	
	
	public double get_TER_BELOW( String gender, int bodyTypeCode,  double height  ){
		Log.i(LOGTAG, "---------------below base");
		
		double baseWeight =0.0;  
		double resultKilograms = 0.0;
		//Check if male or female
		//Default of male gender
		if(gender.equals("female")){
			baseWeight = 106;
		}
		else{
			baseWeight = 112;
		} 
		
		String height_String = height+"";
		int feet = Integer.parseInt( height_String.charAt(0)+"" ) ;

		String temp ="";
		double inch;
		
		for(int i = 2; i< height_String.length(); i++){
			temp += height_String.charAt(i);
		}
		Log.i(LOGTAG, "\n The Extracted feet is "+ feet +" \n The eXtracted Inch is "+ temp );
		
		inch = Integer.parseInt(temp);
			Log.i(LOGTAG,  ""+ ((feet * 12) + inch) ) ;
			Log.i(LOGTAG,  ""+ ((4    * 12) + inch ) );
			//resultKilograms =  (( ((feet * 12) + inch)) -  ((4 * 12) + 0) ) ; 
			resultKilograms =  12 - inch;
 			resultKilograms *= 4;	
 			
 			baseWeight = baseWeight - resultKilograms;
			 Log.i(LOGTAG," result -----------------"+ resultKilograms );
			  resultKilograms =  baseWeight;

			  
			 Log.i(LOGTAG, baseWeight +" result ---------------------------100 should be "+ resultKilograms );
			 
		
		//------------------------------------------------------------------------
		
		
		
		
		switch(bodyTypeCode){

		case DataConstants.CODE_SEDETARY:  
			resultKilograms *= 30;
			break;
		case DataConstants.CODE_BEDREST:
			resultKilograms *= 22.75;
			break;
		case DataConstants.CODE_LIGHT:
			resultKilograms *= 35;
			break;
		case DataConstants.CODE_MODERATE:
			resultKilograms *= 40;
			break;
		case DataConstants.CODE_ACTIVE:
			resultKilograms *= 45;
			break;
		}

		Log.i(LOGTAG, "result of "+ bodyTypeCode +" = "+ resultKilograms);
		
		// 3) if female 
		resultKilograms /= 2.2;

		Log.i(LOGTAG, "divided by 2.2  = "+ resultKilograms);

		Log.i(LOGTAG, "result returned is   = "+ (resultKilograms ) );
		return Math.round(resultKilograms/100)*100;
//		return resultKilograms;

	}
	
	public double get_DBW(String gender,  String height  ){
		 double baseWeight =0;
		 double resultKilograms = 0.0;
		 if(gender.equals("female")){
			baseWeight = 106;
		}
		else{
			baseWeight = 112;
		} 
		 
		String height_String = height+"";
		int feet = Integer.parseInt( height_String.charAt(0)+"" ) ;

		String temp ="";
		double inch;
		
		for(int i = 2; i< height_String.length(); i++){
			temp += height_String.charAt(i);
		}
		inch = Double.parseDouble(temp);
	 		resultKilograms =  ( ((feet * 12) + inch)) -  ((5 * 12) + 0); 
			 
	 		resultKilograms *= 4;
	 			
			 resultKilograms += baseWeight;  
	 	return resultKilograms /2.2;
	}
	
	public String get_STATUS( double BMI){
		if( BMI <= 18.5 ){
			// PAYAT
			return "Under weight";
		}else if(BMI >=18.6 && BMI <= 24.9  ){
			// normal
			return "Normal";
		}else if( BMI >=25 && BMI <= 29.9){
			// OVERWEIGHT
			return "Overweight";
		}else if( BMI >= 30 ){
			//obese
			return "Obese";
		}
		else
			return "no status";
	}
	
}
