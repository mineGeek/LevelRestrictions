package com.github.minGeek.LevelRestrictions.Commands;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.Rules.iRule;
import com.github.mineGeek.LevelRestrictions.Rules.Rule.Actions;

public class EditRule extends CommandBase {
	
	public EditRule(LevelRestrictions plugin) {
		super(plugin);
	}
	
	private Actions getAction( String value ) {
		
		if ( value == "place" ) {
			return Actions.PLACE;
		} else if ( value == "break" ) {
			return Actions.BREAK;
		} else if ( value == "craft" ) {
			return Actions.CRAFT;
		} else if ( value == "use" ) {
			return Actions.USE;
		} else if ( value == "pickup" ) {
			return Actions.PICKUP;
		} else {
			return null;
		}
	}

	@Override
	protected Boolean exec( String cmdName, String[] args ) {
		
		Boolean result = true;
			
		if ( args.length < 3 ) {
			
			this.execMessage = "Too few parameters";
			return false;
			
		}
		
		String ruleName = args[0].toLowerCase();
		String action 	= args[1].toLowerCase();
		String value 	= args[2].toLowerCase();
		
		if ( ruleName == null || action == null || value == null ) {
			
			return false;
			
		}
		
		iRule rule = plugin.rules.getRule( ruleName );
		
		if ( rule == null ) {
			
			this.execMessage = "Rule " + ruleName + " doesn't exist.";
			return false;
			
		}
		
		String path = "rules." + ruleName + ".";
		
		if ( action == "desc" ) {
			
			rule.setDescription( value );
			plugin.getConfig().set( path + "description", value );
			plugin.saveConfig();
			execMessage = "description for " + ruleName + " changed.";
			
		} else if ( action == "min" ) {
			
			Integer was = rule.getMin();
			rule.setMin( Integer.parseInt( value ) );
			plugin.getConfig().set( path + "minLevel", rule.getMin() );
			plugin.saveConfig();
			execMessage = "Minimum changed from " + was.toString() + " to " + rule.getMin();
			
		} else if ( action == "max" ) {
			
			Integer was = rule.getMax();
			rule.setMax( Integer.parseInt( value ) );
			plugin.getConfig().set( path + "maxLevel", rule.getMax() );
			plugin.saveConfig();
			execMessage = "Maximum changed from " + was.toString() + " to " + rule.getMax();
			
		} else if ( action == "addaction" ) {
			
			Actions option = this.getAction( value );
			
			if ( option == null ) {
				
				execMessage = value + " is not a valid action. Try place, break, creaft, use or pickup.";
				return false;
				
			} else {		
				
				String was = rule.getActions().toString();
				rule.addAction(option);

				plugin.getConfig().set( path + "actions", rule.getActions() );
				plugin.saveConfig();
				
				execMessage = "Actions changed from " + was + " to " + rule.getActions().toString();
				
			}
			
		} else if ( action == "removeaction") {
			
			Actions option = this.getAction( value );
			
			if ( option == null ) {
				execMessage = value + " is not a valid action. Try place, break, creaft, use or pickup.";
				return false;
				
			} else {
				
				String was = rule.getActions().toString();
				rule.removeAction( option );
				plugin.getConfig().set( path + "actions", rule.getActions() );
				plugin.saveConfig();
				execMessage = "Actions changed from " + was + " to " + rule.getActions().toString();
				
			}
			
		} else if ( action == "additem" ) {
			
			Integer item = Integer.parseInt( value );
			
			if ( item == null ) {
				
				execMessage = value + " is not a valid item. It needs to be the item id (e.g. 237 )";
				result = false;
				
			} else {				
				
				String was = rule.getItems().toString();
				rule.addItem( item );
				plugin.getConfig().set( path + "items", rule.getItems() );
				plugin.saveConfig();
				execMessage = "Items changed from " + was + " to " + rule.getItems().toString();
				
			}
			
		} else if ( action == "removeitem" ) {
			
			Integer item = Integer.parseInt( value );
			
			if ( item == null ) {
				
				execMessage = value + " is not a valid item. It needs to be the item id (e.g. 237 )";
				return false;
				
			} else {
				
				String was = rule.getItems().toString();
				rule.removeItem( item );
				plugin.getConfig().set( path + "items", rule.getItems() );
				plugin.saveConfig();
				execMessage = "Items changed from " + was + " to " + rule.getItems().toString();
				
			}
			
		}			
			
		
		return result;
		
	}		

}
