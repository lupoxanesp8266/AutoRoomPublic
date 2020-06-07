package com.lupoxan.autoroom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.model.AutoRoom;
/**
 * @since 10/05/2020
 * @author lupo.xan
 * @version 0.3
 *
 */
public class LivingRoomFrame extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton backButton;
	private JButton salitaOn, salitaOff;

	public LivingRoomFrame(ActionListeners action) {
		super();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 5;
		constraints.weighty = 7;
		this.setBorder(AutoRoom.BACK_GROUND);
		this.setLayout(new GridBagLayout());
		this.setBackground(new Color(0,0,180));
		this.setBounds(50, 50, 200, 200);
		// Back to menu button
		backButton = new JButton("Menú");
		backButton.setActionCommand("backLights");
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
		// Other On Button
		salitaOn = new JButton("Encender");
		salitaOn.setActionCommand("salitaOn");
		salitaOn.addActionListener(action);
		salitaOn.setBackground(new Color(51, 51, 51));
		salitaOn.setForeground(new Color(187, 187, 187));
		salitaOn.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		salitaOn.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 3;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(salitaOn, constraints);
		// Other Off Button
		salitaOff = new JButton("Apagar");
		salitaOff.setActionCommand("salitaOff");
		salitaOff.addActionListener(action);
		salitaOff.setBackground(new Color(255, 0, 0));
		salitaOff.setForeground(new Color(187, 187, 187));
		salitaOff.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		salitaOff.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 3;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(salitaOff, constraints);

	}

	public JButton getSalitaOn() {
		return salitaOn;
	}

	public void setSalitaOn(JButton salitaOn) {
		this.salitaOn = salitaOn;
	}

	public JButton getSalitaOff() {
		return salitaOff;
	}

	public void setSalitaOff(JButton salitaOff) {
		this.salitaOff = salitaOff;
	}
	
	

}
