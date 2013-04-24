package com.polytech.polydraw.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.activities.GameActivity;

import com.polytech.polydraw.ui.activities.GameJoinActivity;
import com.polytech.polydraw.ui.activities.ScoreBoardActivity;

public class MenuFragment extends BaseFragment
{
	private Button btnCreate;
	private Button btnJoin;
	private Button btnTest;

	public static MenuFragment newInstance()
	{
		MenuFragment f = new MenuFragment();
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
		
		btnCreate.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				//TODO
			}
		});
		
		btnJoin.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				GameJoinActivity.launch(getActivity());			
			}
		});
		
		btnTest.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				GameActivity.launch(getActivity());
//				ScoreBoardActivity.launch(getActivity());
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_menu, null);
		
		btnCreate = (Button)v.findViewById(R.id.buttonCreate);
		btnJoin = (Button)v.findViewById(R.id.buttonJoin);
		btnTest = (Button)v.findViewById(R.id.buttonTest);
		
		return v;
	}
	
}
