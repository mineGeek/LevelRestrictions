package com.github.mineGeek.LevelRestrictions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.minGeek.LevelRestrictions.Commands.CreateRule;
import com.github.minGeek.LevelRestrictions.Commands.Dump;
import com.github.minGeek.LevelRestrictions.Commands.EditRule;
import com.github.minGeek.LevelRestrictions.Commands.ItemLevel;
import com.github.minGeek.LevelRestrictions.Commands.KillRule;
import com.github.minGeek.LevelRestrictions.Commands.RulesAvailable;
import com.github.minGeek.LevelRestrictions.Commands.RulesFull;
import com.github.mineGeek.LevelRestrictions.DataStore.PlayerStore;
import com.github.mineGeek.LevelRestrictions.Events.RulesListener;
import com.github.mineGeek.LevelRestrictions.Integrators.FactionsPlayer;
import com.github.mineGeek.LevelRestrictions.Managers.Configurator;
import com.github.mineGeek.LevelRestrictions.Rules.Rules;
import com.github.mineGeek.LevelRestrictions.Utilities.Info;

public class LevelRestrictions extends JavaPlugin {

	public 	Configurator config;
	public 	Info 		info;
	public 	Rules 		rules;
	
    @Override
    public void onDisable() {
    	this.config.saveConfig();
    	PlayerStore.saveOnline();
        getLogger().info( this.getName() + " disabled." );
    }	
    
    @Override
    public void onEnable() {
    	
    	PlayerStore.setPlugin( this );
		this.rules = new Rules( this );
		this.info = new Info( this );
    	this.config = new Configurator( this );
    	
    	
    	Bukkit.getPluginManager().registerEvents( new RulesListener( this ), this);

    	RulesAvailable ra = new RulesAvailable( this );
    	getCommand("lrcan").setExecutor( ra );
    	getCommand("lrcant").setExecutor( ra );
    	getCommand("lrfull").setExecutor( new RulesFull( this ) );
    	getCommand("lrcreate").setExecutor( new CreateRule( this ) );
    	getCommand("lrkillrule").setExecutor( new KillRule( this ) );
    	getCommand("lrdump").setExecutor( new Dump( this ) );
    	getCommand("lredit").setExecutor( new EditRule( this ) );
    	getCommand("lrlevel").setExecutor( new ItemLevel( this ) );
    	
    	PlayerStore.loadOnline();
    	FactionsPlayer.FactionsPlayerEnable();
    	getLogger().info( this.getName() + " enabled with " + this.rules.getRules().size() + " rules loaded." );
    	
    	
    	
    }
    
     
	
}
