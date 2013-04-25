package com.polytech.polydraw.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ErrorHandler 
{
	public static void display(Context context, String errorUri, String errorDesc)
	{
		Log.e("POLYDRAW", "errorUri : " + errorUri + " | errorDesc : " + errorDesc);
		Toast.makeText(context, errorDesc, Toast.LENGTH_SHORT).show();
	}
}
