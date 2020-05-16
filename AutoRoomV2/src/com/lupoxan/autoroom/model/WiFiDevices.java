
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
 * Un simple cliente que envía lineas de texto al servidor y lee las respuesta
 * de él.
 * @since 05/04/2020
 * @author lupo.xan
 * @version 0.4
 */
public class WiFiDevices extends Thread{

	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private final Ficheros F = new Ficheros();
	private char status;
	private InetAddress address;

	public WiFiDevices(InetAddress addr, char status) {
		try {
			this.status = status;
			this.address = addr;
			
			socket = new Socket(addr, Integer.parseInt(F.prop(Constantes.PORT)));
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			
			start();
		} catch (IOException e) {
			if(addr.getHostAddress().equals(F.prop(Constantes.IP1))) {
				AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp32").child("exterior").child("state").setValueAsync("Disconnected");
				AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp32").child("exterior").child("rssi").setValueAsync("RSSI: NaN dbm");
			}
			if(addr.getHostAddress().equals(F.prop(Constantes.IP2))) {
				AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp8266").child("salita").child("state").setValueAsync("Disconnected");
				AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp8266").child("salita").child("rssi").setValueAsync("RSSI: NaN dbm");
			}
			if(addr.getHostAddress().equals(F.prop(Constantes.IP3))) {
				AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp8266").child("caseta").child("state").setValueAsync("Disconnected");
				AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp8266").child("caseta").child("rssi").setValueAsync("RSSI: NaN dbm");
			}
		}
	}
	
	@Override
	public void run() {
		
		try {
			output.println(status);
			switch(status) {
			case 'D':
				String[] line = input.readLine().split(",");
				AutoRoom.mainFrame.getSensorsFrame().getStatusDHTValue().setText(line[0]);
				AutoRoom.mainFrame.getSensorsFrame().getTempDHTValue().setText(line[1] + " ºC");
				AutoRoom.mainFrame.getSensorsFrame().getHumDHTValue().setText(line[2] + " %");
				AutoRoom.mainFrame.getSensorsFrame().getSensValue().setText(line[3] + " ºC");
				
				AutoRoom.DATACLOUD.getDB().child("sensores").child("humedad").setValueAsync(line[2] + " %");
				AutoRoom.DATACLOUD.getDB().child("sensores").child("tempDht11").setValueAsync(line[1] + " ºC");
				AutoRoom.DATACLOUD.getDB().child("sensores").child("sensTermica").setValueAsync(line[3] + " ºC");
				AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp32").child("exterior").child("state").setValueAsync("Connected");
				AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp32").child("exterior").child("rssi").setValueAsync("RSSI: " + line[4] + " dbm");
				break;
			case 'I':
				String rssi = input.readLine();
				if(this.address.getHostAddress().equals(F.prop(Constantes.IP2))) {
					AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp8266").child("salita").child("state").setValueAsync("Connected");
					AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp8266").child("salita").child("rssi").setValueAsync("RSSI: " + rssi + " dbm");	
				}
				if(this.address.getHostAddress().equals(F.prop(Constantes.IP3))) {
					AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp8266").child("caseta").child("state").setValueAsync("Connected");
					AutoRoom.DATACLOUD.getDB().child("wifidevices").child("esp8266").child("caseta").child("rssi").setValueAsync("RSSI: " + rssi + " dbm");	
				}
				
				break;
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
