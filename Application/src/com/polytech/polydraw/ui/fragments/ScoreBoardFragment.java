package com.polytech.polydraw.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.adapters.ListScoreAdapter;
import com.polytech.polydraw.api.GameContext;


public class ScoreBoardFragment extends DialogFragment
{	
	private ListView lvScoreBoard;
	private ListScoreAdapter adapter;

	public static ScoreBoardFragment newInstance()
	{
		ScoreBoardFragment f = new ScoreBoardFragment();
		return f;
	}

	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);

		lvScoreBoard.setAdapter(adapter = new ListScoreAdapter(getActivity(), GameContext.getInstance().getPlayerList()));
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) 
	{   
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_scoreboard, null);
		lvScoreBoard = (ListView)v.findViewById(R.id.lvScoreBoard);
		
		return new AlertDialog.Builder(getActivity())                
		.setTitle("Scores")
		.setView(v)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int whichButton) {}
			
		}).create();
	}

}
