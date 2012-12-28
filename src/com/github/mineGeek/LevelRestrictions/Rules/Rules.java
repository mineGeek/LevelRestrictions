package com.github.mineGeek.LevelRestrictions.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.Managers.PlayerMessenger;
import com.github.mineGeek.LevelRestrictions.Rules.Rule.Actions;

public class Rules {

	@SuppressWarnings("unused")
	private LevelRestrictions 	_plugin;
	private List<iRule> 		_rules 		= new ArrayList<iRule>();
	private Map<String, iRule> 	_ruleMap 	= new HashMap<String, iRule>();
	
	
	public Rules( LevelRestrictions plugin ) {
		
		_rules = new ArrayList<iRule>();
		this._plugin = plugin;
		
	}
	
	public Rules() {
		
		_rules = new ArrayList<iRule>();
		_ruleMap.clear();
		
	}
	
	public List<iRule>getRules() {
		
		return this._rules;
		
	}
	
	public void addRule( String ruleName, iRule rule ) {
		
		this.removeRule(ruleName);
		this._rules.add( rule );
		this._ruleMap.put( ruleName, rule );
		
	}
	
	public void addRule( iRule rule ) {
		
		this._rules.add( rule );
		this._ruleMap.put( rule.getTag().toLowerCase(), rule);
	}
	
	public void removeRule( String ruleName ) {
		
		iRule rule = this._ruleMap.get(ruleName);
		this._rules.remove( rule );
		this._ruleMap.remove( ruleName );
		
	}
	
	public void dumpRules( CommandSender sender ) {
		
		Iterator<iRule> i = this._rules.iterator();
		
		while ( i.hasNext() ) {
			
			i.next().dumpRuleToSender( sender );
			
		}
		
		
	}
	
	public iRule getRule( String ruleName ) {
		
		return this._ruleMap.get( ruleName.toLowerCase() );
		
	}
	
	public void clear() {
		
		this._ruleMap.clear();
		this._rules.clear();
		
	}

	public Boolean isRestricted(Actions action, Material material, Player player ) {
		
		Boolean result = false;
		
		Iterator<iRule> i = this._rules.iterator();
		
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
