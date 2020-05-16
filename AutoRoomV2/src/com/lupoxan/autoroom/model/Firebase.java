/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lupoxan.autoroom.model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.view.GraphsFrame;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Clase para la utilización de Firebase
 *
 * @since 05/08/2019
 * @author lupo.xan
 * @version 2.1
 */
public class Firebase {

	/**
	 * Objeto para la utilización del archivo properties
	 */
	private final Ficheros F = new Ficheros();
	/**
	 * Referencia a la base de datos de Firebase
	 */
	private DatabaseReference DB;// Referencia a la base de datos de Firebase, aún sin auth.
	/**
	 * DataSet para llevar a las gráficas
	 */
	protected final static DefaultCategoryDataset DATOTE = new DefaultCategoryDataset();// DataSet para las temperaturas
	/**
	 * Lista con los valores de las fechas guardadas
	 */
	protected static final List<String> FECHAS = new ArrayList<String>();// Lista con las fechas de los valores
	/**
	 * Lista para almacenar las horas (cada media hora)
	 */
	protected final static List<String> HORAS = new ArrayList<String>();// ArrayList para recoger las horas del dia
	/**
	 * Lista con los tokens de cada dispositivo
	 */
	protected static final List<String> TOKENS = new ArrayList<String>();// Lista con los tokens de cada dispositivo
	/**
	 * Lista con los tokens de cada dispositivo que solo quiere las diarias
	 */
	protected static final List<String> DIARIAS = new ArrayList<String>();
	/**
	 * Lista con los tokens de cada dispositivo que solo quiere las de movimiento
	 */
	protected static final List<String> MOVIMIENTO = new ArrayList<String>();
	/**
	 * Lista con los tokens de cada dispositivo que solo quiere las de temperatura
	 */
	protected static final List<String> TEMPERATURAS = new ArrayList<String>();
	/**
	 * Lista con los usuarios de firebase
	 */
	private List<FirebaseUser> userList = new ArrayList<FirebaseUser>();
	/**
	 * Boolean para el estado de la alarma
	 */
	public static boolean intruderAlarm = false;

