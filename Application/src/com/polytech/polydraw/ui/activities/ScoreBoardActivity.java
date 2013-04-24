package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.fragments.ScoreBoardFragment;
import com.slidingmenu.lib.SlidingMenu;

public class ScoreBoardActivity extends ChatEnabledActivity{

		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			
			getFragmentManager()
			.beginTransaction()
			.replace(R.id.frameLayoutEmpty, ScoreBoardFragment.newInstance())
			.commit();
		}	
		
		public static void launch(Activity a)
		{
			Intent i = new Intent(a, ScoreBoardActivity.class);
			a.startActivity(i);
		}
		
		@Override
		protected Fragment getMainFragment() {
			// TODO Auto-generated method stub
			return ScoreBoardFragment.newInstance();
		}
}

