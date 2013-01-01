package com.github.mineGeek.LevelRestrictions.Integrators;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;



public class FactionsPlayer {

	public static boolean enabled = false;
	
	public static void FactionsPlayerEnable()
	{
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Factions");
		
		if (plugin == null || ! plugin.isEnabled())
		{
			Bukkit.getLogger().info("Factions integration for LevelRestrictions not enabled");
			enabled = false;

		} else {
			Bukkit.getLogger().info("Factions integration for LevelRestrictions is enabled");
			enabled = true;
		}
		
	}
	
	public static Boolean isInFaction( Player player, List<String> factions ) {
		
		if ( !enabled || factions == null ) return false;
		return  factions.contains( FPlayers.i.get(player).getFaction().getTag() );
		
	}
	
	public static Boolean isFaction( Player player, String factionName ) {
		FPlayer p = FPlayers.i.get( player );
		return ( p.getFactionId() == factionName );
	}
	
	public static Double getPower( Player player ) {
		FPlayer p = FPlayers.i.get( player );
		return p.getPower();
	}
	
	public static Boolean isPowerMin( Player player, Integer power ) {
		FPlayer p = FPlayers.i.get( player );
		return p.getPowerMinRounded() > power;
	}
	
	public static Boolean isPowerMax( Player player, Integer power ) {
		FPlayer p = FPlayers.i.get( player );
		return p.getPowerMaxRounded() > power;
	}	
	

	
}
