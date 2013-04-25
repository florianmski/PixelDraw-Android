package com.polytech.polydraw.ui.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.adapters.ListPlayerAdapter;
import com.polytech.polydraw.models.Player;
import com.polytech.polydraw.ui.activities.ScoreBoardActivity;

public class WaitingRoomFragment extends BaseFragment
{	
	private ListView lvPlayer;
	private ListPlayerAdapter adapter;
	private Button btnLaunch;
	
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
		
		lvPlayer.setAdapter(adapter = new ListPlayerAdapter(getActivity(), new ArrayList<Player>()));
			
		btnLaunch.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{	
				ScoreBoardActivity.launch(getActivity());
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_waiting_room, null);
		lvPlayer = (ListView)v.findViewById(R.id.lvPlayer);
		btnLaunch = (Button)v.findViewById(R.id.btnLaunch);
		return v;
	}	

}
