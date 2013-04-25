package com.polytech.polydraw.api;

public interface CloseConnectionHandler {
	public void onConnectionClose(int code,String reason);
}
