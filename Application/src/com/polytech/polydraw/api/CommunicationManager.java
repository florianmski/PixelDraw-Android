package com.polytech.polydraw.api;
import java.util.HashMap;

import android.util.Log;
import de.tavendo.autobahn.Wamp.CallHandler;
import de.tavendo.autobahn.Wamp.EventHandler;
import de.tavendo.autobahn.WampConnection;
import de.tavendo.autobahn.WampConnectionHandler;


public class CommunicationManager{
	private final String TAG = "CommunicationManager";
	private final WampConnection mConnection;
	private final String mWSURI = "ws://88.191.157.29:8080";
	
	private CommunicationManager(){
		mConnection = new WampConnection();
	}
	
	private static CommunicationManager mThis;
	
	public static CommunicationManager getInstance(){
		if(mThis == null){
			mThis = new CommunicationManager();
		}
		return mThis;
	}
	
    /**
     * To be call in the first activity of the game to start connection
     */
	public void startConnection(final String player_name, final CallHandler playerIDHandler){
		mConnection.connect(mWSURI, new WampConnectionHandler(){
			@Override
		    public void onOpen() {
		       Log.d(TAG, "Status: Connected to " + mWSURI);
		       mConnection.prefix("event", "http://88.191.157.29:8080/events");
		       mConnection.prefix("call", "http://88.191.157.29:8080/call");
		       HashMap<String, String> key = new HashMap<String, String>();
		       key.put("name", player_name);
		       mConnection.call("login", 
						HashMap.class, 
						playerIDHandler, 
						key);
		    }
		    @Override
		    public void onClose(int code, String reason) {
		       Log.d(TAG, "Connection lost.");
		    }
		});
	}
	
	public void createRoom(final String room_name, CallHandler createRoomHandler){
		HashMap<String,String> key = new HashMap<String,String>();
		key.put("room_name", room_name);
		mConnection.call("create_room", HashMap.class, createRoomHandler, room_name);
	}
	
	/**
	 * Join Room state
	 * @param getRoomListHandler
	 */
	public void getRoomList(CallHandler getRoomListHandler){
		mConnection.call("get_room_list", HashMap.class, getRoomListHandler);
	}
	
	/**
	 * Join Room state
	 * @param joinRoomHandler
	 */
	public void joinRoom(CallHandler joinRoomHandler){
		mConnection.call("join_room", HashMap.class, joinRoomHandler);
	}
	
	private final String chatUri = "chat";
	public void sendChatMessage(String message){
		mConnection.publish(chatUri, message);
	}
	public void subscribeChat(EventHandler chatHandler){
		mConnection.subscribe(chatUri, HashMap.class, chatHandler);
	}
	
	public void unsubscribeChat(){
		mConnection.unsubscribe(chatUri);
	}
	
	/**
	 * To be called by the host in the waiting room when he wants to launch the game
	 * @param startGameHandler
	 */
	public void startGame(CallHandler startGameHandler){
		mConnection.call("call_join_room", HashMap.class, startGameHandler);
	}
	
	
}
