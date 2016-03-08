package com.uic.happyfit;
 
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;
import com.github.mikephil.charting.utils.YLabels.YLabelPosition;
import com.parse.ParseObject;
import com.uic.happyfit.adapters.ItemList;
import com.uic.happyfit.adapters.ItemListExercise;
import com.uic.happyfit.adapters.ItemListProgressReports;
import com.uic.happyfit.data.ProgressDataSource;
public class StatisticsFragment extends  ListFragment implements OnChartValueSelectedListener  {
	
	protected String [] candidates = new String[] { "1st", "2nd", "3rd", "4th" ,"5th"};
	
	 
		private BarChart uiChart;
		List<String> months;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_statistics,
				container, false);
		
		months = new ArrayList<String>();
		months.add("month 6");
		months.add("month 5");
		months.add("month 4");
		months.add("month 3");
		months.add("month 2");
		months.add("month 1");
		
		ProgressDataSource pDataSource = new ProgressDataSource(getActivity());
		pDataSource.open();
		List<ParseObject> mList = pDataSource.getAll();
		
		ArrayAdapter<ParseObject> adapter = new ItemListProgressReports(getActivity(), mList);
		
	//	ArrayAdapter<String> adapter = new ItemList(getActivity(), months);
		setListAdapter(adapter);
		
		initializeGraph( rootView );
		return rootView;
	}
	
	 

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id); 
			setData(5,1000); 
			
	}



	private void initializeGraph(View _rootView) { 
		uiChart = (BarChart) _rootView.findViewById(R.id.chart1);
		// enable the drawing of values
		uiChart.setDrawYValues(true);
		uiChart.setDrawValueAboveBar(true);
		uiChart.setDescription("");
		// if more than 10 entries are displayed in the chart, no values will be
		// drawn
		uiChart.setMaxVisibleValueCount(10);
		// disable 3D
		uiChart.set3DEnabled(false);
		// scaling can now only be done on x- and y-axis separately
		uiChart.setPinchZoom(false);
		// draw shadows for each bar that show the maximum value
		// mChart.setDrawBarShadow(true);
		uiChart.setUnit("");
		
		//------------------------------------
		
		uiChart.setLongClickable(false);
		
		//------------------------------------
		
		// mChart.setDrawXLabels(false);
		uiChart.setDrawGridBackground(false);
		uiChart.setDrawHorizontalGrid(true);
		uiChart.setDrawVerticalGrid(false);
		// mChart.setDrawYLabels(false);
		// sets the text size of the values inside the chart
		uiChart.setValueTextSize(10f);
		uiChart.setDrawBorder(false);
		// mChart.setBorderPositions(new BorderPosition[] {BorderPosition.LEFT,
		// BorderPosition.RIGHT});
		/*Typeface tf = Typeface.createFromAsset(getAssets(),
				"OpenSans-Regular.ttf");*/
		XLabels xl = uiChart.getXLabels();
		xl.setPosition(XLabelPosition.BOTTOM);
		xl.setCenterXLabelText(true);
		//xl.setTypeface(tf);
		YLabels yl = uiChart.getYLabels();
		//yl.setTypeface(tf);
		yl.setLabelCount(8);
		yl.setPosition(YLabelPosition.BOTH_SIDED);
		//mChart.setValueTypeface(tf); 
		// setting data
	
		setDataInit(5,1000); 
	
		Legend l = uiChart.getLegend();
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		l.setFormSize(8f);
		l.setXEntrySpace(4f);
		// mChart.setDrawLegend(false);
	}

	@Override
	public void onResume() {
		super.onResume();
  	}

	private void setDataInit(int count, float range){
		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			xVals.add(candidates[i % 12]);
		}  
		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
  
		yVals1.add(new BarEntry( (float) ((Math.random() * 100) + 40)	, 0));
		yVals1.add(new BarEntry((float) ((Math.random() * 100) + 40), 1));
		yVals1.add(new BarEntry((float) ((Math.random() * 100) + 40), 2));
		yVals1.add(new BarEntry((float) ((Math.random() * 100) + 40), 3));
		yVals1.add(new BarEntry((float) ((Math.random() * 100) + 40), 4));
		
		
		
		BarDataSet set1 = new BarDataSet(yVals1, "Weeks of the month"); 
		 
		set1.setBarSpacePercent(35f);
		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(set1); 
		BarData data = new BarData(xVals, dataSets);
		uiChart.setData(data); 
	}
	
	private void setData(int count, float range) {
		Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show(); 
		ArrayList<String> xVals = new ArrayList<String>();		
		for (int i = 0; i < count; i++) {
			xVals.add(candidates[i % 12]);
		} 
		  
		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		yVals1.add(new BarEntry( (float) ((Math.random() * 100) + 40)	, 0));
		yVals1.add(new BarEntry((float) ((Math.random() * 100) + 40), 1));
		yVals1.add(new BarEntry((float) ((Math.random() * 100) + 40), 2));
		yVals1.add(new BarEntry((float) ((Math.random() * 100) + 40), 3));
		yVals1.add(new BarEntry((float) ((Math.random() * 100) + 40), 4));
		
		BarDataSet set1 = new BarDataSet(yVals1, "Weeks of the month"); 
		set1.setBarSpacePercent(35f);
		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(set1);
		BarData data = new BarData(xVals, dataSets);
		uiChart.setData(data);
	 
		uiChart.invalidate();
	}
	
	@SuppressLint("NewApi") 
	@Override
	public void onValueSelected(Entry e, int dataSetIndex) {
		if (e == null)
			return;
		RectF bounds = uiChart.getBarBounds((BarEntry) e);
		PointF position = uiChart.getPosition(e);
		
		//Log.i("LogTag", bounds +" "+position.toString() );
		//Log.i("LogTag" , e.toString()  );
		//Log.i("LogTag", " "+dataSetIndex );
		
		/*toaster(" "+ candidates[dataSetIndex] );
		Log.i("bounds", ""+dataSetIndex );
		Log.i("position", position.toString());*/
	}
	
	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub 
	}  
	     
}

