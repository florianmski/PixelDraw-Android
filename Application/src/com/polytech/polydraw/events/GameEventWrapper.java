package com.polytech.polydraw.events;

import java.util.ArrayList;
import java.util.List;

import com.polytech.polydraw.models.Player;
import com.polytech.polydraw.models.Room;

public class GameEventWrapper {
	public String player_id;
	public String msg;
	public Room room;
	public ArrayList<Player> player_list;
	public List<Integer> picture;
}
