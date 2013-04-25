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


public class ListScoreAdapter extends BaseAdapter
{	
	private List<String> scores = new ArrayList<String>();
	private Context context;

	public ListScoreAdapter(Context context, List<String> scores)
	{
		this.context = context;
		this.scores = scores;
	}

	public void addMessage(String message)
	{
		scores.add(message);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() 
	{
		return scores.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return scores.get(position);
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

		String message = scores.get(position);
		holder.tvScore.setText(message);

		return convertView;
	}

	private static class ViewHolder 
	{
		private TextView tvPlayer;
		private TextView tvScore;
	}	
}
