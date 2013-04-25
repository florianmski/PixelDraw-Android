package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.polytech.polydraw.ui.fragments.WaitingRoomFragment;
import com.polytech.polydraw.utils.Constants;
import com.slidingmenu.lib.SlidingMenu;

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
}
