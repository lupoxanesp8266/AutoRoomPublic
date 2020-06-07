package com.lupoxan.autoroom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.lupoxan.autoroom.controller.ActionListeners;
import com.lupoxan.autoroom.model.AutoRoom;
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
	 * Versión de la interfaz gráfica de usuario
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Botón que vuelve al menú de ajustes
	 */
	private JButton backButton;
	/**
	 * Modelo para la tabla que se rellena con todos los usuarios registrados
	 */
	private DefaultTableModel userLocalModel;
	/**
	 * Tabla que se rellena con todos los usuarios registrados
	 */
	JTable localUserTable;
	/**
	 * Campos para la información del usuario local
	 */
	JTextField nameField, levelField;
	/**
	 * Campo de la contraseña del usuario local
	 */
	JPasswordField passField;
	/**
	 * Label para poner el usuario
	 */
	JLabel usuario;
	JComboBox<String> modoBox;

	public MariaDBFrame(ActionListeners action) {
		super();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 5;
		constraints.weighty = 7;

		this.setBorder(AutoRoom.BACK_GROUND);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.GRAY);
		this.setBounds(50, 50, 200, 200);

		// Title Label
		JLabel welcome = new JLabel();
		welcome.setIcon(new ImageIcon("/home/pi/autoRoom/img/mariaDB.png"));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(welcome, constraints);

		usuario = new JLabel();
		usuario.setOpaque(true);
		usuario.setBackground(new Color(255, 255, 255));
		usuario.setForeground(new Color(0, 0, 0));
		usuario.setHorizontalAlignment(SwingConstants.CENTER);
		usuario.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		usuario.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(usuario, constraints);

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
		String[] columns = { "ID", "Nombre", "Nivel", "Modo" };
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
					/*
					 * nameField.setText(localUserTable.getModel().getValueAt(localUserTable.
					 * getSelectedRow(), 1).toString());
					 * modoField.setText(localUserTable.getModel().getValueAt(localUserTable.
					 * getSelectedRow(), 3).toString());
					 * levelField.setText(localUserTable.getModel().getValueAt(localUserTable.
					 * getSelectedRow(), 2).toString());
					 */
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
		nameField.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
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
		passField.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
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
		levelField = new JTextField(15);
		levelField.setEnabled(false);
		levelField.setText("2");
		levelField.setForeground(new Color(0, 0, 0));
		levelField.setBackground(new Color(204, 204, 204));
		levelField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		levelField.setPreferredSize(new Dimension(200, 30));
		levelField.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
		constraints.gridx = 5;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(levelField, constraints);
		
		modoBox = new JComboBox<String>();
		modoBox.addItem("usuario");
		modoBox.addItem("admin");
		modoBox.addActionListener(action);
		modoBox.setActionCommand("changeLevel");
		modoBox.setForeground(new Color(0, 0, 0));
		modoBox.setBackground(new Color(204, 204, 204));
		modoBox.setPreferredSize(new Dimension(200, 30));
		modoBox.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
		constraints.gridx = 5;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(modoBox, constraints);
		// Label user
		JLabel levelLabel = new JLabel("Nivel");
		levelLabel.setForeground(new Color(0, 0, 0));
		constraints.gridx = 4;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(levelLabel, constraints);

		JButton aceptarButton = new JButton("Añadir");
		aceptarButton.setActionCommand("addlocaluser");
		aceptarButton.addActionListener(action);
		aceptarButton.setBackground(new Color(102, 255, 102));
		aceptarButton.setForeground(new Color(0, 0, 0));
		aceptarButton.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		aceptarButton.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 5;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(aceptarButton, constraints);

		JButton deleteButton = new JButton("Eliminar");
		deleteButton.setActionCommand("deletelocaluser");
		deleteButton.addActionListener(action);
		deleteButton.setBackground(new Color(255, 0, 0));
		deleteButton.setForeground(new Color(0, 0, 0));
		deleteButton.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		deleteButton.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 4;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(deleteButton, constraints);

		JButton updateButton = new JButton("Modificar");
		updateButton.setActionCommand("updatelocaluser");
		updateButton.addActionListener(action);
		updateButton.setBackground(new Color(251, 163, 26));
		updateButton.setForeground(new Color(0, 0, 0));
		updateButton.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		updateButton.setPreferredSize(new Dimension(150, 50));
		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(updateButton, constraints);
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
			Object[] row = { localUser.getId(), localUser.getName(), localUser.getLevel(), localUser.getModo(), };
			userLocalModel.addRow(row);
		}

	}

	public JTextField getNameField() {
		return nameField;
	}

	public void setNameField(JTextField nameField) {
		this.nameField = nameField;
	}

	public JTextField getLevelField() {
		return levelField;
	}

	public void setLevelField(JTextField levelField) {
		this.levelField = levelField;
	}

	public JPasswordField getPassField() {
		return passField;
	}

	public void setPassField(JPasswordField passField) {
		this.passField = passField;
	}

	public JTable getLocalUserTable() {
		return localUserTable;
	}

	public void setLocalUserTable(JTable localUserTable) {
		this.localUserTable = localUserTable;
	}

	public JLabel getUsuario() {
		return usuario;
	}

	public void setUsuario(JLabel usuario) {
		this.usuario = usuario;
	}

	public JComboBox<String> getModoBox() {
		return modoBox;
	}

	public void setModoBox(JComboBox<String> modoBox) {
		this.modoBox = modoBox;
	}

}
