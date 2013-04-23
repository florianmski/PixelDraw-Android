package com.polytech.polydraw.ui.activities;

import android.os.Bundle;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.fragments.MenuFragment;

public class MenuActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_empty);
		
		getFragmentManager()
		.beginTransaction()
		.replace(R.id.frameLayoutEmpty, MenuFragment.newInstance())
		.commit();
	}
	
}
