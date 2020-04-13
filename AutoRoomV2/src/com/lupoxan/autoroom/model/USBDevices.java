package com.lupoxan.autoroom.model;

import java.io.IOException;

import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;

/**
 * Clase para la comunicación con el arduino que lleva los sensores analógicos (Aún en fase experimental)
 * @author lupo.xan
 * @since 31/03/2020
 * @version 0.1
 */
public class USBDevices {
	
	private IODevice arduino;
	
	Pin led, ldr;
	
	private final Ficheros F = new Ficheros();
	
	public USBDevices() {
		arduino = new FirmataDevice(F.prop(Constantes.ARDUINO));
		try {
			arduino.start();
			arduino.ensureInitializationIsDone();
			
			led = arduino.getPin(Constantes.LEDPWM);
			ldr = arduino.getPin(Constantes.LDR);
			
			led.setMode(Pin.Mode.PWM);	
			ldr.setMode(Pin.Mode.ANALOG);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public long getLux() {
		long valorOscuridad = 13;
		long valorClaridad = 1;
		long resistencia = 1;
		long value = ldr.getValue();  
		
		return ((long)value*valorOscuridad*10)/((long)valorClaridad*resistencia*(1024-value));
	}
	
	public void test(int value) {
		try {
			led.setValue(value);
			
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

	public IODevice getArduino() {
		return arduino;
	}

	public void setArduino(IODevice arduino) {
		this.arduino = arduino;
	}

	public Pin getLdr() {
		return ldr;
	}

	public void setLdr(Pin ldr) {
		this.ldr = ldr;
	}

	
}
