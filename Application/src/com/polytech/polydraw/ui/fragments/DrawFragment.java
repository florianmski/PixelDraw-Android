package com.polytech.polydraw.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.fragments.ColorDialogFragment.OnColorListener;
import com.polytech.polydraw.ui.views.CircleColorView;
import com.polytech.polydraw.ui.views.DrawView;

public class DrawFragment extends BaseFragment
{
	private DrawView dv;
	private CircleColorView cd;
	
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_draw, null);
		dv = (DrawView)v.findViewById(R.id.drawView);
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		MenuItem colorItem = menu.add(Menu.NONE, R.id.menu_color, Menu.NONE, "color");
		colorItem.setActionView(cd)
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
					public void onColorSelected(String colorString) 
					{
						dv.setColor(colorString);
						cd.setColor(colorString);
					}
				});
				cdf.show(getFragmentManager(), null);
			}
		});
		
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
	
	
}