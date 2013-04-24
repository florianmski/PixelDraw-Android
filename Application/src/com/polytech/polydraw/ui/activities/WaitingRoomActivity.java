package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import com.polytech.polydraw.ui.fragments.WaitingRoomFragment;

public class WaitingRoomActivity extends ChatEnabledActivity{
	
	
	public static void launch(Activity a)
	{
		Intent i = new Intent(a, WaitingRoomActivity.class);
		a.startActivity(i);
	}
	
	@Override
	protected Fragment getMainFragment() {
		// TODO Auto-generated method stub
		return WaitingRoomFragment.newInstance();
	}
	
}
