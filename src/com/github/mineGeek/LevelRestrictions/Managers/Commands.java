package com.github.mineGeek.LevelRestrictions.Managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.Managers.Rule.Actions;
import com.github.mineGeek.LevelRestrictions.Utilities.Info;
import com.github.mineGeek.LevelRestrictions.Utilities.Info.RestrictionDisplayOptions;

public class Commands implements CommandExecutor {

	LevelRestrictions _plugin;
	Map<String, Map<String, String>> _confirmations = new HashMap<String, Map<String, String>>();
	
	private void addConfirmation( String playerName, String command, String value ) {
		
		Map<String, String> map;
		
		if ( _confirmations.containsKey( playerName ) ) {
			map = _confirmations.get( playerName );
		} else {
			map = new HashMap<String, String>();
		}
		
		map.put( command, value );
		_confirmations.put(playerName, map);
		
	}
	
	private String getConfValue( String playerName, String command ) {
		
		String result = null;
		
		if ( _confirmations.containsKey( playerName ) ) {
			return _confirmations.get(playerName).get(command);
		}
		
		return result;
	}
	
	private void removeConfirmation( String playerName, String command ) {

		if ( this.getConfValue(playerName, command) != null ) {
			_confirmations.get(playerName).remove(command);
		}
	}
	
