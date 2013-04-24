package com.polytech.polydraw.ui.fragments;

import com.polytech.polydraw.api.CommunicationManager;
import com.polytech.polydraw.api.GameContext;

import android.app.Fragment;

public class BaseFragment extends Fragment
{
	public GameContext getGC()
	{
		return GameContext.getInstance();
	}
	
	public CommunicationManager getCM()
	{
		return CommunicationManager.getInstance();
	}
}
