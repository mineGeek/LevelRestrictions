package com.github.mineGeek.LevelRestrictions.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.Rules.Rule.Actions;


public class RulesListener implements Listener {

	LevelRestrictions plugin;
	
	public RulesListener( LevelRestrictions plugin ) {
		this.plugin = plugin;
	}
	
	
    @EventHandler
    public void onEntityDamage(EntityDamageEvent evt ) {
 
		if ( evt.isCancelled() ) return;

		if ( evt instanceof EntityDamageByEntityEvent) {
			if (((EntityDamageByEntityEvent) evt).getDamager() instanceof Player ) {
				
				try {
					Player player = (Player) ((EntityDamageByEntityEvent) evt).getDamager();

					if ( this.plugin.rules.isRestricted(Actions.USE, player.getItemInHand().getType(),player.getItemInHand().getData().getData(), player) ) {
						evt.setCancelled( true );
					}
				} catch ( Exception e ) {
					System.out.print("Exception in OnEntityDamage" );
					e.printStackTrace();
				}
			}
		}
       
     }
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin( PlayerJoinEvent evt ) {
		
		String message = null;
		this.plugin.players.addPlayer( evt.getPlayer() );
		/**
		 * Show rules Player meets when they login
		 */
		if ( this.plugin.config.getConfigFile().getBoolean("displayPlayerCanDoOnJoin", true ) ) {
		
			//message = this.plugin.info.getPlayerRestrictionMessage(evt.getPlayer(), RestrictionDisplayOptions.CAN, "");
			message = this.plugin.info.getPlayerRestrictionsCurrent( evt.getPlayer(), true );
			if ( message.length() > 0 ) evt.getPlayer().sendMessage( message );
			
			
		}
		
		/**
		 * Show rules Player doesn't meet when they login
		 */
		if ( this.plugin.config.getConfigFile().getBoolean("displayPlayerCantDoOnJoin", true ) ) {
			
			//message = this.plugin.info.getPlayerRestrictionMessage(evt.getPlayer(), RestrictionDisplayOptions.CANT, "");
			message = this.plugin.info.getPlayerRestrictionsCurrent( evt.getPlayer(), false );
			if ( message.length() > 0 ) evt.getPlayer().sendMessage( message );
			
		}
		
		
		
		
		
		//Bukkit.getLogger().info( evt.getEventName() + " :: Player Level:" + evt.getPlayer().getLevel() + " Store: " + this.plugin.players.player(evt.getPlayer()).getLevel() );
		
	}
	
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent evt )
    {
    	//Bukkit.getLogger().info( evt.getEventName() + " " + evt.getPlayer().getName() );
    	this.plugin.players.removePlayer( evt.getPlayer() );
    }
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLevelChange( PlayerLevelChangeEvent evt ) {
		
		//Bukkit.getLogger().info( evt.getEventName() + " " + evt.getPlayer().getName() );
		this.plugin.players.player( evt.getPlayer() ).setLevel( evt.getPlayer().getLevel(), this.plugin.config.playerKeepItemLevelOnXPLoss );
		String message = null;
		/**
		 * Show rules Player now meets when level changes?
		 */
		if ( this.plugin.config.displayPlayerCanDoNowOnLevelChange ) {
			//message =  this.plugin.info.getPlayerCurrentRestrictionMessage( evt.getPlayer(), true );
			message = this.plugin.info.getPlayerRestrictionsCurrent( evt.getPlayer(), true );
			if ( message.length() > 0 ) evt.getPlayer().sendMessage( message );
			
		}
		
		/**
		 * Show rules Player does not meet when level changes?
		 */
		if ( this.plugin.config.displayPlayerCanDoNextOnLevelChange ) {
			
			//message =  this.plugin.info.getPlayerCurrentRestrictionMessage( evt.getPlayer(), false );
			message = this.plugin.info.getPlayerRestrictionsCurrent( evt.getPlayer(), false );
			if ( message.length() > 0 ) evt.getPlayer().sendMessage( message );
			
		}
		
		
		Bukkit.getLogger().info( evt.getEventName() + " :: Player Level:" + evt.getPlayer().getLevel() + " Store: " + this.plugin.players.player(evt.getPlayer()).getLevel() );
	}	
	
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractBlock(PlayerInteractEvent evt){
        
    	//Bukkit.getLogger().info( evt.getEventName() + " " + evt.getPlayer().getName() );
    	
    	if ( evt.isCancelled() ) return;
    	
    	//if ( plugin.rules.isRestricted( Actions.USE, evt.getClickedBlock().getType(), evt.getClickedBlock().getData(),  evt.getPlayer() ) ) {
    	try {
	    	if ( evt.getItem() != null && evt.getItem().getType() != null ) {
	    	
	    		if ( plugin.rules.isRestricted( Actions.USE, evt.getItem().getType(), evt.getItem().getData().getData(),  evt.getPlayer() ) ) {    	
	    			evt.setCancelled( true );
	    		}
	    	}
    	} catch (Exception e ) {
    		System.out.print("Exception in onPlayerInteractBlock");
    		e.printStackTrace();
    	}
    	
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractEvent evt){
        
    	//Bukkit.getLogger().info( evt.getEventName() + " " + evt.getPlayer().getName() );
    	
    	if ( evt.isCancelled() ) return;
    	
    	try {
    		ItemStack is = evt.getItem();
    		if ( is == null ) return;
    		Material m = is.getType();
    		byte b = evt.getItem().getData().getData();
    	
    		System.out.print( m + " - " + b );
    	
    		if ( plugin.rules.isRestricted( Actions.USE, m, b, evt.getPlayer() ) ) {
    			evt.setCancelled( true );
    		}
    	} catch ( Exception e ) {
    		System.out.print("woah");
    		e.printStackTrace();
    	}
    	
    }    
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCraftItem(final CraftItemEvent evt) {
    
    	
    	
    	if(evt.isCancelled()) return;
    	
    	if ( evt.getWhoClicked() instanceof Player ) {
    		//Bukkit.getLogger().info( evt.getEventName() + " " + evt.getWhoClicked().getName() );
	    	if ( plugin.rules.isRestricted( Actions.CRAFT, evt.getRecipe().getResult().getType(), evt.getRecipe().getResult().getData().getData(),  (Player)evt.getWhoClicked() ) ) {
	    		evt.setCancelled( true );
	    	}    	
    	}

    }
    

    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockDestroy(BlockBreakEvent evt)
    {
    
    	//Bukkit.getLogger().info( evt.getEventName() + " " + evt.getPlayer().getName() );
    	
    	if(evt.isCancelled()) return;
    	//System.out.print(" -- " + evt.getBlock().getData() );
    	if ( plugin.rules.isRestricted( Actions.BREAK, evt.getBlock().getType(), evt.getBlock().getData(), evt.getPlayer() ) ) {
    		evt.setCancelled( true );
    	}
	
        
    }
    

    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent evt)
    {
    	//Bukkit.getLogger().info( evt.getEventName() + " " + evt.getPlayer().getName() );
    	
        if(evt.isCancelled()) return;

    	if ( plugin.rules.isRestricted( Actions.PLACE, evt.getBlock().getType(), evt.getBlock().getData(), evt.getPlayer() ) ) {
    		evt.setCancelled( true );
    	}
        
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickup(PlayerPickupItemEvent evt)
    {
    	//Bukkit.getLogger().info( evt.getEventName() + " " + evt.getPlayer().getName() );
    	
        if(evt.isCancelled()) return;
        
    	if ( plugin.rules.isRestricted( Actions.PICKUP, evt.getItem().getItemStack().getType(), evt.getItem().getItemStack().getData().getData(), evt.getPlayer() ) ) {
    		evt.setCancelled( true );
    	}        
        
    }	
	
}
