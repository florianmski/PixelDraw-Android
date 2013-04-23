package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.fragments.ScoreBoardFragment;

public class ScoreBoardActivity extends BaseActivity{

		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.activity_empty);
			
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
}


