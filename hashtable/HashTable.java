package aed.hashtable;

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Arrays;

import es.upm.aedlib.Entry;
import es.upm.aedlib.EntryImpl;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.InvalidKeyException;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;


/**
 * A hash table implementing using open addressing to handle key collisions.
 */
public class HashTable<K,V> implements Map<K,V> {
  Entry<K,V>[] buckets;
  int size;

  public HashTable(int initialSize) {
    this.buckets = createArray(initialSize);
    this.size = 0;
  }

  /**
   * Add here the method necessary to implement the Map api, and
   * any auxilliary methods you deem convient.
   */

  // Examples of auxilliary methods: IT IS NOT REQUIRED TO IMPLEMENT THEM
  
  @SuppressWarnings("unchecked") 
  private Entry<K,V>[] createArray(int size) {
   Entry<K,V>[] buckets = (Entry<K,V>[]) new Entry[size];
   return buckets;
  }

  // Returns the bucket index of an object
  private int index(Object obj) {
    
    int code = obj.hashCode();
    if(code==0) return 0;
    return Math.abs(code%size);
    
    
	  
	  
  }

  // Returns the index where an entry with the key is located,
  // or if no such entry exists, the "next" bucket with no entry,
  // or if all buckets stores an entry, -1 is returned.
  private int search(Object obj) {
	int index = index(obj);
    
    //bucket vacio
    if(buckets[index] == null) {
    	return index;
    }
    //bucket no vacio, buscamos index para insertar
    index++;
    int num = 0;
    for(int i = 0;i<size;i++) {
    	
    	try {
    		num = (index+i) % size;
    	}
    	catch(ArithmeticException e) {
    		num = 0;
    	}
    	finally {
    		if((buckets[num]) == null ){
        		if(num > 0){
        			return num;
        		}
        		return index +i;
        		
        	}
    	}
    	
    }
	 //all buckets store an entry 
	  return -1;
  }

  // Doubles the size of the bucket array, and inserts all entries present
  // in the old bucket array into the new bucket array, in their correct
  // places. Remember that the index of an entry will likely change in
  // the new array, as the size of the array changes.
  private void rehash() {
	  
	  //new array, size doubled
	  int size = this.size *2;
	  Entry<K,V>[] buckets = createArray(size);
	  Entry<K,V>[] oldBuckets = this.buckets;
	  this.buckets = buckets;
	  
	  //insert components of old array to new one
	  for (Entry<K, V> entry : oldBuckets) {
		  int index = search(entry.getKey());
		  buckets[index] = entry;
	  }
	  
	  
	  this.buckets = buckets;
	  this.size = size;
	  
  }

@Override
public Iterator<Entry<K, V>> iterator() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean containsKey(Object arg0) throws InvalidKeyException {
	if(arg0 == null)
		throw new InvalidKeyException();
	
	for (Entry<K, V> entry : buckets) {
		if(entry!= null) {
			if(entry.getKey().equals(arg0))
				return true;
		}
	}
	
	return false;
}
@Override
public Iterable<Entry<K, V>> entries() {
	  PositionList<Entry<K, V>> keyList = new NodePositionList<Entry<K, V>>();
	  
	  for(Entry<K, V> entry : buckets) {
		  if(entry != null)
			  keyList.addLast(entry);
	  }
	  
	  return keyList;
}

@Override
public V get(K arg0) throws InvalidKeyException {
	if(!containsKey(arg0))
		return null;
	
	for (Entry<K, V> entry : buckets) {
		if(entry!= null) {
			if(entry.getKey().equals(arg0))
				return entry.getValue();
		}
	}
	
	return null;
}
@Override
public boolean isEmpty() {
	  if(size == 0)
		  return true;
	  else
		  return false;
}

@Override
public Iterable<K> keys() {
	  PositionList<K> keyList = new NodePositionList<K>();
	  
	  for(Entry<K, V> entry : buckets) {
		  if(entry != null)
			  keyList.addLast(entry.getKey());
	  }
	  
	  return keyList;
}
@Override
public V put(K arg0, V arg1) throws InvalidKeyException {
	if(arg0==null) throw new InvalidKeyException();
	
	
	//rehash if necessary
	int index = search(arg0);
	Entry<K,V> el = new EntryImpl(arg0,arg1);
	if(index == -1) {
		rehash();
	}
	
	//no previous value for K arg0
	if(containsKey(arg0) == false) {
		buckets[index] = el;
		return null;
	}
	
	//previous value for key
	else {
		V res = get(arg0);
		buckets[index] = el;
		return res;
		
	}
	
	
	
	
	
}

@Override
public V remove(K arg0) throws InvalidKeyException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int size() {
	return size;
}
  
}

