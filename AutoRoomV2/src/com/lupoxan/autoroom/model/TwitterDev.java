/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lupoxan.autoroom.model;

import twitter4j.DirectMessageList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Clase para utilizar twitter Last Modified 22/10/2019
 * @since 20/10/2018
 * @author lupo.xan
 * @version 0.5
 */
public class TwitterDev {
	/**
	 * Global para Twitter
	 */
	private final Twitter TW;
	private final Ficheros F = new Ficheros();

	/**
	 * Conexión con Twitter (myAccount)
	 */
	public TwitterDev() {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(F.prop(Constantes.CONSUMERKEY))
				.setOAuthConsumerSecret(F.prop(Constantes.CONSUMERSECRET))
				.setOAuthAccessToken(F.prop(Constantes.ACCESSTOKENKEY))
				.setOAuthAccessTokenSecret(F.prop(Constantes.ACCESSTOKENSECRET));
		this.TW = new TwitterFactory(cb.build()).getInstance();
	}

	/**
	 * Método para publicar un Tweet
	 * 
	 * @param msj Mensaje que se quiere publicar
	 */
	protected void sendTweet(String msj) {
		try {
			this.TW.updateStatus(msj);
		} catch (TwitterException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Método para enviar un mensaje privado
	 * 
	 * @param user Nombre de usuario al que queremos enviarle el mensaje
	 * @param msj  El mensaje en cuestión
	 */
	protected void sendDirectMsj(String user, String msj) {

		try {
			this.TW.sendDirectMessage(user, msj);
		} catch (TwitterException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Método para recibir los mensajes privados
	 * 
	 * @return Mensaje
	 */
	protected String getDirectMsj() {
		DirectMessageList directMessages = null;
		try {
			directMessages = this.TW.getDirectMessages(0);
		} catch (TwitterException ex) {
			System.err.println(ex.getMessage());
		}
		return directMessages.toString();
	}
}
