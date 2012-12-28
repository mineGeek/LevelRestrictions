package com.github.minGeek.LevelRestrictions.Commands;

import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;

public class RulesFull extends CommandBase {

	public RulesFull(LevelRestrictions plugin) {
		super(plugin);
	}
	
	@Override
	protected Boolean exec( String cmdName, String[] args ) {
			
		Boolean result = true;
		Integer argStart = 0;
		Player player = null;
		
		if ( !( sender instanceof Player ) ) {
			
			if ( args.length > 0 ) {
				
				player = sender.getServer().getPlayer( args[0] );
				argStart = 1;
				
				if ( player == null ) {
					execMessage = "The player " + args[0] + " is not online.";
					return true;
				}
				
			} else {
				
				execMessage = "You didn't specify the Player name. e.g. /lrfull notch";
				return true;
				
			}
			
			
		} else {
			
			player = (Player)sender;
			
		}
		
		String cans = "";
		String cants = "";			
		
		if ( args.length > ( 0 + argStart ) ) {			

			
			if ( args[ argStart ].equalsIgnoreCase("all") ) {
				
				cans = plugin.info.getPlayerRestrictionsAll(player, true );
				cants = plugin.info.getPlayerRestrictionsAll(player, false );
			}
		
		} else {
			
			cans = plugin.info.getPlayerRestrictionsAll(player, true );
			cants = plugin.info.getPlayerRestrictionsAll(player, false );
				
			
		}
		
		if ( cans != null && cans.length() > 0 ) {
			execMessage = cans;
		}
		
		if ( cants != null && cants.length() > 0 ) {
			if ( execMessage.length() > 0 ) execMessage = execMessage.concat( " ");
			execMessage = execMessage.concat( cants );
		}
		
		return result;
		
	}
	

}
