package com.polytech.polydraw.adapters;

import java.util.ArrayList;
import java.util.List;

import com.polytech.polydraw.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListGameAdapter extends BaseAdapter{

	
	private List<String> games = new ArrayList<String>();
	private Context context;
	
	public ListGameAdapter(Context context, List<String> games)
	{
		this.context = context;
		this.games = games;
	}
	
	public void addMessage(String message)
	{
		games.add(message);
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
        
        String message = games.get(position);
        holder.tvGame.setText(message);
        
		return convertView;
	}
	
    private static class ViewHolder 
    {
    	private TextView tvGame;
    	private TextView tvNbPlayer;
    }	
}
