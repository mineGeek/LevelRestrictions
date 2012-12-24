package com.github.mineGeek.LevelRestrictions.Rules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.Integrators.FactionsPlayer;

public class FactionsRule extends Rule implements iRule {

	private List<String> _factions;
	
	public FactionsRule() {
		super();
	}
	
	public FactionsRule( Rule rule ) {
		super( rule );
		this._factions = new ArrayList<String>();
	}
	
	public FactionsRule( FactionsRule rule ) {
		super( rule );
		this._factions = rule.getFactions();
	}
	
	public void addFaction( String faction ) {
		this._factions.add( faction );
	}
	
	public void addFactions( List<String> factions ) {
		this._factions = factions;
	}
	
	public List<String> getFactions() {
		return this._factions;
	}
	
	public Boolean isInFaction( Player player ) {		
		
		return FactionsPlayer.isInFaction(player, this._factions );
		
	}
	
	public void dumpRuleToSender( CommandSender sender ) {
		
		super.dumpRuleToSender(sender);
		sender.sendMessage(" factions: " + ( this._factions != null ? this._factions.toString() : "none" ) );		
		
	}
	
	public Boolean isRestricted( Player player, Integer level ) {
		
		Boolean inFaction = this.isInFaction(player);
		Boolean isRestricted = super.isRestricted(player, level);
		Boolean result = isRestricted || inFaction;
		return result;		
		
	}	
}
