package com.github.minGeek.LevelRestrictions.Commands;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;

public class KillRule extends Confirmation {

	public KillRule(LevelRestrictions plugin) {
		
		super(plugin);
		
	}
	
	@Override
	protected Boolean exec( String cmdName, String[] args ) {
		
		Boolean result = true;
		
		if ( args[0].length() > 0 ) {
			
			if ( args[0].equalsIgnoreCase("confirm") ) {
				
				String ruleName = this.confirmValueAndClear( sender.getName() );
				
				if ( ruleName != null ) {
					
					plugin.rules.removeRule( ruleName );
					plugin.getConfig().set("rules." + ruleName, null );
					plugin.saveConfig();
					execMessage = ruleName + " has been trashed.";
					
				} else {
					
					execMessage = "Your confirmation expired. No changes made.";					
				}				
				
			} else {
				
				//see if name already exists
				if ( plugin.rules.getRule( args[0] ) == null ) {
					
					execMessage = args[0] + " doesn't exist!";
					
				} else {
					
					this.confirmationAdd( sender.getName(), args[0] );
					execMessage = "Confirm removal of '" + args[0] + "' by typing: /lrkillrule confirm.";
				}
				
			}
			
		} else {
			
			return false;
			
		}

		return result;
	}
	
	

}
