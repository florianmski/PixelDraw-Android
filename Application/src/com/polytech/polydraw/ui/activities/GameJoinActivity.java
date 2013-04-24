package com.polytech.polydraw.ui.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.adapters.ListGameAdapter;


public class GameJoinActivity extends BaseActivity{

	private ListView lvGame;
	private ListGameAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_join);
		
		final List<String> games = new ArrayList<String>();
		for(int i = 0; i < 7; i++)
			games.add("Game : " + i);
		
		lvGame.setAdapter(this.adapter = new ListGameAdapter(this, games));	
	
	}	
	
	public static void launch(Activity a)
	{
		Intent i = new Intent(a, GameJoinActivity.class);
		a.startActivity(i);
	}
	
}
