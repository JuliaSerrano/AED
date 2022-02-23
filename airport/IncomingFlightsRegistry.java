package aed.airport;


//import aed.bancofiel.Cuenta;
import es.upm.aedlib.Entry;
import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.priorityqueue.*;
import es.upm.aedlib.map.*;
import es.upm.aedlib.positionlist.*;


/**
 * A registry which organizes information on airplane arrivals.
 */
public class IncomingFlightsRegistry {

	
	public PositionList<Pair<String,Long>> registro;
  /**
   * Constructs an class instance.
   */
  public IncomingFlightsRegistry() {
	  
	  this.registro =  new NodePositionList<Pair<String,Long>>();
  }

  /**
   * A flight is predicted to arrive at an arrival time (in seconds).
   */
  public void arrivesAt(String flight, long time) {
	  
	  
	  Position<Pair<String,Long>> pos = find(this.registro,flight);
	  Pair<String,Long> pair = new Pair<>(flight,time);
	  //avion ya registrado. cambiar hora de llegada
	  if(pos!= null) {
		  
		  this.registro.set(pos, pair);
	  }
	  //avion  no registrado, aniadir a registro
	  else {
		  this.registro.addLast(pair);
	  }
	  
  }
  
  //metodo auxiliar que devuelve la pos de un vuelo en el registro o null si no esta en el
  public Position<Pair<String,Long>> find(PositionList<Pair<String,Long>> list, String flight){
	  
	  
	  
	  Position<Pair<String,Long>> cursor = list.first();
	  for(int i=0;i<list.size();i++) {
		  if(cursor.element().getLeft().equals(flight)) {
			  return cursor;
		  }
		  cursor = list.next(cursor); 
	  }
	  return null;
  }

  /**
   * A flight has been diverted, i.e., will not arrive at the airport.
   */
  public void flightDiverted(String flight) {
	  Position<Pair<String,Long>> pos = find(this.registro,flight);
	  //avion existe, borrar de registro
	  if(pos!= null) {
		  this.registro.remove(pos);
	  }
  }

  /**
   * Returns the arrival time of the flight.
   * @return the arrival time for the flight, or null if the flight is not predicted
   * to arrive.
   */
  public Long arrivalTime(String flight) {
	  
	  Position<Pair<String,Long>> pos = find(this.registro,flight);
	  
	  //avion registrado
	  if(pos!= null) return pos.element().getRight();
	  //avion no registrado
	  return null;
	  
    
  }

  /**
   * Returns a list of "soon" arriving flights, i.e., if any 
   * is predicted to arrive at the airport within nowTime+180
   * then adds the predicted earliest arriving flight to the list to return, 
   * and removes it from the registry.
   * Moreover, also adds to the returned list, in order of arrival time, 
   * any other flights arriving withinfirstArrivalTime+120; these flights are 
   * also removed from the queue of incoming flights.
   * @return a list of soon arriving flights.
   */
  public PositionList<FlightArrival> arriving(long nowTime) {
	  
	  PositionList<FlightArrival> res = new NodePositionList<>();
	  //if(this.registro == null) return res;
	  //ordenamos lista por orden de llegada
	  this.registro = ordered(this.registro);
	  
	  //cursor inicio registro
	  Position<Pair<String,Long>> cursor = this.registro.first();
	  
	  //mientras haya elementos en la lista iteramos sobre ella	  
	  while(cursor!= null) {
		  
		
		  long horaAvion = cursor.element().getRight();
		  
		  //avion a punto de aterrizar
		  if( horaAvion >= nowTime && horaAvion<= (nowTime + 180)) {
			
			  //aniadir avion 
			  FlightArrival primero = new FlightArrival(cursor.element().getLeft(),
					cursor.element().getRight());
			  res.addLast(primero);
			  long horaPrimer = primero.arrivalTime();
			  
			  //eliminamos de registro
			  Position<Pair<String,Long>> aux = this.registro.next(cursor);
			  this.registro.remove(cursor);
			  cursor = aux;
			  
			  
			while(cursor!= null) {
				
				long horaConf = cursor.element().getRight();
				//aviones en conflicto
				if(horaConf >= horaPrimer && horaConf <= (horaPrimer +120)) {
					//aniadir avion
					FlightArrival conflicto = new FlightArrival(cursor.element().getLeft(),
							cursor.element().getRight());
					res.addLast(conflicto);
					
					//eliminamos de registro
					Position<Pair<String,Long>> aux2 = this.registro.next(cursor);
					this.registro.remove(cursor);
					cursor = aux2;
				}
				else {
					return res;
				}
			}
		  }
		  else {
			  cursor = this.registro.next(cursor);
		  }
	 
	  
	  }
	  return res;
	  

    
    
    
    
  }
  
  //metodo auxiliar para ordenar por arrivalTime los vuelos
  public PositionList<Pair<String,Long>> ordered(PositionList<Pair<String,Long>> list){
	  if(list.isEmpty() || list.size() < 2)
		  return list;
	   
	  int pos = 0;
	  boolean[] tiemposUtilizados = new boolean[list.size()];
	  
	  PositionList<Pair<String,Long>> tiemposOrdenados = new NodePositionList<Pair<String,Long>>();
	  Position<Pair<String,Long>> c1 = list.first();
	  Position<Pair<String,Long>> c2 = null;
	  Pair<String,Long> pair = null;
	  Pair<String,Long> menorPair = c1.element();
	  
	  for (int i = 0; c1 != null; i++) {
		  //menorPair tiene que ser menor que cero o mayor que list.size() - 1, para que mas adelante en el codigo
		  //no se mezcle con los elementos de la lista
		  pos = -1;
		  c2 = list.first();
		  for (int j = 0; c2 != null; j++) {
			  pair = c2.element();
			  //Si el tiempo no ha sido utilizado ya para ordenar
			  if (!tiemposUtilizados[j]){
				  //Si es el primer elemento
				  if (pos == -1) {
					  menorPair = c2.element();
					  pos = j;
				  }
				  //Comparas cada elemento con el menor
				  else if(menorPair.getRight() > pair.getRight()) {
					  menorPair = pair;
					  pos = j; 
				  }
			  }
			  c2 = list.next(c2);
		  }
		  //Cuando tienes el menor tiempo, lo añades a la lista ordenada en la ultima posicion
		  //Y deja de ser el menorTiempo
		  tiemposOrdenados.addLast(menorPair);
		  menorPair = null;
		  tiemposUtilizados[pos] = true;
		  c1 = list.next(c1);
	  }
	  return tiemposOrdenados;
  }
  
}
