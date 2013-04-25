package com.polytech.polydraw.api;

import java.util.HashMap;
import java.util.List;

import com.polytech.polydraw.models.Player;
import com.polytech.polydraw.models.Room;

public class GameContext {
	
	private String player_name;
	private String player_id;	
	private HashMap<String, Player> mPlayerRegister = new HashMap<String, Player>();
	private Room mCurRoom;
	private static GameContext mThis;
	
	private GameContext(){
		setPlayerName(new String());
		setPlayerID(new String());
//		setRoomID(new String());
	}
	
	public static GameContext getInstance(){
		if(mThis == null){
			mThis = new GameContext();
		}
		return mThis;
	}
	
	public String getPlayerName() {
		return player_name;
	}

	public void setPlayerName(String player_name) {
		this.player_name = player_name;
	}

	public String getPlayerID() {
		return player_id;
	}

	public void setPlayerID(String player_id) {
		this.player_id = player_id;
	}

	public HashMap<String, Player> getPlayerList() {
		return mPlayerRegister;
	}

	public void setPlayerList(List<Player> playerList) {
		for(int i=0; i<playerList.size();i++){
			this.mPlayerRegister.put(playerList.get(i).id, playerList.get(i));
		}
	}

	public Room getCurRoom() {
		return mCurRoom;
	}

	public void setCurRoom(Room mCurRoom) {
		this.mCurRoom = mCurRoom;
	}
}
