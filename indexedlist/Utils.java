package aed.indexedlist;
import es.upm.aedlib.indexedlist.*;

public class Utils {
  public static <E> IndexedList<E> deleteRepeated(IndexedList<E> l) {
      // Hay que modificar este metodo
	  if(l.isEmpty() || l == null) {
		  return null;
	  }
	  
	  //new list
	  IndexedList<E> res = new ArrayIndexedList<E>();
	  
	  int i = 0;
	  for(E elem : l) {
		  //indexOf() returns -1 if the element doesn't exist
		  //if the element in l doesn't exist in list, we add it
		  if(res.indexOf(elem) == (-1)) {
			  res.add(i,elem);
			  i++;
		  }
		  
	  }
	  return res;
  }

	  
	  
	  
	  
  
  
}
