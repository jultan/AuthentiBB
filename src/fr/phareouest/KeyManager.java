package fr.phareouest;
import java.util.Hashtable;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;


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
	
	public void set(String key, Object value){
		keyHash.put(key, value);
	}
	
	public void commit(){
		persistentObject.commit();
	}
}