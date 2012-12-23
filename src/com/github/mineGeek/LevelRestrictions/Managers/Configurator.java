package com.github.mineGeek.LevelRestrictions.Managers;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import com.github.mineGeek.LevelRestrictions.LevelRestrictions;
import com.github.mineGeek.LevelRestrictions.Managers.Rule.Actions;


public class Configurator {


    private static LevelRestrictions _plugin;
    private static FileConfiguration _config;
    
    public FileConfiguration getConfigFile() {
        return _config;
    }
    
    public Configurator( LevelRestrictions plugin ) {
    	
    	plugin.saveDefaultConfig();
        _plugin = plugin;
        _config = _plugin.getConfig();
        this.loadConfig();
        
    }
    
    private void loadConfig() {

    	LevelRestrictions.Rules = new Rules();
    	
    	String defaultMinMessage = _config.getString("defaultMinMessage", "");
    	String defaultMaxMessage = _config.getString("defaultMaxMessage", "");
    	String defaultOtherMessage = _config.getString("defaultOtherMessage", "");
    	Boolean defaultDenyUnlisted = _config.getBoolean("defaultDenyUnlisted", false );
    	
    	if ( _config.contains("rules") ) {
    		
    		for( String tag: _config.getConfigurationSection("rules").getKeys( false ) ) {
    			
    			String path = "rules." + tag;
    			
    			Rule rule = new Rule();
    			rule.setTag( tag );
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
    			
    			LevelRestrictions.Rules.addRule(rule);
    			
    		}
    		
    		
    	}
    	

    } 
	
	
}
