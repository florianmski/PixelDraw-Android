package com.polytech.polydraw.ui.fragments;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.adapters.ListPlayerAdapter;
import com.polytech.polydraw.events.GameEvent;
import com.polytech.polydraw.listeners.RoomEventListener;
import com.polytech.polydraw.models.Player;
import com.polytech.polydraw.models.Room;
import com.polytech.polydraw.ui.activities.GameActivity;
import com.polytech.polydraw.utils.Constants;

public class WaitingRoomFragment extends BaseFragment implements RoomEventListener
{	
	private ListView lvPlayer;
	private ListPlayerAdapter adapter;
	private Button btnLaunch;

	private String adminId;

	public static WaitingRoomFragment newInstance(String adminId)
	{
		WaitingRoomFragment f = new WaitingRoomFragment();
		Bundle b = new Bundle();
		b.putString(Constants.BUNDLE_ADMIN_ID, adminId);
		f.setArguments(b);
		return f;
	}

	public static WaitingRoomFragment newInstance(Bundle b)
	{
		return newInstance(b.getString(Constants.BUNDLE_ADMIN_ID));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

		adminId = getArguments().getString(Constants.BUNDLE_ADMIN_ID);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);

		if(!adminId.equals(getGC().getPlayerID()))
			btnLaunch.setVisibility(View.GONE);

		lvPlayer.setAdapter(adapter = new ListPlayerAdapter(getActivity(), getGC().getPlayerList()));

		btnLaunch.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{	
				launchGame();
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


	public void onStart()
	{
		super.onStart();
		getCM().addRoomEventListener(this);
	}

	public void onDestroy()
	{
		super.onDestroy();
		getCM().removeRoomEventListener(this);
	}

	private void launchGame()
	{
		getCM().removeRoomEventListener(this);
		GameActivity.launch(getActivity());
	}

	@Override
	public void onRoomEvent(GameEvent e) 
	{
		if(e.event.room.state == Room.STATE_DRAWER_CHOOSING)
		{
			launchGame();
		}
		else
		{
			List<Player> players = e.event.room.players;
			getGC().setPlayerList(players);
			adapter.update(players);
		}
	}

}
