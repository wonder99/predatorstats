package com.example.myfirstapp;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button bt_LW1;
	Button bt_C1;
	Button bt_RW1;

	TextView tv_stat1_LW1;
	int stat1_LW1;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_LW1 = (Button) findViewById(R.id.Button_LW1);    
        bt_C1 = (Button) findViewById(R.id.Button_C1);
        bt_RW1 = (Button) findViewById(R.id.Button_RW1);
        tv_stat1_LW1 = (TextView) findViewById(R.id.Stat1_LW1);
        tv_stat1_LW1.setText(Integer.toString(stat1_LW1));
        ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
        bt_LW1.setOnTouchListener(activitySwipeDetector);
        }
    private String addsign(int stat) {
		String result = "";
		if( stat > 0 ) 
			result = "+" + Integer.toString(stat);
		else if (stat < 0)
			result = Integer.toString(stat);
		else
			result = "0";
		return result;
	}
	public class ActivitySwipeDetector implements View.OnTouchListener {

    	static final String logTag = "ActivitySwipeDetector";
    	private Activity activity;
    	static final int MIN_DISTANCE = 100;
    	private float downX, downY, upX, upY;

    	public ActivitySwipeDetector(Activity activity){
    	    this.activity = activity;
    	}

		public void onRightToLeftSwipe(View v){
		    Log.i(logTag, "RightToLeftSwipe!");
		    //if( v.getParent().equals(findViewById(R.id.Layout_LW1)) )
		    int myViewId = (int) v.getId();
		    switch( myViewId ) {
		    case R.id.Button_LW1:
		    	stat1_LW1++; 
		    }
		    
	        tv_stat1_LW1.setText(Integer.toString(stat1_LW1));
		}
		
		public void onLeftToRightSwipe(View v){
		    Log.i(logTag, "LeftToRightSwipe!");
		    stat1_LW1--; 
	        tv_stat1_LW1.setText(Integer.toString(stat1_LW1));
		}
		
		public void onTopToBottomSwipe(){
		    Log.i(logTag, "onTopToBottomSwipe!");
		    //activity.doSomething();
		}
		
		public void onBottomToTopSwipe(){
		    Log.i(logTag, "onBottomToTopSwipe!");
		    //activity.doSomething();
		}

    	public boolean onTouch(View v, MotionEvent event) {
    		switch(event.getAction()){
		        case MotionEvent.ACTION_DOWN: {
		            downX = event.getX();
		            downY = event.getY();
		            return true;
		        }
		        case MotionEvent.ACTION_UP: {
		            upX = event.getX();
		            upY = event.getY();
		
		            float deltaX = downX - upX;
		            float deltaY = downY - upY;
		
		            // swipe horizontal?
		            if(Math.abs(deltaX) > MIN_DISTANCE){
		                // left or right
		                if(deltaX < 0) { this.onLeftToRightSwipe(v); return true; }
		                if(deltaX > 0) { this.onRightToLeftSwipe(v); return true; }
		            }
		            else {
		                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
		                    return false; // We don't consume the event
		            }
		
		            // swipe vertical?
		            if(Math.abs(deltaY) > MIN_DISTANCE){
		                // top or down
		                if(deltaY < 0) { this.onTopToBottomSwipe(); return true; }
		                if(deltaY > 0) { this.onBottomToTopSwipe(); return true; }
		            }
		            else {
		                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
		                    return false; // We don't consume the event
		            }
		
		            return true;
		        }
    		}
	    return false;
    	}
    }
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
