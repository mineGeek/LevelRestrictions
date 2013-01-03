package com.github.mineGeek.LevelRestrictions.Rules;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.Rules.Rule.Actions;

public interface iRule {

	public Boolean isRestricted( Player player, Integer level );
	public void dumpRuleToSender( CommandSender sender );
	public void setTag( String value );
	public void setDescription( String value );
	public String getDescription();
	public void setDefault( Boolean value );
	public void setMin( Integer value );
	public void setMax( Integer value );
	public void addAction( Actions action );
	public void setMinMessage( String value );
	public void setMaxMessage( String value );
	public void setOtherMessage( String value );
	public void addItem( String itemId );
	public void addItems( List<String> items );
	public Boolean isRestricted( Actions action, Material material, byte data, Player player );
	public Boolean isMin( Integer level );
	public Boolean isMax( Integer level );
	public Boolean isMin( Player player );
	public Boolean isMax( Player player );
	public String getOtherMessage(Material material );
	public String getMinMessage(Material material );
	public String getMaxMessage(Material material );
	public String getTag();
	public Integer getMax();
	public Integer getMin();
	public List<Actions> getActions();
	public void removeAction( Actions action );
	public List<String> getItems();
	public void removeItem( String itemId );
	public Boolean isNA( Player player );
	public Boolean levelOk( Integer level );
	public void setMinSet( Boolean value );
	public void setMaxSet( Boolean value );
	public Boolean isApplicable( Actions action, Material material, byte data, Player player );
	public String getActionText();
	public void setActionText( String value );
	
}
