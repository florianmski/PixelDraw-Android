package com.polytech.polydraw.ui.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.fragments.ChatFragment;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnOpenedListener;

public abstract class ChatEnabledActivity extends BaseActivity
{
	private SlidingMenu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_empty);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		initSlidingMenu();
		
		getFragmentManager()
		.beginTransaction()
		.replace(R.id.frameLayoutEmpty, getMainFragment())
		.commit();
	}
	
	protected abstract Fragment getMainFragment();
	
	private void initSlidingMenu()
	{
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu);
        
        menu.setOnOpenedListener(new OnOpenedListener() 
        {	
			@Override
			public void onOpened() 
			{
				((ChatFragment)getFragmentManager().findFragmentById(R.id.fragmentChat)).displayChatIcon(false);
			}
		});
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
	
	public SlidingMenu getSlidingMenu()
	{
		return menu;
	}
}