package com.github.minGeek.LevelRestrictions.Commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.github.mineGeek.LevelRestrictions.LevelRestrictions;

abstract class Confirmation extends CommandBase {

	public Confirmation(LevelRestrictions plugin) {
		super(plugin);
	}

	private Map<String, ConfirmationStruct > _confirmations = new HashMap<String, ConfirmationStruct>();
	
	protected void confirmationAdd( String playerName, String value ) {
		
		_confirmations.put( playerName, new ConfirmationStruct( value ) );
		
	}
	
	protected Boolean confirmationHas( String playerName ) {
		
		if ( this._confirmations.containsKey( playerName ) ) {
			return ( this._confirmations.get( playerName ).expired() );
		} else {
			return false;
		}
	}
	
	protected String confirmationValue( String playerName ) {
	
		if ( this._confirmations.containsKey( playerName ) ) {
			return this._confirmations.get( playerName ).value;
		} else {
			return null;
		}
		
	}
	
	protected String confirmValueAndClear( String playerName ) {
		String result = null;
		
		if( this.confirmationHas( playerName ) ) {
			result = this._confirmations.get( playerName ).value;
			this.confirmationClear( playerName );
		}
		
		return result;
	}
	
	protected String confirmationValue( String playerName, Boolean clear ) {
		
		String result = this.confirmationValue( playerName );
		if ( clear ) this.confirmationClear( playerName );
		return result;
		
	}
	
	protected Boolean confirmationOnce( String playerName ) {
		
		Boolean result = confirmationHas( playerName );
		this.confirmationClear( playerName );
		return result;
		
	}
	
	protected void confirmationGc() {
		
		if ( this._confirmations.size() > 8 ) {
		
			Iterator<Entry<String, ConfirmationStruct>> i = this._confirmations.entrySet().iterator();
			
			while ( i.hasNext() ) {
				Entry<String, ConfirmationStruct> y = i.next();
				if ( y.getValue().expired() ) {
					this._confirmations.remove( y.getKey() );
				}
			}
		
		}
		
	}
	
	protected void confirmationClear() {
		this._confirmations.clear();
	}
	
	protected void confirmationClear( String playerName ) {
		this._confirmations.remove( playerName );
	}
	
	
}
