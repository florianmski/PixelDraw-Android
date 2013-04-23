package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.fragments.DrawFragment;
import com.slidingmenu.lib.SlidingMenu;

public class GameActivity extends BaseActivity
{
	private SlidingMenu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_empty);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu);
		
		getFragmentManager()
		.beginTransaction()
		.replace(R.id.frameLayoutEmpty, DrawFragment.newInstance())
		.commit();
	}
	
	public static void launch(Activity a)
	{
		Intent i = new Intent(a, GameActivity.class);
		a.startActivity(i);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
		case android.R.id.home:
			menu.toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() 
	{
		if (menu.isMenuShowing())
			menu.showContent();
		else
			super.onBackPressed();
	}
}
