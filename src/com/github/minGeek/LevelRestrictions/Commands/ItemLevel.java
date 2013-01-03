package com.github.minGeek.LevelRestrictions.Commands;

import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.DataStore.PlayerStore;

public class ItemLevel extends CommandBase {

	public ItemLevel(LevelRestrictions plugin) {
		super(plugin);
	}
	
	@Override
	protected Boolean exec( String cmdName, String[] args ) {
	
		Player player = null;
		Integer i = 0;
		Integer adjustment = 0;
		Boolean relative = true;
		
		i = args.length - 1;
		adjustment = Integer.parseInt( args[i] );
		
		i--;
		
		if ( args[i].equalsIgnoreCase("set") ) {
			relative = false;
			i--;	
		}		
		
		if ( i == 0 ) {
			player = this.plugin.getServer().getPlayer( args[0]);
		} else {
			player = (Player)sender;
		}

		if ( player != null ) {
			
			if ( relative ) {
				PlayerStore.player(player).incrimentLevel( adjustment );
			} else {
				PlayerStore.player(player).setLevel( adjustment );
			}
			
			this.execMessage = player.getName() + " is now item level " + PlayerStore.player(player).getLevel();
			
		} else {
			this.execMessage = "Cannot find player.";
		}
		
		return true;
	}
	

}
