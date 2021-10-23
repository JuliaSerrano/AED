package aed.recursion;

import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.indexedlist.*;
import es.upm.aedlib.positionlist.*;


public class Utils {

	//multiplicacion 2 numeros con recursividad
  public static int multiply(int a, int b) {
    
	  //si alguno de los enteros es 0
	  if(a == 0 || b == 0) {
		  return 0;
	  }
	  
	  //sumo b veces a
	  else if(b > 0) {
		  return a + multiply(a,b-1);
	  }
	  //si b es negativo
	  return -multiply(a,-b);
  }	  
  //devuelve un indice de list que corresponde a un hoyo(elemento que NO es mayor que sus vecinos)
  public static <E extends Comparable<E>> int findBottom(IndexedList<E> l) {
    
	//tamanio subarray == 1 ----> es hoyo
	  if(l.size() ==1) return 0;
	  //primer y ultimo elemento
	  if(esHoyo(l,0)) return 0;
	  if(esHoyo(l,l.size()-1)) return l.size()-1;
	  
	  //tamanio subarray ==2 ---> mayor elemento = hoyo
	  else if(l.size()==2) {
		  E elStart = l.get(0);
		  E elEnd = l.get(1);
		  int res = elStart.compareTo(elEnd);
		  //start < end -----> hoyo ends
		  if(res < 0) return 0;
		  //start >= end ---> hoyo starts
		  return 1;
	  }
	//tamanio subarray >= 3. Binary search
	  else {
		  int left = 0;
		  int right = l.size()-1;
		  while(left <= right) {
			  int mid = left + (right - left)/2;
			  //if hoyo esta en el medio
			  if(esHoyo(l,mid)) return mid;
			  //left subarray
			  if(l.get(left).compareTo(l.get(mid))<0) {
				  right = mid -1;
			  }
			  else {
				  left = mid +1;
			  }
		  }
		  
	  }
	  
	  
	  
	  return -1;
    //hacer
    
  }
  public static <E extends Comparable<E>> boolean esHoyo(IndexedList<E> l, int index) {
	//un hoyo es un  elemento que no es mayor que sus vecinos
	  
	  
	  //primer elemento
	  if (index == 0) {
		  int comp = l.get(index).compareTo(l.get(index+1));
		  if(comp<= 0) return true;
		  return false;
	  }
	  //ultimo elemento
	  if(index == l.size()-1) {
		  int comp = l.get(index).compareTo(l.get(index-1));
		  if(comp <=0 ) return true;
		  return false;
	  }
	  
	  //cualquier otro elemento
	  E anterior = l.get(index-1);
	  E actual = l.get(index);
	  E sig = l.get(index +1);
	  //comparamos elementos
	  int comp1 = anterior.compareTo(actual);
	  int comp2 = sig.compareTo(actual);
	  
	  if(comp1 >= 0 && comp2 >= 0) return true;
	  return false;
  }

  
  
	
  public static <E extends Comparable<E>> NodePositionList<Pair<E,Integer>>
    joinMultiSets(NodePositionList<Pair<E,Integer>> l1,
		  NodePositionList<Pair<E,Integer>> l2) {
	  //hacer
	  //permitido crear nuevas NodePosotionList para los resultados a devolver y/o metodos aux
	  
    return null;
  }
}
