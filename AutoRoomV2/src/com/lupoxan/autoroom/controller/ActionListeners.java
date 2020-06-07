package com.lupoxan.autoroom.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.lupoxan.autoroom.model.Constantes;
import com.lupoxan.autoroom.model.Ficheros;
import com.lupoxan.autoroom.model.Firebase;
import com.lupoxan.autoroom.model.MariaDB;
import com.lupoxan.autoroom.model.Threading;
import com.lupoxan.autoroom.model.WiFiDevices;
import com.lupoxan.autoroom.model.LocalUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.UpdateRequest;
import com.lupoxan.autoroom.model.ApagadoLuces;
import com.lupoxan.autoroom.model.AutoRoom;
import com.lupoxan.autoroom.view.MainFrame;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 0.8
 */
public class ActionListeners implements ActionListener {

	/**
	 * Frame of mainFrame Class
	 */
	protected MainFrame mainframe;
	/**
	 * For Local Database only
	 */
	private static final MariaDB LOCALBD = new MariaDB();
	/**
	 * Object for Ficheros class
	 */
	private static final Ficheros F = new Ficheros();
	/**
	 * JOptionPane options
	 */
	String[] opciones = { "Aceptar", "Cancelar" };
	/**
	 * 
	 */
	private static final int OFF = 0, COOL = 1, HEAT = 2;
	/**
	 * Modo de comfort --> Apagado = 0; Frio = 1; Calor = 2
	 */
	public static int comfortMode = OFF;
	/**
	 * 
	 */
	private ArrayList<JSlider> valores = new ArrayList<JSlider>();
	/**
	 * 
	 */
	public static Threading controlLeds = new Threading();
	/**
	 * 
	 */
	private LocalUser user;
	/**
	 * 
	 */
	private List<LocalUser> userList = new ArrayList<LocalUser>();
	/**
	 * 
	 */
	InetAddress addr;

