package com.polytech.polydraw.models;

import java.util.List;

public class Room {
	public String id;
	public String name;
	public int count_player;
	public int max_player;
	public String drawer_id;
	public int state;
	public List<Player> players;
	public long ended_at;
	public String category_name;
	
	public String method;
}
