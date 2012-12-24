package com.github.mineGeek.LevelRestrictions.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.Managers.PlayerMessenger;
import com.github.mineGeek.LevelRestrictions.Rules.Rule.Actions;

public class Rules {

	private static List<iRule> _rules;
	private static Map<String, iRule> _ruleMap = new HashMap<String, iRule>();
	
	public Rules() {
		Rules._rules = new ArrayList<iRule>();
	}
	
	public static List<iRule>getRules() {
		return _rules;
	}
	
	public void addRule( iRule rule ) {
		
		_rules.add( rule );
		_ruleMap.put( rule.getTag().toLowerCase(), rule);
	}
	
	public static void removeRule( String ruleName ) {
		
		iRule rule = _ruleMap.get(ruleName);
		_rules.remove( rule );
		_ruleMap.remove( ruleName );
		
	}
	
	public static void dumpRules( CommandSender sender ) {
		
		Iterator<iRule> i = _rules.iterator();
		
		while ( i.hasNext() ) {
			i.next().dumpRuleToSender( sender );
		}
		
	}
	
	public static iRule getRule( String ruleName ) {
		return _ruleMap.get( ruleName.toLowerCase() );
	}
	
	public void clear() {
		_rules.clear();
	}
	/*
	public static String getWhatPlayerCanDo( Player player ) {
		
		Iterator<iRule> i = _rules.iterator();
		String message = "";		
		
		while ( i.hasNext() ) {
			iRule rule = i.next();
			
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
	*/
	public static Boolean isRestricted(Actions action, Material material, Player player ) {
		
		Boolean result = false;
		
		Iterator<iRule> i = _rules.iterator();
		
		while( i.hasNext() ) {
			
			iRule rule = i.next();
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
