package com.lupoxan.autoroom.model;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.border.Border;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 0.1
 */
public class BackGround implements Border{

	private BufferedImage mImagen = null;
	
	public BackGround(BufferedImage mImagen) {
		super();
		this.mImagen = mImagen;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (mImagen != null) {
            g.drawImage(mImagen, 0, 0, width, height, null);
        }
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}

	@Override
	public boolean isBorderOpaque() {
		// TODO Auto-generated method stub
		return true;
	}

}
