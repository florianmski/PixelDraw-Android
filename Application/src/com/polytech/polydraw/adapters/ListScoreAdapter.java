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
import com.polytech.polydraw.models.Player;


public class ListScoreAdapter extends BaseAdapter
{	
	private List<Player> players = new ArrayList<Player>();
	private Context context;

	public ListScoreAdapter(Context context, List<Player> players)
	{
		this.context = context;
		this.players = players;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_score, parent, false);
			holder = new ViewHolder();
			holder.tvPlayer = (TextView)convertView.findViewById(R.id.textViewPlayer);
			holder.tvScore = (TextView)convertView.findViewById(R.id.textViewScore);

			convertView.setTag(holder);
		} 
		else
			holder = (ViewHolder) convertView.getTag();

		Player p = players.get(position);
		holder.tvPlayer.setText(p.name);
		holder.tvScore.setText(String.valueOf(p.score));

		return convertView;
	}

	private static class ViewHolder 
	{
		private TextView tvPlayer;
		private TextView tvScore;
	}	
}
