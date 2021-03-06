package com.polytech.polydraw.ui.fragments;

import java.util.ArrayList;
import java.util.List;

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

public class ChatFragment extends BaseFragment
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
		
		final List<String> messages = new ArrayList<String>();
		for(int i = 0; i < 100; i++)
			messages.add("coucou : " + i);
		
		lvChat.setAdapter(adapter = new ListMessageAdapter(getActivity(), messages));
		
		btnChatSend.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				String message = edtChat.getText().toString().trim();
				if(message.equals(""))
				{
					// TODO send message
				}
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
}