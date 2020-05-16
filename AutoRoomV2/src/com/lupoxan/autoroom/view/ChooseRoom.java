package com.lupoxan.autoroom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.model.BackGround;

/**
 * @since 10/05/2020
 * @author lupo.xan
 * @version 0.1
 */
public class ChooseRoom extends JPanel {
	private static final long serialVersionUID = 1L;
	private BackGround backGround;
	private JButton backButton;
	private JButton room1, room2, salita, entrada;

	public ChooseRoom(ActionListeners action) {
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
		// Back to menu button
		backButton = new JButton("Menú");
		backButton.setActionCommand("back");
		backButton.addActionListener(action);
		backButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		backButton.setPreferredSize(new Dimension(100, 50));
		backButton.setBackground(new Color(255, 255, 255));
		backButton.setForeground(new Color(0, 204, 0));
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		this.add(backButton, constraints);
		// Habitación 1
		room1 = new JButton("Room 1");
		room1.setActionCommand("room1");
		room1.addActionListener(action);
		room1.setBackground(new Color(51, 51, 51));
		room1.setForeground(new Color(187, 187, 187));
		room1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		room1.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(room1, constraints);
		// Habitación 2
		room2 = new JButton("Room 2");
		room2.setActionCommand("room2");
		room2.addActionListener(action);
		room2.setBackground(new Color(51, 51, 51));
		room2.setForeground(new Color(187, 187, 187));
		room2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		room2.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(room2, constraints);
		// Entrada
		entrada = new JButton("Entrada");
		entrada.setActionCommand("entry");
		entrada.addActionListener(action);
		entrada.setBackground(new Color(51, 51, 51));
		entrada.setForeground(new Color(187, 187, 187));
		entrada.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		entrada.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(entrada, constraints);
		// Salita
		salita = new JButton("Salita");
		salita.setActionCommand("livingRoom");
		salita.addActionListener(action);
		salita.setBackground(new Color(51, 51, 51));
		salita.setForeground(new Color(187, 187, 187));
		salita.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		salita.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 3;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(salita, constraints);
	}

	public JButton getRoom1() {
		return room1;
	}

	public void setRoom1(JButton room1) {
		this.room1 = room1;
	}

	public JButton getSalita() {
		return salita;
	}

	public void setSalita(JButton salita) {
		this.salita = salita;
	}

	public JButton getRoom2() {
		return room2;
	}

	public void setRoom2(JButton room2) {
		this.room2 = room2;
	}

	public JButton getEntrada() {
		return entrada;
	}

	public void setEntrada(JButton entrada) {
		this.entrada = entrada;
	}

}