	public Commands( LevelRestrictions plugin) {
		this._plugin = plugin;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if ( cmd.getName().equalsIgnoreCase( "lrdump") ) {
			Rules.dumpRules(sender);
			return true;
		}
		
		/**
		 * 
		 */
		if ( cmd.getName().equalsIgnoreCase("lrcreate") ) {
			
			if ( args[0].length() > 0 ) {
				//see if name already exists
				if ( Rules.getRule( args[0] ) != null ) {
					sender.sendMessage( args[0] + " already exists.");
					return true;
				} else {
					
	    			Rule rule = new Rule();
	    			rule.setTag( args[0] );
	    			LevelRestrictions.Config.getConfigFile().set("rules." + args[0] + ".min", 0 );
	    			_plugin.saveConfig();
					sender.sendMessage(args[0] + " has been created.");
					return true;
				}
			} else {
				return false;
			}
			
		}
		
		if ( cmd.getName().equalsIgnoreCase("lredit") ) {

			if ( args.length < 3 ) return false;
			
			String ruleName = args[0];
			String action = args[1];
			String value = args[2];			
			String message = null;
			Boolean result = true;
			
			if ( ruleName == null || action == null || value == null ) {
				return false;
			}
			
			Rule rule = Rules.getRule( ruleName );
			
			if ( rule == null ) {
				sender.sendMessage("Rule " + ruleName + " doesn't exist.");
				return false;
			}
			
			String path = "rules." + ruleName + ".";
			
			if ( action.equalsIgnoreCase("desc") ) {
				rule.setDescription( value );
				_plugin.getConfig().set( path + "description", value );
				_plugin.saveConfig();
				message = "description for " + ruleName + " changed.";
			}
			
			if ( action.equalsIgnoreCase("min") ) {
				Integer was = rule.getMin();
				rule.setMin( Integer.parseInt( value ) );
				_plugin.getConfig().set( path + "minLevel", rule.getMin() );
				_plugin.saveConfig();
				message = "Minimum changed from " + was.toString() + " to " + rule.getMin();
			}
			
			if ( action.equalsIgnoreCase("max") ) {
				Integer was = rule.getMax();
				rule.setMax( Integer.parseInt( value ) );
				_plugin.getConfig().set( path + "maxLevel", rule.getMax() );
				_plugin.saveConfig();
				message = "Maximum changed from " + was.toString() + " to " + rule.getMax();
			}
			
			if ( action.equalsIgnoreCase("addaction") ) {
				
				Actions option = null;
				
				if ( value.equalsIgnoreCase("place") ) option = Actions.PLACE;
				if ( value.equalsIgnoreCase("break") ) option = Actions.BREAK;
				if ( value.equalsIgnoreCase("craft") ) option = Actions.CRAFT;
				if ( value.equalsIgnoreCase("use") ) 	option = Actions.USE;
				if ( value.equalsIgnoreCase("pickup") ) option = Actions.PICKUP;
				
				if ( option == null ) {
					message = value + " is not a valid action. Try place, break, creaft, use or pickup.";
					result = false;
					
				} else {				
					String was = rule.getActions().toString();
					rule.addAction(option);

					_plugin.getConfig().set( path + "actions", rule.getActions() );
					_plugin.saveConfig();
					message = "Actions changed from " + was + " to " + rule.getActions().toString();
				}
			}
			
			if ( action.equalsIgnoreCase("removeaction") ) {
				
				Actions option = null;
				
				if ( value.equalsIgnoreCase("place") ) option = Actions.PLACE;
				if ( value.equalsIgnoreCase("break") ) option = Actions.BREAK;
				if ( value.equalsIgnoreCase("craft") ) option = Actions.CRAFT;
				if ( value.equalsIgnoreCase("use") ) 	option = Actions.USE;
				if ( value.equalsIgnoreCase("pickup") ) option = Actions.PICKUP;
				
				if ( option == null ) {
					message = value + " is not a valid action. Try place, break, creaft, use or pickup.";
					result = false;
					
				} else {				
					String was = rule.getActions().toString();
					rule.removeAction( option );
					_plugin.getConfig().set( path + "actions", rule.getActions() );
					_plugin.saveConfig();
					message = "Actions changed from " + was + " to " + rule.getActions().toString();
				}
			}			
			
			if ( action.equalsIgnoreCase("additem") ) {
				
				Integer item = Integer.parseInt( value );
				
				if ( item == null ) {
					message = value + " is not a valid item. It needs to be the item id (e.g. 237 )";
					result = false;
					
				} else {				
					String was = rule.getItems().toString();
					rule.addItem( item );
					_plugin.getConfig().set( path + "items", rule.getItems() );
					_plugin.saveConfig();
					message = "Items changed from " + was + " to " + rule.getItems().toString();
				}
			}
			
			if ( action.equalsIgnoreCase("removeitem") ) {
				
				Integer item = Integer.parseInt( value );
				
				if ( item == null ) {
					message = value + " is not a valid item. It needs to be the item id (e.g. 237 )";
					result = false;
					
				} else {				
					String was = rule.getItems().toString();
					rule.removeItem( item );
					_plugin.getConfig().set( path + "items", rule.getItems() );
					_plugin.saveConfig();
					message = "Items changed from " + was + " to " + rule.getItems().toString();
				}
			}			
			
			if ( message.length() > 0 ) {
				sender.sendMessage( message );
			}
			
			return result;
			
			
		}
		
		
		if ( cmd.getName().equalsIgnoreCase("lrkillrule") ) {
			
			if ( args[0].length() > 0 ) {
				
				if ( args[0].equalsIgnoreCase("confirm") ) {
					
					String ruleName = this.getConfValue( sender.getName(), "killrule");
					
					if ( ruleName != null ) {
						
						if ( Rules.getRule( ruleName ) != null ) {							
							Rules.removeRule( ruleName );
							LevelRestrictions.Config.getConfigFile().set("rules." + ruleName, null );
							_plugin.saveConfig();
							sender.sendMessage( ruleName + " has been trashed." );
							this.removeConfirmation(sender.getName(), "killrule");
							return true;								
						} else {
							sender.sendMessage( ruleName + " doesn't exist" );
							this.removeConfirmation(sender.getName(), "killrule");
							return true;
						}						
						
					} else {
						
						sender.sendMessage("You haven't asked to kill anything yet.");
						return true;
					}	
					
					
				} else {
					//see if name already exists
					if ( Rules.getRule( args[0] ) == null ) {
						
						sender.sendMessage( args[0] + " doesn't exist!");
						return true;
						
					} else {
						
						this.addConfirmation( sender.getName(), "killrule", args[0]);
						sender.sendMessage("Confirm removal of '" + args[0] + "' by typing: /lrkillrule confirm.");
						return true;
					}
				}
				
			} else {
				return false;
			}
			
		}		
		
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
					sender.sendMessage("You didn't specify the Player name. e.g. /lrfull notch");
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
					sender.sendMessage("This command can only be run on a player. e.g. /can Notch");
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
					emptyMessage = can ? "nothing new" : "have any new restrictions (at this level)";
						
				} else if ( args[ argStart ].equalsIgnoreCase("previous") ) {
					
					options = can ? RestrictionDisplayOptions.CAN_PREVIOUS : RestrictionDisplayOptions.CANT_PREVIOUS;
					emptyMessage = can ? "nothing new" : "have any new restrictions (at previous level)";
						
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
