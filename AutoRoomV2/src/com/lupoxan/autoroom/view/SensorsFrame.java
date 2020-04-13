package com.lupoxan.autoroom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.model.BackGround;

public class SensorsFrame extends JPanel {
	private static final long serialVersionUID = 1L;
	private BackGround backGround;
	private JButton backButton;
	private JLabel luxValue, movementValue, tempIntValue, tempExtValue, tempDHTValue, humDHTValue, sensValue,
			statusDHTValue;

	public SensorsFrame(ActionListeners action) {
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
		// Inside temperature label
		JLabel tempIntLabel = new JLabel(" Temperatura interior ");
		tempIntLabel.setForeground(new Color(255, 255, 255));
		tempIntLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempIntLabel, constraints);
		// Outside temperature label
		JLabel tempExtLabel = new JLabel(" Temperatura exterior ");
		tempExtLabel.setForeground(new Color(255, 255, 255));
		tempExtLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempExtLabel, constraints);
		// Movement label
		JLabel movementLabel = new JLabel(" Movimiento ");
		movementLabel.setForeground(new Color(255, 255, 255));
		movementLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(movementLabel, constraints);
		// Lux Label
		JLabel luxLabel = new JLabel(" Lux ");
		luxLabel.setForeground(new Color(255, 255, 255));
		luxLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(luxLabel, constraints);
		// Temp DHT11 label
		JLabel tempDHTLabel = new JLabel(" Temperatura DHT11 ");
		tempDHTLabel.setForeground(new Color(255, 255, 255));
		tempDHTLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempDHTLabel, constraints);
		// Hum DHT11 label
		JLabel humDHTLabel = new JLabel(" Humedad DHT11 ");
		humDHTLabel.setForeground(new Color(255, 255, 255));
		humDHTLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		constraints.gridx = 4;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(humDHTLabel, constraints);
		// Heat DHT11 label
		JLabel sensLabel = new JLabel(" Sens. Térmica ");
		sensLabel.setForeground(new Color(255, 255, 255));
		sensLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		constraints.gridx = 4;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(sensLabel, constraints);
		// Status DHT11 label
		JLabel statusDHT = new JLabel(" Status DHT11 ");
		statusDHT.setForeground(new Color(255, 255, 255));
		statusDHT.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(statusDHT, constraints);
		// Inside temperature value label
		tempIntValue = new JLabel("00,00 ºC");
		tempIntValue.setOpaque(true);
		tempIntValue.setBackground(new Color(51, 255, 51));
		tempIntValue.setForeground(new Color(255, 0, 0));
		tempIntValue.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		tempIntValue.setHorizontalAlignment(SwingConstants.CENTER);
		tempIntValue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		tempIntValue.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempIntValue, constraints);
		// Outside temperature value label
		tempExtValue = new JLabel("00,00 ºC");
		tempExtValue.setOpaque(true);
		tempExtValue.setBackground(new Color(51, 255, 51));
		tempExtValue.setForeground(new Color(255, 0, 0));
		tempExtValue.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		tempExtValue.setHorizontalAlignment(SwingConstants.CENTER);
		tempExtValue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		tempExtValue.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempExtValue, constraints);
		// Inside temperature value label
		movementValue = new JLabel("LOW");
		movementValue.setOpaque(true);
		movementValue.setBackground(new Color(51, 255, 51));
		movementValue.setForeground(new Color(255, 0, 0));
		movementValue.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		movementValue.setHorizontalAlignment(SwingConstants.CENTER);
		movementValue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		movementValue.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(movementValue, constraints);
		// Inside lux value label
		luxValue = new JLabel("0 lx");
		luxValue.setOpaque(true);
		luxValue.setBackground(new Color(51, 255, 51));
		luxValue.setForeground(new Color(255, 0, 0));
		luxValue.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		luxValue.setHorizontalAlignment(SwingConstants.CENTER);
		luxValue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		luxValue.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(luxValue, constraints);
		// Temp dht11 value
		tempDHTValue = new JLabel("00.00 ºC");
		tempDHTValue.setOpaque(true);
		tempDHTValue.setBackground(new Color(51, 255, 51));
		tempDHTValue.setForeground(new Color(255, 0, 0));
		tempDHTValue.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		tempDHTValue.setHorizontalAlignment(SwingConstants.CENTER);
		tempDHTValue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		tempDHTValue.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 5;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempDHTValue, constraints);
		// Temp dht11 value
		humDHTValue = new JLabel("00.00 %");
		humDHTValue.setOpaque(true);
		humDHTValue.setBackground(new Color(51, 255, 51));
		humDHTValue.setForeground(new Color(255, 0, 0));
		humDHTValue.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		humDHTValue.setHorizontalAlignment(SwingConstants.CENTER);
		humDHTValue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		humDHTValue.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 5;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(humDHTValue, constraints);
		// Temp dht11 value
		sensValue = new JLabel("00.00 ºC");
		sensValue.setOpaque(true);
		sensValue.setBackground(new Color(51, 255, 51));
		sensValue.setForeground(new Color(255, 0, 0));
		sensValue.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		sensValue.setHorizontalAlignment(SwingConstants.CENTER);
		sensValue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		sensValue.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 5;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(sensValue, constraints);
		// Temp dht11 value
		statusDHTValue = new JLabel("TIMEOUT");
		statusDHTValue.setOpaque(true);
		statusDHTValue.setBackground(new Color(51, 255, 51));
		statusDHTValue.setForeground(new Color(255, 0, 0));
		statusDHTValue.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		statusDHTValue.setHorizontalAlignment(SwingConstants.CENTER);
		statusDHTValue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		statusDHTValue.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 5;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(statusDHTValue, constraints);

	}

	public JLabel getLuxValue() {
		return luxValue;
	}

	public void setLuxValue(JLabel luxValue) {
		this.luxValue = luxValue;
	}

	public JLabel getMovementValue() {
		return movementValue;
	}

	public void setMovementValue(JLabel movementValue) {
		this.movementValue = movementValue;
	}

	public JLabel getTempIntValue() {
		return tempIntValue;
	}

	public void setTempIntValue(JLabel tempIntValue) {
		this.tempIntValue = tempIntValue;
	}

	public JLabel getTempExtValue() {
		return tempExtValue;
	}

	public void setTempExtValue(JLabel tempExtValue) {
		this.tempExtValue = tempExtValue;
	}

	public JLabel getTempDHTValue() {
		return tempDHTValue;
	}

	public void setTempDHTValue(JLabel tempDHTValue) {
		this.tempDHTValue = tempDHTValue;
	}

	public JLabel getHumDHTValue() {
		return humDHTValue;
	}

	public void setHumDHTValue(JLabel humDHTValue) {
		this.humDHTValue = humDHTValue;
	}

	public JLabel getSensValue() {
		return sensValue;
	}

	public void setSensValue(JLabel sensValue) {
		this.sensValue = sensValue;
	}

	public JLabel getStatusDHTValue() {
		return statusDHTValue;
	}

	public void setStatusDHTValue(JLabel statusDHTValue) {
		this.statusDHTValue = statusDHTValue;
	}

}
