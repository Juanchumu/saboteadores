package saboteadores;

import saboteadores.tablero.Tablero;
import saboteadores.VistaTerminal;
import saboteadores.observer.Observador;
import saboteadores.mazo.cartas.Carta;

public class Controlador implements Observador {
	private Tablero modelo;
	private VistaTerminal vista;


	public Controlador(Tablero modelo, VistaTerminal vista){
		this.modelo = modelo;
		this.vista = vista;
		this.modelo.agregarObservador(this);
	}
	public void iniciar(){
		this.vista.iniciarJuego();
		this.cargarJugadores();
	}
	public void cargarJugadores(){
		this.modelo.agregarJugadores( vista.generarJugadores() );
	}
	public Carta menuDeCartas(){
		return vista.menuDeCartas();
	}

	public void opciones(){
		vista.vistaPorDefecto();
		Carta cartita = menuDeCartas();
		int opcion = vista.menuOpciones();
		switch (opcion) {
        case 1:
            //Jugando la carta sobre el tablero...
            switch(vista.menuJugarEnTablero()){
				case 1:
					this.modelo.ponerCartaTablero(cartita,
							vista.introducirPosicion(),
							this.modelo.getJugadorActual().getNombre());
					break;
				case 2:
					this.modelo.quieroVerOro(cartita,
							vista.menuElegirOro(),
							//Quien?
							this.modelo.getJugadorActual().getNombre() );
			}
            break;
        case 2:
            //Jugando la carta sobre un jugador...
			modelo.ponerCartaSobreJugador(cartita,
					vista.menuElegirJugador(),
					this.modelo.getJugadorActual().getNombre());
            break;

        case 3:
			//por falta de tiempo cualquier carta mal jugada se considera
			//descarte
            modelo.descartarCarta(cartita,
					this.modelo.getJugadorActual().getNombre());
            break;

        case 4:
            System.out.println("Acción cancelada.");
            break;

        default:
            System.out.println("Opción inválida.");
		}
    }

	@Override
	public void actualizar(){
		//vista.MostrarTareas(modelo.getListaTareas());
		vista.jugadorActual(this.modelo.getJugadorActual());
		vista.actualizarCartasRestantes(modelo.getCantidadRestanteMazo());
		vista.actualizarTablero(
				modelo.getTableroConSlots() 
				); //se puede mejorar
		opciones();
	}
	
}
