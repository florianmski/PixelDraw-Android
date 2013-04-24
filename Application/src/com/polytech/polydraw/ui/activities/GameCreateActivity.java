package com.polytech.polydraw.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.polytech.polydraw.R;
import com.polytech.polydraw.ui.fragments.GameCreateFragment;


public class GameCreateActivity {
	
	public static void launch(final Activity a)
	{
		//Intent i = new Intent(a, GameCreateActivity.class);
		//a.startActivity(i);
		
		String title = "Enter a room name";
		
		//On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(a);
        final View alertDialogView = factory.inflate(R.layout.fragment_game_create, null);
 
        //Création de l'AlertDialog
        AlertDialog.Builder adb = new AlertDialog.Builder(a);
        adb.setView(alertDialogView);
        adb.setTitle(title);
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
 
            	EditText edt = (EditText)alertDialogView.findViewById(R.id.edtPseudo);		            	
            	WaitingRoomActivity.launch(a);         	
            }});
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub	
			}
		});
           
        adb.show();	
	}
			
}
