package com.polytech.polydraw.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.polytech.polydraw.R;

public class ListPlayerAdapter extends BaseAdapter{
	private List<String> players = new ArrayList<String>();
	private Context context;
	
	public ListPlayerAdapter(Context context, List<String> players)
	{
		this.context = context;
		this.players = players;
	}
	
	public void addMessage(String message)
	{
		players.add(message);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() 
	{
		return players.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return players.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_player, parent, false);
            holder = new ViewHolder();
            holder.tvPlayer = (TextView)convertView.findViewById(R.id.tvPlayer);
            convertView.setTag(holder);
        } 
        else
            holder = (ViewHolder) convertView.getTag();
        
        String message = players.get(position);
        holder.tvPlayer.setText(message);
        
		return convertView;
	}
	
    private static class ViewHolder 
    {
    	private TextView tvPlayer;
    }	
}
