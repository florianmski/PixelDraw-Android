package com.polytech.polydraw.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.models.Player;
import com.polytech.polydraw.models.Room;

public class ListPlayerAdapter extends BaseAdapter
{
	private List<Player> players;
	private Context context;
	
	public ListPlayerAdapter(Context context, List<Player> players)
	{
		this.context = context;
		this.players = players;
	}
	
	public void update(List<Player> players)
	{
		this.players = players;
		this.notifyDataSetChanged();
	}
	
	public void addPlayer(Player p)
	{
		players.add(p);
		this.notifyDataSetChanged();
	}
	
	public void removePlayer(Room r)
	{
		players.remove(r);
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
        
        Player p = players.get(position);
        holder.tvPlayer.setText(p.name);
        
		return convertView;
	}
	
    private static class ViewHolder 
    {
    	private TextView tvPlayer;
    }	
}
