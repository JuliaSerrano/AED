package aed.loops;

public class Utils {
  public static int maxNumRepeated(Integer[] a, Integer elem)  {
      // Hay que modificar este metodo
	  int maxCount = 0; 
	  int actCount = 0;
	  int i = 0;
	  if(a == null || a.length == 0) {
		  return 0;
	  }
	  while(i < a.length) {
		if( a[i].equals(elem)) {
			actCount++;
			i++;
			if(actCount > maxCount) {
				maxCount = actCount;
			}
		}
		else {
			actCount = 0;
			i++;
				  
		}
			  
	}
	return maxCount;  
  }
	  
	  
}
