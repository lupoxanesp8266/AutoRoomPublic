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

public class EntryFrame extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton backButton;
	private JButton otherOn, otherOff;

	public EntryFrame(ActionListeners action) {
		super();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 5;
		constraints.weighty = 7;

		this.setBorder(AutoRoom.BACK_GROUND);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.GRAY);
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
		otherOn = new JButton("Encender");
		otherOn.setActionCommand("otherOn");
		otherOn.addActionListener(action);
		otherOn.setBackground(new Color(51, 51, 51));
		otherOn.setForeground(new Color(187, 187, 187));
		otherOn.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		otherOn.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 3;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(otherOn, constraints);
		// Other Off Button
		otherOff = new JButton("Apagar");
		otherOff.setActionCommand("otherOff");
		otherOff.addActionListener(action);
		otherOff.setBackground(new Color(255, 0, 0));
		otherOff.setForeground(new Color(187, 187, 187));
		otherOff.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		otherOff.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 3;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(otherOff, constraints);
	}

	public JButton getOtherOn() {
		return otherOn;
	}

	public void setOtherOn(JButton otherOn) {
		this.otherOn = otherOn;
	}

	public JButton getOtherOff() {
		return otherOff;
	}

	public void setOtherOff(JButton otherOff) {
		this.otherOff = otherOff;
	}
}
