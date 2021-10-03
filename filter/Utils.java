package aed.filter;

import java.util.Iterator;
import java.util.function.Predicate;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;


public class Utils {

  public static <E> Iterable<E> filter(Iterable<E> d, Predicate<E> pred) {
	  
	  //estructura iterable de entrada
	  if(d == null) throw new IllegalArgumentException();
	  
	  //iterar sobre d
	  Iterator<E> itd = d.iterator();	
	  
	  //nueva estructura iterable
	  PositionList<E> r = new NodePositionList<E>();
	  
	  //mientras haya elementos para iterar sobre d, se ejecuta el while
	  while(itd.hasNext()) {
		  
		  E el = itd.next();
		  
		  //si el es null, no lo anadimos a r. Siguiente iteracion
		  if(el == null) continue;
			
		  //anadimos el a r
		  else if(pred.test(el)) {
			  r.addLast(el);
		  }
		  
	  }
	  return r;
	  
  }
 
  
}

