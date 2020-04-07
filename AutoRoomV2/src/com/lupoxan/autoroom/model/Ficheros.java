/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lupoxan.autoroom.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase para la utilización de ficheros en el sistema
 * @author lupo.xan
 * @since 20/01/2019
 * @version 0.8
 */
public class Ficheros {

    /**
     * Método para leer el archivo properties
     * @param property Clave del properties
     * @return Valor de la clave
     */
    public String prop(String property) {
        String string = null;
        FileReader entrada = null;
        Properties prop = null;
        try {
            entrada = new FileReader(Constantes.FICHEROPROP);
            prop = new Properties();
            prop.load(entrada);

            string = prop.getProperty(property);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return string;
    }

    /**
     * Método para escribir un fichero dada una ruta y un String
     * @param ruta Ruta para guardar el fichero de texto
     * @param msj Mensaje que queremos escribir en el fichero
     */
    protected void escribir(String ruta, String msj) {
        try {
            File archivo = new File(ruta);
            FileWriter salida = new FileWriter(archivo, true);
            BufferedWriter buffer = new BufferedWriter(salida);
            buffer.write(msj);
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para leer de un fichero de texto
     * @param ruta Ruta donde esté el fichero
     * @return Contenido del fichero
     */
    protected String leer(String ruta) {
        String linea, texto = "";
        try (FileReader entrada = new FileReader(ruta)) {
            BufferedReader buffer = new BufferedReader(entrada);

            while ((linea = buffer.readLine()) != null) {
                texto += linea;
                texto += "\n";
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return texto;
    }
}
