package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import com.polytech.polydraw.ui.fragments.DrawFragment;

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
}
