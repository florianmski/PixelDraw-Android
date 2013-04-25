package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.polytech.polydraw.api.CommunicationManager;
import com.polytech.polydraw.ui.fragments.WaitingRoomFragment;
import com.polytech.polydraw.utils.Constants;
import com.slidingmenu.lib.SlidingMenu;

import de.tavendo.autobahn.Wamp.CallHandler;

public class WaitingRoomActivity extends ChatEnabledActivity
{	
	public static void launch(Activity a, String adminId)
	{
		Intent i = new Intent(a, WaitingRoomActivity.class);
		i.putExtra(Constants.BUNDLE_ADMIN_ID, adminId);
		a.startActivity(i);
	}

	@Override
	protected Fragment getMainFragment() 
	{
		return WaitingRoomFragment.newInstance(getIntent().getExtras());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
	
	//Leave the room and return to the room list
	@Override
	public void onBackPressed() {
		
		 new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Quit")
	        .setMessage("Do you want to quit the waiting room ?")
	        .setPositiveButton("YES", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) {

	            	CommunicationManager.getInstance().leaveRoom(new CallHandler() {
	        			
	        			@Override
	        			public void onResult(Object result) {
	        				
	        				//CommunicationManager.getInstance().closeCommunication();	
	        				//MenuActivity.launchClearTop(WaitingRoomActivity.this);
	        			
	        				WaitingRoomActivity.super.onBackPressed();
	        			}
	        			
	        			@Override
	        			public void onError(String errorUri, String errorDesc) {
	        				
	        				
	        			}
	        		});
	            }

	        })
	        .setNegativeButton("NO", null)
	        .show();	
		
	}
	
}
