package com.polytech.polydraw.ui.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.adapters.ListGameAdapter;

public class GameJoinFragment extends BaseFragment{

	private ListView lvGame;
	private ListGameAdapter adapter;
	
	public static GameJoinFragment newInstance()
	{
		GameJoinFragment f = new GameJoinFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		
		
		final List<String> games = new ArrayList<String>();
		for(int i = 0; i < 7; i++)
			games.add("Game : " + i);
		
		lvGame.setAdapter(this.adapter = new ListGameAdapter(getActivity(), games));	

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_game_join, null);
		lvGame = (ListView)v.findViewById(R.id.lvGame);
		return v;
	}

	
	
}
