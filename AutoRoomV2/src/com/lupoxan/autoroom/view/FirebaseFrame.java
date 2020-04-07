package com.lupoxan.autoroom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.controller.ChangeStateListeners;
import com.lupoxan.autoroom.model.BackGround;
import com.lupoxan.autoroom.model.FirebaseUser;

/**
 * Frame para ajustes de firebase
 * 
 * @since 09/03/2020
 * @author lupo.xan
 * @version 0.6
 */
public class FirebaseFrame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private BackGround backGround;
	/**
	 * 
	 */
	private JButton backButton, okButton;
	/**
	 * 
	 */
	private JCheckBox premiunBox, disableUser;
	/**
	 * 
	 */
	private DefaultTableModel userFirebaseModel;
	/**
	 * 
	 */
	JTable firebaseUserTable;

	public FirebaseFrame(ActionListeners action) {
		super();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 5;
		constraints.weighty = 7;

		try {
			backGround = new BackGround(ImageIO.read(new File("/home/pi/autoRoom/img/blue.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setBorder(backGround);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.GRAY);
		this.setBounds(50, 50, 200, 200);

		// Title Label
		JLabel welcome = new JLabel();
		welcome.setIcon(new ImageIcon("/home/pi/autoRoom/img/firebase.png"));
		welcome.setPreferredSize(new Dimension(300, 100));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		this.add(welcome, constraints);

		// Back to tools button
		backButton = new JButton("Volver");
		backButton.setActionCommand("tools");
		backButton.addActionListener(action);
		backButton.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		backButton.setPreferredSize(new Dimension(100, 50));
		backButton.setBackground(new Color(255, 255, 255));
		backButton.setForeground(new Color(0, 204, 0));
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(backButton, constraints);
		// Tokens label
		JLabel labelTokens = new JLabel(" Premium ");
		labelTokens.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		labelTokens.setForeground(new Color(255, 255, 255));
		labelTokens.setOpaque(true);
		labelTokens.setBackground(new Color(0, 0, 0));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(labelTokens, constraints);
		// Disable label
		JLabel labelDisable = new JLabel(" Disable User ");
		labelDisable.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		labelDisable.setForeground(new Color(255, 255, 255));
		labelDisable.setOpaque(true);
		labelDisable.setBackground(new Color(0, 0, 0));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(labelDisable, constraints);
		// Premium JCheckBox
		premiunBox = new JCheckBox("Premium");
		premiunBox.setOpaque(true);
		premiunBox.setBackground(new Color(185, 61, 0));
		premiunBox.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		premiunBox.setHorizontalAlignment(SwingConstants.CENTER);
		premiunBox.setPreferredSize(new Dimension(200, 50));
		premiunBox.addChangeListener(new ChangeStateListeners("premiumBox"));
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(premiunBox, constraints);
		// Disabled JCheckButton
		disableUser = new JCheckBox("Enable / Disable");
		disableUser.setOpaque(true);
		disableUser.setBackground(new Color(255, 0, 0));
		disableUser.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		disableUser.setHorizontalAlignment(SwingConstants.CENTER);
		disableUser.setPreferredSize(new Dimension(200, 50));
		disableUser.addChangeListener(new ChangeStateListeners("disableBox"));
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(disableUser, constraints);
		// Aceptar button
		okButton = new JButton("Aceptar");
		okButton.setActionCommand("changeFirebaseUser");
		okButton.addActionListener(action);
		okButton.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		okButton.setPreferredSize(new Dimension(100, 50));
		okButton.setBackground(new Color(255, 255, 255));
		okButton.setForeground(new Color(0, 204, 0));
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(okButton, constraints);
		// Table
		String[] columns = {"Uid" , "Email" , "Token" , "Premium" , "Inhabilitado" , "Notificaciones" };
		userFirebaseModel = new DefaultTableModel(columns, 0) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}

		};
		firebaseUserTable = new JTable(userFirebaseModel);
		firebaseUserTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		firebaseUserTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!firebaseUserTable.getSelectionModel().isSelectionEmpty()) {

					if (firebaseUserTable.getModel().getValueAt(firebaseUserTable.getSelectedRow(), 3).equals(true)) {
						premiunBox.setSelected(true);
					} else {
						premiunBox.setSelected(false);
					}
					
					if (firebaseUserTable.getModel().getValueAt(firebaseUserTable.getSelectedRow(), 4).equals(true)) {
						disableUser.setSelected(true);
					} else {
						disableUser.setSelected(false);
					}
				}
			}
		});

		JScrollPane table = new JScrollPane(firebaseUserTable);
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 4;
		constraints.gridheight = 4;
		this.add(table, constraints);

	}

	public void fillTable(List<FirebaseUser> userList) {
		int filas = userFirebaseModel.getRowCount();
		if (filas > 0) {
			for (int i = filas - 1; i >= 0; i--) {
				userFirebaseModel.removeRow(i);
			}
		}

		for (int i = 0; i < userList.size(); i++) {
			FirebaseUser firebaseUser = userList.get(i);
			Object[] row = {firebaseUser.getUid(), firebaseUser.getEmail(), firebaseUser.getToken(), firebaseUser.isPremium(), firebaseUser.isDisable(), Arrays.toString(firebaseUser.getNotifications()) };
			userFirebaseModel.addRow(row);
		}

	}

	public JCheckBox getPremiunBox() {
		return premiunBox;
	}

	public void setPremiunBox(JCheckBox premiunBox) {
		this.premiunBox = premiunBox;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JCheckBox getDisableUser() {
		return disableUser;
	}

	public void setDisableUser(JCheckBox disableUser) {
		this.disableUser = disableUser;
	}

	public JTable getFirebaseUserTable() {
		return firebaseUserTable;
	}

	public void setFirebaseUserTable(JTable firebaseUserTable) {
		this.firebaseUserTable = firebaseUserTable;
	}

}
