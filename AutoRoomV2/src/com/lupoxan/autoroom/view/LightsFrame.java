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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.model.BackGround;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 0.2
 */
public class LightsFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	private BackGround backGround;
	private JButton backButton;
	private JCheckBox autoCama, autoMesa, autoUp;
	private JLabel autoCamaLabel, autoMesaLabel, autoUpLabel;
	private JButton mesaOn, camaOn, generalOn, mesaOff, camaOff, generalOff;

	public LightsFrame(ActionListeners action) {
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
		// Title Label
		JLabel title = new JLabel(" ILUMINACIÓN ");
		title.setOpaque(true);
		title.setBackground(new Color(60, 63, 65));
		title.setForeground(new Color(255, 0, 0));
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setPreferredSize(new Dimension(500, 50));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		this.add(title, constraints);
		// Bed Label
		JLabel bedLabel = new JLabel(" Cama ");
		bedLabel.setOpaque(true);
		bedLabel.setBackground(new Color(204, 204, 204));
		bedLabel.setForeground(new Color(255, 255, 0));
		bedLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		bedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bedLabel.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(bedLabel, constraints);
		// Bed Label
		JLabel tableLabel = new JLabel(" Mesa ");
		tableLabel.setOpaque(true);
		tableLabel.setBackground(new Color(204, 204, 204));
		tableLabel.setForeground(new Color(255, 255, 0));
		tableLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		tableLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tableLabel.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tableLabel, constraints);
		// Bed Label
		JLabel upLabel = new JLabel(" General ");
		upLabel.setOpaque(true);
		upLabel.setBackground(new Color(204, 204, 204));
		upLabel.setForeground(new Color(255, 255, 0));
		upLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		upLabel.setHorizontalAlignment(SwingConstants.CENTER);
		upLabel.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(upLabel, constraints);
		// Other Label
		JLabel otherLabel = new JLabel(" Exterior ");
		otherLabel.setOpaque(true);
		otherLabel.setBackground(new Color(204, 204, 204));
		otherLabel.setForeground(new Color(255, 255, 0));
		otherLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		otherLabel.setHorizontalAlignment(SwingConstants.CENTER);
		otherLabel.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 3;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(otherLabel, constraints);
		// Mesa JCheckBox
		autoMesa = new JCheckBox();
		autoMesa.setActionCommand("autoMesa");
		autoMesa.addActionListener(action);
		autoMesa.setText("Auto");
		autoMesa.setForeground(new Color(204, 0, 0));
		autoMesa.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(autoMesa, constraints);
		// Cama JCheckBox
		autoCama = new JCheckBox();
		autoCama.setActionCommand("autoCama");
		autoCama.addActionListener(action);
		autoCama.setText("Auto");
		autoCama.setForeground(new Color(204, 0, 0));
		autoCama.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(autoCama, constraints);
		// General JCheckBox
		autoUp = new JCheckBox();
		autoUp.setActionCommand("autoUp");
		autoUp.addActionListener(action);
		autoUp.setText("Auto");
		autoUp.setForeground(new Color(204, 0, 0));
		autoUp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(autoUp, constraints);
		// Auto Bed Label
		autoMesaLabel = new JLabel("  ");
		autoMesaLabel.setOpaque(true);
		autoMesaLabel.setBackground(new Color(204, 204, 204));
		autoMesaLabel.setForeground(new Color(255, 255, 0));
		autoMesaLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		autoMesaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		autoMesaLabel.setPreferredSize(new Dimension(150, 50));
		autoMesaLabel.setVisible(false);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(autoMesaLabel, constraints);
		// Auto Table Label
		autoCamaLabel = new JLabel("  ");
		autoCamaLabel.setOpaque(true);
		autoCamaLabel.setBackground(new Color(204, 204, 204));
		autoCamaLabel.setForeground(new Color(255, 255, 0));
		autoCamaLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		autoCamaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		autoCamaLabel.setPreferredSize(new Dimension(150, 50));
		autoCamaLabel.setVisible(false);
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(autoCamaLabel, constraints);
		// Auto Up Label
		autoUpLabel = new JLabel("  ");
		autoUpLabel.setOpaque(true);
		autoUpLabel.setBackground(new Color(204, 204, 204));
		autoUpLabel.setForeground(new Color(255, 255, 0));
		autoUpLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		autoUpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		autoUpLabel.setPreferredSize(new Dimension(150, 50));
		autoUpLabel.setVisible(false);
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(autoUpLabel, constraints);
		// Mesa On Button
		mesaOn = new JButton("Encender");
		mesaOn.setActionCommand("mesaOn");
		mesaOn.addActionListener(action);
		mesaOn.setBackground(new Color(51, 51, 51));
		mesaOn.setForeground(new Color(187, 187, 187));
		mesaOn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		mesaOn.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(mesaOn, constraints);
		// Mesa Off Button
		mesaOff = new JButton("Apagar");
		mesaOff.setActionCommand("mesaOff");
		mesaOff.addActionListener(action);
		mesaOff.setBackground(new Color(255, 0, 0));
		mesaOff.setForeground(new Color(187, 187, 187));
		mesaOff.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		mesaOff.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(mesaOff, constraints);
		// Cama On Button
		camaOn = new JButton("Encender");
		camaOn.setActionCommand("camaOn");
		camaOn.addActionListener(action);
		camaOn.setBackground(new Color(51, 51, 51));
		camaOn.setForeground(new Color(187, 187, 187));
		camaOn.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		camaOn.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(camaOn, constraints);
		// Cama Off Button
		camaOff = new JButton("Apagar");
		camaOff.setActionCommand("camaOff");
		camaOff.addActionListener(action);
		camaOff.setBackground(new Color(255, 0, 0));
		camaOff.setForeground(new Color(187, 187, 187));
		camaOff.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		camaOff.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(camaOff, constraints);
		// General On Button
		generalOn = new JButton("Encender");
		generalOn.setActionCommand("generalOn");
		generalOn.addActionListener(action);
		generalOn.setBackground(new Color(51, 51, 51));
		generalOn.setForeground(new Color(187, 187, 187));
		generalOn.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		generalOn.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(generalOn, constraints);
		// General Off Button
		generalOff = new JButton("Apagar");
		generalOff.setActionCommand("generalOff");
		generalOff.addActionListener(action);
		generalOff.setBackground(new Color(255, 0, 0));
		generalOff.setForeground(new Color(187, 187, 187));
		generalOff.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		generalOff.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 2;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(generalOff, constraints);
		
	}

	public JCheckBox getAutoCama() {
		return autoCama;
	}

	public void setAutoCama(JCheckBox autoCama) {
		this.autoCama = autoCama;
	}

	public JCheckBox getAutoMesa() {
		return autoMesa;
	}

	public void setAutoMesa(JCheckBox autoMesa) {
		this.autoMesa = autoMesa;
	}

	public JCheckBox getAutoUp() {
		return autoUp;
	}

	public void setAutoUp(JCheckBox autoUp) {
		this.autoUp = autoUp;
	}

	public JLabel getAutoCamaLabel() {
		return autoCamaLabel;
	}

	public void setAutoCamaLabel(JLabel autoCamaLabel) {
		this.autoCamaLabel = autoCamaLabel;
	}

	public JLabel getAutoMesaLabel() {
		return autoMesaLabel;
	}

	public void setAutoMesaLabel(JLabel autoMesaLabel) {
		this.autoMesaLabel = autoMesaLabel;
	}

	public JLabel getAutoUpLabel() {
		return autoUpLabel;
	}

	public void setAutoUpLabel(JLabel autoUpLabel) {
		this.autoUpLabel = autoUpLabel;
	}

	public JButton getMesaOn() {
		return mesaOn;
	}

	public void setMesaOn(JButton mesaOn) {
		this.mesaOn = mesaOn;
	}

	public JButton getCamaOn() {
		return camaOn;
	}

	public void setCamaOn(JButton camaOn) {
		this.camaOn = camaOn;
	}

	public JButton getGeneralOn() {
		return generalOn;
	}

	public void setGeneralOn(JButton generalOn) {
		this.generalOn = generalOn;
	}

	public JButton getMesaOff() {
		return mesaOff;
	}

	public void setMesaOff(JButton mesaOff) {
		this.mesaOff = mesaOff;
	}

	public JButton getCamaOff() {
		return camaOff;
	}

	public void setCamaOff(JButton camaOff) {
		this.camaOff = camaOff;
	}

	public JButton getGeneralOff() {
		return generalOff;
	}

	public void setGeneralOff(JButton generalOff) {
		this.generalOff = generalOff;
	}

	
}
