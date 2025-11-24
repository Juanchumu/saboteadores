package saboteadores;

import saboteadores.Jugador;
import saboteadores.tablero.Tablero;
import saboteadores.tablero.Slot;
import saboteadores.mazo.cartas.Carta;
import saboteadores.mazo.cartas.Carta_Accion;
import saboteadores.mazo.cartas.Carta_Camino;

public class VistaTerminal {
	private Tablero tableroJuego;
	/// por cada interaccion, se muestra denuevo la vista
	Jugador jugador;
	public VistaTerminal(Tablero tableroJuego){
		this.tableroJuego = tableroJuego;
		System.out.println("Inicio la vista");
		this.jugador = tableroJuego.getJugador(0);

		this.verCartasJugador();
		this.verTablero();
		this.verCantidadDeCartasRestantes();
	}
	private void verCartasJugador(){
		for(Carta c : jugador.getManoJugador()){
			System.out.println("Mano del jugador:");
			System.out.println(c.getTipo());

		}
	}
	private void verTablero(){
		int contador = 0;
		for(int fila = 0; fila < 5; fila++){
			for(int columna = 0; columna < 9; columna++){
				System.out.print("pos"+contador );
				if(tableroJuego.taOcupao(contador) == true){
					System.out.print(tableroJuego.getSlot(contador).getCartaAlojadaEnSlot().getForma() );
				}else{
					System.out.print(" ");
				}
				contador ++;
			}
			System.out.println("");
		}
	}
	private void verCantidadDeCartasRestantes(){
		System.out.println("Cartas Restantes en mazo:" 
				+this.tableroJuego.getCantidadRestanteMazo() );
	}
}
