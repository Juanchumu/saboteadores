package saboteadores;

import java.util.ArrayList;

import saboteadores.Jugador;
import saboteadores.tablero.Tablero;
import saboteadores.Controlador;
import saboteadores.VistaTerminal;

public class Main {

	public static void main(String[] args){
		//System.out.println("Bienvenido al juego de los saboteadores");

		//ArrayList<String> jugadores = new ArrayList<String>();
		//jugadores.add("Juan");
		//jugadores.add("Bruno");
		//jugadores.add("Patrick");
		//jugadores.add("Panessi");
		//nuevo juego 
		//
		Tablero tableroJuego = new Tablero();
		VistaTerminal vista = new VistaTerminal(tableroJuego);
		Controlador controlador = new Controlador(tableroJuego, vista);
		controlador.iniciar();
	}
}
