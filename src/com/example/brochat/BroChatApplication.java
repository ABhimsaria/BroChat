package com.example.brochat;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class BroChatApplication extends Application {
	
	@Override
	public void onCreate() { 
		super.onCreate();
	    Parse.initialize(this, "hGgdFlFvrCe5doB9xEC01ZADc5nqgyZyYy5cqqdg", "ABTltvqq45q1atgRCZhd2pKa2tiesesM0jMob9DH");
	}
}
