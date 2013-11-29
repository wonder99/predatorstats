package com.example.myfirstapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends Activity {

	Button bt_Row1;
/*
	static Button bt_LW1;
	static Button bt_C1;
	static Button bt_RW1;

	static Button[] playerButtons = new Button [15];
	static TextView[] tv_stats1 = new TextView [15];
	static TextView[] tv_stats2 = new TextView [15];
		
	static int[] stat1 = new int [15];
	static int[] stat2 = new int [15];
	
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
	*/
	public class Player {
		public Button bt_player;
		public TextView tv_stat1;
		public TextView tv_stat2;
		int stat1;
		int stat2;
				
		public void set_bt(View v) {
			bt_player = (Button) v;
		}
		public void incr_stat1 () {
			stat1++;
			tv_stat1.setText(Integer.toString(stat1));
		}
		public void decr_stat1 () {
			stat1--;
			tv_stat1.setText(Integer.toString(stat1));
		}
		public void incr_stat2 () {
			stat2++;
			tv_stat2.setText(Integer.toString(stat2));
		}
		public void decr_stat2 () {
			stat2--;
			tv_stat2.setText(Integer.toString(stat2));
		}
		public void setName (String newName) {
			bt_player.setText(newName);
		}
		
	}
	
	static Player[] playerList = new Player [3];
	
	ToggleButton tb_edit_mode;
	boolean editMode;
	
	static Resources res;

	static int editedPlayer;
	
	public static class SelectPlayerDialogFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the Builder class for convenient dialog construction
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.select_player_prompt);
			builder.setItems(R.array.string_array_players, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// The 'which' argument contains the index position
					// of the selected item
					String[] name = res.getStringArray(R.array.string_array_players);
					switch (editedPlayer) {
					case R.id.Button_RW1:
						playerList[2].setName(name[which]);
						break;
					case R.id.Button_C1:
						playerList[1].setName(name[which]);
						break;
					case R.id.Button_LW1:
						playerList[0].setName(name[which]);
						break;

					}

				}
			});
			return builder.create();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		res = getResources();
		
		final PlayerSwipeDetector playerSwipeDetector = new PlayerSwipeDetector(this);
		RowSwipeDetector rowSwipeDetector = new RowSwipeDetector(this);
		
		Button testbut = (Button) findViewById(R.id.Button_LW1);

		for( int i=0; i<playerList.length; i++) {
			playerList[i] = new Player();
		}

		playerList[0].bt_player = (Button)   findViewById(R.id.Button_LW1);
		playerList[0].tv_stat1  = (TextView) findViewById(R.id.Stat1_LW1);
		playerList[0].tv_stat2  = (TextView) findViewById(R.id.Stat2_LW1);
		
		playerList[1].bt_player = (Button)   findViewById(R.id.Button_C1);
		playerList[1].tv_stat1  = (TextView) findViewById(R.id.Stat1_C1);
		playerList[1].tv_stat2  = (TextView) findViewById(R.id.Stat2_C1);

		playerList[2].bt_player = (Button)   findViewById(R.id.Button_RW1);
		playerList[2].tv_stat1  = (TextView) findViewById(R.id.Stat1_RW1);
		playerList[2].tv_stat2  = (TextView) findViewById(R.id.Stat2_RW1);

		
		for( int i=0; i<3; i++) {
			playerList[i].bt_player.setOnTouchListener(playerSwipeDetector);
			playerList[i].bt_player.setOnClickListener(null);
		}

		bt_Row1 = (Button) findViewById(R.id.Button_Row1);
		bt_Row1.setOnTouchListener(rowSwipeDetector);
		tb_edit_mode = (ToggleButton) findViewById(R.id.Button_edit_mode);
		editMode = false;
		tb_edit_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// The toggle is enabled
					editMode = true;
			  	for( int i=0; i<playerList.length; i++) {
						playerList[i].bt_player.setOnTouchListener(null);
						playerList[i].bt_player.setOnClickListener(playerClickListener);
					}
				} else {
					// The toggle is disabled
					editMode = false;
					for( int i=0; i<playerList.length; i++) {
						playerList[i].bt_player.setOnTouchListener(playerSwipeDetector);
						playerList[i].bt_player.setOnClickListener(null);
					}
				}
			}
		} );
	}

	private OnClickListener playerClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			SelectPlayerDialogFragment dlg_player = new SelectPlayerDialogFragment();
			editedPlayer = v.getId();
			dlg_player.show(getFragmentManager(), Integer.toString(v.getId()) );
		}	
	};
	
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
    	static final int MIN_DISTANCE = 30;
    	private float downX, downY, upX, upY;

    	public PlayerSwipeDetector(Activity activity){
    	    this.activity = activity;
    	}

    	public void onRightToLeftSwipe(View v){
    		Log.i(logTag, "RightToLeftSwipe! of ");
    		switch( v.getId() ) {
    		case R.id.Button_LW1:
    			playerList[0].decr_stat1();
    			break;
    		case R.id.Button_C1:
    			playerList[1].decr_stat1();
    			break;
    		case R.id.Button_RW1:
    			playerList[2].decr_stat1();
    			break;
    		}
    	}

    	public void onLeftToRightSwipe(View v){
    		Log.i(logTag, "LeftToRightSwipe!");
    		switch( v.getId() ) {
    		case R.id.Button_LW1:
    			playerList[0].incr_stat1();
    			break;
    		case R.id.Button_C1:
    			playerList[1].incr_stat1();
    			break;
    		case R.id.Button_RW1:
    			playerList[2].incr_stat1();
    			break;
    		}
    	}

    	public void onTopToBottomSwipe(View v){
    		Log.i(logTag, "onTopToBottomSwipe!");
    		switch( v.getId() ) {
    		case R.id.Button_LW1:
    			playerList[0].decr_stat2();
    			break;
    		case R.id.Button_C1:
    			playerList[1].decr_stat2();
    			break;
    		case R.id.Button_RW1:
    			playerList[2].decr_stat2();
    			break;
    		}
    	}

    	public void onBottomToTopSwipe(View v){
    		Log.i(logTag, "onBottomToTopSwipe!");
    		switch( v.getId() ) {
    		case R.id.Button_LW1:
    			playerList[0].incr_stat2();
    			break;
    		case R.id.Button_C1:
    			playerList[1].incr_stat2();
    			break;
    		case R.id.Button_RW1:
    			playerList[2].incr_stat2();
    			break;
    		}		}

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
		
		            return false;
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
			    	for( int i=0; i<3; i++ ) 
			    		playerList[i].decr_stat1();
			    	break;
		    }
		}
		
		public void onLeftToRightSwipe(View v){
		    Log.i(logTag, "LeftToRightSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_Row1:
			    	for( int i=0; i<3; i++ ) 
			    		playerList[i].incr_stat1();
			    	break;
		    }
		}
		
		public void onTopToBottomSwipe(View v){
		    Log.i(logTag, "onTopToBottomSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_Row1:
			    	for( int i=0; i<3; i++ ) 
			    		playerList[i].decr_stat2();
			    	break;
		    }
		}
		
		public void onBottomToTopSwipe(View v){
		    Log.i(logTag, "onBottomToTopSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_Row1:
			    	for( int i=0; i<3; i++ ) 
			    		playerList[i].incr_stat2();
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