	/**
	 * Builder
	 */
	public ActionListeners() {
		super();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "login":
			user = LOCALBD.getUser(mainframe.getLogin().getNameField().getText(),
					new String(mainframe.getLogin().getPasswordField().getPassword()));

			if (user != null) {
				back();

				mainframe.getLogin().getNameField().setText("");
				mainframe.getLogin().getPasswordField().setText("");
				mainframe.getLocalFrame().getUsuario().setText(user.getName());
				LOCALBD.addLog("LogIn for " + user.getName(), new Timestamp(new Date().getTime()));

			} else {
				JOptionPane.showMessageDialog(null, F.prop(Constantes.ERROR));
				LOCALBD.addError("Inicio de sesi�n fallido", new Timestamp(new Date().getTime()));
			}

			break;
		case "logOut":
			mainframe.getMenu().setVisible(false);
			mainframe.getLogin().setVisible(true);
			mainframe.setTitle(F.prop(Constantes.TITULO));
			user = null;
			break;
		case "lights":
			String[] botones = { "Habitaci�n", "Salita", "Exterior", "Terreno" };

			int ventana = JOptionPane.showOptionDialog(null, "�Qu� habitaci�n deseas?", "Elige habitaci�n",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones, botones[0]);

			switch (ventana) {
			case 0:
				mainframe.getLights().setVisible(true);
				mainframe.getMenu().setVisible(false);
				mainframe.setTitle("Habitaci�n 1");
				break;
			case 1:
				mainframe.getLivingRoomFrame().setVisible(true);
				mainframe.getMenu().setVisible(false);
				mainframe.setTitle("Salita");
				break;
			case 2:
				mainframe.getEntryFrame().setVisible(true);
				mainframe.getMenu().setVisible(false);
				mainframe.setTitle("Entrada");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "En construcci�n");
				break;
			}
			break;
		case "register":
			try {
				Runtime.getRuntime().exec(F.prop(Constantes.WEBPAGE));
			} catch (IOException ioe) {
				System.err.println(ioe.getMessage());
			}
			break;
		case "comfort":
			mainframe.getComfort().setVisible(true);
			mainframe.getMenu().setVisible(false);
			mainframe.setTitle("Comfort");
			break;
		case "leds":
			mainframe.getLeds().setVisible(true);
			mainframe.getMenu().setVisible(false);
			mainframe.setTitle("Leds");
			break;
		case "graphs":
			mainframe.getGraphs().setVisible(true);
			mainframe.getMenu().setVisible(false);
			mainframe.setTitle("Gr�ficas");
			break;
		case "dateChange":
			AutoRoom.DATACLOUD.fillGraphs();
			break;
		case "tools":
			mainframe.getTools().setVisible(true);
			mainframe.getMenu().setVisible(false);
			mainframe.getFirebaseFrame().setVisible(false);
			mainframe.getLocalFrame().setVisible(false);
			mainframe.setTitle("Ajustes");
			break;
		case "autoMesa":
			if (mainframe.getLights().getAutoMesa().isSelected()) {
				mainframe.getLights().getMesaOn().setVisible(false);
				mainframe.getLights().getMesaOff().setVisible(false);
				mainframe.getLights().getAutoMesaLabel().setVisible(true);
				AutoRoom.DATACLOUD.getDB().child(F.prop(Constantes.LIGHTS)).child(F.prop(Constantes.AUTOLIGHTS))
						.child(F.prop(Constantes.TABLE)).setValueAsync(true);
			} else {
				mainframe.getLights().getMesaOn().setVisible(true);
				mainframe.getLights().getMesaOff().setVisible(true);
				mainframe.getLights().getAutoMesaLabel().setVisible(false);
				AutoRoom.DATACLOUD.getDB().child(F.prop(Constantes.LIGHTS)).child(F.prop(Constantes.AUTOLIGHTS))
						.child(F.prop(Constantes.TABLE)).setValueAsync(false);
			}
			break;
		case "autoCama":
			if (mainframe.getLights().getAutoCama().isSelected()) {
				mainframe.getLights().getCamaOn().setVisible(false);
				mainframe.getLights().getCamaOff().setVisible(false);
				mainframe.getLights().getAutoCamaLabel().setVisible(true);
				AutoRoom.DATACLOUD.getDB().child("iluminacion").child("auto").child("cama").setValueAsync(true);
			} else {
				mainframe.getLights().getCamaOn().setVisible(true);
				mainframe.getLights().getCamaOff().setVisible(true);
				mainframe.getLights().getAutoCamaLabel().setVisible(false);
				AutoRoom.DATACLOUD.getDB().child("iluminacion").child("auto").child("cama").setValueAsync(false);
			}
			break;
		case "autoUp":
			if (mainframe.getLights().getAutoUp().isSelected()) {
				mainframe.getLights().getGeneralOn().setVisible(false);
				mainframe.getLights().getGeneralOff().setVisible(false);
				mainframe.getLights().getAutoUpLabel().setVisible(true);
				AutoRoom.DATACLOUD.getDB().child("iluminacion").child("auto").child("general").setValueAsync(true);
			} else {
				mainframe.getLights().getGeneralOn().setVisible(true);
				mainframe.getLights().getGeneralOff().setVisible(true);
				mainframe.getLights().getAutoUpLabel().setVisible(false);
				AutoRoom.DATACLOUD.getDB().child("iluminacion").child("auto").child("general").setValueAsync(false);
			}
			break;
		case "mesaOn":
			AutoRoom.mesa.low();
			mainframe.getLights().getMesaOn().setBackground(new Color(0, 255, 0));
			mainframe.getLights().getMesaOn().setText("Encendido");
			mainframe.getLights().getMesaOff().setBackground(new Color(51, 51, 51));
			mainframe.getLights().getMesaOff().setText("Apagar");
			AutoRoom.DATACLOUD.getDB().child(F.prop(Constantes.LIGHTS)).child("luces").child("mesa")
					.setValueAsync(true);
			break;
		case "mesaOff":
			AutoRoom.mesa.high();
			mainframe.getLights().getMesaOff().setBackground(new Color(255, 0, 0));
			mainframe.getLights().getMesaOff().setText("Apagado");
			mainframe.getLights().getMesaOn().setBackground(new Color(51, 51, 51));
			mainframe.getLights().getMesaOn().setText("Encender");
			AutoRoom.DATACLOUD.getDB().child(F.prop(Constantes.LIGHTS)).child("luces").child("mesa")
					.setValueAsync(false);
			break;
		case "camaOn":
			AutoRoom.cama.low();
			mainframe.getLights().getCamaOn().setBackground(new Color(0, 255, 0));
			mainframe.getLights().getCamaOn().setText("Encendido");
			mainframe.getLights().getCamaOff().setBackground(new Color(51, 51, 51));
			mainframe.getLights().getCamaOff().setText("Apagar");
			AutoRoom.DATACLOUD.getDB().child(F.prop(Constantes.LIGHTS)).child("luces").child("cama")
					.setValueAsync(true);
			break;
		case "camaOff":
			AutoRoom.cama.high();
			mainframe.getLights().getCamaOff().setBackground(new Color(255, 0, 0));
			mainframe.getLights().getCamaOff().setText("Apagado");
			mainframe.getLights().getCamaOn().setBackground(new Color(51, 51, 51));
			mainframe.getLights().getCamaOn().setText("Encender");
			AutoRoom.DATACLOUD.getDB().child(F.prop(Constantes.LIGHTS)).child("luces").child("cama")
					.setValueAsync(false);
			break;
		case "generalOn":
			AutoRoom.general.low();
			mainframe.getLights().getGeneralOn().setBackground(new Color(0, 255, 0));
			mainframe.getLights().getGeneralOn().setText("Encendido");
			mainframe.getLights().getGeneralOff().setBackground(new Color(51, 51, 51));
			mainframe.getLights().getGeneralOff().setText("Apagar");
			AutoRoom.DATACLOUD.getDB().child(F.prop(Constantes.LIGHTS)).child("luces").child("general")
					.setValueAsync(true);

