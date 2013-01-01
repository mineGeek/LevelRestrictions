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

	private LevelRestrictions 	_plugin;
	private List<iRule> 		_rules 			= new ArrayList<iRule>();
	private Map<String, iRule> 	_ruleMap 		= new HashMap<String, iRule>();
	private List<String>		_xworlds		= new ArrayList<String>();
	private Boolean 			_defaultDeny 	= false;
	
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
	
	public void setDefaultDeny( Boolean value ) {
		this._defaultDeny = value;
	}
	
	
	public void addExcludedWorldName( String value ) {
		this._xworlds.add( value );
	}
	
	public Boolean isExcludedWorld( String worldName ) {
		return this._xworlds.contains( worldName );
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
		this._xworlds.clear();
		
	}

	public Boolean isRestricted(Actions action, Material material, byte data, Player player ) {
		
		if ( this.isExcludedWorld( player.getWorld().getName() ) ) return false;

		Boolean result = false;
		//System.out.print(material.getData());
		Iterator<iRule> i = this._rules.iterator();
		Boolean wasApplicable = false;

		while( i.hasNext() ) {
			
			iRule rule = i.next();
			String message;
			if ( !rule.isNA( player ) ) {
				if ( rule.isApplicable(action, material, data, player)) {
					wasApplicable = true;
					if ( rule.isRestricted(action, material, data, player ) ) {
						
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
			}
			
		}
		
		if ( !result && (!wasApplicable && this._defaultDeny ) ) {
			if ( !player.hasPermission("levelrestrictions.rules.bypass")) {
				
				String message = this._plugin.config.defaultOtherMessage;
				message = message.replace("%m", material.toString().replace("_", " ").toLowerCase() );						
				
				PlayerMessenger.SendPlayerMessage(player, message );
				result = true;
			}
		}
		if ( result ) System.out.print( player.getName() + " to " + material.getId() + " -- " + data);
		return result;
		
	}
	
}
