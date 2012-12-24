package com.github.mineGeek.LevelRestrictions.Integrators;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;


public class FactionsPlayer {

	public static boolean enabled = false;
	
	public FactionsPlayer()
	{
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Factions");
		
		if (plugin == null || ! plugin.isEnabled())
		{
			Bukkit.getLogger().info("Factions integration not enabled");
			enabled = false;

		} else {
			Bukkit.getLogger().info("Factions integration is enabled");
			enabled = true;
		}
		
	}
	
	public static Boolean isInFaction( Player player, List<String> factions ) {
		
		if ( !enabled || factions == null ) return false;
		FPlayer p = FPlayers.i.get(player);
		String o = p.getFaction().getTag();
		Boolean b =  factions.contains(p.getFaction().getTag() );
		return b;
		
	}
	
	public static Boolean isFaction( Player player, String factionName ) {
		FPlayer p = FPlayers.i.get( player );
		return ( p.getFactionId() == factionName );
	}
	
}
