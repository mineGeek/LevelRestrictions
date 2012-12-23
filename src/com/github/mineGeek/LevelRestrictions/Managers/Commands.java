package com.github.mineGeek.LevelRestrictions.Managers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.Utilities.Info;
import com.github.mineGeek.LevelRestrictions.Utilities.Info.RestrictionDisplayOptions;

public class Commands implements CommandExecutor {

	LevelRestrictions _plugin;
	
	public Commands( LevelRestrictions plugin) {
		this._plugin = plugin;
	}
 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		/**
		 * Display can and cant messages in one
		 * when user types /lrfull or /lrfull <name> is typed in console
		 */
		if (cmd.getName().equalsIgnoreCase("lrfull") ) {
		
			Integer argStart = 0;
			Player player;
			
			if (!(sender instanceof Player)) {
				
				if ( args.length > 0 ) {
					
					player = sender.getServer().getPlayer(args[0]);
					argStart = 1;
					
					if ( player == null ) {
						sender.sendMessage("The player " + args[0] + " is not online.");
						return false;
					}
					
				} else {
					sender.sendMessage("This command can only be run by a player.");
					return false;
				}
				
				
			} else {
				
				player = (Player)sender;
			}

			String cans = "";
			String cants = "";			
			
			if ( args.length > ( 0 + argStart ) ) {			

				
				if ( args[ argStart ].equalsIgnoreCase("all") ) {
					
					cans = Info.getPlayerRestrictionMessage(player, RestrictionDisplayOptions.CAN_ALL, "do nothing");
					cants = Info.getPlayerRestrictionMessage(player, RestrictionDisplayOptions.CANT_ALL, "be limited");
						
				}
			
			} else {
				
					cans = Info.getPlayerRestrictionMessage(player, RestrictionDisplayOptions.CAN_CURRENT, "");
					cants = Info.getPlayerRestrictionMessage(player, RestrictionDisplayOptions.CANT_CURRENT, "");					
				
			}
			
			if ( cans.length() > 0 ) {
				sender.sendMessage( cans );
			}
			
			if ( cants.length() > 0 ) {
				sender.sendMessage( cants );
			}
			
			return true;
			
			
		}
		
		/**
		 * Shows user what rules they do/don't qualify for when using
		 * either /lrcan or /lrcant
		 */
		
		if ( cmd.getName().equalsIgnoreCase("lrcan") || cmd.getName().equalsIgnoreCase("lrcant") ) {

			Integer argStart = 0;
			Player player;
			
			if (!(sender instanceof Player)) {
				
				if ( args.length > 0 ) {
					
					player = sender.getServer().getPlayer(args[0]);
					argStart = 1;
					
					if ( player == null ) {
						sender.sendMessage("The player " + args[0] + " is not online.");
						return false;
					}
					
				} else {
					sender.sendMessage("This command can only be run by a player.");
					return false;
				}
				
				
			} else {
				
				player = (Player)sender;
			}
				
			String emptyMessage = "";
			RestrictionDisplayOptions options = RestrictionDisplayOptions.CAN;
			Boolean can = cmd.getName().equalsIgnoreCase("lrcan") ? true : false;
			
			if ( args.length > ( 0 + argStart ) ) {
				
				
				if ( args[ argStart ].equalsIgnoreCase("all") ) {
					
					options = can ? RestrictionDisplayOptions.CAN_ALL : RestrictionDisplayOptions.CANT_ALL; 
					emptyMessage = can ? "do nothing" : "be limited!";
						
				} else if ( args[ argStart ].equalsIgnoreCase("current" ) ) {
					
					options = can ? RestrictionDisplayOptions.CAN_CURRENT : RestrictionDisplayOptions.CANT_CURRENT;
					emptyMessage = can ? "nothing new" : "have any new restrictions";
						
				} else if ( args[ argStart ].equalsIgnoreCase("previous") ) {
					
					options = can ? RestrictionDisplayOptions.CAN_PREVIOUS : RestrictionDisplayOptions.CANT_PREVIOUS;
					emptyMessage = can ? "nothing new" : "have any new restrictions";
						
				} else if ( args[argStart].equalsIgnoreCase("next") ) {
					
					options = can ? RestrictionDisplayOptions.CAN_NEXT : RestrictionDisplayOptions.CANT_NEXT;
					emptyMessage = can ? "do the same as this level" : "do the same things at this level";
						
				}
				
			} else {
				
				options = can ? RestrictionDisplayOptions.CAN : RestrictionDisplayOptions.CANT;
				emptyMessage = can ? "nothing" : "anything!";				
				
			} 
			
			String message = Info.getPlayerRestrictionMessage(player, options, emptyMessage);
			
			sender.sendMessage(message);
			return true;

		} 
		
		return false;		
		
		
	}
	
}
