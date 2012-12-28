package com.github.mineGeek.LevelRestrictions.Utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;

import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.Rules.iRule;

public class Info {

	LevelRestrictions plugin;
	
	public Info( LevelRestrictions plugin ) {
		this.plugin = plugin;
	}
	
	public static enum RestrictionDisplayOptions { CAN, CAN_ALL, CAN_PREVIOUS, CAN_NEXT, CAN_CURRENT, CANT, CANT_ALL, CANT_PREVIOUS, CANT_NEXT, CANT_CURRENT };
	
	/**
	 * Splits getPlayerRestrictions List Into a single string
	 * @param list
	 * @param prefix
	 * @param color
	 * @param suffix
	 * @return
	 */
	private String getDisplayList( List<String> list, String prefix, ChatColor color ) {
		
		
		String message = "";
		String next = "";
		
		if ( list == null ) return message;
		
		Iterator<String> i = list.iterator();
		
		while ( i.hasNext() ) {
			
			next = i.next();
			
			if ( message.length() > 0 ) {
			
				if ( i.hasNext() ) {
					message = message.concat( ", " );
				} else if ( !i.hasNext() ) {
					message = message.concat( " and ");
				}
				
			}
			
			message = message.concat( next );
						
		}
		
		if ( message.length() > 0 ) {
			
			if ( prefix.length() > 0 ) message = prefix.concat( " " ) + message;
			
		
		}
		
		if ( message.length() > 0 ) {
			return color + message;
		} else {
			return message;
		}
		
		
	}
	
		
	public String getPlayerRestrictionsCurrent( Player player, Boolean canDos ) {
		
		String prefix = canDos ? this.plugin.config.candoNowPrefix : this.plugin.config.candoNextPrefix;
		ChatColor color = canDos ? ChatColor.GREEN : ChatColor.YELLOW;
		return this.getDisplayList( this.getPlayerRestrictions( player, this.plugin.players.player(player).getLevel(), canDos, this.plugin.players.player(player).getLevel() + 1 ) , prefix , color);
		
	}
	
	public String getPlayerRestrictionsPrevious( Player player, Boolean canDos ) {
		
		String prefix = canDos ? this.plugin.config.candoNowPrefix : this.plugin.config.cantdoPrefix;
		ChatColor color = canDos ? ChatColor.GREEN : ChatColor.RED;		
		return this.getDisplayList( this.getPlayerRestrictions( player, this.plugin.players.player(player).getPreviousLevel(), canDos ) , prefix, color);
		
	}
	
	public String getPlayerRestrictionsNext( Player player, Boolean canDos ) {
		
		String prefix = canDos ? this.plugin.config.candoNowPrefix : this.plugin.config.candoNextPrefix;
		ChatColor color = canDos ? ChatColor.YELLOW : ChatColor.RED;		
		return this.getDisplayList( this.getPlayerRestrictions( player, this.plugin.players.player(player).getLevel() + 1, canDos ) , prefix, color);
		
	}
	
	public String getPlayerRestrictionsAll( Player player, Boolean canDos ) {
		
		String prefix = canDos ? this.plugin.config.candoNowPrefix : this.plugin.config.cantdoPrefix;
		ChatColor color = canDos ? ChatColor.GREEN : ChatColor.RED;		
		return this.getDisplayList( this.getPlayerRestrictions( player, this.plugin.players.player(player).getLevel(), canDos, true, 0 ) , prefix, color);
		
	}	
	
	public List<String> getPlayerRestrictions( Player player, Integer level, Boolean canDos, Integer nextLevel ) {
		return this.getPlayerRestrictions(player, level, canDos, false, nextLevel );
	}	
	
	public List<String> getPlayerRestrictions( Player player, Integer level, Boolean canDos ) {
		return this.getPlayerRestrictions(player, level, canDos, false, 0 );
	}

	public List<String> getPlayerRestrictions( Player player, Integer level, Boolean canDos, Boolean doAll, Integer nextLevel ) {
		
		List<String> list = new ArrayList<String>();
		Iterator<iRule> i = this.plugin.rules.getRules().iterator();
		
		while ( i.hasNext() ) {
			
			iRule rule = i.next();
			
			if ( !rule.isNA( player ) ) {
				
				if ( doAll ) {
					
					if ( canDos && rule.levelOk(level) ) {
						
						list.add( rule.getDescription() );

					} else if ( !canDos && !rule.levelOk(level) ) {
						
						list.add( rule.getDescription() );
						
					}
					
				} else {
					if ( rule.isRestricted( player, level ) ) {
						if ( !canDos ) {
							if ( nextLevel > 0 ) {
								if ( !rule.isRestricted( player, nextLevel ) ) list.add( rule.getDescription() );
							} else {
								list.add( rule.getDescription() );
							}
						}
					} else if ( canDos ) {
	
							list.add( rule.getDescription() );
					}
				}
				
			}
			
		}
		
		return list;
		
	}
	
}
