package aed.orderedmap;

import java.util.Comparator;

//import aed.bancofiel.Cuenta;
import es.upm.aedlib.Entry;
import es.upm.aedlib.Position;
import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.positionlist.NodePositionList;

public class PositionListOrderedMap<K,V> implements OrderedMap<K,V> {
    private Comparator<K> cmp;
    private PositionList<Entry<K,V>> elements;
  
    /* Acabar de codificar el constructor */
    public PositionListOrderedMap(Comparator<K> cmp) {
    	this.cmp = cmp;
    	elements = new NodePositionList<Entry<K,V>>();
    }

    /* Ejemplo de un posible método auxiliar: */
  
    /* If key is in the map, return the position of the corresponding
     * entry.  Otherwise, return the position of the entry which
     * should follow that of key.  If that entry is not in the map,
     * return null.  Examples: assume key = 2, and l is the list of
     * keys in the map.  For l = [], return null; for l = [1], return
     * null; for l = [2], return a ref. to '2'; for l = [3], return a
     * reference to [3]; for l = [0,1], return null; for l = [2,3],
     * return a reference to '2'; for l = [1,3], return a reference to
     * '3'. */

    private Position<Entry<K,V>> findKeyPlace(K key) throws IllegalArgumentException {
    	if(elements.isEmpty())
    		return null;
    	
    	if(key == null)
    		throw new IllegalArgumentException();
    		
    	Position<Entry<K,V>> pos = elements.first();
    		
    	for(int i = 0; i < elements.size(); i++) {
    		Entry<K,V> entry = pos.element();
    			
    		if(cmp.compare(entry.getKey(), key) == 0)
    			return pos;
    		else
    			pos = elements.next(pos);
    	}
    		
    	return null;
    }
    
    /* Podéis añadir más métodos auxiliares */
    
    @Override
    public boolean containsKey(K key) throws IllegalArgumentException {
    	if(key == null)
    		throw new IllegalArgumentException();

    	if (findKeyPlace(key) != null) {
    		return true;
    	}

    	return false;
    }
    
    
    
    @Override
    public V get(K key) {
    	if(containsKey(key)) {
    		Entry<K,V> entry = findKeyPlace(key).element();
    		return entry.getValue();
    	}
    	
    	else
    		return null;
    }
    
    @Override
    public V put(K key, V value){
    	Entry<K,V> entry = new EntryImpl<K,V>(key, value);
    	Position<Entry<K,V>> pos = findKeyPlace(key);
    	
    	if(!containsKey(key)) {
    		elements.addLast(entry);
    		return null;
    	}
    	
    	else {
        	elements.addBefore(pos, entry);
        	V valor = pos.element().getValue();
        	elements.remove(pos);
    		return valor;
    	}
    }
    
    @Override
    public V remove(K key) {
    	Position<Entry<K,V>> pos = findKeyPlace(key);
    	
    	if(pos == null)
    		return null;
    	
    	V valor = pos.element().getValue();
    	elements.remove(pos);
		return valor;
    }
    
    @Override
    public int size() {
    	return elements.size();
    }
    
    @Override
    public boolean isEmpty() {
    	if(elements.size() > 0)
    		return false;
    	else
    		return true;
    }
    
    @Override
    public Entry<K,V> floorEntry(K key) throws IllegalArgumentException {
    	Position<Entry<K,V>> pos = elements.first();
    	Entry<K,V> equalEntry = null;
    	Entry<K,V> lesserEntry = null;
    	
    	if(key == null)
    		throw new IllegalArgumentException();
    	
    	for(int i = 0; i < elements.size(); i++) {
    		equalEntry = pos.element();
    		
    		if(cmp.compare(equalEntry.getKey(), key) == 0)
    			return equalEntry;
    		
    		else if(cmp.compare(equalEntry.getKey(), key) < 0 && (lesserEntry == null || cmp.compare(lesserEntry.getKey(), equalEntry.getKey()) < 0)) {
    			lesserEntry = equalEntry;
    			pos = elements.next(pos);
    		}
    		
    		else
    			pos = elements.next(pos);
    	}
    	return lesserEntry;
    }
    
    @Override
    public Entry<K,V> ceilingEntry(K key) throws IllegalArgumentException {
    	Position<Entry<K,V>> pos = elements.first();
    	Entry<K,V> equalEntry = null;
    	Entry<K,V> greaterEntry = null;
    	
    	if(key == null)
    		throw new IllegalArgumentException();
    	
    	for(int i = 0; i < elements.size(); i++) {
    		equalEntry = pos.element();
    		
    		if(cmp.compare(equalEntry.getKey(), key) == 0)
    			return equalEntry;
    		
    		else if(cmp.compare(equalEntry.getKey(), key) > 0 && (greaterEntry == null || cmp.compare(greaterEntry.getKey(), equalEntry.getKey()) > 0)) {
    			greaterEntry = equalEntry;
    			pos = elements.next(pos);
    		}
    		
    		else
    			pos = elements.next(pos);
    	}
    	
    	return greaterEntry;
    }
    
    
    @Override
    public Iterable<K> keys() {
    	PositionList<K> keyList = new NodePositionList<K>();
    	Position<Entry<K,V>> pos = elements.first();
    	
    	for(int i = 0; i < elements.size(); i++) {
    		Entry<K,V> entry = pos.element();
    		
    		if(keyList.first() == null) {
    			keyList.addFirst(entry.getKey());
    		}
    		
    		else {
    			keyList.addLast(entry.getKey());
    		}
    		
    		pos = elements.next(pos);
    	}
    	
    	return keyList;
    }
  
    public String toString() {
    	return elements.toString();
    }
}

