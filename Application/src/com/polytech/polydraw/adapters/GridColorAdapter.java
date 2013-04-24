package com.polytech.polydraw.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.views.CircleColorView;

public class GridColorAdapter extends BaseAdapter
{
	private List<String> colors = new ArrayList<String>();
	private Context context;
	
	public GridColorAdapter(Context context, List<String> colors)
	{
		this.context = context;
		this.colors = colors;
	}

	@Override
	public int getCount() 
	{
		return colors.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return colors.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		final ViewHolder holder;

        if (convertView == null) 
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_color, parent, false);
            holder = new ViewHolder();
            holder.ccv = (CircleColorView)convertView.findViewById(R.id.circleColorView);
            
            convertView.setTag(holder);
        } 
        else
            holder = (ViewHolder) convertView.getTag();
        
        String colorString = colors.get(position);
        holder.ccv.setColor(colorString);
        
		return convertView;
	}
	
    private static class ViewHolder 
    {
    	private CircleColorView ccv;
    }	
}
