package com.polytech.polydraw.ui.fragments;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
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
	
		getCM().getRoomList(new CallHandler() 
		{	
			@Override
			public void onResult(Object result) 
			{	
				refreshRoomList();
			}
			
			@Override

			public void onError(String errorUri, String errorDesc) {}

		});

		lvGame.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
			{
				String roomId = ((Room)adapter.getItem(position)).id;
				getCM().joinRoom(roomId, new CallHandler() 
				{	
					@Override
					public void onResult(Object result) 
					{
						Wrapper wr = (Wrapper) result;
						onRoomJoined(wr.room);
					}
					
					@Override
					public void onError(String errorUri, String errorDesc) {}
				});
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

	
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		MenuItem refreshItem = menu.add(Menu.NONE, R.id.menu_color, Menu.NONE, "color");
		
		refreshItem.setIcon(R.drawable.ic_navigation_refresh)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		refreshItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				refreshRoomList();
				return false;
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
							onRoomJoined(wr.room);
						}

						@Override
						public void onError(String errorUri, String errorDesc) {}
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
	
	public void onRoomJoined(Room r)
	{
		getGC().setCurRoom(r);
		Log.e("test", "null ? " + (r.players == null));
		getGC().setPlayerList(r.players);
		getCM().subscribeGame();
		WaitingRoomActivity.launch(getActivity());
	}
	
	public void refreshRoomList(){
		
		getCM().getRoomList(new CallHandler() 
		{	
			@Override
			public void onResult(Object result) 
			{	
				Wrapper wr = (Wrapper)result;
				List<Room> list_room = wr.rooms;
				lvGame.setAdapter(adapter = new ListGameAdapter(getActivity(), list_room));
			}
			
			@Override
		public void onError(String errorUri, String errorDesc) {}

		});
		
	}
	
	
}
