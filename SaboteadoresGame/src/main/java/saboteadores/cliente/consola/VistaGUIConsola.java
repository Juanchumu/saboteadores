package saboteadores.cliente.consola;
import saboteadores.modelo.enums.CartaAccionTipo;
import saboteadores.modelo.Slots_tablero;
import saboteadores.modelo.Top;
import saboteadores.modelo.EstadoSala;
import saboteadores.modelo.mazo.cartas.Carta;
import saboteadores.modelo.mazo.cartas.CartaReparacion;
import saboteadores.modelo.mazo.cartas.CartaSabotaje;
import saboteadores.modelo.mazo.cartas.CartaSimple;
import saboteadores.modelo.mazo.cartas.Carta_Camino;
import saboteadores.modelo.Jugador;
import saboteadores.modelo.JugadorHistorico;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Platform;


import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import java.util.Optional;

import saboteadores.cliente.consola.ControladorGUIConsola;

//Esto Controla LA VENTANA

public class VistaGUIConsola {
	private ControladorGUIConsola controlador; //podria ser una interface realmente
	private ArrayList<String> nombres = new ArrayList<>();
	private final int MAX = 10;

	private int cartasRestantesMazo;
	private Jugador jugador;
	private String jugadorActual;
	private ArrayList<Jugador> adversarios;
	private Slots_tablero tableroJuego;
	private int largoTablero;
	private String nombreJugador;

	private Top top;

	public VistaGUIConsola(){
		System.out.println("Inicio la vista");
	}

	//Metodo totalmente legal, presente en el ejemplo rmimvc chat 
	public void setControlador(ControladorGUIConsola controlador) {
		this.controlador = controlador;	
	}
	@FXML
	public void initialize(){}
	public void setLargo(int largo){
		this.largoTablero = largo;
	}


	@FXML
	public void clickListo() {
		if (controlador != null) {
			controlador.jugadorListo();
		}
	}


	//Metodo inicial para obtener un nombre:
	@FXML 
	private Label labelNombreJugador;

