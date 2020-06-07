package com.lupoxan.autoroom.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @since 27/05/2020
 * @author lupo.xan
 * @version 0.2
 *
 */
public class ApagadoLuces extends Thread {
	String ip, ruta;
	public ApagadoLuces(String ip, String ruta) {
		this.ip = ip;
		this.ruta = ruta;
		start();
	}
	
	@Override
	public void run() {
		try {
			sleep(60000 * 2);
			new WiFiDevices(InetAddress.getByName(this.ip), 'L');
			AutoRoom.DATACLOUD.getDB().child(this.ruta).setValueAsync(false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
