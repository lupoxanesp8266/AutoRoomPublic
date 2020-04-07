package com.lupoxan.autoroom.model;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.controller.ChangeStateListeners;
import com.lupoxan.autoroom.view.MainFrame;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 1.2.59
 */
public class AutoRoom {

	/**
	 * 
	 */
	public static MainFrame mainFrame = null;
	/**
	 * 
	 */
	private static Calendar cal;// Variable para generar el calendario
	/**
	 * 
	 */
	public static GpioPinDigitalInput pir;// Entradas digitales de la raspberry
	/**
	 * 
	 */
	public static GpioPinDigitalOutput mesa, cama, general, comfort, fan;// Salidas digitales de la raspberry
	/**
	 * 
	 */
	private static GpioController gpio;// Añadir un controlador para el GPIO de la raspberry
	/**
	 * 
	 */
	protected static boolean flag = false;
	/**
	 * Objeto TEMPEXT para comprobar temperatura de la clase DS18B20
	 */
	private static final Ds18b20 TEMPEXT = new Ds18b20();// Objeto TEMPEXT para comprobar temperatura de la clase DS18B20
	/**
	 * Objeto TEMPINT para comprobar temperatura de la clase DS18B20
	 */
	private static final Ds18b20 TEMPINT = new Ds18b20();// Objeto TEMPINT para comprobar temperatura de la clase DS18B20
	/**
	 * Modo del Aire Acondicionado según se actúa sobre el mismo
	 */
	protected static String modoAire = "Apagado", modoActual = modoAire;// Modo de comfort
	/**
	 * Bandera para saber si se ha registrado ya los valores de los sensores 
	 */
	private static boolean logSensors = true;// Para registrar cada media hora el estado de los sensores
	/**
	 * Control PWM (LED)
	 */
	public static int rojo, verde, whiteL, whiteR, ledsCama;// Número de los pines de salida de los Leds de la raspberry
	/**
	 * Bandera para la alarma de intrusos
	 */
	private static boolean alarmActivate = false, flagAlarmTrigger = true;// Banderas para la alarma de intruso
	/**
	 * Inicializar la posición del servo (Creo que sobra)
	 */
	private static int n = 100;// Para marcar la posición del SERVO con la PiCam [100-200]
	/**
	 * Flag para la dirección del servo
	 */
	private static boolean izquierda = true;// Para saber la direccion de la PiCam sobre el servo
	/**
     * Comunicación con Firebase DataBase (CRUD)
     */
    public static Firebase DATACLOUD;//Objeto que permite la comunicacion con la base de datos en la nube (Firebase)
    /**
     * Objeto del tipo Ficheros para el acceso al properties y al csv
     */
    public static final Ficheros F = new Ficheros();
    /**
     * 
     */
    public static Arduino test = new Arduino();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SwingUtilities.invokeLater(new Runnable(){
            public void run() {
            	ActionListeners actionlisteners = null;
            	ChangeStateListeners changeStateListeners = null;
            	actionlisteners = new ActionListeners();
        		changeStateListeners = new ChangeStateListeners();
        		DATACLOUD = new Firebase();
            	pinsIO();
        		
        		mainFrame = new MainFrame(actionlisteners, changeStateListeners);
        		actionlisteners.setMainframe(mainFrame);
        		mainFrame.setVisible(true);
        		
        		DATACLOUD.fillDates();
        		DATACLOUD.notifications();
        		DATACLOUD.lights();
        		DATACLOUD.leds();
        		DATACLOUD.alarm();
        		DATACLOUD.comfort();
        		initTimers();
            }
        });
		
	}
	
	private static void pinsIO() {
		Gpio.wiringPiSetup();
		gpio = GpioFactory.getInstance();// Controlador GPIO llamado gpio

		general = gpio.provisionDigitalOutputPin(Constantes.GENERAL, Constantes.HIGH);// Lámpara general
		cama = gpio.provisionDigitalOutputPin(Constantes.CAMA, Constantes.HIGH);// Lámpara de la cama
		mesa = gpio.provisionDigitalOutputPin(Constantes.MESA, Constantes.HIGH);// lámpara de la mesa
		comfort = gpio.provisionDigitalOutputPin(Constantes.CLIMA, Constantes.HIGH);// Relé para comfort
		fan = gpio.provisionDigitalOutputPin(Constantes.FAN,Constantes.HIGH);//Relé para el enchufe de abajo

		pir = gpio.provisionDigitalInputPin(Constantes.MOV, Constantes.RUP);// Sensor de movimiento

		rojo = Constantes.ORED;
		verde = Constantes.OGREEN;
		whiteL = Constantes.OWHITEL;
		whiteR = Constantes.OWHITER;
		ledsCama = Constantes.OWHITEC;

		SoftPwm.softPwmCreate(rojo, Constantes.CERO, Constantes.HUNDRED);
		SoftPwm.softPwmCreate(verde, Constantes.CERO, Constantes.HUNDRED);
		SoftPwm.softPwmCreate(whiteL, Constantes.CERO, Constantes.HUNDRED);
		SoftPwm.softPwmCreate(whiteR, Constantes.CERO, Constantes.HUNDRED);
		SoftPwm.softPwmCreate(ledsCama, Constantes.CERO, Constantes.HUNDRED - Constantes.MINUS);

	}//*/

	private static void initTimers() {
		Timer fechora;// Para mostrar la fecha y la hora
		Timer timerPir;// Para el sensor PIR
		Timer tweet;// Para send_tweet.py
		Timer timerTempInt;// Para la temperatura
		Timer timerTempExt;// Para la temperatura
		Timer timerTemps;// Para almacenar en la base de datos las temperaturas
		Timer lastConnection;// Para actualizar la última conexión
		Timer intruso;// Para la alarma de intruso
		Timer autoLights;// Para las luces automáticas
		Timer servoTimer;// Para el SERVO con PiCam
		Timer timerConsigna;// Para la consigna de comfort
		Timer lux;//Para ver la cantidad de lux que hay

		TimerTask mostrarFechora;// Tarea para mostrar la fecha y la hora
		TimerTask tareaPir;// Para el sensor PIR
		TimerTask send_tweet;// Para enviar send_tweet.py
		TimerTask tareaTempInt;// Para la temperatura
		TimerTask tareaTempExt;// Para la temperatura
		TimerTask tareaTemps;// Para poner en la base de datos las temperaturas
		TimerTask tareaLast;// Para actualizar la última conexión
		TimerTask intrusoTask;// Tarea para la alarma de intruso
		TimerTask autoLTask;// Tarea para las luces automáticas
		TimerTask servoTask;// Para mover la PiCam con el SERVO
		TimerTask taskConsigna;// Para la consigna de comfort
		TimerTask luxTask;//Para ver la cantidad de lux que hay

		// Timer - 0
		fechora = new Timer();
		mostrarFechora = new TimerTask() {
			@Override
			public void run() {
				cal = Calendar.getInstance();

				String hora = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));
				String fecha = String.format("%02d-%02d-%04d", cal.get(Calendar.DATE), cal.get(Calendar.MONTH) + 1,cal.get(Calendar.YEAR));

				mainFrame.getMenu().getDateLabel().setText(fecha);
				mainFrame.getMenu().getHourLabel().setText(hora);

				if (hora.equals("07:00:00")) {
					AutoRoom.DATACLOUD.sendNotification(Firebase.DIARIAS, "Exterior: " + mainFrame.getMenu().getTempExtValue().getText(), "Buenos días !", "morning", "logo144","com.lupoxan.autoRoom.diary");
				}
				if (hora.equals("00:00:00")) {
					AutoRoom.DATACLOUD.sendNotification(Firebase.DIARIAS, "Exterior: " + mainFrame.getMenu().getTempExtValue().getText(), "Buenas noches !", "night","logo144","com.lupoxan.autoRoom.diary");
				}
				if (hora.equals("21:00:00")) {
					AutoRoom.DATACLOUD.sendNotification(Firebase.DIARIAS, "Exterior: " + mainFrame.getMenu().getTempExtValue().getText(), "A cenar que ya es hora !", "reminder","logo144","com.lupoxan.autoRoom.diary");
				}
			}
		};
		fechora.scheduleAtFixedRate(mostrarFechora, 10, 1000);// Para ver la fecha y la hora cada segundo*/
		// Timer - 1
		timerPir = new Timer();
		tareaPir = new TimerTask() {
			@Override
			public void run() {
				AutoRoom.DATACLOUD.getDB().child("sensores").child("movimiento").setValueAsync(pir.getState());
				mainFrame.getMenu().getMovementValue().setText(pir.getState().toString());
				
				if (pir.isHigh() && flag) {
					AutoRoom.DATACLOUD.getDB().child("sensores").child("PIR").child(AutoRoom.mainFrame.getMenu().getDateLabel().getText()).child(AutoRoom.mainFrame.getMenu().getHourLabel().getText()).setValueAsync(AutoRoom.pir.getState());
					flag = false;
				} else {
					if (pir.isLow()) {
						flag = true;
					}
				}
			}
		};
		timerPir.scheduleAtFixedRate(tareaPir, 11, 250);// Leer el estado del sensor PIR*/
		// Timer - 2
		tweet = new Timer();
		send_tweet = new TimerTask() {
			@Override
			public void run() {
				TwitterDev tw = new TwitterDev();
				tw.sendTweet("Temperatura Interior: " + TEMPINT.getTempC(true) + " ºC -- Temperatura Exterior: "
						+ TEMPEXT.getTempC(false) + " ºC -- Climatización: " + modoAire);
			}
		};
		tweet.scheduleAtFixedRate(send_tweet, 12, 60000 * 60 * 8);// Para enviar tweet cada 8 HORAS*/
		// Timer - 3 Para la temperatura Interior
		timerTempInt = new Timer();
		tareaTempInt = new TimerTask() {
			@Override
			public void run() {
				double temperaturaI = TEMPINT.getTempC(true);

				mainFrame.getMenu().getTempIntValue().setText(String.format("%.2f ºC", temperaturaI));// Poner texto
				mainFrame.getComfort().getTempIntValue().setText(String.format("%.2f ºC", temperaturaI));
				
				DATACLOUD.getDB().child("sensores").child("temp_int").setValueAsync(mainFrame.getMenu().getTempIntValue().getText());
			}
		};
		timerTempInt.scheduleAtFixedRate(tareaTempInt, 13, 60000 * 2);// Para temperatura cada minuto*/
		// Timer - 4 Para la temperatura Exterior
		timerTempExt = new Timer();
		tareaTempExt = new TimerTask() {
			@Override
			public void run() {
				double temperaturaE = TEMPEXT.getTempC(false);

				mainFrame.getMenu().getTempExtValue().setText(String.format("%.2f ºC", temperaturaE));// Poner texto
				mainFrame.getComfort().getTempExtValue().setText(String.format("%.2f ºC", temperaturaE));

				DATACLOUD.getDB().child("sensores").child("temp_ext").setValueAsync(mainFrame.getMenu().getTempExtValue().getText());
			}
		};
		timerTempExt.scheduleAtFixedRate(tareaTempExt, 14, 60000 * 2);// Para temperatura cada minuto*/
		// Timer - 5 Para guardar la temperatura cada 30 minutos
		timerTemps = new Timer();
		tareaTemps = new TimerTask() {
			@Override
			public void run() {
				String[] fecha = mainFrame.getMenu().getHourLabel().getText().split(":");
				if (fecha[1].equals("30") || fecha[1].equals("00")) {
					if (logSensors) {
						 AutoRoom.DATACLOUD.getDB().child(AutoRoom.F.prop(Constantes.SENSORS)).child(AutoRoom.mainFrame.getMenu().getDateLabel().getText() + "/" + AutoRoom.mainFrame.getMenu().getHourLabel().getText()).child("Temp_Ext").setValueAsync(AutoRoom.mainFrame.getMenu().getTempExtValue().getText());
						 AutoRoom.DATACLOUD.getDB().child(AutoRoom.F.prop(Constantes.SENSORS)).child(AutoRoom.mainFrame.getMenu().getDateLabel().getText() + "/" + AutoRoom.mainFrame.getMenu().getHourLabel().getText()).child("Temp_Int").setValueAsync(AutoRoom.mainFrame.getMenu().getTempIntValue().getText());
						 AutoRoom.DATACLOUD.getDB().child(AutoRoom.F.prop(Constantes.SENSORS)).child(AutoRoom.mainFrame.getMenu().getDateLabel().getText() + "/" + AutoRoom.mainFrame.getMenu().getHourLabel().getText()).child("Movimiento").setValueAsync(AutoRoom.pir.getState());

						logSensors = false;
					}
				} else {
					logSensors = true;
				}
			}
		};
		timerTemps.scheduleAtFixedRate(tareaTemps, 15, 1000);// Cada 30 Minutos en DATACLOUD*/
		// Timer - 6 Para la última conexión de la Raspberry con Firebase Database
		lastConnection = new Timer();
		tareaLast = new TimerTask() {
			@Override
			public void run() {
				cal = Calendar.getInstance();

				String horaActual = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));

				String[] fecha = horaActual.split(":");

				if (fecha[2].equals("00")) {
					AutoRoom.DATACLOUD.getDB().child("sistema").child("last-connection").setValueAsync(horaActual);
				}
			}
		};
		lastConnection.scheduleAtFixedRate(tareaLast, 16, 500);// Última conexión de la Raspi con Firebase Database*/
		// Timer - 7 Para la alerta de intruso (email)
		intruso = new Timer();
		intrusoTask = new TimerTask() {
			@Override
			public void run() {
				// Si está la alarma activada comprobamos el estado del sensor
				if (alarmActivate) {
					if (pir.getState().isHigh()) {
						if (flagAlarmTrigger) {
							// AutoRoom.foto_button.doClick();
							flagAlarmTrigger = false;
							AutoRoom.DATACLOUD.getDB().child("sistema").child("alarma").child("disparada").setValueAsync(true);
						}
						cama.toggle();
						mesa.toggle();
						general.toggle();
					} else {
						AutoRoom.DATACLOUD.getDB().child("sistema").child("alarma").child("disparada").setValueAsync(false);
						if (!flagAlarmTrigger) {
							flagAlarmTrigger = true;
						}
					}
				}
			}
		};
		intruso.schedule(intrusoTask, 17, 250);// Para la alarma de intruso*/
		// Timer - 8 Para las luces automáticas
		autoLights = new Timer();
		autoLTask = new TimerTask() {
			@Override
			public void run() {
				if (mainFrame.getLights().getAutoMesa().isSelected()) {
					if (pir.getState().isHigh()) {
						mainFrame.getLights().getAutoMesaLabel().setText("Encendido");
						mainFrame.getLights().getMesaOn().doClick();
					} else {
						mainFrame.getLights().getAutoMesaLabel().setText("Apagado");
						mainFrame.getLights().getMesaOff().doClick();
					}
				}
				if (mainFrame.getLights().getAutoCama().isSelected()) {
					if (pir.getState().isHigh()) {
						mainFrame.getLights().getAutoCamaLabel().setText("Encendido");
						mainFrame.getLights().getCamaOn().doClick();
					} else {
						mainFrame.getLights().getAutoCamaLabel().setText("Apagado");
						mainFrame.getLights().getCamaOff().doClick();
					}
				}
				if (mainFrame.getLights().getAutoUp().isSelected()) {
					if (pir.getState().isHigh()) {
						mainFrame.getLights().getAutoUpLabel().setText("Encendido");
						mainFrame.getLights().getGeneralOn().doClick();
					} else {
						mainFrame.getLights().getAutoUpLabel().setText("Apagado");
						mainFrame.getLights().getGeneralOff().doClick();
					}
				}
			}
		};
		autoLights.schedule(autoLTask, 18, 250);// Para las luces automáticas*/
		// Timer - 9 Para la cámara automática
		servoTimer = new Timer();
		servoTask = new TimerTask() {
			@Override
			public void run() {
				if (mainFrame.getTools().getServoCam().isSelected()) {
					// servoCam.setValue(n);
					if (izquierda) {
						n++;
					} else {
						n--;
					}
					if (n < 100) {
						izquierda = true;
					}
					if (n > 200) {
						izquierda = false;
					}
				}
			}
		};
		servoTimer.scheduleAtFixedRate(servoTask, 19, 500);// Para mover el SERVO con la PiCam*/
		// Timer - 10
		timerConsigna = new Timer();
		taskConsigna = new TimerTask() {
			@Override
			public void run() {
				/*
				 * String[] consigna = AutoRoom.consignaLabel.getText().split(" ºC"); String[]
				 * tempIn = AutoRoom.tempIntLabel.getText().split(",");
				 * 
				 * if (AutoRoom.autoFrio.isSelected()) { if (Integer.parseInt(tempIn[0]) <
				 * Integer.parseInt(consigna[0])) { AutoRoom.aire_off_frio.doClick();
				 * AutoRoom.autoFrioLabel.setText("Apagado"); } if (Integer.parseInt(tempIn[0])
				 * > Integer.parseInt(consigna[0])) { AutoRoom.aire_on_frio.doClick();
				 * AutoRoom.autoFrioLabel.setText("Encendido"); } } if
				 * (AutoRoom.autoCalor.isSelected()) { if (Integer.parseInt(tempIn[0]) <
				 * Integer.parseInt(consigna[0])) { AutoRoom.aire_on_calor.doClick();
				 * AutoRoom.autoCalorLabel.setText("Encendido"); } if
				 * (Integer.parseInt(tempIn[0]) > Integer.parseInt(consigna[0])) {
				 * AutoRoom.aire_off_calor.doClick();
				 * AutoRoom.autoCalorLabel.setText("Apagado"); } }
				 */
			}
		};
		timerConsigna.scheduleAtFixedRate(taskConsigna, 20, 60000 * 3);// Ver temperatura cada 2 minutos*/
		// Timer - 11
		lux = new Timer();
		luxTask = new TimerTask() {
			
			@Override
			public void run() {
				AutoRoom.mainFrame.getMenu().getLuxValue().setText(test.getLux() + " lx");
				DATACLOUD.getDB().child("sensores").child("lux").setValueAsync(test.getLux());
				
			}
		};
		lux.scheduleAtFixedRate(luxTask, 21, 10000);// Ver los lux que hay*/

	}


}
