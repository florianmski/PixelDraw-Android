package com.polytech.polydraw.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.activities.WaitingRoomActivity;

public class GameCreateFragment extends BaseFragment
{
	private Button btnCreate;
	private EditText edtCreate;
	
	public static GameCreateFragment newInstance()
	{
		GameCreateFragment f = new GameCreateFragment();
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
			public void onClick(View arg0) 
			{
				WaitingRoomActivity.launch(getActivity());
			}
		});
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_game_create, null);
		btnCreate = (Button)v.findViewById(R.id.btnCreate);
		edtCreate = (EditText)v.findViewById(R.id.btnCreate);
		return v;
	}
	
}
