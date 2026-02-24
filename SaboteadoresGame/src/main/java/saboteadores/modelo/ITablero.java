package saboteadores.modelo;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import saboteadores.modelo.enums.Rol;
import saboteadores.modelo.enums.CartaTipo;
import saboteadores.modelo.mazo.cartas.Carta_Camino;
import saboteadores.modelo.mazo.cartas.Carta_Oro;
import saboteadores.modelo.mazo.Mazo;
import saboteadores.modelo.mazo.MazoPepitas;
import saboteadores.modelo.Slot;
import saboteadores.modelo.Slots_tablero;
import saboteadores.modelo.EstadoSala;
import saboteadores.modelo.observer.Observable;
import saboteadores.modelo.observer.Observador;
import saboteadores.modelo.Jugador;


import saboteadores.modelo.mazo.cartas.Carta;
import saboteadores.modelo.mazo.cartas.CartaReparacion;
import saboteadores.modelo.mazo.cartas.CartaSabotaje;
import saboteadores.modelo.mazo.cartas.CartaSimple;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

public interface ITablero extends IObservableRemoto {
	public int getLargo() throws RemoteException; 
	public boolean esJugable() throws RemoteException;
	public boolean hayJugadores() throws RemoteException;
	public void agregarJugador(String nombre) throws RemoteException;
	public void agregarJugadores(ArrayList<String> jugadoresNuevos) throws RemoteException;
	//public void incializar() throws RemoteException;
	public void iniciarJuego() throws RemoteException;
	public Slots_tablero getTableroConSlots() throws RemoteException;
	public int getCantidadRestanteMazo() throws RemoteException;
	

	public void ponerCartaSobreJugador(int posCarta,String jugadorObjetivo,String quien) throws RemoteException;
	public void ponerCartaTablero(int posCarta, int pos, String quien) throws RemoteException;
	public void descartarCarta(int posCarta, String quien) throws RemoteException;


	public EstadoSala marcarListo(String nombre) throws RemoteException;
	public void intentarArrancarElJuego() throws RemoteException;
	public boolean estoyLogeado(String nombre) throws RemoteException;
	public Jugador devolverJugador(String nombre) throws RemoteException;
	public String getJugadorSiguiente() throws RemoteException;
	public ArrayList<Jugador> getListaAdversarios(String quien) throws RemoteException;
	public String getPartidaGanada() throws RemoteException;
	public Top getTop() throws RemoteException;

}
