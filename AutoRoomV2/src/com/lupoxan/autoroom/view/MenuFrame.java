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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.model.BackGround;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 0.3
 */
public class MenuFrame extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private BackGround backGround;
	private JButton exit, lights, comfort, leds, graphs, tools;
	private JLabel dateLabel, hourLabel, movementValue, tempIntValue, tempExtValue;
	private JButton sensors;
	
	public MenuFrame(ActionListeners actions) {
		super();
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 5;
		constraints.weighty = 7;

		try {
			backGround = new BackGround(ImageIO.read(new File("/home/pi/autoRoom/img/blue.jpg")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setBorder(backGround);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.GRAY);
		this.setBounds(50, 50, 200, 200);
		// Exit button
		exit = new JButton("Log Out");
		exit.setActionCommand("logOut");
		exit.addActionListener(actions);
		exit.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		exit.setPreferredSize(new Dimension(100, 50));
		exit.setBackground(new Color(255, 255, 255));
		exit.setForeground(new Color(0, 204, 0));
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(exit, constraints);
		// Lights button
		lights = new JButton("Iluminación");
		lights.setActionCommand("lights");
		lights.addActionListener(actions);
		lights.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		lights.setPreferredSize(new Dimension(200, 75));
		lights.setBackground(new Color(102, 255, 102));
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(lights, constraints);
		// Comfort button
		comfort = new JButton("Comfort");
		comfort.setActionCommand("comfort");
		comfort.addActionListener(actions);
		comfort.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		comfort.setPreferredSize(new Dimension(200, 75));
		comfort.setBackground(new Color(102, 255, 102));
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(comfort, constraints);
		// Leds button
		leds = new JButton("Leds");
		leds.setActionCommand("leds");
		leds.addActionListener(actions);
		leds.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		leds.setPreferredSize(new Dimension(200, 75));
		leds.setBackground(new Color(102, 255, 102));
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(leds, constraints);
		// Graphs button
		graphs = new JButton("Gráficas");
		graphs.setActionCommand("graphs");
		graphs.addActionListener(actions);
		graphs.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		graphs.setPreferredSize(new Dimension(200, 75));
		graphs.setBackground(new Color(102, 255, 102));
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(graphs, constraints);
		// Tools button
		tools = new JButton("Ajustes");
		tools.setActionCommand("tools");
		tools.addActionListener(actions);
		tools.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		tools.setPreferredSize(new Dimension(200, 75));
		tools.setBackground(new Color(102, 255, 102));
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tools, constraints);
		// Date label
		dateLabel = new JLabel("00-00-0000");
		dateLabel.setOpaque(true);
		dateLabel.setBackground(new Color(51, 153, 255));
		dateLabel.setForeground(new Color(255, 255, 255));
		dateLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		dateLabel.setPreferredSize(new Dimension(150,50));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(dateLabel, constraints);
		// Hour label
		hourLabel = new JLabel("--:--:--");
		hourLabel.setOpaque(true);
		hourLabel.setBackground(new Color(51, 153, 255));
		hourLabel.setForeground(new Color(255, 255, 255));
		hourLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		hourLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hourLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		hourLabel.setPreferredSize(new Dimension(150,50));
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(hourLabel, constraints);
		// Welcome label
		JLabel welcome = new JLabel();
		welcome.setIcon(new ImageIcon("/home/pi/autoRoom/img/bienvenido.png"));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 3;
		this.add(welcome, constraints);
		// Logo label
		JLabel logoLabel = new JLabel();
		logoLabel.setIcon(new ImageIcon("/home/pi/autoRoom/img/logoAutoRoom.png"));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.gridheight = 3;
		this.add(logoLabel, constraints);
		// Inside temperature label
		JLabel tempIntLabel = new JLabel(" Interior ");
		tempIntLabel.setForeground(new Color(255, 255, 255));
		tempIntLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempIntLabel, constraints);
		// Outside temperature label
		JLabel tempExtLabel = new JLabel(" Exterior ");
		tempExtLabel.setForeground(new Color(255, 255, 255));
		tempExtLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempExtLabel, constraints);
		// Movement label
		JLabel movementLabel = new JLabel(" Movimiento ");
		movementLabel.setForeground(new Color(255, 255, 255));
		movementLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		constraints.gridx = 4;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(movementLabel, constraints);
		
		// Inside temperature value label
		tempIntValue = new JLabel("00,00 ºC");
		tempIntValue.setOpaque(true);
		tempIntValue.setBackground(new Color(51, 255, 51));
		tempIntValue.setForeground(new Color(255, 0, 0));
		tempIntValue.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		tempIntValue.setHorizontalAlignment(SwingConstants.CENTER);
		tempIntValue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		tempIntValue.setPreferredSize(new Dimension(100,50));
		constraints.gridx = 5;
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
		tempExtValue.setPreferredSize(new Dimension(100,50));
		constraints.gridx = 5;
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
		movementValue.setPreferredSize(new Dimension(100,50));
		constraints.gridx = 5;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(movementValue, constraints);
		// CopyRight Label
		sensors = new JButton("Sensores");
		sensors.setOpaque(true);
		sensors.addActionListener(actions);
		sensors.setActionCommand("sensores");
		sensors.setBackground(new Color(51, 255, 51));
		sensors.setForeground(new Color(255, 0, 0));
		sensors.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		sensors.setHorizontalAlignment(SwingConstants.CENTER);
		sensors.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		sensors.setPreferredSize(new Dimension(200,50));
		constraints.gridx = 4;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		this.add(sensors, constraints);
	}

	public JButton getExit() {
		return exit;
	}

	public void setExit(JButton exit) {
		this.exit = exit;
	}

	public JButton getLights() {
		return lights;
	}

	public void setLights(JButton lights) {
		this.lights = lights;
	}

	public JButton getComfort() {
		return comfort;
	}

	public void setComfort(JButton comfort) {
		this.comfort = comfort;
	}

	public JButton getLeds() {
		return leds;
	}

	public void setLeds(JButton leds) {
		this.leds = leds;
	}

	public JButton getGraphs() {
		return graphs;
	}

	public void setGraphs(JButton graphs) {
		this.graphs = graphs;
	}

	public JButton getTools() {
		return tools;
	}

	public void setTools(JButton tools) {
		this.tools = tools;
	}

	public JLabel getDateLabel() {
		return dateLabel;
	}

	public void setDateLabel(JLabel dateLabel) {
		this.dateLabel = dateLabel;
	}

	public JLabel getHourLabel() {
		return hourLabel;
	}

	public void setHourLabel(JLabel hourLabel) {
		this.hourLabel = hourLabel;
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
	

}
