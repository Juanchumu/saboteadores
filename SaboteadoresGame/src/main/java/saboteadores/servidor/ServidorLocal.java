package saboteadores.servidor;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import saboteadores.modelo.Tablero;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;

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
		Tablero modelo = new Tablero();
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

}
