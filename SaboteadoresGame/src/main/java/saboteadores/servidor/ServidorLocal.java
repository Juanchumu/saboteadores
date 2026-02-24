package saboteadores.servidor;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;


import saboteadores.modelo.Tablero;
import saboteadores.modelo.Top;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;

import java.io.*;

public class ServidorLocal {

	public static void main(String[] args) {
		ArrayList<String> ips = Util.getIpDisponibles();
		String ip = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la que escuchará peticiones el servidor", "IP del servidor", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				ips.toArray(),
				null
				);
		String port = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que escuchará peticiones el servidor", "Puerto del servidor", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				8888
				);

		//Cargar el top 
		Top top = cargarTop();
		if (top == null) {
			top = new Top();
		}
		// Cargar el tablero 
		Tablero modelo = cargarTablero();
		if (modelo == null) {
			modelo = new Tablero(top);
			modelo.incializar();
		}


		Servidor servidor = new Servidor(ip, Integer.parseInt(port));
		try {
			servidor.iniciar(modelo);
			System.out.println("Servidor RMI iniciado en " + ip + ":" + port);
			// Mantener el servidor vivo
			System.out.println("Servidor VIVO preciona ENTER para matarlo");
			new java.util.Scanner(System.in).nextLine();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RMIMVCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final String ARCHIVO_TABLERO = "tablero.dat";
	public static void guardarTablero(Tablero tablero) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARCHIVO_TABLERO))) {
			out.writeObject(tablero);
			System.out.println("Tablero guardado.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Tablero cargarTablero() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARCHIVO_TABLERO))) {
			System.out.println("Tablero cargado.");
			return (Tablero) in.readObject();
		} catch (Exception e) {
			System.out.println("No había partida guardada.");
			return null;
		}
	}

	private static final String ARCHIVO_TOP = "top.dat";
	public static void guardarTop(Top top) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARCHIVO_TOP))) {
			out.writeObject(top);
			System.out.println("Top guardado.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public static Top cargarTop() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARCHIVO_TOP))) {
			System.out.println("Top cargado.");
			return (Top) in.readObject();
		} catch (Exception e) {
			System.out.println("No había historico guardado.");
			return null;
		}
	}

}
