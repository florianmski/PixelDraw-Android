package com.polytech.polydraw.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.polytech.polydraw.events.GameEvent;
import com.polytech.polydraw.events.GameEventWrapper;
import com.polytech.polydraw.listeners.DrawEventListener;
import com.polytech.polydraw.listeners.PlayerEventListener;
import com.polytech.polydraw.listeners.RoomEventListener;
import com.polytech.polydraw.listeners.ServerEventListener;
import com.polytech.polydraw.models.Category;
import com.polytech.polydraw.models.Pixel;
import com.polytech.polydraw.models.Wrapper;
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
	
	private CloseConnectionHandler mCloseConnectionHandler;
	public void setConnectionCloseListener(CloseConnectionHandler l){
		mCloseConnectionHandler = l;
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
		       mConnection.call("login", Wrapper.class, playerIDHandler, key);
		    }
		    @Override
		    public void onClose(int code, String reason) {
		       Log.d(TAG, "Connection lost.");
		       mCloseConnectionHandler.onConnectionClose(code, reason);
		    }
		});
	}
	
	public void createRoom(final String room_name, CallHandler createRoomHandler){
		HashMap<String,String> key = new HashMap<String,String>();
		key.put("room_name", room_name);
		mConnection.call("create_room", Wrapper.class, createRoomHandler, key);
	}
	
	/**
	 * Join Room state
	 * @param getRoomListHandler
	 */
	public void getRoomList(CallHandler getRoomListHandler){
		mConnection.call("get_room_list", Wrapper.class, getRoomListHandler);
	}
	
	/**
	 * Join Room state. 
	 * Don't forget to register roomID in game context during callback
	 * @param joinRoomHandler
	 */
	public void joinRoom(String room_id, CallHandler joinRoomHandler){
		HashMap<String, String> key = new HashMap<String, String>();
		key.put("room_id", room_id);
		mConnection.call("join_room", Wrapper.class, joinRoomHandler, key);
		
	}
	
	public void leaveRoom(CallHandler leaveRoomHandler){
		HashMap<String, String> key = new HashMap<String, String>();
		key.put("room_id", mGameContext.getCurRoom().id);
		mConnection.call("leave_room", Wrapper.class, leaveRoomHandler, key);
	}
	
	public void getCategories(CallHandler categoriesHandler){
		mConnection.call("get_categories", Wrapper.class, categoriesHandler);
	}
	
	public void getWord(Category chosenCategory, CallHandler wordHandler){
		HashMap<String, Integer> key = new HashMap<String, Integer>();
		key.put("category_id", chosenCategory.id);
		mConnection.call("get_word", Wrapper.class, wordHandler, key);
	}
	
	public void sendChatMessage(String message){
		String room = mGameContext.getCurRoom().id;
		
		GameEventWrapper ev = new GameEventWrapper();
		ev.player_id = mGameContext.getPlayerID();
		ev.msg = message;
		
		GameEvent eve = new GameEvent();
		eve.type = 0;
		eve.time_stamp = System.currentTimeMillis();
		eve.event = ev;
		mConnection.publish(room, eve);
	}

	public void sendDrawMessage(ArrayList<Pixel> picture){
		String room = mGameContext.getCurRoom().id;
		
		GameEventWrapper core = new GameEventWrapper();
		core.player_id = mGameContext.getPlayerID();
		core.picture = picture;
		
		GameEvent event = new GameEvent();
		event.type = 0;
		event.time_stamp = System.currentTimeMillis();
		event.event = core;
		mConnection.publish(room, event);
	}
	
	/**
	 * To call once we join a room
	 */
	public void subscribeGame(){
		mConnection.subscribe(mGameContext.getCurRoom().id, GameEvent.class, mGameEventHandler);
	}
	
	public void unsubscribeGame(){
		mConnection.unsubscribe(mGameContext.getCurRoom().id);
	}
	
	/**
	 * To be called by the host in the waiting room when he wants to launch the game
	 * @param startGameHandler
	 */
	public void startGame(CallHandler startGameHandler){
		mConnection.call("call_join_room", HashMap.class, startGameHandler);
	}
	
	//Listener for chat message
	private List<PlayerEventListener> mPlayerEventListeners = new ArrayList<PlayerEventListener>();
	//Listener for server message
	private List<ServerEventListener> mServerEventListeners = new ArrayList<ServerEventListener>();
	//Listener for room event (connection/disconnection/host change/...)
	private List<RoomEventListener> mRoomEventListeners = new ArrayList<RoomEventListener>();
	//Listener for picture refresh
	private List<DrawEventListener> mDrawEventListeners = new ArrayList<DrawEventListener>();
	
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
	
	public void removeServerEventListener(ServerEventListener l){
		mServerEventListeners.remove(l);
	}
	
	public void removeAllServerEventListener(ServerEventListener l){
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
			GameEvent gameEventWrapper = (GameEvent) event;
			switch(gameEventWrapper.type){
			case 0:
				for(PlayerEventListener l : mPlayerEventListeners){
					l.onPlayerEvent(gameEventWrapper);
				}
				break;
			case 1:
				for(ServerEventListener l : mServerEventListeners){
					l.onServerEvent(gameEventWrapper);
				}
				break;
			case 2:
				for(RoomEventListener l : mRoomEventListeners){
					l.onRoomEvent(gameEventWrapper);
				}
				break;
			case 3:
				for(DrawEventListener l : mDrawEventListeners){
					l.onDrawEvent(gameEventWrapper);
				}
				break;
			default:
				Log.e("CommunicationManager", "Unhandle event received");
			}
		}
	};
}
