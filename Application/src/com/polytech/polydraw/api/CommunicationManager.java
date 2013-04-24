package com.polytech.polydraw.api;
import java.util.ArrayList;
import java.util.HashMap;

import com.polytech.polydraw.models.DrawEventListener;
import com.polytech.polydraw.models.GameDrawEvent;
import com.polytech.polydraw.models.GameEvent;
import com.polytech.polydraw.models.GamePlayerEvent;
import com.polytech.polydraw.models.GameRoomEvent;
import com.polytech.polydraw.models.GameServerEvent;
import com.polytech.polydraw.models.Player;
import com.polytech.polydraw.models.PlayerEventListener;
import com.polytech.polydraw.models.Room;
import com.polytech.polydraw.models.RoomEventListener;
import com.polytech.polydraw.models.RoomList;
import com.polytech.polydraw.models.ServerEventListener;

import android.util.Log;
import de.tavendo.autobahn.Wamp.CallHandler;
import de.tavendo.autobahn.Wamp.EventHandler;
import de.tavendo.autobahn.WampConnection;
import de.tavendo.autobahn.WampConnectionHandler;


public class CommunicationManager{
	private final String TAG = "CommunicationManager";
	private final WampConnection mConnection;
	private final String mWSURI = "ws://88.191.157.29:8080";
	private GameContext mGameContext = GameContext.getInstance();
	
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
						Player.class, 
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
		mConnection.call("create_room", Room.class, createRoomHandler, room_name);
	}
	
	/**
	 * Join Room state
	 * @param getRoomListHandler
	 */
	public void getRoomList(CallHandler getRoomListHandler){
		mConnection.call("get_room_list", RoomList.class, getRoomListHandler);
	}
	
	/**
	 * Join Room state
	 * @param joinRoomHandler
	 */
	public void joinRoom(int room_id, CallHandler joinRoomHandler){
		HashMap<String, Integer> key = new HashMap<String, Integer>();
		key.put("room_id", room_id);
		mConnection.call("join_room", HashMap.class, joinRoomHandler);
	}
	
	
	
	public void sendChatMessage(String message){
		mConnection.publish(mGameContext.getRoomID() , message);
	}
	public void subscribeGame(){
		mConnection.subscribe(mGameContext.getRoomID(), GameEvent.class, mGameEventHandler);
	}
	
	public void unsubscribeGame(){
		mConnection.unsubscribe(mGameContext.getRoomID());
	}
	
	/**
	 * To be called by the host in the waiting room when he wants to launch the game
	 * @param startGameHandler
	 */
	public void startGame(CallHandler startGameHandler){
		mConnection.call("call_join_room", HashMap.class, startGameHandler);
	}
	
	//Listener for chat message
	private ArrayList<PlayerEventListener> mPlayerEventListeners;
	//Listener for server message
	private ArrayList<ServerEventListener> mServerEventListeners;
	//Listener for room event (connection/disconnection/host change/...)
	private ArrayList<RoomEventListener> mRoomEventListeners;
	//Listener for picture refresh
	private ArrayList<DrawEventListener> mDrawEventListeners;
	
	public void addPlayerEventListener(PlayerEventListener l){
		mPlayerEventListeners.add(l);
	}
	
	public void removePlayerEventListener(PlayerEventListener l){
		mPlayerEventListeners.remove(l);
	}
	
	public void removeAllPlayerEventListener(PlayerEventListener l){
		mPlayerEventListeners.clear();
	}
	
	public void addServerEventListener(ServerEventListener l){
		mServerEventListeners.add(l);
	}
	
	public void removeGameEventListener(ServerEventListener l){
		mServerEventListeners.remove(l);
	}
	
	public void removeAllGameEventListener(ServerEventListener l){
		mServerEventListeners.clear();
	}
	
	public void addRoomEventListener(RoomEventListener l){
		mRoomEventListeners.add(l);
	}
	
	public void removeRoomEventListener(RoomEventListener l){
		mPlayerEventListeners.remove(l);
	}
	
	public void removeAllRoomEventListener(RoomEventListener l){
		mPlayerEventListeners.clear();
	}
	
	public void addDrawEventListener(DrawEventListener l){
		mDrawEventListeners.add(l);
	}
	
	public void removeDrawEventListener(DrawEventListener l){
		mPlayerEventListeners.remove(l);
	}
	
	public void removeAllDrawEventListener(DrawEventListener l){
		mPlayerEventListeners.clear();
	}
	
	EventHandler mGameEventHandler = new EventHandler(){

		@Override
		public void onEvent(String topicUri, Object event) {
			GameEvent gameEvent = (GameEvent) event;
			switch(gameEvent.type){
			case 0:
				for(PlayerEventListener l : mPlayerEventListeners){
					l.onEvent((GamePlayerEvent) event);
				}
				break;
			case 1:
				for(ServerEventListener l : mServerEventListeners){
					l.onEvent((GameServerEvent) event);
				}
				break;
			case 2:
				for(RoomEventListener l : mRoomEventListeners){
					l.onEvent((GameRoomEvent) event);
				}
				break;
			case 3:
				for(DrawEventListener l : mDrawEventListeners){
					l.onEvent((GameDrawEvent) event);
				}
				break;
			default:
				Log.e("CommunicationManager", "Unhandle event received");
			}
			
		}
	};
	
	
}
