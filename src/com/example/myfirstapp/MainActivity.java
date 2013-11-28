package com.example.myfirstapp;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	Button bt_Row1;
	Button bt_LW1;
	Button bt_C1;
	Button bt_RW1;

	TextView tv_stat1_LW1;
	int stat1_LW1;
	TextView tv_stat1_C1;
	int stat1_C1;
	TextView tv_stat1_RW1;
	int stat1_RW1;
	
	TextView tv_stat2_LW1;
	int stat2_LW1;
	TextView tv_stat2_C1;
	int stat2_C1;
	TextView tv_stat2_RW1;
	int stat2_RW1;
	
	ToggleButton tb_edit_mode;
	boolean editMode;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        PlayerSwipeDetector playerSwipeDetector = new PlayerSwipeDetector(this);
        RowSwipeDetector rowSwipeDetector = new RowSwipeDetector(this);
        
        bt_Row1 = (Button) findViewById(R.id.Button_Row1);
        bt_Row1.setOnTouchListener(rowSwipeDetector);
        
        bt_LW1 = (Button) findViewById(R.id.Button_LW1);    
        tv_stat1_LW1 = (TextView) findViewById(R.id.Stat1_LW1);
        tv_stat1_LW1.setText(Integer.toString(stat1_LW1));
        bt_LW1.setOnTouchListener(playerSwipeDetector);
        
        bt_C1 = (Button) findViewById(R.id.Button_C1);
        tv_stat1_C1 = (TextView) findViewById(R.id.Stat1_C1);
        tv_stat1_C1.setText(Integer.toString(stat1_C1));
        bt_C1.setOnTouchListener(playerSwipeDetector);
    
        bt_RW1 = (Button) findViewById(R.id.Button_RW1);
        tv_stat1_RW1 = (TextView) findViewById(R.id.Stat1_RW1);
        tv_stat1_RW1.setText(Integer.toString(stat1_RW1));
        bt_RW1.setOnTouchListener(playerSwipeDetector);
        
        bt_LW1 = (Button) findViewById(R.id.Button_LW1);    
        tv_stat2_LW1 = (TextView) findViewById(R.id.Stat2_LW1);
        tv_stat2_LW1.setText(Integer.toString(stat2_LW1));
        bt_LW1.setOnTouchListener(playerSwipeDetector);
        
        bt_C1 = (Button) findViewById(R.id.Button_C1);
        tv_stat2_C1 = (TextView) findViewById(R.id.Stat2_C1);
        tv_stat2_C1.setText(Integer.toString(stat2_C1));
        bt_C1.setOnTouchListener(playerSwipeDetector);
    
        bt_RW1 = (Button) findViewById(R.id.Button_RW1);
        tv_stat2_RW1 = (TextView) findViewById(R.id.Stat2_RW1);
        tv_stat2_RW1.setText(Integer.toString(stat2_RW1));
        bt_RW1.setOnTouchListener(playerSwipeDetector);
        
        tb_edit_mode = (ToggleButton) findViewById(R.id.toggleButton1);
        editMode = false;
        tb_edit_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                	editMode = true;
                	tv_stat2_RW1.setText("true");
                } else {
                	// The toggle is disabled
                	editMode = false;
                	tv_stat2_RW1.setText("false");
                }
            }
        });
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
	public class PlayerSwipeDetector implements View.OnTouchListener {

    	static final String logTag = "PlayerSwipeDetector";
    	private Activity activity;
    	static final int MIN_DISTANCE = 50;
    	private float downX, downY, upX, upY;

    	public PlayerSwipeDetector(Activity activity){
    	    this.activity = activity;
    	}

		public void onRightToLeftSwipe(View v){
		    Log.i(logTag, "RightToLeftSwipe! of ");
		    switch( v.getId() ) {
			    case R.id.Button_LW1:
			    	stat1_LW1--; 
			    	tv_stat1_LW1.setText(Integer.toString(stat1_LW1));
			    	break;
			    case R.id.Button_C1:
			    	stat1_C1--; 
			    	tv_stat1_C1.setText(Integer.toString(stat1_C1));
			    	break;
			    case R.id.Button_RW1:
			    	stat1_RW1--;
			    	tv_stat1_RW1.setText(Integer.toString(stat1_RW1));
			    	break;
		    }
		}
		
		public void onLeftToRightSwipe(View v){
		    Log.i(logTag, "LeftToRightSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_LW1:
			    	stat1_LW1++; 
			    	tv_stat1_LW1.setText(Integer.toString(stat1_LW1));
			    	break;
			    case R.id.Button_C1:
			    	stat1_C1++; 
			    	tv_stat1_C1.setText(Integer.toString(stat1_C1));
			    	break;
			    case R.id.Button_RW1:
			    	stat1_RW1++;
			    	tv_stat1_RW1.setText(Integer.toString(stat1_RW1));
			    	break;
		    }
		}
		
		public void onTopToBottomSwipe(View v){
		    Log.i(logTag, "onTopToBottomSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_LW1:
			    	stat2_LW1--; 
			    	tv_stat2_LW1.setText(Integer.toString(stat2_LW1));
			    	break;
			    case R.id.Button_C1:
			    	stat2_C1--; 
			    	tv_stat2_C1.setText(Integer.toString(stat2_C1));
			    	break;
			    case R.id.Button_RW1:
			    	stat2_RW1--;
			    	tv_stat2_RW1.setText(Integer.toString(stat2_RW1));
			    	break;
		    }
		}
		
		public void onBottomToTopSwipe(View v){
		    Log.i(logTag, "onBottomToTopSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_LW1:
			    	stat2_LW1++; 
			    	tv_stat2_LW1.setText(Integer.toString(stat2_LW1));
			    	break;
			    case R.id.Button_C1:
			    	stat2_C1++; 
			    	tv_stat2_C1.setText(Integer.toString(stat2_C1));
			    	break;
			    case R.id.Button_RW1:
			    	stat2_RW1++;
			    	tv_stat2_RW1.setText(Integer.toString(stat2_RW1));
			    	break;
		    }
		}

    	public boolean onTouch(View v, MotionEvent event) {
    		switch(event.getAction()){
		        case MotionEvent.ACTION_DOWN: {
		            downX = event.getX();
		            downY = event.getY();
		            v.setBackgroundResource(R.drawable.button_90_45_highlight);
		            return true;
		        }
		        case MotionEvent.ACTION_UP: {
		            upX = event.getX();
		            upY = event.getY();
		            v.setBackgroundResource(R.drawable.button_90_45);
		            float deltaX = downX - upX;
		            float deltaY = downY - upY;
		            	
                    Log.i(logTag, "Swipe was " + Math.abs(deltaX) + " wide and " + Math.abs(deltaY) + "long.  " );
                    Log.i(logTag, "X1=" + downX + ", Y1=" + downY + ", X2=" + upX + ", Y2=" + upY );
                    

		            // swipe horizontal?
		            if( (Math.abs(deltaX) > Math.abs(deltaY)) & (Math.abs(deltaX) > MIN_DISTANCE) ){
		                // left or right
		                if(deltaX < 0) { this.onLeftToRightSwipe(v); return true; }
		                if(deltaX > 0) { this.onRightToLeftSwipe(v); return true; }
		            }
		            else if (Math.abs(deltaY) > MIN_DISTANCE) {
			                if(deltaY < 0) { this.onTopToBottomSwipe(v); return true; }
			                if(deltaY > 0) { this.onBottomToTopSwipe(v); return true; }
			                else {
		                    return false; // We don't consume the event
			                } 
			        }
		
		            return true;
		        }
    		}
	    return false;
    	}
    }

	public class RowSwipeDetector implements View.OnTouchListener {

    	static final String logTag = "RowSwipeDetector";
    	private Activity activity;
    	static final int MIN_DISTANCE = 50;
    	private float downX, downY, upX, upY;

    	public RowSwipeDetector(Activity activity){
    	    this.activity = activity;
    	}

		public void onRightToLeftSwipe(View v){
		    Log.i(logTag, "RightToLeftSwipe! of ");
		    switch( v.getId() ) {
			    case R.id.Button_Row1:
			    	stat1_LW1--; 
			    	stat1_C1--; 
			    	stat1_RW1--; 
			    	tv_stat1_LW1.setText(Integer.toString(stat1_LW1));
			    	tv_stat1_C1.setText(Integer.toString(stat1_C1));
			    	tv_stat1_RW1.setText(Integer.toString(stat1_RW1));
			    	break;
		    }
		}
		
		public void onLeftToRightSwipe(View v){
		    Log.i(logTag, "LeftToRightSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_Row1:
			    	stat1_LW1++; 
			    	stat1_C1++; 
			    	stat1_RW1++; 
			    	tv_stat1_LW1.setText(Integer.toString(stat1_LW1));
			    	tv_stat1_C1.setText(Integer.toString(stat1_C1));
			    	tv_stat1_RW1.setText(Integer.toString(stat1_RW1));
			    	break;
		    }
		}
		
		public void onTopToBottomSwipe(View v){
		    Log.i(logTag, "onTopToBottomSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_Row1:
			    	stat2_LW1--; 
			    	stat2_C1--; 
			    	stat2_RW1--; 
			    	tv_stat2_LW1.setText(Integer.toString(stat2_LW1));
			    	tv_stat2_C1.setText(Integer.toString(stat2_C1));
			    	tv_stat2_RW1.setText(Integer.toString(stat2_RW1));
			    	break;
		    }
		}
		
		public void onBottomToTopSwipe(View v){
		    Log.i(logTag, "onBottomToTopSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_Row1:
			    	stat2_LW1++; 
			    	stat2_C1++; 
			    	stat2_RW1++; 
			    	tv_stat2_LW1.setText(Integer.toString(stat2_LW1));
			    	tv_stat2_C1.setText(Integer.toString(stat2_C1));
			    	tv_stat2_RW1.setText(Integer.toString(stat2_RW1));
			    	break;
		    }
		}

    	public boolean onTouch(View v, MotionEvent event) {
    		switch(event.getAction()){
		        case MotionEvent.ACTION_DOWN: {
		            downX = event.getX();
		            downY = event.getY();
		            v.setBackgroundResource(R.drawable.row_button_75_35_highlight);
		            switch( v.getId() ) {
		            	case R.id.Button_Row1:
		            		View lwView = (View) findViewById(R.id.Button_LW1);
		            		View cView = (View) findViewById(R.id.Button_C1);
		            		View rwView = (View) findViewById(R.id.Button_RW1);
		                    lwView.setBackgroundResource(R.drawable.button_90_45_highlight);
		                    cView.setBackgroundResource(R.drawable.button_90_45_highlight);
		                    rwView.setBackgroundResource(R.drawable.button_90_45_highlight);
		                    break;
		            }
		            return true;
		        }
		        case MotionEvent.ACTION_UP: {
		            upX = event.getX();
		            upY = event.getY();
		            v.setBackgroundResource(R.drawable.row_button_75_35);
		            float deltaX = downX - upX;
		            float deltaY = downY - upY;

		            switch( v.getId() ) {
		            	case R.id.Button_Row1:
		            		View lwView = (View) findViewById(R.id.Button_LW1);
		            		View cView = (View) findViewById(R.id.Button_C1);
		            		View rwView = (View) findViewById(R.id.Button_RW1);
		                    lwView.setBackgroundResource(R.drawable.button_90_45);
		                    cView.setBackgroundResource(R.drawable.button_90_45);
		                    rwView.setBackgroundResource(R.drawable.button_90_45);
		                    break;
		            }

		            
                    Log.i(logTag, "Swipe was " + Math.abs(deltaX) + " wide and " + Math.abs(deltaY) + "long.  " );
                    Log.i(logTag, "X1=" + downX + ", Y1=" + downY + ", X2=" + upX + ", Y2=" + upY );
                    

		            // swipe horizontal?
		            if( (Math.abs(deltaX) > Math.abs(deltaY)) & (Math.abs(deltaX) > MIN_DISTANCE) ){
		                // left or right
		                if(deltaX < 0) { this.onLeftToRightSwipe(v); return true; }
		                if(deltaX > 0) { this.onRightToLeftSwipe(v); return true; }
		            }
		            else if (Math.abs(deltaY) > MIN_DISTANCE) {
			                if(deltaY < 0) { this.onTopToBottomSwipe(v); return true; }
			                if(deltaY > 0) { this.onBottomToTopSwipe(v); return true; }
			                else {
		                    return false; // We don't consume the event
			                } 
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
