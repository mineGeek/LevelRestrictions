package com.github.mineGeek.LevelRestrictions.Utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;

import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.Managers.Rule;
import com.github.mineGeek.LevelRestrictions.Managers.Rules;

public class Info {

	public static enum RestrictionDisplayOptions { CAN, CAN_ALL, CAN_PREVIOUS, CAN_NEXT, CAN_CURRENT, CANT, CANT_ALL, CANT_PREVIOUS, CANT_NEXT, CANT_CURRENT };
	
	/**
	 * Splits getPlayerRestrictions List Into a single string
	 * @param list
	 * @param prefix
	 * @param color
	 * @param suffix
	 * @return
	 */
	private static String getDisplayList( List<String> list, String prefix, ChatColor color, String suffix ) {
		
		String message = "";
		String next = "";
		
		Iterator<String> i = list.iterator();
		
		while ( i.hasNext() ) {
			
			next = i.next();
			
			if ( message.length() > 0 ) {
			
				//TODO: localise and give option to list or append
				if ( i.hasNext() ) {
					message = message.concat( "," );
				} else if ( !i.hasNext() ) {
					message = message.concat( " and ");
				}
				
			}
			
			message = message.concat( next );
						
		}
		
		if ( message.length() > 0 ) {
			
			if ( prefix.length() > 0 ) message = prefix.concat( " " ) + message;
			if ( suffix.length() > 0 ) message = message.concat( " ".concat( suffix ) );
			
		
		}
		
		return color + message;
		
		
	}
	
	/**
	 * Generates and returns string for player restrictions
	 * @param player
	 * @param options
	 * @param emptyMessage
	 * @return
	 */
	public static String getPlayerRestrictionMessage( Player player, RestrictionDisplayOptions options, String emptyMessage ) {
		
		List<String> list;
		list = getPlayerRestrictions( player, options );
		
		if ( list.isEmpty() && emptyMessage.length() > 0 ) {
			list.add( emptyMessage );
		}
		
		String message = "";
		
		//TODO: localise prefixes
		switch ( options ) {
			case CAN: case CAN_ALL: case CAN_CURRENT: case CAN_PREVIOUS: case CAN_NEXT:
				message = getDisplayList( list, "You can:", ChatColor.GREEN, "" );
				break;
			default:
				message = getDisplayList( list, "You can't:", ChatColor.RED, "" );
		}
		
		return message;
		
	}

	/**
	 * Gets list of all applicable restrictions
	 * @param player
	 * @param options
	 * @return
	 */
	public static List<String>getPlayerRestrictions( Player player, RestrictionDisplayOptions options ) {
		
		Iterator<Rule> i = Rules.getRules().iterator();
		List<String> list = new ArrayList<String>();
		
		Boolean can = true;
		Boolean all = LevelRestrictions.Config.getConfigFile().getBoolean("defaultDisplayCurrentRestrictions", true );
		Integer level = player.getLevel();
		
		switch ( options ) {
			case CAN:
				break;
			case CAN_CURRENT:
				all = false;
				break;
			case CAN_ALL:
				all = true;
				break;
			case CAN_PREVIOUS:
				level--;
				break;
			case CAN_NEXT:
				level++;
				break;
			case CANT:
				can = false;
				break;
			case CANT_CURRENT:
				can = false;
				all = false;
				break;
			case CANT_ALL:
				can = false;
				all = true;
				break;
			case CANT_PREVIOUS:
				level--;
				can = false;
				break;
			case CANT_NEXT:
				level++;
				can = false;		
		}
		
		Boolean add = false;
		Boolean couldDoPrevious = false;
		
		while ( i.hasNext() ) {
			
			Rule rule = i.next();			
			couldDoPrevious = level > 0 ? rule.levelOk( ( can ? level-1 : level + 1 ) ) : false;
			
			if ( !all ) {
								
				add = can ? !couldDoPrevious && rule.levelOk( level ) : couldDoPrevious && !rule.levelOk( level );
				add = add ? !rule.isBypassed( player ) : false;				
				
			} else {
				add = rule.isMin(level);
				add = rule.isMax(level);
				
				add = can ? !rule.isMin( level ) && !rule.isMax( level ) : rule.isMin( level ) || rule.isMax(level);

			}
			
			if ( add && rule.getDescription().length() > 0 ) {
					list.add( rule.getDescription() );					
			}
			
		}
		
		return list;		
		
	}

}
