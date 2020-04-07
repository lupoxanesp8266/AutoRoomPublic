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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.model.BackGround;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 0.4
 */
public class LogInFrame extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private BackGround backGround;
	private JTextField nameField;
	private JButton exit, login, register;
	private JLabel nameLabel, passLabel;
	private JPasswordField passwordField;

	public LogInFrame(ActionListeners action) {
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
		exit = new JButton("Salir");
		exit.setActionCommand("exit");
		exit.addActionListener(action);
		exit.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		exit.setPreferredSize(new Dimension(100, 50));
		exit.setBackground(new Color(255, 255, 255));
		exit.setForeground(new Color(0, 204, 0));
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(exit, constraints);
		// Log In Button
		login = new JButton("Log In");
		login.setActionCommand("login");
		login.addActionListener(action);
		login.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		login.setPreferredSize(new Dimension(100, 50));
		login.setBackground(new Color(0,255,0));
		login.setForeground(new Color(255,255,255));
		login.setPreferredSize(new Dimension(200,40));
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(login, constraints);
		// Name Label
		nameLabel = new JLabel(" Usuario: ");
		nameLabel.setForeground(new Color(255, 255, 255));
		nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(nameLabel, constraints);
		// Name Field
		nameField = new JTextField(23);
		nameField.setForeground(new Color(0,0,0));
		nameField.setBackground(new Color(204,204,204));
		nameField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		nameField.setPreferredSize(new Dimension(200,30));
		nameField.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,14));
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(nameField, constraints);
		// Password Label
		passLabel = new JLabel(" Contraseña: ");
		passLabel.setForeground(new Color(255, 255, 255));
		passLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(passLabel, constraints);
		// Password Field
		passwordField = new JPasswordField(23);
		passwordField.setForeground(new Color(0,0,0));
		passwordField.setBackground(new Color(204,204,204));
		passwordField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		passwordField.setPreferredSize(new Dimension(200,30));
		passwordField.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,14));
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(passwordField, constraints);
		// Title label
		JLabel title = new JLabel(" AutoRoom ");
		title.setOpaque(true);
		title.setBackground(new Color(153,153,153));
		title.setForeground(new Color(255,255,255));
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setPreferredSize(new Dimension(500,100));
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		this.add(title,constraints);
		// Decorated label
		JLabel copyLabel = new JLabel();
		copyLabel.setIcon(new ImageIcon("/home/pi/autoRoom/img/logoAutoRoom.png"));
		copyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		this.add(copyLabel, constraints);
		register = new JButton();
		register.setIcon(new ImageIcon("/home/pi/autoRoom/img/registro.png"));
		register.setActionCommand("register");
		register.addActionListener(action);
		constraints.gridx = 3;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(register, constraints);

	}

	public JTextField getNameField() {
		return nameField;
	}

	public void setNameField(JTextField nameField) {
		this.nameField = nameField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

}
