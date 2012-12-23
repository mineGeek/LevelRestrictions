package com.github.mineGeek.LevelRestrictions.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.Managers.Rules;
import com.github.mineGeek.LevelRestrictions.Managers.Rule.Actions;
import com.github.mineGeek.LevelRestrictions.Utilities.Info;
import com.github.mineGeek.LevelRestrictions.Utilities.Info.RestrictionDisplayOptions;


public class RulesListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin( PlayerJoinEvent evt ) {
		
		/**
		 * Show rules Player meets when they login
		 */
		if ( LevelRestrictions.Config.getConfigFile().getBoolean("displayPlayerCanDoOnJoin", true ) ) {
		
			evt.getPlayer().sendMessage( Info.getPlayerRestrictionMessage(evt.getPlayer(), RestrictionDisplayOptions.CAN, "") );
			
		}
		
		/**
		 * Show rules Player doesn't meet when they login
		 */
		if ( LevelRestrictions.Config.getConfigFile().getBoolean("displayPlayerCantDoOnJoin", true ) ) {
			
			evt.getPlayer().sendMessage( Info.getPlayerRestrictionMessage(evt.getPlayer(), RestrictionDisplayOptions.CANT, "") );
			
		}		

		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLevelChange( PlayerLevelChangeEvent evt ) {
		
		/**
		 * Show rules Player now meets when level changes?
		 */
		if ( LevelRestrictions.Config.getConfigFile().getBoolean("displayPlayerCanDoOnLevelChange", true ) ) {
			
			evt.getPlayer().sendMessage( Info.getPlayerRestrictionMessage(evt.getPlayer(), RestrictionDisplayOptions.CANT, "") );
			
		}
		
		/**
		 * Show rules Player does not meet when level changes?
		 */
		if ( LevelRestrictions.Config.getConfigFile().getBoolean("displayPlayerCantDoOnLevelChange", true ) ) {
			
			evt.getPlayer().sendMessage( Info.getPlayerRestrictionMessage(evt.getPlayer(), RestrictionDisplayOptions.CANT, "") );
			
		}
		
	}	
	
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractBlock(PlayerInteractEvent evt){
        
    	if ( evt.isCancelled() ) return;
    	
    	if ( Rules.isRestricted( Actions.USE, evt.getMaterial(), evt.getPlayer() ) ) {
    		evt.setCancelled( true );
    	}
    	
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractEvent evt){
        
    	if ( evt.isCancelled() ) return;
    	
    	if ( Rules.isRestricted( Actions.USE, evt.getMaterial(), evt.getPlayer() ) ) {
    		evt.setCancelled( true );
    	}
    	
    }    
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCraftItem(final CraftItemEvent evt) {
    
    	if(evt.isCancelled()) return;
    	
    	if ( evt.getWhoClicked() instanceof Player ) {
    		
	    	if ( Rules.isRestricted( Actions.CRAFT, evt.getRecipe().getResult().getType() , (Player)evt.getWhoClicked() ) ) {
	    		evt.setCancelled( true );
	    	}    	
    	}

    }
    

    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockDestroy(BlockBreakEvent evt)
    {
    	
    	if(evt.isCancelled()) return;
    	if ( Rules.isRestricted( Actions.BREAK, evt.getBlock().getType(), evt.getPlayer() ) ) {
    		evt.setCancelled( true );
    	}
	
        
    }
    

    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent evt)
    {

        if(evt.isCancelled()) return;

    	if ( Rules.isRestricted( Actions.PLACE, evt.getBlock().getType(), evt.getPlayer() ) ) {
    		evt.setCancelled( true );
    	}
        
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickup(PlayerPickupItemEvent evt)
    {

        if(evt.isCancelled()) return;
        
    	if ( Rules.isRestricted( Actions.PICKUP, evt.getItem().getItemStack().getType(), evt.getPlayer() ) ) {
    		evt.setCancelled( true );
    	}        
        
    }	
	
}
