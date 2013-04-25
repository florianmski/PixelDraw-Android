package com.polytech.polydraw.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.polytech.polydraw.R;
import com.polytech.polydraw.models.Message;

public class ListMessageAdapter extends BaseAdapter
{
	private List<Message> messages;
	private Context context;
	
	public ListMessageAdapter(Context context, List<Message> messages)
	{
		this.context = context;
		this.messages = messages;
	}
	
	public void addMessage(Message message)
	{
		messages.add(message);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() 
	{
		return messages.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return messages.get(position);
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
		
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_message, parent, false);
            holder = new ViewHolder();
            holder.tvPlayer = (TextView)convertView.findViewById(R.id.textViewPlayer);
            holder.tvMessage = (TextView)convertView.findViewById(R.id.textViewMessage);
            
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();
        
        Message m = messages.get(position);
        holder.tvPlayer.setText(m.msg);
        holder.tvMessage.setText(m.msg);
        
		return convertView;
	}
	
    private static class ViewHolder 
    {
    	private TextView tvPlayer;
    	private TextView tvMessage;
    }	
}