	public String getNombre() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Nombre del jugador");
		dialog.setHeaderText("Ingrese su nombre");
		dialog.setContentText("Nombre:");
		Optional<String> resultado = dialog.showAndWait();
		return resultado.orElse(null);
	}
	public void setNombreJugador(String nombre) {
		this.nombreJugador = nombre;
		if (labelNombreJugador != null) {
			Platform.runLater(() -> labelNombreJugador.setText("Jugador: " + nombre));
		}
	}
	public void setTop(Top top){
		this.top = top;
	}
	public void setJugador(Jugador j){
		this.jugador = j;
	}
	public void setAdversarios(ArrayList<Jugador> adversarios){
		this.adversarios = adversarios;
	}
	//para mostrar errores:

	public void mostrarError(String mensaje) {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Ocurrió un problema");
			alert.setContentText(mensaje);
			alert.showAndWait();
		});
	}

	public boolean confirmarListo() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Listo para jugar");
		alert.setHeaderText("¿Estás listo?");
		alert.setContentText("Presioná OK cuando estés listo.");

		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == ButtonType.OK;
	}

	@FXML
	public void mostrarTop() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Top Historico");
		alert.setHeaderText("Historial de jugadores");
		String historico = "";
		if(this.top.getHistorico().isEmpty() == false) {
			for(JugadorHistorico jh : this.top.getHistorico()){
				historico = historico +" "+ jh.getNombre()+" : "+jh.getPuntos()+" puntos\n"; 
			}
		}else{
			historico = historico +"Todavia no hay historial\n"; 
		}
		//alert.setContentText(historico);

		TextArea area = new TextArea(historico);
		area.setWrapText(true);
		area.setEditable(false);
		alert.getDialogPane().setContent(area);
		//Optional<ButtonType> result = 
		alert.showAndWait();
	}

	//pop up para mostrar estado de la sala 

	public void mostrarEstadoSala(EstadoSala estado) {
		Platform.runLater(() -> {
			StringBuilder texto = new StringBuilder();
			texto.append("Jugadores listos: ")
				.append(estado.listos)
				.append(" / ")
				.append(estado.totalJugadores)
				.append("\n\n");

			if (!estado.faltan.isEmpty()) {
				texto.append("Faltan:\n");
				for (String j : estado.faltan) {
					texto.append("- ").append(j).append("\n");
				}
			}

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Sala de espera");
			alert.setHeaderText("Esperando jugadores");
			alert.setContentText(texto.toString());

			alert.showAndWait();
		});
	}

	public void mostrarTodosListos() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Juego");
		alert.setHeaderText("Todos están listos");
		alert.setContentText("La partida va a comenzar");
		alert.showAndWait();

		//aca se cambia la pantalla de juego
		//cambiarPantallaDeJuego();

	}

	@FXML
	private Label labelEstado;
	@FXML
	private ListView<String> listaFaltan;
	@FXML
	private Button botonListo;

	public void actualizarSala(int listos, int totalJugadores, List<String> faltan) {
		Platform.runLater(() -> {
			if (labelEstado != null) {
				labelEstado.setText("Listos: " + listos + " / " + totalJugadores);
			}
			if (listaFaltan != null) {
				listaFaltan.getItems().clear();
				listaFaltan.getItems().addAll(faltan);
			}
			// Deshabilitar el botón si ya está listo
			if (botonListo != null && faltan != null && !faltan.contains(nombreJugador)) {
				botonListo.setDisable(true);
				botonListo.setText("¡Listo!");
			}
		});
	}


	// Cambio de ventana de juego a la vista Consola
	//
	public void cambiarAEscenarioJuego() {
		Platform.runLater(() -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistaConsola.fxml"));
				loader.setController(this); //Utiliza la misma instancia 
				Scene scene = new Scene(loader.load(), 640, 480);
				Stage stage = (Stage) labelEstado.getScene().getWindow();
				stage.setScene(scene);
				this.vistaPorDefecto();
			}catch(Exception e) { mostrarError("Error al cambiar de vista." + e.getMessage()); e.printStackTrace();
			}
		});
	}

	@FXML
	private TextArea consola;
	@FXML
	private TextField inputConsola;


	//GUI
	private int slotSeleccionado = 0;
	private void verTablero(){
		consola.appendText("Tablero de juego:\n");
		int columnas = largoTablero + 2;
		int filas = 5;
		int botonNro = 0;
		for(int f = 0; f < filas; f++){
			for (int c = 0; c < columnas; c++){
				if(tableroJuego.getSlot(botonNro).taOcupao() == true){
					consola.appendText(
							tableroJuego.getSlot(botonNro).getCartaAlojadaEnSlot().getForma()
							);
				}else{
					consola.appendText("");
				}
				botonNro++;
			}
			consola.appendText("\n");
		} 
	}

	/// por cada interaccion, se muestra denuevo la vista
	public void vistaPorDefecto(){
		this.verCartasJugador();
		this.verJugadorActual();
		this.verOtrosJugadores(); //incompleto 
		this.verOrosJugador();
		this.verRestriccionesJugador();
		this.verTablero();
		this.verCantidadDeCartasRestantes();
		//this.opciones(); //esto lo maneja el boton enviar
	}
	public void iniciarJuego(int largo){
		this.largoTablero = largo;
		System.out.println("Bienvenido al juego de los saboteadores");
	}


	public void actualizarTablero(Slots_tablero tablero){
		this.tableroJuego = tablero;
	}
	public void actualizarCartasRestantes(int cantidad){
		this.cartasRestantesMazo = cantidad;
	}
	public void jugadorSiguente(String j){
		this.jugadorActual = j;
	}
	private void verJugadorActual(){
		consola.appendText("Estas controlando al jugador : " + this.jugador.getNombre()+ "\n");
		consola.appendText("Que tiene el rol de : " +this.jugador.getRol().name() + "\n" );
	}

	private void verCartasJugador(){ 
		consola.appendText("Estas son tus cartas: \n");
		int i = 0; //Identificador
		for(Carta c : jugador.getManoJugador()){
			consola.appendText("ID: "+i+" "+cartaAString(c) );
			i++;
		}
	}
	private void verOtrosJugadores(){
		consola.appendText("Estos son los jugadores: \n");
		//Primera Opcion es el propio jugador 
		int i = 0;
		consola.appendText("ID: "+i+" "+jugador.getNombre()+ "\n" );
		for(Jugador j : this.adversarios){
			consola.appendText("ID: "+i+" "+j.getNombre()+ "\n" );
			if(j.tieneRestricciones() == false ){
				consola.appendText("Tiene restricciones: ");
				for(CartaSabotaje s: j.getRestriccionesJugador()){
					consola.appendText(s.getAccion().toString());
				}
				consola.appendText("\n");

			}else{
				consola.appendText("Sin restricciones. \n");
			}
			i++;
		}
	}
	private void verOrosJugador(){
		consola.appendText("Estos son los oros que viste:\n");
		int i = 1;
		String aux = "";
		if(jugador.getOrosJugador().isEmpty()){
			consola.appendText("Todavia no hay oros vistos. \n");
		}else{
			for(Carta_Camino c : jugador.getOrosJugador()){
				aux = i+ " "+ cartaAString(c) + c.getForma(); 
				if(c.getOro()){
					aux = aux +" Oro";
				}else{
					aux = aux +" Carbon";
				}
				consola.appendText(aux + "\n");
				i++;
			}
		}
	}
	private void verRestriccionesJugador(){
		consola.appendText("Restricciones que tenes: ");
		String aux = "";
		if(jugador.getRestriccionesJugador().isEmpty()){
			consola.appendText("... Ninguna.\n");
		}else{
			for(CartaSabotaje c : jugador.getRestriccionesJugador()){
				consola.appendText( c.getAccion().name() + " " ); 
			}
			consola.appendText("\n");
		}
	}
	private void verCantidadDeCartasRestantes(){
		consola.appendText("Cantidad de cartas restantes en el mazo: ");
		consola.appendText(this.cartasRestantesMazo + "\n");
	}

	@FXML
	private void enviar(){
		String texto = inputConsola.getText().trim(); // se lee lo que se escribio
		consola.appendText(texto);
		inputConsola.clear();
		if(texto.isEmpty()) return;

		String[] partes = texto.split("\\s+"); //separa por espacios
		opciones(partes);
	}

	public void opciones(String[] partes){
		int cartaElegida;
		if(jugadorActual.equals(jugador.getNombre().toString())){
			if(partes.length == 0){
				consola.appendText("Por favor introduci algo \n");
			}else{
				switch(partes[0].toLowerCase()){
					case "jugador":
						//jugador carlos 3
						opcionJugador(partes);
						break;
					case "tablero":
						opcionTablero(partes);
						break;
					case "descartar":
						opcionDescartar(partes);
						break;
				}
			}
		}else{
			consola.appendText("No es tu turno... Es turno de:"+ this.jugadorActual );
		}
	}

	private void opcionJugador(String[] partes){
		try{
			if(partes.length == 3){
				String ojugadorObjetivo = partes[1].toString();
				int cartaElegida = Integer.parseInt(partes[2]);
				if(checkBienEscritoJugarores( ojugadorObjetivo ) &&
						checkBienEscritoCarta(cartaElegida)){
					consola.appendText("Jugando sobre "+ ojugadorObjetivo  );
					consola.appendText(" la carta "+cartaElegida+"\n" );
					controlador.ponerCartaSobreJugador( cartaElegida  //Nro carta 
									,ojugadorObjetivo  ); //Nombre Jugador
				}
			}else{
				consola.appendText("Numero de argumentos invalido. \n");
			}
			//Que no exista (en el array)  o que no sea un numero valido
		}catch(ArrayIndexOutOfBoundsException | NumberFormatException e){
			consola.appendText("Comando erroneo. \n");
		}

	}
	private void opcionTablero(String[] partes){
		//tablero 17 3
		//tablero slot carta
		try{
			if(partes.length == 3){
				int SlotElegido = Integer.parseInt(partes[1]);
				int cartaElegida = Integer.parseInt(partes[2]);
				if(checkBienEscritoCarta(cartaElegida) && checkBienEscritoSlot(SlotElegido) ){
					controlador.ponerCartaSobreTablero( cartaElegida,
							SlotElegido);
				}else{
					consola.appendText("Carta o Slot fuera del intervalo aceptable. \n");
				}
			}else{
				consola.appendText("Error Multiples Argumentos. \n");
			}
		}catch(ArrayIndexOutOfBoundsException | NumberFormatException e){
			consola.appendText("Comando erroneo. \n");
		}
	}
	private void opcionDescartar(String[] partes){
		//descartar 3
		//descartar carta
		try{
			if(partes.length == 2){
				int cartaElegida = Integer.parseInt(partes[2]);
				if(checkBienEscritoCarta(cartaElegida)  ){
					controlador.descartarCarta(cartaElegida );
				}else{
					consola.appendText("Carta fuera del intervalo aceptable. \n");
				}
			}else{
				consola.appendText("Error Multiples Argumentos. \n");
			}
		}catch(ArrayIndexOutOfBoundsException | NumberFormatException e){
			consola.appendText("Comando erroneo. \n");
		}
	}


	private boolean checkBienEscritoSlot(int numero){
		int maximoSlot = ((largoTablero + 2)*5 ) - 1;
		return ( (numero >=0) && (numero <= maximoSlot) ); 
	}
	private boolean checkBienEscritoCarta(int numero){
		int maximaCarta = this.jugador.getManoJugador().size() - 1; 
		return ( (numero >=0) && (numero <= maximaCarta) ); 
	}
	private boolean checkBienEscritoJugarores(String nombre){
		ArrayList<String> nombreJugadores;
		nombreJugadores.add(nombreJugador);
		for(Jugador j: this.adversarios){
			nombreJugadores.add(j.getNombre());
		}
		//ahora me fijo si esta presente 
		return nombreJugadores.contains(nombre);
	}

	private String cartaAString(Carta c){
		String salida = "error";
		// dependiendo de carta, sera el string 
		if(c instanceof Carta_Camino){
			//camino o callejon 
			Carta_Camino camino = (Carta_Camino) c;
			salida = camino.getForma() ;
		}
		if(c instanceof CartaSimple){
			CartaSimple simple = (CartaSimple) c;
			if(simple.esVista()){
				salida = "Ojo";
			}else{
				salida = "Derrumbe";
			}
		}
		if(c instanceof CartaSabotaje){
			CartaSabotaje sabos = (CartaSabotaje) c;
			salida = "Sabotaje: "+ sabos.getAccion(); 
		}
		if(c instanceof CartaReparacion){
			CartaReparacion repa = (CartaReparacion) c;
			salida = "Reparacion: "+ repa.getReparacion1(); 
			if(repa.getReparacion2() != CartaAccionTipo.VACIO){
				salida = salida + " y "+ repa.getReparacion2();
			}
		}
		return salida;
	}
	//se deja como esta 
	public void mostrarFinDeJuego(String mensaje) {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Fin del juego");
			alert.setHeaderText("La partida terminó");
			alert.setContentText(mensaje);

			alert.showAndWait();

			Platform.exit();
			System.exit(0);
		});
	}

}
