package com.polytech.polydraw.ui.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.views.CircleColorView;

public class ColorDialogFragment extends DialogFragment
{
	private final static String[] colors = new String[]{
		"#51574a",
		"#447c69",
		"#74c493",
		"#8e8c6d",
		"#e4bf80",
		"#e9d78e",
		"#e2975d",
		"#f19670",
		"#e16552",
		"#c94a53",
		"#be5168",
		"#a34974",
		"#993767",
		"#65387d",
		"#4e2472",
		"#9163b6",
		"#e279a3",
		"#e0598b",
		"#7c9fb0",
		"#5698c4",
		"#9abf88",
		"#ffffff"
	};
	
	private GridLayout gl;
	private OnColorListener listener;
	
	public static ColorDialogFragment newInstance()
	{
		ColorDialogFragment f = new ColorDialogFragment();
		return f;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		
		for(final String s : colors)
		{
			CircleColorView cd = new CircleColorView(getActivity(), s);
			cd.setOnClickListener(new OnClickListener() 
			{	
				@Override
				public void onClick(View v) 
				{
					if(listener != null)
						listener.onColorSelected(s);
					dismiss();
				}
			});
			gl.addView(cd, 150, 150);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_color, null);
		
		gl = (GridLayout)v.findViewById(R.id.gridLayout);
		
		return v;
	}
	
	public void setOnColorListener(OnColorListener listener)
	{
		this.listener = listener;
	}
	
	public interface OnColorListener
	{
		public void onColorSelected(String colorString);
	}
}
