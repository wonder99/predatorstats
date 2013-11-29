package com.wonder99.predatorstats;

import com.wonder99.predatorstats.R;

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

	Button bt_Row1, bt_Row2;
	Button resetStats;

	public class Player {
		private Button bt_player;
		private TextView tv_stat1;
		private TextView tv_stat2;
		private int stat1;
		private int stat2;
			
		public Player( int playerButtonId, int stat1ViewId, int stat2ViewId) {
			bt_player = (Button) findViewById(playerButtonId);
			tv_stat1 = (TextView) findViewById(stat1ViewId);
			tv_stat2 = (TextView) findViewById(stat2ViewId);
			}
		
		public void set_bt(View v) {
			bt_player = (Button) v;
		}
		public void clearStats() {
			stat1 = 0;
			stat2 = 0;
			tv_stat1.setText(Integer.toString(stat1));
			tv_stat2.setText(Integer.toString(stat2));
		}
		public int getStat1() {
			return stat1;
		}
		public int getStat2() {
			return stat2;
		}
		public void incrStat1 () {
			stat1++;
			tv_stat1.setText(Integer.toString(stat1));
		}
		public void decrStat1 () {
			stat1--;
			tv_stat1.setText(Integer.toString(stat1));
		}
		public void incrStat2 () {
			stat2++;
			tv_stat2.setText(Integer.toString(stat2));
		}
		public void decrStat2 () {
			stat2--;
			tv_stat2.setText(Integer.toString(stat2));
		}
		public void setName (String newName) {
			bt_player.setText(newName);
		}
		
	}
	
	static Player[] playerList = new Player [6];
	
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
					playerList[editedPlayer].setName(name[which]);
					
