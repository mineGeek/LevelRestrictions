package com.github.mineGeek.LevelRestrictions.Rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;


public class Rule implements iRule {

	private String _tag;
	private String _description;
	private Integer _min;
	private Integer _max;
	private String _minMessage;
	private String _maxMessage;
	private String _otherMessage;
	private Boolean _default;
	private List<Actions> _actions;
	private List<String> _items;
	private LevelRestrictions plugin;
	private String _actionText = "";
	private Boolean _minSet = false;
	private Boolean _maxSet = false;
	
	public enum Actions { CRAFT, USE, PLACE, BREAK, PICKUP }
	
	public Rule( Rule rule ) {
		
		this.plugin = rule.plugin;
		this._items = new ArrayList<String>();
		this._actions = new ArrayList<Actions>();
		this.setTag( rule.getTag() );
		this.setDescription( rule.getDescription() );
		this.setMin( rule.getMin() );
		this.setMax( rule.getMax() );
		this.setMinMessage( rule.getMinMessage() );
		this.setMaxMessage( rule.getMaxMessage() );
		this.setOtherMessage( rule.getOtherMessage() );
		this.setDefault( rule.getDefault() );
		this.setActions( rule.getActions() );
		this.addItems( rule.getItems() );
		this.setActionText( rule.getActionText() );
		
	}
	
	public Rule( LevelRestrictions plugin ) {
		this.plugin = plugin;
		this._items = new ArrayList<String>();
		this._actions = new ArrayList<Actions>();
	}
	
	public String getActionText() {
		return this._actionText;
	}
	
	public void setActionText( String value ) {
		this._actionText = value;
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
	
	public void setMaxSet( Boolean value ) {
		this._maxSet = value;
	}
	
	public void setMinSet( Boolean value ) {
		this._minSet = value;
	}
	
	public void setMax( Integer value ) {
		this._max = value;
	}
	
	public Integer getMax() {
		return this._max;
	}
	
	public void addAction( Actions action ) {
		this._actions.add( action );
	}
	
	public List<Actions> getActions() {
		return this._actions;
	}
	
	public void setActions( List<Actions> actions ) {
		this._actions = actions;
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
	
	public List<String> getItems() {
		return this._items;
	}
	
	
	
	public void setMinMessage( String value ) {
		this._minMessage = value;
	}
	
	public String getMinMessage() {
		return this._minMessage;
	}
	
	public void setMaxMessage( String value ) {
		this._maxMessage = value;
	}
	
	public String getMaxMessage() {
		return this._maxMessage;
	}
	
	public void setOtherMessage( String value ) {
		this._otherMessage = value;
	}
	
	public String getOtherMessage() {
		return this._otherMessage;
	}
	
	public void removeAction( Actions action ) {
		this._actions.remove( action );
	}
	
	public Boolean appliesToAction( Actions action ) {
		
		return this._actions.contains( action );

	}
	
	public void addItem( String itemId ) {
		this._items.add( itemId );
	}
	
	public void addItems( List<String> items ) {
		this._items = items;
	}
	
	public void setItems( List<String> items) {
		this._items = items;
	}
	
	public void removeItem( String itemId ) {
		this._items.remove( itemId );
	}
	
	public Boolean appliesToItem( String itemId, byte data ) {
		if ( this._items.contains( itemId + "." + data ) ) return true;
		return this._items.contains( itemId );
	}
	
	public String getOtherMessage(Material material ) {
		return this.getMinMaxMessage( this._otherMessage, material);
	}
	
	private String getMinMaxMessage(String message, Material material) {
		
		String result = message.replace("%min", this._min.toString() );
		result = result.replace("%max", this._max.toString() );
		result = result.replace("%a", this.getActionText() );
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
	
		return levelOk( this.plugin.players.player( player ).getLevel() );
		
	}
	
	public Boolean levelOk( Integer level ) {
		
		return !isMin( level ) && ! isMax( level );
		
	}
	
	public Boolean willMin( Player player ) {
		
		return isMin( player )  && !isMin( this.plugin.players.player( player ).getLevel() + 1 );
		
	}
	
	public Boolean willMax( Player player ) {
		return isMax( player ) && !isMax( this.plugin.players.player( player ).getLevel() + 1 );
	}
	
	public Boolean wasMin( Player player ) {
		
		return isMin( player ) && !isMin( this.plugin.players.player( player ).getLevel() - 1 );
		
	}
	
	public Boolean wasMax( Player player ) {
		
		return isMax( player ) && !isMax( this.plugin.players.player( player ).getLevel() - 1 );
		
	}
	
	public Boolean isMin( Player player ) {
		return isMin(  this.plugin.players.player( player ).getLevel() );
	}
	
	public Boolean isMax( Player player ) {
		return isMax( this.plugin.players.player( player ).getLevel() );
	}	
	
	public Boolean isMin( Integer level ) {
		Boolean result = false;
		if ( this._minSet ) {
			result = level < this._min;
			
		} else {			
			result = this._min > 0 && ( level < this._min);
		}
		return result;
		
	}
	
	public Boolean isMax( Integer level ) {
		Boolean result = false;
		
		if ( this._maxSet ) {
			result = level > this._max;
		} else {
			
			result = this._max > 0 && (level > this._max);
		}
		return result;
	}
	
	public Boolean isBypassed( Player player ) {
		
		Boolean ted = player.hasPermission("levelrestrictions.rules.bypass." + this.getTag() );
		return ted;
		
		
	}
	
	public Boolean isNA( Player player ) {
		
		return isBypassed( player );
		
	}
	
	public Boolean isApplicable( Actions action, Material material, byte data, Player player ) {
		if ( !this.appliesToAction( action ) ) 				return false;		
		if ( !this.appliesToItem( String.valueOf( material.getId() ) , data ) ) 		return false;
		return true;
	}
	
	public Boolean isRestricted( Actions action, Material material, byte data, Player player ) {
		

		if ( this.isRestricted(player, this.plugin.players.player( player ).getLevel() ) ) return true;

		return false;
		
	}
	
	public Boolean isRestricted( Player player, Integer level ) {
		
		if ( this.isNA(player) ) return false;
		return !this.levelOk(level);
		
	}
	
	public void dumpRuleToSender( CommandSender sender ) {
		
		sender.sendMessage( this.getTag() );
		sender.sendMessage(" description: " + this.getDescription() );
		String min = this.getMin() != null && this.getMin() > 0 ? " min level: " + this.getMin().toString() : "";
		String max = this.getMax() != null && this.getMax() > 0 ? " max level: " + this.getMax().toString() : "";
		sender.sendMessage( min + max );
		sender.sendMessage( " actions: " + this._actions.toString() );
		sender.sendMessage( " items: " + this._items.toString() );
		
		
		
		
	}
	
}
