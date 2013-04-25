package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.fragments.DrawFragment;
import com.polytech.polydraw.ui.fragments.ScoreBoardFragment;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(Menu.NONE, R.id.menu_score, Menu.NONE, "Scores").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) 
	{
		switch(item.getItemId())
		{
		case R.id.menu_score:
			ScoreBoardFragment.newInstance().show(getFragmentManager(), null);
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}


}
