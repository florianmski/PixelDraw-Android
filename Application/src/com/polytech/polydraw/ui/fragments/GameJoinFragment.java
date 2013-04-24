package com.polytech.polydraw.ui.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.polytech.polydraw.R;
import com.polytech.polydraw.adapters.ListGameAdapter;
import com.polytech.polydraw.ui.activities.GameCreateActivity;
import com.polytech.polydraw.ui.activities.WaitingRoomActivity;

public class GameJoinFragment extends BaseFragment
{
	private ListView lvGame;
	private ListGameAdapter adapter;
	private Button btnCreateRoom;
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
		
		lvGame.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				WaitingRoomActivity.launch(getActivity());
			}
		});
	
		btnCreateRoom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//GameCreateActivity.launch(getActivity());
				GameCreateActivity.launch(getActivity());
			}
			
		});
	
	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_game_join, null);
		lvGame = (ListView)v.findViewById(R.id.lvGame);
		btnCreateRoom = (Button)v.findViewById(R.id.btnCreateRoom);
		return v;
	}

	
	
}
