package com.polytech.polydraw.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.polytech.polydraw.R;
import com.polytech.polydraw.models.Player;
import com.polytech.polydraw.models.Wrapper;
import com.polytech.polydraw.ui.activities.GameActivity;
import com.polytech.polydraw.ui.activities.GameJoinActivity;

import de.tavendo.autobahn.Wamp.CallHandler;

public class MenuFragment extends BaseFragment implements CallHandler
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
//				GameCreateActivity.launch(getActivity());
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
		
		start();
	}
	
	private void startConnection()
	{
		getCM().startConnection(getGC().getPlayerName(), this);
	}
	
	private void start()
	{
		final SharedPreferences settings = getActivity().getSharedPreferences("PolydrawPreferences5", 0);
		boolean isPlayerNameSet = settings.getBoolean("is_player_name_set", false);
		if(!isPlayerNameSet)
		{
			//Alert dialog for name
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			final EditText edt = new EditText(getActivity());
			edt.setHint("Enter your pseudo");
			
			builder.setView(edt).setPositiveButton("Ok", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					String playerName = edt.getText().toString().trim();
					getGC().setPlayerName(playerName);
					SharedPreferences.Editor settingEditor = settings.edit();
					settingEditor.putBoolean("is_player_name_set", true);
					settingEditor.putString("player_name", getGC().getPlayerName());
					settingEditor.commit();
					startConnection();
				}
			});
			builder.create().show();
			
		}
		else
		{
			String playerName = settings.getString("player_name", "Anonymous");
			getGC().setPlayerName(playerName);
			startConnection();
		}
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

	@Override
	public void onResult(Object result) 
	{
		Wrapper convertedResult = (Wrapper)result;
		Player p = convertedResult.player;
		getGC().setPlayerID(String.valueOf(p.id));
//		Log.e("LOLOLOL", "result");
	}

	@Override
	public void onError(String errorUri, String errorDesc) 
	{
		// TODO
//		mButReconnect.setVisibility(View.VISIBLE);
	}
	
}
