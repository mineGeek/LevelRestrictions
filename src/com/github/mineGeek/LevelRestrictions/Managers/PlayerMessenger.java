package com.github.mineGeek.LevelRestrictions.Managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerMessenger {

	JavaPlugin _plugin;
	private static Map<String, Map<String, Long>> _queue = new HashMap<String, Map<String, Long>>();

	
	
	public PlayerMessenger( JavaPlugin thisPlugin ) {
		 _plugin = thisPlugin;
	}
	
	/**
	 * Sends a message to the player but prevents spamming it to them
	 * @param player
	 * @param message
	 */
	public static void SendPlayerMessage(Player player, String message ) {
		
		Boolean printMessage = true;
		//TODO: Make this a setting
		Long benchMark = System.currentTimeMillis() - 1000;
		
		Map<String, Long> map = new HashMap<String, Long>();
		
		if ( _queue.containsKey( player.getName() ) ) {
			if ( _queue.get( player.getName() ).containsKey( message ) ) {
				if ( _queue.get(player.getName()).get(message).longValue() > benchMark ) {
					printMessage = false;
				}
				map = _queue.get(player.getName());
			}
		}
		
		map.put( message, System.currentTimeMillis() );
		_queue.put(player.getName(), map );
		
		if ( printMessage == true ) {
			player.sendMessage( message );
		}
		
		//TODO: Make this better!
		if ( map.size() > 10 ) {
			clean();
		}
		
	}
	
	private static void clean() {
		
		Iterator<Entry<String, Map<String, Long>>> i = _queue.entrySet().iterator();
		
		while ( i.hasNext() ) {
			
			Entry<String, Map<String, Long>> o = i.next();
			
			Iterator<Entry<String, Long>> x = o.getValue().entrySet().iterator();
			
			while ( x.hasNext() ) {
				
				Map.Entry<String, Long> y = x.next();
				
				if ( y.getValue() < System.currentTimeMillis() - 5000 ) {
					_queue.remove( o.getKey() );
				}
				
			}
			
		}
		
	}
}