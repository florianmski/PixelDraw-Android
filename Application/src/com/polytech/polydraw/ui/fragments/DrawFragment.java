package com.polytech.polydraw.ui.fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.polytech.polydraw.R;
import com.polytech.polydraw.events.GameEvent;
import com.polytech.polydraw.listeners.DrawEventListener;
import com.polytech.polydraw.listeners.RoomEventListener;
import com.polytech.polydraw.models.Category;
import com.polytech.polydraw.models.Word;
import com.polytech.polydraw.models.Wrapper;
import com.polytech.polydraw.ui.fragments.ColorDialogFragment.OnColorListener;
import com.polytech.polydraw.ui.views.CircleColorView;
import com.polytech.polydraw.ui.views.DrawView;
import com.polytech.polydraw.utils.ErrorHandler;

import de.tavendo.autobahn.Wamp.CallHandler;

public class DrawFragment extends BaseFragment implements DrawEventListener, RoomEventListener
{
	private final static int SEND_DRAWING_DELAY = 300;
	private final static int DRAWING_DELAY = 20*1000;

	private boolean isDrawer = false;
	private long startTurnAt;
	private String drawerId;

	private DrawView dv;
	private CircleColorView cd;
	private EditText edtChat;
	private Button btnChatSend;
	private ProgressBar pbTime;

	private Handler h = new Handler();
	private Runnable r;

	public static DrawFragment newInstance()
	{
		DrawFragment f = new DrawFragment();
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

		cd = (CircleColorView) getActivity().getLayoutInflater().inflate(R.layout.view_color, null);

		btnChatSend.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				String message = edtChat.getText().toString().trim();
				if(!message.equals(""))
				{
					getCM().sendChatMessage(message);
				}
			}
		});		

		pbTime.setMax(100);

		startTurn();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_draw, null);

		dv = (DrawView)v.findViewById(R.id.drawView);
		edtChat = (EditText)v.findViewById(R.id.editTextChat);
		btnChatSend = (Button)v.findViewById(R.id.buttonChatSend);
		pbTime = (ProgressBar)v.findViewById(R.id.progressBarTime);

		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		if(isDrawer)
		{
			MenuItem colorItem = menu.add(Menu.NONE, R.id.menu_color, Menu.NONE, "color");
			colorItem
			.setActionView(cd)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

			cd.setOnClickListener(new OnClickListener() 
			{	
				@Override
				public void onClick(View v) 
				{
					ColorDialogFragment cdf = ColorDialogFragment.newInstance();
					cdf.setOnColorListener(new OnColorListener() 
					{	
						@Override
						public void onColorSelected(int color) 
						{
							dv.setColor(color);
							cd.setColor(color);
						}
					});
					cdf.show(getFragmentManager(), null);
				}
			});
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
		case R.id.menu_color:
			ColorDialogFragment.newInstance().show(getFragmentManager(), null);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void changeUI()
	{
		getActivity().invalidateOptionsMenu();

		dv.setDrawer(isDrawer);

		h.post(new Runnable() 
		{	
			@Override
			public void run() 
			{
//				long timeRemaining = (getGC().getCurRoom().ended_at * 1000) - System.currentTimeMillis();
//				if(getGC().getCurRoom().ended_at > 0)
//				{
//					int progress = (int) ((timeRemaining * 100) / (getGC().getCurRoom().ended_at * 1000 - startTurnAt));
//					pbTime.setProgress(progress);
//				}
				long timeRemaining = startTurnAt - System.currentTimeMillis() + DRAWING_DELAY;
				int progress = (int) ((timeRemaining * 100) / (DRAWING_DELAY));
				pbTime.setProgress(progress);
				
				h.postDelayed(this, 1000);
			}
		});
	}

	private void startTurn()
	{		
		stopDrawing();

		startTurnAt = System.currentTimeMillis();
		designateDrawer();
		
		changeUI();

		if(isDrawer)
			displayCategories();
		else
		{

		}
	}

	private void displayCategories()
	{
		getCM().getCategories(new CallHandler() 
		{	
			@Override
			public void onResult(Object result) 
			{
				final Wrapper w = (Wrapper) result;
				CharSequence[] categories = new CharSequence[w.categories.size()];
				for(int i = 0; i < w.categories.size(); i++)
				{
					Category c = w.categories.get(i);
					categories[i] = c.name;
				}
				AlertDialog.Builder builder = new Builder(getActivity());
				builder.setItems(categories, new DialogInterface.OnClickListener() 
				{	
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						getCM().getWord(w.categories.get(which), new CallHandler() 
						{	
							@Override
							public void onResult(Object result) 
							{
								Word word = ((Wrapper)result).word;
								getActivity().getActionBar().setTitle(word.name);
								startDrawing();
							}

							@Override
							public void onError(String errorUri, String errorDesc) 
							{
								ErrorHandler.display(getActivity(), errorUri, errorDesc);								
							}
						});
					}
				});

				builder.create().show();
			}

			@Override
			public void onError(String errorUri, String errorDesc) 
			{
				ErrorHandler.display(getActivity(), errorUri, errorDesc);
			}
		});
	}

	private void designateDrawer()
	{
		isDrawer = getGC().getPlayerID().equals(getGC().getCurRoom().drawer_id);
	}

	private void startDrawing()
	{
		h.post(r = new Runnable() 
		{	
			@Override
			public void run() 
			{
				getCM().sendDrawMessage(dv.getDrawing());
				h.postDelayed(this, SEND_DRAWING_DELAY);
			}
		});
	}

	private void stopDrawing()
	{
		if(r != null)
			h.removeCallbacks(r);
	}


	public void onStart()
	{
		super.onStart();
		getCM().addDrawEventListener(this);
		getCM().addRoomEventListener(this);
	}

	public void onDestroy()
	{
		super.onDestroy();
		getCM().removeDrawEventListener(this);
		getCM().removeRoomEventListener(this);
	}

	@Override
	public void onDrawEvent(GameEvent e) 
	{
		// TODO
		// timer
		// end of turn
		// recup score
		// 
		if(!isDrawer)
			dv.update(e.event.picture);
	}

	@Override
	public void onRoomEvent(GameEvent e) 
	{
		String eventDrawerId = e.event.room.drawer_id;
		// start game
		if(drawerId == null)
		{
			drawerId = e.event.room.drawer_id;
		}
		else if(drawerId.equals(eventDrawerId))
		{
			// do nothing
		}
		// end of turn
		else
		{
			startTurn();
		}
		
		getGC().setCurRoom(e.event.room);
	}

}