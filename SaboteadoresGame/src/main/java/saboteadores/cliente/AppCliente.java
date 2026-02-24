package saboteadores.cliente;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Platform;


import saboteadores.cliente.ControladorGUI;
import saboteadores.cliente.VistaGUI;
import saboteadores.modelo.ITablero;

public class AppCliente extends Application {
	private static VistaGUI vista;
	private static ControladorGUI controlador;

	public static void main(String[] args) {
		ArrayList<String> puertos = new ArrayList<String>();
		puertos.add("10001");
		puertos.add("10002");
		puertos.add("10003");
		puertos.add("10004");
		ArrayList<String> ips = Util.getIpDisponibles();
		String ip = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la que escuchar� peticiones el cliente", "IP del cliente", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				ips.toArray(),
				"127.0.0.1"
		);
		String port = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que escuchar� peticiones el cliente", "Puerto del cliente", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				puertos.toArray(),
				9999
		);
		String ipServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la corre el servidor", "IP del servidor", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				null,
				"127.0.0.1"
		);
		String portServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que corre el servidor", "Puerto del servidor", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				8888
		);
		Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));

		vista = new VistaGUI();
		controlador = new ControladorGUI(vista);
		// vista.iniciar(); seria bueno que aca inicie la ventana 
		try {
			c.iniciar(controlador); //se supone que con esto el modelo 
									//y el controlador estan conectados
			vista.setControlador(controlador);
			launch();

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RMIMVCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlloader = new FXMLLoader(AppCliente.class.getResource("/sala_espera.fxml"));
		fxmlloader.setController(vista);

		Scene scene = new Scene(fxmlloader.load(), 640, 480);
		stage.setTitle("Saboteadores");
		stage.setScene(scene);
		stage.show();
		 // Iniciamos el proceso después de mostrar la ventana
        Platform.runLater(() -> {
            controlador.iniciar();
        });


	}

}
