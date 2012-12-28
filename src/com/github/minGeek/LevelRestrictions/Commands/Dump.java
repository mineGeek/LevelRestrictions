package com.github.minGeek.LevelRestrictions.Commands;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;

public class Dump extends CommandBase {

	public Dump(LevelRestrictions plugin) {
		super(plugin);
	}

	@Override
	protected Boolean exec( String cmdName, String[] args ) {
			
		plugin.rules.dumpRules( this.sender );
		return true;
		
	}	
	
	
}
