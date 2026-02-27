package saboteadores.cliente.consola;

import saboteadores.cliente.consola.VistaGUIConsola;

import saboteadores.modelo.mazo.cartas.Carta;

import java.rmi.RemoteException;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import saboteadores.modelo.ITablero;
import saboteadores.modelo.Jugador;
import saboteadores.modelo.EstadoSala;
//import saboteadores.tablero.Observer;
//

import javafx.application.Platform;

public class ControladorGUIConsola implements IControladorRemoto {
	private ITablero modelo;
	private VistaGUIConsola vista;
	private String nombreJugadorDeEsteControlador;
	private boolean juegoEnMarcha; 
	public ControladorGUIConsola(VistaGUIConsola vista)  {
		this.juegoEnMarcha = false;
		this.vista = vista;
	}
	public void iniciar(){
		try {
			//Se establece el largo del tablero para la vista
			this.vista.setLargo(this.modelo.getLargo());

			//al inicio de cada partida solo se puede consultar el top
			this.vista.setTop(this.modelo.getTop());
			// Pedir nombre
			this.nombreJugadorDeEsteControlador = vista.getNombre();
			if (this.nombreJugadorDeEsteControlador == null || 
					this.nombreJugadorDeEsteControlador.trim().isEmpty()) {
				vista.mostrarError("Debe ingresar un nombre válido");
				Platform.exit();
				return;
					}
			// Agregar jugador al modelo solo si no esta 
			if(this.modelo.estoyLogeado(this.nombreJugadorDeEsteControlador) == false ){
				this.modelo.agregarJugador(this.nombreJugadorDeEsteControlador);
			}
			// Actualizar la vista con el nombre del jugador
			vista.setNombreJugador(this.nombreJugadorDeEsteControlador);
		} catch (Exception e) {
			vista.mostrarError("Error al iniciar: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void jugadorListo() {
		boolean presente = false; 
		boolean jugable = false;
		try{ presente = modelo.estoyLogeado(nombreJugadorDeEsteControlador);
		} catch (RemoteException e) { vista.mostrarError("Error al unirse al juego en marcha: " + e.getMessage()); e.printStackTrace();}
		try{ jugable = modelo.esJugable();
		} catch (RemoteException e) { vista.mostrarError("Error al consultar sobre el juego en marcha: " + e.getMessage()); e.printStackTrace();}

		if(presente){
			System.out.println("1");
			if(jugable){
				System.out.println("2");
				actualizarDatosVista();
				try{
				vista.setAdversarios(this.modelo.getListaAdversarios(nombreJugadorDeEsteControlador));
				} catch (RemoteException e) { vista.mostrarError("Error al obtenener adversarios" + e.getMessage()); e.printStackTrace();}

				try{
				vista.setJugador(modelo.devolverJugador(this.nombreJugadorDeEsteControlador ));
				} catch (RemoteException e) { vista.mostrarError("Error al invocar avatar" + e.getMessage()); e.printStackTrace();}
				System.out.println("2.5");

				this.juegoEnMarcha = true;
				vista.cambiarAEscenarioJuego();
			}else{
				try {
					System.out.println("3");
					EstadoSala estado = modelo.marcarListo(nombreJugadorDeEsteControlador);
					// Actualizar vista con el estado actual
					Platform.runLater(() -> {
					vista.actualizarSala(estado.listos, estado.totalJugadores, estado.faltan);
					if (estado.faltan.isEmpty()) {
						//vista.mostrarTodosListos();
						//vista.cambiarAEscenarioJuego();
						// No se puede arrancar el juego desde aca, ya
						// que todos tendrian que arrancarlo 
						//this.modelo.iniciarJuego();
						try{this.modelo.intentarArrancarElJuego();
						} catch (RemoteException e) { vista.mostrarError("Error al intentar arrancar el juego" + e.getMessage()); e.printStackTrace();}
					}
					});
				}catch(RemoteException e) { vista.mostrarError("Error al actualizar sala: " + e.getMessage()); e.printStackTrace();}
			}
		}
	}


	public void ponerCartaSobreTablero(int posCarta, int pos){
		try{
		this.modelo.ponerCartaTablero(posCarta, pos, nombreJugadorDeEsteControlador);
		}catch(RemoteException e) { vista.mostrarError("Error al poner una carta en el tablero." + e.getMessage()); e.printStackTrace();}
	}
	public void descartarCarta(int posCarta){
		try{
		this.modelo.descartarCarta(posCarta, nombreJugadorDeEsteControlador);
		}catch(RemoteException e) { vista.mostrarError("Error al descartar una carta." + e.getMessage()); e.printStackTrace();}
	}
	public void ponerCartaSobreJugador(int posCarta, String nombre){
		try{
		System.out.println("" + posCarta + nombre );
		this.modelo.ponerCartaSobreJugador(posCarta, nombre, this.nombreJugadorDeEsteControlador);
		}catch(RemoteException e) { vista.mostrarError("Error al poner una carta sobre un jugador." + e.getMessage()); e.printStackTrace();}

	}

	public void actualizarDatosVista(){
		try{
					//vista.setNombreJugador(this.nombreJugadorDeEsteControlador);
					//Actualizar Jugador
					vista.setAdversarios(modelo.getListaAdversarios(this.nombreJugadorDeEsteControlador));
					vista.jugadorSiguente(modelo.getJugadorSiguiente());
					vista.actualizarCartasRestantes(modelo.getCantidadRestanteMazo());
					vista.actualizarTablero(modelo.getTableroConSlots());
					vista.setJugador(modelo.devolverJugador(nombreJugadorDeEsteControlador));
		} catch (RemoteException e) { vista.mostrarError("Error al actualizar datos de la vista" + e.getMessage()); e.printStackTrace();}

	}

	@Override
	public void actualizar(IObservableRemoto instanciaDeModelo, Object cambio) throws RemoteException{
		//System.out.println("a1");
		Platform.runLater(() -> {
		//System.out.println("a2");
			try {
				if (modelo != null && modelo.esJugable()) {
					//System.out.println("a3");
					this.actualizarDatosVista();
					// Si es el turno de este jugador, mostrar opciones
					if(this.juegoEnMarcha == false){
						//System.out.println("a4");
						this.juegoEnMarcha = true;
						//System.out.println("a5");
						//se setea una sola vez 
						vista.setJugador(modelo.devolverJugador(this.nombreJugadorDeEsteControlador ));
						vista.setAdversarios(modelo.getListaAdversarios(this.nombreJugadorDeEsteControlador));
						vista.cambiarAEscenarioJuego();
					}else{
						//System.out.println("a6");
						vista.vistaPorDefecto();
					}
					
				}else{
					//ya no es jugable
					vista.mostrarFinDeJuego(modelo.getPartidaGanada());
					
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException{
		this.modelo = (ITablero) modeloRemoto; //no funciona 
											   //arg0.agregarObservador(this);
	}

}
