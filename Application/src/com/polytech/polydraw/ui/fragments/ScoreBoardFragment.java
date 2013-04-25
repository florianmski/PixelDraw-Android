package com.polytech.polydraw.ui.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.adapters.ListScoreAdapter;
import com.polytech.polydraw.ui.activities.GameActivity;


public class ScoreBoardFragment extends BaseFragment
{	
	private ListView lvScoreBoard;
	private ListScoreAdapter adapter;
	private TextView tvTimeRemain;
	private Button btnLaunchTurn;
	
	public static ScoreBoardFragment newInstance()
	{
		ScoreBoardFragment f = new ScoreBoardFragment();
		return f;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		
		final List<String> scores = new ArrayList<String>();
		for(int i = 0; i < 4; i++)		
			scores.add(""+i);
		
		lvScoreBoard.setAdapter(adapter = new ListScoreAdapter(getActivity(), scores));
	
		btnLaunchTurn.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				GameActivity.launch(getActivity());
			}
		});
	}

	public void setTimeRemain(int time)
	{	
		tvTimeRemain.setText(time + " seconds");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_scoreboard, null);
		lvScoreBoard = (ListView)v.findViewById(R.id.lvScoreBoard);
		tvTimeRemain = (TextView)v.findViewById(R.id.tvTimeRemain);
		btnLaunchTurn = (Button)v.findViewById(R.id.btnLaunchTurn);
		return v;
	}
	
}
