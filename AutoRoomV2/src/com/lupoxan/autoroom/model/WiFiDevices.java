
package com.lupoxan.autoroom.model;

//import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Un simple cliente que envía lineas de texto al servidor y lee las respuesta
 * de él.
 * @since 05/04/2020
 * @author lupo.xan
 * @version 0.3
 */
public class WiFiDevices extends Thread{

	private Socket socket;
	private PrintWriter output;
	//private BufferedReader input;
	private final Ficheros F = new Ficheros();
	private char status;

	public WiFiDevices(InetAddress addr, char status) {
		try {
			this.status = status;
			
			socket = new Socket(addr, Integer.parseInt(F.prop(Constantes.PORT)));
			//input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		try {
			output.println(status);
			System.out.println("Enviado: " + status);
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
