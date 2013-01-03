package com.github.mineGeek.LevelRestrictions.DataStore;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;

public class PlayerStore {

	private static 	Map<String, PlayerStoreItem>players = new HashMap<String, PlayerStoreItem>();
	public 	static 	String dataFolder;
	public 	static 	Boolean active = false;	
	private static 	LevelRestrictions plugin;
	
	public static void setPlugin( LevelRestrictions plugin ) {
		PlayerStore.plugin = plugin;
	}
	
	public static PlayerStoreItem player( Player player ) {
		return player( player.getName() );
	}
	
	public static PlayerStoreItem player( String playerName ) {
		return players.get( playerName );
	}
	
	public static void addPlayer( Player player ) {
		players.put( player.getName(), new PlayerStoreItem( plugin, player ) );
	}
	
	public static void removePlayer( Player player ) {
		players.get(player.getName()).save();
		players.remove( player.getName() );
	}
	
	public static void loadOnline() {
		
		if ( plugin.getServer().getOnlinePlayers().length > 0 ) {
		
			for( Player p : plugin.getServer().getOnlinePlayers() ) {
				addPlayer( p );
			}
			
		}
	}
	
	public static void saveOnline() {
		
		if ( plugin.getServer().getOnlinePlayers().length > 0 ) {
		
			for( Player p : plugin.getServer().getOnlinePlayers() ) {
				player( p.getName() ).save() ;
			}
			
		}
	}	
	
	
}
