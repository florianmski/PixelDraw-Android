package com.polytech.polydraw.ui.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.polytech.polydraw.R;
import com.polytech.polydraw.models.Player;
import com.polytech.polydraw.models.Wrapper;
import com.polytech.polydraw.ui.activities.GameJoinActivity;
import com.polytech.polydraw.ui.views.DrawView;
import com.polytech.polydraw.utils.ErrorHandler;

import de.tavendo.autobahn.Wamp.CallHandler;

public class MenuFragment extends BaseFragment implements CallHandler
{
	private Button btnPlay;
	private Button btnName;
	private DrawView dv;

	private Handler h = new Handler();
	private Runnable r;
	private Random random = new Random();
	private List<Integer> integers = new ArrayList<Integer>();
	
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
	public void onResume()
	{
		super.onResume();
		
		h.post(r = new Runnable() 
		{	
			@Override
			public void run() 
			{
				integers.set(new Random().nextInt(integers.size()), Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				dv.setDrawer(false);
				dv.update(integers);
				h.postDelayed(this, 50);
			}
		});
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		h.removeCallbacks(r);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		
		for(int i = 0; i < 256; i++)
			integers.add(0);
		
		btnPlay.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				GameJoinActivity.launch(getActivity());			
			}
		});
		
		btnName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialogSetPlayerName();
			}
		});
			
		checkConnection();
		start();
	}
	/**
	 * Leave application if no internet
	 */
	private void checkConnection(){
	    ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return;
	    }
	    else{
	    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    	builder.setMessage("No Internet! Game Over");
	    	builder.setNeutralButton("OK...", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					getActivity().finish();
				}
			});
	    	
	    	builder.create().show();
	    }
	}
	
	private void showDialogSetPlayerName(){
		
		final SharedPreferences settings = getActivity().getSharedPreferences("PolydrawPreferences5", 0);
		
			// Alert dialog for name
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
					settingEditor.putString("player_name", playerName);
					settingEditor.commit();
					
				}
			});
			builder.create().show();
	}
	
	private void startConnection()
	{
		Log.d("Player Name : ", "Player : " + getGC().getPlayerName());
		getCM().startConnection(getGC().getPlayerName(), this);
	}
	
	private void start()
	{
		final SharedPreferences settings = getActivity().getSharedPreferences("PolydrawPreferences5", 0);
		boolean isPlayerNameSet = settings.getBoolean("is_player_name_set", false);
		if(!isPlayerNameSet)
		{
			// Alert dialog for name
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
		
		btnPlay = (Button)v.findViewById(R.id.buttonPlay);
		btnName = (Button)v.findViewById(R.id.btnName);
		dv = (DrawView)v.findViewById(R.id.drawView);
		return v;
	}

	@Override
	public void onResult(Object result) 
	{
		Wrapper convertedResult = (Wrapper)result;
		Player p = convertedResult.player;
		getGC().setPlayerID(String.valueOf(p.id));
	}

	@Override
	public void onError(String errorUri, String errorDesc) 
	{
		ErrorHandler.display(getActivity(), errorUri, errorDesc);
	}
	
}
