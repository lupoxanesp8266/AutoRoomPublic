package com.lupoxan.autoroom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.model.BackGround;
import com.lupoxan.autoroom.model.LocalUser;

/**
 * Frame para los ajustes de la base de datos local
 * 
 * @since 09/03/2020
 * @author lupo.xan
 * @version 0.2
 *
 */
public class MariaDBFrame extends JPanel {

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
	private JButton backButton;
	/**
	 * 
	 */
	private DefaultTableModel userLocalModel;
	/**
	 * 
	 */
	JTable localUserTable;
	/**
	 * 
	 */
	JTextField nameField, modoField, levelField;
	/**
	 * 
	 */
	JPasswordField passField;

	public MariaDBFrame(ActionListeners action) {
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
		welcome.setIcon(new ImageIcon("/home/pi/autoRoom/img/mariaDB.png"));
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
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(backButton, constraints);
		// Table
		String[] columns = { "ID", "Nombre", "Nivel", "Modo", "Contraseña" };
		userLocalModel = new DefaultTableModel(columns, 0) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}

		};
		localUserTable = new JTable(userLocalModel);
		localUserTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		localUserTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!localUserTable.getSelectionModel().isSelectionEmpty()) {
					//String id = localUserTable.getModel().getValueAt(localUserTable.getSelectedRow(), 0).toString();
					nameField.setText(localUserTable.getModel().getValueAt(localUserTable.getSelectedRow(), 1).toString());
					passField.setText(localUserTable.getModel().getValueAt(localUserTable.getSelectedRow(), 4).toString());
					modoField.setText(localUserTable.getModel().getValueAt(localUserTable.getSelectedRow(), 3).toString());
					levelField.setText(localUserTable.getModel().getValueAt(localUserTable.getSelectedRow(), 2).toString());
				}
			}
		});

		JScrollPane table = new JScrollPane(localUserTable);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 4;
		constraints.gridheight = 5;
		this.add(table, constraints);
		// Label user
		JLabel userLabel = new JLabel("Usuario");
		userLabel.setForeground(new Color(0, 0, 0));
		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(userLabel, constraints);
		// Name JTextField
		nameField = new JTextField(15);
		nameField.setForeground(new Color(0, 0, 0));
		nameField.setBackground(new Color(204, 204, 204));
		nameField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		nameField.setPreferredSize(new Dimension(200, 30));
		nameField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		constraints.gridx = 5;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(nameField, constraints);
		// Label user
		JLabel passWordLabel = new JLabel("Contraseña");
		userLabel.setForeground(new Color(0, 0, 0));
		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(passWordLabel, constraints);
		// Name JTextField
		passField = new JPasswordField(15);
		passField.setForeground(new Color(0, 0, 0));
		passField.setBackground(new Color(204, 204, 204));
		passField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		passField.setPreferredSize(new Dimension(200, 30));
		passField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		constraints.gridx = 5;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(passField, constraints);
		// Label user
		JLabel modoLabel = new JLabel("Modo");
		modoLabel.setForeground(new Color(0, 0, 0));
		constraints.gridx = 4;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(modoLabel, constraints);
		// Name JTextField
		modoField = new JTextField(15);
		modoField.setForeground(new Color(0, 0, 0));
		modoField.setBackground(new Color(204, 204, 204));
		modoField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		modoField.setPreferredSize(new Dimension(200, 30));
		modoField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		constraints.gridx = 5;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(modoField, constraints);
		// Label user
		JLabel levelLabel = new JLabel("Nivel");
		levelLabel.setForeground(new Color(0, 0, 0));
		constraints.gridx = 4;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(levelLabel, constraints);
		// Name JTextField
		levelField = new JTextField(15);
		levelField.setForeground(new Color(0, 0, 0));
		levelField.setBackground(new Color(204, 204, 204));
		levelField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		levelField.setPreferredSize(new Dimension(200, 30));
		levelField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		constraints.gridx = 5;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(levelField, constraints);
		JButton aceptarButton = new JButton("Aceptar");
		constraints.gridx = 5;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(aceptarButton, constraints);
	}

	public void fillTable(List<LocalUser> userList) {
		int filas = userLocalModel.getRowCount();
		if (filas > 0) {
			for (int i = filas - 1; i >= 0; i--) {
				userLocalModel.removeRow(i);
			}
		}

		for (int i = 0; i < userList.size(); i++) {
			LocalUser localUser = userList.get(i);
			Object[] row = { localUser.getId(), localUser.getName(), localUser.getLevel(), localUser.getModo(),
					localUser.getPass() };
			userLocalModel.addRow(row);
		}

	}
}
