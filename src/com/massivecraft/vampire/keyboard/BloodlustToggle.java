package com.massivecraft.vampire.keyboard;

import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.massivecraft.vampire.VPlayer;

public class BloodlustToggle extends VampireKeyBinding 
{
	@Override
	public void pressed(KeyBindingEvent event, SpoutPlayer splayer, VPlayer vplayer)
	{
		vplayer.bloodlust( ! vplayer.bloodlust());
	}
	
	// The Single Instance
	private BloodlustToggle()
	{
		this.defaultKey = Keyboard.KEY_V;
	}
	private static BloodlustToggle instance = new BloodlustToggle();
	public static BloodlustToggle get() { return instance; } 
}
