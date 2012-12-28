package com.github.minGeek.LevelRestrictions.Commands;

import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;

public class RulesAvailable extends CommandBase {

	public RulesAvailable(LevelRestrictions plugin) {
		super(plugin);
	}
	
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

		String emptyMessage = null;

		Boolean can = ( cmdName.equalsIgnoreCase( "lrcan" ) ? true : false );
		
		if ( ( args.length > ( 0 + argStart ) ) && !args[ argStart ].equalsIgnoreCase("current" ) ) {
			
			
			if ( args[ argStart ].equalsIgnoreCase("all") ) {
				
				execMessage = this.plugin.info.getPlayerRestrictionsAll( player, can );
				emptyMessage = can ? "do nothing" : "be limited!";
					
			} else if ( args[ argStart ].equalsIgnoreCase("current" ) ) {
				
				execMessage = this.plugin.info.getPlayerRestrictionsCurrent(player, can );
				emptyMessage = can ? "nothing new" : "have any new restrictions (at this level)";
					
			} else if ( args[ argStart ].equalsIgnoreCase("previous") ) {
				
				execMessage = this.plugin.info.getPlayerRestrictionsPrevious( player, can );
				emptyMessage = can ? "nothing new" : "have any new restrictions (at previous level)";
					
			} else if ( args[argStart].equalsIgnoreCase("next") ) {
				
				execMessage = this.plugin.info.getPlayerRestrictionsNext( player, can );
				emptyMessage = can ? "do the same as this level" : "do the same things at this level";
					
			}
			
			
		} else {

			emptyMessage = can ? "nothing" : "anything!";
			execMessage = this.plugin.info.getPlayerRestrictionsCurrent( player, can );
			if ( execMessage.length() == 0 ) execMessage = can ? "You have no restrictions" : "You seem to have too many restrictions to even count.";
			
		}
		
		if ( execMessage == null || execMessage.length() == 0 ) execMessage = emptyMessage;		
		
		
		return result;
	}

}
