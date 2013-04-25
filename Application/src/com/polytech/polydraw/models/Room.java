package com.polytech.polydraw.models;

import java.util.List;

public class Room {
	public String id;
	public String name;
	public int count_player;
	public int max_player;
	public String admin_id;
	public int state;
	public List<Player> players;
	
	public String method;
}