			break;
		case "generalOff":
			AutoRoom.general.high();
			mainframe.getLights().getGeneralOff().setBackground(new Color(255, 0, 0));
			mainframe.getLights().getGeneralOff().setText("Apagado");
			mainframe.getLights().getGeneralOn().setBackground(new Color(51, 51, 51));
			mainframe.getLights().getGeneralOn().setText("Encender");
			AutoRoom.DATACLOUD.getDB().child(F.prop(Constantes.LIGHTS)).child("luces").child("general")
					.setValueAsync(false);
			break;
		case "otherOn":
			try {
				new WiFiDevices(InetAddress.getByName(F.prop(Constantes.IP1)), 'H');
				new ApagadoLuces(F.prop(Constantes.IP1), "iluminacion/luces/exterior");
				AutoRoom.DATACLOUD.getDB().child("iluminacion").child("luces").child("exterior").setValueAsync(true);
				mainframe.getEntryFrame().getOtherOn().setBackground(new Color(0, 255, 0));
				mainframe.getEntryFrame().getOtherOn().setText("Encendido");
				mainframe.getEntryFrame().getOtherOff().setBackground(new Color(51, 51, 51));
				mainframe.getEntryFrame().getOtherOff().setText("Apagar");
			} catch (UnknownHostException e2) {
				System.err.println(e2.getMessage());
				LOCALBD.addError(e2.getMessage(), new Timestamp(new Date().getTime()));
			}
			break;
		case "otherOff":
			try {
				new WiFiDevices(InetAddress.getByName(F.prop(Constantes.IP1)), 'L');
				AutoRoom.DATACLOUD.getDB().child("iluminacion").child("luces").child("exterior").setValueAsync(false);
				mainframe.getEntryFrame().getOtherOff().setBackground(new Color(255, 0, 0));
				mainframe.getEntryFrame().getOtherOff().setText("Apagado");
				mainframe.getEntryFrame().getOtherOn().setBackground(new Color(51, 51, 51));
				mainframe.getEntryFrame().getOtherOn().setText("Encender");
			} catch (UnknownHostException e2) {
				System.err.println(e2.getMessage());
				LOCALBD.addError(e2.getMessage(), new Timestamp(new Date().getTime()));
				System.out.println("A�adido error");
			}
			break;
		case "salitaOn":
			try {
				new WiFiDevices(InetAddress.getByName(F.prop(Constantes.IP2)), 'H');
				AutoRoom.DATACLOUD.getDB().child("iluminacion").child("luces").child("salita").setValueAsync(true);
				mainframe.getLivingRoomFrame().getSalitaOn().setBackground(new Color(0, 255, 0));
				mainframe.getLivingRoomFrame().getSalitaOn().setText("Encendido");
				mainframe.getLivingRoomFrame().getSalitaOff().setBackground(new Color(51, 51, 51));
				mainframe.getLivingRoomFrame().getSalitaOff().setText("Apagar");
			} catch (UnknownHostException e2) {
				System.err.println(e2.getMessage());
				LOCALBD.addError(e2.getMessage(), new Timestamp(new Date().getTime()));
				System.out.println("A�adido error");
			}
			break;
		case "salitaOff":
			try {
				new WiFiDevices(InetAddress.getByName(F.prop(Constantes.IP2)), 'L');
				AutoRoom.DATACLOUD.getDB().child("iluminacion").child("luces").child("salita").setValueAsync(false);
				mainframe.getLivingRoomFrame().getSalitaOff().setBackground(new Color(255, 0, 0));
				mainframe.getLivingRoomFrame().getSalitaOff().setText("Apagado");
				mainframe.getLivingRoomFrame().getSalitaOn().setBackground(new Color(51, 51, 51));
				mainframe.getLivingRoomFrame().getSalitaOn().setText("Encender");
			} catch (UnknownHostException e2) {
				System.err.println(e2.getMessage());
				LOCALBD.addError(e2.getMessage(), new Timestamp(new Date().getTime()));
				System.out.println("A�adido error");
			}
			break;
		case "onAllLeds":
			mainframe.getLeds().getVerdeSlider().setValue(Constantes.HUNDRED);
			mainframe.getLeds().getRojoSlider().setValue(Constantes.HUNDRED);
			mainframe.getLeds().getWhiteLSlider().setValue(Constantes.HUNDRED);
			mainframe.getLeds().getWhiteRSlider().setValue(Constantes.HUNDRED);
			mainframe.getLeds().getWhiteCSlider().setValue(Constantes.HUNDRED - Constantes.MINUS);

