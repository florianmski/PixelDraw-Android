package com.polytech.polydraw.ui.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.adapters.ListMessageAdapter;
import com.polytech.polydraw.events.GameEvent;
import com.polytech.polydraw.listeners.PlayerEventListener;
import com.polytech.polydraw.listeners.ServerEventListener;
import com.polytech.polydraw.ui.activities.ChatEnabledActivity;

public class ChatFragment extends BaseFragment implements PlayerEventListener, ServerEventListener
{
	private ListView lvChat;
	private EditText edtChat;
	private Button btnChatSend;

	private ListMessageAdapter adapter;

	public static ChatFragment newInstance()
	{
		ChatFragment f = new ChatFragment();
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
		
		lvChat.setAdapter(adapter = new ListMessageAdapter(getActivity(), new ArrayList<String>()));

		btnChatSend.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				String message = edtChat.getText().toString().trim();
				if(!message.equals(""))
					getCM().sendChatMessage(message);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_chat, null);

		lvChat = (ListView)v.findViewById(R.id.listViewChat);
		edtChat = (EditText)v.findViewById(R.id.editTextChat);
		btnChatSend = (Button)v.findViewById(R.id.buttonChatSend);

		return v;
	}
	
	public void onStart()
	{
		super.onStart();
		
		getCM().addPlayerEventListener(this);
		getCM().addServerEventListener(this);
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		
		getCM().removePlayerEventListener(this);
		getCM().removeServerEventListener(this);
	}

	public void onNewMessageReceived(String message)
	{
		// if sliding menu is not opened, don't show the new message icon
		if(!((ChatEnabledActivity)getActivity()).getSlidingMenu().isMenuShowing())
			displayChatIcon(true);
		adapter.addMessage(message);
	}

	public void displayChatIcon(boolean visible)
	{
		getActivity().getActionBar().setIcon(visible ? R.drawable.ic_menu_notifications : R.drawable.ic_launcher);
	}

	@Override
	public void onPlayerEvent(GameEvent e) 
	{
		onNewMessageReceived(e.event.msg);
	}

	@Override
	public void onServerEvent(GameEvent e) 
	{
		onNewMessageReceived(e.event.msg);
	}
}