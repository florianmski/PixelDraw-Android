package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.polytech.polydraw.R;
import com.polytech.polydraw.api.CommunicationManager;
import com.polytech.polydraw.ui.fragments.MenuFragment;

import de.tavendo.autobahn.Wamp.CallHandler;

public class MenuActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_empty);
		
		getFragmentManager()
		.beginTransaction()
		.replace(R.id.frameLayoutEmpty, MenuFragment.newInstance())
		.commit();
	}

	public static void launch(Activity a)
	{
		Intent i = new Intent(a, MenuActivity.class);
		a.startActivity(i);
	}

	public static void launchClearTop(Activity a){
		
		Intent i = new Intent(a, MenuActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		a.startActivity(i);
	}
	
	@Override
	public void onBackPressed() {
		
		 new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Quit")
	        .setMessage("Do you want to quit the game ?")
	        .setPositiveButton("YES", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) {

	            	MenuActivity.super.onBackPressed();
	            }

	        })
	        .setNegativeButton("NO", null)
	        .show();	
		
	}
}
