package com.github.mineGeek.LevelRestrictions.DataStore;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;

public class PlayerStore {

	private Map<String, PlayerStoreItem>_players = new HashMap<String, PlayerStoreItem>();
	public String dataFolder;
	public Boolean active = false;	
	private LevelRestrictions plugin;
	
	public PlayerStore( LevelRestrictions plugin ) {
		this.plugin = plugin;
	}
	
	public PlayerStoreItem player( Player player ) {
		return this.player( player.getName() );
	}
	
	public PlayerStoreItem player( String playerName ) {
		return _players.get( playerName );
	}
	
	public void addPlayer( Player player ) {
		_players.put(player.getName(), new PlayerStoreItem( this.plugin, player ) );
	}
	
	public void removePlayer( Player player ) {
		_players.get(player.getName()).save();
		_players.remove( player.getName() );
	}
	
	public void loadOnline() {
		
		if ( this.plugin.getServer().getOnlinePlayers().length > 0 ) {
		
			for( Player p : this.plugin.getServer().getOnlinePlayers() ) {
				this.addPlayer( p );
			}
			
		}
	}
	
	public void saveOnline() {
		
		if ( this.plugin.getServer().getOnlinePlayers().length > 0 ) {
		
			for( Player p : this.plugin.getServer().getOnlinePlayers() ) {
				this.player( p.getName() ).save() ;
			}
			
		}
	}	
	
	
}