	/**
	 * Constructor que especifica la conexión con la bd remota
	 *
	 */
	public Firebase() {
		FileInputStream serviceAccount = null;
		
		try {
			serviceAccount = new FileInputStream("/home/pi/autoRoom/Scripts/myautoroom-firebase-adminsdk-fh1iu-28fe4530af.json");
			FirebaseOptions optionsFirebase = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://myautoroom.firebaseio.com/").build();
			FirebaseApp.initializeApp(optionsFirebase);
			
			this.DB = FirebaseDatabase.getInstance().getReference();
		} catch (FileNotFoundException ex) {
			System.err.println(ex.getMessage());
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/*
	 * private List<FirebaseUser> userList() {
	 * 
	 * ListUsersPage page;
	 * 
	 * try { page = FirebaseAuth.getInstance().listUsers(null); for
	 * (ExportedUserRecord user : page.iterateAll()) {
	 * this.DB.child("usuarios").child(user.getUid()).child("email").setValueAsync(
	 * user.getEmail());
	 * this.DB.child("usuarios").child(user.getUid()).child("token").setValueAsync(
	 * "");
	 * this.DB.child("usuarios").child(user.getUid()).child("premium").setValueAsync
	 * (false);
	 * this.DB.child("usuarios").child(user.getUid()).child("notificaciones").child(
	 * "diarias") .setValueAsync(false);
	 * this.DB.child("usuarios").child(user.getUid()).child("notificaciones").child(
	 * "temperaturas") .setValueAsync(false);
	 * this.DB.child("usuarios").child(user.getUid()).child("notificaciones").child(
	 * "movimiento") .setValueAsync(false); } } catch (FirebaseAuthException e) {
	 * e.printStackTrace(); }
	 * 
	 * return userList; }
	 */

	/**
	 * Método para enviar las notificaciones a la app
	 */
	protected void rellenarToken() {

		this.DB.child("usuarios").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot ds) {
				TOKENS.clear();
				DIARIAS.clear();
				MOVIMIENTO.clear();
				TEMPERATURAS.clear();
				userList.clear();

				for (DataSnapshot tk : ds.getChildren()) {
					FirebaseUser firebaseUser = new FirebaseUser();

					String email = tk.child("email").getValue().toString();
					firebaseUser.setEmail(email);

					String token = tk.child("token").getValue().toString();
					firebaseUser.setToken(token);

					String uid = tk.getKey();
					firebaseUser.setUid(uid);

					try {
						UserRecord user = FirebaseAuth.getInstance().getUser(uid);
						firebaseUser.setDisable(user.isDisabled());
					} catch (Exception e) {
						e.printStackTrace();
					}

					boolean premium = (boolean) tk.child("premium").getValue();
					firebaseUser.setPremium(premium);

					if (!token.equals("")) {
						TOKENS.add(token);// Añadir al array de tokens
					}

					boolean[] notificaciones = new boolean[3];

					if ((boolean) tk.child("notificaciones").child("diarias").getValue()) {
						DIARIAS.add(tk.child("token").getValue().toString());
						notificaciones[0] = true;
					} else {
						notificaciones[0] = false;
					}
					if ((boolean) tk.child("notificaciones").child("temperaturas").getValue()) {
						TEMPERATURAS.add(tk.child("token").getValue().toString());
						notificaciones[1] = true;
					} else {
						notificaciones[1] = false;
					}
					if ((boolean) tk.child("notificaciones").child("movimiento").getValue()) {
						MOVIMIENTO.add(tk.child("token").getValue().toString());
						notificaciones[2] = true;
					} else {
						notificaciones[2] = false;
					}

					firebaseUser.setNotifications(notificaciones);
					userList.add(firebaseUser);
				}

				AutoRoom.mainFrame.getFirebaseFrame().fillTable(userList);
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}
		});
	}

	/**
	 * Método para recuperar los estados de las alarmas
	 */
	protected void alarm() {
		this.DB.child("sistema").child("alarma").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if ((boolean) snapshot.child("activada").getValue()) {
					AutoRoom.mainFrame.getTools().getAlarmButton().setBackground(new Color(0, 255, 0));
					Firebase.intruderAlarm = true;
				} else {
					AutoRoom.mainFrame.getTools().getAlarmButton().setBackground(new Color(255, 0, 0));
					Firebase.intruderAlarm = false;
				}
			}

			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println(error.getMessage());

			}
		});
	}

	/**
	 * Método para generar las notificaciones
	 */
	protected void notifications() {
		rellenarToken();
		this.DB.child(F.prop(Constantes.SENSORS)).child(F.prop(Constantes.MOVELOWER)).addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot ds) {
						if (ds.getValue().toString().equals(F.prop(Constantes.HIGHUPPER)) && Firebase.intruderAlarm) {
							sendNotification(MOVIMIENTO,
									AutoRoom.mainFrame.getMenu().getHourLabel().getText() + " -- "
											+ AutoRoom.mainFrame.getMenu().getDateLabel().getText(),
									F.prop(Constantes.MOVEUPPER), F.prop(Constantes.INTRUDER), "intruso",
									"com.lupoxan.autoRoom.movement", AndroidConfig.Priority.HIGH);
						}
					}

					@Override
					public void onCancelled(DatabaseError de) {
						System.err.println(de.getMessage());
					}
				});// Enviar notificaciones

		this.DB.child(F.prop(Constantes.SENSORS)).child("temp_ext").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				String[] temperaturaExterior = ds.getValue().toString().split(",");

				if (Integer.parseInt(temperaturaExterior[0]) >= 30) {
					sendNotification(TEMPERATURAS, "Temperatura: " + ds.getValue().toString(), "Temperatura exterior",
							"Temperatura", "logo144_round", "com.lupoxan.autoRoom.temp_ext", AndroidConfig.Priority.NORMAL);

				} else {
					if (Integer.parseInt(temperaturaExterior[0]) <= 10) {
						sendNotification(TEMPERATURAS, "Temperatura: " + ds.getValue().toString(),
								"Temperatura exterior", "Temperatura", "logo144_round",
								"com.lupoxan.autoRoom.temp_ext", AndroidConfig.Priority.NORMAL);
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError de) {

			}

		});// Enviar notificaciones

		this.DB.child(F.prop(Constantes.SENSORS)).child("temp_int").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				String[] temperaturaInterior = ds.getValue().toString().split(",");

				if (Integer.parseInt(temperaturaInterior[0]) >= 30) {
					sendNotification(TEMPERATURAS, "Temperatura: " + ds.getValue().toString(), "Temperatura interior",
							"Temperatura", "logo144", "com.lupoxan.autoRoom.temp_int", AndroidConfig.Priority.NORMAL);

				} else {
					if (Integer.parseInt(temperaturaInterior[0]) <= 10) {
						sendNotification(TEMPERATURAS, "Temperatura: " + ds.getValue().toString(),
								"Temperatura interior", "Temperatura", "logo144", "com.lupoxan.autoRoom.temp_int", AndroidConfig.Priority.NORMAL);
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError de) {

			}

		});// Enviar notificaciones

	}

	/**
	 * Método para la visualización de los estados de la iluminación
	 */
	protected void lights() {

		this.DB.child("iluminacion").child("luces").child("mesa").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot ds) {
				// boolean statusMesa = (boolean) ds.getValue();
				if ((boolean) ds.getValue()) {
					AutoRoom.mainFrame.getLights().getMesaOn().doClick();
				} else {
					AutoRoom.mainFrame.getLights().getMesaOff().doClick();
				}
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});
		this.DB.child("iluminacion").child("luces").child("cama").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot ds) {
				boolean statusCama = (boolean) ds.getValue();
				if (statusCama) {
					AutoRoom.mainFrame.getLights().getCamaOn().doClick();
				} else {
					AutoRoom.mainFrame.getLights().getCamaOff().doClick();
				}
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});
		this.DB.child("iluminacion").child("luces").child("general").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				boolean statusUp = (boolean) ds.getValue();
				if (statusUp) {
					AutoRoom.mainFrame.getLights().getGeneralOn().doClick();
				} else {
					AutoRoom.mainFrame.getLights().getGeneralOff().doClick();
				}
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});
		this.DB.child("iluminacion").child("luces").child("exterior").addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				boolean statusOther = (boolean) snapshot.getValue();
				if (statusOther) {
					AutoRoom.mainFrame.getEntryFrame().getOtherOn().doClick();
				} else {
					AutoRoom.mainFrame.getEntryFrame().getOtherOff().doClick();
				}
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println(error.getMessage());
				
			}
		});//*/
		this.DB.child("iluminacion").child("luces").child("salita").addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				boolean statusOther = (boolean) snapshot.getValue();
				if (statusOther) {
					AutoRoom.mainFrame.getLivingRoomFrame().getSalitaOn().doClick();
				} else {
					AutoRoom.mainFrame.getLivingRoomFrame().getSalitaOff().doClick();
				}
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println(error.getMessage());
				
			}
		});//*/
		this.DB.child("iluminacion").child("luces").child("caseta").addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if((Boolean) snapshot.getValue()) {
					try {
						new WiFiDevices(InetAddress.getByName(F.prop(Constantes.IP3)), 'H');
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}else {
					try {
						new WiFiDevices(InetAddress.getByName(F.prop(Constantes.IP3)), 'L');
					} catch (UnknownHostException e2) {
						e2.printStackTrace();
					}
				}
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});//*/

		this.DB.child("iluminacion").child("auto").child("mesa").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLights().getAutoMesa().setSelected((boolean) ds.getValue());

				if ((boolean) ds.getValue()) {
					AutoRoom.mainFrame.getLights().getMesaOn().setVisible(false);
					AutoRoom.mainFrame.getLights().getMesaOff().setVisible(false);
					AutoRoom.mainFrame.getLights().getAutoMesaLabel().setVisible(true);
				} else {
					AutoRoom.mainFrame.getLights().getMesaOn().setVisible(true);
					AutoRoom.mainFrame.getLights().getMesaOff().setVisible(true);
					AutoRoom.mainFrame.getLights().getAutoMesaLabel().setVisible(false);
				}
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});
		this.DB.child("iluminacion").child("auto").child("cama").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLights().getAutoCama().setSelected((boolean) ds.getValue());

				if ((boolean) ds.getValue()) {
					AutoRoom.mainFrame.getLights().getCamaOn().setVisible(false);
					AutoRoom.mainFrame.getLights().getCamaOff().setVisible(false);
					AutoRoom.mainFrame.getLights().getAutoCamaLabel().setVisible(true);
				} else {
					AutoRoom.mainFrame.getLights().getCamaOn().setVisible(true);
					AutoRoom.mainFrame.getLights().getCamaOff().setVisible(true);
					AutoRoom.mainFrame.getLights().getAutoCamaLabel().setVisible(false);
				}
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});
		this.DB.child("iluminacion").child("auto").child("general").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLights().getAutoUp().setSelected((boolean) ds.getValue());

				if ((boolean) ds.getValue()) {
					AutoRoom.mainFrame.getLights().getGeneralOn().setVisible(false);
					AutoRoom.mainFrame.getLights().getGeneralOff().setVisible(false);
					AutoRoom.mainFrame.getLights().getAutoUpLabel().setVisible(true);
				} else {
					AutoRoom.mainFrame.getLights().getGeneralOn().setVisible(true);
					AutoRoom.mainFrame.getLights().getGeneralOff().setVisible(true);
					AutoRoom.mainFrame.getLights().getAutoUpLabel().setVisible(false);
				}
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});
	}

	/**
	 * Método para la visualización de los estados de los leds
	 */
	protected void leds() {
		this.DB.child("leds").child("rojos").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLeds().getRojoSlider().setValue(Integer.parseInt(ds.getValue().toString()));
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});//
		this.DB.child("leds").child("verdes").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLeds().getVerdeSlider().setValue(Integer.parseInt(ds.getValue().toString()));
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});//
		this.DB.child("leds").child("blanco_l").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLeds().getWhiteLSlider().setValue(Integer.parseInt(ds.getValue().toString()));
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});//
		this.DB.child("leds").child("blanco_r").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLeds().getWhiteRSlider().setValue(Integer.parseInt(ds.getValue().toString()));
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});//
		this.DB.child("leds").child("blanco_cama").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLeds().getWhiteCSlider().setValue(Integer.parseInt(ds.getValue().toString()));
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});//

		this.DB.child("leds").child("autoLeds").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLeds().getLedsAutoBox().setSelected((boolean) ds.getValue());
				
				if((boolean) ds.getValue()) {
					ActionListeners.controlLeds.setValores(AutoRoom.mainFrame.getLeds().getValores());
					ActionListeners.controlLeds.setMode(3);
					ActionListeners.controlLeds.setTime(null);
					ActionListeners.controlLeds.setCheckBox(AutoRoom.mainFrame.getLeds().getLedsAutoBox());
					
					AutoRoom.mainFrame.getLeds().getOnAll().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getOffAll().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getBlinkBox().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getFadeBox().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getFadeSlider().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getBlinkSlider().setEnabled(false);
				}else {
					ActionListeners.controlLeds.setMode(0);
					
					AutoRoom.mainFrame.getLeds().getOnAll().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getOffAll().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getBlinkBox().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getFadeBox().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getFadeSlider().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getBlinkSlider().setEnabled(true);
				}
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});
		this.DB.child("leds").child("fading").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLeds().getFadeBox().setSelected((boolean) ds.getValue());
				
				if (AutoRoom.mainFrame.getLeds().getFadeBox().isSelected()) {

					ActionListeners.controlLeds.setValores(AutoRoom.mainFrame.getLeds().getValores());
					ActionListeners.controlLeds.setCheckBox(AutoRoom.mainFrame.getLeds().getFadeBox());
					ActionListeners.controlLeds.setTime(AutoRoom.mainFrame.getLeds().getFadeSlider());
					ActionListeners.controlLeds.setMode(2);

					AutoRoom.mainFrame.getLeds().getOnAll().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getOffAll().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getBlinkBox().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getBlinkSlider().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getLedsAutoBox().setEnabled(false);

				} else {
					ActionListeners.controlLeds.setMode(0);

					AutoRoom.mainFrame.getLeds().getOnAll().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getOffAll().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getBlinkBox().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getBlinkSlider().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getLedsAutoBox().setEnabled(true);

				}
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});//
		this.DB.child("leds").child("blinker").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLeds().getBlinkBox().setSelected((boolean) ds.getValue());
				
				if (AutoRoom.mainFrame.getLeds().getBlinkBox().isSelected()) {

					ActionListeners.controlLeds.setValores(AutoRoom.mainFrame.getLeds().getValores());
					ActionListeners.controlLeds.setCheckBox(AutoRoom.mainFrame.getLeds().getBlinkBox());
					ActionListeners.controlLeds.setTime(AutoRoom.mainFrame.getLeds().getBlinkSlider());
					ActionListeners.controlLeds.setMode(1);

					AutoRoom.mainFrame.getLeds().getOnAll().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getOffAll().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getFadeBox().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getFadeSlider().setEnabled(false);
					AutoRoom.mainFrame.getLeds().getLedsAutoBox().setEnabled(false);

				} else {
					ActionListeners.controlLeds.setMode(0);

					AutoRoom.mainFrame.getLeds().getOnAll().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getOffAll().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getFadeBox().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getFadeSlider().setEnabled(true);
					AutoRoom.mainFrame.getLeds().getLedsAutoBox().setEnabled(true);

				}
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});

		this.DB.child("leds").child("timer_fade").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLeds().getFadeSlider().setValue(Integer.parseInt(ds.getValue().toString()));
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});
		this.DB.child("leds").child("timer_blink").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getLeds().getBlinkSlider()
						.setValue(Integer.parseInt(ds.getValue().toString()) / 1000);
			}

			@Override
			public void onCancelled(DatabaseError de) {
				System.err.println(de.getMessage());
			}

		});

	}

	/**
	 * Método para la visualización de los estados de comfort
	 */
	protected void comfort() {

		this.DB.child("comfort").child("consigna").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot ds) {
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setValue(Integer.parseInt(ds.getValue().toString()));
				AutoRoom.mainFrame.getComfort().getConsignaLabel().setText(" " + Integer.parseInt(ds.getValue().toString()) + " ºC ");
			}

			@Override
			public void onCancelled(DatabaseError de) {

			}
		});//*/
		this.DB.child("comfort").child("fan").addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if((boolean) snapshot.getValue()) {
					AutoRoom.mainFrame.getComfort().getFanOn().doClick();
				}else{
					AutoRoom.mainFrame.getComfort().getFanOff().doClick();
				}
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});//*/

	}

	/**
	 * Método para obtener los valores de los sensores
	 */
	public void fillGraphs() {
		if (GraphsFrame.fechasData.getSelectedItem().toString() != null) {
			this.DB.child("sensores").child(GraphsFrame.fechasData.getSelectedItem().toString())
					.addValueEventListener(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot ds) {
							if (HORAS.isEmpty()) {
								for (DataSnapshot data : ds.getChildren()) {
									HORAS.add(data.getKey());
								}
							} else {
								HORAS.clear();
								DATOTE.clear();
								for (DataSnapshot data : ds.getChildren()) {
									HORAS.add(data.getKey());
								}
							}

							for (String hora : HORAS) {
								String numeroE = ds.child(hora).child("Temp_Ext").getValue().toString().split("ºC")[0]
										.trim().replaceAll(",", ".");
								String numeroI = ds.child(hora).child("Temp_Int").getValue().toString().split("ºC")[0]
										.trim().replaceAll(",", ".");

								String[] h = hora.split(":");

								DATOTE.addValue(Double.parseDouble(numeroE), "TempExt", h[0]);
								DATOTE.addValue(Double.parseDouble(numeroI), "TempInt", h[0]);
							}

							JFreeChart grafico = ChartFactory.createLineChart("Temperaturas", "Horas",
									"Temperatura [ºC]", DATOTE, PlotOrientation.VERTICAL, true, true, false);

							ChartPanel cPanel = new ChartPanel(grafico);

							GraphsFrame.graficas.add(cPanel);
							GraphsFrame.graficas.revalidate();
						}

						@Override
						public void onCancelled(DatabaseError de) {
							JOptionPane.showMessageDialog(null, "Ha habido un error recuperando los datos.", "Error", 1);
						}

					});// */
		}

	}

	/**
	 * Método para rellenar las fechas en el JComboBox
	 */
	public void fillDates() {
		this.DB.child("sensores").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot ds) {

				if (GraphsFrame.fechasData.getItemCount() > 0) {
					for (int i = 0; i <= GraphsFrame.fechasData.getItemCount(); i++) {
						GraphsFrame.fechasData.remove(i);
					}
				}
				for (DataSnapshot data : ds.getChildren()) {
					GraphsFrame.fechasData.addItem(data.getKey());
				}

				GraphsFrame.fechasData.removeItem("movimiento");
				GraphsFrame.fechasData.removeItem("temp_ext");
				GraphsFrame.fechasData.removeItem("temp_int");
				GraphsFrame.fechasData.removeItem("PIR");
				
				GraphsFrame.fechasData.removeItem("exterior");
				GraphsFrame.fechasData.removeItem("humedad");
				GraphsFrame.fechasData.removeItem("lux");
				GraphsFrame.fechasData.removeItem("sensTermica");
				GraphsFrame.fechasData.removeItem("tempDht11");
				
				GraphsFrame.fechasData.setSelectedItem(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

			}

			@Override
			public void onCancelled(DatabaseError de) {

			}

		});// */
	}

	/**
	 * Método para recuperar la referencia de la base de datos
	 *
	 * @return DataBaseReference de firebase
	 */
	public DatabaseReference getDB() {
		return DB;
	}

	/**
	 * Método para enviar las notificaciones a los clientes
	 *
	 * @param token ArrayList con todos los Tokens de los clientes
	 * @param body  Cuerpo del mensaje de la notificación
	 * @param title Título de la notificación
	 * @param tag   Etiqueta de la notificación
	 * @param icon  Icono en la barra de notificaciones
	 * @param channel Canal de notificaciones
	 * @param Priority prioridad de la notificación
	 */
	protected void sendNotification(List<String> token, String body, String title, String tag, String icon,
			String channel, AndroidConfig.Priority priority) {
		try {
			MulticastMessage message = MulticastMessage.builder()
					.setAndroidConfig(AndroidConfig.builder().setTtl(Constantes.TTL)
							.setPriority(priority)
							.setNotification(AndroidNotification.builder().setTitle(title).setBody(body).setTag(tag)
									.setSound(F.prop(Constantes.TONE)).setChannelId(channel)
									.setTitleLocalizationKey(F.prop(Constantes.TITULO)).setIcon(icon).build())
							.build())
					.addAllTokens(token).build();
			FirebaseMessaging.getInstance().sendMulticast(message);
		} catch (FirebaseMessagingException ex) {
			System.err.println(ex.getMessage());
		}
	}

}
