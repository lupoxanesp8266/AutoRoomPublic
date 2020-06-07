package com.lupoxan.autoroom.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.model.AutoRoom;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 0.2
 */
public class GraphsFrame extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton backButton;
	public static JPanel graficas;
	public static JComboBox<String> fechasData;
	
	public GraphsFrame(ActionListeners action) {
		super();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 5;
		constraints.weighty = 7;

		this.setBorder(AutoRoom.BACK_GROUND);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.GRAY);
		this.setBounds(50, 50, 200, 200);
		//Back to menu button
		backButton = new JButton("Menú");
		backButton.setActionCommand("back");
		backButton.addActionListener(action);
		backButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		backButton.setPreferredSize(new Dimension(100, 50));
		backButton.setBackground(new Color(255, 255, 255));
		backButton.setForeground(new Color(0, 204, 0));
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(backButton, constraints);
		// Title label
		JLabel title = new JLabel("Temperaturas");
		title.setOpaque(true);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setPreferredSize(new Dimension(200, 50));
		title.setBackground(new Color(255, 255, 255));
		title.setForeground(new Color(255, 0, 0));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(title,constraints);
		// Dates
		fechasData = new JComboBox<String>();
		fechasData.setActionCommand("dateChange");
		fechasData.addActionListener(action);
		fechasData.setPreferredSize(new Dimension(200,50));
		((JLabel)fechasData.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(fechasData,constraints);
		// Graphs Container
		graficas = new JPanel(new BorderLayout());
		graficas.setPreferredSize(new Dimension(850,500));
		graficas.setOpaque(true);
		graficas.setBackground(new Color(51,153,255));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 3;
		constraints.gridheight = 1;
		this.add(graficas,constraints);
		
		
	}
}
