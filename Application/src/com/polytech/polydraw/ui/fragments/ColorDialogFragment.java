package com.polytech.polydraw.ui.fragments;

import java.util.Arrays;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.adapters.GridColorAdapter;

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
	
	private GridView gl;
	private GridColorAdapter adapter;
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
		
		getDialog().setTitle("Choose a color");
		
		gl.setAdapter(adapter = new GridColorAdapter(getActivity(), Arrays.asList(colors)));
		gl.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
			{
				if(listener != null)
					listener.onColorSelected((String) adapter.getItem(position));
				dismiss();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_color, null);
		
		gl = (GridView)v.findViewById(R.id.gridView);
		
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
