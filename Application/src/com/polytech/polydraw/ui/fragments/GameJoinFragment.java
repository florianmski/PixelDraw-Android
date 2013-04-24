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

import com.polytech.polydraw.R;
import com.polytech.polydraw.adapters.ListGameAdapter;
import com.polytech.polydraw.models.Room;
import com.polytech.polydraw.models.Wrapper;
import com.polytech.polydraw.ui.activities.WaitingRoomActivity;

import de.tavendo.autobahn.Wamp.CallHandler;

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

		lvGame.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				// TODO take care of the room we choose
				WaitingRoomActivity.launch(getActivity());
			}
		});

		btnCreateRoom.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View arg0) 
			{
				openDialog();
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

	private void openDialog()
	{		
		String title = "Enter a room name";

		LayoutInflater factory = LayoutInflater.from(getActivity());
		final View alertDialogView = factory.inflate(R.layout.fragment_game_create, null);

		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
		adb.setView(alertDialogView);
		adb.setTitle(title);
		adb.setPositiveButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				EditText edt = (EditText)alertDialogView.findViewById(R.id.edtPseudo);		

				String roomName = edt.getText().toString().trim();
				if(!roomName.equals(""))
				{
					getCM().createRoom(roomName, new CallHandler() 
					{	
						@Override
						public void onResult(Object result) 
						{
							Wrapper wr = (Wrapper) result;

							Room r = wr.room;

							getGC().setRoomID((String)r.id);
							getCM().subscribeGame();
							WaitingRoomActivity.launch(getActivity());
						}

						@Override
						public void onError(String errorUri, String errorDesc) 
						{

						}
					});
				}

				WaitingRoomActivity.launch(getActivity());         	
			}});
		adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});

		adb.show();	
	}

}
