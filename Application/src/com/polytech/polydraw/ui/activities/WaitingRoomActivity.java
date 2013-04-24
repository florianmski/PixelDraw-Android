package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.polytech.polydraw.ui.fragments.WaitingRoomFragment;
import com.slidingmenu.lib.SlidingMenu;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
	
}
