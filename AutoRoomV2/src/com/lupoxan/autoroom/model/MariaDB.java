/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lupoxan.autoroom.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * Clase para la utilización de la base de datos local
 * 
 * @since 04/08/2019
 * @author lupo.xan
 * @version 1.16
 */
public class MariaDB {

	private static Connection dbConnection = null;
	private final Ficheros F = new Ficheros();
	private List<LocalUser> userList = new ArrayList<LocalUser>();

	public MariaDB() {
		try {
			Class.forName(F.prop(Constantes.CLASSFORNAME));
			dbConnection = DriverManager.getConnection(F.prop(Constantes.LOCALDATABASE), F.prop(Constantes.LOCALUSER),
					F.prop(Constantes.LOCALPASS));
		} catch (ClassNotFoundException | SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Método para recuperar la conexión a la base de datos
	 * 
	 * @return Conexión a la base de datos
	 */
	public static Connection getDbConnection() {
		return dbConnection;
	}

	/**
	 * Método para añadir usuarios a la base de datos local
	 * 
	 * @param user Nombre de usuario
	 * @param pass Contraseña de usuario
	 * @return True si es correcto, False si no lo es
	 */
	public boolean addUser(LocalUser user) {
		boolean resultado = false;
		if (user.getName().equals("") || user.getPass().equals("")) {
			JOptionPane.showMessageDialog(null, "Escribe al menos un usuario y una contraseña");
		} else {
			try {
				PreparedStatement statement = MariaDB.getDbConnection().prepareStatement("INSERT INTO users (nombre, contrasenia, level, modo) VALUES (?, md5(?), ?, ?)");
				statement.setString(1, user.getName());
				statement.setString(2, user.getPass());
				statement.setInt(3, user.getLevel());
				statement.setString(4, user.getModo());

				if (statement.executeUpdate() == 1) {
					resultado = true;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resultado;
	}

	public boolean deleteUser(int id) {
		boolean resultado = false;
		try {
			PreparedStatement statement = MariaDB.dbConnection.prepareStatement("delete from users where id = ?");
			statement.setInt(1, id);

			int result = statement.executeUpdate();

			if (result == 1) {
				resultado = true;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return resultado;
	}
	
	public boolean password(LocalUser user, String password) {
		boolean resultado = false;
		
		try {
			PreparedStatement statement = dbConnection.prepareStatement("select contrasenia from users WHERE id = ? and contrasenia = md5(?)");

			statement.setInt(1, user.getId());
			statement.setString(2, password);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				resultado = true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return resultado;
	}

	public boolean updateUser(LocalUser user) {
		boolean resultado = false;
		try {
			PreparedStatement statement = MariaDB.dbConnection.prepareStatement("UPDATE users set nombre = ?, contrasenia = md5(?), level = ?, modo = ? where id = ?");
			statement.setString(1, user.getName());
			statement.setString(2, user.getPass());
			statement.setInt(3, user.getLevel());
			statement.setString(4, user.getModo());
			statement.setInt(5, user.getId());

			int result = statement.executeUpdate();

			if (result == 1) {
				resultado = true;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return resultado;
	}

	public boolean addLog(String message, Timestamp date) {
		boolean resultado = false;
		try {
			PreparedStatement statement = MariaDB.dbConnection.prepareStatement("INSERT INTO logs VALUES (null,?,?)");
			statement.setString(1, message);
			statement.setTimestamp(2, date);

			int result = statement.executeUpdate();

			if (result == 1) {
				resultado = true;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return resultado;
	}

	public boolean addError(String message, Timestamp date) {
		boolean resultado = false;
		try {
			PreparedStatement statement = MariaDB.dbConnection.prepareStatement("INSERT INTO errors VALUES (null,?,?)");
			statement.setString(1, message);
			statement.setTimestamp(2, date);

			int result = statement.executeUpdate();

			if (result == 1) {
				resultado = true;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return resultado;
	}

	public LocalUser getUser(String name, String pass) {
		LocalUser user = null;
		try {
			PreparedStatement statement = dbConnection
					.prepareStatement("select * from users WHERE nombre = ? and contrasenia = md5(?)");

			statement.setString(1, name);
			statement.setString(2, pass);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				user = new LocalUser(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4),
						result.getString(5));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return user;
	}

	public List<LocalUser> getAllUsers() {
		
		try {
			Statement statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from users");
			while(resultSet.next()) {
				userList.add(new LocalUser(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getString(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userList;
	}

}
