package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.fragments.GameCreateFragment;


public class GameCreateActivity extends BaseActivity{

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_empty);
		
		getFragmentManager()
		.beginTransaction()
		.replace(R.id.frameLayoutEmpty, GameCreateFragment.newInstance())
		.commit();
	}
	
	public static void launch(Activity a)
	{
		Intent i = new Intent(a, GameCreateActivity.class);
		a.startActivity(i);
	}
	
}
