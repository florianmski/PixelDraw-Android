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
	private final static Integer[] colors = new Integer[]{
		0xff51574a,
		0xff447c69,
		0xff74c493,
		0xff8e8c6d,
		0xffe4bf80,
		0xffe9d78e,
		0xffe2975d,
		0xfff19670,
		0xffe16552,
		0xffc94a53,
		0xffbe5168,
		0xffa34974,
		0xff993767,
		0xff65387d,
		0xff4e2472,
		0xff9163b6,
		0xffe279a3,
		0xffe0598b,
		0xff7c9fb0,
		0xff5698c4,
		0xff9abf88,
		0xffffffff
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
					listener.onColorSelected(adapter.getItem(position));
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
		public void onColorSelected(int colorString);
	}
}
