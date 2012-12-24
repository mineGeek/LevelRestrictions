package com.github.mineGeek.LevelRestrictions.Managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.Integrators.FactionsPlayer;

public class Rule {

	private String _tag;
	private String _description;
	private Integer _min;
	private Integer _max;
	private String _minMessage;
	private String _maxMessage;
	private String _otherMessage;
	private Boolean _default;
	private List<Actions> _actions;
	private List<Integer> _items;
	private List<String> _factions;
	
	public enum Actions { CRAFT, USE, PLACE, BREAK, PICKUP }
	
	public Rule() {
		this._items = new ArrayList<Integer>();
		this._actions = new ArrayList<Actions>();
	}
	
	public String getTag() {
		return this._tag;
	}
	
	
	public void setTag( String value ) {
		this._tag = value;
	}
	
	public String getDescription() {
		return this._description;
	}
	
	public void setDescription( String value ) {
		this._description = value;
	}
	
	public Boolean getDefault() {
		return this._default;
	}
	
	public void setDefault( Boolean value ) {
		this._default = value;
	}
	
	public void setMin( Integer value ) {
		this._min = value;
	}
	
	public Integer getMin() {
		return this._min;
	}
	
	public void setMax( Integer value ) {
		this._max = value;
	}
	
	public Integer getMax() {
		return this._max;
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
	
	public void addAction( Actions action ) {
		this._actions.add( action );
	}
	
	public List<Actions> getActions() {
		return this._actions;
	}
	
	public String getActionsAsString() {

		Iterator<Actions> i = this._actions.iterator();
		String result = "";
		while ( i.hasNext() ) {
			if ( result.length() > 0 ) result = result + ", ";
			result = result + i.next().toString();
		}
		
		return "[" + result + "]";
	}
	
	public List<Integer> getItems() {
		return this._items;
	}
	
	
	public void setMinMessage( String value ) {
		this._minMessage = value;
	}
	
	public void setMaxMessage( String value ) {
		this._maxMessage = value;
	}
	
	public void setOtherMessage( String value ) {
		this._otherMessage = value;
	}
	
	public void removeAction( Actions action ) {
		this._actions.remove( action );
	}
	
	public Boolean appliesToAction( Actions action ) {
		return this._actions.contains( action );
	}
	
	public void addItem( Integer itemId ) {
		this._items.add( itemId );
	}
	
	public void addItems( List<Integer> items ) {
		this._items = items;
	}
	
	public void removeItem( Integer itemId ) {
		this._items.remove( itemId );
	}
	
	public Boolean appliesToItem( Integer itemId ) {
		return this._items.contains( itemId );
	}
	
	public String getOtherMessage(Material material ) {
		return this.getMinMaxMessage( this._otherMessage, material);
	}
	
	private String getMinMaxMessage(String message, Material material) {
		
		String result = message.replace("%min", this._min.toString() );
		result = result.replace("%max", this._max.toString() );
		result = result.replace("%m", material.toString().replace("_", " ").toLowerCase() );
		return result;
		
	}
	
	public String getMinMessage(Material material ) {
		return this.getMinMaxMessage(this._minMessage, material);
	}
	
	public String getMaxMessage( Material material ) {
		return this.getMinMaxMessage( this._maxMessage, material );
	}
	
	public Boolean levelOk( Player player ) {
	
		return levelOk( player.getLevel() );
		
	}
	
	public Boolean levelOk( Integer level ) {
		
		return !isMin( level ) && ! isMax( level );
		
	}
	
	public Boolean willMin( Player player ) {
		
		return isMin( player )  && !isMin( player.getLevel() + 1 );
		
	}
	
	public Boolean willMax( Player player ) {
		return isMax( player ) && !isMax( player.getLevel() + 1 );
	}
	
	public Boolean wasMin( Player player ) {
		
		return isMin( player ) && !isMin( player.getLevel() - 1 );
		
	}
	
	public Boolean wasMax( Player player ) {
		
		return isMax( player ) && !isMax( player.getLevel() - 1 );
		
	}
	
	public Boolean isMin( Player player ) {
		return isMin( player.getLevel() );
	}
	
	public Boolean isMax( Player player ) {
		return isMax( player.getLevel() );
	}	
	
	public Boolean isMin( Integer level ) {
		
		return this._min > 0 && ( level < this._min );
		
	}
	
	public Boolean isMax( Integer level ) {
		
		return this._max > 0 && ( level > this._max );
		
	}
	
	public Boolean isBypassed( Player player ) {
		
		Boolean ted = player.hasPermission("levelrestrictions.rules.bypass." + this.getTag() );
		return ted;
		
		
	}
	
	public Boolean isRestricted( Actions action, Material material, Player player ) {
			
		if ( !this.appliesToAction( action ) ) 				return false;		
		if ( !this.appliesToItem( material.getId() ) ) 		return false;
		if ( this.isRestricted(player, player.getLevel() ) )return true;
		
		return this.getDefault();
		
	}
	
	public Boolean isRestricted( Player player, Integer level ) {
		
		Boolean restricted = false;
		
		if ( this.isBypassed(player) ) return false;
		restricted = !this.levelOk(level);
		restricted = restricted && !this.isInFaction(player);
		
		
		return restricted;
		
		
	}
	
	public void dumpRuleToSender( CommandSender sender ) {
		
		sender.sendMessage( this.getTag() );
		sender.sendMessage(" description: " + this.getDescription() );
		String min = this._min > 0 ? " min level: " + this._min.toString() : "";
		String max = this._max > 0 ? " max level: " + this._max.toString() : "";
		sender.sendMessage( min + max );
		sender.sendMessage( " actions: " + this._actions.toString() );
		sender.sendMessage( " items: " + this._items.toString() );
		sender.sendMessage(" factions: " + ( this._factions != null ? this._factions.toString() : "none" ) );
		
		
		
	}
	
}
