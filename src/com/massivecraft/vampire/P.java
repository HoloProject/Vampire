package com.massivecraft.vampire;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.massivecraft.mcore3.MPlugin;
import com.massivecraft.vampire.cmd.CmdBase;
import com.massivecraft.vampire.cmdarg.AHVPlayer;
import com.massivecraft.vampire.keyboard.BloodlustToggle;
import com.massivecraft.vampire.keyboard.Shriek;

public class P extends MPlugin 
{
	// Our single plugin instance
	public static P p;
	
	// Command
	public CmdBase cmdBase;
	
	// noCheatExemptedPlayerNames
	// http://dev.bukkit.org/server-mods/nocheatplus/
	// https://gist.github.com/2638309
	public Set<String> noCheatExemptedPlayerNames = new HashSet<String>();
	
	public P()
	{
		P.p = this;
	}

	@Override
	public void onEnable()
	{
		if ( ! preEnable()) return;
		
		// Create and load VPlayers
		VPlayers.i.loadOldFormat();
		
		// Load Conf from disk
		Conf.load();
		Conf.save();
		Lang.load();
		Lang.save();
		
		// Add Base Commands
		this.cmdBase = new CmdBase();
		this.cmdBase.register();
		
		// Add Argument Handlers
		this.cmd.setArgHandler(VPlayer.class, AHVPlayer.getInstance());
		
		// Start timer
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TheTask(), 0, Conf.taskInterval);
	
		// Register events
		new TheListener(this);
		this.noCheatPlusSetup();
		
		// Register Key Bindings
		BloodlustToggle.get().register();
		Shriek.get().register();
		
		postEnable();
	}
	
	protected void noCheatPlusSetup()
	{
		Plugin noCheatPlus = Bukkit.getPluginManager().getPlugin("NoCheatPlus");
		if (noCheatPlus == null) return;
		if (noCheatPlus.isEnabled() == false) return;
		try
		{
			new NoCheatPlusListener(this);
		}
		catch (Exception e)
		{
			log("NoCheatPlus integration failed :( this Exception was raised:");
			e.printStackTrace();
			return;
		}
		log("NoCheatPlus integration successful :)");
	}
}