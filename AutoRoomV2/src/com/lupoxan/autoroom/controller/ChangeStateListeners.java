package com.lupoxan.autoroom.controller;

import java.awt.Color;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.lupoxan.autoroom.model.AutoRoom;

/**
 * @since 01/03/2020
 * @author lupo.xan
 * @version 0.4
 *
 */
public class ChangeStateListeners implements ChangeListener {
	
	private String command;
	
	public ChangeStateListeners() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangeStateListeners(String command) {
		super();
		this.command = command;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		switch (command) {
		case "autoHeat":
			if (AutoRoom.mainFrame.getComfort().getAutoHeat().isSelected()) {
				AutoRoom.mainFrame.getComfort().getAutoHeatLabel().setVisible(true);
				AutoRoom.mainFrame.getComfort().getAutoCool().setVisible(false);
				AutoRoom.mainFrame.getComfort().getHeatOn().setVisible(false);
				AutoRoom.mainFrame.getComfort().getHeatOff().setVisible(false);
				AutoRoom.mainFrame.getComfort().getCoolOn().setVisible(false);
				AutoRoom.mainFrame.getComfort().getCoolOff().setVisible(false);
			} else {
				AutoRoom.mainFrame.getComfort().getAutoHeatLabel().setVisible(false);
				AutoRoom.mainFrame.getComfort().getAutoCool().setVisible(true);
				AutoRoom.mainFrame.getComfort().getHeatOn().setVisible(true);
				AutoRoom.mainFrame.getComfort().getHeatOff().setVisible(true);
				AutoRoom.mainFrame.getComfort().getCoolOn().setVisible(true);
				AutoRoom.mainFrame.getComfort().getCoolOff().setVisible(true);
			}
			break;
		case "autoCool":
			if (AutoRoom.mainFrame.getComfort().getAutoCool().isSelected()) {
				AutoRoom.mainFrame.getComfort().getAutoCoolLabel().setVisible(true);
				AutoRoom.mainFrame.getComfort().getAutoHeat().setVisible(false);
				AutoRoom.mainFrame.getComfort().getHeatOn().setVisible(false);
				AutoRoom.mainFrame.getComfort().getHeatOff().setVisible(false);
				AutoRoom.mainFrame.getComfort().getCoolOn().setVisible(false);
				AutoRoom.mainFrame.getComfort().getCoolOff().setVisible(false);
			} else {
				AutoRoom.mainFrame.getComfort().getAutoCoolLabel().setVisible(false);
				AutoRoom.mainFrame.getComfort().getAutoHeat().setVisible(true);
				AutoRoom.mainFrame.getComfort().getHeatOn().setVisible(true);
				AutoRoom.mainFrame.getComfort().getHeatOff().setVisible(true);
				AutoRoom.mainFrame.getComfort().getCoolOn().setVisible(true);
				AutoRoom.mainFrame.getComfort().getCoolOff().setVisible(true);
			}
			break;
		case "consignaSlider":
			switch(AutoRoom.mainFrame.getComfort().getConsignaSlider().getValue()) {
			case 10:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,0,255));
				break;
			case 11:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,20,235));
				break;
			case 12:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,40,215));
				break;
			case 13:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,60,195));
				break;
			case 14:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,80,175));
				break;
			case 15:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,100,155));
				break;
			case 16:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,120,135));
				break;
			case 17:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,140,115));
				break;
			case 18:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,160,95));
				break;
			case 19:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,180,75));
				break;
			case 20:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,200,55));
				break;
			case 21:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,220,35));
				break;
			case 22:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,240,15));
				break;
			case 23:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(0,255,0));
				break;
			case 24:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(20,235,0));
				break;
			case 25:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(40,215,0));
				break;
			case 26:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(60,195,0));
				break;
			case 27:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(80,175,0));
				break;
			case 28:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(100,155,0));
				break;
			case 29:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(120,135,0));
				break;
			case 30:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(140,115,0));
				break;
			case 31:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(160,95,0));
				break;
			case 32:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(180,75,0));
				break;
			case 33:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(200,55,0));
				break;
			case 34:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(240,15,0));
				break;
			case 35:
				AutoRoom.mainFrame.getComfort().getConsignaSlider().setBackground(new Color(255,0,0));
				break;
			}
			AutoRoom.mainFrame.getComfort().getConsignaLabel().setText(AutoRoom.mainFrame.getComfort().getConsignaSlider().getValue() + "ºC");
			AutoRoom.DATACLOUD.getDB().child("comfort").child("consigna").setValueAsync(AutoRoom.mainFrame.getComfort().getConsignaSlider().getValue());
			break;
		}
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
