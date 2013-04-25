package com.polytech.polydraw.utils;

public class Constants 
{
	public static String BUNDLE_ADMIN_ID = get("AdminId");
	
	private static String get(String name)
	{
		return "com.polytech.polydraw." + name;
	}
}
