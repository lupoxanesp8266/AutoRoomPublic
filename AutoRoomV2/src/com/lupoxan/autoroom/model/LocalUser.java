package com.lupoxan.autoroom.model;

/**
 * Clase para los usuarios locales
 * 
 * @since 10/03/2020
 * @author lupo.xan
 * @version 0.1
 */
public class LocalUser {
	private int id;
	private String name;
	private String pass;
	private int level;
	private String modo;
	
	public LocalUser() {
		super();
	}

	public LocalUser(int id,String name, String pass, int level, String modo) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.level = level;
		this.modo = modo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
