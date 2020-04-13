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
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.controller.ChangeStateListeners;
import com.lupoxan.autoroom.model.BackGround;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 0.2
 */
public class ComfortFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	private BackGround backGround;
	private JButton backButton;
	private JLabel autoHeatLabel, autoCoolLabel, consignaLabel, tempIntValue, tempExtValue;
	private JButton heatOn, coolOn, heatOff, coolOff, fanOn, fanOff;
	private JSlider consignaSlider;

	public ComfortFrame(ActionListeners action) {
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
		backButton.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		backButton.setPreferredSize(new Dimension(100, 50));
		backButton.setBackground(new Color(255, 255, 255));
		backButton.setForeground(new Color(0, 204, 0));
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(backButton, constraints);
		// Title label
		JLabel title = new JLabel();
		title.setIcon(new ImageIcon("/home/pi/autoRoom/img/comfort.png"));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		this.add(title, constraints);
		// Title Heat Label
		JLabel heatLabel = new JLabel("Calor");
		heatLabel.setIcon(new ImageIcon("/home/pi/autoRoom/img/Sun.png"));
		heatLabel.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		heatLabel.setForeground(new Color(255, 255, 255));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(heatLabel, constraints);
		// Title Cool Label
		JLabel coolLabel = new JLabel("Frío");
		coolLabel.setIcon(new ImageIcon("/home/pi/autoRoom/img/frío.png"));
		coolLabel.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		coolLabel.setForeground(new Color(255, 255, 255));
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(coolLabel, constraints);
		// Label Auto Heat
		autoHeatLabel = new JLabel("  ");
		autoHeatLabel.setOpaque(true);
		autoHeatLabel.setBackground(new Color(204, 204, 204));
		autoHeatLabel.setForeground(new Color(255, 255, 0));
		autoHeatLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		autoHeatLabel.setHorizontalAlignment(SwingConstants.CENTER);
		autoHeatLabel.setPreferredSize(new Dimension(150, 50));
		autoHeatLabel.setVisible(false);
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(autoHeatLabel, constraints);
		// Label Auto Cool
		autoCoolLabel = new JLabel("  ");
		autoCoolLabel.setOpaque(true);
		autoCoolLabel.setBackground(new Color(204, 204, 204));
		autoCoolLabel.setForeground(new Color(255, 255, 0));
		autoCoolLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		autoCoolLabel.setHorizontalAlignment(SwingConstants.CENTER);
		autoCoolLabel.setPreferredSize(new Dimension(150, 50));
		autoCoolLabel.setVisible(false);
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(autoCoolLabel, constraints);
		// Heat On Button
		heatOn = new JButton("Encender");
		heatOn.setActionCommand("heatOn");
		heatOn.addActionListener(action);
		heatOn.setBackground(new Color(0, 255, 0));
		heatOn.setForeground(new Color(187, 187, 187));
		heatOn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		heatOn.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(heatOn, constraints);
		// Heat Off Button
		heatOff = new JButton("Apagar");
		heatOff.setActionCommand("heatOff");
		heatOff.addActionListener(action);
		heatOff.setBackground(new Color(255, 0, 0));
		heatOff.setForeground(new Color(187, 187, 187));
		heatOff.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		heatOff.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(heatOff, constraints);
		// Cool On Button
		coolOn = new JButton("Encender");
		coolOn.setActionCommand("coolOn");
		coolOn.addActionListener(action);
		coolOn.setBackground(new Color(0, 255, 0));
		coolOn.setForeground(new Color(187, 187, 187));
		coolOn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		coolOn.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(coolOn, constraints);
		// Cool Off Button
		coolOff = new JButton("Apagar");
		coolOff.setActionCommand("coolOff");
		coolOff.addActionListener(action);
		coolOff.setBackground(new Color(255, 0, 0));
		coolOff.setForeground(new Color(187, 187, 187));
		coolOff.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		coolOff.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(coolOff, constraints);
		// Fan Title label
		JLabel fanTitle = new JLabel(" Ventilador ");
		fanTitle.setOpaque(true);
		fanTitle.setBackground(new Color(51, 153, 255));
		fanTitle.setForeground(new Color(255, 255, 255));
		fanTitle.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		fanTitle.setHorizontalAlignment(SwingConstants.CENTER);
		fanTitle.setPreferredSize(new Dimension(200, 50));
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(fanTitle, constraints);
		// Fan On Button
		fanOn = new JButton("Encender");
		fanOn.setActionCommand("fanOn");
		fanOn.addActionListener(action);
		fanOn.setBackground(new Color(0, 255, 0));
		fanOn.setForeground(new Color(187, 187, 187));
		fanOn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		fanOn.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(fanOn, constraints);
		// Cool Off Button
		fanOff = new JButton("Apagar");
		fanOff.setActionCommand("fanOff");
		fanOff.addActionListener(action);
		fanOff.setBackground(new Color(255, 0, 0));
		fanOff.setForeground(new Color(187, 187, 187));
		fanOff.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		fanOff.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(fanOff, constraints);
		// Consigna title label
		JLabel valuesTitle = new JLabel(" Valores ");
		valuesTitle.setOpaque(true);
		valuesTitle.setBackground(new Color(51, 153, 255));
		valuesTitle.setForeground(new Color(255, 255, 255));
		valuesTitle.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		valuesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		valuesTitle.setPreferredSize(new Dimension(200, 50));
		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		this.add(valuesTitle, constraints);
		// Consigna title label
		JLabel consignaTitle = new JLabel(" Consigna ");
		consignaTitle.setOpaque(true);
		consignaTitle.setBackground(new Color(51, 153, 255));
		consignaTitle.setForeground(new Color(255, 255, 255));
		consignaTitle.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		consignaTitle.setHorizontalAlignment(SwingConstants.CENTER);
		consignaTitle.setPreferredSize(new Dimension(200, 50));
		constraints.gridx = 3;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(consignaTitle, constraints);
		// JSlider Consigna
		consignaSlider = new JSlider();
		consignaSlider.setOrientation(1);
		consignaSlider.setOpaque(true);
		consignaSlider.setMaximum(35);
		consignaSlider.setMinimum(10);
		consignaSlider.setMajorTickSpacing(5);
		consignaSlider.setValue(10);
		consignaSlider.setPreferredSize(new Dimension(70, 300));
		consignaSlider.setBackground(new Color(0, 0, 255));
		consignaSlider.setPaintLabels(true);
		consignaSlider.setPaintTicks(true);
		consignaSlider.setPaintTrack(true);
		consignaSlider.addChangeListener(new ChangeStateListeners("consignaSlider"));
		constraints.gridx = 3;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 3;
		this.add(consignaSlider, constraints);
		// Consigna Label
		consignaLabel = new JLabel(" 10ºC ");
		consignaLabel.setOpaque(true);
		consignaLabel.setBackground(new Color(51, 255, 51));
		consignaLabel.setForeground(new Color(255, 0, 0));
		consignaLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		consignaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		consignaLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		consignaLabel.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 5;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(consignaLabel, constraints);
		// Inside temperature label
		JLabel tempIntLabel = new JLabel(" Interior ");
		tempIntLabel.setForeground(new Color(255, 255, 255));
		tempIntLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempIntLabel, constraints);
		// Outside temperature label
		JLabel tempExtLabel = new JLabel(" Exterior ");
		tempExtLabel.setForeground(new Color(255, 255, 255));
		tempExtLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		constraints.gridx = 4;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempExtLabel, constraints);
		// Outside temperature label
		JLabel consigLabel = new JLabel(" Consigna ");
		consigLabel.setForeground(new Color(255, 255, 255));
		consigLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		constraints.gridx = 4;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(consigLabel, constraints);
		// Inside temperature value label
		tempIntValue = new JLabel(" 25,30 ºC ");
		tempIntValue.setOpaque(true);
		tempIntValue.setBackground(new Color(51, 255, 51));
		tempIntValue.setForeground(new Color(255, 0, 0));
		tempIntValue.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		tempIntValue.setHorizontalAlignment(SwingConstants.CENTER);
		tempIntValue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		tempIntValue.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 5;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempIntValue, constraints);
		// Outside temperature value label
		tempExtValue = new JLabel(" 25,30 ºC ");
		tempExtValue.setOpaque(true);
		tempExtValue.setBackground(new Color(51, 255, 51));
		tempExtValue.setForeground(new Color(255, 0, 0));
		tempExtValue.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		tempExtValue.setHorizontalAlignment(SwingConstants.CENTER);
		tempExtValue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		tempExtValue.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 5;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(tempExtValue, constraints);
	}

	public JLabel getAutoHeatLabel() {
		return autoHeatLabel;
	}

	public void setAutoHeatLabel(JLabel autoHeatLabel) {
		this.autoHeatLabel = autoHeatLabel;
	}

	public JLabel getAutoCoolLabel() {
		return autoCoolLabel;
	}

	public void setAutoCoolLabel(JLabel autoCoolLabel) {
		this.autoCoolLabel = autoCoolLabel;
	}

	public JButton getHeatOn() {
		return heatOn;
	}

	public void setHeatOn(JButton heatOn) {
		this.heatOn = heatOn;
	}

	public JButton getCoolOn() {
		return coolOn;
	}

	public void setCoolOn(JButton coolOn) {
		this.coolOn = coolOn;
	}

	public JButton getHeatOff() {
		return heatOff;
	}

	public void setHeatOff(JButton heatOff) {
		this.heatOff = heatOff;
	}

	public JButton getCoolOff() {
		return coolOff;
	}

	public void setCoolOff(JButton coolOff) {
		this.coolOff = coolOff;
	}

	public JLabel getConsignaLabel() {
		return consignaLabel;
	}

	public void setConsignaLabel(JLabel consignaLabel) {
		this.consignaLabel = consignaLabel;
	}

	public JSlider getConsignaSlider() {
		return consignaSlider;
	}

	public void setConsignaSlider(JSlider consignaSlider) {
		this.consignaSlider = consignaSlider;
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

	public JButton getFanOn() {
		return fanOn;
	}

	public void setFanOn(JButton fanOn) {
		this.fanOn = fanOn;
	}

	public JButton getFanOff() {
		return fanOff;
	}

	public void setFanOff(JButton fanOff) {
		this.fanOff = fanOff;
	}

}
