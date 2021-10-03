package aed.bancofiel;

import java.util.Collections;
import java.util.Comparator;
import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.indexedlist.ArrayIndexedList;


/**
 * Implements the code for the bank application.
 * Implements the client and the "gestor" interfaces.
 */
public class BancoFiel implements ClienteBanco, GestorBanco {

  // NOTAD. No se deberia cambiar esta declaracion.
  public IndexedList<Cuenta> cuentas;

  // NOTAD. No se deberia cambiar esta constructor.
  public BancoFiel() {
    this.cuentas = new ArrayIndexedList<Cuenta>();
  }

  // ----------------------------------------------------------------------
  // Anadir metodos aqui ...
  @Override
  public String crearCuenta(String dni, int saldoIncial) {
	  
	  Cuenta cuenta = new Cuenta(dni,saldoIncial);
	  cuentas.add(cuentas.size(), cuenta);
	  return cuenta.getId();
	  
	  
	  
  }
  //devuelve la posición que ocupa la cuenta con
  //identificador id, lanza CuentaNoExisteExc si la cuenta con ese id no existe
  
  private int buscarCuenta (String id) throws CuentaNoExisteExc{
	  int i=0;
	  while(i < cuentas.size()) {
		  Cuenta cuenta = cuentas.get(i);
		  if(cuenta.getId().equals(id)) {
			  return i;
		  }
		  i++;
	  }
	  throw new CuentaNoExisteExc();
  }  

   

  @Override
  public void borrarCuenta(String id)
			throws CuentaNoExisteExc, CuentaNoVaciaExc{
	  
	  
	  //cuenta no existe throws Exception
	  int pos = buscarCuenta(id);
	  
	  //saldo de la cuenta con ID 'id'
	  int saldo = cuentas.get(pos).getSaldo();
	  
	  //if the balance of the bank account is not 0.
	  if( saldo != 0) {
		  throw new CuentaNoVaciaExc();
	  }
	  cuentas.removeElementAt(pos);
	  
  }
  @Override
  public int ingresarDinero(String id, int cantidad)
			throws CuentaNoExisteExc{
	  
	  //localizamos la cuenta (si no existe, se lanza CuentaNoExisteExc)
	  //ingresamos 'cantidad' y devolvemos el saldo actual
	  int pos = buscarCuenta(id);
	  Cuenta cuenta = cuentas.get(pos);
	  cuenta.ingresar(cantidad);
	  return cuenta.getSaldo();
  }
  @Override
  public int retirarDinero(String id, int cantidad)
			throws CuentaNoExisteExc, InsuficienteSaldoExc{
	  

	  
	  //buscamos la cuenta(si no existe, se lanza CuentaNoExisteExc) y su saldo
	  int pos = buscarCuenta(id);
	  Cuenta cuenta = cuentas.get(pos);
	  int saldo = cuenta.getSaldo();
	  
	  //if the balance of the account is less than 'cantidad'
	  // invoke InsuficienteSaldoExc
	  if(saldo < cantidad) {
		  throw new InsuficienteSaldoExc();
	  }
	  //retiramos 'cantidad' del saldo y devolvemos 
	  //saldo actual
	  cuenta.retirar(cantidad);
	  return cuenta.getSaldo();
	  
  }
  @Override
  public int consultarSaldo(String id) 
			throws CuentaNoExisteExc{
	  
	  //localizamos la cuenta (si no existe, se lanza CuentaNoExisteExc)
	  int pos = buscarCuenta(id);
	  Cuenta cuenta = cuentas.get(pos);
	  return cuenta.getSaldo();
	  
  }
  @Override
  public void hacerTransferencia(String idFrom, String idTo, int cantidad)
			throws CuentaNoExisteExc, InsuficienteSaldoExc{
	  
	  //localizamos ambas cuebtas, si alguna o ambas no existen throw CuentaNoExisteExc
	  int posFrom = buscarCuenta(idFrom);
	  int posTo = buscarCuenta(idTo);
	  
	  //retiramos 'cantidad' de la cuenta con identificador 'idFrom'
	  //si su saldo es < que 'cantidad' InsuficienteSaldoExc
	  Cuenta cuentaFrom = cuentas.get(posFrom);
	  int saldoFrom = cuentaFrom.getSaldo();
	  if(saldoFrom < cantidad) {
		  throw new InsuficienteSaldoExc();
	  }
	  cuentaFrom.retirar(cantidad);
	  
	  //ingresamos 'cantidad' a la cuenta con identificador 'idTo'
	  Cuenta cuentaTo = cuentas.get(posTo);
	  cuentaTo.ingresar(cantidad);
  }
  @Override
  public IndexedList<String> getIdCuentas(String dni){
	  IndexedList<String> res = new ArrayIndexedList<String>();
	  for(int i=0;i<cuentas.size();i++) {
		  Cuenta aux = cuentas.get(i);
		  if(aux.getDNI().equals(dni)) {
			  res.add(res.size(), aux.getId());
		  }
	  }
	  
	  return res;
	  
  }
  @Override
  public int getSaldoCuentas(String dni) {
	  int sum = 0;
	  IndexedList<String> cuentasDni = getIdCuentas(dni);
	  int i = 0;
	  while(i<cuentasDni.size()) {
		  String idAux = cuentasDni.get(i);
		  int saldo = 0;
		try {
			saldo = consultarSaldo(idAux);
		} catch (CuentaNoExisteExc e) {
			
		}
		  sum += saldo;
		  i++;
	  }
	  return sum;
  }
  @Override
  public IndexedList<Cuenta> getCuentasOrdenadas(Comparator<Cuenta> cmp){
	  
	  int laMenorCuenta;
	  //Creamos un array de booleans para saber que cuentas se van utilizando
	  boolean[] cuentasUsadas = new boolean[cuentas.size()];
	  IndexedList<Cuenta> cuentasOrdenadas = new ArrayIndexedList<Cuenta>();
	  
	  if(cuentas.size() >= 2) {
		  for (int i = 0; i < cuentas.size(); i++) {
			  //laMenorCuenta tiene que ser menor que cero o mayor que cuentas.size() - 1,
			  //para que más adelante en el código
			  //no mezclar ese primer valor con los elementos de la lista
			  laMenorCuenta = -1;
			  for (int j = 0; j < cuentas.size(); j++) {
				  //Si la cuenta no ha sido utilizada ya para ordenar
				  if (!cuentasUsadas[j]){
					  //Si es el primer elemento
					  if (laMenorCuenta == -1)
						  laMenorCuenta = j;
					  //Comparas cada elemento con el menor
					  else if (cmp.compare(cuentas.get(laMenorCuenta), cuentas.get(j)) > 0)
						  laMenorCuenta = j;
				  }
			  }
			  //Cuando tienes la menor cuenta, la añades a la lista ordenada
			  cuentasOrdenadas.add(cuentasOrdenadas.size(), cuentas.get(laMenorCuenta));
			  cuentasUsadas[laMenorCuenta] = true;
		  }
	  }
	  
	  else
		   cuentasOrdenadas = cuentas;
	  
	  return cuentasOrdenadas;
	  
  }



  // ----------------------------------------------------------------------
  // NOTAD. No se deberia cambiar este metodo.
  public String toString() {
    return "banco";
  }
  
}



