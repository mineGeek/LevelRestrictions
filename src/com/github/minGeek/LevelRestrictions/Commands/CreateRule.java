package com.github.minGeek.LevelRestrictions.Commands;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.Rules.Rule;

public class CreateRule extends CommandBase {
	
	public CreateRule(LevelRestrictions plugin) {
		super(plugin);
	}

	@Override
	protected Boolean exec( String cmdName, String[] args ) {
		
		Boolean result = false;
			
		if ( args[0].length() > 0 ) {
			//see if name already exists
			if ( plugin.rules.getRule( args[0] ) != null ) {
				
				this.execMessage = args[0] + " already exists.";
				result = true;
				
			} else {
				
    			Rule rule = new Rule( this.plugin );
    			rule.setTag( args[0] );
    			plugin.rules.addRule( rule );
    			plugin.config.getConfigFile().set( "rules." + args[0] + ".min", 0 );
    			plugin.saveConfig();
				this.execMessage = args[0] + " has been created.";
				result = true;
			}
			
		} else {
			
			result = false;
			
		}	
		
		return result;
		
	}	

}
