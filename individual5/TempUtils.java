package aed.individual5;

import es.upm.aedlib.Pair;
import es.upm.aedlib.map.*;


public class TempUtils {
  public static Map<String,Integer> maxTemperatures(long startTime,
                                                    long endTime,
                                                    TempData[] tempData) {
	  
	//nuevo map resultado
	  Map <String,Integer> map = new HashTableMap <>() ;
	  
	  //recorrer array tempData
	  for(TempData k: tempData) {
		  //esta en el intervalo de tiempos dado
		  if(k.getTime()>= startTime && k.getTime()<= endTime) {
			  
			  
			  //entrada no existe
			  if(!map.containsKey(k.getLocation())) {
				  map.put(k.getLocation(), k.getTemperature());
			  }
			  
			  //entrada existe 
			  else if(map.containsKey(k.getLocation())) {
				  
				 //temperatura > existente, introducimos nueva temp
				  if(k.getTemperature() > map.get(k.getLocation())) {
					  map.put(k.getLocation(), k.getTemperature());
				  }
				  
				  
			  }
		  }
	  }
	  
    return map;
  }


  public static Pair<String,Integer> maxTemperatureInComunidad(long startTime,
                                                               long endTime,
                                                               String region,
                                                               TempData[] tempData,                           
                                                               Map<String,String> comunidadMap) {
	  
	  //array vacio
	  if(tempData == null || tempData.length == 0) return null;
	  
	  //par resultado
	  Pair<String, Integer> res = new Pair<>(null, 0);
	  
	  //mapa con temp tomadas dentro del intervalo dado. 
	  Map <String,Integer> map = new HashTableMap <>() ;
	  map = maxTemperatures(startTime,endTime,tempData);
	  
	  //no hay ciudades dentro del intervalo
	  if(map.isEmpty()) return null;
	  
	  //ciudades tienen que estar en la comunidad
	  for(String ciudad : map.keys()) {
		  
		  //temperatura
		  Integer temp = map.get(ciudad);
		  
		  //ciudad es de la comunidad dada y su temperatura es mayor
		  if(comunidadMap.get(ciudad).equals(region) && (temp.compareTo(res.getRight())>0)) {
			  //ciudad
			  res.setLeft(ciudad);
			  //temperatura
			  res.setRight(map.get(ciudad));
			  
		  }
	  }
	  
	  
	  
	  
	  
	  return res;
  }

}
