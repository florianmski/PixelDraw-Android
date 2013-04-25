package com.polytech.polydraw.models;

import java.util.List;

public class Room {
	public final static int STATE_WAITING = 1;
	public final static int STATE_DRAWER_CHOOSING = 2;
	public final static int STATE_IN_GAME = 3;
	
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
