package com.polytech.polydraw.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.models.Room;

public class ListGameAdapter extends BaseAdapter
{	
	private List<Room> games;
	private Context context;
	
	public ListGameAdapter(Context context, List<Room> games)
	{
		this.context = context;
		this.games = games;
	}
	
	public void addRoom(Room r)
	{
		games.add(r);
		this.notifyDataSetChanged();
	}
	
	public void removeRoom(Room r)
	{
		games.remove(r);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() 
	{
		return games.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return games.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_game, parent, false);
            holder = new ViewHolder();
            holder.tvGame = (TextView)convertView.findViewById(R.id.tvGame);
            holder.tvNbPlayer = (TextView)convertView.findViewById(R.id.tvNbPlayer);
            
            convertView.setTag(holder);
        } 
        else
            holder = (ViewHolder) convertView.getTag();
        
        Room r = games.get(position);
        holder.tvGame.setText(r.name);
        
		return convertView;
	}
	
    private static class ViewHolder 
    {
    	private TextView tvGame;
    	private TextView tvNbPlayer;
    }	
}
