package saboteadores.cliente;
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

import saboteadores.cliente.ControladorGUI;

//Esto Controla LA VENTANA

public class VistaGUI {
	private ArrayList<String> nombres = new ArrayList<>();
	private final int MAX = 10;

	private int cartasRestantesMazo;
	private Jugador jugador;
	private String jugadorActual;
	private ArrayList<Jugador> adversarios;
	private Slots_tablero tableroJuego;
	private int largoTablero;
	private ControladorGUI controlador; //podria ser una interface realmente
	private String nombreJugador;

	private Top top;

	@FXML
	private Label labelEstado;
	@FXML
	private Label labelNombreJugador;
	@FXML
	private Label labelRolJugador;
	@FXML
	private Label turnoActual;
	@FXML
	private Label labelCartasRestantes;
	@FXML
	private Label labelOrosVistos;
	@FXML
	private ListView<String> listaFaltan;
	@FXML
	private Button botonListo;

	//Elementos del tablero 
	@FXML
	private GridPane botonesTablero;

	@FXML
	private ScrollPane scrollTablero;

	@FXML
	private Label labelManoTitulo;

	@FXML
	private VBox contenedorRestricciones;

	public VistaGUI(){
		System.out.println("Inicio la vista");
	}

	//Metodo totalmente legal, presente en el ejemplo rmimvc chat 
	public void setControlador(ControladorGUI controlador) {
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
	//

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
		for(JugadorHistorico jh : this.top.getHistorico()){
			historico = historico +" "+ jh.getNombre()+" : "+jh.getPuntos()+" puntos\n"; 
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

	public void actualizarSala(int listos, int totalJugadores, java.util.List<String> faltan) {
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


	// Cambio de ventana de juego 
	//
	public void cambiarAEscenarioJuego() {
		Platform.runLater(() -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista.fxml"));
				loader.setController(this); //Utiliza la misma instancia 
				Scene scene = new Scene(loader.load(), 640, 480);
				Stage stage = (Stage) labelEstado.getScene().getWindow();
				stage.setScene(scene);
				this.vistaPorDefecto();
			}catch(Exception e) { mostrarError("Error al cambiar de vista." + e.getMessage()); e.printStackTrace();
			}
		});
	}


	//GUI
	private int slotSeleccionado = 0;
	private void verTablero(){
		//botonesTablero = new GridPane();
		botonesTablero.getChildren().clear();

		botonesTablero.getColumnConstraints().clear();
		botonesTablero.getRowConstraints().clear();
		int columnas = largoTablero + 2;
		int filas = 5;
		for (int i = 0; i < columnas; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setPercentWidth(100.0 / columnas );
			botonesTablero.getColumnConstraints().add(col);
		}
		for (int i = 0; i < columnas; i++) {
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(100.0 / filas );
			botonesTablero.getRowConstraints().add(row);
		}

		//int columnas = largoTablero + 2;
		//int filas = 5;
		int botonNro = 0;
		String textoBoton = "";
		for(int f = 0; f < filas; f++){
			for (int c = 0; c < columnas; c++){
		//for (int c = 0; c < columnas; c++){
		//	for(int f = 0; f < filas; f++){
				int botonNroLocal = botonNro; //Importante
				if(tableroJuego.getSlot(botonNro).taOcupao() == true){
					//esto realmente tiene que pedir el array de slots
					textoBoton = tableroJuego.getSlot(botonNro).getCartaAlojadaEnSlot().getForma();
				}else{
					textoBoton = " ";
				}
				Button btn = new Button(textoBoton);
				btn.setOnAction(e ->{
					this.slotSeleccionado = botonNroLocal;
					System.out.println("Apretaste el boton"+ botonNroLocal);
					deshabilitarTodos();
				} );
				botonesTablero.add(btn, c, f);
				botonNro++;
			}
		} 
	}
	private void deshabilitarTodos(){
		for(Node node : botonesTablero.getChildren()){
			if(node instanceof Button){
				node.setDisable(true);
			}
		}
	}

	/// por cada interaccion, se muestra denuevo la vista
	public void vistaPorDefecto(){
		//this.espacioBlanco();
		this.verCartasJugador();
		this.verJugadorActual();
		this.verOtrosJugadores(); //incompleto 
		this.verOrosJugador();
		this.verRestriccionesJugador();
		this.verTablero();
		this.verCantidadDeCartasRestantes();
		this.opcionesBotones();
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
		//labelNombreJugador = new Label();
		labelNombreJugador.setText(this.jugador.getNombre());
		//labelRolJugador = new Label();
		labelRolJugador.setText( this.jugador.getRol().name() );
	}

	//Mano del jugador 
	@FXML
	private VBox contenedorMano;
	@FXML
	private ToggleGroup tgMano;

	private void verCartasJugador(){ 
		//las cartas tienen ID distinto al del modelo, no pasan por RMI
		contenedorMano.getChildren().clear();
		//System.out.println(jugador.getNombre() );
		tgMano = new ToggleGroup();
		int i = 0; //Identificador
		for(Carta c : jugador.getManoJugador()){
			//System.out.println( cartaAString(c) );
			//System.out.println( c );
			RadioButton rb = new RadioButton( cartaAString(c) );
			rb.setToggleGroup(tgMano);
			rb.setUserData(i);
			contenedorMano.getChildren().add(rb);
			i++;
		}
		//Seleccionar la primera 
		tgMano.selectToggle(tgMano.getToggles().get(0));
	}
	@FXML
	private VBox contenedorJugadores;
	@FXML
	private ToggleGroup tgAdversarios;
	private void verOtrosJugadores(){
		tgAdversarios = new ToggleGroup();
		contenedorJugadores.getChildren().clear();
		
		//Primera Opcion es el propio jugador 
		RadioButton nombreJugador = new RadioButton(jugador.getNombre());
		nombreJugador.setToggleGroup(tgAdversarios);
		nombreJugador.setUserData(jugador.getNombre());
		nombreJugador.setSelected(true);
		contenedorJugadores.getChildren().add(nombreJugador);

		for(Jugador j : this.adversarios){
			VBox bloqueJugador = new VBox();
			bloqueJugador.setSpacing(3);
			//bloqueJugador.setMinHeight(30);
			//bloqueJugador.setStyle("-fx-border-color: red;");

			RadioButton nombreAdversario = new RadioButton(j.getNombre());
			nombreAdversario.setToggleGroup(tgAdversarios);
			nombreAdversario.setUserData(j.getNombre());
			//contenedorJugadores.getChildren().add(nombreAdversario);
			bloqueJugador.getChildren().add(nombreAdversario);
			
			//aca tendria que ser un VBox en el que se cargan las 
			VBox contenedorRestriAdversario = new VBox();
			contenedorRestriAdversario.setSpacing(3);
			//contenedorRestriAdversario.setMinHeight(20);
			//contenedorRestriAdversario.setStyle("-fx-border-color: blue;");
			//restricciones
			if(j.tieneRestricciones() == false ){
				//System.out.println("Tiene restricciones");
				for(CartaSabotaje s: j.getRestriccionesJugador()){
					Label jugadorestriccion = new Label(s.getAccion().toString());
					contenedorRestriAdversario.getChildren().add(jugadorestriccion);
				}
			}else{
				Label sinRestricciones = new Label("Sin Restricciones");
				contenedorRestriAdversario.getChildren().add(sinRestricciones);
			}
			bloqueJugador.getChildren().add(contenedorRestriAdversario);
			contenedorJugadores.getChildren().add(bloqueJugador);
		}
		//contenedorJugadores.requestLayout();
		//contenedorJugadores.layout();
	}



	@FXML 
	private VBox contenedorOrosVistos;

	private void verOrosJugador(){
		//contenedorOrosVistos = new VBox();
		contenedorOrosVistos.getChildren().clear();
		int i = 1;
		String aux = "";
		if(jugador.getOrosJugador().isEmpty()){
			contenedorOrosVistos.getChildren().add(new Label("Todavia no hay oros vistos"));
		}else{
			contenedorOrosVistos.getChildren().add(new Label("Oros vistos:"));
			for(Carta_Camino c : jugador.getOrosJugador()){
				aux = i+ " "+ cartaAString(c) + c.getForma(); 
				if(c.getOro()){
					aux = aux +"Oro";
				}else{
					aux = aux +"Carbon";
				}
				contenedorOrosVistos.getChildren().add(new Label(aux));
				i++;
			}
		}
	}
	private void verRestriccionesJugador(){
		//contenedorRestricciones = new VBox();
		contenedorRestricciones.getChildren().clear();
		String aux = "";
		int i = 1;
		if(jugador.getRestriccionesJugador().isEmpty()){
			contenedorRestricciones.getChildren().add(new Label("Todavia no hay restricciones"));
		}else{
			contenedorRestricciones.getChildren().add(new Label("Restricciones:"));
			for(CartaSabotaje c : jugador.getRestriccionesJugador()){
				aux = i+ " " + c.getAccion().name(); 
				contenedorRestricciones.getChildren().add(new Label(aux));
				i++;
			}
		}
	}
	private void verCantidadDeCartasRestantes(){
		//labelCartasRestantes = new Label();
		labelCartasRestantes.setText( this.cartasRestantesMazo + ""  );
	}
	@FXML
	private VBox opciones;
	@FXML
	private Button jugarEnUnJugador;
	@FXML
	private Button jugarEnElTablero;
	@FXML
	private Button descartarLaCarta;

	public void opcionesBotones(){
		if(jugadorActual.equals(jugador.getNombre().toString())){
		//final Carta carta = (Carta) tgMano.getSelectedToggle().getUserData(); //es una carta 
		opciones.getChildren().clear();
		jugarEnUnJugador = new Button("Jugar en un Jugador");
		jugarEnUnJugador.setOnAction(e -> {
			controlador.ponerCartaSobreJugador( 
					(int) tgMano.getSelectedToggle().getUserData() , 
					//es la posicion de la carta 
			tgAdversarios.getSelectedToggle().getUserData().toString() );
		});
		opciones.getChildren().add(jugarEnUnJugador);
		jugarEnElTablero = new Button("Jugar en el tablero");
		jugarEnElTablero.setOnAction(e ->{
			controlador.ponerCartaSobreTablero( 
					(int) tgMano.getSelectedToggle().getUserData() +0 ,  //es la posicion de la carta
			 slotSeleccionado );
		});
		opciones.getChildren().add(jugarEnElTablero);
		descartarLaCarta = new Button("Descartar carta");
		descartarLaCarta.setOnAction(e -> {
			controlador.descartarCarta( 
					(int) tgMano.getSelectedToggle().getUserData()
					); //es la posicion de una carta  
		});
		opciones.getChildren().add(descartarLaCarta);
		}else{
			opciones.getChildren().clear();
			Label noEsMiTurno = new Label("No es mi turno");
			opciones.getChildren().addLast(noEsMiTurno);
			Label esTurnoDe = new Label("Es turno de:"+ this.jugadorActual );
			opciones.getChildren().addLast(esTurnoDe);
		}
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
