package com.github.mineGeek.LevelRestrictions.DataStore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;


public class DataStore {

	public Map<String, Object> data = new HashMap<String, Object>();
	
	private String _fileName 	= "playerData";
	private String _fileExt 	= "bin";
	private LevelRestrictions	plugin;
	public String dataFolder;
	public Boolean active = false;
	
	public DataStore( LevelRestrictions plugin ) {
		this.plugin = plugin;
	}
	
	public void set( String ColumnName, Object value ) {
		
		this.data.put(ColumnName, value);
	}
	
	public Object get( String columnName ) {
		return this.data.get( columnName );
	}
	
	public Integer getAsInteger( String columnName, Integer defaultValue ) {
		
		Object value = this.get( columnName );
		
		if ( value == null ) return defaultValue;
		return (Integer)this.get( columnName );
		
	}
	
	public Boolean getAsBoolean( String columnName, Boolean defaultValue ) {
		Object value = this.get( columnName );
		if ( value == null ) return defaultValue;
		return (Boolean)value;
	}
	
	public String getAsString( String columnName, String defaultValue ) {
		Object value = this.get( columnName );
		if ( value == null ) return defaultValue;
		return value.toString();
	}
	
	public String getFileName() {
		return this._fileName;
	}
	
	public void setFileName( String value ) {
		this._fileName = value;
	}
	
	public String getFileExt() {
		return this._fileExt;
	}
	
	public void setFileExt( String value ) {
		this._fileExt = value;
	}
	

	public String getFullFileName() {

		String a = "";

			a = this.plugin.config.getDataFolder() + File.separator + "players" +  File.separator + this.getFileName() + "." + this.getFileExt();

		return a;
	}
	
	public Boolean save() {
		
		//if ( !this.plugin.config.playerKeepItemLevelOnXPLoss ) return false;
		
    	try {
			 SLAPI.save( this.data, this.getFullFileName() );
			 return true;
		} catch (Exception e) { e.printStackTrace(); }		
		
    	return false;
	}
	
	public Boolean load() {
		
		//if ( !this.plugin.config.playerKeepItemLevelOnXPLoss ) return false;
		
		try {
			this.data = SLAPI.load( this.getFullFileName() );
			return true;
		} catch ( Exception e ) {}
		
		return false;
		
	}
	
}
