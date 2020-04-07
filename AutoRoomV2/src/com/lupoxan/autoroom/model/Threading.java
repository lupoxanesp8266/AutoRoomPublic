/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lupoxan.autoroom.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JSlider;

/**
 * @since 20/10/2019
 * @author lupo.xan
 * @version 1.9
 */
public class Threading extends Thread {

	private static final int BAJA = -1;
	private static final int SUBE = 1;
	private static final int MAX = 100;
	private static final int MIN = 0;

	private int direccion = SUBE;
	private int brillo = 0;

	private JCheckBox checkBox;

	private JSlider tiempo;
	private List<JSlider> valores = new ArrayList<JSlider>();

	private int mode = -1;// Off: 0, Blink: 1, Fade: 2, Cool: 3

	public Threading() {
		start();
	}

	@Override
	public void run() {
		while (true) {
			switch (this.mode) {
			case -1:
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case 0:// Off
				for (int i = 0; i < this.valores.size(); i++) {
					this.valores.get(i).setValue(Threading.MIN);
				}

				this.brillo = Threading.MIN;
				this.mode = -1;
				break;
			case 1:// Blink Mode
				while (this.checkBox.isSelected()) {
					try {

						if (this.brillo == Threading.MAX) {
							this.brillo = Threading.MIN;
						} else {
							this.brillo = Threading.MAX;
						}

						for (int i = 0; i < this.valores.size(); i++) {
							this.valores.get(i).setValue(this.brillo);
						}
						Thread.sleep(this.tiempo.getValue() * 1000);
					} catch (InterruptedException ex) {
						System.err.println(ex.getMessage());
					}
				}
				break;
			case 2:// Fading Mode
				while (this.checkBox.isSelected()) {
					try {
						if (this.brillo >= Threading.MAX) {
							this.direccion = Threading.BAJA;
						}
						if (this.brillo <= Threading.MIN) {
							this.direccion = Threading.SUBE;
						}
						this.brillo += this.direccion;

						for (int i = 0; i < this.valores.size(); i++) {
							this.valores.get(i).setValue(this.brillo);
						}
						Thread.sleep(this.tiempo.getValue());
					} catch (InterruptedException ex) {
						System.err.println(ex.getMessage());
					}
				}
				break;
			case 3:// Cool Mode
				while (this.checkBox.isSelected()) {
					try {
						if (AutoRoom.pir.isHigh()) {
							this.direccion = Threading.SUBE;
						}
						if (AutoRoom.pir.isLow()) {
							this.direccion = Threading.BAJA;
						}

						this.brillo += this.direccion;

						if (this.brillo >= Threading.MAX) {
							this.brillo = Threading.MAX;
						}
						if (this.brillo <= Threading.MIN) {
							this.brillo = Threading.MIN;
						}

						for (int i = 0; i < this.valores.size(); i++) {
							this.valores.get(i).setValue(this.brillo);
						}
						Thread.sleep(15);
					} catch (InterruptedException ex) {
						System.err.println(ex.getMessage());
					}
				}
				break;
			}
		}
	}

	public void setTime(JSlider time) {
		this.tiempo = time;
	}

	public JSlider getTime() {
		return this.tiempo;
	}

	public void setValores(List<JSlider> valores) {
		this.valores = valores;
	}

	public List<JSlider> getValores() {
		return this.valores;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public JCheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(JCheckBox checkBox) {
		this.checkBox = checkBox;
	}
}
