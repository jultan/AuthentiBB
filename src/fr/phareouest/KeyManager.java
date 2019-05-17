package fr.phareouest;
import java.util.Enumeration;
import java.util.Hashtable;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.component.ButtonField;


class KeyManager {
	private PersistentObject persistentObject;
	private Hashtable keyHash;

		
	public KeyManager(){
		persistentObject= PersistentStore.getPersistentObject(0x9787015f06321e7cL);
		
		synchronized(persistentObject){
			keyHash = (Hashtable)persistentObject.getContents();
			if (null == keyHash){
				keyHash = new Hashtable();
				persistentObject.setContents(keyHash);
				persistentObject.commit();
			}
		}
	}
	
	public Object get(String key) {
		return keyHash.get(key);
	}
	
	public Object remove(String key) {
		return keyHash.remove(key);
	}
	
	public String firstKey() {
		Enumeration en = keyHash.keys();
		String key = en.nextElement().toString();
		return key;
	}
	
	
	public int size() {
		return keyHash.size();
	}
	
	public void set(String key, Object value){
		keyHash.put(key, value);
	}
	
	public Enumeration keys(){
		return keyHash.keys();
	}
	
	public void commit(){
		persistentObject.commit();
	}
}