			mainframe.getLeds().getOnAll().setBackground(new Color(0, 255, 0));
			mainframe.getLeds().getOffAll().setBackground(new Color(51, 51, 51));
			break;
		case "offAllLeds":
			offAllLeds();
			break;
		case "heatOn":
			if (comfortMode == OFF) {
				comfortMethod(AutoRoom.comfort, 2);
				mainframe.getComfort().getHeatOn().setVisible(false);
				mainframe.getComfort().getCoolOn().setVisible(false);
				mainframe.getComfort().getCoolOff().setVisible(false);
				AutoRoom.DATACLOUD.getDB().child("comfort").child("modo").setValueAsync("Calor");
				comfortMode = HEAT;
			}
			break;
		case "heatOff":
			if (comfortMode == HEAT) {
				comfortMethod(AutoRoom.comfort, 1);
				mainframe.getComfort().getHeatOn().setVisible(true);
				mainframe.getComfort().getCoolOn().setVisible(true);
				mainframe.getComfort().getCoolOff().setVisible(true);
				AutoRoom.DATACLOUD.getDB().child("comfort").child("modo").setValueAsync("Apagado");
				comfortMode = OFF;
			}
			break;
		case "coolOn":
			if (comfortMode == OFF) {
				comfortMethod(AutoRoom.comfort, 1);
				mainframe.getComfort().getHeatOn().setVisible(false);
				mainframe.getComfort().getCoolOn().setVisible(false);
				mainframe.getComfort().getHeatOff().setVisible(false);
				AutoRoom.DATACLOUD.getDB().child("comfort").child("modo").setValueAsync("Frio");
				comfortMode = COOL;
			}
			break;
		case "coolOff":
			if (comfortMode == COOL) {
				comfortMethod(AutoRoom.comfort, 2);
				mainframe.getComfort().getHeatOn().setVisible(true);
				mainframe.getComfort().getCoolOn().setVisible(true);
				mainframe.getComfort().getHeatOff().setVisible(true);
				AutoRoom.DATACLOUD.getDB().child("comfort").child("modo").setValueAsync("Apagado");
				comfortMode = OFF;
			}
			break;
		case "fanOn":
			AutoRoom.fan.low();
			mainframe.getComfort().getFanOn().setBackground(new Color(0, 255, 0));
			mainframe.getComfort().getFanOn().setText("Encendido");
			mainframe.getComfort().getFanOff().setBackground(new Color(51, 51, 51));
			mainframe.getComfort().getFanOff().setText("Apagar");
			AutoRoom.DATACLOUD.getDB().child("comfort").child("fan").setValueAsync(true);
			break;
		case "fanOff":
			AutoRoom.fan.high();
			mainframe.getComfort().getFanOff().setBackground(new Color(255, 0, 0));
			mainframe.getComfort().getFanOff().setText("Apagado");
			mainframe.getComfort().getFanOn().setBackground(new Color(51, 51, 51));
			mainframe.getComfort().getFanOn().setText("Encender");
			AutoRoom.DATACLOUD.getDB().child("comfort").child("fan").setValueAsync(false);
			break;
		case "reboot":
			int x;
			x = JOptionPane.showOptionDialog(null, "�Seguro que deseas Reiniciar el sistema?", "Sistema",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
			if (x == 0) {
				try {
					Runtime.getRuntime().exec("reboot");
					LOCALBD.addLog("Reiniciando", new Timestamp(new Date().getTime()));
				} catch (IOException ioe) {
					System.err.println(ioe);
				}
			}
			break;
		case "powerOff":
			x = JOptionPane.showOptionDialog(null, "�Seguro que deseas Apagar el sistema?", "Sistema",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
			if (x == 0) {
				try {
					Runtime.getRuntime().exec("poweroff");
					LOCALBD.addLog("Apagando", new Timestamp(new Date().getTime()));
				} catch (IOException ioe) {
					System.err.println(ioe);
				}
			}
			break;
		case "firebaseTools":
			if (user.getLevel() == 1) {
				mainframe.getFirebaseFrame().setVisible(true);
				mainframe.getTools().setVisible(false);
				mainframe.setTitle("Firebase Admin");
			} else {
				JOptionPane.showMessageDialog(null, "No tienes permiso");
			}
			break;
		case "localTools":
			if (user.getLevel() == 1) {
				mainframe.getLocalFrame().setVisible(true);
				mainframe.getTools().setVisible(false);
				mainframe.setTitle("Local Admin");
				userList.clear();
				userList = LOCALBD.getAllUsers();
				mainframe.getLocalFrame().fillTable(userList);
			} else {
				JOptionPane.showMessageDialog(null, "No tienes permiso");
			}

			break;
		case "changeFirebaseUser":
			try {
				String uid = mainframe.getFirebaseFrame().getFirebaseUserTable().getModel()
						.getValueAt(mainframe.getFirebaseFrame().getFirebaseUserTable().getSelectedRow(), 0).toString();

				UpdateRequest request = new UpdateRequest(uid)
						.setDisabled(mainframe.getFirebaseFrame().getDisableUser().isSelected());

				UserRecord userRecord;
				userRecord = FirebaseAuth.getInstance().updateUser(request);
				JOptionPane.showMessageDialog(null, userRecord.getEmail() + " \nha sido actualizado correctamente");
				AutoRoom.DATACLOUD.getDB().child("usuarios").child(uid).child("premium")
						.setValueAsync(mainframe.getFirebaseFrame().getPremiunBox().isSelected());
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}

			break;
		case "intruderAlarm":
			if (Firebase.intruderAlarm) {
				AutoRoom.DATACLOUD.getDB().child("sistema").child("alarma").child("activada").setValueAsync(false);
			} else {
				AutoRoom.DATACLOUD.getDB().child("sistema").child("alarma").child("activada").setValueAsync(true);
			}
			break;
		case "autoLedsBox":
			if (AutoRoom.mainFrame.getLeds().getLedsAutoBox().isSelected()) {

				controlLeds.setCheckBox(AutoRoom.mainFrame.getLeds().getLedsAutoBox());
				controlLeds.setValores(AutoRoom.mainFrame.getLeds().getValores());
				controlLeds.setMode(3);
				controlLeds.setTime(null);

				mainframe.getLeds().getOnAll().setEnabled(false);
				mainframe.getLeds().getOffAll().setEnabled(false);
				mainframe.getLeds().getBlinkBox().setEnabled(false);
				mainframe.getLeds().getFadeBox().setEnabled(false);
				mainframe.getLeds().getFadeSlider().setEnabled(false);
				mainframe.getLeds().getBlinkSlider().setEnabled(false);

				AutoRoom.DATACLOUD.getDB().child("leds").child("autoLeds").setValueAsync(true);
			} else {
				controlLeds.setMode(0);

				mainframe.getLeds().getOnAll().setEnabled(true);
				mainframe.getLeds().getOffAll().setEnabled(true);
				mainframe.getLeds().getBlinkBox().setEnabled(true);
				mainframe.getLeds().getFadeBox().setEnabled(true);
				mainframe.getLeds().getFadeSlider().setEnabled(true);
				mainframe.getLeds().getBlinkSlider().setEnabled(true);

				AutoRoom.DATACLOUD.getDB().child("leds").child("autoLeds").setValueAsync(false);
			}
			break;
		case "fading":
			if (AutoRoom.mainFrame.getLeds().getFadeBox().isSelected()) {

				controlLeds.setValores(AutoRoom.mainFrame.getLeds().getValores());
				controlLeds.setCheckBox(AutoRoom.mainFrame.getLeds().getFadeBox());
				controlLeds.setTime(AutoRoom.mainFrame.getLeds().getFadeSlider());
				controlLeds.setMode(2);

				mainframe.getLeds().getOnAll().setEnabled(false);
				mainframe.getLeds().getOffAll().setEnabled(false);
				mainframe.getLeds().getBlinkBox().setEnabled(false);
				mainframe.getLeds().getBlinkSlider().setEnabled(false);
				mainframe.getLeds().getLedsAutoBox().setEnabled(false);

				AutoRoom.DATACLOUD.getDB().child("leds").child("fading").setValueAsync(true);
			} else {
				controlLeds.setMode(0);

				mainframe.getLeds().getOnAll().setEnabled(true);
				mainframe.getLeds().getOffAll().setEnabled(true);
				mainframe.getLeds().getBlinkBox().setEnabled(true);
				mainframe.getLeds().getBlinkSlider().setEnabled(true);
				mainframe.getLeds().getLedsAutoBox().setEnabled(true);

				AutoRoom.DATACLOUD.getDB().child("leds").child("fading").setValueAsync(false);
			}
			break;
		case "blinking":
			if (AutoRoom.mainFrame.getLeds().getBlinkBox().isSelected()) {

				controlLeds.setValores(AutoRoom.mainFrame.getLeds().getValores());
				controlLeds.setCheckBox(AutoRoom.mainFrame.getLeds().getBlinkBox());
				controlLeds.setTime(AutoRoom.mainFrame.getLeds().getBlinkSlider());
				controlLeds.setMode(1);

				mainframe.getLeds().getOnAll().setEnabled(false);
				mainframe.getLeds().getOffAll().setEnabled(false);
				mainframe.getLeds().getFadeBox().setEnabled(false);
				mainframe.getLeds().getFadeSlider().setEnabled(false);
				mainframe.getLeds().getLedsAutoBox().setEnabled(false);

				AutoRoom.DATACLOUD.getDB().child("leds").child("blinker").setValueAsync(true);
			} else {
				controlLeds.setMode(0);

				mainframe.getLeds().getOnAll().setEnabled(true);
				mainframe.getLeds().getOffAll().setEnabled(true);
				mainframe.getLeds().getFadeBox().setEnabled(true);
				mainframe.getLeds().getFadeSlider().setEnabled(true);
				mainframe.getLeds().getLedsAutoBox().setEnabled(true);

				AutoRoom.DATACLOUD.getDB().child("leds").child("blinker").setValueAsync(false);
			}
			break;
		case "sensores":
			mainframe.getSensorsFrame().setVisible(true);
			mainframe.getMenu().setVisible(false);
			break;
		case "backLights":
			back();
			break;
		case "back":
			back();
			break;
		case "exit":
			x = JOptionPane.showOptionDialog(null, "�Seguro que deseas salir?", "Cerrar", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
			if (x == 0) {
				AutoRoom.general.high();
				AutoRoom.mesa.high();
				AutoRoom.cama.high();
				AutoRoom.fan.high();

				System.exit(0);

			}
			break;
		case "changeLevel":
			if (mainframe.getLocalFrame().getModoBox().getSelectedItem().equals("usuario")) {
				mainframe.getLocalFrame().getLevelField().setText("2");
			} else {
				mainframe.getLocalFrame().getLevelField().setText("1");
			}
			break;
		case "addlocaluser":
			try {
				boolean flag = false;

				for (LocalUser user : userList) {
					if (mainframe.getLocalFrame().getNameField().getText().equals(user.getName())) {
						flag = true;
						break;
					}
				}
				
				if (!flag) {
					if (LOCALBD.addUser(new LocalUser(0, mainframe.getLocalFrame().getNameField().getText(),
							new String(mainframe.getLocalFrame().getPassField().getPassword()),
							Integer.parseInt(mainframe.getLocalFrame().getLevelField().getText()),
							mainframe.getLocalFrame().getModoBox().getSelectedItem().toString()))) {
						JOptionPane.showMessageDialog(null, "Usuario a�adido correctamente");

						mainframe.getLocalFrame().getNameField().setText("");
						mainframe.getLocalFrame().getPassField().setText("");

						userList.clear();
						userList = LOCALBD.getAllUsers();
						mainframe.getLocalFrame().fillTable(userList);

					} else {
						JOptionPane.showMessageDialog(null, "Error al a�adir usuario");
					}
				}else {
					JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error");
			}
			break;
		case "deletelocaluser":
			try {
				if (LOCALBD.deleteUser(Integer.parseInt(mainframe.getLocalFrame().getLocalUserTable().getModel()
						.getValueAt(mainframe.getLocalFrame().getLocalUserTable().getSelectedRow(), 0).toString()))) {
					JOptionPane.showMessageDialog(null, "Usuario borrado correctamente");

					userList.clear();
					userList = LOCALBD.getAllUsers();
					mainframe.getLocalFrame().fillTable(userList);

				} else {
					JOptionPane.showMessageDialog(null, "Error al borrar usuario");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Debes selccionar un usuario");
			}
			break;
		case "updatelocaluser":
			JLabel jUserName = new JLabel("Contrase�a actual");
			JTextField oldPassword = new JPasswordField();
			JLabel jPassword = new JLabel("Contrase�a nueva");
			JTextField newPassword = new JPasswordField();
			Object[] objetos = { jUserName, oldPassword, jPassword, newPassword };
			int result = JOptionPane.showConfirmDialog(null, objetos, "Cambio de contrase�a",
					JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {
				if (LOCALBD.password(user, oldPassword.getText())) {
					user.setPass(newPassword.getText());
					if (LOCALBD.updateUser(user)) {
						JOptionPane.showMessageDialog(null, "Contrase�a cambiada correctamente");
					} else {
						JOptionPane.showMessageDialog(null, "Ha habido un error");
					}
				} else {
					JOptionPane.showMessageDialog(null, "La contrase�a actual no coincide con la introducida");
					LOCALBD.addError("Intento de cambio de contrase�a", new Timestamp(new Date().getTime()));
				}
			}
			break;
		}
	}

	/**
	 * 
	 */
	private void offAllLeds() {
		mainframe.getLeds().getVerdeSlider().setValue(Constantes.CERO);
		mainframe.getLeds().getRojoSlider().setValue(Constantes.CERO);
		mainframe.getLeds().getWhiteLSlider().setValue(Constantes.CERO);
		mainframe.getLeds().getWhiteRSlider().setValue(Constantes.CERO);
		mainframe.getLeds().getWhiteCSlider().setValue(Constantes.CERO);

		mainframe.getLeds().getOnAll().setBackground(new Color(51, 51, 51));
		mainframe.getLeds().getOffAll().setBackground(new Color(255, 0, 0));
	}

	/**
	 * 
	 */
	private void back() {
		mainframe.getMenu().setVisible(true);
		mainframe.getLogin().setVisible(false);
		mainframe.getLights().setVisible(false);
		mainframe.getComfort().setVisible(false);
		mainframe.getLeds().setVisible(false);
		mainframe.getGraphs().setVisible(false);
		mainframe.getTools().setVisible(false);
		mainframe.getSensorsFrame().setVisible(false);
		mainframe.getLivingRoomFrame().setVisible(false);
		mainframe.getLights().setVisible(false);
		mainframe.getEntryFrame().setVisible(false);
		mainframe.setTitle("Men�");
	}

	/**
	 * M�todo para dar interactuar con el A/C
	 *
	 * @param pin Pin de salida digital de la raspberry
	 * @param num Cnatidad de toques que har� el rel� de salida
	 */
	private void comfortMethod(GpioPinDigitalOutput pin, int num) {
		try {
			for (int i = 0; i < num; i++) {
				pin.low();
				Thread.sleep(Constantes.WAIT);
				pin.high();
				Thread.sleep(Constantes.WAIT);
			}
		} catch (InterruptedException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Main Frame getter
	 * 
	 * @return Frame mainFrame
	 */
	public MainFrame getMainframe() {
		return mainframe;
	}

	/**
	 * Main frame setter
	 * 
	 * @param mainframe Frame
	 */
	public void setMainframe(MainFrame mainframe) {
		this.mainframe = mainframe;
	}

	public ArrayList<JSlider> getValores() {
		return valores;
	}

	public void setValores(ArrayList<JSlider> valores) {
		this.valores = valores;
	}
}