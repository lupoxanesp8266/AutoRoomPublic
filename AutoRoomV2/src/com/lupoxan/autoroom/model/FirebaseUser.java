package com.lupoxan.autoroom.model;

import java.util.Arrays;

/**
 * Clase para los usuarios firebase
 * 
 * @since 10/03/2020
 * @author lupo.xan
 * @version 0.2
 */
public class FirebaseUser {
	private String uid;
	private String email;
	private String token;
	private boolean premium;
	private boolean disable;
	private boolean[] notifications;

	public FirebaseUser() {
		super();
	}

	public FirebaseUser(String uid, String email, String token, boolean premium, boolean disable, boolean[] notifications) {
		super();
		this.uid = uid;
		this.email = email;
		this.token = token;
		this.premium = premium;
		this.disable = disable;
		this.notifications = notifications;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public boolean[] getNotifications() {
		return notifications;
	}

	public void setNotifications(boolean[] notifications) {
		this.notifications = notifications;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	@Override
	public String toString() {
		return "Uid: " + uid + "\tEmail: " + email + "\tToken: " + token + "\tPremium: " + premium
				+ "\tDisable: " + disable + "\tNotifications: " + Arrays.toString(notifications);
	}

}
