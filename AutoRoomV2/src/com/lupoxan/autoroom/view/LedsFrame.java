package com.lupoxan.autoroom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import com.lupoxan.autoroom.controller.ChangeStateListeners;
import com.lupoxan.autoroom.model.AutoRoom;
import com.lupoxan.autoroom.model.BackGround;
import com.lupoxan.autoroom.model.Constantes;
import com.pi4j.wiringpi.SoftPwm;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 0.3
 */
public class LedsFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	private GridBagConstraints constraints;
	private BackGround backGround;
	private JButton backButton;

	private JSlider verdeSlider = new JSlider();
	private JSlider rojoSlider = new JSlider();
	private JSlider whiteLSlider = new JSlider();
	private JSlider whiteRSlider = new JSlider();
	private JSlider whiteCSlider = new JSlider();
	private JSlider fadeSlider = new JSlider();
	private JSlider blinkSlider = new JSlider();

	private JLabel verdeValue, rojoValue, whiteLValue, whiteRValue, whiteCValue, fadeValue, blinkValue;
	
	private JCheckBox fadeBox = new JCheckBox();
	private JCheckBox blinkBox = new JCheckBox();
	private JCheckBox ledsAutoBox = new JCheckBox();
	
	private JButton offAll, onAll;

	private ArrayList<JSlider> valores = new ArrayList<JSlider>();

	public LedsFrame(ActionListeners action, ChangeStateListeners changeStateListeners) {
		super();
		constraints = new GridBagConstraints();
		constraints.weightx = 5;
		constraints.weighty = 5;

		try {
			backGround = new BackGround(ImageIO.read(new File("/home/pi/autoRoom/img/blue.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setBorder(backGround);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.GRAY);
		this.setBounds(50, 50, 200, 200);
		
		setAllJSliders();
		setAllLabels();
		setAllValueLabels();
		setAllJCheckBoxs(action);
		
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
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		this.add(backButton, constraints);
		// ON ALL button
		onAll = new JButton("On");
		onAll.setActionCommand("onAllLeds");
		onAll.addActionListener(action);
		onAll.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		onAll.setPreferredSize(new Dimension(100, 50));
		onAll.setBackground(new Color(51, 51, 51));
		onAll.setForeground(new Color(187, 187, 187));
		constraints.gridx = 3;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(onAll, constraints);
		// OFF ALL menu button
		offAll = new JButton("Off");
		offAll.setActionCommand("offAllLeds");
		offAll.addActionListener(action);
		offAll.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		offAll.setPreferredSize(new Dimension(100, 50));
		offAll.setBackground(new Color(255, 0, 0));
		offAll.setForeground(new Color(187, 187, 187));
		constraints.gridx = 5;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(offAll, constraints);
		// Title label
		JLabel title = new JLabel();
		title.setIcon(new ImageIcon("/home/pi/autoRoom/img/LED.jpg"));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.gridheight = 1;
		this.add(title, constraints);

	}

	private void setAllValueLabels() {
		// Verde Value Label
		verdeValue = new JLabel(" 0% ");
		verdeValue.setForeground(new Color(0, 255, 0));
		verdeValue.setOpaque(true);
		verdeValue.setBackground(new Color(255, 255, 255));
		verdeValue.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		verdeValue.setHorizontalAlignment(SwingConstants.CENTER);
		verdeValue.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(verdeValue, constraints);
		// Rojo Value Label
		rojoValue = new JLabel(" 0% ");
		rojoValue.setForeground(new Color(255, 0, 0));
		rojoValue.setOpaque(true);
		rojoValue.setBackground(new Color(255, 255, 255));
		rojoValue.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		rojoValue.setHorizontalAlignment(SwingConstants.CENTER);
		rojoValue.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(rojoValue, constraints);
		// Blanco Left Value Label
		whiteLValue = new JLabel(" 0% ");
		whiteLValue.setForeground(new Color(156, 156, 156));
		whiteLValue.setOpaque(true);
		whiteLValue.setBackground(new Color(255, 255, 255));
		whiteLValue.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		whiteLValue.setHorizontalAlignment(SwingConstants.CENTER);
		whiteLValue.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(whiteLValue, constraints);
		// Blanco Roght Value Label
		whiteRValue = new JLabel(" 0% ");
		whiteRValue.setForeground(new Color(156, 156, 156));
		whiteRValue.setOpaque(true);
		whiteRValue.setBackground(new Color(255, 255, 255));
		whiteRValue.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		whiteRValue.setHorizontalAlignment(SwingConstants.CENTER);
		whiteRValue.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(whiteRValue, constraints);
		// Blanco cama Value Label
		whiteCValue = new JLabel(" 0% ");
		whiteCValue.setForeground(new Color(156, 156, 156));
		whiteCValue.setOpaque(true);
		whiteCValue.setBackground(new Color(255, 255, 255));
		whiteCValue.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		whiteCValue.setHorizontalAlignment(SwingConstants.CENTER);
		whiteCValue.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 2;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(whiteCValue, constraints);
		// Fade Value Label
		fadeValue = new JLabel(" 50 ms ");
		fadeValue.setForeground(new Color(0, 0, 0));
		fadeValue.setOpaque(true);
		fadeValue.setBackground(new Color(255, 255, 255));
		fadeValue.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		fadeValue.setHorizontalAlignment(SwingConstants.CENTER);
		fadeValue.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 5;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(fadeValue, constraints);
		// Blink Value Label
		blinkValue = new JLabel(" 1000 ms ");
		blinkValue.setForeground(new Color(0, 0, 0));
		blinkValue.setOpaque(true);
		blinkValue.setBackground(new Color(255, 255, 255));
		blinkValue.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		blinkValue.setHorizontalAlignment(SwingConstants.CENTER);
		blinkValue.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 5;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(blinkValue, constraints);
	}

	private void setAllJCheckBoxs(ActionListeners action) {
		// Fade Box
		fadeBox = new JCheckBox("Fading");
		fadeBox.setOpaque(true);
		fadeBox.setBackground(new Color(0, 255, 0));
		fadeBox.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		fadeBox.setHorizontalAlignment(SwingConstants.CENTER);
		fadeBox.setPreferredSize(new Dimension(100, 50));
		fadeBox.setActionCommand("fading");
		fadeBox.addActionListener(action);
		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(fadeBox, constraints);
		// Blink Box
		blinkBox = new JCheckBox("Blink");
		blinkBox.setOpaque(true);
		blinkBox.setBackground(new Color(0, 255, 0));
		blinkBox.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		blinkBox.setHorizontalAlignment(SwingConstants.CENTER);
		blinkBox.setPreferredSize(new Dimension(100, 50));
		blinkBox.setActionCommand("blinking");
		blinkBox.addActionListener(action);
		constraints.gridx = 4;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(blinkBox, constraints);
		// AutoLeds Box
		ledsAutoBox = new JCheckBox("Auto");
		ledsAutoBox.setOpaque(true);
		ledsAutoBox.setBackground(new Color(200, 120, 0));
		ledsAutoBox.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		ledsAutoBox.setHorizontalAlignment(SwingConstants.CENTER);
		ledsAutoBox.setPreferredSize(new Dimension(100, 50));
		ledsAutoBox.addActionListener(action);
		ledsAutoBox.setActionCommand("autoLedsBox");
		constraints.gridx = 4;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(ledsAutoBox, constraints);
	}

	public void offAllLeds() {
		verdeSlider.setValue(Constantes.CERO);
		rojoSlider.setValue(Constantes.CERO);
		whiteLSlider.setValue(Constantes.CERO);
		whiteRSlider.setValue(Constantes.CERO);
		whiteCSlider.setValue(Constantes.CERO);

		onAll.setBackground(new Color(51, 51, 51));
		offAll.setBackground(new Color(255, 0, 0));
	}

	private void setAllLabels() {
		// Green title label
		JLabel greenLabel = new JLabel(" Verdes ");
		greenLabel.setForeground(new Color(0, 255, 0));
		greenLabel.setOpaque(true);
		greenLabel.setBackground(new Color(255, 255, 255));
		greenLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		greenLabel.setHorizontalAlignment(SwingConstants.CENTER);
		greenLabel.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(greenLabel, constraints);
		// Red title label
		JLabel redLabel = new JLabel(" Rojos ");
		redLabel.setForeground(new Color(255, 0, 0));
		redLabel.setOpaque(true);
		redLabel.setBackground(new Color(255, 255, 255));
		redLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		redLabel.setHorizontalAlignment(SwingConstants.CENTER);
		redLabel.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(redLabel, constraints);
		// White Left title label
		JLabel whiteLLabel = new JLabel(" Blanco L ");
		whiteLLabel.setForeground(new Color(0, 0, 0));
		whiteLLabel.setOpaque(true);
		whiteLLabel.setBackground(new Color(255, 255, 255));
		whiteLLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		whiteLLabel.setHorizontalAlignment(SwingConstants.CENTER);
		whiteLLabel.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(whiteLLabel, constraints);
		// White Right title label
		JLabel whiteRLabel = new JLabel(" Blanco R ");
		whiteRLabel.setForeground(new Color(0, 0, 0));
		whiteRLabel.setOpaque(true);
		whiteRLabel.setBackground(new Color(255, 255, 255));
		whiteRLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		whiteRLabel.setHorizontalAlignment(SwingConstants.CENTER);
		whiteRLabel.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(whiteRLabel, constraints);
		// White Bed title label
		JLabel whiteCLabel = new JLabel(" Cama ");
		whiteCLabel.setForeground(new Color(0, 0, 0));
		whiteCLabel.setOpaque(true);
		whiteCLabel.setBackground(new Color(255, 255, 255));
		whiteCLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		whiteCLabel.setHorizontalAlignment(SwingConstants.CENTER);
		whiteCLabel.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(whiteCLabel, constraints);
		// Fade title label
		JLabel fadeLabel = new JLabel(" Fade ");
		fadeLabel.setForeground(new Color(0, 255, 0));
		fadeLabel.setOpaque(true);
		fadeLabel.setBackground(new Color(255, 255, 255));
		fadeLabel.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		fadeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fadeLabel.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 3;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(fadeLabel, constraints);
		// Blink title label
		JLabel blinkLabel = new JLabel(" Blink ");
		blinkLabel.setForeground(new Color(0, 255, 0));
		blinkLabel.setOpaque(true);
		blinkLabel.setBackground(new Color(255, 255, 255));
		blinkLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		blinkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		blinkLabel.setPreferredSize(new Dimension(80, 30));
		constraints.gridx = 3;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(blinkLabel, constraints);
	}

	private void setAllJSliders() {
		// Verde Slider
		verdeSlider = new JSlider();
		verdeSlider.setOrientation(0);
		verdeSlider.setOpaque(true);
		verdeSlider.setMaximum(100);
		verdeSlider.setMinimum(0);
		verdeSlider.setMajorTickSpacing(10);
		verdeSlider.setValue(0);
		verdeSlider.setPreferredSize(new Dimension(200, 30));
		verdeSlider.setBackground(new Color(0, 155, 0));
		verdeSlider.setPaintLabels(false);
		verdeSlider.setPaintTicks(true);
		verdeSlider.setPaintTrack(true);
		verdeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				verdeValue.setText(verdeSlider.getValue() + "%");
				SoftPwm.softPwmWrite(AutoRoom.verde, verdeSlider.getValue());
				AutoRoom.DATACLOUD.getDB().child(AutoRoom.F.prop(Constantes.LEDS)).child(AutoRoom.F.prop(Constantes.VERDES)).setValueAsync(verdeSlider.getValue());
			}
		});
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(verdeSlider, constraints);
		// Rojo Slider
		rojoSlider = new JSlider();
		rojoSlider.setOrientation(0);
		rojoSlider.setOpaque(true);
		rojoSlider.setMaximum(100);
		rojoSlider.setMinimum(0);
		rojoSlider.setMajorTickSpacing(10);
		rojoSlider.setValue(0);
		rojoSlider.setPreferredSize(new Dimension(200, 30));
		rojoSlider.setBackground(new Color(255, 0, 0));
		rojoSlider.setPaintLabels(false);
		rojoSlider.setPaintTicks(true);
		rojoSlider.setPaintTrack(true);
		rojoSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				rojoValue.setText(rojoSlider.getValue() + "%");
				SoftPwm.softPwmWrite(AutoRoom.rojo, rojoSlider.getValue());
				AutoRoom.DATACLOUD.getDB().child(AutoRoom.F.prop(Constantes.LEDS))
						.child(AutoRoom.F.prop(Constantes.ROJOS)).setValueAsync(rojoSlider.getValue());
			}
		});
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(rojoSlider, constraints);
		// Blanco Left Slider
		whiteLSlider = new JSlider();
		whiteLSlider.setOrientation(0);
		whiteLSlider.setOpaque(true);
		whiteLSlider.setMaximum(100);
		whiteLSlider.setMinimum(0);
		whiteLSlider.setMajorTickSpacing(10);
		whiteLSlider.setValue(0);
		whiteLSlider.setPreferredSize(new Dimension(200, 30));
		whiteLSlider.setBackground(new Color(153, 153, 153));
		whiteLSlider.setPaintLabels(false);
		whiteLSlider.setPaintTicks(true);
		whiteLSlider.setPaintTrack(true);
		whiteLSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				whiteLValue.setText(whiteLSlider.getValue() + "%");
				SoftPwm.softPwmWrite(AutoRoom.whiteL, whiteLSlider.getValue());
				AutoRoom.DATACLOUD.getDB().child(AutoRoom.F.prop(Constantes.LEDS))
						.child(AutoRoom.F.prop(Constantes.BLANCOL)).setValueAsync(whiteLSlider.getValue());
			}
		});
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(whiteLSlider, constraints);
		// Blanco Right Slider
		whiteRSlider = new JSlider();
		whiteRSlider.setOrientation(0);
		whiteRSlider.setOpaque(true);
		whiteRSlider.setMaximum(100);
		whiteRSlider.setMinimum(0);
		whiteRSlider.setMajorTickSpacing(10);
		whiteRSlider.setValue(0);
		whiteRSlider.setPreferredSize(new Dimension(200, 30));
		whiteRSlider.setBackground(new Color(153, 153, 153));
		whiteRSlider.setPaintLabels(false);
		whiteRSlider.setPaintTicks(true);
		whiteRSlider.setPaintTrack(true);
		whiteRSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				whiteRValue.setText(whiteRSlider.getValue() + "%");
				SoftPwm.softPwmWrite(AutoRoom.whiteR, whiteRSlider.getValue());
				AutoRoom.DATACLOUD.getDB().child(AutoRoom.F.prop(Constantes.LEDS))
						.child(AutoRoom.F.prop(Constantes.BLANCOR)).setValueAsync(whiteRSlider.getValue());
			}
		});
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(whiteRSlider, constraints);
		// Blanco Cama Slider
		whiteCSlider = new JSlider();
		whiteCSlider.setOrientation(0);
		whiteCSlider.setOpaque(true);
		whiteCSlider.setMaximum(70);
		whiteCSlider.setMinimum(0);
		whiteCSlider.setMajorTickSpacing(10);
		whiteCSlider.setValue(0);
		whiteCSlider.setPreferredSize(new Dimension(200, 30));
		whiteCSlider.setBackground(new Color(153, 153, 153));
		whiteCSlider.setPaintLabels(false);
		whiteCSlider.setPaintTicks(true);
		whiteCSlider.setPaintTrack(true);
		whiteCSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				whiteCValue.setText(whiteCSlider.getValue() + "%");
				SoftPwm.softPwmWrite(AutoRoom.ledsCama, whiteCSlider.getValue());
				AutoRoom.DATACLOUD.getDB().child(AutoRoom.F.prop(Constantes.LEDS)).child("blanco_cama")
						.setValueAsync(whiteCSlider.getValue());
			}
		});
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(whiteCSlider, constraints);
		// Fade Slider
		fadeSlider = new JSlider();
		fadeSlider.setOrientation(0);
		fadeSlider.setOpaque(true);
		fadeSlider.setMaximum(200);
		fadeSlider.setMinimum(1);
		fadeSlider.setMajorTickSpacing(10);
		fadeSlider.setValue(50);
		fadeSlider.setPreferredSize(new Dimension(200, 30));
		fadeSlider.setBackground(new Color(0, 155, 0));
		fadeSlider.setPaintLabels(false);
		fadeSlider.setPaintTicks(true);
		fadeSlider.setPaintTrack(true);
		fadeSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				fadeValue.setText(fadeSlider.getValue() + " ms");
				AutoRoom.DATACLOUD.getDB().child(AutoRoom.F.prop(Constantes.LEDS))
						.child(AutoRoom.F.prop(Constantes.TIMERF)).setValueAsync(fadeSlider.getValue());
			}
		});
		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(fadeSlider, constraints);
		// Blink Slider
		blinkSlider = new JSlider();
		blinkSlider.setOrientation(0);
		blinkSlider.setOpaque(true);
		blinkSlider.setMaximum(5);
		blinkSlider.setMinimum(1);
		blinkSlider.setMajorTickSpacing(1);
		blinkSlider.setValue(1);
		blinkSlider.setPreferredSize(new Dimension(200, 30));
		blinkSlider.setBackground(new Color(0, 155, 0));
		blinkSlider.setPaintLabels(false);
		blinkSlider.setPaintTicks(true);
		blinkSlider.setPaintTrack(true);
		blinkSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				blinkValue.setText(blinkSlider.getValue() * 1000 + " ms");
				AutoRoom.DATACLOUD.getDB().child(AutoRoom.F.prop(Constantes.LEDS))
						.child(AutoRoom.F.prop(Constantes.TIMERB)).setValueAsync(blinkSlider.getValue() * 1000);
			}
		});
		constraints.gridx = 4;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(blinkSlider, constraints);

		valores.add(verdeSlider);
		valores.add(rojoSlider);
		valores.add(whiteRSlider);
		valores.add(whiteLSlider);
		valores.add(whiteCSlider);
	}

	public JSlider getVerdeSlider() {
		return verdeSlider;
	}

	public void setVerdeSlider(JSlider verdeSlider) {
		this.verdeSlider = verdeSlider;
	}

	public JSlider getRojoSlider() {
		return rojoSlider;
	}

	public void setRojoSlider(JSlider rojoSlider) {
		this.rojoSlider = rojoSlider;
	}

	public JSlider getWhiteLSlider() {
		return whiteLSlider;
	}

	public void setWhiteLSlider(JSlider whiteLSlider) {
		this.whiteLSlider = whiteLSlider;
	}

	public JSlider getWhiteRSlider() {
		return whiteRSlider;
	}

	public void setWhiteRSlider(JSlider whiteRSlider) {
		this.whiteRSlider = whiteRSlider;
	}

	public JSlider getWhiteCSlider() {
		return whiteCSlider;
	}

	public void setWhiteCSlider(JSlider whiteCSlider) {
		this.whiteCSlider = whiteCSlider;
	}

	public JSlider getFadeSlider() {
		return fadeSlider;
	}

	public void setFadeSlider(JSlider fadeSlider) {
		this.fadeSlider = fadeSlider;
	}

	public JSlider getBlinkSlider() {
		return blinkSlider;
	}

	public void setBlinkSlider(JSlider blinkSlider) {
		this.blinkSlider = blinkSlider;
	}

	public JLabel getVerdeValue() {
		return verdeValue;
	}

	public void setVerdeValue(JLabel verdeValue) {
		this.verdeValue = verdeValue;
	}

	public JLabel getRojoValue() {
		return rojoValue;
	}

	public void setRojoValue(JLabel rojoValue) {
		this.rojoValue = rojoValue;
	}

	public JLabel getWhiteLValue() {
		return whiteLValue;
	}

	public void setWhiteLValue(JLabel whiteLValue) {
		this.whiteLValue = whiteLValue;
	}

	public JLabel getWhiteRValue() {
		return whiteRValue;
	}

	public void setWhiteRValue(JLabel whiteRValue) {
		this.whiteRValue = whiteRValue;
	}

	public JLabel getWhiteCValue() {
		return whiteCValue;
	}

	public void setWhiteCValue(JLabel whiteCValue) {
		this.whiteCValue = whiteCValue;
	}

	public JLabel getFadeValue() {
		return fadeValue;
	}

	public void setFadeValue(JLabel fadeValue) {
		this.fadeValue = fadeValue;
	}

	public JLabel getBlinkValue() {
		return blinkValue;
	}

	public void setBlinkValue(JLabel blinkValue) {
		this.blinkValue = blinkValue;
	}

	public JCheckBox getFadeBox() {
		return fadeBox;
	}

	public void setFadeBox(JCheckBox fadeBox) {
		this.fadeBox = fadeBox;
	}

	public JCheckBox getBlinkBox() {
		return blinkBox;
	}

	public void setBlinkBox(JCheckBox blinkBox) {
		this.blinkBox = blinkBox;
	}

	public JCheckBox getLedsAutoBox() {
		return ledsAutoBox;
	}

	public void setLedsAutoBox(JCheckBox ledsAutoBox) {
		this.ledsAutoBox = ledsAutoBox;
	}

	public JButton getOffAll() {
		return offAll;
	}

	public void setOffAll(JButton offAll) {
		this.offAll = offAll;
	}

	public JButton getOnAll() {
		return onAll;
	}

	public void setOnAll(JButton onAll) {
		this.onAll = onAll;
	}

	public ArrayList<JSlider> getValores() {
		return valores;
	}

	public void setValores(ArrayList<JSlider> valores) {
		this.valores = valores;
	}

}
