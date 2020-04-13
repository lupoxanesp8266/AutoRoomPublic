
package com.lupoxan.autoroom.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Un simple cliente que env�a lineas de texto al servidor y lee las respuesta
 * de �l.
 * @since 05/04/2020
 * @author lupo.xan
 * @version 0.3
 */
public class WiFiDevices extends Thread{

	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private final Ficheros F = new Ficheros();
	private char status;

	public WiFiDevices(InetAddress addr, char status) {
		try {
			this.status = status;
			
			socket = new Socket(addr, Integer.parseInt(F.prop(Constantes.PORT)));
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			
			start();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		
		try {
			output.println(status);
			if(status == 'D') {
				String[] line = input.readLine().split(",");
				
				AutoRoom.mainFrame.getSensorsFrame().getStatusDHTValue().setText(line[0]);
				AutoRoom.mainFrame.getSensorsFrame().getTempDHTValue().setText(line[1] + " �C");
				AutoRoom.mainFrame.getSensorsFrame().getHumDHTValue().setText(line[2] + " %");
				AutoRoom.mainFrame.getSensorsFrame().getSensValue().setText(line[3] + " �C");
				
				AutoRoom.DATACLOUD.getDB().child("sensores").child("humedad").setValueAsync(line[2] + " %");
				AutoRoom.DATACLOUD.getDB().child("sensores").child("tempDht11").setValueAsync(line[1] + " �C");
				AutoRoom.DATACLOUD.getDB().child("sensores").child("sensTermica").setValueAsync(line[3] + " �C");
			}
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
