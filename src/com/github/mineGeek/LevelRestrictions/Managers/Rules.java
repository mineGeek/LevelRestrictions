package com.github.mineGeek.LevelRestrictions.Managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.Managers.Rule.Actions;

public class Rules {

	private static List<Rule> _rules;
	
	public Rules() {
		Rules._rules = new ArrayList<Rule>();
	}
	
	public static List<Rule>getRules() {
		return _rules;
	}
	
	public void addRule( Rule rule ) {
		
		_rules.add( rule );
		
	}
	
	public void clear() {
		_rules.clear();
	}
	
	public static String getWhatPlayerCanDo( Player player ) {
		
		Iterator<Rule> i = _rules.iterator();
		String message = "";		
		
		while ( i.hasNext() ) {
			Rule rule = i.next();
			
			if ( !rule.isMin(player) || rule.isMax(player)) {

				if ( rule.getDescription().length() > 0 ) {
				
					if ( message.length() > 0 ) {
						message = message.concat(", " + rule.getDescription() );
					} else {
						message = message.concat( rule.getDescription() );
					}
					
				}
			}
		}
		
		return message;
		
	}
	
	public static String getWhatPlayerCannotDo( Player player ) {
		
		Iterator<Rule> i = _rules.iterator();
		String message = "";
		
		while ( i.hasNext() ) {
			Rule rule = i.next();
			
			if ( rule.isMin(player) || rule.isMax(player)) {

				if ( rule.getDescription().length() > 0 ) {
				
					if ( message.length() > 0 ) {
						message = message.concat(", " + rule.getDescription() );
					} else {
						message = message.concat( rule.getDescription() );
					}
					
				}
			}
		}
		
		return message;		
		
	}
	
	public static Boolean isRestricted(Actions action, Material material, Player player ) {
		
		Boolean result = false;
		
		Iterator<Rule> i = _rules.iterator();
		
		while( i.hasNext() ) {
			
			Rule rule = i.next();
			String message;
			
			if ( rule.isRestricted(action, material, player ) ) {
				
				if ( rule.isMin( player ) ) {
					
					message = rule.getMinMessage(material);
					
				} else if ( rule.isMax(player)) {
					
					message = rule.getMaxMessage(material);
					
				} else {
					
					message = rule.getOtherMessage(material);
					
				}
				
				PlayerMessenger.SendPlayerMessage( player, message);
				
				result = true;
				break;
				
			}
			
		}
		
		return result;
		
	}
	
}