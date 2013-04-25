package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;

import com.polytech.polydraw.api.CommunicationManager;
import com.polytech.polydraw.ui.fragments.DrawFragment;

import de.tavendo.autobahn.Wamp.CallHandler;

public class GameActivity extends ChatEnabledActivity
{	
	public static void launch(Activity a)
	{
		Intent i = new Intent(a, GameActivity.class);
		a.startActivity(i);
	}

	@Override
	protected Fragment getMainFragment() 
	{
		return DrawFragment.newInstance();
	}
	
	public void onBackPressed() {
		
		 new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Quit")
	        .setMessage("Do you want to return to the menu ?")
	        .setPositiveButton("YES", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) {

	            		CommunicationManager.getInstance().leaveRoom(new CallHandler() {
	        			
	        			@Override
	        			public void onResult(Object result) {
	        				
	        				CommunicationManager.getInstance().closeCommunication();	
	        				MenuActivity.launchClearTop(GameActivity.this);
	        				
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
