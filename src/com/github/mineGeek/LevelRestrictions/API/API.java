package com.github.mineGeek.LevelRestrictions.API;

import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.DataStore.PlayerStore;

public class API {
	
	
	public static void incimentPlayerItemLevel( Player player ) {
		
		PlayerStore.player(player).incrimentLevel();
		
	}
	
	public static void incimentPlayerItemLevel( Player player, Integer amount ) {
		
		PlayerStore.player(player).incrimentLevel( amount );
		
	}
	
	public static void setPlayerItemLevel( Player player, Integer amount ) {
		
		PlayerStore.player(player).setLevel( amount );
		
	}
	
}
