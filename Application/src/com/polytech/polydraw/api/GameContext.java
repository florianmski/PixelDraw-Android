package com.polytech.polydraw.api;

import java.util.ArrayList;
import com.polytech.polydraw.models.Player;
import com.polytech.polydraw.models.Room;



public class GameContext {
	
	private String player_name;
	private String player_id;
//	private String room_id;
	
	private ArrayList<Player> mPlayerList;
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

//	public String getRoomID() {
//		return room_id;
//	}
//
//	public void setRoomID(String room_id) {
//		this.room_id = room_id;
//	}

	public ArrayList<Player> getPlayerList() {
		return mPlayerList;
	}

	public void setPlayerList(ArrayList<Player> mPlayerList) {
		this.mPlayerList = mPlayerList;
	}

	public Room getCurRoom() {
		return mCurRoom;
	}

	public void setCurRoom(Room mCurRoom) {
		this.mCurRoom = mCurRoom;
	}
}
