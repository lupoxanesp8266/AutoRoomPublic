package com.lupoxan.autoroom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.model.BackGround;
import com.lupoxan.autoroom.model.Servo;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 0.1
 */
public class ToolsFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	private BackGround backGround;
	private JButton backButton, rebootButton, powerOffButton, firebaseTools, localTools, alarmButton;

	private JSlider servoSlider = new JSlider();
	private JLabel servoLabel = new JLabel();
	private JCheckBox servoCam = new JCheckBox();
	/**
	 * Objeto Servo para la utilización del mismo
	 */
	private final static Servo SERVO = new Servo(1);// Servo para mover la cámara RaspiCam

	public ToolsFrame(ActionListeners action) {
		super();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 5;
		constraints.weighty = 7;

		try {
			backGround = new BackGround(ImageIO.read(new File("/home/pi/autoRoom/img/blue.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setBorder(backGround);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.GRAY);
		this.setBounds(50, 50, 200, 200);
		// Back to menu button
		backButton = new JButton("Menú");
		backButton.setActionCommand("back");
		backButton.addActionListener(action);
		backButton.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		backButton.setPreferredSize(new Dimension(100, 50));
		backButton.setBackground(new Color(255, 255, 255));
		backButton.setForeground(new Color(0, 204, 0));
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(backButton, constraints);
		servoSlider = new JSlider();
		servoSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					SERVO.setPos(servoSlider.getValue());
				} catch (IOException ex) {
					System.err.println(ex.getMessage());
				} finally {
					servoLabel.setText(SERVO.getPos() + " º");
				}

			}
		});
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(servoSlider, constraints);
		
		JLabel title = new JLabel("Ajustes");
		title.setIcon(new ImageIcon("/home/pi/autoRoom/img/engine.png"));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		title.setForeground(new Color(255, 255, 255));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		this.add(title, constraints);
		// Rebbot JButton
		rebootButton = new JButton("Reiniciar");
		rebootButton.setActionCommand("reboot");
		rebootButton.addActionListener(action);
		rebootButton.setBackground(new Color(255, 165, 0));
		rebootButton.setForeground(new Color(0, 0, 0));
		rebootButton.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		rebootButton.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(rebootButton, constraints);
		// PowerOff JButton
		powerOffButton = new JButton("Apagar");
		powerOffButton.setActionCommand("powerOff");
		powerOffButton.addActionListener(action);
		powerOffButton.setBackground(new Color(255, 0, 0));
		powerOffButton.setForeground(new Color(0, 0, 0));
		powerOffButton.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		powerOffButton.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(powerOffButton, constraints);
		// Firebase tools button
		firebaseTools = new JButton("Firebase");
		firebaseTools.setActionCommand("firebaseTools");
		firebaseTools.addActionListener(action);
		firebaseTools.setBackground(new Color(102, 255, 102));
		firebaseTools.setForeground(new Color(0, 0, 0));
		firebaseTools.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		firebaseTools.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(firebaseTools, constraints);
		// Firebase tools button
		localTools = new JButton("Local");
		localTools.setActionCommand("localTools");
		localTools.addActionListener(action);
		localTools.setBackground(new Color(102, 255, 102));
		localTools.setForeground(new Color(0, 0, 0));
		localTools.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		localTools.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(localTools, constraints);
		// Alarm JButton
		alarmButton = new JButton("Alarma intruso");
		alarmButton.setActionCommand("intruderAlarm");
		alarmButton.addActionListener(action);
		alarmButton.setBackground(new Color(255, 0, 0));
		alarmButton.setForeground(new Color(0, 0, 0));
		alarmButton.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		alarmButton.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(alarmButton, constraints);
	}

	public JSlider getServoSlider() {
		return servoSlider;
	}

	public void setServoSlider(JSlider servoSlider) {
		this.servoSlider = servoSlider;
	}

	public JLabel getServoLabel() {
		return servoLabel;
	}

	public void setServoLabel(JLabel servoLabel) {
		this.servoLabel = servoLabel;
	}

	public JCheckBox getServoCam() {
		return servoCam;
	}

	public void setServoCam(JCheckBox servoCam) {
		this.servoCam = servoCam;
	}

	public JButton getRebootButton() {
		return rebootButton;
	}

	public void setRebootButton(JButton rebootButton) {
		this.rebootButton = rebootButton;
	}

	public JButton getPowerOffButton() {
		return powerOffButton;
	}

	public void setPowerOffButton(JButton powerOffButton) {
		this.powerOffButton = powerOffButton;
	}

	public JButton getFirebaseTools() {
		return firebaseTools;
	}

	public void setFirebaseTools(JButton firebaseTools) {
		this.firebaseTools = firebaseTools;
	}

	public JButton getLocalTools() {
		return localTools;
	}

	public void setLocalTools(JButton localTools) {
		this.localTools = localTools;
	}

	public JButton getAlarmButton() {
		return alarmButton;
	}

	public void setAlarmButton(JButton alarmButton) {
		this.alarmButton = alarmButton;
	}
}
