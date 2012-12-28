package com.github.mineGeek.LevelRestrictions.Managers;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.Rules.FactionsRule;
import com.github.mineGeek.LevelRestrictions.Rules.Rule;
import com.github.mineGeek.LevelRestrictions.Rules.Rule.Actions;
import com.github.mineGeek.LevelRestrictions.Rules.iRule;


public class Configurator {

    private LevelRestrictions plugin;
    private FileConfiguration _config;
    
    public String defaultMinMessage;
    public String defaultMaxMessage;
    public String defaultOtherMessage;
    public Boolean defaultDenyUnlisted;
    public Boolean playerKeepItemLevelOnXPLoss;
    public String candoPrefix;
    public String cantdoPrefix;
    public String candoNowPrefix;
    public String candoNextPrefix;
    public String candoPreviousPrefix;
    public String cantdoPreviousPrefix;
    public Boolean displayPlayerCanDoNowOnLevelChange;
    public Boolean displayPlayerCanDoNextOnLevelChange;
    
    public Boolean displayRestrictionsAsList = false;
    
    public File getDataFolder() {
    	return plugin.getDataFolder();
    }
    
    public FileConfiguration getConfigFile() {
        return _config;
    }
    
    public Configurator( LevelRestrictions plugin ) {
    	
    	plugin.saveDefaultConfig();
        this.plugin = plugin;
        _config = plugin.getConfig();
        this.loadConfig();
        
    }
    
    public void saveConfig() {
    	
    	if ( playerKeepItemLevelOnXPLoss ) {
    		
    	}
    	
    }
    
    public void checkPlayerDataFolder() {
    	
    	File file = new File( plugin.getDataFolder() + File.separator + "players" );
    	
    	if ( !file.isDirectory() ) {
    		try {
    			file.mkdir();
    		} catch (Exception e ) {
    			plugin.getLogger().info("Failed making plugins/LevelRestrictions/players");
    		}
    	}
    	
    	
    }
    
    public void loadRule( String ruleName ) {
    	
    	String path = "rules." + ruleName;
		iRule rule;
		
		//If Factions: are defined in this rule, load up as a faction rule
		if ( _config.contains( path + ".factions" ) ) {
			
			rule = new FactionsRule( this.plugin );
			List<String> factions = _config.getStringList( path + ".factions");
			((FactionsRule) rule).addFactions(factions);
			
		} else {
			
			//Default rule
			rule = new Rule( this.plugin);
		}
		
		rule.setTag( ruleName );
		rule.setDescription( _config.getString(path + ".description", ""));
		rule.setDefault( _config.getBoolean( path + ".denyUnlisted", defaultDenyUnlisted ) );
		rule.setMin( _config.getInt( path + ".minLevel", 0) );
		rule.setMax( _config.getInt( path + ".maxLevel", 0) );
		rule.setMinMessage( _config.getString( path + ".minMessage", defaultMinMessage ) );
		rule.setMaxMessage( _config.getString( path + ".maxMessage", defaultMaxMessage ) );
		rule.setOtherMessage( _config.getString( path + ".otherMessage", defaultOtherMessage ) );
		
		if ( _config.contains(path + ".actions") ) {
			
			List<String> actions = _config.getStringList( path + ".actions" );
			
			if ( actions.contains("use") ) rule.addAction( Actions.USE );
			if ( actions.contains("break") ) rule.addAction( Actions.BREAK );
			if ( actions.contains("place") ) rule.addAction( Actions.PLACE );
			if ( actions.contains("craft") ) rule.addAction( Actions.CRAFT );
			if ( actions.contains("pickup") ) rule.addAction( Actions.PICKUP );
			
		}
		
		if ( _config.contains( path + ".items" ) ) {
			
			List<Integer> items = _config.getIntegerList( path + ".items");
			rule.addItems( items );
			
		}  			
		
		plugin.rules.addRule(ruleName, rule);    	
    	
    }
    
    public void loadRules() {
    	
    	plugin.rules.clear();
    	
    	if ( _config.contains("rules") ) {
    		
    		for( String tag: _config.getConfigurationSection("rules").getKeys( false ) ) {
    			
    			loadRule( tag );
    			
    		} 
    	}    	
    	
    }
    
    
    private void loadConfig() {
   	
        defaultMinMessage = _config.getString("defaultMinMessage", "");
        defaultMaxMessage = _config.getString("defaultMaxMessage", "");
        defaultOtherMessage = _config.getString("defaultOtherMessage", "");
        defaultDenyUnlisted = _config.getBoolean("defaultDenyUnlisted", false );    	
        playerKeepItemLevelOnXPLoss = _config.getBoolean("playerKeepItemLevelOnXPLoss", true );
        displayRestrictionsAsList = _config.getBoolean("displayRestrictionsAsList", true );
        candoPrefix = _config.getString("candoPrefix", "No longer restricted: ");
        cantdoPrefix = _config.getString("cantdoPrefix", "Restricted: ");
        candoNowPrefix = _config.getString("candoNowPrefix", "You can now ");
        candoNextPrefix = _config.getString("candoNextPrefix", "Next level you can ");        
        displayPlayerCanDoNowOnLevelChange = _config.getBoolean("displayPlayerCanDoNowOnLevelChange", true);
        displayPlayerCanDoNextOnLevelChange = _config.getBoolean("displayPlayerCanDoNowOnLevelChange", true );
        candoPreviousPrefix = _config.getString("candoPreviousPrefix", "You used to be able to ");
        cantdoPreviousPrefix = _config.getString("cantdoPreviousPrefix", "You used to not be able to ");
        this.checkPlayerDataFolder();
        
        loadRules();
        
    } 
	
	
}
