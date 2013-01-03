package com.github.mineGeek.LevelRestrictions.DataStore;

import org.bukkit.entity.Player;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;

public class PlayerStoreItem extends DataStore {
	
	
	public PlayerStoreItem( LevelRestrictions plugin, Player player ) {
		
		super( plugin );
		this.setFileName( player.getName() );
		this.load();
		if ( this.getLevel() < player.getLevel() ) this.setLevel( player.getLevel() );
		
	}
	
	public void incrimentLevel( Integer level ) {
		this.setLevel( this.getLevel() + level );
	}
	
	public void incrimentLevel() {
		this.incrimentLevel( 1 );
	}
	
	public void setLevel( Integer level ) {
		this.set("level", level);
	}
	
	public void setLevel( Integer level, Boolean onlyHigherCheck ) {
		
		if ( onlyHigherCheck ) {
			if ( level > this.getLevel() ) {
				this.setPreviousLevel( this.getLevel() );
				this.setLevel( level );
			}
		} else {
			this.setPreviousLevel( this.getLevel() );
			this.setLevel( level );
		}
		
	}
	
	public void setPreviousLevel( Integer level ) {
		this.set("previousLevel", level);
	}
	
	public Integer getPreviousLevel() {
		return this.getAsInteger("previousLevel", 0);
	}
	
	public Integer getLevel() {
		return this.getAsInteger("level", 0 );
	}
	

}
