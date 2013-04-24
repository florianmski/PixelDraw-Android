package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.content.Intent;


public class GameCreateActivity extends BaseActivity{
	
	public static void launch(final Activity a)
	{
		Intent i = new Intent(a, GameCreateActivity.class);
		a.startActivity(i);
		
	}		
}
