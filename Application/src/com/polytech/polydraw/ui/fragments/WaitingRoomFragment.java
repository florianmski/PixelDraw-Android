package com.polytech.polydraw.ui.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.adapters.ListPlayerAdapter;

public class WaitingRoomFragment extends BaseFragment{

	
	private ListView lvPlayer;
	private ListPlayerAdapter adapter;
	
	public static WaitingRoomFragment newInstance()
	{
		WaitingRoomFragment f = new WaitingRoomFragment();
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
		final List<String> players = new ArrayList<String>();
		for(int i = 0; i < 6; i++){
			
			players.add("Player "+i);
		}
		lvPlayer.setAdapter(adapter = new ListPlayerAdapter(getActivity(), players));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_waiting_room, null);
		lvPlayer = (ListView)v.findViewById(R.id.lvPlayer);
		return v;
	}	

}
