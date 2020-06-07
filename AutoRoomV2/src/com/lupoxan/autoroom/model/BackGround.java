package com.lupoxan.autoroom.model;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.border.Border;

/**
 * @since 29/02/2020
 * @author lupo.xan
 * @version 0.2
 */
public class BackGround implements Border{

	private BufferedImage image = null;
	
	public BackGround() {
		super();
		try {
			this.image = ImageIO.read(new File("/home/pi/autoRoom/img/blue.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (image != null) {
            g.drawImage(image, 0, 0, width, height, null);
        }
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}

	@Override
	public boolean isBorderOpaque() {
		
		return true;
	}

}