//					switch (editedPlayer) {
//					case R.id.Button_RW1:
//						playerList[2].setName(name[which]);
//						break;
//					case R.id.Button_C1:
//						playerList[1].setName(name[which]);
//						break;
//					case R.id.Button_LW1:
//						playerList[0].setName(name[which]);
//						break;
//
//					}

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
		
		/*
		 * Set up the Stats Reset button
		 */
		resetStats = (Button) findViewById(R.id.buttonReset);
		resetStats.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				for( int i=0; i<playerList.length; i++ ) {
					playerList[i].clearStats();
				}
			};
		});

		RowSwipeDetector rowSwipeDetector = new RowSwipeDetector(this);
		bt_Row1 = (Button) findViewById(R.id.Button_Row1);
		bt_Row1.setOnTouchListener(rowSwipeDetector);
		bt_Row2 = (Button) findViewById(R.id.Button_Row2);
		bt_Row2.setOnTouchListener(rowSwipeDetector);
		
		final PlayerSwipeDetector playerSwipeDetector = new PlayerSwipeDetector(this);
		
		playerList[0] = new Player(R.id.Button_LW1,R.id.Stat1_LW1,R.id.Stat2_LW1);
		playerList[1] = new Player(R.id.Button_C1, R.id.Stat1_C1, R.id.Stat2_C1);
		playerList[2] = new Player(R.id.Button_RW1,R.id.Stat1_RW1,R.id.Stat2_RW1);

		playerList[3] = new Player(R.id.Button_LW2,R.id.Stat1_LW2,R.id.Stat2_LW2);
		playerList[4] = new Player(R.id.Button_C2, R.id.Stat1_C2, R.id.Stat2_C2);
		playerList[5] = new Player(R.id.Button_RW2,R.id.Stat1_RW2,R.id.Stat2_RW2);

		for( int i=0; i<playerList.length; i++) {
			playerList[i].bt_player.setOnTouchListener(playerSwipeDetector);
			playerList[i].bt_player.setOnClickListener(null);
		}

		/*
		 * Configure Edit Mode Button
		 */
		tb_edit_mode = (ToggleButton) findViewById(R.id.Button_edit_mode);
		editMode = false;
		tb_edit_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// Entering Edit Mode
					editMode = true;
					for( int i=0; i<playerList.length; i++) {
						playerList[i].bt_player.setOnTouchListener(null);
						playerList[i].bt_player.setOnClickListener(playerClickListener);
					}
				} else {
					// Exiting Edit Mode
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
			String playerName;
			Button playerButton;
			SelectPlayerDialogFragment dlg_player = new SelectPlayerDialogFragment();
			playerButton = (Button) v;
			playerName = playerButton.getText().toString();
			for( int i=0; i<playerList.length; i++ ) {
				if( playerList[i].bt_player.getId() == v.getId() ) {
					editedPlayer = i;
					break;
				}
			}
			//editedPlayer = v.getId();
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
			for( int i=0; i<playerList.length; i++ ) {
				if( playerList[i].bt_player.getId() == v.getId() ) {
					playerList[i].decrStat1();
					break;
				}
			}
    	}

    	public void onLeftToRightSwipe(View v){
    		Log.i(logTag, "LeftToRightSwipe!");
			for( int i=0; i<playerList.length; i++ ) {
				if( playerList[i].bt_player.getId() == v.getId() ) {
					playerList[i].incrStat1();
					break;
				}
			}
    	}

    	public void onTopToBottomSwipe(View v){
    		Log.i(logTag, "onTopToBottomSwipe!");
			for( int i=0; i<playerList.length; i++ ) {
				if( playerList[i].bt_player.getId() == v.getId() ) {
					playerList[i].decrStat2();
					break;
				}
			}
    	}

    	public void onBottomToTopSwipe(View v){
    		Log.i(logTag, "onBottomToTopSwipe!");
			for( int i=0; i<playerList.length; i++ ) {
				if( playerList[i].bt_player.getId() == v.getId() ) {
					playerList[i].incrStat1();
					break;
				}
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
		            	
//                    Log.i(logTag, "Swipe was " + Math.abs(deltaX) + " wide and " + Math.abs(deltaY) + "long.  " );
//                    Log.i(logTag, "X1=" + downX + ", Y1=" + downY + ", X2=" + upX + ", Y2=" + upY );

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
    	static final int MIN_DISTANCE = 30;
    	private float downX, downY, upX, upY;

    	public RowSwipeDetector(Activity activity){
    	    this.activity = activity;
    	}

		public void onRightToLeftSwipe(View v){
		    Log.i(logTag, "RightToLeftSwipe! of ");
		    switch( v.getId() ) {
			    case R.id.Button_Row1:
			    	for( int i=0; i<3; i++ ) 
			    		playerList[i].decrStat1();
			    	break;
			    case R.id.Button_Row2:
			    	for( int i=3; i<6; i++ ) 
			    		playerList[i].decrStat1();
			    	break;
		    }
		}
		
		public void onLeftToRightSwipe(View v){
		    Log.i(logTag, "LeftToRightSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_Row1:
			    	for( int i=0; i<3; i++ ) 
			    		playerList[i].incrStat1();
			    	break;
			    case R.id.Button_Row2:
			    	for( int i=3; i<6; i++ ) 
			    		playerList[i].incrStat1();
			    	break;
		    }
		}
		
		public void onTopToBottomSwipe(View v){
		    Log.i(logTag, "onTopToBottomSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_Row1:
			    	for( int i=0; i<3; i++ ) 
			    		playerList[i].decrStat2();
			    	break;
			    case R.id.Button_Row2:
			    	for( int i=3; i<6; i++ ) 
			    		playerList[i].decrStat2();
			    	break;
		    }
		}
		
		public void onBottomToTopSwipe(View v){
		    Log.i(logTag, "onBottomToTopSwipe!");
		    switch( v.getId() ) {
			    case R.id.Button_Row1:
			    	for( int i=0; i<3; i++ ) 
			    		playerList[i].incrStat2();
			    	break;
			    case R.id.Button_Row2:
			    	for( int i=3; i<6; i++ ) 
			    		playerList[i].incrStat2();
			    	break;
		    }
		}

    	public boolean onTouch(View v, MotionEvent event) {
    		View lwView, cView, rwView;
    		switch(event.getAction()){
		        case MotionEvent.ACTION_DOWN: {
		            downX = event.getX();
		            downY = event.getY();
		            v.setBackgroundResource(R.drawable.row_button_75_35_highlight);
		            switch( v.getId() ) {
		            	case R.id.Button_Row1:
		            		lwView = (View) findViewById(R.id.Button_LW1);
		            		cView = (View) findViewById(R.id.Button_C1);
		            		rwView = (View) findViewById(R.id.Button_RW1);
		                    lwView.setBackgroundResource(R.drawable.button_90_45_highlight);
		                    cView.setBackgroundResource(R.drawable.button_90_45_highlight);
		                    rwView.setBackgroundResource(R.drawable.button_90_45_highlight);
		                    break;
		            	case R.id.Button_Row2:
		            		lwView = (View) findViewById(R.id.Button_LW2);
		            		cView = (View) findViewById(R.id.Button_C2);
		            		rwView = (View) findViewById(R.id.Button_RW2);
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
		            		lwView = (View) findViewById(R.id.Button_LW1);
		            		cView = (View) findViewById(R.id.Button_C1);
		            		rwView = (View) findViewById(R.id.Button_RW1);
		                    lwView.setBackgroundResource(R.drawable.button_90_45);
		                    cView.setBackgroundResource(R.drawable.button_90_45);
		                    rwView.setBackgroundResource(R.drawable.button_90_45);
		                    break;
		            	case R.id.Button_Row2:
		            		lwView = (View) findViewById(R.id.Button_LW2);
		            		cView = (View) findViewById(R.id.Button_C2);
		            		rwView = (View) findViewById(R.id.Button_RW2);
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
