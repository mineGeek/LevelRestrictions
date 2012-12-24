package com.github.mineGeek.LevelRestrictions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.mineGeek.LevelRestrictions.Events.RulesListener;
import com.github.mineGeek.LevelRestrictions.Integrators.FactionsPlayer;
import com.github.mineGeek.LevelRestrictions.Managers.Commands;
import com.github.mineGeek.LevelRestrictions.Managers.Configurator;
import com.github.mineGeek.LevelRestrictions.Rules.Rules;

public class LevelRestrictions extends JavaPlugin {

	public static Configurator Config;
	public static Rules Rules;
	private Commands _commands;
	public static FactionsPlayer iFactions;
	
    @Override
    public void onDisable() {
        getLogger().info( this.getName() + " disabled." );
    }	
    
    @Override
    public void onEnable() {
    	
    	LevelRestrictions.Rules 	= new Rules();
    	LevelRestrictions.Config 	= new Configurator( this );
    	Bukkit.getPluginManager().registerEvents( new RulesListener(), this);
    	
    	_commands = new Commands( this );
    	
    	getCommand("lrcan").setExecutor( _commands );
    	getCommand("lrcant").setExecutor( _commands );
    	getCommand("lrfull").setExecutor( _commands );
    	getCommand("lrcreate").setExecutor( _commands );
    	getCommand("lrkillrule").setExecutor( _commands );
    	getCommand("lrdump").setExecutor( _commands );
    	getCommand("lredit").setExecutor( _commands );
    	getLogger().info( this.getName() + " enabled." );
    	
    	LevelRestrictions.iFactions = new FactionsPlayer();
    	
    	
    }
    
     
	
}
