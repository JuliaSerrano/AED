package aed.orderedmap;

import java.util.Comparator;

import es.upm.aedlib.Entry;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.positionlist.NodePositionList;

public class PositionListOrderedMap<K,V> implements OrderedMap<K,V> {
    private Comparator<K> cmp;
    private PositionList<Entry<K,V>> elements;
  
    /* Acabar de codificar el constructor */
    public PositionListOrderedMap(Comparator<K> cmp) {
	this.cmp = cmp;
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

    private Position<Entry<K,V>> findKeyPlace(K key) {
	return null;
    }

    /* Podéis añadir más métodos auxiliares */
  
    public boolean containsKey(K key) {
	return false;
    }
  
    public V get(K key) {
	return null;
    }
  
    public V put(K key, V value) {
	return null;
    }
  
    public V remove(K key) {
	return null;
    }
  
    public int size() {
	return 0;
    }
  
    public boolean isEmpty() {
	return false;
    }
  
    public Entry<K,V> floorEntry(K key) {
	return null;
    }
  
    public Entry<K,V> ceilingEntry(K key) {
	return null;
    }
  
    public Iterable<K> keys() {
	return null;
    }
  
    public String toString() {
	return elements.toString();
    }
 
  
}
